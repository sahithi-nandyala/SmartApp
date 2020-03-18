package edu.smart.pojo;

import java.util.ArrayList;

public class GraphVizDetails {

	ArrayList<String> keyConcepts;
	ArrayList<String> concepts;
	double[][] adjacencyMatrix;
	ArrayList<ArrayList<String>> pathList;
	
	public GraphVizDetails(){
		keyConcepts = new ArrayList<String>();
		concepts = new ArrayList<String>();
		pathList = new ArrayList<ArrayList<String>>();
	}
	
	public ArrayList<ArrayList<String>> getPathList() {
		return pathList;
	}

	public void setPathList(ArrayList<ArrayList<String>> pathList) {
		this.pathList = pathList;
	}

	public ArrayList<String> getKeyConcepts() {
		return keyConcepts;
	}
	public void setKeyConcepts(ArrayList<String> keyConcepts) {
		this.keyConcepts = keyConcepts;
	}
	public ArrayList<String> getConcepts() {
		return concepts;
	}
	public void setConcepts(ArrayList<String> concepts) {
		this.concepts = concepts;
	}
	public double[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
	public void setAdjacencyMatrix(double[][] adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
	}
	
}
