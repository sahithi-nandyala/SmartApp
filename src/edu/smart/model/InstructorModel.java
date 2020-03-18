package edu.smart.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import edu.smart.pojo.AssignmentDetails;
import edu.smart.pojo.ClassDetails;
import edu.smart.pojo.ProjectDetails;
import edu.smart.pojo.UserDetails;

public class InstructorModel {
	
	private UserDetails studentDetails;
	private UserDetails instructordetails;
	private Map<ClassDetails,ArrayList<UserDetails>> classStudentList;
	private Map<ClassDetails,ArrayList<AssignmentDetails>> classAssignmentList;
	private Map<UserDetails,CourseDetailsModel> studentResultList;
	private ArrayList<CourseDetailsModel> studentModels; 
	private ClassDetails classDetails;
	private ArrayList<UserDetails> studentDetailsList;
	private AssignmentDetails assignmentDetails;
	private int numberOfStudents;
	private int numberOfResponses;
	private double avgRecallC;
	private double avgRecallP;
	private Map<String, Integer> mostMissingConcepts;
	private Map<ArrayList<String>, Integer> mostMissingLinks;
	private int numberOfClasses;
	private int numberOfAssignments;
	private int numberOfProjects;
	private ArrayList<AssignmentDetails> assignmentList;
	private ArrayList<AssignmentDetails> recentassignmentList;
	private ArrayList<ProjectDetails> projectList;
	private int projectsNeeded;
	private int status;
	private Map<CourseDetailsModel, UserDetails> modelStudentResultList;
	
	
	public InstructorModel() {
		studentDetails = new UserDetails();
		instructordetails = new UserDetails();
		classStudentList = new LinkedHashMap<ClassDetails,ArrayList<UserDetails>> ();
		studentResultList = new LinkedHashMap<UserDetails,CourseDetailsModel>();
		studentModels = new ArrayList<CourseDetailsModel>();
		classAssignmentList = new LinkedHashMap<ClassDetails,ArrayList<AssignmentDetails>>();
		classDetails = new ClassDetails();
		studentDetailsList = new ArrayList<UserDetails>();
		assignmentDetails = new AssignmentDetails();
		mostMissingConcepts = new LinkedHashMap<String, Integer>();
		mostMissingLinks = new LinkedHashMap<ArrayList<String>, Integer>();
		assignmentList = new ArrayList<AssignmentDetails>();
		recentassignmentList = new ArrayList<AssignmentDetails>();
		projectList = new ArrayList<ProjectDetails>();
		status=-1;
		modelStudentResultList = new LinkedHashMap<CourseDetailsModel, UserDetails>();
	}
	
	
	public Map<CourseDetailsModel, UserDetails> getModelStudentResultList() {
		return modelStudentResultList;
	}


	public void setModelStudentResultList(Map<CourseDetailsModel, UserDetails> modelStudentResultList) {
		this.modelStudentResultList = modelStudentResultList;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public int getProjectsNeeded() {
		return projectsNeeded;
	}


	public void setProjectsNeeded(int projectsNeeded) {
		this.projectsNeeded = projectsNeeded;
	}


	public ArrayList<AssignmentDetails> getRecentassignmentList() {
		return recentassignmentList;
	}


	public void setRecentassignmentList(ArrayList<AssignmentDetails> recentassignmentList) {
		this.recentassignmentList = recentassignmentList;
	}


	public ArrayList<AssignmentDetails> getAssignmentList() {
		return assignmentList;
	}


	public void setAssignmentList(ArrayList<AssignmentDetails> assignmentList) {
		this.assignmentList = assignmentList;
	}


	public ArrayList<ProjectDetails> getProjectList() {
		return projectList;
	}


	public void setProjectList(ArrayList<ProjectDetails> projectList) {
		this.projectList = projectList;
	}


	public int getNumberOfClasses() {
		return numberOfClasses;
	}


	public void setNumberOfClasses(int numberOfClasses) {
		this.numberOfClasses = numberOfClasses;
	}


	public int getNumberOfAssignments() {
		return numberOfAssignments;
	}


	public void setNumberOfAssignments(int numberOfAssignments) {
		this.numberOfAssignments = numberOfAssignments;
	}


	public int getNumberOfProjects() {
		return numberOfProjects;
	}


	public void setNumberOfProjects(int numberOfProjects) {
		this.numberOfProjects = numberOfProjects;
	}


	public Map<String, Integer> getMostMissingConcepts() {
		return mostMissingConcepts;
	}


	public void setMostMissingConcepts(Map<String, Integer> mostMissingConcepts) {
		this.mostMissingConcepts = mostMissingConcepts;
	}


	public Map<ArrayList<String>, Integer> getMostMissingLinks() {
		return mostMissingLinks;
	}


	public void setMostMissingLinks(Map<ArrayList<String>, Integer> mostMissingLinks) {
		this.mostMissingLinks = mostMissingLinks;
	}


	public double getAvgRecallC() {
		return avgRecallC;
	}


	public void setAvgRecallC(double avgRecallC) {
		this.avgRecallC = avgRecallC;
	}


	public double getAvgRecallP() {
		return avgRecallP;
	}


	public void setAvgRecallP(double avgRecallP) {
		this.avgRecallP = avgRecallP;
	}


	public int getNumberOfStudents() {
		return numberOfStudents;
	}


	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}


	public int getNumberOfResponses() {
		return numberOfResponses;
	}


	public void setNumberOfResponses(int numberOfResponses) {
		this.numberOfResponses = numberOfResponses;
	}


	public AssignmentDetails getAssignmentDetails() {
		return assignmentDetails;
	}


	public void setAssignmentDetails(AssignmentDetails assignmentDetails) {
		this.assignmentDetails = assignmentDetails;
	}


	public ClassDetails getClassDetails() {
		return classDetails;
	}


	public void setClassDetails(ClassDetails classDetails) {
		this.classDetails = classDetails;
	}


	public ArrayList<UserDetails> getStudentDetailsList() {
		return studentDetailsList;
	}


	public void setStudentDetailsList(ArrayList<UserDetails> studentDetailsList) {
		this.studentDetailsList = studentDetailsList;
	}


	public Map<ClassDetails, ArrayList<AssignmentDetails>> getClassAssignmentList() {
		return classAssignmentList;
	}


	public void setClassAssignmentList(Map<ClassDetails, ArrayList<AssignmentDetails>> classAssignmentList) {
		this.classAssignmentList = classAssignmentList;
	}


	public ArrayList<CourseDetailsModel> getStudentModels() {
		return studentModels;
	}


	public void setStudentModels(ArrayList<CourseDetailsModel> studentModels) {
		this.studentModels = studentModels;
	}


	public Map<UserDetails, CourseDetailsModel> getStudentResultList() {
		return studentResultList;
	}

	public void setStudentResultList(Map<UserDetails, CourseDetailsModel> studentResultList) {
		this.studentResultList = studentResultList;
	}

	public UserDetails getStudentDetails() {
		return studentDetails;
	}
	public void setStudentDetails(UserDetails studentDetails) {
		this.studentDetails = studentDetails;
	}
	public UserDetails getInstructordetails() {
		return instructordetails;
	}
	public void setInstructordetails(UserDetails instructordetails) {
		this.instructordetails = instructordetails;
	}
	public Map<ClassDetails, ArrayList<UserDetails>> getClassStudentList() {
		return classStudentList;
	}
	public void setClassStudentList(Map<ClassDetails, ArrayList<UserDetails>> classStudentList) {
		this.classStudentList = classStudentList;
	}
	
	

}
