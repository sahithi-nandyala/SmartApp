package edu.smart.management;


import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import edu.smart.dao.CourseDetailsDaoImpl;
import edu.smart.model.CourseDetailsModel;
import edu.smart.model.StudentManagementModel;
import edu.smart.pojo.ClassDetails;
import edu.smart.pojo.MailDetails;
import edu.smart.pojo.UserDetails;

public class StudentManagement {
	
	
	public StudentManagementModel getStudents(ClassDetails classDetails, CourseDetailsDaoImpl courseDetailsDaoImpl) {
		StudentManagementModel studentManagementModel = new StudentManagementModel();
		studentManagementModel=courseDetailsDaoImpl.getStudents(classDetails.getClassId());
		studentManagementModel.getInstructordetails().setUserID(classDetails.getTeacherId());
		return studentManagementModel;
	}
	
	public StudentManagementModel getAllStudentsOfTeacherByClass(int instructorId, CourseDetailsDaoImpl courseDetailsDaoImpl,StudentManagementModel studentModel) {
		//StudentManagementModel studentManagementModel = new StudentManagementModel();
		studentModel=courseDetailsDaoImpl.getAllStudentsOfTeacherByClass(instructorId);
		studentModel.setNoOfClasses(studentModel.getClassStudentList().keySet().size());
		return studentModel;
	}
	
	public int  addStudent(UserDetails studentDetails, CourseDetailsDaoImpl courseDetailsDaoImpl) {
		int status=0;
		status = courseDetailsDaoImpl.addStudent(studentDetails);
		return status;
	}
	
	public int addStudentToClass(UserDetails studentDetails, int classId, CourseDetailsDaoImpl courseDetailsDaoImpl) {
		int status=0;
		status = courseDetailsDaoImpl.addStudentToClass(studentDetails,classId);
		return status;
	}
	
	
	public StudentManagementModel getClassesOfTeacher(UserDetails teacherDetails, CourseDetailsDaoImpl courseDetailsDaoImpl) {
		StudentManagementModel studentManagementModel = new StudentManagementModel();
		//System.out.println("teacherDetails.getUserID()"+teacherDetails.getUserID());
		studentManagementModel=courseDetailsDaoImpl.getClasses(teacherDetails.getUserID());
		studentManagementModel.getInstructordetails().setUserID(teacherDetails.getUserID());
		return studentManagementModel;
	}
	
	public int addClass(ClassDetails classDetails, CourseDetailsDaoImpl courseDetailsDaoImpl) {
		int status=0;
		status=courseDetailsDaoImpl.addClass(classDetails);
		return status;
	}
	
	public int removeClass(int teacherId, ClassDetails classDetails, CourseDetailsDaoImpl courseDetailsDaoImpl) {
		int status=0;
		status=courseDetailsDaoImpl.removeClassFromTeacher(teacherId, classDetails.getClassId());
		return status;
	}
	
	public int removeClasses(int teacherId, String[] classIds, CourseDetailsDaoImpl courseDetailsDaoImpl) {
		int status=0;
		for(int i=0; i<classIds.length;i++) {
			status=courseDetailsDaoImpl.removeClassFromTeacher(teacherId, Integer.parseInt(classIds[i]));
		}
		
		return status;
	}
	
	public int removeStudentsFromClass(int classId, String[] studentIds, CourseDetailsDaoImpl courseDetailsDaoImpl) {
		int status=0;
		for(int i=0; i<studentIds.length;i++) {
			status=courseDetailsDaoImpl.removeStudentFromClass(classId, Integer.parseInt(studentIds[i]));
		}
		
		return status;
	}
	
	public String generateCsvFile(StudentManagementModel studentmodel)
    {
        String output = "ClassID, ClassName, Description, Student FirstName, Username, School Name, Email\n";

        /*for (int i=0;i<classList.size();i++) {
            output += classList.get(i).getClassId() + ", " + classList.get(i).getClassName() + ", " + classList.get(i).getDescription() +"\n";
        }*/
        for (Map.Entry<ClassDetails, ArrayList<UserDetails>> pair : studentmodel.getClassStudentList().entrySet()) {
        		for(UserDetails stu: pair.getValue()) {
        			output += pair.getKey().getClassId() + ", " + pair.getKey().getClassName() + ", " + pair.getKey().getDescription();
        			output+=", " + stu.getFirstName() + ", " + stu.getUserName() + ", " + stu.getSchoolName() + ", " + stu.getEmail()+ "\n";
        		}
        }
        return output;
    }

	public CourseDetailsModel setExpertFeedbackPageValues(CourseDetailsModel courseDetailsModel) {
		
		courseDetailsModel.setNoOfWords(countWords(courseDetailsModel.getText()));
		courseDetailsModel.setNoOfKeyLinks(courseDetailsModel.getKeyRelations().size());
		courseDetailsModel.setNoOfKeyConcepts(courseDetailsModel.getKeyConcepts().size());
		return courseDetailsModel;
	}
	
	public int countWords(String input) { 
		
		if (input == null || input.isEmpty()) { 
			return 0; 
		} 
		
		String[] words = input.split("\\s+"); 
		return words.length; 
	}
	
	public MailDetails composeMessage(MailDetails mail) {
		mail.setSubject("SMART Account Request - Approval Needed");
		String message="The following account needs approval. \n\nUsername: "+mail.getUserName()+"  \nFirstname: "+mail.getFirstName()+
				"  \nEmail: "+mail.getEmail()+"  \nSchool Name: "+mail.getSchoolName()+" \n\n";
		message=message+"Requester Message: "+mail.getMessage()+" \n\n\n";
		message= message+"This is an automatically generated email from SMART.\n\n";
		mail.setMessage(message);
		mail.setTo("smartproject992@gmail.com");
		return mail;
	}
	
	public MailDetails composeResearcherApprovalMessage(MailDetails mail) {
		mail.setSubject("SMART Account - Approved");
		String message="Congratulations! Your request for SMART account has been approved. Please login with your registered username and password to start using the system.\n\nThis is an automatically generated email from SMART.";
		mail.setMessage(message);
		mail.setTo(mail.getEmail());
		return mail;
	}
	
	public MailDetails composeResearcherUpgradeApprovalMessage(MailDetails mail) {
		mail.setSubject("SMART Account - Approved");
		String message="Congratulations! Your request to upgrade to SMART Researcher account has been approved. Please login with your registered username and password to start using the system as a Researcher.\n\nThis is an automatically generated email from SMART.";
		mail.setMessage(message);
		mail.setTo(mail.getEmail());
		return mail;
	}
	
	public String generateTempPassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*/\\?";
		String pwd = RandomStringUtils.random( 10, characters );
		return pwd;
	}
	
	public MailDetails composeTemporaryPasswordMessage(MailDetails mail) {
		mail.setSubject("SMART Account Temporary Password");
		String pwd= mail.getMessage();
		String message="Your temporary password for the account is below.\n"+pwd+"\n\n";
		message= message+"This is an automatically generated email from SMART.\n\n";
		mail.setMessage(message);
		mail.setTo(mail.getEmail());
		return mail;
	}
	
	public MailDetails composeUpgradeToResearcherMessage(MailDetails mail) {
		mail.setSubject("SMART Account Request - Approval Needed");
		String message="The following instructor account requested upgrade to Researcher. \n\nUsername: "+mail.getUserName()+"  \nFirstname: "+mail.getFirstName()+
				"  \nEmail: "+mail.getEmail()+"  \nSchool Name: "+mail.getSchoolName()+" \n\n";
		message=message+"Requester Message: "+mail.getMessage()+" \n\n\n";
		message= message+"This is an automatically generated email from SMART.\n\n";
		mail.setMessage(message);
		mail.setTo("smartproject992@gmail.com");
		return mail;
	}
	
	public MailDetails composecontactus(MailDetails mail) {
		mail.setSubject("Ticket-"+mail.getSubject());
		String message;
		
		message=mail.getRequest()+" raised by.  \nEmail: "+mail.getEmail()+"\n\n";
						
		
		message=message+"Requester Message: "+mail.getDescription()+" \n\n\n";
		message= message+"This is an automatically generated email from SMART.\n\n";
		mail.setMessage(message);
		mail.setTo("smartproject992@gmail.com");
		return mail;
	}
}

