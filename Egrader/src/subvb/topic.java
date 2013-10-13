package subvb;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;

public class topic {

	
	public static double topicco(List<List<HasWord>> sentences,MaxentTagger tagger,ArrayList<String> alltags) {
		String grammar =  "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
	    String[] options = { "-maxLength", "80", "-retainTmpSubcategories" };
	    LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
	    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

	    
	     
	     System.setProperty("wordnet.database.dir","C:/Program Files (x86)/WordNet/2.1/dict");	
	     ArrayList<String> base = new ArrayList<String>();
	     base.add(0,"child");
	     base.add(1,"offspring");
	     base.add(2,"genitor");
	     base.add(3,"relative");
	     base.add(4,"profession");
	     base.add(5,"territory");
	     base.add(6,"area");
	     base.add(7,"parent");
	     base.add(8,"institution");
	     base.add(9,"language");base.add(10,"social unit");
	     base.add(11,"sibling");
	     
	    
	      
	     
	    
	    double cnt=0.0,match=0.0;
	   int i=1;
	    for (List<? extends HasWord> sentence : sentences) {
	   
	    	 Tree parse = lp.apply(sentence);
	    	 String senttags = parse.taggedYield().toString().substring(1,parse.taggedYield().toString().length()-1);
	              
	         //System.out.println("tags"+parse.taggedYield());
	    	 
	    	 String[] t = senttags.split(", ");
	    	 WordNetDatabase database = WordNetDatabase.getFileInstance();
		    // String[] words="aa";String[] tags="bb"; 
	    	 ArrayList<String> words = new ArrayList<String>();
	    	 ArrayList<String> tags = new ArrayList<String>();
		      i=0;
		      
		      for ( String current : t)
		      {
		    	
		    	       words.add(i,current.substring(0,current.indexOf("/")));
		    	       tags.add(i,current.substring(current.indexOf("/")+1,current.length()));
		    	    
		    			 // System.out.println(current);
		    	       i++;
		    	  
		      }
	    	 
	         
	         
	         for(int j=0;j<tags.size();j++){
	        	 
	        	NounSynset[] hypernym;
				// System.out.println("words"+words.get(j));
	        // System.out.println("tags"+tags.get(j));
	        	
	        	
	        	
	        	
	        	if(words.get(j).equalsIgnoreCase("he")||words.get(j).equalsIgnoreCase("she")||words.get(j).equalsIgnoreCase("they")||words.get(j).equalsIgnoreCase("his")||words.get(j).equalsIgnoreCase("her")||words.get(j).equalsIgnoreCase("their"))
	             {
	            	 cnt++;
	            	 //match++;
	            	 //System.out.println("match cnt "+match+cnt);
	             }
	         
	        	
	             if(words.get(j).equals("I")||words.get(j).equalsIgnoreCase("My"))
	             {
	            	 cnt++;
	            	 match++;
	            	 //System.out.println("match cnt "+match+cnt);
	             }	        	
	        	/*
	        	if(words.get(j).contains("PRP")||tags.get(j).equalsIgnoreCase("it"))
	        	{
	        		cnt++;
	             match++;
	        	}*/
	         
	             else if(tags.get(j).equals("NNP")||tags.get(j).equals("NNPS"))
	        	 {
	        		
	        		 
	        		 match++;
	        		 
	        		 cnt++;
	        		 
	        		 //System.out.println("match cnt "+match+cnt);
	        	 }
	        	 
	        	 else if(tags.get(j).equals("NN")||tags.get(j).equals("NNS"))
	        	 { 	 
	        		 
	        		 
	        		 int flag=0;
	        		 cnt++;
	        	 SynsetType type = SynsetType.NOUN;
	     		 String seed = words.get(j);
	     		 Synset[] synsets = database.getSynsets(seed, type);
	     		 NounSynset Nsyn = (NounSynset)(synsets[0]);
	     		
	     		  hypernym = Nsyn.getHypernyms();
	     		 
	     		
	     		  
	           for(int no=1;no<=2;no++){
		   
		   //System.out.println("From calculate_lcs:");
		   
	  			for(NounSynset NounSyn : hypernym){
	  		
	  				//System.out.println("From calculate_lcs:");
	  				
	  				for( i=0; i<NounSyn.getWordForms().length; i++)
	  				{
	  					
	  					
	  					for(int k=0;k<base.size();k++)
	  					{
	  					if(NounSyn.getWordForms()[i].contains(base.get(k)))
	  					{
	  						
	  						
	  					//System.out.println(NounSyn.getWordForms()[i]);
	  						if(flag==0)
	  						{
	  						match++;
	  						flag=1;
	  						}
	  					//	System.out.println("tags"+match+tags.get(j));
	  					   break;
	  					 					
	  					}
	  				
	  					}
	  					
	  					
	  				}
	  				
	  				hypernym = NounSyn.getHypernyms();
	  				System.out.println();
	  			}
	  				//word_hypernyms.add(verbSyn);
	  				//calculate_lcs_srini(verbSyn, word_hypernyms, length++);
	  			
	  			}
	     		  
	     		  
	        	 }
	     		  
	     		  
	        	 
	 				
	        	 
	        	 }
	        	 
	        	 
	         }
	    	
	  //  System.out.println("Match"+match);
	   // System.out.println("Score"+(match/cnt)*5);
	    
	    double score = ((match/cnt)*5);
         return score;
	
}

}