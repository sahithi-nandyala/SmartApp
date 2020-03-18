package edu.smart.bo;

import java.io.File;


import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections15.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import edu.smart.dao.CourseDetailsDaoImpl;
import edu.smart.model.CourseDetailsModel;
import edu.smart.pojo.*;
import edu.smart.util.AppendUtil;


public class Runner {

	@Autowired
	CourseDetailsDaoImpl courseDetailsDaoImpl;
	@Autowired
	FloydWarshall floydwarshall;
	
	private static LinkedList<String> distinctNodes;
	private static LinkedList<String> sourceVertices;
	private static LinkedList<String> targetVertices;
	private static LinkedList<Double> weight;
	public static int final_size = 0;
	public static final int INFINITY =999;
	static double[][] live_matrix;
	static int[][] path;
    static double[][] adjacency_matrix;
    public TreeMap<Integer,String> keyConceptVertex;
	public TreeMap<Integer,String> basicConceptVertex;
	public ArrayList<String> allConceptList;
	
	public CourseDetailsModel updatePathList(CourseDetailsModel courseDetailsModel) throws Exception {
		/*System.out.println("Inside updatePathList}}}}}}}}}}}}}}}}}}}}}}}}}}}");*/
		int NoofConcepts,NoofRelations;
		ArrayList<String> Relations = new ArrayList<String>();
		String relArray[] = courseDetailsModel.getRelation().split(",");
		IMetricCalculation iMetricCalculation = new MetricCalculationImpl();
		ArrayList<String> pathList = new ArrayList<String>();
		courseDetailsModel.setPathList(pathList);;
		for(int i=0;i<relArray.length;i++) {
			Relations.add(relArray[i]);
		//	System.out.println("Relations: "+ relArray[i]);
		}
		
		NoofConcepts=graphData(Relations);
		UserGraph graph = iMetricCalculation.createGraph(distinctNodes,
				sourceVertices, targetVertices, weight);
		List<String> keyConcepts= courseDetailsModel.getKeyConcepts();
		/*System.out.println("Key Concepts************: "+keyConcepts);*/
		//List<String> keyConcepts=getKeyConcepts(result1);
		allConceptList=new ArrayList<String>();
		keyConceptVertex=new TreeMap();
		basicConceptVertex=new TreeMap();
		boolean flag=false;
		for(int g=0;g<distinctNodes.size();g++)
		{
				for(String s:keyConcepts)
				{
					if(s.equals(distinctNodes.get(g)))
					{
						keyConceptVertex.put(g, s);
						flag=true;
						break;
					}
					flag=false;
				}
				if(flag==false)
				{
					basicConceptVertex.put(g,distinctNodes.get(g));
				}
				allConceptList.add(distinctNodes.get(g));	
		}
		ArrayList<String> kConcepts = new ArrayList<String>();
		ArrayList<String> bConcepts = new ArrayList<String>();
		
		//System.out.println("\n\nKeyconcepts");
		 Set set1 = keyConceptVertex.entrySet();
	     int[] keyIndices=new int[set1.size()];
	     int n=0;
	      // Get an iterator
	      Iterator i = set1.iterator();	      
	      // Display elements
	      while(i.hasNext()) {
	         Map.Entry me = (Map.Entry)i.next();
	     //    System.out.print(me.getKey() + ": ");
	         keyIndices[n]=(int) me.getKey();
	         n++;
	       //  System.out.println(me.getValue());
	         kConcepts.add((String) me.getValue());
	      }
	   //   System.out.println("\nBasic concepts");
	      Set set2 = basicConceptVertex.entrySet();
	      
	      // Get an iterator
	      Iterator i2 = set2.iterator();	      
	      // Display elements
	      while(i2.hasNext()) {
	         Map.Entry me = (Map.Entry)i2.next();
	    //     System.out.print(me.getKey() + ": ");
	     //    System.out.println(me.getValue());
	         bConcepts.add(me.getValue().toString());
	      }
	     

	     
		Set<Set<MyNode>> subGraphSet = iMetricCalculation
				.findDisconnectedSubgraph(graph.getGraph());
	//	System.out.println("\nSubgraph: "+subGraphSet.size());

		
		double density = iMetricCalculation.calculateGraphDensity(graph.getGraph());
	//	System.out.println("Network Density: "+density);

		
		Double diameter = iMetricCalculation.calculateGraphDiameter(graph.getGraph());
    //    System.out.println("Graph Diameter : " + diameter);


        Transformer<MyNode, Double> meanDistance = iMetricCalculation.calculateGraphMeanDistance(graph.getGraph());   
        double WholemeanDistance = 0.0;
        int count=0;
        for(MyNode node : graph.getGraph().getVertices()){
      //  	System.out.println(node +" "+meanDistance.transform(node));
        	WholemeanDistance +=meanDistance.transform(node);
        	count++;
        
        }
        count=count*(count-1);
        WholemeanDistance=2*WholemeanDistance/count;
      //  System.out.println("Mean Distance : " + WholemeanDistance);
    
        Double relation = 0d; 
        for(Double freq : weight){
        	relation = relation + freq;
        }
		NoofRelations=relation.intValue();
      //  System.out.println("Number of Relations : "+relation);
     //   System.out.println("Number of Concepts: "+distinctNodes.size());
        Double avgDegree = iMetricCalculation.calculateAverageDegree(graph.getGraph()); 
     //   System.out.println("Average Degree : "+avgDegree);
     //   System.out.println("Key Concepts : "+keyConcepts);
     //   System.out.println("Key Indices : "+keyIndices);

        //for calculating shortest path and shortest distance matrix
       floydwarshall = new FloydWarshall(distinctNodes.size());       
       double Subgraph= floydwarshall.floydwarshall(adjacency_matrix,path,keyIndices, courseDetailsModel);

       
		return courseDetailsModel;
	}
	
	
	public CourseDetailsModel runnermain(List<String> Relations, CourseDetailsModel courseDetailsModel) throws Exception {
		
		//FileWriter writer;
	//	File f = new File("C://output.csv");
		//	writer = new FileWriter(f);
		StringBuilder betweenness = new StringBuilder();
		StringBuilder closenessCentrality = new StringBuilder();
		StringBuilder pageRank = new StringBuilder();
		StringBuilder eigenVectorCentrality = new StringBuilder();
		StringBuilder clusteringcoeff=new StringBuilder();
		int NoofConcepts;
		int NoofRelations;
		DecimalFormat df = new DecimalFormat("*.###");
		IMetricCalculation iMetricCalculation = new MetricCalculationImpl();
		NoofConcepts=graphData(Relations);
		UserGraph graph = iMetricCalculation.createGraph(distinctNodes,
				sourceVertices, targetVertices, weight);
		
		
		//System.out.println("Betweenness Centrality Values:-");
		Map<String, Double> result = iMetricCalculation
				.calculateBetweennessCentrality(graph.getGraph(),
						graph.getNodes());
		Set<Entry<String, Double>> setEntry = result.entrySet();		
		for (Entry<String, Double> entry : setEntry) {
		//	System.out.println(entry.getKey() + ": " + entry.getValue());
			AppendUtil.check(betweenness, entry);
		}
				
		
		//System.out.println("\n\n\nCloseness Centrality Values:-");
		Map<String, Double> result1 = iMetricCalculation
				.calculateClosenessCentrality(graph.getGraph(),
						graph.getNodes());
		Set<Entry<String, Double>> setEntry1 = result1.entrySet();
		for (Entry<String, Double> entry : setEntry1) {
		//	System.out.println(entry.getKey() + ": " + entry.getValue());
			AppendUtil.check(closenessCentrality, entry);
			}
		
		//System.out.println("\n\n\nPage Rank Values:-");
		Map<String, Double> result2 = iMetricCalculation.calculatePageRank(
				graph.getGraph(), graph.getNodes());
		Set<Entry<String, Double>> setEntry2 = result2.entrySet();
		for (Entry<String, Double> entry : setEntry2) {
		//	System.out.println(entry.getKey() + ": " + entry.getValue());
			AppendUtil.check(pageRank, entry);
		}
	
		//System.out.println("\n\n\nEigen Vector Centrality:-");
		Map<String, Double> result3 = iMetricCalculation
				.calculateEigenVectorCentrality(graph.getGraph(),
						graph.getNodes());
		Set<Entry<String, Double>> setEntry3 = result3.entrySet();
		for (Entry<String, Double> entry : setEntry3) {
		//	System.out.println(entry.getKey() + ": " + entry.getValue());
			AppendUtil.check(eigenVectorCentrality, entry);
			}
		
	//	System.out.println("\n\n\nClustring Coefficients:-");
		Map<String, Double> result4=iMetricCalculation.clusteringCoefficient(graph.getGraph(),graph.getNodes());
		Set<Entry<String, Double>> setEntry4 = result4.entrySet();
		for (Entry<String, Double> entry : setEntry4) {
		//	System.out.println(entry.getKey() + ": " + entry.getValue());
			AppendUtil.check(clusteringcoeff, entry);
			}
		
		String centrality=courseDetailsModel.getCentrality();
		if(centrality==null)
		{
			centrality="CC";
		}
		if(courseDetailsModel.getThreshold()==0)
		{
			courseDetailsModel.setThreshold(75);
		}
		List<String> keyConcepts;
		//extract key concepts
			switch(centrality) {
			case "BC": keyConcepts=getKeyConcepts(result,courseDetailsModel.getThreshold());
						break;
			case "PR": keyConcepts=getKeyConcepts(result2,courseDetailsModel.getThreshold());
						break;
			case "CL": keyConcepts=getKeyConcepts(result1,courseDetailsModel.getThreshold());
						break;
			case "EV": keyConcepts=getKeyConcepts(result3,courseDetailsModel.getThreshold());
						break;
			case "CC": keyConcepts=getKeyConcepts(result4,courseDetailsModel.getThreshold());
						break;
			default :keyConcepts=getKeyConcepts(result4,courseDetailsModel.getThreshold());
			}
		
		System.out.println("Key Concepts************: "+keyConcepts);
		//List<String> keyConcepts=getKeyConcepts(result1);
		allConceptList=new ArrayList<String>();
		keyConceptVertex=new TreeMap();
		basicConceptVertex=new TreeMap();
		boolean flag=false;
		for(int g=0;g<distinctNodes.size();g++)
		{
				for(String s:keyConcepts)
				{
					if(s.equals(distinctNodes.get(g)))
					{
						keyConceptVertex.put(g, s);
						flag=true;
						break;
					}
					flag=false;
				}
				if(flag==false)
				{
					basicConceptVertex.put(g,distinctNodes.get(g));
				}
				allConceptList.add(distinctNodes.get(g));	
		}
		ArrayList<String> kConcepts = new ArrayList<String>();
		ArrayList<String> bConcepts = new ArrayList<String>();
		
		//System.out.println("\n\nKeyconcepts");
		 Set set1 = keyConceptVertex.entrySet();
	     int[] keyIndices=new int[set1.size()];
	     int n=0;
	      // Get an iterator
	      Iterator i = set1.iterator();	      
	      // Display elements
	      while(i.hasNext()) {
	         Map.Entry me = (Map.Entry)i.next();
	     //    System.out.print(me.getKey() + ": ");
	         keyIndices[n]=(int) me.getKey();
	         n++;
	       //  System.out.println(me.getValue());
	         kConcepts.add((String) me.getValue());
	      }
	   //   System.out.println("\nBasic concepts");
	      Set set2 = basicConceptVertex.entrySet();
	      
	      // Get an iterator
	      Iterator i2 = set2.iterator();	      
	      // Display elements
	      while(i2.hasNext()) {
	         Map.Entry me = (Map.Entry)i2.next();
	    //     System.out.print(me.getKey() + ": ");
	     //    System.out.println(me.getValue());
	         bConcepts.add(me.getValue().toString());
	      }
	     

	     
		Set<Set<MyNode>> subGraphSet = iMetricCalculation
				.findDisconnectedSubgraph(graph.getGraph());
	//	System.out.println("\nSubgraph: "+subGraphSet.size());

		/*for (Set<MyNode> set : subGraphSet) {
		//	System.out.println(set);
		
		}*/

		
		double density = iMetricCalculation.calculateGraphDensity(graph.getGraph());
		//System.out.println("Network Density: "+density);

		
		Double diameter = iMetricCalculation.calculateGraphDiameter(graph.getGraph());
      //  System.out.println("Graph Diameter : " + diameter);


       /* Transformer<MyNode, Double> meanDistance = iMetricCalculation.calculateGraphMeanDistance(graph.getGraph());   
        double WholemeanDistance = 0.0;
        double count=0;
        for(MyNode node : graph.getGraph().getVertices()){
      //  	System.out.println(node +" "+meanDistance.transform(node));
        	WholemeanDistance +=meanDistance.transform(node);
        	count++;
        
        }
        count=(count*(count-1));
        WholemeanDistance= WholemeanDistance/count;*/
      //  System.out.println("Mean Distance : " + WholemeanDistance);
    
        Double relation = 0d; 
        for(Double freq : weight){
        	relation = relation + freq;
        }
		NoofRelations=relation.intValue();
    //    System.out.println("Number of Relations : "+relation);
      //  System.out.println("Number of Concepts: "+distinctNodes.size());
        Double avgDegree = iMetricCalculation.calculateAverageDegree(graph.getGraph()); 
       // System.out.println("Average Degree : "+avgDegree);
       // System.out.println("Key Concepts : "+keyConcepts);
       // System.out.println("Key Indices : "+keyIndices);

        //for calculating shortest path and shortest distance matrix
       floydwarshall = new FloydWarshall(distinctNodes.size());       
       double WholemeanDistance= floydwarshall.floydwarshall(adjacency_matrix,path,keyIndices, courseDetailsModel);

        

        courseDetailsModel.setBetweenness(betweenness.toString());
        courseDetailsModel.setClosenessCentrality(closenessCentrality.toString());
        courseDetailsModel.setEigenVectorCentrality(eigenVectorCentrality.toString());
        courseDetailsModel.setPageRank(pageRank.toString());
        courseDetailsModel.setClusteringcoef(clusteringcoeff.toString());
        
        courseDetailsModel.setDensity(density);
        courseDetailsModel.setAvgdegree(avgDegree);
        courseDetailsModel.setDiameter(diameter);
        courseDetailsModel.setMeandistance(WholemeanDistance);
        courseDetailsModel.setNoOfConcepts(NoofConcepts);
        courseDetailsModel.setNoOfRelations(NoofRelations);
        courseDetailsModel.setSubgraph(subGraphSet.size());

	    courseDetailsModel.setbConcepts(bConcepts);
	    courseDetailsModel.setKeyConcepts(kConcepts);

        courseDetailsModel.setAdjacencyMatrix(this.adjacency_matrix);        
        courseDetailsModel.setAllConceptList(allConceptList);
        System.out.println(allConceptList);
       	String dbAdjMatrix="";//Integer.toString((int)adjacency_matrix[0][0])+",";
       	
	  /*  String tempconcepts=courseDetailsModel.getConcepthighlightpairs();
	    String text=courseDetailsModel.getText();
	    String[] pairs=tempconcepts.split("/");
	    
	   for(int k=0;k<pairs.length;k++)
	   {
		   String[] pair=pairs[k].split(":");
		   for(String keyc:kConcepts)
		   {
			   if(keyc.equals(pair[1]))
			   {
				   text=text.replaceAll(pair[0], "<span>"+pair[0]+"</span>");
			   }
		   }
	   }
       	System.out.println(text);
       	courseDetailsModel.setText(text);
       	*/
       	for (int k=0; k < adjacency_matrix.length; k++) {
			for (int j = 0; j < adjacency_matrix.length; j++) {
				if(k==j || k>j) {
					continue;
				}
				if(j==adjacency_matrix.length-1) {
					dbAdjMatrix=dbAdjMatrix + (int)adjacency_matrix[k][j];
				}
				else {
					dbAdjMatrix=dbAdjMatrix + (int)adjacency_matrix[k][j]+",";
				}
			}
			if(k!=adjacency_matrix.length-1) {
				dbAdjMatrix=dbAdjMatrix + "/";
			}
		}
       	
        
   //    	System.out.println("dbAdjMatrix: "+dbAdjMatrix);
        courseDetailsModel.setDBAdjacencyMatrix(dbAdjMatrix.toString());
        return courseDetailsModel;

	}

	private int graphData(List<String> Relations) {
			
		return getValuesFromDatabase(Relations);

	}
	
	public int getValuesFromDatabase(List<String> Relations){
		try{
			sourceVertices = new LinkedList<>();
			targetVertices = new LinkedList<>();
			weight = new LinkedList<>();
			distinctNodes = new LinkedList<>();

		//	System.out.println();
			for(int k=0;k<Relations.size();k++)
	        {
				String e = (Relations.get(k)).toString();
	        	String r[]=e.split("/");
	        	String a=r[0];
	        	String b=r[1];
	        	if(!distinctNodes.contains(a))
					distinctNodes.add(a);
				if(!distinctNodes.contains(b))
					distinctNodes.add(b);
				sourceVertices.add(r[0]);
				targetVertices.add(r[1]);
				weight.add(Double.parseDouble(r[2]));
	        }
			
			//System.out.println("distinctNodes: "+ distinctNodes);
			
			final_size = distinctNodes.size();
			 
			int i = 0, j = 0;
			live_matrix = new double[final_size][final_size];
			path=new int[final_size][final_size];
			
			for (i = 0; i < final_size; i++) {
			for (j = 0; j < final_size; j++) {
			// exclude = check(codec.get(i-1), i);
			if (i == j) {
			live_matrix[i][j] = 0;
			}
			else {
				
				String s=distinctNodes.get(i)+"/"+distinctNodes.get(j);
				//String r=distinctNodes.get(j)+"/"+distinctNodes.get(i);
				for(String k : Relations)
				{
					String f[]=k.split("/");
					if(k.contains(s))
					{
						live_matrix[i][j] += Double.parseDouble(f[2]);
						live_matrix[j][i] += Double.parseDouble(f[2]);
					}					
					
				}
					
				
			}
			}
			}
			
			 int numberofvertices=final_size;
			 adjacency_matrix = new double[numberofvertices][numberofvertices];
			     //  System.out.println("Enter the Weighted Matrix for the graph");
			       for (int source = 0; source <numberofvertices; source++)
			       {
			           for (int destination = 0; destination <numberofvertices; destination++)
			           {
			               adjacency_matrix[source][destination] = live_matrix[source][destination];
			               if (source == destination)
			               {
			                   adjacency_matrix[source][destination] = 0;
			                   path[source][destination]=source;
			                   continue;
			               }
			               if (adjacency_matrix[source][destination] == 0)
			               {
			                   adjacency_matrix[source][destination] = INFINITY;
			                   path[source][destination]=-1;
			               }
			               else
			               {
			            	   path[source][destination]=source;
			               }
			           }
			       }
			     /*  System.out.println("\n\nAdjacency matrix\n");
			       for (i = 0; i < final_size; i++) {
						for (j = 0; j < final_size; j++) {
						//live_matrix[j][i] = live_matrix[i][j];
						System.out.print(adjacency_matrix[i][j] + "\t");
						}
						System.out.println();
					} */   

		}catch(Exception e){
			
		}
		return distinctNodes.size();
	}

	public double Percentile(List<Double> latencies, double Percentile)
	{
		Collections.sort(latencies);
        //latencies.sort(c);;
        int Index = (int)Math.ceil(((double)Percentile / (double)100) * (double)latencies.size());
        return latencies.get(Index-1);
	}
	
	public List<String> getKeyConcepts(Map<String, Double> setEntry, int threshold)
	{
		
        List<String> Keyconcepts=new ArrayList<String>();
		Set<Entry<String, Double>> mappings = setEntry.entrySet();	
		List<Double> values=new ArrayList<Double>();
        double temp=0.0;
        //getting the highest value from the list
        for(Entry<String, Double> mapping1 : mappings)
        {              
        	values.add(mapping1.getValue());
        	//if(mapping1.getValue() > temp)
        		//temp=mapping1.getValue();
        	         
        }
        double percent=Percentile(values,threshold);
        
	//	int threshold=20;
		//double calculateThreshold=(temp*threshold)/100;
        
        System.out.println("percent values is" + percent);
		for(Entry<String, Double> mapping1 : mappings)
        {
			if(mapping1.getValue() > percent)// added by me
			{
				System.out.println("keyconcpet values is"+ mapping1.getValue());
				Keyconcepts.add(mapping1.getKey());
			}
        }
		return Keyconcepts;
	}
	
	
}
