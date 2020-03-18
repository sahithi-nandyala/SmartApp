package edu.smart.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import edu.smart.pojo.ClassDetails;
import edu.smart.pojo.ClassStudentDetails;
import edu.smart.pojo.UserDetails;

public class StudentManagementModel {

	private ArrayList<ClassStudentDetails> studentList;
	private ArrayList<UserDetails> studentDetailsList;
	private UserDetails studentDetails;
	private UserDetails instructordetails;
	private ArrayList<ClassDetails> classDetailsList;
	private ClassDetails classadded;
	private ClassDetails classDetails;
	private String[] selectedClasses;
	private Map<ClassDetails,ArrayList<UserDetails>> classStudentList;
	private int id;
	private int exist;
	private int noOfClasses;
	
	
	public int getNoOfClasses() {
		return noOfClasses;
	}

	public void setNoOfClasses(int noOfClasses) {
		this.noOfClasses = noOfClasses;
	}

	public int getExist() {
		return exist;
	}

	public void setExist(int exist) {
		this.exist = exist;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<ClassDetails, ArrayList<UserDetails>> getClassStudentList() {
		return classStudentList;
	}

	public void setClassStudentList(Map<ClassDetails, ArrayList<UserDetails>> classStudentList) {
		this.classStudentList = classStudentList;
	}

	public String[] getSelectedClasses() {
		return selectedClasses;
	}

	public void setSelectedClasses(String[] selectedClasses) {
		this.selectedClasses = selectedClasses;
	}

	public ClassDetails getClassDetails() {
		return classDetails;
	}

	public void setClassDetails(ClassDetails classDetails) {
		this.classDetails = classDetails;
	}

	public UserDetails getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(UserDetails studentDetails) {
		this.studentDetails = studentDetails;
	}

	public ArrayList<ClassStudentDetails> getStudentList() {
		return studentList;
	}

	public void setStudentList(ArrayList<ClassStudentDetails> studentList) {
		this.studentList = studentList;
	}
	
	public ArrayList<UserDetails> getStudentDetailsList() {
		return studentDetailsList;
	}

	public void setStudentDetailsList(ArrayList<UserDetails> studentDetailsList) {
		this.studentDetailsList = studentDetailsList;
	}

	public StudentManagementModel() {
		studentList = new ArrayList<ClassStudentDetails>();
		studentDetailsList = new ArrayList<UserDetails>();
		studentDetails = new UserDetails();
		classStudentList = new LinkedHashMap<ClassDetails,ArrayList<UserDetails>> ();
		instructordetails = new UserDetails();
		exist = -1;
	}
	
	
	public ArrayList<ClassDetails> getClassDetailsList() {
		return classDetailsList;
	}

	public void setClassDetailsList(ArrayList<ClassDetails> classDetailsList) {
		this.classDetailsList = classDetailsList;
	}

	public UserDetails getInstructordetails() {
		return instructordetails;
	}

	public void setInstructordetails(UserDetails instructordetails) {
		this.instructordetails = instructordetails;
	}

	public ClassDetails getClassadded() {
		return classadded;
	}

	public void setClassadded(ClassDetails classadded) {
		this.classadded = classadded;
	}
	
}