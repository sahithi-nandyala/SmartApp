package edu.smart.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import edu.smart.controller.CourseDetailsController;
import edu.smart.model.CourseDetailsModel;
import edu.smart.model.InstructorModel;
import edu.smart.model.ProjectManagementModel;
import edu.smart.model.UserDetailsModel;
import edu.smart.pojo.CourseDetails;
import edu.smart.pojo.ProjectDetails;
import edu.smart.pojo.UserDetails;
import edu.smart.util.AppendUtil;
import edu.smart.model.StudentManagementModel;
import edu.smart.pojo.AssignmentDetails;
import edu.smart.pojo.ClassDetails;
import edu.smart.pojo.ClassStudentDetails;
public class CourseDetailsDaoImpl{
	static Logger log = Logger.getLogger(CourseDetailsController.class.getName());
	JdbcTemplate template; 
	
	public void setTemplate(JdbcTemplate template) {  
	    this.template = template;  
	}
	
	public int addStudentToClass(UserDetails studentDetails, int classId) {
		int status=0;

		StringBuilder sqlAppend= new StringBuilder();

		sqlAppend.append("INSERT INTO class_student (classid, studentid) VALUES (");
		sqlAppend.append("'" + classId  + "',");
		sqlAppend.append("'" + studentDetails.getUserID() +  "')");	
		status = template.update(sqlAppend.toString());

		return status;
	}
	
	public int addStudent(UserDetails studentDetails) {

			int status=0;
			int userid=0;
			StringBuilder sqlAppend= new StringBuilder();
			String usertype;
			String query1="select role,userid from members where username=\""
			+studentDetails.getUserName()+"\" ";
			SqlRowSet sqlRowset = template.queryForRowSet(query1);
			
			if(sqlRowset.next()) {
				userid=sqlRowset.getInt("userid");
				usertype = sqlRowset.getString("role");
			}
			else {
				status=2;
				return status;
			}
			if(!usertype.equals("student")) {
				status=4;
				return status;
			}
			log.info(status);
			String query3="select * from class_student where classid ="+studentDetails.getClassId()+" and studentid="+userid;
			SqlRowSet sqlRowset2 = template.queryForRowSet(query3);
			if(sqlRowset2.next()) {
				status=3;
				return status;
			}
			else {
				String query2="INSERT INTO class_student (classid,studentid) values ("+studentDetails.getClassId()+","+userid+")";
				status = template.update(query2);
				log.info(status);
				log.info(query2);
			}
			return status;
		}
	
	public int removeStudentFromClass(int classId, int studentId) {
		int status=0;

		String query = "DELETE FROM class_student where classid="+classId+" and studentid="+studentId;
		status = template.update(query);
		
		return status;
	}
	
	public AssignmentDetails getassignmentdetails(int assignid)
	{
		AssignmentDetails assigmentdetails=new AssignmentDetails();
		StringBuilder sqlAppend= new StringBuilder();
		sqlAppend.append("SELECT * FROM assignment WHERE assgntid="+assignid+";");
		SqlRowSet sqlRowset = template.queryForRowSet(sqlAppend.toString());
		if(sqlRowset.next())
		{	assigmentdetails.setAssgnmntId(assignid);
			assigmentdetails.setTitle(sqlRowset.getString("title"));
			assigmentdetails.setDirections(sqlRowset.getString("directions").replace("`", "'"));
			assigmentdetails.setDescription(sqlRowset.getString("description").replace("`", "'"));
			assigmentdetails.setStatus(sqlRowset.getString("as_status"));
			assigmentdetails.setType(sqlRowset.getString("type"));
		}
		return assigmentdetails;
	}
	public int removeClassFromTeacher(int teacherId, int classId) {
		int status=0;

		String query = "UPDATE class set teacherid=null where classid=\""+classId+"\"";
		status = template.update(query);
		
		return status;
	}
	
	public StudentManagementModel getStudents(int classId) {
		
		StudentManagementModel studentManagementModel= new StudentManagementModel();
		ArrayList<ClassStudentDetails> studentList= new ArrayList<ClassStudentDetails>();
		ArrayList<UserDetails> studentDetailsList = new ArrayList<UserDetails>();
		UserDetails studentDetails = new UserDetails();
		
		ClassStudentDetails classStudentDetails = new ClassStudentDetails();
		String query;
		query="Select * from class_student where classid=\""+classId+"\"";
		SqlRowSet sqlRowset = template.queryForRowSet(query);
		while(sqlRowset.next()) {
			classStudentDetails.setStudentId(sqlRowset.getInt("studentid"));
			studentList.add(classStudentDetails);
		}
		
		for(int i=0; i<studentList.size();i++){
			studentDetails=getUserDetailsbyId(studentList.get(i).getStudentId());
			studentDetailsList.add(studentDetails);
		}
		
		studentManagementModel.setStudentList(studentList);
		studentManagementModel.setStudentDetailsList(studentDetailsList);
		return studentManagementModel;
	}
	
	public StudentManagementModel getAllStudentsOfTeacherByClass(int teacherId) {
		StudentManagementModel studentManagementModel= new StudentManagementModel();
		ArrayList<Integer> classIdList = new ArrayList<Integer>();
		String query="";
		query="Select classid from class where teacherid="+teacherId+" order by classid desc";
		SqlRowSet sqlRowset = template.queryForRowSet(query);
		while(sqlRowset.next()) {
			classIdList.add(sqlRowset.getInt("classid"));
		}
		query="";
		for(int i=0; i<classIdList.size(); i++) {
			ClassDetails classDetails = new ClassDetails();
			ArrayList<Integer> studentIdList = new ArrayList<Integer>();
			ArrayList<UserDetails> studentDetailsList = new ArrayList<UserDetails>();
			
			classDetails = getClassDetailsbyId(classIdList.get(i));
			String query2 = "select * from class_student where classid="+classIdList.get(i);
			SqlRowSet sqlRowset2 = template.queryForRowSet(query2);
			
			while(sqlRowset2.next()) {
				studentIdList.add(sqlRowset2.getInt("studentid"));
			}
			for(int j=0; j<studentIdList.size(); j++) {
				UserDetails studentDetails= new UserDetails();
				studentDetails=getUserDetailsbyId(studentIdList.get(j));
				studentDetails.setClassStudentId(Integer.toString(classIdList.get(i))+Integer.toString(studentDetails.getUserID()));
				studentDetailsList.add(studentDetails);
			}
			log.info("classId: "+classIdList.get(i));
			System.out.println("classId: "+classIdList.get(i));
			studentManagementModel.getClassStudentList().put(classDetails, studentDetailsList);
		}
		
		return studentManagementModel;
	}
	
	public StudentManagementModel getClasses(int teacherId) {
		
		StudentManagementModel studentManagementModel= new StudentManagementModel();
		ArrayList<ClassDetails> classList= new ArrayList<ClassDetails>();
		//ClassDetails classDetails = new ClassDetails();
		System.out.println("teacher ID: "+teacherId);
		String query;
		query="Select * from class where teacherid="+teacherId;
		System.out.println("query: "+query);
		SqlRowSet sqlRowset = template.queryForRowSet(query);
		while(sqlRowset.next()) {
			ClassDetails classDetails = new ClassDetails();
			classDetails.setClassId(sqlRowset.getInt("classid"));
			classDetails.setClassName(sqlRowset.getString("classname"));
			classDetails.setDescription(sqlRowset.getString("description"));
			classDetails.setTeacherId(teacherId);
			classList.add(classDetails);
		}
		
		studentManagementModel.setClassDetailsList(classList);
		return studentManagementModel;
	}
	
	public ClassDetails getClassDetailsbyId(int classId) {
		ClassDetails classDetails = new ClassDetails();
		String query="";
		query="Select * from class where classid="+classId;
		SqlRowSet sqlRowset2 = template.queryForRowSet(query);
		while(sqlRowset2.next()) {
			classDetails.setClassId(sqlRowset2.getInt("classid"));
			classDetails.setClassName(sqlRowset2.getString("classname"));
			classDetails.setDescription(sqlRowset2.getString("description"));
		}
		return classDetails;
		
	}
	
	public UserDetails getUserDetailsbyId(int userId) {
		UserDetails userDetails = new UserDetails();
		String query="";
		query="Select * from members where userid="+userId;
		SqlRowSet sqlRowset2 = template.queryForRowSet(query);
		while(sqlRowset2.next()) {
			userDetails.setUserID(sqlRowset2.getInt("userid"));
			userDetails.setAnswer(sqlRowset2.getString("answer"));
			userDetails.setEmail(sqlRowset2.getString("email"));
			userDetails.setFirstName(sqlRowset2.getString("firstname"));
			userDetails.setPassword(sqlRowset2.getString("password"));
			userDetails.setSchoolName(sqlRowset2.getString("schoolname"));
			userDetails.setUserName(sqlRowset2.getString("username"));
			userDetails.setUserType(sqlRowset2.getString("role"));
		}
		return userDetails;
		
	}
	
	public int saveUser(UserDetails userDetails) {

		StringBuilder sqlAppend= new StringBuilder();
		//String userid=userDetails.getUserType().substring(0, 3)+"_"+userDetails.getUserName();
		
		String username=userDetails.getUserName().toLowerCase();
		String query="select * from members where username='"+userDetails.getUserName()+"'";
		SqlRowSet sqlRowset = template.queryForRowSet(query);
		if(sqlRowset.next()) {
			return 3;
		}
		sqlAppend.append("INSERT INTO members (role, schoolname, firstname, username, password, email, answer) VALUES (");
		//sqlAppend.append("'" + userid  + "',");
		sqlAppend.append("'" + userDetails.getUserType()  + "',");
		sqlAppend.append("'" + userDetails.getSchoolName()  + "',");
		sqlAppend.append("'" + userDetails.getFirstName()  + "',");
		sqlAppend.append("'" + userDetails.getUserName()  + "',");
		sqlAppend.append("'" + userDetails.getPassword()  + "',");
		sqlAppend.append("'" + userDetails.getEmail()  + "',");
		sqlAppend.append("'" + userDetails.getAnswer()  +  "')");	
		int status = template.update(sqlAppend.toString());
		if(status!=0 && userDetails.getUserType().equals("researcher")) {
			String query2= "select userid from members where username='"+userDetails.getUserName()+"'";
			SqlRowSet sqlRowset2 = template.queryForRowSet(query2);
			//userDetails.setUserID(sqlRowset2.getInt("userid"));
			while(sqlRowset2.next()) {
				String query1 = "insert into validresearcher values ("+sqlRowset2.getInt("userid")+", "+ 0 +")";
				status = template.update(query1);
			}
		}
		if(status!=0 && userDetails.getUserType().equals("teacher")) {
			String query2= "select userid from members where username='"+userDetails.getUserName()+"'";
			SqlRowSet sqlRowset2 = template.queryForRowSet(query2);
			//userDetails.setUserID(sqlRowset2.getInt("userid"));
			while(sqlRowset2.next()) {
				String query1 = "insert into validresearcher values ("+sqlRowset2.getInt("userid")+", "+ 0 +")";
				status = template.update(query1);
			}
		}
		if(status!=0) {
			String query2= "select userid from members where username='"+userDetails.getUserName()+"'";
			SqlRowSet sqlRowset2 = template.queryForRowSet(query2);
			//userDetails.setUserID(sqlRowset2.getInt("userid"));
			while(sqlRowset2.next()) {
				String query1 = "insert into validuser values ("+sqlRowset2.getInt("userid")+", "+ 1 +")";
				status = template.update(query1);
			}
		}
		return status;
	}
	
	public int updateValidResearcherUpgrade(int userid) {
		int status=0;
		String query2= "select valid from validresearcher where userid="+userid;
		SqlRowSet sqlRowset = template.queryForRowSet(query2);
		if(sqlRowset.next()) {
			String query = "update validresearcher set valid=2 where userid="+userid;
			status = template.update(query);
		}
		else {
			String query = "insert into validresearcher values ("+userid+","+2+")";
			status = template.update(query);
		}
		return status;
		
	}
	
	public int save(CourseDetailsModel course, String model) {

		StringBuilder sqlAppend= new StringBuilder();
		StringBuilder sqlAppend2= new StringBuilder();
		course.setSm_Subgraph(99999.0);
		
			sqlAppend.append("INSERT INTO expert_model (problemID, title, text, concepts,keyconcepts, relations, avgdegree, density, diameter, noofconcepts, meandistance, betweeness, noofrelations, pagerank, closenessCentrality, eigenvector, Clustering, subgroups) VALUES (");
			sqlAppend.append("'" + course.getAssgntID()  + "',");
			sqlAppend.append("'" + course.getTitle()  + "',");
			sqlAppend.append("'" + course.getText().replace('\'', '~')  + "',");
			sqlAppend.append("'" + AppendUtil.listToString(course.getAllConceptList())  + "',");
			sqlAppend.append("'" + AppendUtil.listToString(course.getKeyConcepts())  + "',");
			sqlAppend.append("'" + course.getRelation()  + "',");
			sqlAppend.append("'" + course.getAvgdegree()  + "',");
			sqlAppend.append("'" + course.getDensity()  + "',");
			sqlAppend.append("'" + course.getDiameter()  + "',");
			sqlAppend.append("'" + course.getNoOfConcepts() + "',");
			sqlAppend.append("'" + course.getMeandistance() + "',");
			sqlAppend.append("'" + course.getBetweenness()  + "',");
			sqlAppend.append("'" + course.getNoOfRelations() + "',");		
			sqlAppend.append("'" + course.getPageRank()  + "',");
			sqlAppend.append("'" + course.getClosenessCentrality()  + "',");
			sqlAppend.append("'" + course.getEigenVectorCentrality()  + "',");
			sqlAppend.append("'" + course.getClusteringcoef()  + "',");
			//sqlAppend.append("'" + course.getDBAdjacencyMatrix()  + "',");
			sqlAppend.append("'" + course.getSubgraph()  + "')");
			
			int flag1=template.update(sqlAppend.toString());
			
			SqlRowSet sqlRowset = template.queryForRowSet("select max(expertmodelid) from expert_model where title='" + course.getTitle() + "' and text='" + course.getText().replace('\'', '~') + "'");
			if(sqlRowset.next()) {
				course.setExpertID(sqlRowset.getInt("max(expertmodelid)"));
			}
			
			sqlAppend2.append("INSERT INTO expert_model2 (expertmodelid, problemID, pathlist, concepthighlightpairs, adjacencyMatrix) VALUES (");
			sqlAppend2.append("'" + course.getExpertID()  + "',");
			sqlAppend2.append("'" + course.getAssgntID()  + "',");
			sqlAppend2.append("'" + AppendUtil.listToString(course.getPathList())  + "',");
			sqlAppend2.append("'" + course.getConcepthighlightpairs() + "',");
			sqlAppend2.append("'" + course.getDBAdjacencyMatrix() + "')");
			//sqlAppend2.append("'" + course.getLinkReduction() + "')");
			System.out.println(sqlAppend2.toString());
		
		int flag=template.update(sqlAppend2.toString()); 

		return course.getExpertID();
	}
	
	public int updatereference(CourseDetailsModel course)
	{
		StringBuilder sqlAppend2= new StringBuilder();
		sqlAppend2.append("UPDATE expert_model SET ");
		sqlAppend2.append(" title='"+course.getTitle()+"'");
		sqlAppend2.append(", text='"+course.getText().replace('\'', '~')+"'");
		sqlAppend2.append(", concepts='"+AppendUtil.listToString(course.getAllConceptList())+"'");
		sqlAppend2.append(", keyconcepts='"+AppendUtil.listToString(course.getKeyConcepts())+"'");
		sqlAppend2.append(", relations='"+course.getRelation()+"'");
		sqlAppend2.append(", avgdegree="+course.getAvgdegree());
		sqlAppend2.append(", density="+course.getDensity());
		sqlAppend2.append(", diameter="+course.getDiameter());
		sqlAppend2.append(", noofconcepts="+course.getNoOfConcepts());
		sqlAppend2.append(", meandistance="+course.getMeandistance());
		sqlAppend2.append(", noofrelations="+course.getNoOfRelations());
		sqlAppend2.append(", subgroups="+course.getSubgraph());
		sqlAppend2.append(" WHERE expertmodelid="+course.getExpertID()+";");

		template.update(sqlAppend2.toString());
		
		StringBuilder sqlAppend1= new StringBuilder();
		sqlAppend1.append("UPDATE expert_model2 SET ");
		sqlAppend1.append(" pathlist='"+AppendUtil.listToString(course.getPathList())+"'");
		sqlAppend1.append(", adjacencyMatrix='"+course.getDBAdjacencyMatrix()+"'");
		sqlAppend1.append(" WHERE expertmodelid="+course.getExpertID()+";");
		
		template.update(sqlAppend1.toString());
		return 1;
	}
	
	public int savestudentesponse(CourseDetailsModel course)
	{
		StringBuilder sqlAppend= new StringBuilder();
		StringBuilder sqlAppend2= new StringBuilder();
		course.setSm_Subgraph(99999.0);
		
		sqlAppend.append("INSERT INTO student_model (text, concepts, keyconcepts, relations, avgdegree, density, "
				+ "diameter, noofconcepts, meandistance, expertmodelid, betweeness, noofrelations, pagerank, "
				+ "closenessCentrality, eigenvector, missingconcepts, subgroups, SM_balanced,SM_conceptual,SM_density, SM_diameter, SM_meandistance, "
				+ "SM_noofconcepts, SM_noofrelations, SM_relational, SM_subgraphs, SM_avgdegree) VALUES (");
		sqlAppend.append("'" + course.getText().replace('\'', '~')  + "',");
		sqlAppend.append("'" + AppendUtil.listToString(course.getAllConceptList())  + "',");
		sqlAppend.append("'" + AppendUtil.listToString(course.getKeyConcepts())  + "',");
		sqlAppend.append("'" + course.getRelation()  + "',");
		sqlAppend.append("'" + course.getAvgdegree()  + "',");
		sqlAppend.append("'" + course.getDensity()  + "',");
		sqlAppend.append("'" + course.getDiameter()  + "',");
		sqlAppend.append("'" + course.getNoOfConcepts() + "',");
		sqlAppend.append("'" + course.getMeandistance() + "',");
		sqlAppend.append("'" + course.getExpertID() + "',");
		sqlAppend.append("'" + course.getBetweenness()  + "',");
		sqlAppend.append("'" + course.getNoOfRelations() + "',");		
		sqlAppend.append("'" + course.getPageRank()  + "',");
		sqlAppend.append("'" + course.getClosenessCentrality()  + "',");
		sqlAppend.append("'" + course.getEigenVectorCentrality()  + "',");
		sqlAppend.append("'" + AppendUtil.listToString(course.getMissingConcepts())  + "',");
		sqlAppend.append("'" + course.getSubgraph()  + "',");
		sqlAppend.append("'" + course.getSm_Balancedmatching()  + "',");
		sqlAppend.append("'" + course.getSm_conceptualmatching()  + "',");
		sqlAppend.append("'" + course.getSm_density()  + "',");
		sqlAppend.append("'" + course.getSm_diameter()  + "',");
		sqlAppend.append("'" + course.getSm_meandistance()  + "',");
		sqlAppend.append("'" + course.getSm_NOofconcepts()  + "',");
		sqlAppend.append("'" + course.getSm_NoOfRelations()  + "',");
		sqlAppend.append("'" + course.getSm_propositionalmatching()  + "',");
		sqlAppend.append("'" + course.getSm_Subgraph()  + "',");
		sqlAppend.append("'" + course.getSm_avgdegree() + "')");
		
		int flag1=template.update(sqlAppend.toString());
		
		if(flag1!=0)
		{
		SqlRowSet sqlRowset = template.queryForRowSet("select max(modelID) from student_model where text='" + course.getText().replace('\'', '~') + "'");
		if(sqlRowset.next()) {
			course.setStudentresponseid(sqlRowset.getInt("max(modelID)"));
		}
		}
		sqlAppend2.append("INSERT INTO student_model2 (modelID, pathlist, recallC, recallP, missinglinks, adjacencyMatrix) VALUES (");
		sqlAppend2.append("'" + course.getStudentresponseid() + "',");
		sqlAppend2.append("'" + AppendUtil.listToString(course.getPathList())  + "',");
		sqlAppend2.append("'" + course.getRecallkeyconcepts()  + "',");
		sqlAppend2.append("'" + course.getRecallKeylinks()  + "',");
		sqlAppend2.append("'" + AppendUtil.listToString(course.getMissingLinks())+ "',");
		sqlAppend2.append("'" + course.getDBAdjacencyMatrix() + "')");
		
		int flag=template.update(sqlAppend2.toString()); 
		
		if(flag1!=0 && flag!=0)
		{
			
			//if(course.getStudentId()!=null) {
				String query = "INSERT INTO student_model_mapping (studentid, modelid) values (" + course.getStudentId() + ", " + course.getStudentresponseid() + ")";
				template.update(query);
			//}
			
			course.setModel(Integer.toString(course.getStudentId()));
		}
		
		
		return course.getStudentresponseid();
		
		
	}
	
	public int addresponseentry(int studentid,int assignid, int modelid)
	{
		String query = "INSERT INTO studentresponses (studentid, assignid, studentmodelid) values (" + studentid + ", " + assignid + ","+modelid+")";
		return template.update(query);
	}
	public int saveconcepts(int id, CourseDetailsModel courseDetailsModel)
	{
		StringBuilder sqlAppend2= new StringBuilder();
		int status=0;
		sqlAppend2.append("UPDATE expert_model SET keyconcepts=");
		sqlAppend2.append("'" + AppendUtil.listToString(courseDetailsModel.getKeyConcepts())  + "' WHERE expertmodelid="+id+";");
		template.update(sqlAppend2.toString());
		String query ="UPDATE expert_model2 SET pathlist='"+AppendUtil.listToString(courseDetailsModel.getPathList())+"' WHERE expertmodelid="+id+";";
		status=template.update(query);
		return status;
	}
	
	public ArrayList<String> getKeyConcepts(int id) {
		StringBuilder sqlAppend= new StringBuilder();
		sqlAppend.append("SELECT keyconcepts FROM expert_model WHERE expertmodelid="+id+";");
		SqlRowSet sqlRowset = template.queryForRowSet(sqlAppend.toString());
		CourseDetailsModel result = new CourseDetailsModel();
		while(sqlRowset.next()) {
			result.setKeyConcepts(AppendUtil.stringToList(sqlRowset.getString("keyconcepts")));
		}
		return result.getKeyConcepts();
	}
	
	public CourseDetailsModel ExpertModelValues(int id)
	{
		
		StringBuilder sqlAppend= new StringBuilder();
		StringBuilder sqlAppend2= new StringBuilder();
		sqlAppend.append("SELECT * FROM expert_model WHERE expertmodelid="+id+";");
		SqlRowSet sqlRowset = template.queryForRowSet(sqlAppend.toString());
		CourseDetailsModel result = new CourseDetailsModel();
		while(sqlRowset.next()) {
			System.out.println("jnflkdslkl");
			//result.setBetweenness(sqlRowset.getString("betweeness"));
			result.setTitle(sqlRowset.getString("title"));
			result.setText(sqlRowset.getString("text").replace("~", "\'"));
			result.setAvgdegree(sqlRowset.getDouble("avgdegree"));
			result.setDensity(sqlRowset.getDouble("density"));
			result.setDiameter(sqlRowset.getDouble("diameter"));
			result.setMeandistance(sqlRowset.getDouble("meandistance"));
			result.setNoOfConcepts(sqlRowset.getInt("noofconcepts"));
			result.setNoOfRelations(sqlRowset.getInt("noofrelations"));
			result.setSubgraph(sqlRowset.getDouble("subgroups"));
			result.setKeyConcepts(AppendUtil.stringToList(sqlRowset.getString("keyconcepts")));
			result.setAllConceptList(AppendUtil.stringToList(sqlRowset.getString("concepts")));
			result.setRelation(sqlRowset.getString("relations"));	
			result.setPageRank(sqlRowset.getString("pagerank"));
			result.setBetweenness(sqlRowset.getString("betweeness"));
			result.setClusteringcoef(sqlRowset.getString("Clustering"));
			result.setClosenessCentrality(sqlRowset.getString("closenessCentrality"));
			result.setEigenVectorCentrality(sqlRowset.getString("eigenvector"));
		}
		sqlAppend2.append("SELECT * FROM expert_model2 WHERE expertmodelid="+id+";");
		SqlRowSet sqlRowset2 =  template.queryForRowSet(sqlAppend2.toString());
		
		while(sqlRowset2.next()) {

			result.setPathList(AppendUtil.stringToList(sqlRowset2.getString("pathlist")));	
			result.setConcepthighlightpairs(sqlRowset2.getString("concepthighlightpairs"));	
			result.setDBAdjacencyMatrix(sqlRowset2.getString("adjacencyMatrix"));
			//result.setLinkReduction(Integer.parseInt(sqlRowset2.getString("linkReduction")));
		}
		
		if(result.getDBAdjacencyMatrix()!=null) {
			ArrayList<ArrayList<String>> adjacencyList= new ArrayList<ArrayList<String>>();
			String[] adjacencyArray1 = result.getDBAdjacencyMatrix().split("/");
			int i,j, size=0;
			
			//System.out.println("Adjacency matrix from db: "+ studentModel.getExpert().getDBAdjacencyMatrix());
			for(i=0;i<adjacencyArray1.length;i++) {
				ArrayList<String> adjacencyList1= new ArrayList<String>();
				String[] adjacencyArray2=adjacencyArray1[i].split(",");
				if(i==0) {
					size=adjacencyArray2.length;
				}
				for(j=0;j<adjacencyArray2.length;j++) {
					adjacencyList1.add(adjacencyArray2[j]);
					//adjacencyMatrix[i][j]= Double.parseDouble(adjacencyArray2[j]);
				}
				adjacencyList.add(adjacencyList1);
				//System.out.println();
			}
			
			size=size+1;
			int l=0;
			double[][] adjacencyMatrix = new double[size][size];
			if(!adjacencyList.isEmpty()) {
				for(i=0; i<size; i++) {
					for(j=0; j<size; j++) {
						if(i==j || i>j) {
							adjacencyMatrix[i][j]=0.0;
						}
						else {
							if(adjacencyList.get(i).get(l)!=null && adjacencyList.get(i).get(l)!="null") {
								adjacencyMatrix[i][j]=Double.parseDouble(adjacencyList.get(i).get(l));
								l++;
							}
						}
						//System.out.print(adjacencyMatrix[i][j]+ "  ");
					}
					//System.out.println();
					l=0;
				}
			}
			result.setAdjacencyMatrix(adjacencyMatrix);
		}
		int found=0;
		ArrayList<String> bConcepts= new ArrayList<String>();
		for(int m=0;m<result.getAllConceptList().size();m++) {
			for(int n=0;n<result.getKeyConcepts().size();n++) {
				if(result.getKeyConcepts().get(n).equals(result.getAllConceptList().get(m))) {
					found=1;
					break;
				}
			}
			if(found==0) {
				bConcepts.add(result.getAllConceptList().get(m));
				//System.out.println("bConcepts : "+allConcepts.get(m));
			}
			found=0;
		}
		
		result.setbConcepts(bConcepts);
		
		return result;
	}

	public int update(CourseDetailsModel course) {
		return 0;
	}

	public CourseDetailsModel getCourseDetailsById(int id) {
		return null;
	}
	
	public UserDetails userlogin(UserDetails userDetails)
	{
		//System.out.println(userDetails.getUserName()+"  : "+userDetails.getPassword());
		String username = userDetails.getUserName();    
	    String pwd = userDetails.getPassword();
	   
	    SqlRowSet sqlRowset = template.queryForRowSet("select * from members where username='" + username + "' and password='" + pwd + "'");
	    if (sqlRowset.next()) {
	    	userDetails.setUserType(sqlRowset.getString("role"));
	    	userDetails.setFirstName(sqlRowset.getString("firstname"));
	    	userDetails.setUserID(sqlRowset.getInt("userid"));
	    	userDetails.setSchoolName(sqlRowset.getString("schoolname"));
	    	userDetails.setEmail(sqlRowset.getString("email"));
	    	SqlRowSet sqlRowset2 = template.queryForRowSet("select valid from validuser where userid="+userDetails.getUserID());
	    	if(sqlRowset2.next()) {
	    		if(sqlRowset2.getInt("valid")==0) {
	    			userDetails.setLoginStatus("invalid");
	    			return userDetails;
	    		}
	    	}
		userDetails.setLoginStatus("success");
	    	return userDetails;
	    } 
	    else {
	    	userDetails.setLoginStatus("failed");
	    	
	    	return userDetails;
	    }
		
	}
	
	public int editClass(ClassDetails classDetails)
	{
		String query = "Update class set classname=\""+classDetails.getClassName()+"\" ,description=\""+classDetails.getDescription()+"\" where classid="+classDetails.getClassId();
		//String userid=userDetails.getUserType().substring(0, 3)+"_"+userDetails.getUserName();
		int status=template.update(query);
		return status;
	}
	
	public int addClass(ClassDetails classDetails)
	{
		StringBuilder sqlAppend= new StringBuilder();
		//String userid=userDetails.getUserType().substring(0, 3)+"_"+userDetails.getUserName();
		int status=0;

		sqlAppend.append("INSERT INTO class (classname, description, teacherid) VALUES (");
		//sqlAppend.append("'" + userid  + "',");
		sqlAppend.append("'" + classDetails.getClassName()  + "',");
		sqlAppend.append("'" + classDetails.getDescription()  + "',");
		sqlAppend.append(classDetails.getTeacherId()+")");
	
		status=template.update(sqlAppend.toString());
		
		SqlRowSet sqlRowset = template.queryForRowSet("select * from class where classname='"+classDetails.getClassName()+"' AND description='"+classDetails.getDescription()+"' AND teacherid="+classDetails.getTeacherId());
		if(sqlRowset.next()) {
			classDetails.setClassId(sqlRowset.getInt("classid"));
		}
		
		return status;
	}
	
	public ClassDetails removeclass(ClassDetails classDetails)
	{
		StringBuilder sqlAppend= new StringBuilder();
		//String userid=userDetails.getUserType().substring(0, 3)+"_"+userDetails.getUserName();

		sqlAppend.append("delete from class where classname='"+classDetails.getClassName()+"' AND description='"+classDetails.getDescription()+"' AND teacherid='"+classDetails.getTeacherId()+"'");

	
		template.update(sqlAppend.toString());
		return classDetails;
	}
	
	public ArrayList<ClassDetails> getclases(int userid)
	{
		
		ClassDetails classdetails;
		//ProjectManagementModel projectmanagement=new ProjectManagementModel();
		ArrayList<ClassDetails> classes=new ArrayList<ClassDetails>();
		SqlRowSet sqlRowset = template.queryForRowSet("select * from class where teacherid='" + userid + "'");
		while(sqlRowset.next()) {
	    	classdetails=new ClassDetails();
	    	classdetails.setClassId(sqlRowset.getInt("classid"));
	    	classdetails.setClassName(sqlRowset.getString("classname"));
	    	classdetails.setDescription(sqlRowset.getString("description"));
	    	classes.add(classdetails);	    	
	    }
		//projectmanagement.setClassdetails(classes);
		return classes;
	}
	
	public int getstudentcountbyclassid(int classid)
	{
		int studentcount=0;
		String query2 = "select * from class_student where classid="+classid;
		SqlRowSet sqlRowset = template.queryForRowSet(query2);
		while(sqlRowset.next()) {
			studentcount++;
		}

		return studentcount;
	}	
	
	public int countStudentResponsesinClass(int classid, int assignmentid)
	{
		int studentresponses=0;
		String query2 = "select distinct studentid,assignid from studentresponses where assignid="+assignmentid+" and studentid in (select studentid from class_student where classid="+classid+")";
		SqlRowSet sqlRowset = template.queryForRowSet(query2);
		while(sqlRowset.next()) {
			studentresponses++;
		}
		return studentresponses;
	}
	public ArrayList<ClassDetails> getclassofassignment(int assignmentid)
	{
		//StringBuilder sqlAppend= new StringBuilder();
		ClassDetails classdetails;
		//ProjectManagementModel projectmanagement=new ProjectManagementModel();
		ArrayList<ClassDetails> classes=new ArrayList<ClassDetails>();
		SqlRowSet sqlRowset = template.queryForRowSet("select * from class where classid IN (SELECT classid FROM classassignments where assgntid=" + assignmentid +")");
		while(sqlRowset.next()) {
	    	classdetails=new ClassDetails();
	    	classdetails.setClassId(sqlRowset.getInt("classid"));
	    	classdetails.setStudentcount(getstudentcountbyclassid(classdetails.getClassId()));
	    	classdetails.setClassName(sqlRowset.getString("classname"));
	    	classdetails.setDescription(sqlRowset.getString("description"));
	    	classdetails.setStudentresponses(countStudentResponsesinClass(classdetails.getClassId(),assignmentid));
	    	//SqlRowSet sqlRowset1 = template.queryForRowSet("select * from class where classid='" + classdetails.getClassId() + "'");
			//if(sqlRowset1.next())
			//{

				//classdetails.setClassName(sqlRowset.getString("classname"));
		    //	classdetails.setDescription(sqlRowset.getString("description"));
			//}

	    	
	    	classes.add(classdetails);	    	
	    }
		//projectmanagement.setClassdetails(classes);
	return classes;
	}
	
	public void updateassignment(AssignmentDetails assign)
	{
		 template.execute("UPDATE assignment  SET title='"+assign.getTitle()+"', directions='"+assign.getDirections()+"', description='"+assign.getDescription()+"', as_status='"+assign.getStatus()+"', type='"+assign.getType() +"' WHERE assgntid="+assign.getAssgnmntId());
		 int choosenmodel=assign.getChoosenmodelID();
		 if(choosenmodel!=0)
		 template.execute("UPDATE choosenexpert SET choosenmodelid="+assign.getChoosenmodelID()+" WHERE assignmentid="+assign.getAssgnmntId());
	}
	public void choosemodel(CourseDetails CD)
	{
		template.execute("UPDATE choosenexpert SET choosenmodelid="+CD.getModelID()+" WHERE assignmentid="+CD.getAssignmentID());
	}
	public void deletereferencemodel(int modelid)
	{
		template.execute("DELETE FROM expert_model2 where expertmodelid='" + modelid + "'");
		template.execute("DELETE FROM expert_model where expertmodelid='" + modelid + "'");	
		
	}
	public void deleteassignment(int assignmentid)
	{
		
		template.execute("DELETE FROM classassignments where assgntid='" + assignmentid + "'");
		template.execute("DELETE FROM choosenexpert where assignmentid='" + assignmentid + "'");
		template.execute("DELETE FROM studentresponses where assignid='" + assignmentid + "'");
		template.execute("DELETE FROM expert_model2 where problemID='" + assignmentid + "'");
		template.execute("DELETE FROM expert_model where problemID='" + assignmentid + "'");
		
		SqlRowSet sqlRowset = template.queryForRowSet("select * from studentresponses where assignid='" + assignmentid + "'");
		while(sqlRowset.next()) {
			int modelid=sqlRowset.getInt("studentmodelid");
			template.execute("DELETE FROM student_model2 where modelID=" + modelid + "");
			template.execute("DELETE FROM student_model where modelID='" + modelid + "'");
		}
		
		template.execute("DELETE FROM assignment where assgntid='" + assignmentid + "'");
	}
	public void addclasstoassgn(int assignmentid, int classid)
	{
		StringBuilder sqlAppend= new StringBuilder();


		sqlAppend.append("INSERT into classassignments (assgntid,classid) VALUES (");
		sqlAppend.append("'" + assignmentid  + "',");
		sqlAppend.append("'" + classid  + "')");		
	
		template.update(sqlAppend.toString());
	}
	public ArrayList<ProjectDetails> getprojects(int userid)
	{
		ProjectDetails projectdetails;
		ArrayList<ProjectDetails> projects=new ArrayList<ProjectDetails>();
		SqlRowSet sqlRowset = template.queryForRowSet("select * from project where teacherid='" + userid + "'" + " order by createdDate desc");
		while(sqlRowset.next()) {
			projectdetails=new ProjectDetails();
			projectdetails.setProjectId(sqlRowset.getInt("projectid"));
			projectdetails.setProjectName(sqlRowset.getString("projectname"));
			projectdetails.setDescription(sqlRowset.getString("desciption"));
			projectdetails.setSubject(sqlRowset.getString("sub"));
			projectdetails.setDateCreated(sqlRowset.getString("createdDate").split(" ")[0]);
			projectdetails.setImagepath(sqlRowset.getString("imgpath"));
	    	
			projects.add(projectdetails);	    	
	    }
		return projects;
	}
	public ProjectDetails addproject(ProjectDetails projectDetails)
	{
		StringBuilder sqlAppend= new StringBuilder();
		//String userid=userDetails.getUserType().substring(0, 3)+"_"+userDetails.getUserName();

		sqlAppend.append("INSERT INTO project (projectname, desciption, sub, imgpath, teacherid) VALUES (");
		//sqlAppend.append("'" + userid  + "',");
		sqlAppend.append("'" + projectDetails.getProjectName()  + "',");
		sqlAppend.append("'" + projectDetails.getDescription() + "',");
		sqlAppend.append("'" + projectDetails.getSubject()  + "',");
		sqlAppend.append("'" + projectDetails.getImagepath().replace("\\","/")   + "',");
		sqlAppend.append("'" + projectDetails.getTeacherId()  + "')");
		
	
		template.update(sqlAppend.toString());
		
		SqlRowSet sqlRowset = template.queryForRowSet("select * from project where projectname='"+projectDetails.getProjectName()+"' AND desciption='"+projectDetails.getDescription()+"' AND sub='"+projectDetails.getSubject()+"' AND teacherid='"+projectDetails.getTeacherId()+"' ");
		if(sqlRowset.next()) {
			
			projectDetails.setProjectId(sqlRowset.getInt("projectid"));
		}
		
		return projectDetails;
	}
	
	public void editproject(ProjectDetails projectDetails)
	{
		if(projectDetails.getImagepath()==null)		
		template.execute("UPDATE project  SET projectname='"+projectDetails.getProjectName()+"', desciption='"+projectDetails.getDescription()+"', sub='"+projectDetails.getSubject()+"'  WHERE projectid="+projectDetails.getProjectId());
		 
		else
			template.execute("UPDATE project  SET projectname='"+projectDetails.getProjectName()+"', desciption='"+projectDetails.getDescription()+"', sub='"+projectDetails.getSubject()+"', imgpath='"+projectDetails.getImagepath()+"'  WHERE projectid="+projectDetails.getProjectId());
			
	}
	public void removeproject(Integer[] ids)
	{
		for(int i=0;i<ids.length;i++)
		{
			SqlRowSet sqlRowset = template.queryForRowSet("select * from assignment where projectid='" + ids[i] + "'");
			while(sqlRowset.next()) {
				int assignid=sqlRowset.getInt("assgntid");
				deleteassignment(assignid);
			}
			template.execute("DELETE FROM project where projectid='" + ids[i] + "'");
			
		}		
		
	}
	
	public ArrayList<AssignmentDetails> getassignments(int userid)
	{
		AssignmentDetails assign;
		ArrayList<AssignmentDetails> assignments=new ArrayList<AssignmentDetails>();
		SqlRowSet sqlRowset = template.queryForRowSet("SELECT assignment.* FROM project\r\n" + 
				"INNER JOIN\r\n" + 
				"assignment\r\n" + 
				"ON project.projectid=assignment.projectid\r\n" + 
				"WHERE project.teacherid='" + userid + "'"+ " order by createdDate desc");
		while(sqlRowset.next()) {
			assign=new AssignmentDetails();
			assign.setAssgnmntId(sqlRowset.getInt("assgntid"));
			assign.setTitle(sqlRowset.getString("title"));
			assign.setDateCreated(sqlRowset.getString("createdDate").split(" ")[0]);
			assign.setDescription(sqlRowset.getString("description").replace("`", "'"));
			assign.setDirections(sqlRowset.getString("directions").replace("`", "'"));
			assign.setProjectId(sqlRowset.getInt("projectid"));
			assign.setStatus(sqlRowset.getString("as_status"));
			//assign.setType(sqlRowset.getString("type"));
	    	
			assignments.add(assign);	    	
	    }
		return assignments;
	}
	
	public AssignmentDetails addassignment(AssignmentDetails assign)
	{
		
		StringBuilder sqlAppend= new StringBuilder();
		sqlAppend.append("INSERT INTO assignment (projectid, title, description,directions, as_status) VALUES (");
		//sqlAppend.append("'" + userid  + "',");
		sqlAppend.append("'" + assign.getProjectId()  + "',");
		sqlAppend.append("'" + assign.getTitle() + "',");
		sqlAppend.append("'" + assign.getDescription().replace("'", "`")  + "',");
		sqlAppend.append("'" + assign.getDirections().replace("'", "`")   + "',");
		sqlAppend.append("'" + assign.getStatus()  + "')");
		
		template.update(sqlAppend.toString());
		SqlRowSet sqlRowset = template.queryForRowSet("select * from assignment where projectid='"+assign.getProjectId()+"' "+ "AND title='"+assign.getTitle()+"' "	+ "AND description='"+assign.getDescription().replace("'", "`") +"' "+ "AND as_status='"+assign.getStatus().replace("'", "`") +"' ");
		if(sqlRowset.next()) {
			assign.setAssgnmntId(sqlRowset.getInt("assgntid"));
		}
		template.update("INSERT INTO choosenexpert(assignmentid, choosenmodelid) VALUES ("+assign.getAssgnmntId()+",0)");
		return assign;
	}
	
	public int editProfile(UserDetails userDetails)
	{
		String query = "Update members set schoolname=\""+userDetails.getSchoolName()+"\" ,firstname=\""+userDetails.getFirstName()+"\" ,email=\""+userDetails.getEmail()+"\" ,username=\""+userDetails.getUserName()+"\", answer=\""+userDetails.getAnswer()+"\" where userid="+userDetails.getUserID();
		int status=template.update(query);
		return status;
	}
	
	
	public ArrayList<CourseDetails> getallexpertmodels(int assignmentid)
	{
		CourseDetails coursedetails;
		ArrayList<CourseDetails> models=new ArrayList<CourseDetails>();
		SqlRowSet sqlRowset = template.queryForRowSet("select * from expert_model where problemID='"+assignmentid+ "'");
		while(sqlRowset.next()) {
			coursedetails=new CourseDetails();
			coursedetails.setModelID(sqlRowset.getInt("expertmodelid"));
			coursedetails.setTitle(sqlRowset.getString("title"));
			SqlRowSet sqlRowset2 = template.queryForRowSet("select * from expert_model2 where expertmodelid='"+coursedetails.getModelID()+ "'");
			if(sqlRowset2.next())
			coursedetails.setCreatedon(sqlRowset2.getString("createddate").split(" ")[0]);
			//coursedetails.setText(sqlRowset.getString("text"));			
	    	
			models.add(coursedetails);	    	
	    }
		return models;
	}
	public int getchoosenexpertmodelid(int assignmentid)
	{
		SqlRowSet sqlRowset = template.queryForRowSet("select * from choosenexpert where assignmentid="+assignmentid);
		if(sqlRowset.next()) {
			return sqlRowset.getInt("choosenmodelid");
		}
		else
			return 0;
	}
	public int removeclassfromassignment(int assignid, int classid)
	{
		
		template.execute("DELETE FROM classassignments where assgntid='" + assignid + "' AND classid='"+ classid+"'");
		
		return 0;
	}
	public CourseDetailsModel getstudentmodel(int studentresponseid)
	{
		CourseDetailsModel courseDetailsModel=new CourseDetailsModel();
		String query1 = "SELECT * FROM student_model WHERE modelID="+studentresponseid;
		String query2 = "SELECT * FROM student_model2 WHERE modelID="+studentresponseid;
		SqlRowSet sqlRowset = template.queryForRowSet(query1);
		SqlRowSet sqlRowset2 = template.queryForRowSet(query2);
		
		if(sqlRowset.next()) {
			//courseDetailsModel.setAssgntID(sqlRowset.getInt("assignmentID"));
			courseDetailsModel.setExpertID(sqlRowset.getInt("expertmodelid"));
			courseDetailsModel.setText(sqlRowset.getString("text").replace("~", "\'"));
			courseDetailsModel.setRelation(sqlRowset.getString("relations"));
			courseDetailsModel.setAllConceptList(AppendUtil.stringToList(sqlRowset.getString("concepts")));
			courseDetailsModel.setKeyConcepts(AppendUtil.stringToList(sqlRowset.getString("keyconcepts")));
			courseDetailsModel.setMissingConcepts(AppendUtil.stringToList(sqlRowset.getString("missingconcepts")));
			courseDetailsModel.setNoOfRelations(sqlRowset.getInt("noofrelations"));
			courseDetailsModel.setNoOfConcepts(sqlRowset.getInt("noofconcepts"));
			courseDetailsModel.setAvgdegree(sqlRowset.getDouble("avgdegree"));
			courseDetailsModel.setDiameter(sqlRowset.getDouble("diameter"));
			courseDetailsModel.setDensity(sqlRowset.getDouble("density"));
			courseDetailsModel.setMeandistance(sqlRowset.getDouble("meandistance"));
			courseDetailsModel.setSm_avgdegree(sqlRowset.getDouble("SM_avgdegree"));
			courseDetailsModel.setSm_Balancedmatching(sqlRowset.getDouble("SM_balanced"));
			courseDetailsModel.setSm_conceptualmatching(sqlRowset.getDouble("SM_conceptual"));
			courseDetailsModel.setSm_density(sqlRowset.getDouble("SM_density"));
			courseDetailsModel.setSm_diameter(sqlRowset.getDouble("SM_diameter"));
			courseDetailsModel.setSm_meandistance(sqlRowset.getDouble("SM_meandistance"));
			courseDetailsModel.setSm_NOofconcepts(sqlRowset.getDouble("SM_noofconcepts"));
			courseDetailsModel.setSm_NoOfRelations(sqlRowset.getDouble("SM_noofrelations"));
			courseDetailsModel.setSm_propositionalmatching(sqlRowset.getDouble("SM_relational"));
			
			
		}		
		if(sqlRowset2.next()) {
			courseDetailsModel.setDBAdjacencyMatrix(sqlRowset2.getString("adjacencyMatrix"));
			courseDetailsModel.setPathList(AppendUtil.stringToList(sqlRowset2.getString("pathlist")));
			courseDetailsModel.setRecallkeyconcepts(Double.parseDouble(sqlRowset2.getString("recallC")));
			courseDetailsModel.setRecallKeylinks(Double.parseDouble(sqlRowset2.getString("recallP")));
			courseDetailsModel.setMissingLinks(AppendUtil.stringToList(sqlRowset2.getString("missinglinks")));
		}
		
		return courseDetailsModel;
	}
	public CourseDetailsModel getStudentModelDetails(int studentresponseid, CourseDetailsModel courseDetailsModel) {
		// TODO Auto-generated method stub
		
		String query1 = "SELECT * FROM student_model WHERE modelID="+studentresponseid;
		String query2 = "SELECT * FROM student_model2 WHERE modelID="+studentresponseid;
		SqlRowSet sqlRowset = template.queryForRowSet(query1);
		SqlRowSet sqlRowset2 = template.queryForRowSet(query2);
		System.out.println();
		if(sqlRowset.next()) {
			//courseDetailsModel.setAssgntID(sqlRowset.getInt("assignmentID"));
			courseDetailsModel.setExpertID(sqlRowset.getInt("expertmodelid"));
			courseDetailsModel.setText(sqlRowset.getString("text").replace("~", "\'"));
			courseDetailsModel.setAllConceptList(AppendUtil.stringToList(sqlRowset.getString("concepts")));
			courseDetailsModel.setKeyConcepts(AppendUtil.stringToList(sqlRowset.getString("keyconcepts")));
			courseDetailsModel.setMissingConcepts(AppendUtil.stringToList(sqlRowset.getString("missingconcepts")));
			courseDetailsModel.setNoOfRelations(sqlRowset.getInt("noofrelations"));
			courseDetailsModel.setNoOfConcepts(sqlRowset.getInt("noofconcepts"));
		}
		System.out.println(courseDetailsModel.getExpertID());
		CourseDetailsModel expertParameters = ExpertModelValues(courseDetailsModel.getExpertID());
		courseDetailsModel.setExpert(expertParameters);
		if(sqlRowset2.next()) {
			courseDetailsModel.setDBAdjacencyMatrix(sqlRowset2.getString("adjacencyMatrix"));
			courseDetailsModel.setPathList(AppendUtil.stringToList(sqlRowset2.getString("pathlist")));
			courseDetailsModel.setRecallkeyconcepts(Double.parseDouble(sqlRowset2.getString("recallC")));
			courseDetailsModel.setRecallKeylinks(Double.parseDouble(sqlRowset2.getString("recallP")));
			courseDetailsModel.setMissingLinks(AppendUtil.stringToList(sqlRowset2.getString("missinglinks")));
		}
		//System.out.println("courseDetailsModel.getDBAdjacencyMatrix "+courseDetailsModel.getDBAdjacencyMatrix());
		//System.out.println("courseDetailsModel.getKeyConcepts() "+courseDetailsModel.getKeyConcepts());
		//System.out.println("courseDetailsModel.getPathList() "+ courseDetailsModel.getPathList());
		if(courseDetailsModel.getDBAdjacencyMatrix()!=null) {
			ArrayList<ArrayList<String>> adjacencyList= new ArrayList<ArrayList<String>>();
			String[] adjacencyArray1 = courseDetailsModel.getDBAdjacencyMatrix().split("/");
			int i,j, size=0;
			
			//System.out.println("Adjacency matrix from db: "+ studentModel.getExpert().getDBAdjacencyMatrix());
			for(i=0;i<adjacencyArray1.length;i++) {
				ArrayList<String> adjacencyList1= new ArrayList<String>();
				String[] adjacencyArray2=adjacencyArray1[i].split(",");
				if(i==0) {
					size=adjacencyArray2.length;
				}
				for(j=0;j<adjacencyArray2.length;j++) {
					adjacencyList1.add(adjacencyArray2[j]);
					//adjacencyMatrix[i][j]= Double.parseDouble(adjacencyArray2[j]);
				}
				adjacencyList.add(adjacencyList1);
				//System.out.println();
			}
			
			size=size+1;
			int l=0;
			double[][] adjacencyMatrix = new double[size][size];
			if(!adjacencyList.isEmpty()) {
				for(i=0; i<size; i++) {
					for(j=0; j<size; j++) {
						if(i==j || i>j) {
							adjacencyMatrix[i][j]=0.0;
						}
						else {
							if(adjacencyList.get(i).get(l)!=null && adjacencyList.get(i).get(l)!="null") {
								adjacencyMatrix[i][j]=Double.parseDouble(adjacencyList.get(i).get(l));
								l++;
							}
						}
						//System.out.print(adjacencyMatrix[i][j]+ "  ");
					}
					//System.out.println();
					l=0;
				}
			}
			courseDetailsModel.setAdjacencyMatrix(adjacencyMatrix);
		}
		return courseDetailsModel;
	}
	
	public int checkValidResearcher(UserDetails userDetails)
	{
		//System.out.println(userDetails.getUserName()+"  : "+userDetails.getPassword());
		String username = userDetails.getUserName();    
	    String pwd = userDetails.getPassword();
	    int status=-1;
	   
	    SqlRowSet sqlRowset = template.queryForRowSet("select valid from validresearcher where userid in (select userid from members where username=\""+
	    		username+"\")");
	    
	    if (sqlRowset.next()) {
	    		status=sqlRowset.getInt("valid");
	    } 
	    	return status;
		
	}
}