package subvb;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class vberr {
	public static ArrayList missvb(List<List<HasWord>> sentences,MaxentTagger tagger,ArrayList<String> alltags) {
		  int flag=0;
		  ArrayList<Integer> score = new ArrayList<Integer>();
		 
		  for (int v=0;v<alltags.size();v++){
			  

			  int s=0;
			  String str = alltags.get(v).substring(1,alltags.get(v).length()-1);
//			  String st = substring(1,str.length()-1);
			 // System.out.println("tags"+str);
			  String[] t = str.split(", ");
		       String prev="aaa";
			  
			  
			  
		      
		      for ( String current : t)
		      {
			      if(current.equals("VB")||current.equals("VBD")||current.equals("VBP")||current.equals("VBZ"))
			      {
			    	  flag=1;
			      }
			  
		      }
			  
			//  if(alltags.get(v).contains("VB"))
			 //  {flag = 1;}
			  if (flag==0){
				//System.out.println("The "+(v+1)+"th sentence doesn't have a single verb");
				score.add(v+1);  
			  }
			  		  flag=0;
		  }
		  
		  // System.out.println("Score of missvb"+score);
		  return score;
	  }
	  public static ArrayList vterr(List<List<HasWord>> sentences,MaxentTagger tagger,ArrayList<String> alltags) {
		  ArrayList<String> vtrule = new ArrayList<String>();
		  ArrayList<Integer> score = new ArrayList<Integer>();
		  vtrule.add(0,"VBD, VBD"); vtrule.add(1,"VBN VBN"); vtrule.add(2,"VBG, VBN");
		  vtrule.add(3,"VBD, VB");vtrule.add(4,"VB, VB");vtrule.add(5,"VBN, VB");
		  vtrule.add(6,"VBG, VB");vtrule.add(7,"VBG, VBG");vtrule.add(8,"VBD, VBD");
		  vtrule.add(9,"VB, VBD");vtrule.add(10,"VBN, VBD");vtrule.add(11,"VBP, VBD");
		  vtrule.add(12,"VBG, VBD");vtrule.add(13,"VBD, VBP");vtrule.add(14,"VBD, VBP");
		  vtrule.add(15,"VB, VBP");vtrule.add(16,"VBN, VBP");vtrule.add(17,"VBP, VBP");
		  vtrule.add(18,"VBG, VBP");vtrule.add(19,"VBD, VBP");vtrule.add(20,"VBD, VBZ");
		  vtrule.add(21,"VB, VBZ");vtrule.add(22,"VBN, VBZ");vtrule.add(23,"VBP, VBZ");
		  vtrule.add(24,"VBG, VBZ");vtrule.add(25,"VBD, VBZ");
		  
		  int flag=0;
			 
		  for (int v=0;v<alltags.size();v++)
		  {
			for (int r=0;r<vtrule.size();r++) 
			{
			 if(alltags.get(v).matches(vtrule.get(r)))	
				{
				flag=1;
				System.out.println("vtrule.get(r)"+vtrule.get(r));
				}
			}
			if (flag==1){
				//System.out.println("The tags"+(v+1)+"th sentence has a verb tense error"+alltags.get(v));
			score.add(v+1);
			}
			flag=0;
		  }
		  
		  return score;
			}
	  public static ArrayList extraverb(List<List<HasWord>> sentences,MaxentTagger tagger,ArrayList<String> alltags) {
		  ArrayList<Integer> score = new ArrayList<Integer>();
	      for(int j=0;j<alltags.size();j++)
	  {
		  
		 
		  
		     String  str= alltags.get(j).substring(1, alltags.get(j).length()-1);
		    String[] s = str.split(", ");

			 String prev="";
			 for ( String current : s) {

				    if(current.equals("VB")||current.equals("VBP")||current.equals("VBD")||current.equals("VBZ"))
				    {
				    	
				    	if(prev.equals("VB")||prev.equals("VBP")||prev.equals("VBD")||prev.equals("VBZ"))
				    		score.add(j+1);   
				    		//System.out.println(alltags.get(j)+"Sentence has an extra verb");
				    }
				    
				    if(current.equals("VBG"))
				    {
				    	if(prev.equals("VBG"))
				    		score.add(j+1); 
				    		//System.out.println(alltags.get(j)+"Sentence has an extra verb");
				    		
				    }
				    	
			     
			       prev=current;
			  }
		  
	  }
	  return score;
	  }
}
