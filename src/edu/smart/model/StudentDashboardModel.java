
package edu.smart.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import edu.smart.pojo.AssignmentDetails;
import edu.smart.pojo.ClassDetails;


public class StudentDashboardModel {
	
	private int inputclassID;
	private Map<ClassDetails,ArrayList<AssignmentDetails>> assignmentdetails;
	private int studentid;
	private String emptymsg;
	private AssignmentDetails displayedassignment;
	private ArrayList<CourseDetailsModel> allresponses;
	private ArrayList<AssignmentDetails> recentassignments;
	private int totalclasses;
	private int totalassignments;
	
	public int getInputclassID() {
		return inputclassID;
	}
	public void setInputclassID(int inputclassID) {
		this.inputclassID = inputclassID;
	}

	public int getStudentid() {
		return studentid;
	}
	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}
	public String getEmptymsg() {
		return emptymsg;
	}
	public void setEmptymsg(String emptymsg) {
		this.emptymsg = emptymsg;
	}
	public AssignmentDetails getDisplayedassignment() {
		return displayedassignment;
	}
	public void setDisplayedassignment(AssignmentDetails displayedassignment) {
		this.displayedassignment = displayedassignment;
	}
	public Map<ClassDetails,ArrayList<AssignmentDetails>> getAssignmentdetails() {
		return assignmentdetails;
	}
	public void setAssignmentdetails(Map<ClassDetails,ArrayList<AssignmentDetails>> assignmentdetails) {
		this.assignmentdetails = assignmentdetails;
	}
	public ArrayList<CourseDetailsModel> getAllresponses() {
		return allresponses;
	}
	public void setAllresponses(ArrayList<CourseDetailsModel> allresponses) {
		this.allresponses = allresponses;
	}
	public ArrayList<AssignmentDetails> getRecentassignments() {
		return recentassignments;
	}
	public void setRecentassignments(ArrayList<AssignmentDetails> recentassignments) {
		this.recentassignments = recentassignments;
	}
	public int getTotalassignments() {
		return totalassignments;
	}
	public void setTotalassignments(int totalassignments) {
		this.totalassignments = totalassignments;
	}
	public int getTotalclasses() {
		return totalclasses;
	}
	public void setTotalclasses(int totalclasses) {
		this.totalclasses = totalclasses;
	}


}
