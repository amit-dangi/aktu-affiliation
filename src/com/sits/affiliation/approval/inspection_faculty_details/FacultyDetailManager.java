package com.sits.affiliation.approval.inspection_faculty_details;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.sits.general.ReadProps;
import java.util.List;
import java.io.File;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONObject;
import org.apache.commons.fileupload.FileItem;

import com.sits.conn.DBConnection;
import com.sits.general.ApplicationConstants;
import com.sits.general.General;
import com.sits.general.Logging;

public class FacultyDetailManager {
	static Logger l = Logger.getLogger("exceptionlog");

	public static JSONObject saveFacDetail(JSONArray aflist, String ip, String user_id, String login_id,List<FileItem> items) {
		l = Logger.getLogger("exceptionlog");
		System.out.println("user_id||"+user_id);
		Connection conn = null;
		PreparedStatement psmt = null;
		String qry = "",mastid="";
		int count = 0, i = 0, k=1;
		JSONObject jsonObj = new JSONObject();
		int consObj = aflist.size();
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			for (int j = 0; j < consObj; j++) {
				i = 0;
				JSONObject obj = (JSONObject) aflist.get(j);
				if(General.checknull(obj.get("FD_STATUS").toString()).equals("N")){
					
					qry = "INSERT INTO af_faculty_detail (AF_FD_ID, fac_name, pan, father_name, "
							+ "fac_id, department, course_name, branch_name, shift, "
							+ "qualification, designation, is_director, CREATED_BY, CREATED_DATE,"
							+ " CREATED_MACHINE,gender,doj) values (?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,STR_TO_DATE(?,'%d/%m/%Y'))";
					psmt = conn.prepareStatement(qry, psmt.RETURN_GENERATED_KEYS);
					psmt.setString(++i, General.checknull(user_id).trim());
					psmt.setString(++i, General.checknull(obj.get("FAC_NAME").toString()));
					/*psmt.setString(++i, General.checknull(obj.get("AADHAR").toString()));*/
					psmt.setString(++i, General.checknull(obj.get("PAN").toString()));
					psmt.setString(++i, General.checknull(obj.get("F_NAME").toString()));
					psmt.setString(++i, General.checknull(obj.get("FAC_ID").toString()));
					psmt.setString(++i, General.checknull(obj.get("DEPT").toString()));
					psmt.setString(++i, General.checknull(obj.get("C_NAME").toString()));
					psmt.setString(++i, General.checknull(obj.get("B_NAME").toString()));
					psmt.setString(++i, General.checknull(obj.get("SHIFT").toString()));
					psmt.setString(++i, General.checknull(obj.get("QUALI").toString()));
					psmt.setString(++i, General.checknull(obj.get("DESG").toString()));
					psmt.setString(++i, General.checknull(obj.get("IS_DIR").toString()));
					psmt.setString(++i, General.checknull(login_id));
					psmt.setString(++i, General.checknull(ip));
					psmt.setString(++i, General.checknull(obj.get("FAC_GEN").toString()));
					psmt.setString(++i, General.checknull(obj.get("FAC_DOJ").toString()).equals("") ? null
							: General.checknull(obj.get("FAC_DOJ").toString()));
					count = psmt.executeUpdate();
					ResultSet rs = psmt.getGeneratedKeys();
					while(rs.next()) {
						mastid=(rs.getString(1));
					}
				}
				if(General.checknull(obj.get("FD_STATUS").toString()).equals("U")){
					mastid = General.checknull(obj.get("FD_MID").toString());
					qry = "UPDATE af_faculty_detail SET fac_name=?, pan=?, father_name=?,fac_id=?, "
							+ "department=?, course_name=?, branch_name=?, shift=?,  qualification=?, "
							+ "designation=?, is_director=?, UPDATED_BY=?, UPDATE_DATE=now(), UPDATE_MACHINE=?,gender=?,doj=str_to_date(?,'%d/%m/%Y') "
							+ "WHERE FD_ID=?";
					psmt = conn.prepareStatement(qry);
					psmt.setString(++i, General.checknull(obj.get("FAC_NAME").toString()));
//					psmt.setString(++i, General.checknull(obj.get("AADHAR").toString()));
					psmt.setString(++i, General.checknull(obj.get("PAN").toString()));
					psmt.setString(++i, General.checknull(obj.get("F_NAME").toString()));
					psmt.setString(++i, General.checknull(obj.get("FAC_ID").toString()));
					psmt.setString(++i, General.checknull(obj.get("DEPT").toString()));
					psmt.setString(++i, General.checknull(obj.get("C_NAME").toString()));
					psmt.setString(++i, General.checknull(obj.get("B_NAME").toString()));
					psmt.setString(++i, General.checknull(obj.get("SHIFT").toString()));
					psmt.setString(++i, General.checknull(obj.get("QUALI").toString()));
					psmt.setString(++i, General.checknull(obj.get("DESG").toString()));
					psmt.setString(++i, General.checknull(obj.get("IS_DIR").toString()));
					psmt.setString(++i, General.checknull(login_id));
					psmt.setString(++i, General.checknull(ip));
					psmt.setString(++i, General.checknull(obj.get("FAC_GEN").toString()));
					psmt.setString(++i, General.checknull(obj.get("FAC_DOJ").toString()).equals("") ? null
							: General.checknull(obj.get("FAC_DOJ").toString()));
					psmt.setString(++i, mastid);
					count = psmt.executeUpdate();
				}
				
				 if (items != null) {
		              Iterator<FileItem> iter = items.iterator();
		              while (iter.hasNext()) {
		                FileItem fileItem = iter.next();
		                if (fileItem.getFieldName().equals("PROFILE_PIC_"+k))
		                saveDoc(ip, "af_faculty_detail", mastid, user_id, conn, fileItem, "");
		                   
		              } 
		            } 
				++k;
			}

			if (count > 0) {
				jsonObj.put("msg", "Faculty Details Submit Successfully");
				jsonObj.put("status", "1");
				conn.commit();
			} else {
				jsonObj.put("status", "0");
				jsonObj.put("msg", ApplicationConstants.FAIL);
				conn.rollback();
			}
		} catch (Exception e) {
			if (e.getMessage().contains("unique")) {
				jsonObj.put("status", "0");
				jsonObj.put("msg", ApplicationConstants.UNIQUE_CONSTRAINT);
			} else {
				jsonObj.put("status", "0");
				jsonObj.put("msg", ApplicationConstants.FAIL);
			}
			System.out.println("Exception in FacultyDetailManager[SAVE]" + " " + e.getMessage());
			l.fatal(Logging.logException("FacultyDetailManager[SAVE]", e.toString()));
		} finally {
			try {
				conn.close();
				psmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Exception in FacultyDetailManager[SAVE]" + " " + ex.getMessage());
			}
		}
		return jsonObj;
	}
	
	 public static boolean saveDoc(String machine, String tablename, String mastid, String userid, Connection conn, FileItem fileItem, String attid) {
		    try {
		      String attachid = attid;
		      if (attachid.equals("")) {
		        attachid = filedetailsave(machine, fileItem.getName(), mastid, userid, fileItem.getSize(), tablename, conn);
		      } else {
		        String qry2 = "select file_attachment_id from file_attachment where reference_id='"+mastid+"'";
		        PreparedStatement psmt6 = conn.prepareStatement(qry2);
		        ResultSet rst3 = psmt6.executeQuery();
		        if (rst3.next())
		          attachid = General.checknull(rst3.getString(1)); 
		        filedetailupdate(machine, fileItem.getName(), mastid, userid, fileItem.getSize(), tablename, attid, conn);
		      } 
		      String directoryName = String.valueOf(ReadProps.getkeyValue("document.path", "sitsResource")) + "AKTU_AFFILIATION_PORTAL/FACULTY_PHOTO/"+userid;
		      File directory = new File(directoryName);
		      if (!directory.isDirectory())
		        directory.mkdirs(); 
		      File file = new File(String.valueOf(directoryName) +"/"+ mastid + "_" + fileItem.getName());
		      fileItem.write(file);
		    } catch (Exception e) {
		      System.out.println("Error in UploadAssignmentManager[saveDoc] : " + e.getMessage());
		      l.fatal(Logging.logException("UploadAssignmentManager[saveDoc]", e.toString()));
		      return false;
		    } 
		    return true;
		  }
		  
		  public static String filedetailsave(String machine, String name, String mastid, String userid, long size, String tablename, Connection conn) {
		    PreparedStatement psmt = null;
		    ResultSet rst = null;
		    String qry = "";
		    String attachid = "";
		    int count = 0;
		    try {
		      String Qry = "SELECT IFNULL(MAX(file_attachment_id)+1,1) FROM file_attachment";
		      psmt = conn.prepareStatement(Qry);
		      rst = psmt.executeQuery();
		      if (rst.next())
		        attachid = General.checknull(rst.getString(1)); 
		      qry = "INSERT INTO file_attachment (file_attachment_id,file_name,file_type,table_name,reference_id,CREATED_BY,CREATED,MACHINE)  VALUES (?, ?,?,?,?,?,now(),?)";
		      psmt = conn.prepareStatement(qry);
		      psmt.setString(1, attachid);
		      psmt.setString(2, name);
		      psmt.setString(3, "emp_doc_detail");
		      psmt.setString(4, tablename);
		      psmt.setString(5, mastid);
		      psmt.setString(6, userid);
		      psmt.setString(7, machine);
		      count = psmt.executeUpdate();
		      if (count == 0)
		        attachid = ""; 
		    } catch (Exception e) {
		      attachid = "";
		      System.out.println("EXCEPTION IS CAUSED BY: FileCreationManager[saveRecord] " + e.getMessage().trim().toUpperCase());
		      l.fatal(Logging.logException("FileCreationManager [saveRecord]", e.toString()));
		      return attachid;
		    } 
		    return attachid;
		  }
		  
		  public static void filedetailupdate(String machine, String name, String mastid, String userid, long size, String tablename, String attid, Connection conn) {
		    PreparedStatement psmt = null;
		    ResultSet rst = null;
		    String qry = "";
		    int count = 0;
		    try {
		      qry = "UPDATE file_attachment SET file_name=?, file_type=? WHERE  table_name= ? and reference_id=? ";
		      psmt = conn.prepareStatement(qry);
		      psmt.setString(1, name);
		      psmt.setString(2, "emp_doc_detail");
		      psmt.setString(3, tablename);
		      psmt.setString(4, mastid);
		      count = psmt.executeUpdate();
		    } catch (Exception e) {
		      System.out.println("EXCEPTION IS CAUSED BY: FileCreationManager[saveRecord] " + e.getMessage().trim().toUpperCase());
		      l.fatal(Logging.logException("FileCreationManager [saveRecord]", e.toString()));
		    } 
		  }
	
	public static JSONArray getSavedData(String id) {
		JSONArray jsonArray = new JSONArray();
		PreparedStatement pst = null;
		Connection conn = null;
		ResultSet rst = null;
		String query = "";
		query = "select date_format(doj,'%d/%m/%Y') as doj, gender, FD_ID,fac_id,department,course_name,branch_name,shift,AF_FD_ID,"
				+ "fac_name,aadhar,pan,father_name,qualification,designation,is_director "
			/*	+ ","
				+ "(select  concat(reference_id,'_',file_name)file_name from file_attachment "
				+ " where CREATED_BY=? and reference_id=FD_ID )file_name,"
				+ "(select file_attachment_id from file_attachment  where "
				+ "CREATED_BY=? and reference_id=FD_ID)file_attachment_id"*/
				+ " from af_faculty_detail where AF_FD_ID=?; ";
		try {
			conn = DBConnection.getConnection();
			pst = conn.prepareStatement(query);
			pst.setString(1, General.checknull(id));
			/*pst.setString(2, General.checknull(id));
			pst.setString(3, General.checknull(id));
		*///System.out.println("pst::::"+pst);
			rst = pst.executeQuery();
			while (rst.next()) {
				JSONObject json = new JSONObject();
				json.put("FD_ID", General.checknull(rst.getString("FD_ID")));
				json.put("fac_id", General.checknull(rst.getString("fac_id")));
				json.put("department", General.checknull(rst.getString("department")));
				json.put("course_name", General.checknull(rst.getString("course_name")));
				json.put("branch_name", General.checknull(rst.getString("branch_name")));
				json.put("shift", General.checknull(rst.getString("shift")));
				json.put("AF_FD_ID", General.checknull(rst.getString("AF_FD_ID")));
				json.put("fac_name", General.checknull(rst.getString("fac_name")));
				json.put("aadhar", General.checknull(rst.getString("aadhar")));
				json.put("pan", General.checknull(rst.getString("pan")));
				json.put("father_name", General.checknull(rst.getString("father_name")));
				json.put("qualification", General.checknull(rst.getString("qualification")));
				json.put("designation", General.checknull(rst.getString("designation")));
				json.put("is_director", General.checknull(rst.getString("is_director")));
				/*json.put("file_id",	General.checknull(rst.getString("file_attachment_id"))) ;
				json.put("file_name", General.checknull(rst.getString("file_name")));
				*/json.put("doj", General.checknull(rst.getString("doj")));
				json.put("gender", General.checknull(rst.getString("gender")));
				jsonArray.add(json);
			}
		} catch (Exception e) {
			System.out.println("FileName=[FacultyDetailManager],MethodName=[getSavedData()]" + e.getMessage().toString());
			l.fatal(Logging.logException("FileName=[FacultyDetailManager],MethodName=[getSavedData()]", e.getMessage().toString()));
		} finally {
			try {
				if (rst != null) {
					rst.close();
					rst = null;
				}
				if (pst != null) {
					pst.close();
					pst = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (final Exception e) {
				l.fatal(Logging.logException("FileName=[FacultyDetailManager],MethodName=[getSavedData()]", e.getMessage().toString()));
			}
		}
		return jsonArray;
	}

	public static String delete(String id, String file_name, String user_id) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rst = null;
		String qry="";
		int delCnt=0,cnt=0;
		String str ="";
		try {
			conn=DBConnection.getConnection();
			conn.setAutoCommit(false);
			qry="DELETE FROM af_faculty_detail WHERE FD_ID=?";
			psmt=conn.prepareStatement(qry);
			psmt.setString(1,General.checknull(id));
			delCnt=psmt.executeUpdate();
			if(delCnt>0) {
				qry="";psmt=null;
				qry = "delete from file_attachment where reference_id='"+id+"' and table_name='af_faculty_detail'";
				psmt=conn.prepareStatement(qry);
				cnt=psmt.executeUpdate();
				if(cnt>0){
			        String directoryName = String.valueOf(ReadProps.getkeyValue("document.path", "sitsResource")) + "AKTU_AFFILIATION_PORTAL/FACULTY_PHOTO/" + user_id + "/" + file_name;
			        File file = new File(directoryName);
			        file.delete();
					conn.commit();
					str = ApplicationConstants.DELETED;
				}
				conn.commit();
				str = ApplicationConstants.DELETED;
			}else {
				conn.rollback();
				str = ApplicationConstants.FAIL;
			}
		} catch (Exception e) {
			str=e.getMessage().toString() ;
			if(str.indexOf("foreign key constraint fails") != -1)
				str = ApplicationConstants.DELETE_FORIEGN_KEY;
			else 
			str = ApplicationConstants.EXCEPTION_MESSAGE;
			l.fatal(Logging.logException("FacultyDetailManager[delete]", e.getMessage().toString()));
			System.out.println("Error in FacultyDetailManager[delete] : "+e.getMessage());
		} finally {
			try {
				if(psmt != null) psmt.close();
				if(rst != null) rst.close();
				conn.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}						
		}
		return str;		
	}	
	public static String deletattchdata(String id, String name,String user_id) {
	    l = Logger.getLogger("exceptionlog");
	    String cSql = "", msg = "";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rst = null;
	    int count = 0;
	    try {
	      conn = DBConnection.getConnection();
	      conn.setAutoCommit(false);
	        cSql = "delete from file_attachment where file_attachment_id=? ";
	        pstmt = conn.prepareStatement(cSql);
	        pstmt.setString(1, id.trim());
	        count = pstmt.executeUpdate();
	        if (count > 0) {
	          String directoryName = String.valueOf(ReadProps.getkeyValue("document.path", "sitsResource")) + "AKTU_AFFILIATION_PORTAL/FACULTY_PHOTO/" + user_id + "/" + name;
	          File file = new File(directoryName);
	          file.delete();
	          msg = "Record Deleted Successfully ";
	          conn.commit();
	        } 
	    } catch (Exception e) {
	      System.out.println("Error in FacultyDetailManager[deletattchdata] : " + e.getMessage());
	      l.fatal(Logging.logException("FacultyDetailManager[deletattchdata]", e.getMessage().toString()));
	    } finally {
	      try {
	        if (pstmt != null)
	          pstmt = null; 
	        if (rst != null)
	          rst = null; 
	        if (conn != null)
	          conn.close(); 
	      } catch (Exception e) {
	        e.printStackTrace();
	      } 
	    } 
	    return msg;
	  }
	
	/*public static ArrayList<FacultyDetailModel> getSavedData(String id) {
		ArrayList<FacultyDetailModel> al = new ArrayList<FacultyDetailModel>();
		PreparedStatement pst = null;
		Connection conn = null;
		ResultSet rst = null;
		String query = "";
		query = "select date_format(doj,'%d/%m/%Y') as doj, gender, FD_ID,fac_id,department,course_name,branch_name,shift,AF_FD_ID,"
				+ "fac_name,aadhar,pan,father_name,qualification,designation,is_director,"
				+ "(select  concat(reference_id,'_',file_name)file_name from file_attachment "
				+ " where CREATED_BY=? and reference_id=FD_ID )file_name,"
				+ "(select file_attachment_id from file_attachment  where "
				+ "CREATED_BY=? and reference_id=FD_ID)file_attachment_id"
				+ " from af_faculty_detail where AF_FD_ID=?; ";
		try {
			conn = DBConnection.getConnection();
			pst = conn.prepareStatement(query);
			pst.setString(1, General.checknull(id));
			pst.setString(2, General.checknull(id));
			pst.setString(3, General.checknull(id));
			System.out.println("pst::::"+pst);
			rst = pst.executeQuery();
			while (rst.next()) {
				FacultyDetailModel model = new FacultyDetailModel();
				model.setFD_ID(General.checknull(rst.getString("FD_ID")));
				model.setFac_id(General.checknull(rst.getString("fac_id")));
				model.setDepartment(General.checknull(rst.getString("department")));
				model.setCourse_name( General.checknull(rst.getString("course_name")));
				model.setBranch_name(General.checknull(rst.getString("branch_name")));
				model.setShift( General.checknull(rst.getString("shift")));
				model.setAF_FD_ID(General.checknull(rst.getString("AF_FD_ID")));
				model.setFac_name(General.checknull(rst.getString("fac_name")));
				model.setAadhar(General.checknull(rst.getString("aadhar")));
				model.setPan(General.checknull(rst.getString("pan")));
				model.setFather_name( General.checknull(rst.getString("father_name")));
				model.setQualification(General.checknull(rst.getString("qualification")));
				model.setDesignation(General.checknull(rst.getString("designation")));
				model.setIs_director(General.checknull(rst.getString("is_director")));
				model.setFile_attachment_id(General.checknull(rst.getString("file_attachment_id"))) ;
				model.setFile_name( General.checknull(rst.getString("file_name")));
				model.setDoj(General.checknull(rst.getString("doj")));
				model.setGender(General.checknull(rst.getString("gender")));
				al.add(model);
			}
		} catch (Exception e) {
			System.out.println("FileName=[FacultyDetailManager],MethodName=[getSavedData()]" + e.getMessage().toString());
			l.fatal(Logging.logException("FileName=[FacultyDetailManager],MethodName=[getSavedData()]", e.getMessage().toString()));
		} finally {
			try {
				if (rst != null) {
					rst.close();
					rst = null;
				}
				if (pst != null) {
					pst.close();
					pst = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (final Exception e) {
				l.fatal(Logging.logException("FileName=[FacultyDetailManager],MethodName=[getSavedData()]", e.getMessage().toString()));
			}
		}
		return al;
	}*/
	
	public static JSONArray getSavedDataFile(String id) {
		JSONArray jsonArray = new JSONArray();
		PreparedStatement pst = null;
		Connection conn = null;
		ResultSet rst = null;
		String query = "";
		query = "select FD_ID, concat(b.reference_id,'_',b.file_name) file_name, file_attachment_id "
				+ "from file_attachment b, af_faculty_detail a where b.CREATED_BY=a.AF_FD_ID and b.reference_id=a.FD_ID "
				+ "and AF_FD_ID=? and table_name='af_faculty_detail' group by b.reference_id order by b.CREATED desc";
		try {
			conn = DBConnection.getConnection();
			pst = conn.prepareStatement(query);
			pst.setString(1, General.checknull(id));
			/*pst.setString(2, General.checknull(id));
			pst.setString(3, General.checknull(id));
			 *///	
			//System.out.println("pst::::"+pst);
			rst = pst.executeQuery();
			while (rst.next()) {
				JSONObject json = new JSONObject();
				json.put("FD_ID", General.checknull(rst.getString("FD_ID")));
				json.put("file_id",	General.checknull(rst.getString("file_attachment_id"))) ;
				json.put("file_name", General.checknull(rst.getString("file_name")));
				jsonArray.add(json);
			}
		} catch (Exception e) {
			System.out.println("FileName=[FacultyDetailManager],MethodName=[getSavedDataFile()]" + e.getMessage().toString());
			l.fatal(Logging.logException("FileName=[FacultyDetailManager],MethodName=[getSavedDataFile()]", e.getMessage().toString()));
		} finally {
			try {
				if (rst != null) {
					rst.close();
					rst = null;
				}
				if (pst != null) {
					pst.close();
					pst = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (final Exception e) {
				l.fatal(Logging.logException("FileName=[FacultyDetailManager],MethodName=[getSavedDataFile()]", e.getMessage().toString()));
			}
		}
		return jsonArray;
	}
}