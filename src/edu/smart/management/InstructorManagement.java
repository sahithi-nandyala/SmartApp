package edu.smart.management;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import edu.smart.controller.CourseDetailsController;
import edu.smart.dao.CourseDetailsDaoImpl;
import edu.smart.dao.ManagementDaoImpl;
import edu.smart.model.CourseDetailsModel;
import edu.smart.model.InstructorModel;
import edu.smart.pojo.AssignmentDetails;
import edu.smart.pojo.ClassDetails;
import edu.smart.pojo.UserDetails;

public class InstructorManagement {
	
	static Logger log = Logger.getLogger(CourseDetailsController.class.getName());
	
	public InstructorModel getAllStudentsOfInstructorByClass(int instructorId, ManagementDaoImpl managementDaoImpl,InstructorModel instructorModel) {
		//StudentManagementModel studentManagementModel = new StudentManagementModel();
		instructorModel.setClassStudentList(managementDaoImpl.getAllStudentsOfInstructorByClass(instructorId, managementDaoImpl));
		return instructorModel;
	}
	
	public InstructorModel getClassLevelResults(InstructorModel instructorModel,ManagementDaoImpl managementDaoImpl) {
	
	    for (Entry<ClassDetails, ArrayList<UserDetails>> entry : instructorModel.getClassStudentList().entrySet())
	    {
	        ClassDetails classDetails = new ClassDetails();
	        ArrayList<UserDetails> studentList = new ArrayList<UserDetails>();
	        classDetails = entry.getKey();
	        studentList = entry.getValue();
	        for(UserDetails stu: studentList) {
	        		instructorModel=managementDaoImpl.getStudentModels(classDetails,instructorModel);
	        }
	        
	    }
		return instructorModel;
	}
	
	public InstructorModel getClassLevelResultOfAssgnt(int assgntId,ManagementDaoImpl managementDaoImpl, InstructorModel instructorModel, int download) {
		instructorModel=getAllStudentsOfInstructorByClass(instructorModel.getInstructordetails().getUserID(),managementDaoImpl,instructorModel);
		//instructorModel.setClassDetails(managementDaoImpl.getClassOfAssignment(assgntId));
		double avgRecallC=0;
		double avgRecallP=0;
		LinkedHashMap<String, Integer> mostMissingConcepts = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> mostMissingConceptsSorted = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> mostMissingLinks = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> mostMissingLinksSorted = new LinkedHashMap<String, Integer>();
		log.info("class ID: "+instructorModel.getClassDetails().getClassId());
		log.info("Assignmnet ID: "+instructorModel.getAssignmentDetails().getAssgnmntId());
		for (Map.Entry<ClassDetails,ArrayList<UserDetails>> pair : instructorModel.getClassStudentList().entrySet()) {
		    if(pair.getKey().getClassId()==instructorModel.getClassDetails().getClassId()) {
		    		instructorModel.setStudentDetailsList(pair.getValue());
		    		break;
		    }
		}
		if(download==0) {
			instructorModel=managementDaoImpl.getStudentModelsOfClass(instructorModel);
		}
		else {
			instructorModel=managementDaoImpl.getStudentModelsOfClassForDownload(instructorModel);
		}
		
		if(instructorModel.getStatus()==-2) {
			return instructorModel;
		}
		/*if(instructorModel.getStatus()==-3) {
			return instructorModel;
		}*/
		instructorModel.setNumberOfStudents(instructorModel.getStudentDetailsList().size());
		instructorModel.setNumberOfResponses(instructorModel.getStudentResultList().size());
		DecimalFormat df2 = new DecimalFormat(".#####");
		for (Map.Entry<UserDetails,CourseDetailsModel> pair : instructorModel.getStudentResultList().entrySet()) {
			pair.getValue().setRecallkeyconcepts(Double.parseDouble((df2.format(pair.getValue().getRecallkeyconcepts()))));
			pair.getValue().setRecallKeylinks(Double.parseDouble((df2.format(pair.getValue().getRecallKeylinks()))));
			avgRecallC = avgRecallC + pair.getValue().getRecallkeyconcepts();
			avgRecallP = avgRecallP + pair.getValue().getRecallKeylinks();
		}
		avgRecallC = avgRecallC / instructorModel.getNumberOfResponses();
		avgRecallP = avgRecallP / instructorModel.getNumberOfResponses();
		
		log.info("avgRecallC = "+avgRecallC);
		log.info("avgRecallP = "+avgRecallP);
		
		instructorModel.setAvgRecallC(Double.parseDouble((df2.format(avgRecallC))));
		instructorModel.setAvgRecallP(Double.parseDouble((df2.format(avgRecallP))));
		
		int index=0;
		
		for(CourseDetailsModel model: instructorModel.getStudentModels()) {
			if(index==0){
				index++;
				for(int j=0; j<model.getMissingConcepts().size(); j++) {
					mostMissingConcepts.put(model.getMissingConcepts().get(j),1);
				}
				continue;
			}
			ArrayList<String> mostMissingConceptList = new ArrayList<String>(mostMissingConcepts.keySet());
			for(int j=0; j<model.getMissingConcepts().size(); j++) {
				if(inList(model.getMissingConcepts().get(j),mostMissingConceptList)==0) {
					mostMissingConcepts.put(model.getMissingConcepts().get(j),1);
				}
				else {
					for (Map.Entry<String, Integer> pair : mostMissingConcepts.entrySet()) {
						if(pair.getKey().equals(model.getMissingConcepts().get(j))) {
							pair.setValue(pair.getValue()+1);
						}
					}
				}
			}
		}
		
		mostMissingConceptsSorted = sortHashMapByValues(mostMissingConcepts);
		instructorModel.setMostMissingConcepts(mostMissingConceptsSorted);
		
		int index2=0;
		for(CourseDetailsModel model: instructorModel.getStudentModels()) {
		
			if(index2==0) {
				index2++;
				for(int j=0; j<model.getMissingLinks().size(); j++) {
					/*	String[] relationConcepts = model.getMissingLinks().get(j).split("-");
					ArrayList<String> relationConceptsList = new ArrayList<String>();
					for(String c: relationConcepts) {
						relationConceptsList.add(c);
					}*/
					mostMissingLinks.put(model.getMissingLinks().get(j),1);
				}
				continue;
			}
			ArrayList<String> relationAddedList = new ArrayList<String>(mostMissingLinks.keySet());
			
			for(int j=0; j<model.getMissingLinks().size(); j++) {
				/*String[] relationConcepts = model.getMissingLinks().get(j).split("-");
				ArrayList<String> relationConceptsList = new ArrayList<String>();
				for(String c: relationConcepts) {
					relationConceptsList.add(c);
				}*/
				if (inList(model.getMissingLinks().get(j), relationAddedList)==0) {
					mostMissingLinks.put(model.getMissingLinks().get(j),1);
				}
				else {
					for (Map.Entry<String, Integer> pair : mostMissingLinks.entrySet()) {
						if(pair.getKey().equals(model.getMissingLinks().get(j))) {
							pair.setValue(pair.getValue()+1);
						}
					}
				}
			}
			
		}
		mostMissingLinksSorted = sortHashMapByValues(mostMissingLinks);
		for (Map.Entry<String, Integer> pair : mostMissingLinksSorted.entrySet()) {
			String[] relationConcepts = pair.getKey().split("\\^");
			ArrayList<String> relationConceptsList = new ArrayList<String>();
			for(String c: relationConcepts) {
				relationConceptsList.add(c);
			}
			instructorModel.getMostMissingLinks().put(relationConceptsList, pair.getValue());
		}
		return instructorModel;
	}
	
	public InstructorModel getClassLevelResultOfAssgntForDownload(int assgntId,ManagementDaoImpl managementDaoImpl, InstructorModel instructorModel, int download) {
		instructorModel=getAllStudentsOfInstructorByClass(instructorModel.getInstructordetails().getUserID(),managementDaoImpl,instructorModel);
		//instructorModel.setClassDetails(managementDaoImpl.getClassOfAssignment(assgntId));
		double avgRecallC=0;
		double avgRecallP=0;
		LinkedHashMap<String, Integer> mostMissingConcepts = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> mostMissingConceptsSorted = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> mostMissingLinks = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> mostMissingLinksSorted = new LinkedHashMap<String, Integer>();
		log.info("class ID: "+instructorModel.getClassDetails().getClassId());
		log.info("Assignmnet ID: "+instructorModel.getAssignmentDetails().getAssgnmntId());
		for (Map.Entry<ClassDetails,ArrayList<UserDetails>> pair : instructorModel.getClassStudentList().entrySet()) {
		    if(pair.getKey().getClassId()==instructorModel.getClassDetails().getClassId()) {
		    		instructorModel.setStudentDetailsList(pair.getValue());
		    		break;
		    }
		}
		if(download==0) {
			instructorModel=managementDaoImpl.getStudentModelsOfClass(instructorModel);
		}
		else {
			instructorModel=managementDaoImpl.getStudentModelsOfClassForDownload(instructorModel);
		}
		
		if(instructorModel.getStatus()==-2) {
			return instructorModel;
		}
		/*if(instructorModel.getStatus()==-3) {
			return instructorModel;
		}*/
		/*instructorModel.setNumberOfStudents(instructorModel.getModelStudentResultList().size());
		instructorModel.setNumberOfResponses(instructorModel.getStudentResultList().size());
		DecimalFormat df2 = new DecimalFormat(".#####");
		for (Map.Entry<UserDetails,CourseDetailsModel> pair : instructorModel.getStudentResultList().entrySet()) {
			pair.getValue().setRecallkeyconcepts(Double.parseDouble((df2.format(pair.getValue().getRecallkeyconcepts()))));
			pair.getValue().setRecallKeylinks(Double.parseDouble((df2.format(pair.getValue().getRecallKeylinks()))));
			avgRecallC = avgRecallC + pair.getValue().getRecallkeyconcepts();
			avgRecallP = avgRecallP + pair.getValue().getRecallKeylinks();
		}
		avgRecallC = avgRecallC / instructorModel.getNumberOfResponses();
		avgRecallP = avgRecallP / instructorModel.getNumberOfResponses();
		
		log.info("avgRecallC = "+avgRecallC);
		log.info("avgRecallP = "+avgRecallP);
		
		instructorModel.setAvgRecallC(Double.parseDouble((df2.format(avgRecallC))));
		instructorModel.setAvgRecallP(Double.parseDouble((df2.format(avgRecallP))));
		
		int index=0;
		
		for(CourseDetailsModel model: instructorModel.getStudentModels()) {
			if(index==0){
				index++;
				for(int j=0; j<model.getMissingConcepts().size(); j++) {
					mostMissingConcepts.put(model.getMissingConcepts().get(j),1);
				}
				continue;
			}
			ArrayList<String> mostMissingConceptList = new ArrayList<String>(mostMissingConcepts.keySet());
			for(int j=0; j<model.getMissingConcepts().size(); j++) {
				if(inList(model.getMissingConcepts().get(j),mostMissingConceptList)==0) {
					mostMissingConcepts.put(model.getMissingConcepts().get(j),1);
				}
				else {
					for (Map.Entry<String, Integer> pair : mostMissingConcepts.entrySet()) {
						if(pair.getKey().equals(model.getMissingConcepts().get(j))) {
							pair.setValue(pair.getValue()+1);
						}
					}
				}
			}
		}
		
		mostMissingConceptsSorted = sortHashMapByValues(mostMissingConcepts);
		instructorModel.setMostMissingConcepts(mostMissingConceptsSorted);
		
		int index2=0;
		for(CourseDetailsModel model: instructorModel.getStudentModels()) {
		
			if(index2==0) {
				index2++;
				for(int j=0; j<model.getMissingLinks().size(); j++) {
						String[] relationConcepts = model.getMissingLinks().get(j).split("-");
					ArrayList<String> relationConceptsList = new ArrayList<String>();
					for(String c: relationConcepts) {
						relationConceptsList.add(c);
					}
					mostMissingLinks.put(model.getMissingLinks().get(j),1);
				}
				continue;
			}
			ArrayList<String> relationAddedList = new ArrayList<String>(mostMissingLinks.keySet());
			
			for(int j=0; j<model.getMissingLinks().size(); j++) {
				String[] relationConcepts = model.getMissingLinks().get(j).split("-");
				ArrayList<String> relationConceptsList = new ArrayList<String>();
				for(String c: relationConcepts) {
					relationConceptsList.add(c);
				}
				if (inList(model.getMissingLinks().get(j), relationAddedList)==0) {
					mostMissingLinks.put(model.getMissingLinks().get(j),1);
				}
				else {
					for (Map.Entry<String, Integer> pair : mostMissingLinks.entrySet()) {
						if(pair.getKey().equals(model.getMissingLinks().get(j))) {
							pair.setValue(pair.getValue()+1);
						}
					}
				}
			}
			
		}
		mostMissingLinksSorted = sortHashMapByValues(mostMissingLinks);
		for (Map.Entry<String, Integer> pair : mostMissingLinksSorted.entrySet()) {
			String[] relationConcepts = pair.getKey().split("-");
			ArrayList<String> relationConceptsList = new ArrayList<String>();
			for(String c: relationConcepts) {
				relationConceptsList.add(c);
			}
			instructorModel.getMostMissingLinks().put(relationConceptsList, pair.getValue());
		}*/
		return instructorModel;
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
	
	
	public LinkedHashMap<String, Integer> sortHashMapByValues(LinkedHashMap<String, Integer> passedMap) {
	    List<String> mapKeys = new ArrayList<>(passedMap.keySet());
	    List<Integer> mapValues = new ArrayList<>(passedMap.values());
	    //Collections.sort(mapKeys);
	    Collections.sort(mapValues,Collections.reverseOrder());

	    LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();

	    Iterator<Integer> valueIt = mapValues.iterator();
	    while (valueIt.hasNext()) {
	        int val = valueIt.next();
	        Iterator<String> keyIt = mapKeys.iterator();

	        while (keyIt.hasNext()) {
	            String key = keyIt.next();
	            int comp1 = passedMap.get(key);
	            int comp2 = val;

	            if (comp1==comp2) {
	                keyIt.remove();
	                sortedMap.put(key, val);
	                break;
	            }
	        }
	    }
	    return sortedMap;
	}
	
	public int checkIfClassNameExists(ClassDetails classDetails, ManagementDaoImpl managementDaoImpl) {
		int exist=0;
		exist = managementDaoImpl.checkIfClassNameExists(classDetails.getClassName(), classDetails.getClassId(), classDetails.getTeacherId());
		return exist;
	}
	
	public String generateResultsCsvFile(InstructorModel instructorModel)
    {

		String output = "Assignment ID, Assignment title, Class name, Class ID, User name, email, first name, response date and time, number of words, "
        		+ " number of concepts, number of relations, number of key concepts, number of key links, recall-C, recall-P \n";

        /*for (int i=0;i<classList.size();i++) {
            output += classList.get(i).getClassId() + ", " + classList.get(i).getClassName() + ", " + classList.get(i).getDescription() +"\n";
        }*/
        AssignmentDetails assignmentDetails = new AssignmentDetails();
        ClassDetails classDetails = new ClassDetails();
        
        assignmentDetails = instructorModel.getAssignmentDetails();
        classDetails = instructorModel.getClassDetails();
        
        for (Map.Entry<CourseDetailsModel, UserDetails> pair : instructorModel.getModelStudentResultList().entrySet()) {
        		UserDetails studentDetails = new UserDetails();
        		CourseDetailsModel model = new CourseDetailsModel();
        		studentDetails = pair.getValue();
        		model = pair.getKey();
        		//model.setNoOfKeyConcepts(model.getKeyConcepts().size());
        		String trimmed = model.getText().trim();
        		model.setNoOfWords(trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length);
        		
        		output += 	assignmentDetails.getAssgnmntId() + ", " + assignmentDetails.getTitle() + ", " + classDetails.getClassName()+ ", " +classDetails.getClassId()
        		+ ", " + studentDetails.getUserName() + ", " + studentDetails.getEmail()+ ", " + studentDetails.getFirstName()+ ", " + model.getCreatedDateTime()+ ", " 
        				+ model.getNoOfWords()+ ", " + model.getNoOfConcepts()+ ", " + model.getNoOfRelations()+ ", " + model.getKeyConcepts().size()+", "+ model.getCommonLinks().size()+ ", " 
        				+ model.getRecallkeyconcepts()+ ", "+ model.getRecallKeylinks() +"\n";
        }
        return output;

    }
	public String generateResultsCsvFileforresearcher(InstructorModel instructorModel)
    {

        String output = "Assignment ID, Assignment title, Class name, Class ID, User name, email, first name, response date and time, number of words, "
        		+ " number of concepts, number of relations, number of key concepts, number of key links, recall-C, recall-P,Average degree,density,mean distance,diameter,subgroups,SM_No of Concepts,"
        		+ "SM_NoOfRelations,SM_Avg degree,SM_Density,SM_Diameter,SM_Mean Distance,SM_Subgroups,SM_Propositional Matching,SM_Concept Matching,SM_Balanced Matching\n";

        /*for (int i=0;i<classList.size();i++) {
            output += classList.get(i).getClassId() + ", " + classList.get(i).getClassName() + ", " + classList.get(i).getDescription() +"\n";
        }*/
        AssignmentDetails assignmentDetails = new AssignmentDetails();
        ClassDetails classDetails = new ClassDetails();
        
        assignmentDetails = instructorModel.getAssignmentDetails();
        classDetails = instructorModel.getClassDetails();
        
        for (Map.Entry<CourseDetailsModel, UserDetails> pair : instructorModel.getModelStudentResultList().entrySet()) {
        		UserDetails studentDetails = new UserDetails();
        		CourseDetailsModel model = new CourseDetailsModel();
        		studentDetails = pair.getValue();
        		model = pair.getKey();
        		model.setNoOfKeyConcepts(model.getKeyConcepts().size());
        		model.setNoOfKeyLinks(model.getKeyConcepts().size()+1);
        		String trimmed = model.getText().trim();
        		model.setNoOfWords(trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length);
        		
        		output += 	assignmentDetails.getAssgnmntId() + ", " + assignmentDetails.getTitle() + ", " + classDetails.getClassName()+ ", " +classDetails.getClassId()
        		+ ", " + studentDetails.getUserName() + ", " + studentDetails.getEmail()+ ", " + studentDetails.getFirstName()+ ", " + model.getCreatedDateTime()+ ", " 
        				+ model.getNoOfWords()+ ", " + model.getNoOfConcepts()+ ", " + model.getNoOfRelations()+ ", " + model.getNoOfKeyConcepts()+", "+ model.getCommonLinks().size()+ ", " 
        				+ model.getRecallkeyconcepts()+ ", "+ model.getRecallKeylinks() + "," +model.getAvgdegree()
        				+ "," +model.getDensity()+"," +model.getMeandistance()+"," +model.getDiameter()+"," +model.getSubgraph()+"," +model.getSm_NOofconcepts()+"," 
        				+model.getSm_NoOfRelations()+"," +model.getSm_avgdegree()+"," +model.getSm_density()+"," +model.getSm_diameter()+"," +model.getSm_meandistance()+"," +model.getSm_Subgraph()+"," +model.getSm_propositionalmatching()+"," +model.getSm_conceptualmatching()+"," +model.getSm_Balancedmatching()+"\n";
        }
        return output;
    }
	
	public InstructorModel setValuesForDashboardStats(InstructorModel instructorModel, CourseDetailsDaoImpl courseDetailsDaoImpl) {
		int numberofstudents=0;
		int default_projectsNeeded=3;
		int projectneeededlimit=5;
		ArrayList<AssignmentDetails> assignmentList= new ArrayList<AssignmentDetails>();
		ArrayList<AssignmentDetails> assignmentList5= new ArrayList<AssignmentDetails>();
		for (Map.Entry<ClassDetails,ArrayList<UserDetails>> pair : instructorModel.getClassStudentList().entrySet()) {
			numberofstudents = numberofstudents + pair.getValue().size();
		}
		instructorModel.setNumberOfStudents(numberofstudents);
		instructorModel.setNumberOfClasses(instructorModel.getClassStudentList().keySet().size());
		instructorModel.setProjectList(courseDetailsDaoImpl.getprojects(instructorModel.getInstructordetails().getUserID()));
		assignmentList = courseDetailsDaoImpl.getassignments(instructorModel.getInstructordetails().getUserID());
		if(assignmentList.size()>5) {
			for(int i=0;i<5;i++) {
				assignmentList5.add(assignmentList.get(i));
			}
		}
		else {
			assignmentList5.addAll(assignmentList);
		}
		instructorModel.setAssignmentList(assignmentList);
		instructorModel.setRecentassignmentList(assignmentList5);
		instructorModel.setNumberOfAssignments(instructorModel.getAssignmentList().size());
		instructorModel.setNumberOfProjects(instructorModel.getProjectList().size());
		/*if((5-instructorModel.getNumberOfProjects()) <= 0)
			instructorModel.setProjectsNeeded(default_PorjectsNeeded);
		else
			instructorModel.setProjectsNeeded(instructorModel.getNumberOfProjects());*/
		if(instructorModel.getNumberOfProjects() >= 5)
			instructorModel.setProjectsNeeded((instructorModel.getNumberOfProjects()-default_projectsNeeded));
		
		if(instructorModel.getNumberOfProjects() == 0 )
			instructorModel.setProjectsNeeded(projectneeededlimit);
		else
			instructorModel.setProjectsNeeded(instructorModel.getNumberOfProjects());
		
		
		return instructorModel;
	}
	
	public String generatecsv(CourseDetailsModel coursedetailsmodel)
	{
		String COMMA_DELIMITER = ",";		
		String NEW_LINE_SEPARATOR = "\n";
		//CSV file header
		String FILE_HEADER = "Expertid,No of Words,No of Relations,No of Concepts,Average Degree,Density,Diameter,Mean Distance,Subgroups\n";
		
		StringBuffer fileWriter = null;
	
			fileWriter = new StringBuffer();
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(String.valueOf(coursedetailsmodel.getExpertID()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getNoOfWords()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getNoOfRelations()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getNoOfConcepts()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getAvgdegree()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getDensity()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getDiameter()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getMeandistance()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getSubgraphs()));
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("*****Centrality Measures****");
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("Concepts,Clustering centrality,Betweenness Centrality,Pagerank,Closeness Centrality,Eigenvector centrality");
		
			String[] betweenness=coursedetailsmodel.getBetweenness().split("/");
			String[] closeness=coursedetailsmodel.getClosenessCentrality().split("/");
			String[] pagerank=coursedetailsmodel.getPageRank().split("/");
			String[] eigenvector=coursedetailsmodel.getEigenVectorCentrality().split("/");
			String[] clustering=coursedetailsmodel.getClusteringcoef().split("/");
			
			for(int i=0;i<betweenness.length;i++)
			{
				fileWriter.append(NEW_LINE_SEPARATOR);
				fileWriter.append(clustering[i].split(":")[0]);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(clustering[i].split(":")[1]);
/*				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(betweenness[i].split(":")[0]);*/
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(betweenness[i].split(":")[1]);
/*				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(pagerank[i].split(":")[0]);*/
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(pagerank[i].split(":")[1]);
/*				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(closeness[i].split(":")[0]);*/
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(closeness[i].split(":")[1]);
/*				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(eigenvector[i].split(":")[0]);*/
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(eigenvector[i].split(":")[1]);
			}
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("****Relations****");
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("Source,Target,Frequency");
			String[] relations=coursedetailsmodel.getRelation().split(",");
			for(int i=0;i<relations.length;i++)
			{
				fileWriter.append(NEW_LINE_SEPARATOR);
				fileWriter.append(relations[i].split("/")[0]);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(relations[i].split("/")[1]);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(relations[i].split("/")[2]);
			}
			
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("****KeyConcepts****");
			
			ArrayList<String> Keyconcepts=coursedetailsmodel.getKeyConcepts();
			
			for(String key:Keyconcepts)
			{
				fileWriter.append(NEW_LINE_SEPARATOR);
				fileWriter.append(key);
			}
			System.out.println(betweenness.length+" "+closeness.length+" "+pagerank.length+" "+eigenvector.length+" "+clustering.length);
		return fileWriter.toString();
	}
	
	public String generatecsvstu(CourseDetailsModel coursedetailsmodel)
	{
		String COMMA_DELIMITER = ",";		
		String NEW_LINE_SEPARATOR = "\n";
		//CSV file header
		String FILE_HEADER = "Student Model ID,No of Words,No of Relations,No of Concepts,Average Degree,Density,Diameter,Mean Distance,SM_Average Degree,SM_Density,SM_Diameter,SM_Mean Distance,SM_No of Concepts,SM_No of relations,SM_Conceptual Similarity,SM_Propositional Similarity,Sm_Balanced,Recall-C,Recall-P\n";
		
		StringBuffer fileWriter = null;
	
			fileWriter = new StringBuffer();
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(String.valueOf(coursedetailsmodel.getExpertID()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getNoOfWords()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getNoOfRelations()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getNoOfConcepts()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getAvgdegree()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getDensity()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getDiameter()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getMeandistance()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getSm_avgdegree()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getSm_density()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getSm_diameter()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getSm_meandistance()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getSm_NOofconcepts()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getSm_NoOfRelations()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getSm_conceptualmatching()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getSm_propositionalmatching()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getSm_Balancedmatching()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getRecallkeyconcepts()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(coursedetailsmodel.getRecallKeylinks()));
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("*****All Concepts****");
			ArrayList<String> Concepts=coursedetailsmodel.getAllConceptList();

			
			for(int i=0;i<Concepts.size();i++)
			{
				fileWriter.append(NEW_LINE_SEPARATOR);
				fileWriter.append(Concepts.get(i));
				fileWriter.append(COMMA_DELIMITER);

			}
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("****Relations****");
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("Source,Target,Frequency");
			String[] relations=coursedetailsmodel.getRelation().split(",");
			for(int i=0;i<relations.length;i++)
			{
				fileWriter.append(NEW_LINE_SEPARATOR);
				fileWriter.append(relations[i].split("/")[0]);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(relations[i].split("/")[1]);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(relations[i].split("/")[2]);
			}
			
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("****KeyConcepts****");
			
			ArrayList<String> Keyconcepts=coursedetailsmodel.getKeyConcepts();
			
			for(String key:Keyconcepts)
			{
				fileWriter.append(NEW_LINE_SEPARATOR);
				fileWriter.append(key);
			}
		//	System.out.println(betweenness.length+" "+closeness.length+" "+pagerank.length+" "+eigenvector.length+" "+clustering.length);
		return fileWriter.toString();
	}
	
	public CourseDetailsModel convertToAdjacencyMatrix(CourseDetailsModel model) {	
		ArrayList<ArrayList<String>> adjacencyList= new ArrayList<ArrayList<String>>();
		String[] adjacencyArray1 = model.getDBAdjacencyMatrix().split("/");
		int i,j, size=0;
		
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
		model.setAdjacencyMatrix(adjacencyMatrix);
		return model;
	}
}
