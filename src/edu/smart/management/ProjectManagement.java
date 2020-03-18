package edu.smart.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import edu.smart.pojo.ClassDetails;
import edu.smart.pojo.CourseDetails;
import edu.smart.pojo.UserDetails;
import edu.smart.util.AppendUtil;
public class ProjectManagement {

JdbcTemplate template; 
	
	public void setTemplate(JdbcTemplate template) {  
	    this.template = template;  
	}
	
	public int update(ClassDetails course) {
		return 0;
	}
	
	public void copyuserdetails()
	{
		
	}
	public void getclasses(int teacherId)
	{
		
		
	}
}
