package edu.smart.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

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
	
	public static String multiValArrayToString(String[] synonymsArray) {
			StringBuilder synomymsBuilder = new StringBuilder();
			for (String synonym : synonymsArray) {
				synomymsBuilder.append(synonym);
				synomymsBuilder.append("$");
			}
			return synomymsBuilder.toString();
	}
	
	public static String mapToString(Map<String,String> synonymsMap) {
		StringBuilder synomymsBuilder = new StringBuilder();
		for (Map.Entry<String,String> entry : synonymsMap.entrySet()) {
			synomymsBuilder.append(entry.getKey() + ":" + entry.getValue());
			synomymsBuilder.append("$");
		}
		return synomymsBuilder.toString();
}
	
	public static Map<String,String> stringToMap(String synonymsString) {
		Map<String,String> synomymsMap = new HashMap<String,String>();
		
		if(StringUtils.isNotEmpty(synonymsString)) {
			StringTokenizer st= new StringTokenizer(synonymsString, "$") ;
			
			while(st.hasMoreElements()){
				String[] synonymsSplitArray = st.nextToken().split(":");
				synomymsMap.put(synonymsSplitArray[0], synonymsSplitArray.length > 1 ? synonymsSplitArray[1] : StringUtils.EMPTY);
			}
		}	
		return synomymsMap;
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
		 Map<String,String> synonymsMap = courseDetailsModel.getKeyConceptSynonyms();
		 String synonymStr = StringUtils.EMPTY;
		    String text=studenttext;
		    String[] pairs=tempconcepts.split("/");
		   List<String> synonymsList = new ArrayList<String>();
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
					   
					   if((!CollectionUtils.isEmpty(synonymsMap)) && StringUtils.isNotEmpty(synonymsMap.get(keyc))) {
						   synonymStr = synonymsMap.get(keyc);
						   String[] synonymArr = synonymStr.split(",");
						   for(int l=0;l<synonymArr.length;l++) {
							   if(text.contains(synonymArr[l])) {
								   synonymsList.add(synonymArr[l]);
							   }
						   }
					   }
				   }
			   }
		   }
		   if(!CollectionUtils.isEmpty(synonymsList)) {
			   for(String synonym : synonymsList) {
					text=text.replaceAll(synonym, "<span>"+synonym+"</span>");
			   }
		   }
       	
		return text;
	}
}
