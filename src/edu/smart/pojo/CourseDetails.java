package edu.smart.pojo;

import java.util.ArrayList;

public class CourseDetails {
	
	private String title;
	private String text;
	private int modelID;
	private String model;
	private String assignmentID;
	private String assignTitle;
	private String concepttype;
	private ArrayList<String> manualKeyconcepts;
	private int userId;
	private int studentresponseid;	
	private String centrality;
	private int threshold;
	private String createdon;

	public int getStudentresponseid() {
		return studentresponseid;
	}
	public void setStudentresponseid(int studentresponseid) {
		this.studentresponseid = studentresponseid;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}

	public String getAssignmentID() {
		return assignmentID;
	}
	public void setAssignmentID(String assignmentID) {
		this.assignmentID = assignmentID;
	}
	public String getAssignTitle() {
		return assignTitle;
	}
	public void setAssignTitle(String assignTitle) {
		this.assignTitle = assignTitle;
	}
	public int getModelID() {
		return modelID;
	}
	public void setModelID(int modelID) {
		this.modelID = modelID;
	}
	public ArrayList<String> getManualKeyconcepts() {
		return manualKeyconcepts;
	}
	public void setManualKeyconcepts(ArrayList<String> manualKeyconcepts) {
		this.manualKeyconcepts = manualKeyconcepts;
	}
	public String getConcepttype() {
		return concepttype;
	}
	public void setConcepttype(String concepttype) {
		this.concepttype = concepttype;
	}
	public String getCentrality() {
		return centrality;
	}
	public void setCentrality(String centrality) {
		this.centrality = centrality;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	public String getCreatedon() {
		return createdon;
	}
	public void setCreatedon(String createdon) {
		this.createdon = createdon;
	}
	
	
	
	

}
