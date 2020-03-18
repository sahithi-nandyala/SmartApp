package edu.smart.util;


import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.apache.log4j.Logger;

import edu.smart.controller.CourseDetailsController;
import edu.smart.model.CourseDetailsModel;



public class DisplayGraph {
	
	static Logger log = Logger.getLogger(CourseDetailsController.class.getName());
	
	public CourseDetailsModel createJsonStudentFeedback(CourseDetailsModel studentModel, int value) {	
		
		ArrayList<String> bConcepts= new ArrayList<String>();
		ArrayList<String> keyConcepts= new ArrayList<String>();
		ArrayList<String> validBConceptsList= new ArrayList<String>();
		ArrayList<Integer> validBConceptsIndices= new ArrayList<Integer>();
		ArrayList<String> allConcepts= new ArrayList<String> ();
		ArrayList<String> pathList= new ArrayList<String>();
		CourseDetailsModel expert= studentModel.getExpert();
		ArrayList<ArrayList<String>> adjacencyList= new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> adjacencyList2= new ArrayList<ArrayList<String>>();
		//expert model
		String[] adjacencyArray1 = studentModel.getExpert().getDBAdjacencyMatrix().split("/"); //rules
		int i,j, size=0, found=0;
		
		//System.out.println("Adjacency matrix from db: "+ studentModel.getExpert().getDBAdjacencyMatrix());
		
		//*******loaded adjancy matrix in adjanancylist
		for(i=0;i<adjacencyArray1.length;i++) {
			ArrayList<String> adjacencyList1= new ArrayList<String>();
			String[] adjacencyArray2=adjacencyArray1[i].split(","); //columns values
			if(i==0) {
				size=adjacencyArray2.length;
			}
			for(j=0;j<adjacencyArray2.length;j++) {
				adjacencyList1.add(adjacencyArray2[j]);
				//adjacencyMatrix[i][j]= Double.parseDouble(adjacencyArray2[j]);
			}
			adjacencyList.add(adjacencyList1);
			//System.out.println();
		}
		
		size=size+1;
		int l=0;
		double[][] adjacencyMatrix = new double[size][size];
		for(i=0; i<size; i++) {
			for(j=0; j<size; j++) {
				if(i==j || i>j) {
					adjacencyMatrix[i][j]=0.0; ///to set to lowerhalf zero
				}
				else {
					adjacencyMatrix[i][j]=Double.parseDouble(adjacencyList.get(i).get(l));
					l++;
				}
				//System.out.print(adjacencyMatrix[i][j]+ "  ");
			}
			//System.out.println();
			l=0;
		}
		
		///*********  keyconcepts and conceptlist -----add concepts
		keyConcepts.addAll(expert.getKeyConcepts());
		allConcepts.addAll(expert.getAllConceptList());
		pathList.addAll(expert.getPathList()); //shortest distance between the nodes , all concepts
		expert.setAdjacencyMatrix(adjacencyMatrix);
		System.out.println("Expert adjancey matrix"+ adjacencyMatrix);
		
		for(int m=0;m<allConcepts.size();m++) {
			for(int n=0;n<keyConcepts.size();n++) {
				if(keyConcepts.get(n).equals(allConcepts.get(m))) {
					found=1; 
					break;
				}
			}
			if(found==0) {
				bConcepts.add(allConcepts.get(m)); //concept which ---basic concepts used for displaying graph rest of 
				//the nodes if matches then its key concept else its basic concept
				System.out.println("Basic Concept is : "+ allConcepts.get(m));
			}
			found=0;
		}
		
		///=====wat is bconcepts
		expert.setbConcepts(bConcepts);
		
		//************  
		JsonObjectBuilder nodesJsonBuilder = Json.createObjectBuilder();
		JsonArrayBuilder nodesArrBld = Json.createArrayBuilder();
		JsonArrayBuilder linksArrBld = Json.createArrayBuilder();
		JsonObjectBuilder linksJsonBuilder = Json.createObjectBuilder();
		
		///*****common concept between student nd expert
		ArrayList<String> commonKeyConcepts= new ArrayList<String>();
		for(i=0;i<studentModel.getAllConceptList().size();i++) {
			if(inList(studentModel.getAllConceptList().get(i), expert.getKeyConcepts())==1) {
				commonKeyConcepts.add(studentModel.getAllConceptList().get(i));
			}
		}
		System.out.println("Common Key concepts: "+commonKeyConcepts);
		//if(studentModel.getCommonKeyConcepts() != null)
		//{
			//studentModel.setCommonKeyConcepts(commonKeyConcepts);
	/*		System.out.println("Common Key concepts: "+commonKeyConcepts);
			studentModel.setCommonKeyConcepts(studentModel.getKeyConcepts());
			System.out.println("Common Key concepts: "+studentModel.getCommonKeyConcepts());*/
			//if(value != 1) {
			//	studentModel.setCommonKeyConcepts(studentModel.getKeyConcepts());  //reuse purpose
			//}
		//}
		
		studentModel.setCommonKeyConcepts(commonKeyConcepts);
		if(value != 1) {
			studentModel.setCommonKeyConcepts(studentModel.getKeyConcepts());  
		}
		     
			/*if(studentModel.getCommonKeyConcepts().isEmpty())	
			{
				studentModel.setCommonKeyConcepts(studentModel.getKeyConcepts()); 
			}*/
			
		//************common key concepts type categorization
		System.out.println("Common Key concepts: "+studentModel.getCommonKeyConcepts());
		String concepts="";
		i=0;
		while(i<expert.getKeyConcepts().size()) {
			nodesJsonBuilder.add("name", expert.getKeyConcepts().get(i));
			if(inList(expert.getKeyConcepts().get(i),studentModel.getCommonKeyConcepts())==1) {
				System.out.println("matching type-G Key concepts: "+expert.getKeyConcepts().get(i));
				nodesJsonBuilder.add("type", "G"); //color nd comparios
			}
			else {
				nodesJsonBuilder.add("type", "R"); //on feedback page
			}
			
			ArrayList<String> validBConcepts= new ArrayList<String>();
			for(int m=0;m<size;m++) {
				for(int n=0;n<size;n++) {
					if(m==n || m>n) {
						continue;
					}
					//immediately connected basic concepts for key concept on ohover
					//if there is no link value becomes 9999, check for valid links
					//order of adjacy same as allconcepts mth -keyconcept nd nth- basic concept
					if((expert.getAdjacencyMatrix()[m][n]!=999 && expert.getAdjacencyMatrix()[m][n]!=0)||
							(expert.getAdjacencyMatrix()[n][m]!=999 && expert.getAdjacencyMatrix()[n][m]!=0)) {
						if(expert.getAllConceptList().get(m).equals(keyConcepts.get(i))&&
								(inList(expert.getAllConceptList().get(n),expert.getbConcepts())==1)) {
							validBConcepts.add(expert.getAllConceptList().get(n));
						}
						else if(expert.getAllConceptList().get(n).equals(keyConcepts.get(i))&&
								(inList(expert.getAllConceptList().get(m),expert.getbConcepts())==1)) {
							validBConcepts.add(expert.getAllConceptList().get(m));
						}
					}
				}
			}
			
			concepts="<b>";
			for(int k=0; k<validBConcepts.size();k++) {
				String output = validBConcepts.get(k).substring(0, 1).toUpperCase() + validBConcepts.get(k).substring(1);
				if(k==validBConcepts.size()-1) {
					concepts=concepts+output+"</b>";
				}
				else {
					concepts=concepts+output+"<br /><br />";
				}
			}
			if(concepts.equals("<b>")) {
				concepts=concepts+"No Concepts</b>";
			}
			validBConceptsList.addAll(validBConcepts);
			System.out.println("****Concepts****: "+concepts);
			nodesJsonBuilder.add("concepts", concepts);
			nodesArrBld.add(nodesJsonBuilder);
			i++;
		}
		
		/*i=0;
		while(i<expert.getbConcepts().size()) {
			nodesJsonBuilder.add("name", expert.getbConcepts().get(i));
			nodesJsonBuilder.add("type", "N");
			nodesArrBld.add(nodesJsonBuilder);
			i++;
		}*/
		
		
		//creating data for jason
		JsonArray nodesArr = nodesArrBld.build();
		int nodesCount=expert.getAllConceptList().size();
		int pathSize=0;
		
		/*for(i=0;i<nodesCount;i++) {
			for(j=0;j<nodesCount;j++) {
				if(i==j || i>j) {
					continue;
				}
				if(expert.getAdjacencyMatrix()[i][j]!=9 && expert.getAdjacencyMatrix()[i][j]!=0) {
					if(inList(expert.getAllConceptList().get(i), expert.getKeyConcepts())==1) {
						
					}
					for(int k=0;k<expert.getPathList().size();k++) {
						String[] path=new String[expert.getPathList().get(k).split("-").length];
						path=expert.getPathList().get(k).split("-");
						pathSize=path.length;
						if(inList(expert.getAllConceptList().get(Integer.parseInt(path[0])),expert.getKeyConcepts())==1) {
							if(inList(expert.getAllConceptList().get(Integer.parseInt(path[pathSize-1])),expert.getKeyConcepts())==1) {
								
							}
						}
					}
					linksJsonBuilder.add("source", expert.getAllConceptList().get(i));
					linksJsonBuilder.add("target", expert.getAllConceptList().get(j));
					if(found==1) {
						linksJsonBuilder.add("type", "A");
					}
					else {
						linksJsonBuilder.add("type", "B");
					}
					found=0;
					linksArrBld.add(linksJsonBuilder);
				}
			}
		}*/
		
		///*************link creation ???
		int foundKeyConcepts=0;
		ArrayList<String> reducedPathList= new ArrayList<String>();
		String source="", dest="";
		String link="", reverseLink="";
		for(int k=0;k<expert.getPathList().size();k++) {
			String[] path=new String[expert.getPathList().get(k).split("\\^").length]; //expert model path list
			path=expert.getPathList().get(k).split("\\^");
			pathSize=path.length;
			foundKeyConcepts=0;
			//1-2-3 in path refrences idex of allconcepts.
			//source
			if(inList(expert.getAllConceptList().get(Integer.parseInt(path[0])),expert.getKeyConcepts())==1) {
				//dest
				if(inList(expert.getAllConceptList().get(Integer.parseInt(path[pathSize-1])),expert.getKeyConcepts())==1) {
					for(int m=1; m<(path.length-1);m++) {
						if(inList(expert.getAllConceptList().get(Integer.parseInt(path[m])), expert.getKeyConcepts())==1) {
							
							System.out.println("matching expert path links"+ (expert.getAllConceptList().get(Integer.parseInt(path[m]))).toString());
							foundKeyConcepts=1; ///are basic concept is not same keyconcepts continue because we need link src to destination.
							break;
						}
					}
					
					if(foundKeyConcepts==1) {
						continue;
					}
					source=expert.getAllConceptList().get(Integer.parseInt(path[0]));
					dest=expert.getAllConceptList().get(Integer.parseInt(path[pathSize-1]));
					link=source+"^"+dest;
					reverseLink=dest+"^"+source;
					if(reducedPathList.isEmpty()) {
						System.out.println("****reduced link****: "+link);
						
						reducedPathList.add(link);
					}
					else if((inList(link,reducedPathList)==1)||(inList(reverseLink,reducedPathList)==1)) {
							continue;
					}
					else {
						reducedPathList.add(link);
						System.out.println("****reduced link****: "+link);
					}
					
				}
			}
		}
		
		found=0;
		ArrayList<String> commonLinks= new ArrayList<String>();
		ArrayList<String> missingLinks= new ArrayList<String>();
		ArrayList<String> missingLinksForDisplay= new ArrayList<String>();
		ArrayList<String> totalExpertLinks= new ArrayList<String>();
		//reduced path list
		//student model pathlist-studen feedback
		for(i=0;i<reducedPathList.size();i++) {
			String path[]=reducedPathList.get(i).split("\\^");
			System.out.println("path[]: "+path[0]+"   "+path[1]);
			linksJsonBuilder.add("source", path[0]);
			linksJsonBuilder.add("target", path[1]);
			if((inList(path[0],studentModel.getCommonKeyConcepts())==1) && (inList(path[1],studentModel.getCommonKeyConcepts())==1)) {
				for(int m=0;m<studentModel.getAdjacencyMatrix().length;m++) {
					System.out.println("adjancency length"+ studentModel.getAdjacencyMatrix().length );
					for(int n=0; n<studentModel.getAdjacencyMatrix().length;n ++) {
						if(inList(studentModel.getAllConceptList().get(m), studentModel.getCommonKeyConcepts())==1) {
							System.out.println("value of M" + studentModel.getAllConceptList().get(m));
							if(inList(studentModel.getAllConceptList().get(n), studentModel.getCommonKeyConcepts())==1) {
								System.out.println("value of N" + studentModel.getAllConceptList().get(n));
								//uncommented by me it was there in original code---ashwini
								//if(studentModel.getAdjacencyMatrix()[m][n]!=0.0 && studentModel.getAdjacencyMatrix()[m][n]!=999.0) {
									System.out.println("index of M" + m);
									System.out.println("index of N"+ n);
									System.out.println("adjancency matrix :" + studentModel.getAdjacencyMatrix()[m][n]);
									System.out.println("path[0] :" + path[0]);
								    System.out.println("path[1] :" + path[1]);
									System.out.println("matching links based on reduced path list"+ (studentModel.getAllConceptList().get(n)).toString());
									found=1; ///same links as expert  marked as green
								//} else{System.out.println("not found at "+ studentModel.getAdjacencyMatrix()[m][n]);}
								
							}else{System.out.println("not found at get all concept list of n");}
						
						}else{System.out.println("not found at get all concept list of m");}
					}
				}
				
				if(found==1) {
					commonLinks.add(path[0]+"^"+path[1]);
					totalExpertLinks.add(path[0]+"^"+path[1]);
					linksJsonBuilder.add("type", "G");
				}
				else {
					missingLinks.add(path[0]+"^"+path[1]);
					missingLinksForDisplay.add(path[0]+" - "+path[1]);
					totalExpertLinks.add(path[0]+"^"+path[1]);
					linksJsonBuilder.add("type", "R");
				}
			}
			else {
				missingLinks.add(path[0]+"^"+path[1]);
				missingLinksForDisplay.add(path[0]+" - "+path[1]);
				totalExpertLinks.add(path[0]+"^"+path[1]);
				linksJsonBuilder.add("type", "R");
			}
			linksArrBld.add(linksJsonBuilder);
			found=0;
		}
		int foundCommon=0;
		studentModel.setCommonLinks(commonLinks);
		
		//// Link setting to fields???????
		
		System.out.println("commonLinks: "+commonLinks);
		
		///wrong link  information
		ArrayList<String> wrongLinks= new ArrayList<String>();
		for(i=0; i<studentModel.getCommonKeyConcepts().size();i++) {
			for(j=0; j<studentModel.getCommonKeyConcepts().size();j++) {
				//if i give value index of string --from allconceptlist arraylist
				///getting index for studet model
				int index1= getIndex(studentModel.getCommonKeyConcepts().get(i), studentModel.getAllConceptList());
				int index2= getIndex(studentModel.getCommonKeyConcepts().get(j), studentModel.getAllConceptList());
				//fetching index are corresponding  expert model
				int index3= getIndex(studentModel.getCommonKeyConcepts().get(i), expert.getAllConceptList());
				int index4= getIndex(studentModel.getCommonKeyConcepts().get(j), expert.getAllConceptList());
				
				for(int m=0;m<studentModel.getCommonLinks().size();m++) {
					//to identify source of link is in coomon keyconcept i==source and j== destination
					if((studentModel.getCommonKeyConcepts().get(i).equals(studentModel.getCommonLinks().get(m).split("\\^")[0]))&&
							(studentModel.getCommonKeyConcepts().get(j).equals(studentModel.getCommonLinks().get(m).split("\\^")[1]))) {
						foundCommon=1;
						break;
					}
					else if((studentModel.getCommonKeyConcepts().get(j).equals(studentModel.getCommonLinks().get(m).split("\\^")[0]))&&
							(studentModel.getCommonKeyConcepts().get(i).equals(studentModel.getCommonLinks().get(m).split("\\^")[1]))) {
						foundCommon=1;
						break;
					}
					//
				}
				if(foundCommon==1) {
					foundCommon=0;
					continue;
				}
				if((expert.getAdjacencyMatrix()[index3][index4]==0.0) || (expert.getAdjacencyMatrix()[index3][index4]==999.0)) {
					if(((studentModel.getAdjacencyMatrix()[index1][index2]!=0.0) && (studentModel.getAdjacencyMatrix()[index1][index2]!=999.0))) {
						linksJsonBuilder.add("source", studentModel.getCommonKeyConcepts().get(i));
						linksJsonBuilder.add("target", studentModel.getCommonKeyConcepts().get(j));
						linksJsonBuilder.add("type", "Y"); ///wrong links
						System.out.println("wrong link source: "+studentModel.getCommonKeyConcepts().get(i));
						System.out.println("wrong link target: "+studentModel.getCommonKeyConcepts().get(j));
						wrongLinks.add(studentModel.getCommonKeyConcepts().get(i)+"^"+studentModel.getCommonKeyConcepts().get(j));
					}
				}
			}
		}
		studentModel.setTotalExpertLinks(totalExpertLinks);
		studentModel.setWrongLinks(wrongLinks);
		System.out.println("wrongLinks: "+wrongLinks);
		System.out.println("totalLinks: "+totalExpertLinks);
		
		studentModel.setMissingLinks(missingLinks);
		studentModel.setMissingLinksForDisplay(missingLinksForDisplay);
	
		JsonArray linksArr = linksArrBld.build();
        System.out.println(nodesArr);
        System.out.println(linksArr);
        
        
        studentModel.setStudentFeedbackLinks(linksArr.toString());
        studentModel.setStudentFeedbackNodes(nodesArr.toString());
        if(value==1) {
        	/*ArrayList<String> commonKeyConcepts1= new ArrayList<String>();
    		for(i=0;i<studentModel.getAllConceptList().size();i++) {
    			if(inList(studentModel.getAllConceptList().get(i), expert.getKeyConcepts())==1) {
    				commonKeyConcepts1.add(studentModel.getAllConceptList().get(i));
    			}
    		}
    	
    		studentModel.setCommonKeyConcepts(commonKeyConcepts1);
        		studentModel.setKeyConcepts(studentModel.getCommonKeyConcepts());*/
        	studentModel.setKeyConcepts(studentModel.getCommonKeyConcepts());
        }
        //studentModel.setKeyConcepts(studentModel.getCommonKeyConcepts());
        return studentModel;
	}
	
	public CourseDetailsModel createJsonExpertBase(CourseDetailsModel studentModel) {	
		
		int i=0,j=0;
		int found=0;
		int size=0;
		ArrayList<String> bConcepts= new ArrayList<String>();
		ArrayList<String> keyConcepts= new ArrayList<String>();
		ArrayList<String> allConcepts= new ArrayList<String> ();
		ArrayList<String> pathList= new ArrayList<String>();
		ArrayList<String> commonLinks= new ArrayList<String>();
		CourseDetailsModel expert= studentModel.getExpert();
		ArrayList<ArrayList<String>> adjacencyList= new ArrayList<ArrayList<String>>();
		String[] adjacencyArray1 = studentModel.getExpert().getDBAdjacencyMatrix().split("/");
		ArrayList<Integer> validBConceptsIndices= new ArrayList<Integer>();
		ArrayList<String> validBConceptsList= new ArrayList<String>();
		
		
		//System.out.println("Adjacency matrix from db: "+ studentModel.getExpert().getDBAdjacencyMatrix());
		for(i=0;i<adjacencyArray1.length;i++) {
			ArrayList<String> adjacencyList1= new ArrayList<String>();
			String[] adjacencyArray2=adjacencyArray1[i].split(",");
			if(i==0) {
				size=adjacencyArray2.length;
			}
			for(j=0;j<adjacencyArray2.length;j++) {
				adjacencyList1.add(adjacencyArray2[j]);
				//adjacencyMatrix[i][j]= Double.parseDouble(adjacencyArray2[j]);
			}
			adjacencyList.add(adjacencyList1);
			//System.out.println();
		}
		
		size=size+1;
		int l=0;
		double[][] adjacencyMatrix = new double[size][size];
		for(i=0; i<size; i++) {
			for(j=0; j<size; j++) {
				if(i==j || i>j) {
					adjacencyMatrix[i][j]=0.0;
				}
				else {
					adjacencyMatrix[i][j]=Double.parseDouble(adjacencyList.get(i).get(l));
					l++;
				}
				//System.out.print(adjacencyMatrix[i][j]+ "  ");
			}
			//System.out.println();
			l=0;
		}
		
		keyConcepts.addAll(expert.getKeyConcepts());
		allConcepts.addAll(expert.getAllConceptList());
		pathList.addAll(expert.getPathList());
		expert.setAdjacencyMatrix(adjacencyMatrix);
		
		for(int m=0;m<allConcepts.size();m++) {
			for(int n=0;n<keyConcepts.size();n++) {
				if(keyConcepts.get(n).equals(allConcepts.get(m))) {
					found=1;
					break;
				}
			}
			if(found==0) {
				bConcepts.add(allConcepts.get(m));
				//System.out.println("bConcepts : "+allConcepts.get(m));
			}
			found=0;
		}
		
		expert.setbConcepts(bConcepts);
		/*courseDetailsModel.getbConcepts().size() + courseDetailsModel.getKeyConcepts().size();
		ArrayList<String> conceptList=new ArrayList<String>();
		
		conceptList.addAll(courseDetailsModel.getKeyConcepts());
		conceptList.addAll(courseDetailsModel.getbConcepts());*/
		
		JsonObjectBuilder nodesJsonBuilder = Json.createObjectBuilder();
		JsonArrayBuilder nodesArrBld = Json.createArrayBuilder();
		JsonArrayBuilder linksArrBld = Json.createArrayBuilder();
		JsonObjectBuilder linksJsonBuilder = Json.createObjectBuilder();
		String concepts="";
		
		i=0;
		found=0;
		while(i<keyConcepts.size()) {
			nodesJsonBuilder.add("name", keyConcepts.get(i));
			for(int n=0;n<studentModel.getMissingConcepts().size();n++) {
				if(keyConcepts.get(i).equals(studentModel.getMissingConcepts().get(n))) {
					found=1;
					break;
				}
			}
			if(found==1) {
				nodesJsonBuilder.add("type", "R");
			}
			else {
				nodesJsonBuilder.add("type", "G");
			}
			found=0;
			ArrayList<String> validBConcepts= new ArrayList<String>();
			for(int m=0;m<size;m++) {
				for(int n=0;n<size;n++) {
					if(m==n || m>n) {
						continue;
					}
					if((expert.getAdjacencyMatrix()[m][n]!=999.0 && expert.getAdjacencyMatrix()[m][n]!=0.0)||
							(expert.getAdjacencyMatrix()[n][m]!=999.0 && expert.getAdjacencyMatrix()[n][m]!=0.0)) {
						if(expert.getAllConceptList().get(m).equals(keyConcepts.get(i))&&
								(inList(expert.getAllConceptList().get(n),expert.getKeyConcepts())==0)){
								if(validBConceptsIndices.size()==0) {
									validBConceptsIndices.add(n);
									validBConcepts.add(expert.getAllConceptList().get(n));
								}
								else {
									if(inListInt(n,validBConceptsIndices)==0) {
										validBConceptsIndices.add(n);
										validBConcepts.add(expert.getAllConceptList().get(n));
									}
								}
						}
						else if(expert.getAllConceptList().get(n).equals(keyConcepts.get(i))&&
								(inList(expert.getAllConceptList().get(m),expert.getKeyConcepts())==0)) {
							if(validBConceptsIndices.size()==0) {
								validBConceptsIndices.add(m);
								validBConcepts.add(expert.getAllConceptList().get(m));
							}
							else {
								if(inListInt(m,validBConceptsIndices)==0) {
									validBConceptsIndices.add(m);
									validBConcepts.add(expert.getAllConceptList().get(m));
								}
							}
						}
					}
				}
			}
			concepts="";
			for(int k=0; k<validBConcepts.size();k++) {
				if(k==validBConcepts.size()-1) {
					concepts=concepts+validBConcepts.get(k);
				}
				else {
					concepts=concepts+validBConcepts.get(k)+", ";
				}
			}
			if(concepts=="") {
				concepts="No Concepts";
			}
			validBConceptsList.addAll(validBConcepts);
			//System.out.println("****Concepts****: "+concepts);
			nodesJsonBuilder.add("concepts", concepts);
			nodesArrBld.add(nodesJsonBuilder);
			i++;
		}
		
		i=0;
		/*while(i<bConcepts.size()) {
			nodesJsonBuilder.add("name", bConcepts.get(i));
			nodesJsonBuilder.add("type", "N");
			nodesArrBld.add(nodesJsonBuilder);
			i++;
		}*/
		
		int foundKey1=0, foundKey2=0, foundB1=0;
		
		JsonArray nodesArr = nodesArrBld.build();
		found=0;
		foundKey1=0; 
		foundKey2=0;
		
		for(i=0;i<size;i++) {
			for(j=0;j<size;j++) {
				if(i==j || i>j) {
					continue;
				}
				if(expert.getAdjacencyMatrix()[i][j]!=999.0 && expert.getAdjacencyMatrix()[i][j]!=0.0) {
					foundKey1=0; foundKey2=0;
					for(int k=0; k<keyConcepts.size(); k++) {
						if(expert.getAllConceptList().get(i).equals(keyConcepts.get(k))) {
							foundKey1=1;
							//System.out.println(keyConcepts.get(k));
						}
						if(expert.getAllConceptList().get(j).equals(keyConcepts.get(k))) {
							foundKey2=1;
							//System.out.println(keyConcepts.get(k));
						}
						
					}
					if(foundKey1==0 || foundKey2==0) {
						linksJsonBuilder.add("source", expert.getAllConceptList().get(i));
						linksJsonBuilder.add("target", expert.getAllConceptList().get(j));
						linksJsonBuilder.add("type", "B");
						
					}
					else {
						linksJsonBuilder.add("source", expert.getAllConceptList().get(i));
						linksJsonBuilder.add("target", expert.getAllConceptList().get(j));
						/*for(int k=0;k<expert.getPathList().size();k++) {
							String[] path=new String[expert.getPathList().get(k).split("-").length];
							path=expert.getPathList().get(k).split("-");
								for(int p=0;p<path.length-1;p++) {
									if(((i==Integer.parseInt(path[p])) && (j==Integer.parseInt(path[p+1]))) || 
											((i==Integer.parseInt(path[p+1])) && (j==Integer.parseInt(path[p])))){
											found=1;
											break;
									}
								}
						}*/
						
						
						int index1=-1, index2=-1;
						for(int m=0; m<studentModel.getAllConceptList().size();m++) {
							
							if(studentModel.getAllConceptList().get(m).equals(expert.getAllConceptList().get(i))) {
								index1=m;
							}
							if(studentModel.getAllConceptList().get(m).equals(expert.getAllConceptList().get(j))) {
								index2=m;
							}
						}
						System.out.println("index: "+index1+"  "+index2);
						if(index1>0 && index2>0) {
							if(studentModel.getAdjacencyMatrix()[index1][index2]!=999.0 && studentModel.getAdjacencyMatrix()[index1][index2]!=0.0) {
								found=1;
							}
						}
						
						if(found==1) {
							linksJsonBuilder.add("type", "G");
							commonLinks.add(expert.getAllConceptList().get(i)+"^"+expert.getAllConceptList().get(j));
						}
						else {
							linksJsonBuilder.add("type", "R");
						}
					}
					found=0;
					linksArrBld.add(linksJsonBuilder);
				}
			}
		}
		
		JsonArray linksArr = linksArrBld.build();
        //System.out.println(nodesArr);
        //System.out.println(linksArr);
        
       
        studentModel.setExpertBaseLinks(linksArr.toString());
		studentModel.setExpertBaseNodes(nodesArr.toString());
        studentModel.setCommonLinks(commonLinks);
        
        //System.out.println("Common Links: "+studentModel.getCommonLinks());
        return studentModel;
	}
	
	
	
	public CourseDetailsModel createJsonStudentBase(CourseDetailsModel studentModel) {	
		
		int i,j;
		int found=0;
		int size=0;
		ArrayList<String> bConcepts= new ArrayList<String>();
		ArrayList<String> keyConcepts= new ArrayList<String>();
		ArrayList<String> allConcepts= new ArrayList<String> ();
		ArrayList<String> pathList= new ArrayList<String>();
		CourseDetailsModel expert= studentModel.getExpert();
		ArrayList<ArrayList<String>> adjacencyList= new ArrayList<ArrayList<String>>();
		String[] adjacencyArray1 = studentModel.getExpert().getDBAdjacencyMatrix().split("/");
		
		//System.out.println("Adjacency matrix from db: "+ studentModel.getExpert().getDBAdjacencyMatrix());
		for(i=0;i<adjacencyArray1.length;i++) {
			ArrayList<String> adjacencyList1= new ArrayList<String>();
			String[] adjacencyArray2=adjacencyArray1[i].split(",");
			if(i==0) {
				size=adjacencyArray2.length;
			}
			for(j=0;j<adjacencyArray2.length;j++) {
				adjacencyList1.add(adjacencyArray2[j]);
				//adjacencyMatrix[i][j]= Double.parseDouble(adjacencyArray2[j]);
			}
			adjacencyList.add(adjacencyList1);
			//System.out.println();
		}
		
		size=size+1;
		int l=0;
		double[][] adjacencyMatrix = new double[size][size];
		for(i=0; i<size; i++) {
			for(j=0; j<size; j++) {
				if(i==j || i>j) {
					adjacencyMatrix[i][j]=0.0;
				}
				else {
					adjacencyMatrix[i][j]=Double.parseDouble(adjacencyList.get(i).get(l));
					l++;
				}
			}
			l=0;
		}
		
		keyConcepts.addAll(expert.getKeyConcepts());
		allConcepts.addAll(expert.getAllConceptList());
		pathList.addAll(expert.getPathList());
		expert.setAdjacencyMatrix(adjacencyMatrix);
		
		for(int m=0;m<allConcepts.size();m++) {
			for(int n=0;n<keyConcepts.size();n++) {
				if(keyConcepts.get(n).equals(allConcepts.get(m))) {
					found=1;
					break;
				}
			}
			if(found==0) {
				bConcepts.add(allConcepts.get(m));
				//System.out.println("bConcepts : "+allConcepts.get(m));
			}
			found=0;
		}
		
		expert.setbConcepts(bConcepts);
		
		JsonObjectBuilder nodesJsonBuilder = Json.createObjectBuilder();
		JsonArrayBuilder nodesArrBld = Json.createArrayBuilder();
		JsonArrayBuilder linksArrBld = Json.createArrayBuilder();
		JsonObjectBuilder linksJsonBuilder = Json.createObjectBuilder();
		
		i=0;
		found=0;
		while(i<studentModel.getKeyConcepts().size()) {
			nodesJsonBuilder.add("name", studentModel.getKeyConcepts().get(i));
			for(int n=0;n<expert.getKeyConcepts().size();n++) {
				if(studentModel.getKeyConcepts().get(i).equals(expert.getKeyConcepts().get(n))) {
					found=1;
					break;
				}
			}
			if(found==1) {
				nodesJsonBuilder.add("type", "G");
			}
			else {
				nodesJsonBuilder.add("type", "R");
			}
			found=0;
			nodesArrBld.add(nodesJsonBuilder);
			i++;
		}
		
		i=0;
		while(i<studentModel.getbConcepts().size()) {
			nodesJsonBuilder.add("name", studentModel.getbConcepts().get(i));
			nodesJsonBuilder.add("type", "N");
			nodesArrBld.add(nodesJsonBuilder);
			i++;
		}
		
		int foundKey1=0, foundKey2=0;
		
		JsonArray nodesArr = nodesArrBld.build();
		found=0;
		foundKey1=0; 
		foundKey2=0;
		
		int keyIndex1=0, keyIndex2=0;
		int foundStudent=0;
		
		for(i=0;i<studentModel.getAdjacencyMatrix().length;i++) {
			for(j=0;j<studentModel.getAdjacencyMatrix().length;j++) {
				if(i==j || i>j) {
					continue;
				}
				if(studentModel.getAdjacencyMatrix()[i][j]!=999.0 && studentModel.getAdjacencyMatrix()[i][j]!=0.0) {
					foundKey1=0; foundKey2=0;
					for(int k=0; k<studentModel.getKeyConcepts().size(); k++) {
						if(studentModel.getAllConceptList().get(i).equals(studentModel.getKeyConcepts().get(k))) {
							foundKey1=1;
							keyIndex1=i;
							//System.out.println(keyConcepts.get(k));
						}
						if(studentModel.getAllConceptList().get(j).equals(studentModel.getKeyConcepts().get(k))) {
							foundKey2=1;
							keyIndex2=j;
							//System.out.println(keyConcepts.get(k));
						}
						
					}
					if(foundKey1==0 || foundKey2==0) {
						linksJsonBuilder.add("source", studentModel.getAllConceptList().get(i));
						linksJsonBuilder.add("target", studentModel.getAllConceptList().get(j));
						linksJsonBuilder.add("type", "B");
					}
					else {
						linksJsonBuilder.add("source", studentModel.getAllConceptList().get(i));
						linksJsonBuilder.add("target", studentModel.getAllConceptList().get(j));
						foundStudent=0;
						if((studentModel.getAdjacencyMatrix()[keyIndex1][keyIndex2]!=999.0 && studentModel.getAdjacencyMatrix()[keyIndex1][keyIndex2]!=0.0)||
								(studentModel.getAdjacencyMatrix()[keyIndex2][keyIndex1]!=999.0 && studentModel.getAdjacencyMatrix()[keyIndex2][keyIndex1]!=0.0)) {
	
							foundStudent=1;
							int index1=-1, index2=-1;
							
							for(int m=0; m<expert.getAllConceptList().size();m++) {
								if(expert.getAllConceptList().get(m).equals(studentModel.getAllConceptList().get(i))) {
									index1=m;
								}
								if(expert.getAllConceptList().get(m).equals(studentModel.getAllConceptList().get(j))) {
									index2=m;
								}
							}
							
							if(index1>=0 && index2>=0) {
								if(expert.getAdjacencyMatrix()[index1][index2]!=999.0 && expert.getAdjacencyMatrix()[index1][index2]!=0.0) {
									found=1;
								}
							}
							
						}
						
						if(foundStudent==1 && found==1) {
							linksJsonBuilder.add("type", "G");
						}
						
						else if(foundStudent==1 && found==0) {
							linksJsonBuilder.add("type", "R");
						}
						
					}
					found=0;
					foundStudent=0;
					linksArrBld.add(linksJsonBuilder);
				}
			}
		}
		
		JsonArray linksArr = linksArrBld.build();
        //System.out.println(nodesArr);
        //System.out.println(linksArr);

        studentModel.setStudentBaseLinks(linksArr.toString());
        studentModel.setStudentBaseNodes(nodesArr.toString());
        
        return studentModel;
	}
	
	
	
	public CourseDetailsModel createJsonExpert(CourseDetailsModel studentModel) {	
		
		int i,j;
		CourseDetailsModel courseDetailsModel;
		int totalLinks=0;
		if(null!=studentModel.getExpert()) {
			courseDetailsModel= studentModel.getExpert();
			//System.out.println("Expert Present!");
		}
		else {
			courseDetailsModel=studentModel;
		}
		
		int nodesCount = courseDetailsModel.getbConcepts().size() + courseDetailsModel.getKeyConcepts().size();
		ArrayList<String> conceptList=new ArrayList<String>();
		
		conceptList.addAll(courseDetailsModel.getKeyConcepts());
		conceptList.addAll(courseDetailsModel.getbConcepts());
		
		JsonObjectBuilder nodesJsonBuilder = Json.createObjectBuilder();
		JsonArrayBuilder nodesArrBld = Json.createArrayBuilder();
		JsonArrayBuilder linksArrBld = Json.createArrayBuilder();
		JsonObjectBuilder linksJsonBuilder = Json.createObjectBuilder();
		
		i=0;
		while(i<courseDetailsModel.getKeyConcepts().size()) {
			nodesJsonBuilder.add("name", courseDetailsModel.getKeyConcepts().get(i));
			nodesJsonBuilder.add("type", "Y");
			nodesArrBld.add(nodesJsonBuilder);
			i++;
		}
		
		i=0;
		while(i<courseDetailsModel.getbConcepts().size()) {
			nodesJsonBuilder.add("name", courseDetailsModel.getbConcepts().get(i));
			nodesJsonBuilder.add("type", "N");
			nodesArrBld.add(nodesJsonBuilder);
			i++;
		}
		
		JsonArray nodesArr = nodesArrBld.build();
		int found=0;
		for(i=0;i<nodesCount;i++) {
			for(j=0;j<nodesCount;j++) {
				if(i==j || i>j) {
					continue;
				}
				if(courseDetailsModel.getAdjacencyMatrix()[i][j]!=999 && courseDetailsModel.getAdjacencyMatrix()[i][j]!=0) {
					linksJsonBuilder.add("source", courseDetailsModel.getAllConceptList().get(i));
					linksJsonBuilder.add("target", courseDetailsModel.getAllConceptList().get(j));
					for(int k=0;k<courseDetailsModel.getPathList().size();k++) {
						String[] path=new String[courseDetailsModel.getPathList().get(k).split("\\^").length];
						path=courseDetailsModel.getPathList().get(k).split("\\^");
							for(int p=0;p<path.length-1;p++) {
								if(((i==Integer.parseInt(path[p])) && (j==Integer.parseInt(path[p+1]))) || 
										((i==Integer.parseInt(path[p+1])) && (j==Integer.parseInt(path[p])))){
										found=1;
										break;
								}
							}
					}
					totalLinks++;
					if(found==1) {
						linksJsonBuilder.add("type", "A");
					}
					else {
						linksJsonBuilder.add("type", "B");
					}
					found=0;
					linksArrBld.add(linksJsonBuilder);
				}
			}
		}
		
		JsonArray linksArr = linksArrBld.build();
        //System.out.println(nodesArr);
        //System.out.println(linksArr);
        
        studentModel.setExpertBaseLinks(Integer.toString(totalLinks));
		
        studentModel.setLinks(linksArr.toString());
        studentModel.setNodes(nodesArr.toString());
        
        System.out.println("studentModel.getNodes "+studentModel.getNodes());
        System.out.println("studentModel.getNodes "+studentModel.getLinks());
        return studentModel;
	}
	
	public int inList(String a, ArrayList<String> list) {
		int found=0;
		for(int i=0;i<list.size();i++) {
			if(a.equals(list.get(i))) {
				found=1;
				break;
			}
		}
		
		if(found==0) {
			return 0;
		}else {
			return 1;
		}
	}
	
	public int inListInt(int a, ArrayList<Integer> list) {
		int found=0;
		for(int i=0;i<list.size();i++) {
			if(a==list.get(i)) {
				found=1;
				break;
			}
		}
		
		if(found==1) {
			return 1;
		}else {
			return 0;
		}
	}
	
	public int getIndex(String a, ArrayList<String> list) {
		int found=0;
		int i=0;
		for(i=0;i<list.size();i++) {
			if(a.equals(list.get(i))) {
				found=1;
				break;
			}
		}
		
		if(found==1) {
			return i;
		}else {
			return 0;
		}
	}
	

	public CourseDetailsModel createJsonOneDistance(CourseDetailsModel courseDetailsModel) {	
		
		int i,j;
		int nodesCount = courseDetailsModel.getAllConceptList().size();
		ArrayList<String> conceptList=new ArrayList<String>();
		
		conceptList.addAll(courseDetailsModel.getKeyConcepts());
		conceptList.addAll(courseDetailsModel.getbConcepts());
		
		JsonObjectBuilder nodesJsonBuilder = Json.createObjectBuilder();
		JsonArrayBuilder nodesArrBld = Json.createArrayBuilder();
		JsonArrayBuilder linksArrBld = Json.createArrayBuilder();
		JsonObjectBuilder linksJsonBuilder = Json.createObjectBuilder();
		
		ArrayList<Integer> validBConceptsIndices= new ArrayList<Integer>();
		ArrayList<String> validBConcepts= new ArrayList<String>();
		int foundKey=0;
		int keyIndex=0;
		
		i=0;
		while(i<courseDetailsModel.getKeyConcepts().size()) {
			nodesJsonBuilder.add("name", courseDetailsModel.getKeyConcepts().get(i));
			System.out.println("type --y --: "+ courseDetailsModel.getKeyConcepts().get(i));
			nodesJsonBuilder.add("type", "Y");
			nodesArrBld.add(nodesJsonBuilder);
			i++;
		}
		
		for(i=0;i<nodesCount;i++) {
			for(j=0;j<nodesCount;j++) {
				if(i==j || i>j) {
					continue;
				}
				if(courseDetailsModel.getAdjacencyMatrix()[i][j]!=999 && courseDetailsModel.getAdjacencyMatrix()[i][j]!=0) {
					if((inList(courseDetailsModel.getAllConceptList().get(i),courseDetailsModel.getKeyConcepts())==1)&&
							(inList(courseDetailsModel.getAllConceptList().get(j),courseDetailsModel.getKeyConcepts())==0)){
							if(validBConceptsIndices.size()==0) {
								validBConceptsIndices.add(j);
								validBConcepts.add(courseDetailsModel.getAllConceptList().get(j));
							}
							else {
								if(inListInt(j,validBConceptsIndices)==0) {
									validBConceptsIndices.add(j);
									validBConcepts.add(courseDetailsModel.getAllConceptList().get(j));
								}
							}
					}
					else if((inList(courseDetailsModel.getAllConceptList().get(j),courseDetailsModel.getKeyConcepts())==1)&&
							(inList(courseDetailsModel.getAllConceptList().get(i),courseDetailsModel.getKeyConcepts())==0)) {
						if(validBConceptsIndices.size()==0) {
							validBConceptsIndices.add(i);
							validBConcepts.add(courseDetailsModel.getAllConceptList().get(i));
						}
						else {
							if(inListInt(i,validBConceptsIndices)==0) {
								validBConceptsIndices.add(i);
								validBConcepts.add(courseDetailsModel.getAllConceptList().get(i));
							}
						}
					}
				}
			}
		}
		
		System.out.println("validBConcepts: "+validBConcepts);
		System.out.println("courseDetailsModel.getbConcepts(): "+courseDetailsModel.getbConcepts());
		
		i=0;
		while(i<courseDetailsModel.getbConcepts().size()) {
			nodesJsonBuilder.add("name", courseDetailsModel.getbConcepts().get(i));
			if(inList(courseDetailsModel.getbConcepts().get(i),validBConcepts)==1) {
				nodesJsonBuilder.add("type", "K");
			}
			else {
				nodesJsonBuilder.add("type", "N");
			}
			nodesArrBld.add(nodesJsonBuilder);
			i++;
		}
		
		JsonArray nodesArr = nodesArrBld.build();
		int found=0;
		
		for(i=0;i<nodesCount;i++) {
			for(j=0;j<nodesCount;j++) {
				if(i==j || i>j) {
					continue;
				}
				if(courseDetailsModel.getAdjacencyMatrix()[i][j]!=999 && courseDetailsModel.getAdjacencyMatrix()[i][j]!=0) {
					linksJsonBuilder.add("source", courseDetailsModel.getAllConceptList().get(i));
					linksJsonBuilder.add("target", courseDetailsModel.getAllConceptList().get(j));
					
					if((inList(courseDetailsModel.getAllConceptList().get(i), courseDetailsModel.getKeyConcepts())==1)&&
							(inList(courseDetailsModel.getAllConceptList().get(j), courseDetailsModel.getKeyConcepts())==1)) {
						linksJsonBuilder.add("type", "A");
					}
					else if(inList(courseDetailsModel.getAllConceptList().get(i), courseDetailsModel.getKeyConcepts())==1) {
						if(inListInt(j,validBConceptsIndices)==1) {
							linksJsonBuilder.add("type", "A");
						}
					}
					else if(inListInt(i,validBConceptsIndices)==1) {
						if(inList(courseDetailsModel.getAllConceptList().get(j), courseDetailsModel.getKeyConcepts())==1) {
							linksJsonBuilder.add("type", "A");
						}
					}
					else {
						linksJsonBuilder.add("type", "B");
					}
					found=0;
					linksArrBld.add(linksJsonBuilder);
				}
			}
		}
		
		JsonArray linksArr = linksArrBld.build();
        //System.out.println(nodesArr);
        //System.out.println(linksArr);
        //System.out.println();
        int experIndicator=0;
        
		if(null!=courseDetailsModel.getExpert()) {
			experIndicator=1;
		}
		if(experIndicator==1) {
			CourseDetailsModel expert=courseDetailsModel.getExpert();
			expert=createJsonOneDistance(expert);
			courseDetailsModel.setSingleExpertLinks(expert.getSingleStudentLinks());
			courseDetailsModel.setSingleExpertNodes(expert.getSingleStudentNodes());
			//System.out.println(courseDetailsModel.getSingleExpertLinks());
		}
		courseDetailsModel.setSingleStudentLinks(linksArr.toString());
		courseDetailsModel.setSingleStudentNodes(nodesArr.toString());
	    //System.out.println(courseDetailsModel.getSingleStudentLinks());
        
        return courseDetailsModel;
	}

	
	
	
	public CourseDetailsModel createJsonStudent(CourseDetailsModel courseDetailsModel) {
	
		
		int i,j;
		
		int nodesCount = courseDetailsModel.getAllConceptList().size();
		/*if(courseDetailsModel.getbConcepts().isEmpty()) {
			nodesCount = courseDetailsModel.getAllConceptList().size();
		}*/
		ArrayList<String> conceptList=new ArrayList<String>();
		
		conceptList.addAll(courseDetailsModel.getKeyConcepts());
		conceptList.addAll(courseDetailsModel.getbConcepts());
		
		JsonObjectBuilder nodesJsonBuilder = Json.createObjectBuilder();
		JsonArrayBuilder nodesArrBld = Json.createArrayBuilder();
		JsonArrayBuilder linksArrBld = Json.createArrayBuilder();
		JsonObjectBuilder linksJsonBuilder = Json.createObjectBuilder();
		
		i=0;
		/*CourseDetailsModel expert= courseDetailsModel.getExpert();
		ArrayList<String> commonKeyConcepts= new ArrayList<String>();
		for(i=0;i<courseDetailsModel.getAllConceptList().size();i++) {
			if(inList(courseDetailsModel.getAllConceptList().get(i), expert.getKeyConcepts())==1) {
				commonKeyConcepts.add(courseDetailsModel.getAllConceptList().get(i));
			}
		}
		i=0;
		courseDetailsModel.setCommonKeyConcepts(commonKeyConcepts);*/
		while(i<courseDetailsModel.getCommonKeyConcepts().size()) {
			nodesJsonBuilder.add("name", courseDetailsModel.getCommonKeyConcepts().get(i));
			System.out.println("courseDetailsModel commonconcepts "+courseDetailsModel.getCommonKeyConcepts().get(i));
			nodesJsonBuilder.add("type", "Y");
			nodesArrBld.add(nodesJsonBuilder);
			i++;
		}
		System.out.println("display student courseDetailsModel.getAllConceptList() "+courseDetailsModel.getAllConceptList());
		i=0;
		while(i<courseDetailsModel.getAllConceptList().size()) {
			if(inList(courseDetailsModel.getAllConceptList().get(i),courseDetailsModel.getCommonKeyConcepts())!=1) {
				System.out.println("courseDetailsModel getallconceptlist -- type N "+courseDetailsModel.getAllConceptList().get(i));
				nodesJsonBuilder.add("name", courseDetailsModel.getAllConceptList().get(i));
				nodesJsonBuilder.add("type", "N");
				nodesArrBld.add(nodesJsonBuilder);
			}
			i++;
		}
		
		JsonArray nodesArr = nodesArrBld.build();
		int found=0;
		int foundCommon=0, foundWrong=0;
		
		for(int m=0;m<courseDetailsModel.getCommonLinks().size();m++) {
			System.out.println("courseDetailsModel commonlinks source and destination--type A "+courseDetailsModel.getCommonLinks().get(m));
			linksJsonBuilder.add("source", courseDetailsModel.getCommonLinks().get(m).split("\\^")[0]);
			linksJsonBuilder.add("target", courseDetailsModel.getCommonLinks().get(m).split("\\^")[1]);
			
			linksJsonBuilder.add("type", "A");
			linksArrBld.add(linksJsonBuilder);
		}
		/*System.out.println("adjacency matrix: ");
		for(i=0;i<nodesCount;i++) {
			for(j=0;j<nodesCount;j++) {
				System.out.print(courseDetailsModel.getAdjacencyMatrix()[i][j]+"         ");
			}
			System.out.println();
		}*/
		
		for(i=0;i<nodesCount;i++) {
			for(j=0;j<nodesCount;j++) {
				//if(i==j || i>j) {
				//	continue;
				//}
				for(int m=0; m<courseDetailsModel.getCommonLinks().size();m++) {
					if(courseDetailsModel.getAllConceptList().get(i).equals(courseDetailsModel.getCommonLinks().get(m).split("\\^")[0])) {
						if(courseDetailsModel.getAllConceptList().get(j).equals(courseDetailsModel.getCommonLinks().get(m).split("\\^")[1])) {
							foundCommon=1;
							break;
						}
					}
				}
				if(foundCommon==1) {
					foundCommon=0;
					continue;
				}
				for(int m=0; m<courseDetailsModel.getWrongLinks().size();m++) {
					if(courseDetailsModel.getAllConceptList().get(i).equals(courseDetailsModel.getWrongLinks().get(m).split("\\^")[0])) {
						if(courseDetailsModel.getAllConceptList().get(j).equals(courseDetailsModel.getWrongLinks().get(m).split("\\^")[1])) {
							foundWrong=1;
							break;
						}
					}
				}
				if(foundWrong==1) {
					foundWrong=0;
					/*linksJsonBuilder.add("source", courseDetailsModel.getAllConceptList().get(i));
					linksJsonBuilder.add("target", courseDetailsModel.getAllConceptList().get(j));
					linksJsonBuilder.add("type", "R");
					linksArrBld.add(linksJsonBuilder);*/
				}
				else {
					if(courseDetailsModel.getAdjacencyMatrix()[i][j]!=999 && courseDetailsModel.getAdjacencyMatrix()[i][j]!=0) {
						linksJsonBuilder.add("source", courseDetailsModel.getAllConceptList().get(i));
						linksJsonBuilder.add("target", courseDetailsModel.getAllConceptList().get(j));
						System.out.println(" wrong links type B "+courseDetailsModel.getAllConceptList().get(i) + ",,," +  courseDetailsModel.getAllConceptList().get(j));
						linksJsonBuilder.add("type", "B");
						linksArrBld.add(linksJsonBuilder);
					}
				}
				/*if(courseDetailsModel.getAdjacencyMatrix()[i][j]!=9 && courseDetailsModel.getAdjacencyMatrix()[i][j]!=0) {
					linksJsonBuilder.add("source", courseDetailsModel.getAllConceptList().get(i));
					linksJsonBuilder.add("target", courseDetailsModel.getAllConceptList().get(j));
					for(int m=0; m<courseDetailsModel.getCommonKeyConcepts().size();m++) {
						if((inList(courseDetailsModel.getAllConceptList().get(i),courseDetailsModel.getCommonKeyConcepts())==1)&&
								(inList(courseDetailsModel.getAllConceptList().get(j),courseDetailsModel.getCommonKeyConcepts())==1)){
								foundCommon=1;
						}
					}
					if(foundCommon==1) {
						for(int k=0;k<courseDetailsModel.getPathList().size();k++) {
							String[] path=new String[courseDetailsModel.getPathList().get(k).split("-").length];
							path=courseDetailsModel.getPathList().get(k).split("-");
								//for(int p=0;p<path.length-1;p++) {
							if(((i==Integer.parseInt(path[0])) && (j==Integer.parseInt(path[path.length-1]))) || 
									((i==Integer.parseInt(path[path.length-1])) && (j==Integer.parseInt(path[0])))){
									found=1;
									break;
							}
								//}
						}
					
						if(found==1) {
							linksJsonBuilder.add("type", "A");
						}
					}
					else {
						linksJsonBuilder.add("type", "B");
					}
					found=0;
					linksArrBld.add(linksJsonBuilder);
				}*/
			}
		}
		
		JsonArray linksArr = linksArrBld.build();
        System.out.println("nodesArr displaystudent "+nodesArr);
        System.out.println("linksArr displaystudent "+linksArr);
        
        
		courseDetailsModel.setStudentLinks(linksArr.toString());
		courseDetailsModel.setStudentNodes(nodesArr.toString());
		//courseDetailsModel.setKeyConcepts(courseDetailsModel.getCommonKeyConcepts());
		
		return courseDetailsModel;
	}
	
public CourseDetailsModel createJsonExpertReduced(CourseDetailsModel studentModel) {	
	
	ArrayList<String> bConcepts= new ArrayList<String>();
	ArrayList<String> keyConcepts= new ArrayList<String>();
	ArrayList<String> validBConceptsList= new ArrayList<String>();
	ArrayList<Integer> validBConceptsIndices= new ArrayList<Integer>();
	ArrayList<String> allConcepts= new ArrayList<String> ();
	ArrayList<String> pathList= new ArrayList<String>();

	int i,j, size=0, found=0;
	
	
	keyConcepts.addAll(studentModel.getKeyConcepts());
	allConcepts.addAll(studentModel.getAllConceptList());
	pathList.addAll(studentModel.getPathList());
	bConcepts.addAll(studentModel.getbConcepts());

	
	JsonObjectBuilder nodesJsonBuilder = Json.createObjectBuilder();
	JsonArrayBuilder nodesArrBld = Json.createArrayBuilder();
	JsonArrayBuilder linksArrBld = Json.createArrayBuilder();
	JsonObjectBuilder linksJsonBuilder = Json.createObjectBuilder();

	String concepts="";
	i=0;
	size=studentModel.getAllConceptList().size();
	while(i<studentModel.getKeyConcepts().size()) {
		nodesJsonBuilder.add("name", studentModel.getKeyConcepts().get(i));
		nodesJsonBuilder.add("type", "G");
		ArrayList<String> validBConcepts= new ArrayList<String>();
		for(int m=0;m<size;m++) {
			for(int n=0;n<size;n++) {
				if(m==n || m>n) {
					continue;
				}
				
				if((studentModel.getAdjacencyMatrix()[m][n]!=999 && studentModel.getAdjacencyMatrix()[m][n]!=0)||
						(studentModel.getAdjacencyMatrix()[n][m]!=999 && studentModel.getAdjacencyMatrix()[n][m]!=0)) {
					if(studentModel.getAllConceptList().get(m).equals(keyConcepts.get(i))&&
							(inList(studentModel.getAllConceptList().get(n),studentModel.getbConcepts())==1)) {
						validBConcepts.add(studentModel.getAllConceptList().get(n));
					}
					else if(studentModel.getAllConceptList().get(n).equals(keyConcepts.get(i))&&
							(inList(studentModel.getAllConceptList().get(m),studentModel.getbConcepts())==1)) {
						validBConcepts.add(studentModel.getAllConceptList().get(m));
					}
				}
			}
		}
		concepts="<b>";
		for(int k=0; k<validBConcepts.size();k++) {
			String output = validBConcepts.get(k).substring(0, 1).toUpperCase() + validBConcepts.get(k).substring(1);
			if(k==validBConcepts.size()-1) {
				concepts=concepts+output+"</b>";
			}
			else {
				concepts=concepts+output+"<br /><br />";
			}
		}
		if(concepts.equals("<b>")) {
			concepts=concepts+"No Concepts</b>";
		}
		validBConceptsList.addAll(validBConcepts);
		System.out.println("****Concepts****: "+concepts);
		nodesJsonBuilder.add("concepts", concepts);
		nodesArrBld.add(nodesJsonBuilder);
		i++;
	}
	
	JsonArray nodesArr = nodesArrBld.build();

	int pathSize=0;
	int foundKeyConcepts=0;
	
	ArrayList<String> reducedPathList= new ArrayList<String>();
	String source="", dest="";
	String link="", reverseLink="";
	for(int k=0;k<studentModel.getPathList().size();k++) {
		//System.out.println("Splitting test ^ for shyama^b^com : "+"shyama^b^com".split("\\^")[0]);
		String[] path=new String[studentModel.getPathList().get(k).split("\\^").length];
		path=studentModel.getPathList().get(k).split("\\^");
		pathSize=path.length;
		foundKeyConcepts=0;
		//log.info("pathSize: "+pathSize);
		//log.info(path[0]+"   >     "+path[pathSize-1]);
		if(inList(studentModel.getAllConceptList().get(Integer.parseInt(path[0])),studentModel.getKeyConcepts())==1) {
			if(inList(studentModel.getAllConceptList().get(Integer.parseInt(path[pathSize-1])),studentModel.getKeyConcepts())==1) {
				for(int m=1; m<(path.length-1);m++) {
					if(inList(studentModel.getAllConceptList().get(Integer.parseInt(path[m])), studentModel.getKeyConcepts())==1) {
						foundKeyConcepts=1;
						break;
					}
				}
				
				if(foundKeyConcepts==1) {
					foundKeyConcepts=0;
					continue;
				}
				source=studentModel.getAllConceptList().get(Integer.parseInt(path[0]));
				dest=studentModel.getAllConceptList().get(Integer.parseInt(path[pathSize-1]));
				link=source+"^"+dest;
				reverseLink=dest+"^"+source;
				if(reducedPathList.isEmpty()) {
					reducedPathList.add(link);
				}
				else if((inList(link,reducedPathList)==1)||(inList(reverseLink,reducedPathList)==1)) {
						continue;
				}
				else {
					reducedPathList.add(link);
				}
				
			}
		}
	}
	
	found=0;
	ArrayList<ArrayList<String>> keyRelations = new ArrayList<ArrayList<String>>();
	
	for(i=0;i<reducedPathList.size();i++) {
		ArrayList<String> relations = new ArrayList<String>();
		String path[]=reducedPathList.get(i).split("\\^");
		System.out.println("path[]: "+path[0]+"   "+path[1]);
		linksJsonBuilder.add("source", path[0]);
		linksJsonBuilder.add("target", path[1]);
		linksJsonBuilder.add("type", "G");
		linksArrBld.add(linksJsonBuilder);
		relations.add(path[0]);
		relations.add(path[1]);
		keyRelations.add(relations);
	}
	studentModel.setKeyRelations(keyRelations);
	JsonArray linksArr = linksArrBld.build();
    System.out.println("Expert Reduced: "+nodesArr);
    System.out.println("Expert Reduced: "+linksArr);
    
    log.info("Expert Reduced: "+nodesArr);
    log.info("Expert Reduced: "+linksArr);
    
    studentModel.setExpertBaseLinks(linksArr.toString());
    studentModel.setExpertBaseNodes(nodesArr.toString());
    
    return studentModel;
}
	
}