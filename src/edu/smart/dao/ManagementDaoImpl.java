package edu.smart.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import edu.smart.controller.CourseDetailsController;
import edu.smart.management.InstructorManagement;
import edu.smart.model.CourseDetailsModel;
import edu.smart.model.InstructorModel;
import edu.smart.model.UserDetailsModel;
import edu.smart.pojo.AssignmentDetails;
import edu.smart.pojo.ClassDetails;
import edu.smart.pojo.UserDetails;
import edu.smart.util.AppendUtil;
import edu.smart.util.DisplayGraph;
import sun.font.CreatedFontTracker;
public class ManagementDaoImpl {
		static Logger log = Logger.getLogger(CourseDetailsController.class.getName());
		JdbcTemplate template; 
		
		
		public void setTemplate(JdbcTemplate template) {  
		    this.template = template;  
		}
		
		public Map<ClassDetails, ArrayList<UserDetails>> getAllStudentsOfInstructorByClass(int teacherId, ManagementDaoImpl managementDaoImpl) {

			InstructorModel instructorModel= new InstructorModel();
			ArrayList<Integer> classIdList = new ArrayList<Integer>();
			CourseDetailsDaoImpl courseDetailsDaoImpl = new CourseDetailsDaoImpl();
			String query="";
			query="Select classid from class where teacherid="+teacherId;
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
				instructorModel.getClassStudentList().put(classDetails, studentDetailsList);
			}
			
			return instructorModel.getClassStudentList();
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
		
		public InstructorModel getStudentModels(ClassDetails classDetails, InstructorModel instructorModel) {
			String query = "select * from student_model where expertmodelid in ( select expertmodelid from expert_model where problemID in "
					+ "( select assgntid from classassignments where classid="+classDetails.getClassId()+"))";
			SqlRowSet sqlRowset = template.queryForRowSet(query);
			CourseDetailsDaoImpl courseDetailsDaoImpl = new CourseDetailsDaoImpl();
			
			while(sqlRowset.next()) {
				CourseDetailsModel courseDetailsModel = new CourseDetailsModel();
				courseDetailsModel.setKeyConcepts(AppendUtil.stringToList(sqlRowset.getString("keyconcepts")));
				courseDetailsModel.setMissingConcepts(AppendUtil.stringToList(sqlRowset.getString("missingconcepts")));
				courseDetailsModel.setExpertID(sqlRowset.getInt("expertmodelid"));
				courseDetailsModel.setModel(Integer.toString(sqlRowset.getInt("modelid")));
				String query2="Select studentid from student_model_mapping where modelid = "+Integer.parseInt(courseDetailsModel.getModel());
				SqlRowSet sqlRowset2 = template.queryForRowSet(query2);
				UserDetails studentDetails = new UserDetails();
				studentDetails.setUserID(sqlRowset2.getInt("studentid"));
				studentDetails=courseDetailsDaoImpl.getUserDetailsbyId(studentDetails.getUserID());
				studentDetails.setClassId(Integer.toString(classDetails.getClassId()));
				String query3="Select problemID from student_model where modelid = "+Integer.parseInt(courseDetailsModel.getModel());
				SqlRowSet sqlRowset3 = template.queryForRowSet(query3);
				courseDetailsModel.setAssgntID(sqlRowset3.getInt("problemID"));
				instructorModel.getStudentResultList().put(studentDetails, courseDetailsModel);
			}
			
			instructorModel = getAssignmentsOfClass(classDetails, instructorModel);
			return instructorModel;
		}
		
		public InstructorModel getStudentModelsOfClass(InstructorModel instructorModel) {
			int assignId= instructorModel.getAssignmentDetails().getAssgnmntId();
			ArrayList<CourseDetailsModel> modelList = new ArrayList<CourseDetailsModel>();
			String query="select max(modelid), studentid from student_model_mapping where modelid in (select modelid from student_model where expertmodelid in "
					+ "( select expertmodelid from expert_model where problemID ="+assignId+")) group by studentid";
			
			SqlRowSet sqlRowset = template.queryForRowSet(query);
			if(!sqlRowset.next()) {
				instructorModel.setStatus(-2);
				return instructorModel;
			}
			sqlRowset.previous();
			while(sqlRowset.next()) {
				CourseDetailsModel courseDetailsModel = new CourseDetailsModel();
				UserDetails studentDetails = new UserDetails();
				courseDetailsModel.setModel(Integer.toString(sqlRowset.getInt("max(modelid)")));
				courseDetailsModel.setStudentresponseid(sqlRowset.getInt("max(modelid)"));
				
				int modelId = sqlRowset.getInt("max(modelid)");
				String query2 ="select * from student_model where modelID="+modelId;
				String query3 ="select * from student_model2 where modelID="+modelId;
				
				SqlRowSet sqlRowset2 = template.queryForRowSet(query2);
				
				if(sqlRowset2.next()) {
					courseDetailsModel.setKeyConcepts(AppendUtil.stringToList(sqlRowset2.getString("keyconcepts")));
					courseDetailsModel.setMissingConcepts(AppendUtil.stringToList(sqlRowset2.getString("missingconcepts")));
					courseDetailsModel.setExpertID(sqlRowset2.getInt("expertmodelid"));
					SqlRowSet sqlRowset3 = template.queryForRowSet(query3);
					if(sqlRowset3.next()) {
						courseDetailsModel.setMissingLinks(AppendUtil.stringToList(sqlRowset3.getString("missinglinks")));
						courseDetailsModel.setRecallkeyconcepts(Double.parseDouble(sqlRowset3.getString("recallC")));
						courseDetailsModel.setRecallKeylinks(Double.parseDouble(sqlRowset3.getString("recallP")));
					}					
				}
				
				int studentId = sqlRowset.getInt("studentid");
				if(studentId!=0) {
					for (UserDetails student : instructorModel.getStudentDetailsList()) {
						if(student.getUserID()==studentId) {
							studentDetails = student;
						}
					}
				}
				
				modelList.add(courseDetailsModel);
				instructorModel.getStudentResultList().put(studentDetails, courseDetailsModel);
			}
			instructorModel.setStudentModels(modelList);
			return instructorModel;
		}
		
		public CourseDetailsModel ExpertModelValues(int id)
		{
			StringBuilder sqlAppend= new StringBuilder();
			StringBuilder sqlAppend2= new StringBuilder();
			sqlAppend.append("SELECT * FROM expert_model WHERE expertmodelid="+id+";");
			SqlRowSet sqlRowset = template.queryForRowSet(sqlAppend.toString());
			CourseDetailsModel result = new CourseDetailsModel();
			while(sqlRowset.next()) {
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
		
		public InstructorModel getStudentModelsOfClassForDownload(InstructorModel instructorModel) {
			int assignId= instructorModel.getAssignmentDetails().getAssgnmntId();
			ArrayList<CourseDetailsModel> modelList = new ArrayList<CourseDetailsModel>();
			String dateAndTime="";
			String query="select modelid, studentid from student_model_mapping where modelid in (select modelid from student_model where expertmodelid in "
					+ "( select expertmodelid from expert_model where problemID ="+assignId+"))";
			
			SqlRowSet sqlRowset = template.queryForRowSet(query);
			while(sqlRowset.next()) {
				CourseDetailsModel courseDetailsModel = new CourseDetailsModel();
				UserDetails studentDetails = new UserDetails();
				courseDetailsModel.setModel(Integer.toString(sqlRowset.getInt("modelid")));
				courseDetailsModel.setStudentresponseid(sqlRowset.getInt("modelid"));
				
				int modelId = sqlRowset.getInt("modelid");
				String query2 ="select * from student_model where modelID="+modelId;
				String query3 ="select * from student_model2 where modelID="+modelId;
				log.info("modelid: "+modelId);
				SqlRowSet sqlRowset2 = template.queryForRowSet(query2);
				
				if(sqlRowset2.next()) {
					courseDetailsModel.setKeyConcepts(AppendUtil.stringToList(sqlRowset2.getString("keyconcepts")));
					courseDetailsModel.setMissingConcepts(AppendUtil.stringToList(sqlRowset2.getString("missingconcepts")));
					courseDetailsModel.setExpertID(sqlRowset2.getInt("expertmodelid"));
					courseDetailsModel.setText(sqlRowset2.getString("text"));
					courseDetailsModel.setRelation(sqlRowset2.getString("relations"));
					courseDetailsModel.setAllConceptList(AppendUtil.stringToList(sqlRowset2.getString("concepts")));
					courseDetailsModel.setKeyConcepts(AppendUtil.stringToList(sqlRowset2.getString("keyconcepts")));
					courseDetailsModel.setMissingConcepts(AppendUtil.stringToList(sqlRowset2.getString("missingconcepts")));
					//courseDetailsModel.setKeyConcepts(AppendUtil.stringToList(sqlRowset2.getString("keyconcepts")));
					courseDetailsModel.setNoOfConcepts(sqlRowset2.getInt("noofconcepts"));
					courseDetailsModel.setNoOfRelations(sqlRowset2.getInt("noofrelations"));
					courseDetailsModel.setAvgdegree(sqlRowset2.getDouble("avgdegree"));
					courseDetailsModel.setDensity(sqlRowset2.getDouble("density"));
					courseDetailsModel.setDiameter(sqlRowset2.getDouble("diameter"));
					courseDetailsModel.setMeandistance(sqlRowset2.getDouble("meandistance"));
					courseDetailsModel.setSubgraph(sqlRowset2.getDouble("subgroups"));
					courseDetailsModel.setSm_NOofconcepts(sqlRowset2.getDouble("SM_noofconcepts"));
					courseDetailsModel.setSm_NoOfRelations(sqlRowset2.getDouble("SM_noofrelations"));
					courseDetailsModel.setSm_avgdegree(sqlRowset2.getDouble("SM_avgdegree"));
					courseDetailsModel.setSm_density(sqlRowset2.getDouble("SM_density"));
					courseDetailsModel.setSm_meandistance(sqlRowset2.getDouble("SM_meandistance"));
					courseDetailsModel.setSm_diameter(sqlRowset2.getDouble("SM_diameter"));
					courseDetailsModel.setSm_Subgraph(sqlRowset2.getDouble("SM_subgraphs"));
					courseDetailsModel.setSm_Balancedmatching(sqlRowset2.getDouble("SM_balanced"));
					courseDetailsModel.setSm_conceptualmatching(sqlRowset2.getDouble("SM_conceptual"));
					courseDetailsModel.setSm_propositionalmatching(sqlRowset2.getDouble("SM_relational"));
					
					SqlRowSet sqlRowset3 = template.queryForRowSet(query3);
					if(sqlRowset3.next()) {
						courseDetailsModel.setDBAdjacencyMatrix(sqlRowset3.getString("adjacencyMatrix"));
						courseDetailsModel.setPathList(AppendUtil.stringToList(sqlRowset3.getString("pathlist")));
						courseDetailsModel.setMissingLinks(AppendUtil.stringToList(sqlRowset3.getString("missinglinks")));
						courseDetailsModel.setRecallkeyconcepts(Double.parseDouble(sqlRowset3.getString("recallC")));
						courseDetailsModel.setRecallKeylinks(Double.parseDouble(sqlRowset3.getString("recallP")));
						dateAndTime = sqlRowset3.getDate("createddate").toString();
						dateAndTime += " " + sqlRowset3.getTime("createddate").toString();
						courseDetailsModel.setCreatedDateTime(dateAndTime);
					}
					
				}
				//courseDetailsModel.setNoOfKeyConcepts(courseDetailsModel.getNoOfConcepts()-courseDetailsModel.getMissingConcepts().size());
				//courseDetailsModel.setNoOfKeyLinks(courseDetailsModel.getNoOfRelations()-courseDetailsModel.getMissingLinks().size());
				int studentId = sqlRowset.getInt("studentid");
				if(studentId!=0) {
					for (UserDetails student : instructorModel.getStudentDetailsList()) {
						if(student.getUserID()==studentId) {
							studentDetails = student;
						}
					}
				}
				InstructorManagement instructorManagement = new InstructorManagement();
				courseDetailsModel=instructorManagement.convertToAdjacencyMatrix(courseDetailsModel);
				CourseDetailsModel expertParameters = ExpertModelValues(courseDetailsModel.getExpertID());
				courseDetailsModel.setExpert(expertParameters);
				DisplayGraph displayGraph = new DisplayGraph();
				courseDetailsModel=displayGraph.createJsonStudentFeedback(courseDetailsModel,0);
				System.out.println("Common Links: "+courseDetailsModel.getCommonLinks().size());
				modelList.add(courseDetailsModel);
				instructorModel.getModelStudentResultList().put(courseDetailsModel, studentDetails);
			}
			instructorModel.setStudentModels(modelList);
			//instructorModel.setStatus(-3);
			return instructorModel;
		}
		
		public InstructorModel getAssignmentsOfClass(ClassDetails classDetails, InstructorModel instructorModel) {
			String query2 = "select * from assignment where assgntid in (select assgntid from classassignments where classid ="+classDetails.getClassId()+")";
			SqlRowSet sqlRowset2 = template.queryForRowSet(query2);
			ArrayList<AssignmentDetails> assignmentDetailsList = new ArrayList<AssignmentDetails>();
			while(sqlRowset2.next()) {
				AssignmentDetails assignmentDetails = new AssignmentDetails();
				assignmentDetails.setAssgnmntId(sqlRowset2.getInt("assgntid"));
				assignmentDetails.setTitle(sqlRowset2.getString("title"));
				assignmentDetailsList.add(assignmentDetails);
			}
			instructorModel.getClassAssignmentList().put(classDetails, assignmentDetailsList);
			return instructorModel;
		}
		
		public ClassDetails getClassOfAssignment(int assignmentid)
		{
			ClassDetails classdetails = new ClassDetails();;
			SqlRowSet sqlRowset = template.queryForRowSet("select classid from classassignments where assgntid=" + assignmentid);
			if(sqlRowset.next()) {
			    	classdetails.setClassId(sqlRowset.getInt("classid"));
			    	SqlRowSet sqlRowset1 = template.queryForRowSet("select * from class where classid=" + classdetails.getClassId());
					if(sqlRowset1.next())
					{
						classdetails.setClassName(sqlRowset1.getString("classname"));
					    	classdetails.setDescription(sqlRowset1.getString("description"));
					}	    	
			}
			return classdetails;
		}
		
		public AssignmentDetails getAssignmentDetails(int assignId) {
			AssignmentDetails assignmentDetails = new AssignmentDetails();
			String query = "select * from assignment where assgntid="+assignId;
			SqlRowSet sqlRowset = template.queryForRowSet(query);
			if(sqlRowset.next()) {
				assignmentDetails.setTitle(sqlRowset.getString("title"));
				assignmentDetails.setDescription(sqlRowset.getString("description"));
				assignmentDetails.setDirections(sqlRowset.getString("directions"));
			}
			assignmentDetails.setAssgnmntId(assignId);
			log.info("AssignmentName: "+assignmentDetails.getTitle());
			return assignmentDetails;
		}
		
		public int checkIfClassNameExists(String className, int classId, int teacherId) {
			int exist=0;
			String currentClassName;
			String query2 = "select classname from class where classid="+classId +" and teacherid="+teacherId;
			SqlRowSet sqlRowset2 = template.queryForRowSet(query2);
			if(sqlRowset2.next()) {
				currentClassName = sqlRowset2.getString("classname");
				if(currentClassName.equals(className)) {
					return 0;
				}
			}
			ArrayList<String> classNameList = new ArrayList<String>();
			String query = "select classname from class where teacherid="+teacherId;
			SqlRowSet sqlRowset = template.queryForRowSet(query);
			while(sqlRowset.next()) {
				String name = sqlRowset.getString("classname");
				classNameList.add(name);
			}
			log.info("Existing names: ");
			for(String clas: classNameList) {
				log.info(clas+"\n");
				if(clas.toLowerCase().equals(className.toLowerCase())) {
					exist=1;
					log.info("Exists!!!");
					break;
				}
			}
			return exist;
		}
		
		public InstructorModel getAssignmentsOfInstructor(InstructorModel instructorModel) {
			
			return instructorModel;
		}
		
		public UserDetailsModel searchUser (UserDetailsModel userDetailsModel) {
			String role, email, username, firstname, schoolname;
			if(userDetailsModel.getUserDetails().getUserType()=="") {
				role="%%";
			}
			else {
				role="%"+userDetailsModel.getUserDetails().getUserType()+"%";
			}
			
			if(userDetailsModel.getUserDetails().getEmail()=="") {
				email="%%";
			}
			else {
				email="%"+userDetailsModel.getUserDetails().getEmail()+"%";
			}
			
			if(userDetailsModel.getUserDetails().getUserName()=="") {
				username="%%";
			}
			else {
				username="%"+userDetailsModel.getUserDetails().getUserName()+"%";
			}
			
			if(userDetailsModel.getUserDetails().getFirstName()=="") {
				firstname="%%";
			}
			else {
				firstname="%"+userDetailsModel.getUserDetails().getFirstName()+"%";
			}
			
			if(userDetailsModel.getUserDetails().getSchoolName()=="") {
				schoolname="%%";
			}
			else {
				schoolname="%"+userDetailsModel.getUserDetails().getSchoolName()+"%";
			}
			String query="select * from members where role like \""+role+"\" and email like \""+email+"\" and username like \""+username+"\" and firstname like \""
					+firstname+"\" and schoolname like \""+schoolname+"\" order by userid desc";
			
			SqlRowSet sqlRowset = template.queryForRowSet(query);
			while(sqlRowset.next()) {
				UserDetails user = new UserDetails();
				user.setPassword(sqlRowset.getString("password"));
				user.setUserID(sqlRowset.getInt("userid"));
				user.setEmail(sqlRowset.getString("email"));
				user.setUserName(sqlRowset.getString("username"));
				user.setFirstName(sqlRowset.getString("firstname"));
				user.setSchoolName(sqlRowset.getString("schoolname"));
				user.setUserType(sqlRowset.getString("role"));
				user.setStatus("");
				if(user.getUserType().equals("researcher")||user.getUserType().equals("teacher")) {
					String query2="select valid from validresearcher where userid="+user.getUserID();
					SqlRowSet sqlRowset2 = template.queryForRowSet(query2);
					if(sqlRowset2.next()) {
						int valid = sqlRowset2.getInt("valid");
						if(valid==0) {
							user.setStatus("Pending Account Approval");
						}
						else if(valid==1) {
							user.setStatus("Approved");
						}
						else if(valid==2) {
							user.setStatus("Pending Upgrade Approval");
						}
					}
				}
				String query2="select valid from validuser where userid="+user.getUserID();
				SqlRowSet sqlRowset2 = template.queryForRowSet(query2);
				if(sqlRowset2.next()) {
					int valid = sqlRowset2.getInt("valid");
					if(valid==0) {
						if(user.getStatus().equals("")) {
							user.setStatus("Deleted");
						}
						else {
							user.setStatus(user.getStatus()+", Deleted");
						}
					}
					else if(valid==1) {
						if(user.getStatus().equals("")) {
							user.setStatus("Valid");
						}
						else {
							user.setStatus(user.getStatus()+", Valid");
						}
					}
				}
				userDetailsModel.getUserList().add(user);
			}
			return userDetailsModel;
		}
		
		public int approveResearcher (UserDetails userDetails) {
			int status=0;
			String query = "update validresearcher set valid=1 where userid in (select userid from members where username = \""+userDetails.getUserName()+"\")";
			status = template.update(query);
			return status;
		}
		
		public int upgradeToResearcher (UserDetails userDetails) {
			int status=0;
			String query = "update members set role=\"researcher\" where username=\""+userDetails.getUserName()+"\"";
			status = template.update(query);
			return status;
		}
		
		public UserDetails getEmail (UserDetails userDetails) {
			String query2 = "select email from members where username = \""+userDetails.getUserName()+"\"";
			SqlRowSet sqlRowset = template.queryForRowSet(query2);
			while(sqlRowset.next()) {
				userDetails.setEmail(sqlRowset.getString("email"));
			}
			return userDetails;
		}
		
		public int forgotPasswordValidUser(UserDetails userDetails) {
			int status=0;
			String query2 = "select * from members where username = \""+userDetails.getUserName()+"\" and email= \""+userDetails.getEmail()+"\"";
			SqlRowSet sqlRowset = template.queryForRowSet(query2);
			if(sqlRowset.next()) {
				return 1;
			}
			return status;
		}
		
		public int updatePassword(UserDetails userDetails) {
			int status=0;
			String query = "update members set password=\""+userDetails.getPassword()+"\" where username= \""+userDetails.getUserName()+"\"";
			status = template.update(query);
			return status;
		}
		
		public int changeTempPassword(UserDetails userDetails) {
			int status=0;
			String query = "update members set password=\""+userDetails.getPassword()+"\" where username= \""+userDetails.getUserName()+"\" "
					+ "and password=\""+userDetails.getTempPassword()+"\"";
			status = template.update(query);
			return status;
		}
		
		public int forgotPasswordValidStudent(UserDetails userDetails) {
			int status=0;
			String query2 = "select * from members where username = \""+userDetails.getUserName()+"\" and answer= \""+userDetails.getAnswer()+"\"";
			SqlRowSet sqlRowset = template.queryForRowSet(query2);
			if(sqlRowset.next()) {
				return 1;
			}
			return status;
		}
		
		public int changePasswordStudent(UserDetails userDetails) {
			int status=0;
			String query = "update members set password=\""+userDetails.getPassword()+"\" where username= \""+userDetails.getUserName()+"\"";
			status = template.update(query);
			return status;
		}
		
		public int deleteUser(int userid) {
			int status=0;
			String query="update validuser set valid=0 where userid="+userid;
			status = template.update(query);
			return status;
		}
		
		public int validateUser(int userid) {
			int status=0;
			/*String query2 = "select valid from validuser where userid="+userid;
			SqlRowSet sqlRowset = template.queryForRowSet(query2);
			if(!sqlRowset.next()) {
				String query3="insert into validuser values ("+userid+", 1)";
				status = template.update(query3);
				return 1;
			}*/
			String query="update validuser set valid=1 where userid="+userid;
			status = template.update(query);
			return status;
		}
}
