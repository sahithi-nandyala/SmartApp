package edu.smart.pojo;

import java.util.ArrayList;

public class ClassDetails {
	
	private int classId;
	private String className;
	private String description;
	private int teacherId;
	private String[] classIds;
	private String[] studentIds;
	private int classIdSelected;
	private ArrayList<ArrayList<String>> missingKeyConcepts;
	private ArrayList<ArrayList<String>> missingKeyRelations;
	private int assignmentId;
	private int studentcount;
	private int studentresponses;
	
	public int getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}
	public ClassDetails() {
		missingKeyConcepts = new ArrayList<ArrayList<String>>();
		missingKeyRelations = new ArrayList<ArrayList<String>>();
		classIdSelected=0;
	}
	public ArrayList<ArrayList<String>> getMissingKeyConcepts() {
		return missingKeyConcepts;
	}
	public void setMissingKeyConcepts(ArrayList<ArrayList<String>> missingKeyConcepts) {
		this.missingKeyConcepts = missingKeyConcepts;
	}
	public ArrayList<ArrayList<String>> getMissingKeyRelations() {
		return missingKeyRelations;
	}
	public void setMissingKeyRelations(ArrayList<ArrayList<String>> missingKeyRelations) {
		this.missingKeyRelations = missingKeyRelations;
	}
	public int getClassIdSelected() {
		return classIdSelected;
	}
	public void setClassIdSelected(int classIdSelected) {
		this.classIdSelected = classIdSelected;
	}
	public String[] getStudentIds() {
		return studentIds;
	}
	public void setStudentIds(String[] studentIds) {
		this.studentIds = studentIds;
	}
	public String[] getClassIds() {
		return classIds;
	}
	public void setClassIds(String[] classIds) {
		this.classIds = classIds;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public int getStudentcount() {
		return studentcount;
	}
	public void setStudentcount(int studentcount) {
		this.studentcount = studentcount;
	}
	public int getStudentresponses() {
		return studentresponses;
	}
	public void setStudentresponses(int studentresponses) {
		this.studentresponses = studentresponses;
	}
	
}
