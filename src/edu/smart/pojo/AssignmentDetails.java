package edu.smart.pojo;

import java.util.ArrayList;

public class AssignmentDetails {

		private int assgnmntId;
		private int projectId;
		private String title;
		private String status;
		private String dateCreated;
		private String description;
		private String[] removeassign;
		private String directions;
		private ArrayList<ClassDetails> classdetails;
		private int selectedclassIDtoadd;
		private ArrayList<CourseDetails> expertmodels;
		private int choosenmodelID;
		private CourseDetails refmodel;
		private ClassDetails addedclass;
		private ClassDetails classDetails;
		private int classId;
		private String assIdString;
		private String type; 
		
		public String getAssIdString() {
			return assIdString;
		}
		public void setAssIdString(String assIdString) {
			this.assIdString = assIdString;
		}
		public int getClassId() {
			return classId;
		}
		public void setClassId(int classId) {
			this.classId = classId;
		}
		public AssignmentDetails() {
			classDetails = new ClassDetails();
		}
		public ClassDetails getClassDetails() {
			return classDetails;
		}
		public void setClassDetails(ClassDetails classDetails) {
			this.classDetails = classDetails;
		}
		public ArrayList<ClassDetails> getClassdetails() {
			return classdetails;
		}
		public void setClassdetails(ArrayList<ClassDetails> classdetails) {
			this.classdetails = classdetails;
		}		
		 
		public int getAssgnmntId() {
			return assgnmntId;
		}
		public void setAssgnmntId(int assgnmntId) {
			this.assgnmntId = assgnmntId;
		}
		public int getProjectId() {
			return projectId;
		}
		public void setProjectId(int projectId) {
			this.projectId = projectId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDateCreated() {
			return dateCreated;
		}
		public void setDateCreated(String dateCreated) {
			this.dateCreated = dateCreated;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String[] getRemoveassign() {
			return removeassign;
		}
		public void setRemoveassign(String[] removeassign) {
			this.removeassign = removeassign;
		}
		public String getDirections() {
			return directions;
		}
		public void setDirections(String directions) {
			this.directions = directions;
		}
		public int getSelectedclassIDtoadd() {
			return selectedclassIDtoadd;
		}
		public void setSelectedclassIDtoadd(int selectedclassIDtoadd) {
			this.selectedclassIDtoadd = selectedclassIDtoadd;
		}
		public ArrayList<CourseDetails> getExpertmodels() {
			return expertmodels;
		}
		public void setExpertmodels(ArrayList<CourseDetails> expertmodels) {
			this.expertmodels = expertmodels;
		}
		public int getChoosenmodelID() {
			return choosenmodelID;
		}
		public void setChoosenmodelID(int choosenmodelID) {
			this.choosenmodelID = choosenmodelID;
		}
		public CourseDetails getRefmodel() {
			return refmodel;
		}
		public void setRefmodel(CourseDetails refmodel) {
			this.refmodel = refmodel;
		}
		public ClassDetails getAddedclass() {
			return addedclass;
		}
		public void setAddedclass(ClassDetails addedclass) {
			this.addedclass = addedclass;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
}
