package subvb;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class subvberr {
	public static ArrayList sverror(List<List<HasWord>> sentences,MaxentTagger tagger,ArrayList<String> alltags) {

	    //String[] alltags = new String[6];
	    ArrayList<String> svrule = new ArrayList<String>();
	    ArrayList<Integer> score = new ArrayList<Integer>();
	    String Sen;
	    int count =0,v=0;
	    svrule.add(0,"NNPS, VBZ");
	    svrule.add(1,"NNS, VBZ");
	    svrule.add(2,"NNP, VBP");
	    svrule.add(3,"NN, VBP, VBD");
	    svrule.add(4,"NNS, VBZ, VBD");
	    //svrule.add(5,"DT, NN");
	    //svrule.add(6,"PRP, IN");
	    svrule.add(5,"NNS, VBN");
	    svrule.add(6,"NN, VBP");
	    
	/*	    
	for(int j=0;j<alltags.size();j++)
	 {
		 System.out.println("List of Tags : "+j+"th"+alltags.get(j));//Sentences inside string array in Tag format
		
		 System.out.println("Sentence:"+sentences.get(j));
	 }*/
	    
	    for (List<HasWord> sentence : sentences) 
		  {       // Loop to process each sentence
			    ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
			    //System.out.println("Position of I:"+Sentence.listToString(tSentence, true));
			    Sen=Sentence.listToString(tSentence, true);
			    //System.out.println("Position of I:"+Sentence.listToString(tSentence, true).indexOf('I'));
			 //}
		  //System.out.println("Sentence with I:"+sentences.toString());
		  
		  String[] words = Sen.split("\\s");
		  String[] tags = alltags.get(v).toString().split(", ");
		  
		/*for (int j=0;j<words.length;j++)
			  System.out.println("Words:"+j+":"+words[j]);
		
		  for (int j=0;j<tags.length;j++)
			  System.out.println("Tags:"+j+":"+tags[j]);*/
		
		  
		  //score.add(0);
		     for (int i=0; i<tags.length-1; i++)
		     {
		    	//making sure that it checks only after first word.
		    	 
		    	//Implementing a rule that I starts new sentence only if followed by verb
		    		 
		    	 if(tags[i].contains("PRP")&&(words[i].matches("he")||words[i].matches("she")||words[i].matches("it"))&&tags[i+1].contains("VBP"))
		         {
		    	  count++;
		    	  score.add(v+1);
		    	  //System.out.println("SvErr Position for block 1 :"+i);
		    	 }
		    	 
		    	 if(tags[i].contains("PRP")&&(words[i].matches("He")||words[i].matches("She")||words[i].matches("It"))&&tags[i+1].contains("VBP"))
		         {
		    	  count++;
		    	  score.add(v+1);
		    	  //System.out.println("SvErr Position for block 2 :"+i);
		    	 }
		    	 
		    	 if(tags[i].contains("PRP")&&(words[i].contains("they")||words[i].contains("we")||words[i].contains("you"))&&tags[i+1].contains("VBZ"))
		         {
		    	  count++;
		    	  score.add(v+1);
		    	  //System.out.println("SvErr Position for block 3 :"+i);
		    	 }
		    	 
		    	 if(tags[i].contains("PRP")&&(words[i].contains("They")||words[i].contains("We")||words[i].contains("You"))&&tags[i+1].contains("VBZ"))
		         {
		    	  count++;
		    	  score.add(v+1);
		    	  //System.out.println("SvErr Position for block 4 :"+i);
		    	 }
		    	
		    	 if(tags[i].contains("PRP")&&(words[i].contains("I"))&&tags[i+1].matches("VBZ"))
		         {
		    	  count++;
		    	  score.add(v+1);
		    	  //System.out.println("SvErr Position for block 5 :"+i);
		    	 }
		    	 
		    	 if(tags[i].contains("PRP")&&(words[i].contains("I"))&&words[i+1].contains("'m")&&tags[i+2].matches("VBD"))
		         {
		    	  count++;
		    	  score.add(v+1);
		    	  //System.out.println("SvErr Position for block 5.1 :"+i);
		    	 }
		    	 
		    	 if(tags[i].contains("PRP")&&(words[i].contains("I"))&&!tags[i+1].contains("VB"))
		         {
		    	  count++;
		    	  score.add(v+1);
		    	  //System.out.println("SvErr Position for block 5.2 :"+i);
		    	 }
		    	 
		    	 //System.out.println("Words :"+words[i]); 	
		     }//End of for loop for each word
		    
		     v++;
		  
	 	    for(int k=0;k<alltags.size();k++)
	 	    	{
	 	    	for (int r=0;r<svrule.size();r++)
	 	    		{
		 
	 	    		if (alltags.get(k).contains(svrule.get(r)))
	 	    		{
		  //System.out.println(r+1+"th Sentence has incorrect subject verb agreement.");
	 	    			score.add(k+1);
	 	    			//System.out.println("SvErr Position for block 6 for rule :"+r);
	 	    		}
	 	    	}
	 	    }
	 } 
	 //for(int x=0;x<alltags.size();x++)
	  /*for(int s=0;s<score.size();s++)
	    System.out.println("SVER Score :"+score.get(s).toString());*/
	    return score;
	}//End of function
	 
	}//End of class

