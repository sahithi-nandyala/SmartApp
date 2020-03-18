package edu.smart.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import edu.smart.model.CourseDetailsModel;

public class AppendUtil {
	
	public static String listToString(ArrayList<String> listOfString){
		
		String resultString = "";
		StringBuilder tempString = new StringBuilder("");
		
		if(null != listOfString){
		
		for(String eachString : listOfString){
			if(tempString.length()==0){
				tempString.append(eachString);
			}else{
				tempString.append(","+ eachString);
			}
		}
			resultString = tempString.toString();
		}
	
	return resultString;
		
	}
	
	public static ArrayList<String> stringToList(String inputString){
		
		ArrayList<String> resultList = new ArrayList<String>();
		
		
		StringTokenizer st= new StringTokenizer(inputString, ",") ;
		
		while(st.hasMoreElements()){
			resultList.add(st.nextToken());
		}
			
		return resultList;
		
	}
	
	public static String arrayToString(int[] inputArray){
		
		String resultString = "";
		StringBuilder tempString = new StringBuilder("");
				
				if(null != inputArray){
				
				for(int eachString : inputArray){
					if(tempString.length()==0){
						tempString.append(eachString);
					}else{
						tempString.append(","+ eachString);
					}
				}
				resultString = tempString.toString();
				}
			
			return resultString;			
		
	}
	
	public static StringBuilder check(StringBuilder append, Entry<String, Double> entry) {
		if(append.toString().length()>0) {
			append.append("/" + entry.getKey() + ":" + entry.getValue().floatValue());
		}
		else {
			append.append(entry.getKey() + ":" + entry.getValue().floatValue());
		}
		return append;
	}
	
	public String highlightkeyconcepts(CourseDetailsModel courseDetailsModel)
	{
		 String tempconcepts=courseDetailsModel.getConcepthighlightpairs();
		 ArrayList<String> kConcepts=courseDetailsModel.getKeyConcepts();
		    String text=courseDetailsModel.getText();
		    String[] pairs=tempconcepts.split("/");
		    
		    ArrayList<String> doublewords=new ArrayList<String>();
		    ArrayList<String> singlewords=new ArrayList<String>();
		    for(int i=0;i<pairs.length;i++)
		    {
		    	String[] pair=pairs[i].split(":");
		    	if(pair[0].contains(" "))
		    	{
		    		doublewords.add(pairs[i]);
		    	}
		    	else
		    	{
		    		singlewords.add(pairs[i]);
		    	}
		    }
		    
		    for(int k=0;k<doublewords.size();k++)
			 {
		    	String[] pair=doublewords.get(k).toString().split(":");
		    	for(String keyc:kConcepts)
				   {
			    		
						   if(keyc.equals(pair[1]))
						   {
							   text=text.replaceAll(pair[0], "<span><b>"+pair[0]+"</b></span>");
						   }
			    		
				   }
			 }
		    for(int k=0;k<doublewords.size();k++)
			   {
		    	 String[] pair=doublewords.get(k).toString().split(":");
		    	
			    	if(pair[0].length() > 3)
			    	{
					   if(text.contains(pair[0]))
					   {
						   text=text.replaceAll(pair[0], "<b>"+pair[0]+"</b>");
					   }
			    	}
			   }
		    for(int k=0;k<singlewords.size();k++)
			 {
		    	String[] pair=singlewords.get(k).toString().split(":");
		    	for(String keyc:kConcepts)
				   {
			    		
						   if(keyc.equals(pair[1]))
						   {
							   text=text.replaceAll(pair[0], "<span><b>"+pair[0]+"</b></span>");
						   }
			    		
				   }
			 }
		    
/*		   for(int k=0;k<pairs.length;k++)
		   {
			   String[] pair=pairs[k].split(":");
			   
			   for(String keyc:kConcepts)
			   {
				   if(keyc.equals(pair[1]))
				   {
					   text=text.replaceAll(pair[0], "<span><b>"+pair[0]+"</b></span>");
				   }
			   }					   
		   }*/
		    System.out.println(text);
		    for(int k=0;k<singlewords.size();k++)
			   {
		    	String[] pair=singlewords.get(k).toString().split(":");
		    	
		    	  if(pair[0].length() > 3) //change added by ashwini, to avoid words with length less than 3 so thatreference model keywords are highlighted
				  {
					   if(text.contains(pair[0]))
					   {
						   //System.out.println("word id "+ pair[0]);
						   text=text.replaceAll(pair[0], "<b>"+pair[0]+"</b>");
					   }
				   }
			   }
	       	//System.out.println(text);
	       	courseDetailsModel.setText(text);
	       	
		return courseDetailsModel.getText();
	}
	public String highlightkeyconceptsstudent(String studenttext,CourseDetailsModel courseDetailsModel)
	{
		 String tempconcepts=courseDetailsModel.getConcepthighlightpairs();
		 ArrayList<String> kConcepts=courseDetailsModel.getKeyConcepts();
		    String text=studenttext;
		    String[] pairs=tempconcepts.split("/");
		    
		   for(int k=0;k<pairs.length;k++)
		   {
			   String[] pair=pairs[k].split(":");
			   for(String keyc:kConcepts)
			   {
				   if(keyc.equals(pair[1]))
				   {
					   if(text.contains(pair[0]))
					   text=text.replaceAll(pair[0], "<span>"+pair[0]+"</span>");
					   else if(text.contains(pair[1]))
						text=text.replaceAll(pair[1], "<span>"+pair[1]+"</span>");
				   }
			   }
		   }
       	
		return text;
	}
}
