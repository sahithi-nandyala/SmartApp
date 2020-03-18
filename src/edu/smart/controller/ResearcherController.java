package edu.smart.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import edu.smart.bo.SmartTestBoImpl;
import edu.smart.dao.CourseDetailsDaoImpl;
import edu.smart.dao.ManagementDaoImpl;
import edu.smart.dao.StudentDetailsDaoImpl;
import edu.smart.model.CourseDetailsModel;
import edu.smart.model.ProjectManagementModel;
import edu.smart.model.StudentDashboardModel;
import edu.smart.model.StudentManagementModel;
import edu.smart.model.UserDetailsModel;
import edu.smart.pojo.AssignmentDetails;
import edu.smart.pojo.ClassDetails;
import edu.smart.pojo.ClassStudentDetails;
import edu.smart.pojo.ProjectDetails;
import edu.smart.pojo.UserDetails;
import edu.smart.util.DisplayGraph;
import edu.smart.util.UserRegistration;

public class ResearcherController {
	
	static Logger log = Logger.getLogger(CourseDetailsController.class.getName());
	
	@Autowired
	SmartTestBoImpl smartTestBoImpl;
	
	@Autowired
	CourseDetailsModel courseDetailsModel;
	
	@Autowired
	DisplayGraph displayGraph;
	
	@Autowired
	CourseDetailsDaoImpl courseDetailsDaoImpl;
	
	@Autowired
	ManagementDaoImpl managementDaoImpl;
	
	@Autowired
	ClassDetails classDetails;
	
	@Autowired
	ClassStudentDetails classStudentDetails;
	
	@Autowired
	AssignmentDetails assignmentDetails;
	
	@Autowired
	ProjectDetails projectDetails;
	
	@Autowired
	UserDetailsModel userDetailsModel;
	
	@Autowired
	UserDetails userDetails;
	
	@Autowired
	StudentDetailsDaoImpl studentdetailsdaoimpl;
	
	@Autowired
	StudentDashboardModel studentdashboardmodel;
	
	StudentManagementModel studentmodel=new StudentManagementModel();	
	
	ProjectManagementModel  projectmanagement=new ProjectManagementModel();
	
	UserRegistration userRegistration = new UserRegistration();
	
	
	
	

}
