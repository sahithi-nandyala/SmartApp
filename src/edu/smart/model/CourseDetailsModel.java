package edu.smart.model;

import java.util.ArrayList;

import edu.smart.pojo.AssignmentDetails;
import edu.smart.pojo.CourseDetails;
/**
 * @author shyamabs
 *
 */
public class CourseDetailsModel {
	
	private int studentId;
	private String title;
	private int expertID;
	private int studentresponseid;

	private int assgntID;
	private int threshold;
	private String centrality;
	private String termFrequency;
	private String concepts;
	private String relation;
	private String text;
	private String betweenness;
	private String closenessCentrality;
	private String pageRank;
	private String eigenVectorCentrality;
	private String clusteringcoef;
	private String DBAdjacencyMatrix;
	private String problemStatement;
	private String topic;
	private String model;
	private CourseDetails referencedetails;	
	private int noOfConcepts;
	private int noOfRelations;
	private int noOfKeyLinks;
	private int noOfWords;
	private int noOfKeyConcepts;
	private int subgraphs;
	//private int linkreduction;
	private double avgdegree;	
	private double meandistance;
	private double density;
	private double diameter;	
	private double Subgraph;
	private double recallkeyconcepts;
	private double recallKeylinks;
	private double sm_NOofconcepts;
	private double sm_NoOfRelations;
	private double sm_avgdegree;
	private double sm_Subgraph;
	private double sm_density;
	private double sm_diameter;
	private double sm_meandistance;
	private double sm_connected;
	private double sm_conceptualmatching;
	private double sm_propositionalmatching;
	private double sm_Balancedmatching;
	private ArrayList<String> keyConcepts;
	private ArrayList<ArrayList<String>> keyRelations;
	private ArrayList<String> bConcepts;
	private ArrayList<String> missingConcepts;
	private double[][] adjacencyMatrix;
	private ArrayList<String> allConceptList;
	private double keymatrix[][];
	private ArrayList<String> pathList;
	private CourseDetailsModel expert;
	private ArrayList<String> commonLinks;
	private ArrayList<String> totalExpertLinks;
	private String nodes;
	private String links;
	private String expertBaseNodes;
	private String studentBaseNodes;
	private String expertBaseLinks;
	private String studentBaseLinks;
	private String studentNodes;
	private String studentLinks;
	private String singleStudentNodes;
	private String singleStudentLinks;
	private String singleExpertNodes;
	private String singleExpertLinks;
	private String studentFeedbackNodes;
	private String studentFeedbackLinks;
	private ArrayList<String> commonKeyConcepts;
	private ArrayList<String> wrongLinks;
	private ArrayList<String> missingLinks;
	private ArrayList<String> missingLinksForDisplay;
	private String createdDateTime;
	private AssignmentDetails assignmentDetails;
	private String concepthighlightpairs;
	private ArrayList<CourseDetailsModel> allresponses;
	
	
	public ArrayList<String> getMissingLinksForDisplay() {
		return missingLinksForDisplay;
	}
	public void setMissingLinksForDisplay(ArrayList<String> missingLinksForDisplay) {
		this.missingLinksForDisplay = missingLinksForDisplay;
	}
	public AssignmentDetails getAssignmentDetails() {
		return assignmentDetails;
	}
	public void setAssignmentDetails(AssignmentDetails assignmentDetails) {
		this.assignmentDetails = assignmentDetails;
	}
	public int getNoOfKeyConcepts() {
		return noOfKeyConcepts;
	}
	public void setNoOfKeyConcepts(int noOfKeyConcepts) {
		this.noOfKeyConcepts = noOfKeyConcepts;
	}
	public int getNoOfWords() {
		return noOfWords;
	}
	public void setNoOfWords(int noOfWords) {
		this.noOfWords = noOfWords;
	}
	public int getNoOfKeyLinks() {
		return noOfKeyLinks;
	}
	public void setNoOfKeyLinks(int noOfKeyLinks) {
		this.noOfKeyLinks = noOfKeyLinks;
	}
	public String getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public ArrayList<String> getTotalExpertLinks() {
		return totalExpertLinks;
	}
	public void setTotalExpertLinks(ArrayList<String> totalExpertLinks) {
		this.totalExpertLinks = totalExpertLinks;
	}
	public double getSm_NOofconcepts() {
		return sm_NOofconcepts;
	}
	public void setSm_NOofconcepts(double sm_NOofconcepts) {
		this.sm_NOofconcepts = sm_NOofconcepts;
	}
	public double getSm_NoOfRelations() {
		return sm_NoOfRelations;
	}
	public void setSm_NoOfRelations(double sm_NoOfRelations) {
		this.sm_NoOfRelations = sm_NoOfRelations;
	}
	public double getSm_avgdegree() {
		return sm_avgdegree;
	}
	public void setSm_avgdegree(double sm_avgdegree) {
		this.sm_avgdegree = sm_avgdegree;
	}
	public double getSm_Subgraph() {
		return sm_Subgraph;
	}
	public void setSm_Subgraph(double sm_Subgraph) {
		this.sm_Subgraph = sm_Subgraph;
	}
	public double getSm_density() {
		return sm_density;
	}
	public void setSm_density(double sm_density) {
		this.sm_density = sm_density;
	}
	public double getSm_diameter() {
		return sm_diameter;
	}
	public void setSm_diameter(double sm_diameter) {
		this.sm_diameter = sm_diameter;
	}
	public double getSm_meandistance() {
		return sm_meandistance;
	}
	public void setSm_meandistance(double sm_meandistance) {
		this.sm_meandistance = sm_meandistance;
	}
	public double getSm_connected() {
		return sm_connected;
	}
	public void setSm_connected(double sm_connected) {
		this.sm_connected = sm_connected;
	}
	public double getSm_conceptualmatching() {
		return sm_conceptualmatching;
	}
	public void setSm_conceptualmatching(double sm_conceptualmatching) {
		this.sm_conceptualmatching = sm_conceptualmatching;
	}
	public double getSm_propositionalmatching() {
		return sm_propositionalmatching;
	}
	public void setSm_propositionalmatching(double sm_propositionalmatching) {
		this.sm_propositionalmatching = sm_propositionalmatching;
	}
	public double getSm_Balancedmatching() {
		return sm_Balancedmatching;
	}
	public void setSm_Balancedmatching(double sm_Balancedmatching) {
		this.sm_Balancedmatching = sm_Balancedmatching;
	}
	
	public ArrayList<String> getMissingLinks() {
		return missingLinks;
	}
	public void setMissingLinks(ArrayList<String> missingLinks) {
		this.missingLinks = missingLinks;
	}
	public int getAssgntID() {
		return assgntID;
	}
	public void setAssgntID(int assgntID) {
		this.assgntID = assgntID;
	}
	
	public ArrayList<String> getWrongLinks() {
		return wrongLinks;
	}
	public void setWrongLinks(ArrayList<String> wrongLinks) {
		this.wrongLinks = wrongLinks;
	}
	public String getStudentFeedbackNodes() {
		return studentFeedbackNodes;
	}
	public void setStudentFeedbackNodes(String studentFeedbackNodes) {
		this.studentFeedbackNodes = studentFeedbackNodes;
	}
	public String getStudentFeedbackLinks() {
		return studentFeedbackLinks;
	}
	public void setStudentFeedbackLinks(String studentFeedbackLinks) {
		this.studentFeedbackLinks = studentFeedbackLinks;
	}
	public ArrayList<String> getCommonKeyConcepts() {
		return commonKeyConcepts;
	}
	public void setCommonKeyConcepts(ArrayList<String> commonKeyConcepts) {
		this.commonKeyConcepts = commonKeyConcepts;
	}
	public ArrayList<String> getCommonLinks() {
		return commonLinks;
	}
	public void setCommonLinks(ArrayList<String> commonLinks) {
		this.commonLinks = commonLinks;
	}
	public String getSingleStudentNodes() {
		return singleStudentNodes;
	}
	public void setSingleStudentNodes(String singleStudentNodes) {
		this.singleStudentNodes = singleStudentNodes;
	}
	public String getSingleStudentLinks() {
		return singleStudentLinks;
	}
	//
	public void setSingleStudentLinks(String singleStudentLinks) {
		this.singleStudentLinks = singleStudentLinks;
	}
	public String getSingleExpertNodes() {
		return singleExpertNodes;
	}
	public void setSingleExpertNodes(String singleExpertNodes) {
		this.singleExpertNodes = singleExpertNodes;
	}
	public String getSingleExpertLinks() {
		return singleExpertLinks;
	}
	public void setSingleExpertLinks(String singleExpertLinks) {
		this.singleExpertLinks = singleExpertLinks;
	}
	public String getStudentNodes() {
		return studentNodes;
	}
	public void setStudentNodes(String studentNodes) {
		this.studentNodes = studentNodes;
	}
	public String getStudentLinks() {
		return studentLinks;
	}
	public void setStudentLinks(String studentLinks) {
		this.studentLinks = studentLinks;
	}
	public String getExpertBaseNodes() {
		return expertBaseNodes;
	}
	public void setExpertBaseNodes(String expertBaseNodes) {
		this.expertBaseNodes = expertBaseNodes;
	}
	public String getStudentBaseNodes() {
		return studentBaseNodes;
	}
	public void setStudentBaseNodes(String studentBaseNodes) {
		this.studentBaseNodes = studentBaseNodes;
	}
	public String getExpertBaseLinks() {
		return expertBaseLinks;
	}
	public void setExpertBaseLinks(String expertBaseLinks) {
		this.expertBaseLinks = expertBaseLinks;
	}
	public String getStudentBaseLinks() {
		return studentBaseLinks;
	}
	public void setStudentBaseLinks(String studentBaseLinks) {
		this.studentBaseLinks = studentBaseLinks;
	}
	public String getNodes() {
		return nodes;
	}
	public void setNodes(String nodes) {
		this.nodes = nodes;
	}
	public String getLinks() {
		return links;
	}
	public void setLinks(String links) {
		this.links = links;
	}
	public CourseDetailsModel getExpert() {
		return expert;
	}
	public void setExpert(CourseDetailsModel expert) {
		this.expert = expert;
	}
	public ArrayList<String> getMissingConcepts() {
		return missingConcepts;
	}
	public void setMissingConcepts(ArrayList<String> missingConcepts) {
		this.missingConcepts = missingConcepts;
	}
	public ArrayList<String> getPathList() {
		return pathList;
	}
	public void setPathList(ArrayList<String> pathList) {
		this.pathList = pathList;
	}
	public double[][] getKeymatrix() {
		return keymatrix;
	}
	public void setKeymatrix(double[][] keymatrix) {
		this.keymatrix = keymatrix;
	}
	public ArrayList<String> getAllConceptList() {
		return allConceptList;
	}
	public void setAllConceptList(ArrayList<String> allConceptList) {
		this.allConceptList = allConceptList;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTermFrequency() {
		return termFrequency;
	}
	public void setTermFrequency(String termFrequency) {
		this.termFrequency = termFrequency;
	}
	
	public String getConcepts() {
		return concepts;
	}
	public void setConcepts(String concepts) {
		this.concepts = concepts;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getBetweenness() {
		return betweenness;
	}
	public void setBetweenness(String betweenness) {
		this.betweenness = betweenness;
	}
	public String getClosenessCentrality() {
		return closenessCentrality;
	}
	public void setClosenessCentrality(String closenessCentrality) {
		this.closenessCentrality = closenessCentrality;
	}
	public String getPageRank() {
		return pageRank;
	}
	public void setPageRank(String pageRank) {
		this.pageRank = pageRank;
	}
	public String getEigenVectorCentrality() {
		return eigenVectorCentrality;
	}
	public void setEigenVectorCentrality(String eigenVectorCentrality) {
		this.eigenVectorCentrality = eigenVectorCentrality;
	}
	
	public int getNoOfConcepts() {
		return noOfConcepts;
	}
	public void setNoOfConcepts(int noOfConcepts) {
		this.noOfConcepts = noOfConcepts;
	}
	public int getNoOfRelations() {
		return noOfRelations;
	}
	public void setNoOfRelations(int noOfRelations) {
		this.noOfRelations = noOfRelations;
	}
	public int getSubgraphs() {
		return subgraphs;
	}
	public void setSubgraphs(int subgraphs) {
		this.subgraphs = subgraphs;
	}
	
//	public int getLinkReduction()
//	{
//		return linkreduction;
//	}
//	public void setLinkReduction(int linkreduction)
//	{
//		this.linkreduction=linkreduction;
//	}
	
	
	public double getAvgdegree() {
		return avgdegree;
	}
	public void setAvgdegree(double avgdegree) {
		this.avgdegree = avgdegree;
	}
	public double getMeandistance() {
		return meandistance;
	}
	public void setMeandistance(double meandistance) {
		this.meandistance = meandistance;
	}
	public double getDensity() {
		return density;
	}
	public void setDensity(double density) {
		this.density = density;
	}
	public double getDiameter() {
		return diameter;
	}
	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}
	public double getSubgraph() {
		return Subgraph;
	}
	public void setSubgraph(double connectedness) {
		this.Subgraph = connectedness;
	}
	
	public CourseDetailsModel(){
		keyConcepts = new ArrayList<String>();
		keyRelations = new ArrayList<ArrayList<String>>();
		allConceptList = new ArrayList<String>();
		bConcepts = new ArrayList<String>();
		pathList= new ArrayList<String>();
		commonLinks= new ArrayList<String>();
		commonKeyConcepts= new ArrayList<String>();
		wrongLinks= new ArrayList<String>(); 
		missingLinks = new ArrayList<String>(); 
		missingLinksForDisplay = new ArrayList<String>(); 
		missingConcepts = new ArrayList<String>(); 
		totalExpertLinks = new ArrayList<String>(); 
		noOfKeyLinks = 0;
		assignmentDetails = new AssignmentDetails();
	}
	

	
	public ArrayList<ArrayList<String>> getKeyRelations() {
		return keyRelations;
	}
	public void setKeyRelations(ArrayList<ArrayList<String>> keyRelations) {
		this.keyRelations = keyRelations;
	}
	public ArrayList<String> getbConcepts() {
		return bConcepts;
	}
	public void setbConcepts(ArrayList<String> bConcepts) {
		this.bConcepts = bConcepts;
	}
	public ArrayList<String> getKeyConcepts() {
		return keyConcepts;
	}
	public void setKeyConcepts(ArrayList<String> keyConcepts) {
		this.keyConcepts = keyConcepts;
	}
	
	public double[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
	public void setAdjacencyMatrix(double[][] adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
	}
	public int getExpertID() {
		return expertID;
	}
	public void setExpertID(int expertID) {
		this.expertID = expertID;
	}

	public String getDBAdjacencyMatrix() {
		return DBAdjacencyMatrix;
	}
	public void setDBAdjacencyMatrix(String dBAdjacencyMatrix) {
		DBAdjacencyMatrix = dBAdjacencyMatrix;
	}

	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getProblemStatement() {
		return problemStatement;
	}
	public void setProblemStatement(String problemStatement) {
		this.problemStatement = problemStatement;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}

	public double getRecallkeyconcepts() {
		return recallkeyconcepts;
	}
	public void setRecallkeyconcepts(double recallkeyconcepts) {
		this.recallkeyconcepts = recallkeyconcepts;
	}
	public double getRecallKeylinks() {
		return recallKeylinks;
	}
	public void setRecallKeylinks(double recallKeylinks) {
		this.recallKeylinks = recallKeylinks;
	}

	public CourseDetails getReferencedetails() {
		return referencedetails;
	}
	public void setReferencedetails(CourseDetails referencedetails) {
		this.referencedetails = referencedetails;
	}
	public int getStudentresponseid() {
		return studentresponseid;
	}
	public void setStudentresponseid(int studentresponseid) {
		this.studentresponseid = studentresponseid;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	public String getCentrality() {
		return centrality;
	}
	public void setCentrality(String centrality) {
		this.centrality = centrality;
	}
	public String getClusteringcoef() {
		return clusteringcoef;
	}
	public void setClusteringcoef(String clusteringcoef) {
		this.clusteringcoef = clusteringcoef;
	}
	public String getConcepthighlightpairs() {
		return concepthighlightpairs;
	}
	public void setConcepthighlightpairs(String concepthighlightpairs) {
		this.concepthighlightpairs = concepthighlightpairs;
	}
	public ArrayList<CourseDetailsModel> getAllresponses() {
		return allresponses;
	}
	public void setAllresponses(ArrayList<CourseDetailsModel> allresponses) {
		this.allresponses = allresponses;
	}


}
