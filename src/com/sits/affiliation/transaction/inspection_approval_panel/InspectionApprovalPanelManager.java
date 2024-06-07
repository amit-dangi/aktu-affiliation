package com.sits.affiliation.transaction.inspection_approval_panel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.sits.general.Logging;
import com.sits.general.General;
import com.sits.conn.DBConnection;
import org.apache.log4j.Logger;

public class InspectionApprovalPanelManager
{
    static Logger log;
    
    static {
        InspectionApprovalPanelManager.log = Logger.getLogger("exceptionlog");
    }
    
    public static InspectionApprovalPanelModel saveRecord(final InspectionApprovalPanelModel model, final String user_id, final String ip, final String fstatus)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement ustPstmt = null;
        PreparedStatement updetPstmt = null;
        ResultSet rst = null;
        String cInsert = "";
        String cSql = "";
        String id = "";
        String upsql = "";
        String updetsql = "";
        int updtCnt = 0;
        int i = 0;
        String str = "";
        int[] detcnt = null;
        int[] detcnt2 = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            cSql = " SELECT LPAD(CONVERT(IFNULL(MAX(SUBSTR(panel_id,3,6)),0)+1,SIGNED INTEGER),4,'0') FROM af_approv_pannel_mast";
            pstmt = conn.prepareStatement(cSql);
            rst = pstmt.executeQuery();
            if (rst.next()) {
                id = General.checknull(rst.getString(1)).trim();
            }
            pstmt = null;
            cInsert = " INSERT INTO af_approv_pannel_mast ( ";
            cInsert = String.valueOf(cInsert) + "  panel_id,panel_code,panel_name,isActive,CREATED_BY,CREATED_DATE,CREATED_MACHINE ";
            cInsert = String.valueOf(cInsert) + " ) VALUES (?,?,?,?,?, now(), ? ";
            cInsert = String.valueOf(cInsert) + " )";
            pstmt = conn.prepareStatement(cInsert);
            upsql = " update af_approv_pannel_mast set  panel_code=?, panel_name=?,isActive=?, UPDATED_BY=?, UPDATED_DATE=now(),UPDATED_MACHINE=? where panel_id=?";
            ustPstmt = conn.prepareStatement(upsql);
            if (fstatus.equals("N")) {
                pstmt.setString(1, "PD" + id);
                pstmt.setString(2, General.checknull(model.getPanel_code()));
                pstmt.setString(3, General.checknull(model.getPanel_name()));
                pstmt.setString(4, General.checknull(model.getIs_active()));
                pstmt.setString(5, user_id.trim());
                pstmt.setString(6, ip.trim());
                updtCnt = pstmt.executeUpdate();
            } 
            else if (fstatus.equals("E")) {
                ustPstmt.setString(1, General.checknull(model.getPanel_code()));
                ustPstmt.setString(2, General.checknull(model.getPanel_name()));
                ustPstmt.setString(3, General.checknull(model.getIs_active()));
                ustPstmt.setString(4, user_id.trim());
                ustPstmt.setString(5, ip.trim());
                ustPstmt.setString(6, General.checknull(model.getPanel_id()));
                updtCnt = ustPstmt.executeUpdate();
                           }
            if (updtCnt > 0) {
                final ArrayList<InspectionApprovalPanelModel> al = (ArrayList<InspectionApprovalPanelModel>)model.getList();
                System.out.println("al||"+al.toString());
                String qry = "";
                i = 0;
                pstmt = null;
                qry = "INSERT INTO af_approv_pannel_detail (panel_id,member_type,department,designation,email_id,contant_no,is_convenor,issActive,CREATED_BY, CREATED_DATE, CREATED_MACHINE,emp_type) values(?,?,?,?,?,?,?,?,?,now(),?,?) ";
                pstmt2 = conn.prepareStatement(qry);
                updetsql = " update af_approv_pannel_detail set member_type=?,department=?,designation=?,email_id=?,contant_no=?,is_convenor=?,issActive=?, UPDATED_BY=?,UPDATED_DATE=now(),UPDATED_MACHINE=?,emp_type=?  where Panel_det_id=?";
                updetPstmt = conn.prepareStatement(updetsql);
                for (int t = 0; t < al.size(); t++) {
                     InspectionApprovalPanelModel mdl = (InspectionApprovalPanelModel) al.get(t); 
                     if (General.checknull(mdl.getPanel_det_id()).equals("")) {
                        if (fstatus.equals("N")) {
                            pstmt2.setString(1, "PD" + id);
                        }
                        else {
                            pstmt2.setString(1, General.checknull(model.getPanel_id()));
                        }
                        
                        pstmt2.setString(2, General.checknull(mdl.getMember_type().trim()));
                        pstmt2.setString(3, General.checknull(mdl.getDepartment() .trim()));
                        pstmt2.setString(4, General.checknull(mdl.getDesignation().trim()));
                        pstmt2.setString(5, General.checknull(mdl.getEmail_id().trim()));
                        pstmt2.setString(6, General.checknull(mdl.getContact_no().trim()));
                        pstmt2.setString(7, General.checknull(mdl.getIs_convenor().trim()));
                        pstmt2.setString(8, General.checknull(mdl.getIss_active().trim()));
                        pstmt2.setString(9, General.checknull(user_id.trim()));
                        pstmt2.setString(10, ip.trim());
                        pstmt2.setString(11, General.checknull(mdl.getEmp_type()));
                        pstmt2.addBatch();
                    }
                    else {
                        updetPstmt.clearParameters();
                        updetPstmt.setString(1, General.checknull(mdl.getMember_type().trim()));
                        updetPstmt.setString(2, General.checknull(mdl.getDepartment().trim()));
                        updetPstmt.setString(3, General.checknull(mdl.getDesignation().trim()));
                        updetPstmt.setString(4, General.checknull(mdl.getEmail_id().trim()));
                        updetPstmt.setString(5, General.checknull(mdl.getContact_no().trim()));
                        updetPstmt.setString(6, General.checknull(mdl.getIs_convenor().trim()));
                        updetPstmt.setString(7, General.checknull(mdl.getIss_active().trim()));
                        updetPstmt.setString(8, General.checknull(user_id.trim()));
                        updetPstmt.setString(9, ip.trim());
                        updetPstmt.setString(10, General.checknull(mdl.getEmp_type()));
                        updetPstmt.setString(11, General.checknull(mdl.getPanel_det_id()));
                        updetPstmt.addBatch();
                    }
                }
            }
            detcnt = pstmt2.executeBatch();
            detcnt2 = updetPstmt.executeBatch();
            if (detcnt.length > 0 || detcnt2.length > 0) {
                conn.commit();
                if (fstatus.equals("N")) {
                    model.setErrMsg("Record Saved Successfully"); 
                }
                else {
                    model.setErrMsg("Record Updated Successfully");
                }
                model.setValid(true);
            }
            else {
                conn.rollback();
                model.setErrMsg("Unable to Process Request Kindly Contact Your Admin");
                model.setValid(false);
            }
        }
        catch (Exception e) {
            str = e.getMessage().toString();
            if (str.indexOf("Duplicate entry") != -1) {
                str = "Record  Already Exist";
            }
            else {
                str = "Unable to Process Request Kindly Contact Your Admin";
            }
            model.setErrMsg(str);
            model.setValid(false);
            InspectionApprovalPanelManager.log.fatal((Object)Logging.logException("InspectionApprovalPanelManager[save]", e.getMessage().toString()));
            System.out.println("Error in InspectionApprovalPanelManager[save] : " + e.getMessage());
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (rst != null) {
                    rst.close();
                }
                conn.close();
                str = "";
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            return model;
        }
        finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (rst != null) {
                    rst.close();
                }
                conn.close();
                str = "";
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return model;
    }
    
    public static ArrayList<InspectionApprovalPanelModel> searchRecord(final InspectionApprovalPanelModel qModel) {
        final ArrayList<InspectionApprovalPanelModel> list = new ArrayList<InspectionApprovalPanelModel>();
        String cSql = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rst = null;
        String panel_id = "";
        String panel_code = "";
        String panel_name = "";
        String isActive = "";
        int i = 1;
        try {
            panel_id = General.checknull(qModel.getPanel_id()).trim();
            panel_code = General.checknull(qModel.getPanel_code()).trim();
            panel_name = General.checknull(qModel.getPanel_name()).trim();
            isActive = General.checknull(qModel.getIs_active()).trim();
            conn = DBConnection.getConnection();
            cSql = " select panel_id, panel_code,panel_name,isActive ";
            cSql = String.valueOf(cSql) + " from af_approv_pannel_mast ";
            cSql = String.valueOf(cSql) + " WHERE 1=1 ";
            if (!panel_code.trim().equals("")) {
                cSql = String.valueOf(cSql) + " AND panel_code like ? ";
            }
            if (!panel_name.trim().equals("")) {
                cSql = String.valueOf(cSql) + " AND panel_name like ? ";
            }
            if (!isActive.trim().equals("")) {
                cSql = String.valueOf(cSql) + " AND isActive = ? ";
            }
            pstmt = conn.prepareStatement(cSql);
            if (!panel_id.trim().equals("")) {
                pstmt.setString(i++, "%" + panel_id.toUpperCase() + "%");
            }
            if (!panel_code.trim().equals("")) {
                pstmt.setString(i++, "%" + panel_code.toUpperCase() + "%");
            }
            if (!panel_name.trim().equals("")) {
                pstmt.setString(i++, "%" + panel_name.toUpperCase() + "%");
            }
            if (!isActive.trim().equals("")) {
                pstmt.setString(i++, "%" + isActive.toUpperCase() + "%");
            }
            //System.out.println("Search:: " + pstmt);
            rst = pstmt.executeQuery();
            if (rst.next()) {
                do {
                    final InspectionApprovalPanelModel qd = new InspectionApprovalPanelModel();
                    panel_id = General.checknull(rst.getString("PANEL_ID"));
                    panel_code = General.checknull(rst.getString("PANEL_CODE"));
                    panel_name = General.checknull(rst.getString("PANEL_NAME"));
                    isActive = General.checknull(rst.getString("isActive"));
                    qd.setPanel_id(panel_id);
                    qd.setPanel_code(panel_code);
                    qd.setPanel_name(panel_name);
                    qd.setIs_active(isActive);
                    list.add(qd);
                } while (rst.next());
            }
        }
        
        catch (Exception e) {
            System.out.println("Error in InspectionApprovalPanelManager[searchRecord] : " + e.getMessage());
            InspectionApprovalPanelManager.log.fatal((Object)Logging.logException("InspectionApprovalPanelManager[searchRecord]", e.getMessage().toString()));
            try {
                if (pstmt != null) {
                    pstmt = null;
                }
                if (rst != null) {
                    rst = null;
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            return list;
        }
        finally {
            try {
                if (pstmt != null) {
                    pstmt = null;
                }
                if (rst != null) {
                    rst = null;
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
         
        }
        return list;
    }
    
    public static String deleteDetailRecord(final String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        final ResultSet rst = null;
        String cInsert = "";
        String st = "";
        int updtCnt = 0;
        String str = "";
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            cInsert = "delete from af_approv_pannel_detail  WHERE Panel_det_id=?";
            pstmt = conn.prepareStatement(cInsert);
            pstmt.setString(1, id.trim());
            updtCnt = pstmt.executeUpdate();
            if (updtCnt > 0) {
                conn.commit();
                st = "Record Deleted Successfully";
            }
            else {
                conn.rollback();
                st = "Unable to Process Request Kindly Contact Your Admin";
            }
        }
        catch (Exception e) {
            str = e.getMessage().toString();
            if (str.indexOf("foreign key constraint fails") != -1) {
                str = "Record is already mapped";
            }
            else {
                str = "Unable to Process Request Kindly Contact Your Admin";
            }
            InspectionApprovalPanelManager.log.fatal((Object)Logging.logException("InspectionApprovalPanelManager[delete]", e.getMessage().toString()));
            System.out.println("Error in InspectionApprovalPanelManager[delete] : " + e.getMessage());
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                conn.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            return st;
        }
        finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                
                conn.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return st;
    }
    
   
    public static InspectionApprovalPanelModel deleteRecord(final String q_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        final ResultSet rst = null;
        String cInsert = "";
        int updtCnt = 0;
        int updtCnt2 = 0;
        String str = "";
        final InspectionApprovalPanelModel qModel = new InspectionApprovalPanelModel();
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            cInsert = "delete from af_approv_pannel_detail  WHERE panel_id=? ";
            pstmt = conn.prepareStatement(cInsert);
            pstmt.setString(1, q_id.trim());
            updtCnt = pstmt.executeUpdate();
            if (updtCnt > 0) {
                cInsert = "";
                pstmt = null;
                cInsert = "delete from af_approv_pannel_mast  WHERE panel_id=?";
                pstmt = conn.prepareStatement(cInsert);
                pstmt.setString(1, q_id.trim());
                updtCnt2 = pstmt.executeUpdate();
            }
            if (updtCnt2 > 0 && updtCnt > 0) {
                conn.commit();
                qModel.setErrMsg("Record Deleted Successfully");
                qModel.setValid(true);
            }
            else {
                conn.rollback();
                qModel.setErrMsg("Unable to Process Request Kindly Contact Your Admin");
                qModel.setValid(false);
            }
        }
        catch (Exception e) {
            str = e.getMessage().toString();
            if (str.indexOf("foreign key constraint fails") != -1) {
                str = "Record is already mapped";
            }
            else {
                str = "Unable to Process Request Kindly Contact Your Admin";
            }
            qModel.setErrMsg(str);
            qModel.setValid(false);
            InspectionApprovalPanelManager.log.fatal((Object)Logging.logException("InspectionApprovalPanelManager[delete]", e.getMessage().toString()));
            System.out.println("Error in InspectionApprovalPanelManager[delete] : " + e.getMessage());
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                conn.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            return qModel;
        }
        finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                conn.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
         
        return qModel;
    }
    
    public static InspectionApprovalPanelModel viewRecord(final String panel_id) {
        String cSql = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rst = null;
        final InspectionApprovalPanelModel im = new InspectionApprovalPanelModel();
        int i = 1;
        try {
            conn = DBConnection.getConnection();
            cSql = "select * from af_approv_pannel_mast where panel_id=? ";
            pstmt = conn.prepareStatement(cSql);
            pstmt.setString(i++, panel_id.toUpperCase().trim());
           // System.out.println("master:: " + pstmt);
            rst = pstmt.executeQuery();
            if (rst.next()) {
                do {
                    im.setPanel_id(General.checknull(rst.getString("panel_id")));
                    im.setPanel_code(General.checknull(rst.getString("panel_code")));
                    im.setPanel_name(General.checknull(rst.getString("panel_name")));
                    im.setIs_active(General.checknull(rst.getString("isActive")));
                } while (rst.next());
            }
        }
        catch (Exception e) {
            System.out.println("Error in "
            		+ "[viewRecord] : " + e.getMessage());
            InspectionApprovalPanelManager.log.fatal((Object)Logging.logException("InspectionApprovalPanelManager[viewRecord]", e.getMessage().toString()));
            try {
                if (pstmt != null) {
                    pstmt = null;
                }
                if (rst != null) {
                    rst = null;
                }                                                            
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            return im;
        }
        finally {
            try {
                if (pstmt != null) {
                    pstmt = null;
                }
                if (rst != null) {
                    rst = null;
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return im;
    }
       public static JSONArray viewRecordDetails(final String panel_id) {
        final JSONArray arr = new JSONArray();
        String cSql = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rst = null;
        try {
            conn = DBConnection.getConnection();
            cSql = "select emp_type,Panel_det_id,member_type,department,designation,email_id,contant_no,is_convenor,issActive FROM af_approv_pannel_detail WHERE Panel_id=? ";
            pstmt = conn.prepareStatement(cSql);
            pstmt.setString(1, panel_id.toUpperCase().trim());
          //  System.out.println("detail:: " + pstmt);
            rst = pstmt.executeQuery();
            if (rst.next()) {
                do {
                    final JSONObject obj = new JSONObject();
                    obj.put((Object)"emp_type", (Object)General.checknull(rst.getString("emp_type")));
                    obj.put((Object)"Panel_det_id", (Object)General.checknull(rst.getString("Panel_det_id")));
                    obj.put((Object)"member_type", (Object)General.checknull(rst.getString("member_type")));
                    obj.put((Object)"department", (Object)General.checknull(rst.getString("department")));
                    obj.put((Object)"designation", (Object)General.checknull(rst.getString("designation")));
                    obj.put((Object)"email_id", (Object)General.checknull(rst.getString("email_id")));
                    obj.put((Object)"contant_no", (Object)General.checknull(rst.getString("contant_no")));
                    obj.put((Object)"is_convenor", (Object)General.checknull(rst.getString("is_convenor")));
                    obj.put((Object)"issActive", (Object)General.checknull(rst.getString("issActive")));
                    arr.add((Object)obj);
                    
                    
                } while (rst.next());
            }
        }
        
        catch (Exception e) {
            System.out.println("Error in InspectionApprovalPanelManager[viewRecord] : " + e.getMessage());
            InspectionApprovalPanelManager.log.fatal((Object)Logging.logException("InspectionApprovalPanelManager[viewRecord]", e.getMessage().toString()));
            try {
                if (pstmt != null) {
                    pstmt = null;
                }
                if (rst != null) {
                    rst = null;
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            return arr;
        }
        finally {
            try {
                if (pstmt != null) {
                    pstmt = null;
                }
                if (rst != null) {
                    rst = null;
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            
        }
        return arr;
    }
}