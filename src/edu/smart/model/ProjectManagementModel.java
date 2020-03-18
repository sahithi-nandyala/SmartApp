package edu.smart.model;

import java.util.ArrayList;

import edu.smart.pojo.AssignmentDetails;
import edu.smart.pojo.ClassDetails;
import edu.smart.pojo.CourseDetails;
import edu.smart.pojo.ProjectDetails;
import edu.smart.pojo.UserDetails;

public class ProjectManagementModel {

	private UserDetails instructorDetails;
	private ArrayList<ClassDetails> classdetails;
	private ArrayList<ProjectDetails> existingprojects;
	private ProjectDetails projectadded;
	private AssignmentDetails assignmentadded;
	private ArrayList<AssignmentDetails> existingassignments;
	private String[] removeprojects;
	private AssignmentDetails displayedAssignment;
	private ArrayList<CourseDetails> referencemodels;
	
	
	
	public ProjectManagementModel() {
		instructorDetails = new UserDetails();
	}

	public ArrayList<ClassDetails> getClassdetails() {
		return classdetails;
	}

	public ArrayList<AssignmentDetails> getExistingassignments() {
		return existingassignments;
	}

	public void setExistingassignments(ArrayList<AssignmentDetails> existingassignments) {
		this.existingassignments = existingassignments;
	}

	public void setClassdetails(ArrayList<ClassDetails> classdetails) {
		this.classdetails = classdetails;
	}


	public UserDetails getInstructorDetails() {
		return instructorDetails;
	}

	public void setInstructorDetails(UserDetails instructorDetails) {
		this.instructorDetails = instructorDetails;
	}

	public ProjectDetails getProjectadded() {
		return projectadded;
	}

	public void setProjectadded(ProjectDetails projectadded) {
		this.projectadded = projectadded;
	}

	public ArrayList<ProjectDetails> getExistingprojects() {
		return existingprojects;
	}

	public void setExistingprojects(ArrayList<ProjectDetails> existingprojects) {
		this.existingprojects = existingprojects;
	}

	public AssignmentDetails getAssignmentadded() {
		return assignmentadded;
	}

	public void setAssignmentadded(AssignmentDetails assignmentadded) {
		this.assignmentadded = assignmentadded;
	}

	public String[] getRemoveprojects() {
		return removeprojects;
	}

	public void setRemoveprojects(String[] removeprojects) {
		this.removeprojects = removeprojects;
	}

	public AssignmentDetails getDisplayedAssignment() {
		return displayedAssignment;
	}

	public void setDisplayedAssignment(AssignmentDetails displayedAssignment) {
		this.displayedAssignment = displayedAssignment;
	}

	public ArrayList<CourseDetails> getReferencemodels() {
		return referencemodels;
	}

	public void setReferencemodels(ArrayList<CourseDetails> referencemodels) {
		this.referencemodels = referencemodels;
	}
	
}
