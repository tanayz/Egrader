package subvb;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;

public class sentform {

	
		public static ArrayList sentord(List<List<HasWord>> sentences,MaxentTagger tagger,ArrayList<String> alltags) {
	    String grammar =  "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
	    String[] options = { "-maxLength", "80", "-retainTmpSubcategories" };
	    LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
	    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

	    
	     int s=0,v=0,o=0,root=0,advp=0,flag=0,pflag=-1,pp=0,ws=0;
	     
	     String advfreq="often, sometimes, generally, never, normally, occasionally, rarely, regularly, seldom, usually, not";
	      
	     
	     ArrayList<Integer> score = new ArrayList<Integer>();	 
	  
	    
	   int i=1,sent=1;
	    for (List<? extends HasWord> sentence : sentences) {
	    	
	    	
	    	sentence=sentence.subList(0, sentence.size()-1);
	    	
	    	s=0;v=0;o=0;advp=0;ws=0;
	    	// System.out.println("Sentence"+i);	
	      Tree parse = lp.apply(sentence);
	     // parse.pennPrint();
	      //System.out.println();
	      //System.out.println("tags"+parse.taggedYield());
	      
	      
	    
	    

	      GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
	      Collection tdl = gs.typedDependenciesCCprocessed(true);
	      
	      // System.out.println("Nodes"+gs.getNodes());
	      
	      
	     
	       
	       
	       
	       
	       
	      
	        
	       
	       for (int j=1;j<gs.getNodes().size();j++)
	       {
	    	  
	    		 if(gs.getNodeByIndex(j).nodeString().contains("ROOT"))  
	    	   root=j;
	       }
	    
	       
	       
	       for( int j=root+2;j<gs.getNodes().size();j++)
	    	   
	       {
	    	   
	    	   if(gs.getNodeByIndex(j).nodeString().equals("NP"))
	    	   {
	    		   if(s==0)
	    		   {
	    			   s=j;
	    			   
	    			   }
	    		   else
	    		   {o=j;}
	    	   }
	    	   
	    	   if(gs.getNodeByIndex(j).nodeString().contains("VBZ")||gs.getNodeByIndex(j).nodeString().contains("VBD")||gs.getNodeByIndex(j).nodeString().equals ("VB")||gs.getNodeByIndex(j).nodeString().contains("VBP"))
	    	   {
	    		   v=j;
	    	   }
	    	   
	    	   if(gs.getNodeByIndex(j).nodeString().contains("RB"))
	    	   {
	    		   
	    		 //  System.out.println("Adverb");
	    		   if(advp==0)
	    		   advp=j;
	    		  
	    		  		   
	    	   }
	    	   
	    	   
	    	   if(gs.getNodeByIndex(j).nodeString().contains("JJ"))
	    	   {
	    		   
	    		   String str= parse.taggedYield().toString().substring(1,parse.taggedYield().toString().length()-1);
	 		       String[] t = str.split(", ");
	 		       flag=0;
	 		      String prev=null;
	 		      for ( String current : t)
	 		      {
	 		    	 
	 		    	 
	 		    	  if(current.contains("JJ"))
	 		    	  {
	 		    		 
	 		    		 if(gs.getNodeByIndex(j-2).nodeString().equals("CC"))
	 		    		 flag=0;
	 		    		  
	 		    		 else if(prev.contains("PRP"))
	 		    			 flag=0;
	 		    		  
	 		    		 else if((prev.contains("NN")||prev.contains("NNP") && (!gs.getNodeByIndex(j-2).nodeString().equals("CD")) && v>0))
	 		    		  {
	 		    			  flag=1;
	 		    			  
	 		    		  // System.out.println("flag"+flag+"prev"+prev+"curr"+current);		    		 
	 		    		  }
	 		    		  
	 		    		  
	 		    		  
	 		    	  }
	 		    	  prev=current;
	 		      }
	    		   
	    		   
	    	   }
	    	   
	    	  
	    	   
	    	   
	    	   if(gs.getNodeByIndex(j).nodeString().contains("PP"))
	    	   {
	    		   pp=j;
	    		   
	    		   for(int k=pp+1;k<=gs.getNodes().size();k++)
	    		   {
	    			   
	    			   
	    			   if(gs.getNodeByIndex(k).nodeString().contains("NP") || gs.getNodeByIndex(k).nodeString().contains("VBG") || gs.getNodeByIndex(k).nodeString().contains("NP")
	    					   )
	    			   {
	    				  pflag=0;
	    				  // System.out.println("here");
	    			   }
	    			   else
	    			   {
	    				   if(pflag==-1||pp-s==2)
	    				   pflag=1;
	    				  }
	    			   
	    			  // System.out.println("pp,j,flag"+pp+k+pflag);
	    		   }
	    		   
	    		   
	    	   }
	    	   
	    	   
	    	   
	    	   
	       }
	         
	       
	       if(gs.getNodes().contains("TO"))
	       {
	       if(v>o && gs.getNodeByIndex(v-2).nodeString().contains("TO"))
	           	  
	       flag=0;
	       
	       else
	    	   flag=1;
	    	   
	       }
	       
	      
	    	   
	    if((v<s && v>0)||pflag==1||flag==1||gs.getNodeByIndex(gs.getNodes().size()-1).nodeString().contains("TO"))
	       
	       {
	    	
	    	   if(s==0||o==0)
	    	   {
	    		 System.out.println("Here9"+sent);
	    	   ws=1;
	    	   
	    	   }
			   //System.out.println("Wrong Word Order in v,o,s"+v+o+s+"flag"+flag+"pflag"+pflag);
	       } 
	       
	       else
	       {
	    	   
	    	   
	    	   
	    	   if(v>advp)
	    	   {
	    		   
	    		   
	    		   String str= parse.taggedYield().toString().substring(1,parse.taggedYield().toString().length()-1);
	    		      String[] t = str.split(", ");
	    		       flag=0;
	    		      
	    		      for ( String current : t)
	    		      {
	    		    	  
	    		    	  if(current.contains("RB")&&flag==0)
	    		    	  {
	    		    		  flag=1;
	    		    		  
	    		    		  if(!advfreq.contains(current.substring(0,current.indexOf('/')).toLowerCase())){}
	    		    		  //System.out.println("Wrong word order1"+current.substring(0,current.indexOf('/')));
	    		    			 // System.out.println("Wrong word order1"+(current.substring(0,current.indexOf('/'))));
	    		    	  }
	    		      }
	    		   
	    	   }
	    	   
	    	   
	    	   if(advp-v>=1)
	    	   {
	    		   
	    		   
	    		   String str= parse.taggedYield().toString().substring(1,parse.taggedYield().toString().length()-1);
	 		       String[] t = str.split(", ");
	 		       flag=0;
	 		      
	 		      for ( String current : t)
	 		      {
	 		    	  
	 		    	  
	 		    	  
	 		    	  if(current.contains("RB")&&flag==0)
	 		    	  {
	 		    		 
	 		    		  flag=1;
	 		    		 
	 		    		 // if(current.substring(0,current.indexOf('/')).contains("often"))
	 		    		  if(advfreq.equalsIgnoreCase(current.substring(0,current.indexOf('/'))))
	 		    		  {
	 		    			 
	 		    		 // System.out.println("Wrong word order2"+current.substring(current.indexOf('/')+1,current.length()));
	 		    		  }
	 		    	  }
	 		      }
	    	   }
	    	   
	    	   
	       }
	      
	   
	      //System.out.println(tdl);
	     // System.out.println();
	      i++;
	      
	      
	      int nodeS=0,sbar=0,adjp=0;
	      
	      try
	      {
	      
	      for(int j=1;j<gs.getNodes().size();j++)
	      {
	   	   
	   	   if(gs.getNodeByIndex(j).nodeString().equals("S"))
	   		   nodeS=j;
	   	   if(gs.getNodeByIndex(j).nodeString().equals("FRAG"))
	   		   ws=1;
	   	   if(gs.getNodeByIndex(j).nodeString().equals("SBAR"))
	   	   
	   		   sbar=j;
	   	   
	   	if(gs.getNodeByIndex(j).nodeString().equals("ADJP"))
	    	   
			   adjp=j;
	   		  
	   	  
	      }
	      
         if(!parse.toString().contains("VP") || nodeS==0)
	      {
	    	  ws=1;
	    	 System.out.println("Here8"+sent);
	      }
	      
	      if(!(gs.getNodeByIndex(nodeS+1).nodeString().equals("NP") || gs.getNodeByIndex(nodeS+1).nodeString().equals("CC")))
	      {
	   	   ws=1;
	     System.out.println("Here7"+sent);
	   	   
	      }
	      
	      if(!(gs.getNodeByIndex(nodeS).parent().nodeString().equals("SBAR")||gs.getNodeByIndex(nodeS).parent().nodeString().equals("ROOT")||(gs.getNodeByIndex(nodeS).parent().nodeString().equals("NP") && gs.getNodeByIndex(s).nodeString().equals("NP"))))
	    	
	      {
	    	  ws=1;
	    	  System.out.println("Here6"+sent);
	      }
	      if(sbar>0)
	      {
	   	   
	          if(!gs.getNodeByIndex(sbar).parent().nodeString().equals("VP")||!gs.getNodeByIndex(sbar).parent().nodeString().equals("S"))
	          {
	        	  ws=1;
	   	  System.out.println("Here5"+sent);
	          }
	      }   
	      
	      
	  
	   if(gs.getNodeByIndex(adjp+3).toString().contains("NN") && !((gs.getNodeByIndex(adjp+2).toString().contains("CD"))))
	   {
		   ws=1;
  		 System.out.println("Here 4"+sent);

	   }
	  
	     
	      
	      
	      String str= parse.taggedYield().toString().substring(1,parse.taggedYield().toString().length()-1);
	       String[] t = str.split(", ");
	       String prev="aaa";
	       flag=0;
	      
	      for ( String current : t)
	      {
	    	     	  
	    	  
	    	 if(prev.equals("PRP$") && !(current.contains("NN")||current.contains("JJ")))
	    	  {
	    		 ws=1;
	    		 System.out.println("Here 3"+sent);
	    	  }
	    	  
	    	  if(prev.contains("VBG")&& current.contains("NN"))
	    	  {
	    		 ws=1;
	    		System.out.println("Here 2"+sent);
	    		  
	    	  }
	    	  
	    	  if((prev.equals("an/DT")||prev.equals("a/DT"))&& current.contains("NNP"))
	    	  {
	    		 ws=1;
	    		 System.out.println("Here9");
	    		  
	    	  }
	    	  
	    	  
	    	/*  if((current.equals("I/NN")||current.equals("My/NN")))
	    	  {
	    		 ws=1;
	    		 System.out.println("Here9");
	    		  
	    	  }
	    	  */
	    	  
	    	  if((prev.equals("VBZ")||(prev.equals("VBP")))&& current.contains("DT"))
	    	  {
	    		 ws=1;
	    		System.out.println("Here 12"+sent);
	    		  
	    	  }
	    	  
	    	  
	    	  if(prev.contains("IN") &&  !(current.contains("DT")||current.contains("NN")||current.contains("CD")))
	    	  {
	    	   ws=1;
	    	   System.out.println("Here 1"+sent);
	    	  }
	    	  
	    	  
	    	  
	    	  prev=current;
	      }
	      
	      }
	      
	      catch(Exception e){}
	      
	      
	      
	      if(ws==1)
	    	  
	      {
	      	//System.out.println("Wrong Word Structure");
	      score.add(sent);
	      
	      }
	      
	     // System.out.println("Score for Sent struct"+score); 
	      
	      sent++;
	    }

	   
	    
	 
	 //  System.out.println("Score of Sentorder"+score);
	    return score;
	    
	  }
	
	
	
}
