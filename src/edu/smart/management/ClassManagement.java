package edu.smart.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import edu.smart.dao.CourseDetailsDaoImpl;
import edu.smart.model.StudentManagementModel;
import edu.smart.pojo.ClassDetails;
import edu.smart.pojo.CourseDetails;
import edu.smart.pojo.UserDetails;
import edu.smart.util.AppendUtil;

public class ClassManagement {
	
	@Autowired
	CourseDetailsDaoImpl courseDetailsDaoImpl;
	
	public StudentManagementModel getStudents(ClassDetails classDetails) {
		StudentManagementModel studentManagementModel = new StudentManagementModel();
		studentManagementModel=courseDetailsDaoImpl.getStudents(classDetails.getClassId());
		return studentManagementModel;
	}
	
	public String  addStudent(UserDetails studentDetails) {
		String userid = "";//courseDetailsDaoImpl.addStudent(studentDetails);
		return userid;
	}
	
	public int addStudentToClass(UserDetails studentDetails, int classId) {
		int status=0;
		status = courseDetailsDaoImpl.addStudentToClass(studentDetails,classId);
		return status;
	}
	
	public int removeStudentFromClass(UserDetails studentDetails, int classId) {
		int status=0;
		//status = courseDetailsDaoImpl.removeStudentFromClass(studentDetails, classId);
		return status;
	}
	

}
