package subvb;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class proant {

	public static int proant(List<List<HasWord>> sentences,MaxentTagger tagger,ArrayList<String> alltags) {
		int s2a = 5,v=0;
		ArrayList<String> prom = new ArrayList<String>();
		String Sen;
		for (List<HasWord> sentence : sentences) 
		  {       // Loop to process each sentence
			    ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
			    System.out.println("Position of 1	:"+Sentence.listToString(tSentence, true));
			    Sen=Sentence.listToString(tSentence, true);
			    //System.out.println("Position of I:"+Sentence.listToString(tSentence, true).indexOf('I'));
			 //}
		  //System.out.println("Sentence with I:"+sentences.toString());
		  
		  String[] words = Sen.split("\\s");
		 
		  String[] tags = alltags.get(v).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(", ");
		  
		 //for (int j=0;j<words.length;j++)
			  //System.out.println(v+" Words:"+j+":"+words[j]);
		
		  /*for (int j=0;j<tags.length;j++)
		  {
			  if(tags[j].contains("NN"))
			  {
			  System.out.println(v+" Tags -> "+j+":"+tags[j]+" Word -> "+words[j]);
			  }
		  }*/
		for (int j=0;j<tags.length;j++){
			  if(tags[j].matches("NNP"))
			  {
			   if(words[j].matches("Chicago")||words[j].contains("Mexico")||words[j].contains("US")
			  ||words[j].contains("Chihuahua")||words[j].contains("Guerrero")||words[j].contains("El")
			  ||words[j].contains("Salvador")||words[j].contains("India")||words[j].contains("USA")
			  ||words[j].contains("Brasil")||words[j].contains("Nixon")||words[j].contains("School")
			  ||words[j].contains("Panama")||words[j].contains("Texas")||words[j].contains("English")
			  ||words[j].contains("Puerto")||words[j].contains("Rico")||words[j].contains("January")
			  ||words[j].contains("Florida")||words[j].contains("Future"))
			  {
			  prom.add("N"+",S"+","+v+","+j);
			  }
			  else
			  {
				  if((words[j].contains("Karen")||words[j].endsWith("a")))
				  {
				  prom.add("F"+",S"+","+v+","+j);
				  }
				  else
					  if(!words[j].matches("[A-Z].")&&!words[j].matches("[A-Z]"))
				  prom.add("M"+",S"+","+v+","+j);  
			  }
			  }
		  
			if(tags[j].matches("NNPS"))
	 		{
	 		if(words[j].matches("Girls")||words[j].matches("Daughters"))
	 		{
	 		prom.add("F"+",P"+","+v+","+j);
	 		}
	 		
	 		else
	 		{
	 		if(words[j].matches("Boys")||words[j].matches("Sons"))
	 		{
			prom.add("M"+",P"+","+v+","+j);
			}
	 		
	 		else
			{
			prom.add("N"+",P"+","+v+","+j);
			}
	 		}		 			 		
	 		}
			
	 		if(tags[j].matches("NN"))
	 		{
	 		if(words[j].matches("mom")||words[j].matches("mother")||words[j].matches("daughter")
	 		||words[j].matches("sister")||words[j].matches("wife")||words[j].matches("girl"))
	 		{
	 		prom.add("F"+",S"+","+v+","+j);
	 		}
	 		
	 		else
	 		{
	 		if(words[j].matches("brother")||words[j].matches("father")||words[j].matches("husband")
			||words[j].matches("son")||words[j].matches("boy")||words[j].matches("supervisor"))
	 		{
			prom.add("M"+",S"+","+v+","+j);
			}
			else
			prom.add("N"+",S"+","+v+","+j);
			}
	 		
	 		}
	 	  
	 		if(tags[j].matches("NNS"))
	 		{
	 		if(words[j].matches("mothers")||words[j].matches("daughters")
	 		||words[j].matches("sisters")||words[j].matches("wives")||words[j].matches("girls")){
	 		prom.add("F"+",P"+","+v+","+j);
	 		}
	 		else
	 		{
	 		if(words[j].matches("brothers")||words[j].matches("fathers")||words[j].matches("husbands")
			||words[j].matches("sons")||words[j].matches("boys")||words[j].matches("cousins"))
	 		{
			prom.add("M"+",P"+","+v+","+j);
			}
			else
			prom.add("N"+",P"+","+v+","+j);
	 		}
	 		}	 		
	 		}
		++v;
	 	 }
	//////////Running sentence loop again to check the pronoun antecedents/////////////////
		int s=0;
		for (List<HasWord> sentence : sentences) 
		  {       // Loop to process each sentence
			    ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
			    //System.out.println("Position of I:"+Sentence.listToString(tSentence, true));
			    Sen=Sentence.listToString(tSentence, true);
			    //System.out.println("Position of I:"+Sentence.listToString(tSentence, true).indexOf('I'));
			 //}
		  //System.out.println("Sentence with I:"+sentences.toString());
		  
		  String[] words = Sen.split("\\s");
		  String[] tags = alltags.get(s).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(", ");
		  
		  for(int w=0;w<words.length;w++)
		  {
			if(words[w].matches("He")||words[w].matches("he")||words[w].matches("Him")||words[w].matches("him"))  
			{
				for(int p=0;p<prom.size();p++)
				{
				int iv=Integer.parseInt(prom.get(p).substring(4,prom.get(p).indexOf(',',5)));
				int po=Integer.parseInt(prom.get(p).substring(prom.get(p).indexOf(',',5)+1,prom.get(p).length()));
				if(prom.get(p).contains("M,S")&&iv<=p&&iv>=p-2&&w>po)
				{
				//s2a++;	
				}
				else{
				if(prom.get(p).contains("F,S")&&iv<=p&&iv>=p-2&&w>po)
				{
				s2a=1;	
				}
				else
				{
					if (s2a>4)
					s2a--;	
				}
				}
				}
				  
				  
			  }
			
			if(words[w].matches("she")||words[w].matches("She")||words[w].matches("her")||words[w].matches("Her"))  
			{
			for(int p=0;p<prom.size();p++)
			{
			int iv=Integer.parseInt(prom.get(p).substring(4,prom.get(p).indexOf(',',5)));
			int po=Integer.parseInt(prom.get(p).substring(prom.get(p).indexOf(',',5)+1,prom.get(p).length()));
			
			if(prom.get(p).contains("F,S")&&iv<=p&&iv>=p-2&&w>po)
			{
			//s2a++;	
			}
			else{
			if(prom.get(p).contains("M,S")&&iv<=p&&iv>=p-2&&w>po)
			{
			s2a=1;	
			}
			else
			{
			if(s2a>4)
			s2a--;	
			}
			}
			}
			  
			  
		  }
			

			if(words[w].matches("They")||words[w].matches("they")||words[w].matches("their")||words[w].matches("Their"))  
			{
			for(int p=0;p<prom.size();p++)
			{
			int iv=Integer.parseInt(prom.get(p).substring(4,prom.get(p).indexOf(',',5)));
			int po=Integer.parseInt(prom.get(p).substring(prom.get(p).indexOf(',',5)+1,prom.get(p).length()));
			
			if(prom.get(p).contains("N,P")&&iv<=p&&iv>=p-2&&w>po)
			{
			//s2a++;	
			}
			
			else
			{
			if(s2a>4)
			s2a--;	
			}
			}
		  }
			
		  
		  }	
		
		
		  s++;
		  }
		for (int j=0;j<prom.size();j++)
			System.out.println("Prom : "+"Gender :"+prom.get(j).substring(0,1)+
					" Number:"+prom.get(j).substring(2,3)+
					" Sentence:"+prom.get(j).substring(4,prom.get(j).indexOf(',',5))+
					" Position:"+prom.get(j).substring(prom.get(j).indexOf(',',5)+1,prom.get(j).length()));
		System.out.println("S2A Score :"+s2a);
		return s2a;
}
}