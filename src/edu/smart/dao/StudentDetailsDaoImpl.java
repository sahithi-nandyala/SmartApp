package edu.smart.dao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import edu.smart.controller.CourseDetailsController;
import edu.smart.model.CourseDetailsModel;
import edu.smart.model.StudentDashboardModel;
import edu.smart.pojo.AssignmentDetails;
import edu.smart.pojo.ClassDetails;
import edu.smart.pojo.CourseDetails;

public class StudentDetailsDaoImpl {

	static Logger log = Logger.getLogger(CourseDetailsController.class.getName());
	JdbcTemplate template; 
	
	CourseDetailsDaoImpl courseDetailsDaoImpl;
	
	public void setTemplate(JdbcTemplate template) {  
	    this.template = template;  
	}
	
	public ArrayList<AssignmentDetails> getassignments(int classid) {
		
		ArrayList<AssignmentDetails> AD=new ArrayList<AssignmentDetails>();
		AssignmentDetails assign;
		SqlRowSet sqlRowset = template.queryForRowSet("SELECT * FROM classassignments where classid="+classid+";");
		while(sqlRowset.next()) {
			assign=new AssignmentDetails();
			int assignmentid=sqlRowset.getInt("assgntid");
			assign.setAssgnmntId(assignmentid);
			SqlRowSet sqlRowset2 = template.queryForRowSet("SELECT * FROM assignment where assgntid='"+assignmentid+"';");
			if(sqlRowset2.next()) {
				assign.setTitle(sqlRowset2.getString("title"));
				assign.setDirections(sqlRowset2.getString("directions"));
				assign.setDescription(sqlRowset2.getString("description"));
				assign.setStatus(sqlRowset2.getString("as_status"));
				
			}
			if(assign.getStatus().equals("open"))
			{
				SqlRowSet sqlRowset1 = template.queryForRowSet("Select * from choosenexpert where assignmentid='"+assignmentid+"'");
				if(sqlRowset1.next()) {
					int refmodelid=sqlRowset1.getInt("choosenmodelid");
					assign.setChoosenmodelID(refmodelid);
					if(refmodelid!=0)
					{
						AD.add(assign);
					}
					
				}
			}
		}
		
		return AD;
	}
	
	public int saveaddedclasses(StudentDashboardModel studentmodel)
	{
		SqlRowSet sqlRowset = template.queryForRowSet("SELECT * FROM class_student where studentid="+studentmodel.getStudentid()+" AND classid="+studentmodel.getInputclassID()+";");
		if(sqlRowset.next()) {
			return 2;
		}
		StringBuilder sqlAppend= new StringBuilder();
		//String userid=userDetails.getUserType().substring(0, 3)+"_"+userDetails.getUserName();
		
		sqlAppend.append("INSERT INTO class_student (studentid, classid) VALUES (");
		sqlAppend.append(studentmodel.getStudentid()  + ",");
		sqlAppend.append(studentmodel.getInputclassID()+")");
		
		return template.update(sqlAppend.toString());
	}
	
	public int deleteclass(StudentDashboardModel studentmodel)
	{
		StringBuilder sqlAppend= new StringBuilder();

		sqlAppend.append("delete from class_student where studentid="+studentmodel.getStudentid()+" AND classid="+studentmodel.getInputclassID()+";");
	
		return template.update(sqlAppend.toString());
	}
	public Map <ClassDetails,ArrayList<AssignmentDetails>> getclasses(int studentid)
	{
		ArrayList<AssignmentDetails> AD;
		Map <ClassDetails,ArrayList<AssignmentDetails>> g=new <ClassDetails,ArrayList<AssignmentDetails>> HashMap();	  	
		SqlRowSet sqlRowset = template.queryForRowSet("SELECT * FROM class_student where studentid="+studentid+";");
		while(sqlRowset.next()) {
			int classid=sqlRowset.getInt("classid");
			
			ClassDetails classdetails=new ClassDetails();
			SqlRowSet sqlRowset1 = template.queryForRowSet("select * from class where classid='" + classid + "'");
			if(sqlRowset1.next())
			{
				classdetails.setClassId(classid);
				classdetails.setClassName(sqlRowset1.getString("classname"));
		    	classdetails.setDescription(sqlRowset1.getString("description"));
			}
			
			AD=new ArrayList<AssignmentDetails>();
			AD.addAll(getassignments(classid));
			g.put(classdetails, AD);
		}
		return g;
	}
	
	public ArrayList<AssignmentDetails> getrecentsubmittedassignments(int studentid)
	{
		ArrayList<AssignmentDetails> assgnid=new ArrayList<AssignmentDetails>();
		AssignmentDetails AD;
		int flag=3;
		SqlRowSet sqlRowset = template.queryForRowSet("SELECT * FROM classassignments where classid in (SELECT classid FROM class_student where studentid="+studentid+");");
		while(sqlRowset.next() && flag!=0) {
			AD=new AssignmentDetails();
			AD.setAssgnmntId(sqlRowset.getInt("assgntid"));
		//	AD.setDateCreated(sqlRowset.getString("createddate").split(" ")[0]);
			SqlRowSet sqlRowset1 = template.queryForRowSet("SELECT * FROM assignment where assgntid="+AD.getAssgnmntId()+" order by createdDate;");
			if(sqlRowset1.next())
			{
				AD.setTitle(sqlRowset1.getString("title"));
				AD.setDateCreated(sqlRowset1.getString("createdDate").split(" ")[0]);
			}
			assgnid.add(AD);	
			flag--;
		}
		
		return assgnid;
	}
	public AssignmentDetails getdisplayingassignment(int assignid)
	{
		AssignmentDetails assign=new AssignmentDetails();
		SqlRowSet sqlRowset2 = template.queryForRowSet("SELECT * FROM assignment where assgntid='"+assignid+"';");
		if(sqlRowset2.next()) {
			assign.setTitle(sqlRowset2.getString("title"));
			assign.setDirections(sqlRowset2.getString("directions"));
			assign.setDescription(sqlRowset2.getString("description"));
			assign.setStatus(sqlRowset2.getString("as_status"));	
			assign.setType(sqlRowset2.getString("type"));
			
			SqlRowSet sqlRowset1 = template.queryForRowSet("Select * from choosenexpert where assignmentid='"+assignid+"'");
			if(sqlRowset1.next()) {
				int refmodelid=sqlRowset1.getInt("choosenmodelid");
				assign.setChoosenmodelID(refmodelid);
			}
		}
		
		return assign;
	}
	
	public int getstudentid(int studentmodelid)
	{
		int studentid=0;
		SqlRowSet sqlRowset = template.queryForRowSet("SELECT studentid FROM studentresponses where studentmodelid="+studentmodelid+";");
		if(sqlRowset.next())
		{
			studentid=sqlRowset.getInt("studentid");
		}
		return studentid;
	}
	public ArrayList<CourseDetailsModel> getstudentresponses(int assignid, int studentid)
	{
		ArrayList<CourseDetailsModel> allresponses=new ArrayList<CourseDetailsModel>();
		CourseDetailsModel responses;
		int r=0;
		SqlRowSet sqlRowset = template.queryForRowSet("SELECT * FROM studentresponses where studentid="+studentid+" AND assignid="+assignid+" order by createddate ASC;");

		while(sqlRowset.next()) {
			responses=new CourseDetailsModel();
			
			responses.setStudentresponseid(sqlRowset.getInt("studentmodelid"));
			responses.setCreatedDateTime(sqlRowset.getString("createddate"));
			SqlRowSet sqlRowset2 = template.queryForRowSet("SELECT * FROM student_model where modelID="+responses.getStudentresponseid()+";");
			if(sqlRowset2.next())
			{
				responses.setText(sqlRowset2.getString("text"));
				responses.setExpertID(sqlRowset2.getInt("expertmodelid"));

			}
			SqlRowSet sqlRowset3 = template.queryForRowSet("SELECT * FROM student_model2 where modelID="+responses.getStudentresponseid()+";");
			if(sqlRowset3.next())
			{
				responses.setRecallkeyconcepts(RoundTo2Decimals(sqlRowset3.getDouble("recallC")));
				responses.setRecallKeylinks(RoundTo2Decimals(sqlRowset3.getDouble("recallP")));
			}
			r++;
			responses.setTitle("R"+r);
			allresponses.add(responses);
		}
		return allresponses;
	}
	
	double RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
    return Double.valueOf(df2.format(val));
	}
}
