package edu.smart.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import edu.smart.dao.CourseDetailsDaoImpl;
import edu.smart.model.CourseDetailsModel;
import edu.smart.pojo.CourseDetails;
import edu.smart.pojo.UserDetails;
import edu.smart.util.AppendUtil;
import edu.smart.util.DisplayGraph;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

public class SmartTestBoImpl {
	
	@Autowired
	CourseDetailsDaoImpl courseDetailsDaoImpl;
	
	/*@Autowired
	CourseDetailsModel courseDetailsModel;
*/
	@Autowired
	Runner runner;
	
	public List<String> finalReln=new ArrayList<String>();
	public List<String> finalConcepts=new ArrayList<String>();
	public int finalTF[];
	public int relation_freq[];
	// Set<String> concepthighlightpairs = new HashSet<String>();
	 HashMap<String,String> concepthighlightpairs=new  HashMap<String,String> ();
	
	public CourseDetailsModel smartTestNplImpl(CourseDetails courseDetails, String type){
		CourseDetailsModel courseDetailsModel=new CourseDetailsModel();
		String model = courseDetails.getModel().toString();
		String text = courseDetails.getText();
		courseDetailsModel.setThreshold(courseDetails.getThreshold());
		courseDetailsModel.setCentrality(courseDetails.getCentrality());
		List<String> concepts=new ArrayList<String>();
		List<String> AllRelations=new ArrayList<String>();
		char[] c= {'!','@','#','$','%','^','&','*','(',')','|','\\','/',',','`','~',':',';','<','>'};
		String newtext=text;
		for(int i=0;i<c.length;i++)
		{
			newtext=newtext.replace(c[i], ' ');
		}
		text=newtext;
		 	Properties props = new Properties();
	        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, natlog, openie");
	        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	        
	        Annotation document = new Annotation(text);

	        pipeline.annotate(document);	     
	        
	        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	       
	        for(CoreMap sentence: sentences) {
	         	          
	          SemanticGraph dependencies = sentence.get(BasicDependenciesAnnotation.class);
	          List<String> relnConcepts = ExtractCompundAmodRelations(dependencies.typedDependencies(), sentence); 
 	          
	          concepts.addAll(relnConcepts);    
	          
	        }
	       // System.out.println(concepthighlightpairs);
	       /* for (String hl: concepthighlightpairs.keySet())
	        {
	        	String original=hl.toString();
	        	if(text.contains(original))
	        		text=text.replaceAll(hl, "<b>"+original+"</b>");
	        }*/
	        StringBuilder conceptHLpairs=new StringBuilder();
	        for (String hl: concepthighlightpairs.keySet())
	        {
	        	String key =hl.toString();
	            String value = concepthighlightpairs.get(hl).toString();  
	            conceptHLpairs.append(key+":"+value+"/");
	        }
	        
	      //  System.out.println(text);
	      //  courseDetails.setText(text);
	      //  courseDetailsModel.setText(courseDetails.getText());
	        courseDetailsModel.setConcepthighlightpairs(conceptHLpairs.toString());
	      //  System.out.println(concepts);
	        Set<String> unique_concepts = new HashSet<String>(concepts);
	        List<String> uniqueConcepts=new ArrayList<String>(unique_concepts);
	        int UniqueTF[] =ExtractTermFrequency(concepts, uniqueConcepts);
	        
	       //Add all the relations into AllRelations list
	        for(int i=0;i<concepts.size()-1;i++)
	        {
	        	String s=concepts.get(i)+"/"+concepts.get(i+1);
	        	AllRelations.add(s);
	        }
	        
	        for(String s: AllRelations)
	        	System.out.println(s);
	        
	        // compute relations frequency
	        Set<String> unique_relations = new HashSet<>(AllRelations);
	        ArrayList<String> finalReln=new ArrayList<String>(unique_relations);
	        int h=finalReln.size();
	        int relation_freq[]= new int[h];
	        int count=0;
	        for(int i=0;i<finalReln.size();i++)
	        {	        	
	        	String u= finalReln.get(i);
	        	for(String temp : AllRelations)
		        {
	        		
	        			if(u.equals(temp))
	        			{
	        			   count++;
	        			}
	        				
		        }	        	
	        	relation_freq[i]=count;
	        	count=0;
	        }
	        for(int k=0;k<finalReln.size();k++)
	        {
	        	String s=finalReln.get(k)+"/"+relation_freq[k];
	        	finalReln.set(k, s);
	        //	System.out.println(finalReln.get(k));
	        }
		        
		        try {
		        	courseDetailsModel = runner.runnermain(finalReln,courseDetailsModel);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		  //  coursemodel =    main1(finalReln,course); 
		        
		        
	       // courseDetailsModel.setConcepts(AppendUtil.listToString(uniqueConcepts));
	        courseDetailsModel.setRelation(AppendUtil.listToString(finalReln));
	        courseDetailsModel.setTitle(courseDetails.getTitle());
	        courseDetailsModel.setTermFrequency(AppendUtil.arrayToString(UniqueTF));
	        courseDetailsModel.setText(courseDetails.getText());
	        courseDetailsModel.setExpertID(courseDetails.getModelID());
	        courseDetailsModel.setConcepthighlightpairs(conceptHLpairs.toString());
	       
	        
	        if(model.equals("student"))
	        {
	        //getting matrix values from database to compute similarity measures
	        CourseDetailsModel expertParameters = courseDetailsDaoImpl.ExpertModelValues(courseDetails.getModelID());
	        
	        //compute similarity values
	        courseDetailsModel=Computesimilaritymeasures(courseDetailsModel,expertParameters);
	        
	        courseDetailsModel=graphcomparision(courseDetailsModel,expertParameters);
	        courseDetailsModel.setExpert(expertParameters);
	       String stutext= courseDetailsModel.getText();
	       stutext=stutext.replaceAll("<b>", "");
	       stutext=stutext.replaceAll("</b>", "");
	       courseDetailsModel.setText(stutext);
	        /*int NoofcommonKeys=expertParameters.getKeyConcepts().size()-courseDetailsModel.getMissingConcepts().size(),totalKeys=expertParameters.getKeyConcepts().size();
			double Recall= (double)NoofcommonKeys/(double)totalKeys;
			System.out.println("Recall of keyconcepts:"+Recall);*/
			
	       }
	        if(type.equals("student")) {
	        		DisplayGraph dp = new DisplayGraph();
		        SmartTestBoImpl smartTestBoImpl = new SmartTestBoImpl();
		        courseDetailsModel=dp.createJsonStudentFeedback(courseDetailsModel,0);
		       // courseDetailsModel=smartTestBoImpl.calculateRecall(courseDetailsModel);
		        courseDetailsModel.setStudentId(courseDetails.getUserId());
	        }
	        
	        
	        return courseDetailsModel;
	        	       
	}
	
	
	 private List<String> ExtractCompundAmodRelations(Collection<TypedDependency> tdl, CoreMap sentence) { 
		 	List<String> primary = new ArrayList<String>(); 
	        List<Integer> indexList=new ArrayList<Integer>(); 
	        List<Integer> tempList=new ArrayList<Integer>(); 
	        List<String> concepts=new ArrayList<String>();
	       

	        	for (TypedDependency td : tdl) 
	        	{ 
	        		//System.out.println(td);
	        		String rel = td.reln().toString(); 
		            String gov = td.gov().lemma(); 
		            String dep = td.dep().lemma(); 
		            String deptext=td.dep().originalText();
		            String govtext=td.gov().originalText();
		            int deppos=td.dep().index();
		            int govpos=td.gov().index();
	        	
		            if(rel=="compound" || rel=="amod")
		            {
		            	String temporg=deptext+" "+govtext;
		            	if(sentence.toString().contains(temporg))
		            	{
		            		String temp=dep+" "+gov;
		            		primary.add(temp.toLowerCase());
			            	indexList.add(deppos);
			            	indexList.add(govpos);
			            	tempList.add(deppos);
			            	concepthighlightpairs.put(temporg,temp.toLowerCase());
			           	}
		            		            	
		            }
	        	}
	        	for (TypedDependency td : tdl) 
	        	{
        		
		            String gov = td.gov().lemma(); 
		            String dep = td.dep().lemma(); 
		            int deppos=td.dep().index();
		            int govpos=td.gov().index();
	            	String deptag=td.dep().tag();
	            	String govtag=td.gov().tag();
	            	String deptext=td.dep().originalText();
	            	 String govtext=td.gov().originalText();
	            	if(deptag!=null && deptag.contains("NN"))
	            	{
	            		boolean flag=false;
	            		
	            		for(int j=0;j<indexList.size();j++)
	            		{
	            			if(deppos==indexList.get(j))
	            			{
	            				flag=true;
	            				break;
	            			}	            				
	            		}
	            		if(flag==false)
	            		{
	            			primary.add(dep.toLowerCase());
	            			indexList.add(deppos);
	            			tempList.add(deppos);
	            			concepthighlightpairs.put(deptext,dep.toLowerCase());
	            		}
	            		
	            			
	            	}
	            	if(govtag!=null && govtag.contains("NN"))
	            	{
	            		boolean flag=false;
	            		
	            		for(int j=0;j<indexList.size();j++)
	            		{
		            		if(govpos==indexList.get(j))
		            		{
	            				flag=true;
	            				break;
	            			}
	            		}
	            		if(flag==false)
	            		{
	            			primary.add(gov.toLowerCase());
	            			indexList.add(govpos);
	            			tempList.add(govpos);
	            			concepthighlightpairs.put(govtext,gov.toLowerCase());
	            		}
	            	}	        	
	            }
	        	List<Integer> indexes=new ArrayList<Integer>();
	        	indexes.addAll(tempList);
	        	
	        	tempList.sort(null);
	        	for(int i=0;i<indexes.size();i++)
	        	{
	        		for(int j=0;j<indexes.size();j++)
	        		{
	        			if(tempList.get(i)==indexes.get(j))
	        			{
	        				concepts.add(primary.get(j));
	        			}
	        		}
	        	}
	        	
	        	//System.out.println(concepts);
	        	
	        	//System.out.println(tempList);
	        	
	        return concepts; 
	 }
	 
	 private int[] ExtractTermFrequency(List<String> rawConcepts, List<String> Concepts)
	 {
		// List<String> Concepts=new ArrayList<String>(uniqueConcepts);
		 int termFrequency[]=new int[Concepts.size()];
		 int count=0;
		 for (int i = 0; i < Concepts.size(); i++)
		 {
			 String s=Concepts.get(i);
			 for (String temp : rawConcepts) 
			 {
					if(s.equals(temp))
						count++;
			 }
			 
			 termFrequency[i]=count;
			 count=0;
		 }
		 return termFrequency;
	 }
	 
/*	 private List<String> ExtractFinalConcepts(List<String> tempConcept, Set<String> uniqueConcepts,int freq[])
	 {
		 List<String> finalConcepts = new ArrayList<String>(); 
		 List<String> primary = new ArrayList<String>(); 
		 List<String> Concepts=new ArrayList<String>(uniqueConcepts);
	        for(String s:tempConcept)
	        {
	        	String words[]=s.split(" ");
	        	primary.add(words[0]);
	        	primary.add(words[1]);
	        }
	        for (int i = 0; i < primary.size(); i++)
			 {
				 String s=primary.get(i);
				 for (int j = 0; j < Concepts.size(); j++)
				 { 
					 String temp=Concepts.get(j);
					 if(s.equals(temp))
						 freq[j]=freq[j]-1;
				 }
			 }
	        
	        for (int i = 0; i < Concepts.size(); i++)
			 {
	        	if(freq[i]>=1)
	        	{
	        		finalConcepts.add(Concepts.get(i));
	        	}
	        	
			 }
			 
	        return finalConcepts;
	 }
	 
	 private List<String> ExtractFinalRelations(List<String> Allreln, List<String> concepts)
	 {
		 List<String> relations= new ArrayList<String>();
		 
		 for(int i=0;i<concepts.size();i++)
		 {
			 String temp=concepts.get(i);
			 for(int j=0;j<Allreln.size();j++)
			 {
				 String reln[]=Allreln.get(j).split(",");
				 if(reln[0].equals(temp) || reln[1].equals(temp))
				 {
					 for(int k=i+1;k<concepts.size();k++)
					 {
						 String temp2=concepts.get(k);
						 if(reln[0].equals(temp2)||reln[1].equals(temp2))
							 relations.add(temp+"/"+temp2);
					 }
				 }
			 }
		 }
		 
		 return relations;
	 }
	*/
	 public CourseDetailsModel Computesimilaritymeasures(CourseDetailsModel student, CourseDetailsModel expert)
		{
		 try
		 {
			int noofconcepts1=student.getNoOfConcepts(), noofconcepts2=expert.getNoOfConcepts();
			int noofrelations=student.getNoOfRelations(),noofrelations2=expert.getNoOfRelations();
			int Subgraphs1=student.getSubgraphs(),Subgraphs2=expert.getSubgraphs();
			
			double NOC,NOR,Subgraph;
			//similaritymeasures sm=new similaritymeasures();
			NOC=computation(noofconcepts1,noofconcepts2);
			NOR=computation(noofrelations, noofrelations2);
			Subgraph=computation(Subgraphs1, Subgraphs2);
					
			double avgdegree1=student.getAvgdegree(),avgdegree2=expert.getAvgdegree();
			double density1=student.getDensity(),density2=expert.getDensity();
			double diameter1=student.getDiameter(),diameter2=expert.getDiameter();
			double meandistance1=student.getMeandistance(),meandistance2=expert.getMeandistance();
			double connectedness1=student.getSubgraph(),connectedness2=expert.getSubgraph();
			double avgd, density, diameter, MD, connected;
			avgd=computation(avgdegree1, avgdegree2);
			density=computation(density1, density2);
			diameter=computation(diameter1,diameter2);
			MD=computation(meandistance1,meandistance2);
			connected=computation(connectedness1,connectedness2);
			System.out.println("\n \n Similarity Measures");
			System.out.println(" No of concepts="+ NOC);
			System.out.println(" No of Subgraph="+ Subgraph);
			System.out.println(" Avg Degree="+ avgd);
			System.out.println(" No of Density="+ density);
			System.out.println(" No of diameter="+ diameter);
			System.out.println(" Mean Distance="+ MD);
			System.out.println(" No of connected="+ connected);
			System.out.println(" No of concepts="+ NOC +": \n No of Relations="+NOR+": \n Subgraph="+Subgraph+": \n Avg Degree="+avgd+": \n Density="+density+": \n diameter="+diameter+": \n Mean Distance="+MD+"\n Conectedness="+connected);
			
			
			student.setSm_NOofconcepts(NOC);
			student.setSm_NoOfRelations(NOR);
			student.setSm_avgdegree(avgd);
			student.setSm_Subgraph(Subgraph);
			student.setSm_connected(connected);
			student.setSm_meandistance(MD);
			student.setSm_density(density);
			student.setSm_diameter(diameter);
			
		//	String studentconcepts=student.getAllConceptList();
		//	String expertconcepts=expert.getAllConceptList();
			
			ArrayList<String> RefConcepts=expert.getAllConceptList();
			ArrayList<String> stuConcepts=student.getAllConceptList();
			Double CS= 0d;
			if(RefConcepts.size() >  0 && stuConcepts.size() > 0)
			{
				 CS=computeconceptualSimilarity(RefConcepts,stuConcepts);
				System.out.println("concept matching: "+CS);
			}
			if(CS.isInfinite() ||CS.isNaN())
			{
				CS=0.0;
			}
			String studentrelations=student.getRelation();
			String expertrelations=expert.getRelation();
			
			String[] tempstu=studentrelations.split(",");
			String[] tempref=expertrelations.split(",");
			
			//String[] relationStu= new String[tempstu.length];
			ArrayList<String> relationStu=new ArrayList<String>();
			for(int i=0;i<tempstu.length;i++)
			{
			  String[] s=tempstu[i].split("/");
			  String l=s[0]+"/"+s[1];
			  relationStu.add(i, l);
			}
			
			//String[] relationExp= new String[tempref.length];
			ArrayList<String> relationExp=new ArrayList<String>();
			for(int i=0;i<tempref.length;i++)
			{
			  String[] s=tempref[i].split("/");
			  String l=s[0]+"/"+s[1];
			  relationExp.add(l);
			}
			
			Double PS=computepropositionalSimilarity(relationExp,relationStu);
			System.out.println("Propositional matching: "+PS);
			if(PS.isInfinite()||PS.isNaN())
				PS=0.0;
			Double BS=PS/CS;
			System.out.println("Balanced matching:" + BS);
			if(BS.isInfinite()||BS.isNaN())
				BS=0.0;
			student.setSm_conceptualmatching(CS);
			student.setSm_propositionalmatching(PS);
			student.setSm_Balancedmatching(BS);
		 }
		 catch(Exception ex)
		 {
			 System.out.println(ex.getMessage());
		 }
			
			return student;
		}
	 public double computation(double a,double b)
		{ 
			double sm=0.0;
			if(a>b)
			{
				sm=1-((a-b)/a);
			}
			else
				sm=1-((b-a)/b);
			return sm;
		}
	 public double computeconceptualSimilarity(ArrayList<String> ref, ArrayList<String> stu)
		{
			double cs=0.0;
			double a=0.7, b=0.3;
			int c=0;
			HashSet<String> similar=new HashSet<String>();
			List<String> ref_stu=new ArrayList<String>();
			List<String> stu_ref=new ArrayList<String>();
			for(String refconcepts : ref)
			{
				for(String stuconcepts : stu)
				{
					if(refconcepts.equals(stuconcepts))
					{
						similar.add(stuconcepts);
						c++;
					}
				}
				if(c==0)
				{
					ref_stu.add(refconcepts);				
				}
				c=0;
			}
			c=0;
			for(String stuconcepts : stu)
			{
				for(String refconcepts : ref)
				{
					if(stuconcepts.equals(refconcepts))
					{
						similar.add(refconcepts);
						c++;
					}
				}
				if(c==0)
				{
					stu_ref.add(stuconcepts);				
				}
				c=0;
			}
			cs=similar.size()/(similar.size()+(a*stu_ref.size())+(b*ref_stu.size()));
			return cs;
		}
	 
	 public double computepropositionalSimilarity(ArrayList<String> ref, ArrayList<String> stu)
		{
			double cs=0.0;
			double a=0.7, b=0.3;
			int c=0;
			HashSet<String> similar=new HashSet<String>();
			List<String> ref_stu=new ArrayList<String>();
			List<String> stu_ref=new ArrayList<String>();
			for(String refconcepts : ref)
			{
				String[] re=refconcepts.split("/");
				String r=re[1]+"/"+re[0];
				for(String stuconcepts : stu)
				{
				
					if(refconcepts.equals(stuconcepts)||r.equals(stuconcepts))
					{
						similar.add(stuconcepts);
						c++;
					}
				}
				if(c==0)
				{
					ref_stu.add(refconcepts);				
				}
				c=0;
			}
			c=0;
			for(String stuconcepts : stu)
			{
				String[] st=stuconcepts.split("/");
				String s=st[1]+"/"+st[0];
				for(String refconcepts : ref)
				{
					if(stuconcepts.equals(refconcepts)||s.equals(refconcepts))
					{
						similar.add(refconcepts);
						c++;
					}
				}
				if(c==0)
				{
					stu_ref.add(stuconcepts);				
				}
				c=0;
			}
			cs=similar.size()/(similar.size()+(a*stu_ref.size())+(b*ref_stu.size()));
			return cs;
		}
	 public CourseDetailsModel graphcomparision(CourseDetailsModel student, CourseDetailsModel expert)
	 {
		 ArrayList<String> studentConcepts=student.getAllConceptList();
		 ArrayList<String> expertKeyConceptsArray=expert.getKeyConcepts();
		 ArrayList<String> MisingConcepts=new ArrayList<String>();
		
		 for(int i=0;i<expertKeyConceptsArray.size();i++)
		 {
			 String f=expertKeyConceptsArray.get(i);
			 int c=0;
			 for(String s:studentConcepts)
			 {
				 if(f.equals(s))
				 {
					 c++;
					 break;
				 }
			 }
			 if(c==0)
			 {
				 MisingConcepts.add(expertKeyConceptsArray.get(i));
			 }
		 }
		 student.setMissingConcepts(MisingConcepts);
		// System.out.println("MissingConcepts: "+ MisingConcepts);
		 
		 return student;
	 }
	 
	 public CourseDetailsModel calculateRecall(CourseDetailsModel courseDetailsModel)
	 {

		 CourseDetailsModel expertdetails=courseDetailsModel.getExpert();
	        
		 int NoofcommonKeys=expertdetails.getKeyConcepts().size()-courseDetailsModel.getMissingConcepts().size(),totalKeys=expertdetails.getKeyConcepts().size();
		 double Recall= (double)NoofcommonKeys/(double)totalKeys;
		 System.out.println("Recall of keyconcepts:"+Recall);
			
		int noofcommonlinks=courseDetailsModel.getCommonLinks().size();
		int avglinks=courseDetailsModel.getTotalExpertLinks().size();//Integer.parseInt((courseDetailsModel.getExpertBaseLinks()));
		double recalllinks=(double)noofcommonlinks/(double)avglinks;
		System.out.println("Recall of links:"+recalllinks);
		
		courseDetailsModel.setRecallkeyconcepts(Recall);
		courseDetailsModel.setRecallKeylinks(recalllinks);
		
		 return courseDetailsModel;
	 }
}
	

