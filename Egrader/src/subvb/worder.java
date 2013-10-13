package subvb;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class worder {
	public static ArrayList wordorder(List<List<HasWord>> sentences)
	  {
		  String grammar = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
		    String[] options = { "-maxLength", "80", "-retainTmpSubcategories" };
		    LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
		    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		    ArrayList<Integer> score = new ArrayList<Integer>();

		    
		     int s=0,v=0,o=0,root=0,advp=0,flag=0,pflag=-1,pp=0;
		     
		     String advfreq="often, sometimes, generally, never, normally, occasionally, rarely, regularly, seldom, sometimes, usually";
		  		    
		   int i=1,sent=1;
		    for (List<? extends HasWord> sentence : sentences) {
		    	
		    	s=0;v=0;o=0;advp=0;flag=0;pflag=-1;pp=0;
		    	 //System.out.println("Sentence"+i);	
		      Tree parse = lp.apply(sentence);
		      //parse.pennPrint();
		      //System.out.println();
		      //System.out.println(parse.taggedYield());
		          
		      //System.out.println();

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
		 	 		    		  
		 	 		    		  
		 	 		    		 else if((prev.contains("NN")||prev.contains("NNP")) && (!gs.getNodeByIndex(j-2).nodeString().equals("CD")) && v>0)
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
		    				   //System.out.println("here");
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
		    	
		    	   
				  // System.out.println("Wrong Word Order in v,o,s,flag,pflag"+v+o+s+flag+pflag);
				   score.add(sent);
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
		    		    		  if(!advfreq.contains(current.substring(0,current.indexOf('/')).toLowerCase()))
		    		    		  //System.out.println("Wrong word order1"+current.substring(0,current.indexOf('/')));
		    		    			  score.add(sent);
		    		    	  }
		    		      }
		    		   
		    	   }
		    	   
		    	   
		    	   if(advp-v>=2)
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
		 		    		  if(advfreq.contains(current.substring(0,current.indexOf('/')).toLowerCase()))
		 		    		  {
		 		    			 
		 		    		  //System.out.println("Wrong word order2");
		 		    			 score.add(sent);
		 		    		  }
		 		    	  }
		 		      }
		    	   }
		    	   
		    	   
		       }
		      
		   
		      //System.out.println(tdl);
		      //System.out.println();
		      i++;
		      sent++;
		    }
  //System.out.println("Score for Word Order"+score);
		return score;   
	  }
			
			

}
