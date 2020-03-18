package edu.smart.controller;

import java.util.ArrayList;
import java.util.Collections;

//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.smart.bo.Runner;
import edu.smart.bo.SmartTestBoImpl;
import edu.smart.dao.CourseDetailsDaoImpl;
import edu.smart.dao.StudentDetailsDaoImpl;
import edu.smart.management.InstructorManagement;
import edu.smart.management.StudentManagement;
import edu.smart.model.CourseDetailsModel;
import edu.smart.model.UserDetailsModel;
import edu.smart.pojo.CourseDetails;
import edu.smart.pojo.UserDetails;
import edu.smart.util.AppendUtil;
import edu.smart.util.DisplayGraph;

@Controller

public class CourseDetailsController {
	
	static Logger log = Logger.getLogger(CourseDetailsController.class.getName());
	
	@Autowired
	SmartTestBoImpl smartTestBoImpl;
	
/*	@Autowired
	CourseDetailsModel courseDetailsModel;*/
	
	@Autowired
	DisplayGraph displayGraph;
	
	@Autowired
	UserDetailsModel userDetailsModel;
	
	@Autowired
	UserDetails userDetails;

	@Autowired
	CourseDetailsDaoImpl courseDetailsDaoImpl;
	
	@Autowired
	StudentDetailsDaoImpl studentdetailsdaoimpl;
	
	AppendUtil appendutil;
	
	@RequestMapping(value = "/studentSolution",method = RequestMethod.GET)  
    public ModelAndView showForm(@ModelAttribute("courseDetails") CourseDetails courseDetails){  
		ModelAndView mav = new ModelAndView("Course/FormDetails");
	    return mav;
    }  
	
	/*@RequestMapping(value = "/expertSolution", method = RequestMethod.POST)  
    public ModelAndView expertSolution(@ModelAttribute("courseDetails") CourseDetails courseDetails){ 
		
		//courseDetailsModel.setProblemTopic(courseDetails.getProblemTopic());		
		courseDetailsModel.setTopic(courseDetails.getTopic());
		courseDetailsModel.setProblemStatement(courseDetails.getProblemStatement());
		ModelAndView mav = new ModelAndView("Course/ExpertForm","modelname",courseDetailsModel);
	    return mav;
    } */
	
	@RequestMapping(value = "/problemStatement",method = RequestMethod.GET)  
    public ModelAndView ShowProblemStatement(@ModelAttribute("courseDetails") CourseDetails courseDetails){  
		ModelAndView mav = new ModelAndView("Course/ProblemStatement");
	    return mav;
    }
	
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)  
    public ModelAndView feedback(HttpServletRequest request,HttpServletResponse response, @ModelAttribute("courseDetails") CourseDetails courseDetails){  
		
		HttpSession session = request.getSession();		
		int assignid = (int) session.getAttribute("assignid");
		log.info("logged info successfully");
		CourseDetailsModel courseDetailsModel = new CourseDetailsModel();
		ModelAndView mav;
		if(null!=smartTestBoImpl){
			courseDetailsModel=smartTestBoImpl.smartTestNplImpl(courseDetails,"expert");
		}
		
		//courseDetailsModel=courseDetailsDaoImpl.ExpertModelValues(courseDetails.getModelID());
		courseDetailsModel.setAssgntID(assignid);
		if(null !=courseDetailsModel.getExpert()) {
			courseDetailsModel=displayGraph.createJsonExpertBase(courseDetailsModel);
			//mav = new ModelAndView("Course/Graph","courseDetails",courseDetailsModel);
		}
		else {
			courseDetailsModel=displayGraph.createJsonExpert(courseDetailsModel);
			//mav = new ModelAndView("Course/Graph","courseDetails",courseDetailsModel);
		}
		log.info("before set expert id");
		
		courseDetailsModel.setExpertID(courseDetailsDaoImpl.save(courseDetailsModel,courseDetails.getModel().toString()));
	 	//redirectAttributes.addFlashAttribute("model",courseDetailsModel);
	 	
		 session.setAttribute("expertid", courseDetailsModel.getExpertID());		  
		 
		mav = new ModelAndView("redirect:concepts");
	    return mav;
    }
	
	/*@RequestMapping(value = "/displayexpertgraph", method = RequestMethod.POST)*/
	@RequestMapping(value = "/displayexpertgraph",method = RequestMethod.POST)
	public ModelAndView displayexpertgraph(HttpServletRequest request,HttpServletResponse response, @ModelAttribute("courseDetails") CourseDetails courseDetails)  
	{
		
		//session.setAttribute("expertid",courseDetails.getModelID());
		CourseDetailsModel courseDetailsModel = new CourseDetailsModel();
	  	int expertid = courseDetails.getModelID();
		//int expertid=1;
	  	System.out.println("expertid: "+expertid);
	  	log.info("expertid: "+expertid);
	  	ModelAndView mav;
	  	appendutil=new AppendUtil();
	  	courseDetailsModel=courseDetailsDaoImpl.ExpertModelValues(expertid);
	  	StudentManagement studentManagement = new StudentManagement();
	  	courseDetailsModel.setText(appendutil.highlightkeyconcepts(courseDetailsModel));
	  	DisplayGraph displayGraph = new DisplayGraph();
	  	courseDetailsModel = displayGraph.createJsonExpert(courseDetailsModel);
	  	courseDetailsModel = displayGraph.createJsonExpertReduced(courseDetailsModel);
	  	courseDetailsModel = studentManagement.setExpertFeedbackPageValues(courseDetailsModel);
	  	/*CourseDetailsModel courseDetailsModel=courseDetailsDaoImpl.ExpertModelValues(expertid);*/
	  	courseDetailsModel.setExpertID(expertid);
	  	mav = new ModelAndView("Course/ExpertModelGraph","modelname",courseDetailsModel);
	  	//mav = new ModelAndView("Course/ExpertModelGraph");
	    return mav;
	}
		
	
		@RequestMapping(value = "/concepts",method = RequestMethod.GET)  
	    public ModelAndView showconcepts(HttpServletRequest request,HttpServletResponse response, @ModelAttribute("courseDetails") CourseDetails courseDetails){  
			HttpSession session = request.getSession();
		  	int expertid = (int) session.getAttribute("expertid");
		  	appendutil=new AppendUtil();
			CourseDetailsModel courseDetailsModel = new CourseDetailsModel();
		   	courseDetailsModel = courseDetailsDaoImpl.ExpertModelValues(expertid);	    
		   	ArrayList<String> Keyconcepts=courseDetailsModel.getKeyConcepts();
		   	ArrayList<String> allconcepts=courseDetailsModel.getAllConceptList();
		   	ArrayList<String> basicConcepts=new ArrayList<String>();
		   	for(String a : allconcepts)
		   	{
		   		int c=0;
		   		for (String k: Keyconcepts)
		   		{
		   			if(a.equals(k))
		   			{
		   				c++;
		   			}
		   		}
		   		if(c==0)
		   		{
		   			basicConcepts.add(a);
		   		}
		   	}
		   	courseDetailsModel.setbConcepts(basicConcepts);
		   	courseDetailsModel.setText(appendutil.highlightkeyconcepts(courseDetailsModel));
		   	courseDetailsModel.setExpertID(expertid);
			ModelAndView mav = new ModelAndView("Course/Concepts","modelname",courseDetailsModel);
		    return mav;
	    }
	

	  @RequestMapping(value = "/saveConcepts", method = RequestMethod.POST)
	  public String saveConcepts(@RequestParam(value="myArrayk[]") String[] myArrayk, @RequestParam(value="expid") int expid,HttpServletRequest request,HttpServletResponse response) {
			CourseDetailsModel courseDetailsModel = new CourseDetailsModel();
		  HttpSession session = request.getSession();
		  	int expertid = (int) session.getAttribute("expertid");
		  System.out.println(expertid);

		  ArrayList<String> Keyconcepts=new ArrayList<String>();
		  Collections.addAll(Keyconcepts, myArrayk);
		  courseDetailsModel=courseDetailsDaoImpl.ExpertModelValues(expertid);
		  System.out.println("Before pathlist: "+courseDetailsModel.getPathList());
		  courseDetailsModel.setKeyConcepts(Keyconcepts);
		  Runner runner = new Runner();
		  try {
			courseDetailsModel = runner.updatePathList(courseDetailsModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  courseDetailsDaoImpl.saveconcepts(expertid, courseDetailsModel);
		  System.out.println("After pathlist: "+courseDetailsModel.getPathList());
		//  courseDetailsModel=courseDetailsDaoImpl.ExpertModelValues(expertid);
		//  ModelAndView mav =new ModelAndView("Course/concepthighlightt","model",courseDetailsModel);
		  return "concepthighlightt";
	  }
	  
	 
	@RequestMapping(value = "/displayAssignment/displayStudentGraph", method = RequestMethod.POST)  
    public ModelAndView displayStudentGraph(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("courseDetails") CourseDetails courseDetails){  
		
	   	HttpSession session = request.getSession();
	  	int userId = (int) session.getAttribute("userid");
	  	int assignid=(int) session.getAttribute("assignid");
		
		CourseDetailsModel courseDetailsModel = new CourseDetailsModel();
		log.info("logged info successfully");
		
		courseDetails.setUserId(userId);
		courseDetailsModel.setStudentId(userId);
		courseDetailsModel.setAssignmentDetails(courseDetailsDaoImpl.getassignmentdetails(assignid));
		
		ModelAndView mav;
		if(null!=smartTestBoImpl){
			courseDetailsModel=smartTestBoImpl.smartTestNplImpl(courseDetails,"student");
		}
		courseDetailsModel=displayGraph.createJsonStudentFeedback(courseDetailsModel,1);
		courseDetailsModel=smartTestBoImpl.calculateRecall(courseDetailsModel);
		//courseDetailsModel=displayGraph.createJsonExpertBase(courseDetailsModel);
		//mav = new ModelAndView("Course/Graph","courseDetails",courseDetailsModel);
		
		//----codeline---232
		courseDetailsModel=displayGraph.createJsonExpert(courseDetailsModel);
		
		//mav = new ModelAndView("Course/Graph","courseDetails",courseDetailsModel);
		//courseDetailsModel=displayGraph.createJsonStudentBase(courseDetailsModel);
		
		
		courseDetailsModel=displayGraph.createJsonStudent(courseDetailsModel);
		courseDetailsModel=displayGraph.createJsonOneDistance(courseDetailsModel);
		
		//courseDetailsModel=displayGraph.createJsonOneDistance(courseDetailsModel.getExpert());
		//courseDetailsDaoImpl.save(courseDetailsModel,courseDetails.getModel().toString());
		int studentmodelid=courseDetailsDaoImpl.savestudentesponse(courseDetailsModel);
		
		courseDetailsDaoImpl.addresponseentry(userId, assignid, studentmodelid);
		courseDetailsModel.setAssignmentDetails(courseDetailsDaoImpl.getassignmentdetails(assignid));
		appendutil=new AppendUtil();
		courseDetailsModel.getExpert().setText(appendutil.highlightkeyconcepts(courseDetailsModel.getExpert()));
		//System.out.println("assignment title: "+courseDetailsModel.getAssignmentDetails().getTitle());
		
		courseDetailsModel.setText(appendutil.highlightkeyconceptsstudent(courseDetailsModel.getText(),courseDetailsModel.getExpert()));
		
		mav = new ModelAndView("Course/FeedbackStudent","modelname",courseDetailsModel);
	    return mav;
    }
	
	@RequestMapping(value = "/displayAssignment/displayresponse", method = RequestMethod.POST)  
    public ModelAndView displayresponse(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("courseDetails") CourseDetails courseDetails){  
		
	   	HttpSession session = request.getSession();
	  	int userId = (int) session.getAttribute("userid");
	  	int assignid=(int) session.getAttribute("assignid");
  		  	
		CourseDetailsModel courseDetailsModel = new CourseDetailsModel();
	  	courseDetailsModel.setAssgntID(assignid);
	  	courseDetailsModel.setAssignmentDetails(courseDetailsDaoImpl.getassignmentdetails(assignid));
		log.info("logged info successfully");
		
		courseDetails.setUserId(userId);
		courseDetailsModel.setStudentId(userId);
		
		courseDetailsModel.setStudentresponseid(courseDetails.getStudentresponseid());
	
		courseDetailsModel = courseDetailsDaoImpl.getStudentModelDetails(courseDetailsModel.getStudentresponseid(), courseDetailsModel);
		appendutil=new AppendUtil();
		courseDetailsModel.getExpert().setText(appendutil.highlightkeyconcepts(courseDetailsModel.getExpert()));
		
		courseDetailsModel.setText(appendutil.highlightkeyconceptsstudent(courseDetailsModel.getText(),courseDetailsModel.getExpert()));
		
		ModelAndView mav;
		courseDetailsModel=displayGraph.createJsonStudentFeedback(courseDetailsModel,0);
		courseDetailsModel=displayGraph.createJsonExpert(courseDetailsModel);
		courseDetailsModel=displayGraph.createJsonStudent(courseDetailsModel);

		System.out.println("getassignmentdetails"+courseDetails.getAssignmentID() );
		courseDetailsModel.setAssignmentDetails(courseDetailsDaoImpl.getassignmentdetails(assignid));
		//courseDetailsDaoImpl.addresponseentry(userId, assignid, studentmodelid);
		mav = new ModelAndView("Course/FeedbackStudent","modelname",courseDetailsModel);
	    return mav;
    }
	
	@RequestMapping(value = "displayresponsetoinstructor", method = RequestMethod.POST)  
    public ModelAndView displayresponsetoinstructor(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("courseDetails") CourseDetails courseDetails){  
		
	   	HttpSession session = request.getSession();
	  	int userId = (int) session.getAttribute("userid");
	  	int assignid=(int) session.getAttribute("assignid");
  		  	
		CourseDetailsModel courseDetailsModel = new CourseDetailsModel();
	  	courseDetailsModel.setAssgntID(assignid);
	  	courseDetailsModel.setAssignmentDetails(courseDetailsDaoImpl.getassignmentdetails(assignid));
		log.info("logged info successfully");
		
		courseDetails.setUserId(userId);
		courseDetailsModel.setStudentId(userId);
		
		courseDetailsModel.setStudentresponseid(courseDetails.getStudentresponseid());
	
		courseDetailsModel = courseDetailsDaoImpl.getStudentModelDetails(courseDetailsModel.getStudentresponseid(), courseDetailsModel);
		appendutil=new AppendUtil();
		courseDetailsModel.getExpert().setText(appendutil.highlightkeyconcepts(courseDetailsModel.getExpert()));
		
		courseDetailsModel.setText(appendutil.highlightkeyconceptsstudent(courseDetailsModel.getText(),courseDetailsModel.getExpert()));
		
		ModelAndView mav;
		courseDetailsModel=displayGraph.createJsonStudentFeedback(courseDetailsModel,0);
		courseDetailsModel=displayGraph.createJsonExpert(courseDetailsModel);
		courseDetailsModel=displayGraph.createJsonStudent(courseDetailsModel);

		System.out.println("getassignmentdetails"+courseDetails.getAssignmentID() );
		courseDetailsModel.setAssignmentDetails(courseDetailsDaoImpl.getassignmentdetails(assignid));
		int studentid=studentdetailsdaoimpl.getstudentid(courseDetailsModel.getStudentresponseid());
		courseDetailsModel.setAllresponses(studentdetailsdaoimpl.getstudentresponses(assignid, studentid));
		
		//courseDetailsDaoImpl.addresponseentry(userId, assignid, studentmodelid);
		mav = new ModelAndView("Course/InstructorFeedbackStudent","modelname",courseDetailsModel);
	    return mav;
    }
	
	@RequestMapping(value = "/displayStudentModelGraph", method = RequestMethod.POST)  
    public ModelAndView displayStudentModelGraph(){
		ModelAndView mav;
		CourseDetailsModel courseDetailsModel = new CourseDetailsModel();
		courseDetailsModel=displayGraph.createJsonStudentBase(courseDetailsModel);
		mav = new ModelAndView("Course/Graph","courseDetails",courseDetailsModel);
	    return mav;
	}
	
	@RequestMapping(value = "/getStudentResponseFeedback", method = RequestMethod.POST)  
    public ModelAndView getStudentResponseFeedback(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("courseDetails") CourseDetails courseDetails){  
		System.out.println("inside getStudent");
	   	HttpSession session = request.getSession();
	  	int userId = (int) session.getAttribute("userid");
		CourseDetailsModel courseDetailsModel = new CourseDetailsModel();
	  	courseDetailsModel.setStudentresponseid(courseDetails.getStudentresponseid());
	  	courseDetailsModel = courseDetailsDaoImpl.getStudentModelDetails(courseDetailsModel.getStudentresponseid(), courseDetailsModel);
	  	courseDetailsModel=displayGraph.createJsonStudentFeedback(courseDetailsModel,0);
		courseDetailsModel=displayGraph.createJsonExpert(courseDetailsModel);
		courseDetailsModel=displayGraph.createJsonStudent(courseDetailsModel);
		//courseDetailsModel=displayGraph.createJsonOneDistance(courseDetailsModel);
		courseDetailsModel.setAssignmentDetails(courseDetailsDaoImpl.getassignmentdetails(Integer.parseInt(courseDetails.getAssignmentID())));
		//courseDetailsDaoImpl.addresponseentry(userId, assignid, studentmodelid);
		appendutil=new AppendUtil();
		courseDetailsModel.getExpert().setText(appendutil.highlightkeyconcepts(courseDetailsModel.getExpert()));
		System.out.println("assignment title: "+courseDetailsModel.getAssignmentDetails().getTitle());
		
		courseDetailsModel.setText(appendutil.highlightkeyconceptsstudent(courseDetailsModel.getText(),courseDetailsModel.getExpert()));
		int studentid=studentdetailsdaoimpl.getstudentid(courseDetailsModel.getStudentresponseid());
		courseDetailsModel.setAllresponses(studentdetailsdaoimpl.getstudentresponses(Integer.parseInt(courseDetails.getAssignmentID()), studentid));
		ModelAndView mav = new ModelAndView("Course/InstructorFeedbackStudent","modelname",courseDetailsModel);
	    return mav;
	}
	
}

