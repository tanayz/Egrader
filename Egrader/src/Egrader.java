
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.io.PrintStream;

import java.util.regex.*;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import subvb.*;

class Egrade {
	
	//private static List<List<Integer>> grade = new ArrayList<List<Integer>>();
	//private static List<Integer> row = new ArrayList<Integer>();
	
  private Egrade() {}

  @SuppressWarnings("unchecked")
public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.err.println("usage: Essay folder missing");
      return;
    }
    
    List<String> results = new ArrayList<String>();
    File[] files = new File(args[0]).listFiles();

    for (File file : files) {
        if (file.isFile()) {
            results.add(args[0]+"\\"+file.getName());
        }
    }
       
    for(int i=0;i<results.size();i++)
    	System.out.println("Filename: "+results.get(i));
    
    String input;
    input=results.get(0).toString();
    MaxentTagger tagger = new MaxentTagger("models/wsj-0-18-bidirectional-distsim.tagger");//Loading the model for the Tagger
    /////////////////////////////////////////////////////////////////////////////////////
    int[][] gv = new int[1][7];
    String[][] sa = new String[results.size()][9];
    double fn=0;
    
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////Start of file loop//////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    for(int f=0;f<results.size();f++)
    {
    	input=results.get(f).toString();
    ArrayList<String> sent = new ArrayList<String>();
    BufferedReader br = new BufferedReader(new FileReader(input));
    String line;
    int l=0;
    while ((line = br.readLine()) != null)  {
    	if(!line.endsWith("."))
    		line=line+".";
    		
    	sent.add(line);
    	//System.out.println("Sentences "+sent.get(l));
    	l++;
    }
    br.close();
    
    File file = new File(input);
    
	// if file doesn't exists, then create it
	if (!file.exists()) {
		file.createNewFile();
	}

	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
	for (int w=0;w<sent.size();w++)
		if(sent.get(w).toString().endsWith("U.S."))
			bw.write(sent.get(w).toString().replace("U.S.", "US .")+"\n");
		else
			bw.write(sent.get(w).toString().replace("U.S.", "US ")+"\n");
	bw.close();

	System.out.println("Done with writing and adding period.......");
    
    ////////////////////////////////////////////////////////////////////////////////////////
    List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader(input)));
    List<String> allMatches = new ArrayList<String>();
    ArrayList<String> alltags = new ArrayList<String>();
    int i=0,nu=0,cnt=0;
//////////////////Using regular expression to extract tags //////////////////////////////
    Pattern pattern = Pattern.compile("(/[A-Z]+|/[,])"); 
										 

    for (List<HasWord> sentence : sentences) {       // Loop to process each sentence
    ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
    //System.out.println(Sentence.listToString(tSentence, false));

    Matcher matcher = pattern.matcher(Sentence.listToString(tSentence, false));
    while (matcher.find())
    {
    	allMatches.add(matcher.group().substring(1, matcher.group().length()));
    	//System.out.println("Matched Regexp : "+matcher.group().substring(1, matcher.group().length()));
    }
    alltags.add(i,allMatches.toString());
    allMatches.clear();
    i++;
    }
    ////////////////////////Calling the sentence splitter function//////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    	
    cnt=slength(sentences,tagger,alltags,input);
    
    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    allMatches.clear();
    alltags.clear();
    sentences.clear();
    i=0;
    sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader(input)));
    
    
//////////////////Using regular expression to extract tags //////////////////////////////
    pattern = Pattern.compile("(/[A-Z]+|/[,])"); 
    for (List<HasWord> sentence : sentences) {       // Loop to process each sentence
    ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
    System.out.println(Sentence.listToString(tSentence, false));

    Matcher matcher = pattern.matcher(Sentence.listToString(tSentence, false));
    while (matcher.find())
    {
    	allMatches.add(matcher.group().substring(1, matcher.group().length()));
    	//System.out.println("Matched Regexp : "+matcher.group().substring(1, matcher.group().length()));
    }
    alltags.add(i,allMatches.toString());
    allMatches.clear();
    i++;
    }
    //////////////////Declaring score arrays///////////////////////////////////////////////////
    ArrayList<Integer> svers = new ArrayList<Integer>();
    ArrayList<Integer> woers = new ArrayList<Integer>();
    ArrayList<Integer> msvbs = new ArrayList<Integer>();
    ArrayList<Integer> exvbs = new ArrayList<Integer>();
    ArrayList<Integer> vters = new ArrayList<Integer>();
    ArrayList<Integer> soers = new ArrayList<Integer>();
    //////////////////Calling functions from subvb package/////////////////////////////////////
    int s2a=0;double s2b=0;
    soers=sentform.sentord(sentences,tagger,alltags);
    woers=worder.wordorder(sentences);
    svers=subvberr.sverror(sentences,tagger,alltags);
    msvbs=vberr.missvb(sentences,tagger,alltags);
   exvbs=vberr.extraverb(sentences, tagger, alltags);
    vters=vberr.vterr(sentences,tagger,alltags);
    s2a = proant.proant(sentences, tagger, alltags);
    s2b = topic.topicco(sentences, tagger, alltags);
    gv=grading(soers,woers,svers,msvbs,exvbs,vters,cnt);
    
    /*for(int d=0;d<svers.size();d++)
    System.out.println("We have SVErr at : "+svers.get(d));
    
    for(int d=0;d<msvbs.size();d++)
    System.out.println("We have MSVErr at : "+msvbs.get(d));
    
    for(int d=0;d<vters.size();d++)
    System.out.println("We have VTErr at : "+vters.get(d));*/
    int s2b1=0,two=2,thr=3;
    System.out.println("Everything :"+fn);s2b1=(int)Math.ceil(s2b);
    fn=((double)gv[0][0]+(double)gv[0][1]+(double)gv[0][2]+two*(double)(gv[0][3])+s2a+thr*(s2b1)+(double)gv[0][6])*0.1;
    
    double rounded = (double)(long)((fn * 2) + 0.5) / 2;
    
    
   sa[f][0]= input.substring(5, input.length());
   sa[f][1]=Integer.toString(gv[0][0]);
   sa[f][2]=Integer.toString(gv[0][1]);
   sa[f][3]=Integer.toString(gv[0][2]);
   sa[f][4]=Integer.toString(gv[0][3]);
   sa[f][5]=Integer.toString(s2a);
   sa[f][6]=Integer.toString(s2b1);
   sa[f][7]=Integer.toString(gv[0][6]);
   sa[f][8]=Double.toString(rounded);
    }
    File file = new File("Output.txt");
    
	// if file doesn't exists, then create it
	
	FileWriter fk = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bk = new BufferedWriter(fk);
	
    
    System.out.println("Printing the final Score list.............................");
	bk.write("Printing the final Score list.............................\r\n"); 
	  System.out.println("File\t1a\t1b\t1c\t1d\t2a\t2b\t3a\tFinal");
	  bk.write("File\t1a\t1b\t1c\t1d\t2a\t2b\t3a\tFinal\r\n");
	  for (int r=0;r<results.size();r++)
	  {
		  for (int c=0;c<9;c++)
    {
    	System.out.print(sa[r][c]+"\t");
    	bk.write(sa[r][c]+"\t");
    }
		  System.out.println("");
		  bk.write("\r\n");
		  
	  }
	 bk.close(); 
   	}

  public static int slength(List<List<HasWord>> sentences,MaxentTagger tagger,ArrayList<String> alltags,String input) throws IOException {
	  int count=alltags.size(),i,v=0,c=0,ss=0,sw=0,marks=0;//Using the tags to determine initial number of sentence in a line
	  String k= new String();
	  String news= new String();
	  ArrayList<Integer> oc = new ArrayList<Integer>();
	  ArrayList<String> ns = new ArrayList<String>();
	  
	  File file = new File(input);
	    
		// if file doesn't exists, then create it
		
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
	  
	  for (List<HasWord> sentence : sentences) 
	  {       // Loop to process each sentence
		    ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
		    //System.out.println("Position of I:"+Sentence.listToString(tSentence, true));
		    k=Sentence.listToString(tSentence, true);
		    //System.out.println("Position of I:"+Sentence.listToString(tSentence, true).indexOf('I'));
		 //}
	  //System.out.println("Sentence with I:"+sentences.toString());
	  
	  String[] words = k.split("\\s");
	  String[] tags = alltags.get(v).toString().split(", ");
	  oc.add(0);
	     for (i=0; i<words.length; i++)
	     {
	    	//making sure that it checks only after first word.
	    	 
	    	 if (i>1&&i<tags.length-1){
	    		//Implementing a rule that I starts new sentence only if followed by verb
	    	 if(words[i].trim().matches("I")&&tags[i+1].contains("VB")&&(i+2<words.length))
	         {
	    	  count++;
	    	  oc.add(i);
	    	 }
	    	 
	    	//Implementing a rule that He/She/It/They start new sentence
	    	if((i+2<words.length)&&((words[i].trim().matches("He")||words[i].trim().matches("She")||words[i].trim().matches("It")||words[i].trim().matches("They")||words[i].trim().matches("My")||words[i].trim().matches("Their")||words[i].trim().matches("His")||words[i].trim().matches("Her"))&&(tags[i+1].contains("VB"))))
	    	{
	    	count++;
	    	oc.add(i);
	    	}
	    	//Implementing a rule that he/she/it/they if not after verb/conjunction start new sentence
	    	if(words[i].trim().matches("he")||words[i].trim().matches("she")||words[i].trim().matches("it")||words[i].trim().matches("they")||words[i].trim().matches("my")||words[i].trim().matches("their")||words[i].trim().matches("his")||words[i].trim().matches("her"))
	    	{
	    		if (i+2<tags.length&&!tags[i-1].matches("CC")&&(i+2<words.length)&&((tags[i+1].contains("VB")||tags[i+1].matches("IN"))))
		    	 {count++;
		    	 oc.add(i);
		    	 }	
	    	}
	    	//Implementing a rule that and can't be followed and preceded by noun/pronoun in same sentence
	    	if(words[i].trim().matches("and"))
	    	{
	    		if ((i+1<words.length)&&(tags[i-1].contains("NN")||tags[i-1].contains("PRP"))&&!((tags[i+1].contains("NN")||tags[i+1].contains("PRP")||tags[i+1].contains("CD"))))
	    		{ count++;
	    		oc.add(i+1);
	    		}
	    		
	    	}
	    	//Implementing a rule that and can't be followed and preceded by noun/pronoun in same sentence
	    	//Implementing a rule that and can't be followed and preceded by noun/pronoun in same sentence
	    	if(words[i].trim().matches("[A-Z][a-z]+")&&tags[i].contains("NN") )
	    	{
	    		if ((i+2<words.length)&&(tags[i-1].contains("CC")|| words[i-1].contains("end")))
	    		{ count++;
	    		oc.add(i);
	    		}
	    		
	    	}
	    	
	    	 }
	    	
	    	 //System.out.println("Words :"+words[i]); 	
	     }
	     
	     //System.out.println("Number of sentences are : "+count);
	     
	     v++;
	     oc.add(words.length); 
	     for(ss=c;ss<oc.size()-1;ss++)
	     {
	    	 //Creating new set of sentences
	    	 
	    	 //System.out.println("OC Size : "+oc.size()+" C Val:"+c);
	    	 news="";
	    	 if(ss+1<oc.size())
	    	 {
	    	 for(sw=oc.get(ss);sw<oc.get(ss+1);sw++)
	    	 {
	    		if(sw<words.length)
	    		 news=news+" "+words[sw].replace(",","").replace(".",""); 
	    		
	    	 }
	    	  }
	    	 
	    	 if(!news.endsWith("."))
	    		 news=news+".";
	    	  	 ns.add(news);
	    	 
	    	 }
	     c=ss; //Handling multiple sentences
	     }
	       
	  //////////////////////////////////////////
	  for(int n=0;n<oc.size();n++)
	     {
	    	 //////Check the nominal clause/////////////////////////////////////
		 // System.out.println(oc.get(n));		  
	     }
	  //////////////////////////////////////////
	  
	  for(int n=0;n<ns.size();n++)
	     {
	    	 //////Check the nominal clause/////////////////////////////////////
		  if(ns.get(n).length()>1)
		  {
		  //System.out.println(ns.get(n));
			  //System.out.println(ns.get(n).toString()+"\n");
		  bw.write(ns.get(n).toString()+"\r\n");
		  
		  }
		  }
	 // System.out.println("Done with writing...........");
	  
	  
	  /////////////////////////Adding value to grade array///////////////////////////////////
	  
	  if(count>6)
		  marks=5;
	  else 
		  if(count>0)
		  marks=count-1;

	  ///////////////////////////////////////////////////////////////////////////////////////
	  bw.close();
	return count;  
  }
  public static int[][] grading(ArrayList<Integer> soers,ArrayList<Integer> woers,ArrayList<Integer> svers,ArrayList<Integer> msvbs,ArrayList<Integer> exvbs,ArrayList<Integer> vters,Integer cnt) throws IOException {
  
	  /////////////////////////////////New codes/////////////////////////////////////
	  /////////////////////////Initializing the Grade array///////////////////////////
	  int[][] grade = new int[cnt][7];
	  int[][] gv = new int[1][7];
	  
	  for (int r=0;r<cnt;r++)
	    {
	    	for (int c=0;c<7;c++)
	        {
	        	grade[r][c]=1;
	        			
	        }
	    	System.out.println("");		
	    }
	  //////////////////////Capturing Subject Verb Errors//////////////////////////// 
	  for(int s=0;s<woers.size();s++)
	  {
		  grade[woers.get(s)-1][0]=0;  
	  }
	  //////////////////////Capturing Subject Verb Errors//////////////////////////// 
	  for(int s=0;s<svers.size();s++)
	  {
		  if(svers.get(s)>0)
		  grade[svers.get(s)-1][1]=0;  
	  }
	  /////////////////////Capturing Verb Tense Errors///////////////////////////////
	  /////////////////////Capturing Missing Verb Error//////////////////////////////
	  for(int m=0;m<msvbs.size();m++)
	  {
		  grade[msvbs.get(m)-1][2]=0;
		  //grade[msvbs.get(m)-1][1]=0;
	  }
	  /////////////////////Capturing Extra Verb Error//////////////////////////////
	  for(int m=0;m<exvbs.size();m++)
	  {
		  grade[exvbs.get(m)-1][2]=0; 
		  //System.out.println("Debug "+exvbs.get(m));
	  }
	  /////////////////////Capturing Verb Tense Error///////////////////////////////
	  for(int v=0;v<vters.size();v++)
	  {
		  grade[vters.get(v)-1][2]=0;  
	  }
	  /////////////////////Capturing Sentence Order Error///////////////////////////////
	  for(int v=0;v<soers.size();v++)
	  {
		  grade[soers.get(v)-1][3]=0;  
	  }
	  //////////////////////Printing the Grade array/////////////////////////////////
	  System.out.println("Printing the final grade array.............................");
	  System.out.println("1a\t1b\t1c\t1d\t2a\t2b\t3a");
	  for (int r=0;r<cnt;r++)
	  {
		  for (int c=0;c<7;c++)
      {
      	System.out.print(grade[r][c]+"\t");		
      }
		  System.out.println("");		
	  }
	  /////////////////////Printing Scores Section-wise///////////////////////////////
	  /////////////////////////For Word Order///////////////////////////////////////
	  int wos=0;
	  for(int s=0;s<cnt;s++)
	  {
		  wos=wos+grade[s][0];
	  }
	  System.out.println("(1a)Word Order Score of the Essay is      :"+(int)Math.ceil(5*(wos/cnt)));
	  //gv[0][0]=(int)Math.ceil(5*wos/cnt);
	  gv[0][0]=(int)Math.round(5*wos/cnt);
	  /////////////////////////For Subject Verb///////////////////////////////////////
	  int svs=0;
	  for(int s=0;s<cnt;s++)
	  {
		  svs=svs+grade[s][1];
	  }
	  
	  System.out.println("(1b)Subject Verb Score of the Essay is    :"+(int)Math.ceil((float)(svs*5)/cnt));
	  //gv[0][1]=(int)Math.ceil((float)(svs*5)/cnt);
	  gv[0][1]=(int)Math.round((float)(svs*5)/cnt); 
	  /////////////////////////For Verb Tense///////////////////////////////////////
	  int vts=0;
	  for(int s=0;s<cnt;s++)
	  {
		  vts=vts+grade[s][2];
	  }
	  System.out.println("(1c)Verb Tense Score of the Essay is      :"+(int)Math.ceil(5*vts/cnt));
	 // gv[0][2]=(int)Math.ceil(5*vts/cnt);
	  gv[0][2]=(int)Math.round(5*vts/cnt);
	  /////////////////////////For Sentence Order///////////////////////////////////////
	  int sos=0;
	  for(int s=0;s<cnt;s++)
	  {
		  sos=sos+grade[s][3];
	  }
	  
	  System.out.println("(1d)Sentence order of Score of the Essay is :"+Math.round((sos*5)/cnt));
	  //System.out.println("(1b)Subject Verb Score of the Essay is    :"+(int)Math.ceil((float)(sos*5)/cnt));
	  //gv[0][3]=(int)Math.ceil((float)(sos*5)/cnt);
	  
	  gv[0][3]=Math.round((sos*5)/cnt);
	  
	  
	  
	  
	  ////////////////////For pronoun antecedent///////////////////////////////////
	  
	  
	  gv[0][5]=1; //Assigning dummy value for 2b category
	  ////////////////////////For Sentence Length//////////////////////////////////
	  int sls=0;
	  if(cnt>=6)
		  sls=5;
	  else sls = cnt-1;
	  System.out.println("(3a)Sentence Length Score of the Essay is :"+sls);
	  gv[0][6]=sls;
	  return gv;
  }
  }
  


	  
	  
  
	  
  
  
  
  


