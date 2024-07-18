package com.sits.general;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.sits.conn.DBConnection;

public class General {
	public static String encPassword(String pass) {
		if(!pass.equals("")){
			String newpass="";
			char c;
			int nVal1 = 0;
			int nVal2 = 0;
			int l1=pass.length();
			for(int j=l1;j<10;j++){
				pass=(pass+" ");
			}
			int l=pass.length();
			for(int i=1;i<=l;i++) {
				c = pass.charAt(i-1);
				nVal1 = c;
				nVal1 = nVal1 + i;
				if(nVal1==-39) {
					newpass = newpass + "'||''''||'";
				} else {
					newpass = newpass + (char)nVal1;
				}
			}
			newpass = newpass.trim();
			return newpass;
		} else {
			return "";
		}
	}

	public static String decPassword(String pass){
		if(!pass.equals("")) {
			String ver1="";
			char c;
			int nLen  = pass.length();
			int nVal1 = 0, j1=1;
			for(int i=1;i<=nLen;i++){
				c = pass.charAt(i-1);
				nVal1 = c;
				nVal1 = nVal1 - (0+i);
				for(;nVal1 < 0;){
					nVal1 = nVal1 + (256*j1);
					j1 = j1 + 1;
				}
				ver1= ver1 + (char)nVal1;
			}
			ver1= ver1.trim();
			return ver1;
		} else {
			return "";
		}
	}
	

	public static String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}	
	
	public static String PSTL(String PTSL){
		if(PTSL!=null) {
		    PTSL=PTSL.replace('0','M');
		    PTSL=PTSL.replace('1','C');
		    PTSL=PTSL.replace('2','Z');
		    PTSL=PTSL.replace('3','K');
		    PTSL=PTSL.replace('4','L');
		    PTSL=PTSL.replace('5','H');
		    PTSL=PTSL.replace('6','Q');
		    PTSL=PTSL.replace('7','J');
		    PTSL=PTSL.replace('8','U');
		    PTSL=PTSL.replace('9','W');
		}
		return PTSL;
	}	
	
	public static String checknull(String n){
		if(n!=null) {
			return n;
		} else {
			return "";
		}
	}
	
	public static String checkXSS(String n){
		if(n!=null) {
			//n= n.replace(':', ' ');
		    n= n.replace('<',' ');
		    n= n.replace('>',' ');
		    //n= n.replace('/',' ');
		    n= n.replace('"',' ');
		    n= n.replace(';',' ');
		    n= n.replace('(',' ');
		    n= n.replace(')',' ');
		    n= n.replace('%',' ');
		    n= n.replace('&',' ');
		    n= n.replace('+',' ');			
		}
		return n;
	}
	
	public static String check_null(String n){
		if(n!=null) {
			return checkXSS(n);
		} else {
			return "";
		}
	}
	
	public static String getRandomPass(){
		int charsToPrint = 4;
		int charsToInt = 4;
		int charsToSChar = 2;
		
		String elegibleChars = "ABCDEFGHJKLMPQRSTUVWXYZabcdefhjkmnpqrstuvwxyz";
		String elegibleInt = "123456789";
		String elegibleSChar = "#$*";
		
		char[] chars = elegibleChars.toCharArray();
		StringBuffer finalString = new StringBuffer();
		
		char[] ints = elegibleInt.toCharArray();
		StringBuffer finalInt = new StringBuffer();
		
		char[] SChar = elegibleSChar.toCharArray();
		StringBuffer finalSChar = new StringBuffer();
		
		for ( int i1 = 0; i1 < charsToPrint; i1++ ) {
			double randomValue = Math.random();
			int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
			char characterToShow = chars[randomIndex];
			finalString.append(characterToShow);
		}
		for ( int i2 = 0; i2 < charsToInt; i2++ ) {
			double randomValue = Math.random();
			int randomIndex = (int) Math.round(randomValue * (ints.length - 1));
			char intToShow = ints[randomIndex];
			finalInt.append(intToShow);
		}
		
		String x=finalString.toString();
		String x1=finalInt.toString();
		String PASSWORD = x+x1;
		return shuffle(PASSWORD);
	}
	
	public static String shuffle(String input){
		List<Character> characters = new ArrayList<Character>();
		for(char c:input.toCharArray()) {
			characters.add(c);
		}
		StringBuilder output = new StringBuilder(input.length());
		while(characters.size()!=0) {
			int randPicker = (int)(Math.random()*characters.size());
			output.append(characters.remove(randPicker));
		}
		return output.toString();
	}
	
	public static String getToken(){
		int charsToPrint = 4;
		int charsToInt = 4;

		String elegibleChars = "ABCDEFGHJKLMPQRSTUVWXYZabcdefhjkmnpqrstuvwxyz";
		String elegibleInt = "123456789";
		
		char[] chars = elegibleChars.toCharArray();
		StringBuffer finalString = new StringBuffer();
		
		char[] ints = elegibleInt.toCharArray();
		StringBuffer finalInt = new StringBuffer();
		
	
		for ( int i1 = 0; i1 < charsToPrint; i1++ ) {
			double randomValue = Math.random();
			int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
			char characterToShow = chars[randomIndex];
			finalString.append(characterToShow);
		}
		for ( int i2 = 0; i2 < charsToInt; i2++ ) {
			double randomValue = Math.random();
			int randomIndex = (int) Math.round(randomValue * (ints.length - 1));
			char intToShow = ints[randomIndex];
			finalInt.append(intToShow);
		}
		
		String x=finalString.toString();
		String x1=finalInt.toString();
		String Token = x+x1;
		return getMD5(Token);//shuffle(Token);
	}	
	
    public static String getMD5(String input) {
        byte[] source;
        try {
            //Get byte according by specified coding.
            source = input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            source = input.getBytes();
        }
        String result = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            //The result should be one 128 integer
            byte temp[] = md.digest();
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = temp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            result = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }	
	
	public static String isNumeric(String no, String errorMsg) {
		String msg = null;
		if(!General.checknull(no).equals("")){
			Pattern num = Pattern.compile("[(A-Za-z)]");
		
			Matcher matcher = num.matcher(no);
			if(matcher.find()){
				msg = errorMsg;
			} else {
				msg="";
			}
		}
		return msg;
	}

	
	public static String isLength(String str, int len, String errorMsg){
		String msg = null;
		if(str.length()!=len){
			msg = errorMsg;
		} else {
			msg ="";
		}
		return msg;
	}

	public static String isDecimal(String value, String errorMsg){
		String msg = null;
		if(value.length()>0){
			Pattern num = Pattern.compile("[(A-Za-z)]");
			Matcher matcher = num.matcher(value);
			if(!matcher.find()){
				int n = value.indexOf(".");
				int m = value.indexOf(".");
				if(value.indexOf(".")!=-1) {
					if(n!=m){
						msg = errorMsg;
					} else {
						msg="";
					}
				} else {
					msg=errorMsg;
				}
			} else {
				msg=errorMsg;
			}
		} else {
			msg="";
		}
		return msg;
	}
	
	public static String isEmail(String email, String errorMsg){
		String msg = null;
		if(email.length()>0){
			String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(email);
			if(m.find()){
				msg="";
			} else {
				msg = errorMsg;
			}
		} else {
			msg="";
		}
		return msg;
	}

	public static boolean isEmailId(String email) {
		if(email.length()>0) {
			String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(email);
			if(m.find()){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}	

	public static boolean isNumber(String no) {
		if(!General.checknull(no).equals("")){
			//Pattern num = Pattern.compile("[0-9]\\d");
			Pattern num = Pattern.compile("\\d+");
			Matcher matcher = num.matcher(no);
			if(matcher.find()){
				return true;
			} else {
				return false;
			}
		}
		return true;
	}	

	public static boolean IsDecimal(String value){
		if(value.length()>0){
			Pattern num = Pattern.compile("[(A-Za-z)]");
			Matcher matcher = num.matcher(value);
			if(!matcher.find()){
				int n = value.indexOf(".");
				int m = value.indexOf(".");
				if(value.indexOf(".")!=-1) {
					if(n!=m){
						return false;
					} else {
						return true;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}	


	public static boolean isAlphaNumeric(String s){
	    String pattern= "^[a-zA-Z0-9]*$";
	        if(s.matches(pattern)){
	            return true;
	        }
	        return false;   
	}
	
	public static boolean isPAN(String pan) {
		if(pan.length()>0) {
			Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
			Matcher matcher = pattern.matcher(pan);
			if(matcher.find()) {
				return true;      
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isValidDate(String date) {
		if(date.length()>0) {
			Pattern pattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
			Matcher matcher = pattern.matcher(date);
			if(matcher.matches()) {
				matcher.reset();
				if(matcher.find()) {
					String day = matcher.group(1);
					String month = matcher.group(2);
					int year = Integer.parseInt(matcher.group(3));
					if (day.equals("31") && (month.equals("4") || month .equals("6") || month.equals("9") || month.equals("11") || month.equals("04") || month .equals("06") || month.equals("09"))) {
						return false; // only 1,3,5,7,8,10,12 has 31 days
					} else if (month.equals("2") || month.equals("02")) { // leap Year
						if(year % 4==0) {
							if(day.equals("30") || day.equals("31")) {
								return false;
							} else {
								return true;
							}
						} else {
							if(day.equals("29")||day.equals("30")||day.equals("31")){
								return false; 
							} else {
								return true; 
							}
						}
					} else {
						return true;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return true;
		} 
	}

	 public static String salttostring(String SALT){
	      String PTSL="";
	      PTSL=SALT;
	      PTSL=PTSL.replace('0','M');
	      PTSL=PTSL.replace('1','C');
	      PTSL=PTSL.replace('2','Z');
	      PTSL=PTSL.replace('3','K');
	      PTSL=PTSL.replace('4','L');
	      PTSL=PTSL.replace('5','H');
	      PTSL=PTSL.replace('6','Q');
	      PTSL=PTSL.replace('7','J');
	      PTSL=PTSL.replace('8','U');
	      PTSL=PTSL.replace('9','W');
	      return PTSL;
	}

	  public static String stringtosalt(String str){
	      String salt="";
	      salt=str;
	      salt = salt.replace('M','0');
	      salt = salt.replace('C','1');
	      salt = salt.replace('Z','2');
	      salt = salt.replace('K','3');
	      salt = salt.replace('L','4');
	      salt = salt.replace('H','5');
	      salt = salt.replace('Q','6');
	      salt = salt.replace('J','7');
	      salt = salt.replace('U','8');
	      salt = salt.replace('W','9');
	      return salt;
	}	
	 
	public static String strtoMD5(String str){
		try{
			MessageDigest mdAlgorithm =null; 
			String plainText ="";
			plainText =str;
			mdAlgorithm = MessageDigest.getInstance("MD5");
			mdAlgorithm.update(plainText.getBytes());
			byte[] digest = mdAlgorithm.digest();
			StringBuffer hexString = new StringBuffer();
			for(int k = 0; k < digest.length; k++){
				plainText = Integer.toHexString(0xFF & digest[k]);
				if(plainText.length() < 2) {
					plainText = "0" + plainText;
				}
				hexString.append(plainText);
			}
			str=(hexString.toString());
		} catch(Exception e){}
		return str;
	}

	public static String capitalize(String s) {
        if (s.length() == 0) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

	public static double roundTwoDecimal(Double d) {
		DecimalFormat twoDForm = new DecimalFormat("#############.00");
		return Double.valueOf(twoDForm.format(d));
	}
	
	private static String df4format(String pattern, Object value) {
	    return new DecimalFormat(pattern+".0000").format(value);
	}
	
	private static String df2format(String pattern, Object value) {
	    return new DecimalFormat(pattern+".##").format(value);
	}	
	
	public static String df2format(Double value) {
	    if(value < 1000) {
	        return df2format("###", value);
	    } else {
	        Double hundreds = value % 1000;
	        int other = (int) (value / 1000);
	        return df2format(",##", other) + ',' + df2format("000", hundreds);
	    }
	}
	
	public static String df4format(Double value) {
	    if(value < 1000) {
	        return df4format("###", value);
	    } else {
	        Double hundreds = value % 1000;
	        int other = (int) (value / 10000);
	        return df4format(",##", other) + ',' + df4format("000", hundreds);
	    }
	}	
	
	public static String getSeqNo(String seq_name){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		String cSql="",seqNo="";
		try {
			conn = DBConnection.getConnection();
			cSql="select "+seq_name+".nextval from dual";
			//System.out.println(cSql);
			pstmt = conn.prepareStatement(cSql);
			rst = pstmt.executeQuery();
			if(rst.next()) {
				seqNo = rst.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("Error in General.java(getSeqNo)01 : "+e.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt = null;
				if(rst != null) rst = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return seqNo;
	}
	
	public static String curr_date() {
		String curr_date="",sql="";
		Connection conn = null;
		PreparedStatement mpstmt = null;
		ResultSet mrst = null;
		try {
			conn = DBConnection.getConnection();
			//sql="select to_char(sysdate,'dd/mm/yyyy') from dual";
			sql="select date_format(now(),'%d-%m-%Y')";
			mpstmt = conn.prepareStatement(sql);
			mrst = mpstmt.executeQuery();
			if(mrst.next()){
				curr_date = General.checknull(mrst.getString(1));
			} else {
				curr_date="";
			}
		} catch (SQLException e) {
			System.out.println("curr_date (General.java) : "+e.getMessage());
		} finally {
			try {
				if(mrst != null) mrst.close();
				if(mpstmt != null) mpstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return curr_date;
	}
	
	public static String currdate_time() {
		String curr_date="",sql="";
		Connection conn = null;
		PreparedStatement mpstmt = null;
		ResultSet mrst = null;
		try {
			conn = DBConnection.getConnection();
			//sql="select to_char(sysdate,'dd-mm-yyyy hh24:mi:ss') from dual";
			sql="select date_format(now(),'%d-%m-%Y %H:%i:%s')";
			mpstmt = conn.prepareStatement(sql);
			mrst = mpstmt.executeQuery();
			if(mrst.next()){
				curr_date = General.checknull(mrst.getString(1));
			} else {
				curr_date="";
			}
		} catch (SQLException e) {
			System.out.println("curr_date (General.java) : "+e.getMessage());
		} finally {
			try {
				if(mrst != null) mrst.close();
				if(mpstmt != null) mpstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return curr_date;
	}	

	  private static SimpleDateFormat inSDF = new SimpleDateFormat("dd/mm/yyyy");
	  private static SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-mm-dd");

	  public static String formatDate(String inDate) {
	    String outDate = "";
	    if (inDate != null) {
	        try {
	            Date date = inSDF.parse(inDate);
	            outDate = outSDF.format(date);
	        } catch (ParseException ex) {
	            System.out.println("Unable to format date: " + inDate + ex.getMessage());
	            ex.printStackTrace();
	        }
	    }
	    return outDate;
	  }
	  
	  /**
	   * 
	   * 
	   * */
	  public static Logger getLogger(HttpServletRequest req) {
		  String requestUri = req.getRequestURI();
		  String jspName = requestUri.substring(requestUri.lastIndexOf('/'));
		  return Logger.getLogger(jspName);
	  }
	  
	  public static String countSpace(String n){
    	  String n2="";
    	if(!General.checknull(n).equals(""))
    	 {
    	   if((!n.equals("")&& n.length()>0)){
    		  n2= n.replaceAll("\\s+"," ");
    		   	return n2;
    		   	}
    	   }
    	return  n2;
	  }
	  
	  /**
	   Checks if is null or blank.
      @param str
	   @return true/false
	 */
	public static boolean isNullOrBlank(final String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}
	
	static Logger log = Logger.getLogger("exceptionlog");
	public static Double getAmountAfterRounding(String head_id, String amount) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "",namt="",damt="";
		double xamount=0.0,amt=0.0;
		int n=0,aamt=0;
		n =  amount.indexOf ( "." ) ;
		namt = amount.substring(0,n);
		damt = amount.substring(n);
		//System.out.println(head_id+"|"+amount+"|"+n+"|"+namt+"|"+damt);
		sql = " select rounding from salary_head_mast where salary_head_id = ? ";
		try {
			if(!head_id.equals("") && !amount.equals("")){
				conn = DBConnection.getConnection();
				pstmt=conn.prepareStatement(sql);
			    pstmt.setString(1,head_id);
			    rs = pstmt.executeQuery();
				if (rs.next()) {
					amt = rs.getDouble(1);
				}
				if(amt == 0.0){
					xamount = Double.parseDouble(amount);
				} else if(amt == 0.5){
					if(Double.parseDouble(damt) >= 0.5){
						aamt = Integer.parseInt(namt) + 1;
						amount = aamt + ".00";
						xamount = Double.parseDouble(amount);
					} else if(Double.parseDouble(damt) >= 0.01 && Double.parseDouble(damt) < 0.5){
						amount = Integer.parseInt(namt) + ".00";
						xamount = Double.parseDouble(amount);
					} else {
						xamount = Double.parseDouble(amount);
					}
				} else if(amt == 1.0){
					if(Double.parseDouble(damt) > 0.0){
						aamt = Integer.parseInt(namt) + 1;
						amount = aamt + ".00";
						xamount = Double.parseDouble(amount);
					} else {
						xamount = Double.parseDouble(amount);
					}
				} else if(amt == 10.0){
					if(Double.parseDouble(amount.substring(n-1)) != 0.0){
						amt = Double.parseDouble(amount) + 10.0;
						amount = String.valueOf(amt).substring(0, String.valueOf(amt).indexOf(".")-1);
						amount = amount + "0.00";
						xamount = Double.parseDouble(amount);
					} else {
						xamount = Double.parseDouble(amount);
					}
				}
			}	
		} catch (Exception e) {
			//System.out.println("Error::"+e.getMessage());
//			log.fatal(Logging.logException("General[getAmountAfterRounding]", e.getMessage().toString()));
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
		return xamount;
	}
	
	 /**
	   Checks if is adhar is valid or not.
    @param str
	   @return true/false
	 */
	public static boolean checkAdharNumber(final String str) {
		 char ch = str.charAt(0);
			if('0'==ch || '1'==ch || str.length()>12 || str.length()<12 || !(isNumber(str)) )
			return false;
			else
			return true;	
			
	}
	
	public static String toCamelCase(String s)
    {
    	s=General.countSpace(s); // added by karanveer singh on 21/01/2018 to remove double space from any string.
        String[] parts = s.split(" ");
        String camelCaseString = "";
        for (String part : parts)
        {
            if(camelCaseString.equals(""))
            {
                camelCaseString = toProperCase(part);
            }
            else
            {
                camelCaseString = camelCaseString+" "+toProperCase(part);
            }
        }
        return camelCaseString;
    }
	
	public static String toProperCase(String s)
    {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }	

	public static Integer cntMenuPages(String module,String user_status,String user_id) {
		String sql="";
		int cntRecord=0;
		Connection conn = null;
		PreparedStatement mpstmt = null;
		ResultSet mrst = null;
		try {
			conn = DBConnection.getConnection();
			
			if(user_status.equals("A")){
				sql="select count(*) from tree_menu where module=? and jsp_file is not null";
			} else {
				sql=" select count(*) from tree_menu where MENU_ID in ( ";
				sql+=" select page_id from web_user_acess where user_id = '"+user_id+"') and module=? ";
			}
			mpstmt = conn.prepareStatement(sql);
			mpstmt.setString(1, module);
			mrst = mpstmt.executeQuery();
			if(mrst.next()){
				cntRecord = mrst.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("cntMenuPages (General.java) : "+e.getMessage());
		} finally {
			try {
				if(mrst != null) mrst.close();
				if(mpstmt != null) mpstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return cntRecord;
	}
	
	/**
	 * Method to compare date.
	 * Argument type:String (fromDate and toDate).
	 * Return Type:boolean.
	 * if(param ==1) ie:Same date will be allowed
	 * if(param==0) ie:Same date will not be allowed
	 **/
	
	public static boolean compareDate(String frmDate,String toDate,int param)
	{
		boolean flg=false;
		try{
			if(General.isValidDate(frmDate) && General.isValidDate(toDate))
			{
			 String str1=frmDate.substring(6,10)+frmDate.substring(3,5)+frmDate.substring(0,2);
		 	 String str2=toDate.substring(6,10)+toDate.substring(3,5)+toDate.substring(0,2);
			 int x=Integer.parseInt(str1);
			 int y=Integer.parseInt(str2);
				 if(x>y && param==1) {
					 flg=false;
				 } else if(x>=y && param==0) {
					 flg= false;	 
				 } else {
					flg=true;
				}
			} else {
				flg= false;
			}
		}catch(Exception e){
			System.out.println("Exception in General[compareDate]"+e.getMessage());
			return flg;
		}
		return flg;
	}
	
	public static String getGradeSheetPath() {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rst = null;
		String qry = "", path = "";
		try {
			conn = DBConnection.getConnection();
			qry = "select descp1 from cparam where code='GRADESHEET' and serial='PATH' and param1='Y'";
			psmt = conn.prepareStatement(qry);
			rst = psmt.executeQuery();
			if (rst.next()) {
				path = General.checknull(rst.getString("descp1"));
			}

		} catch (Exception e) {
			System.out.println("Exception in General[getGradeSheetPath] " + e.getMessage());
			return path;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (psmt != null) {
					psmt.close();
				}
				if (rst != null) {
					rst.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
	}
	
	/*
	 * Method to Generate the log for log_mast table
	  */
	public static int manageLog(String formName, String remarks, String userId, String machine) {
		Connection conn = null;
		PreparedStatement psmt = null;
		int cnt = 0;
		String qry = "";
		try {
			conn = DBConnection.getConnection();
			qry = "insert into log_mast (form,remarks,create_by,created,machine)" + " values(?,?,?,now(),?)";
			psmt = conn.prepareStatement(qry);
			psmt.setString(1, General.checknull(formName));
			psmt.setString(2, General.checknull(remarks));
			psmt.setString(3, General.checknull(userId));
			psmt.setString(4, General.checknull(machine));
			cnt = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Exception in General(manageLog) " + e.getMessage());
			return cnt;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (psmt != null) {
					psmt.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return cnt;
	}
	
	public static String userType(String puser_id, String puser_type) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		String userDetail="",cSql="";
		try {
			conn = DBConnection.getConnection();
			if(puser_type.trim().equals("CL")) {
				cSql="select id as college_id, college_code, college_name from academic_college_profile_master ";
				cSql+="where id = (select college from user_type_mast where user_type_id = (select employeeid from user_mast ";
				cSql+="where user_id='"+puser_id+"' and user_type='"+puser_type+"'))";
				
				pstmt = conn.prepareStatement(cSql);
				rst = pstmt.executeQuery();
				if(rst.next()) {
					userDetail = General.checknull(rst.getString("college_id"))+"~"+General.checknull(rst.getString("college_code"))+"~"+General.checknull(rst.getString("college_name"));
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in General(userType) " + e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}			
		}
		return userDetail;
	}
	
	/**
	 *  Default DDO and Location for college login
	 * 
	 * */
	public static String getDefaultDDOLocation(String puser_type) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		String defaultDDOLocation="",cSql="";
		try {
			conn = DBConnection.getConnection();
			if(puser_type.trim().equals("CL")) {
				cSql="select ddo_id, location_code, id from ddolocationmapping  ";
				cSql+="where id = (select pdoc from cparam where code='USER' and serial='DEFAULT') ";
				
				pstmt = conn.prepareStatement(cSql);
				rst = pstmt.executeQuery();
				if(rst.next()) {
					defaultDDOLocation = General.checknull(rst.getString("ddo_id"))+"~"+General.checknull(rst.getString("location_code"))+"~"+General.checknull(rst.getString("id"));
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in General(getDefaultDDOLocation) " + e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}			
		}
		return defaultDDOLocation;
	}	
	
	 public static boolean isValidFormat(String format, String value) {
	        Date date = null;
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat(format);
	            date = sdf.parse(value);
	            if (!value.equals(sdf.format(date))) {
	                date = null;
	            }
	        } catch (ParseException ex) {
	            
	        }
	        if(date != null){
	        	if(value.split("-")[2].length()>4)
	        	return false;
	        	else
	        	return true;
	        }
	        else
	        return false;	
	        			
	    }	

	 public static boolean isAlphaNumericWithSpecialCharacters(String s){
		    String pattern= "^[a-zA-Z0-9!@#$^*]*$";
		        if(s.matches(pattern)){
		            return true;
		        }
		        return false;   
		}
	 
	 public static boolean isValidAlpha(String str) {
			if(!General.checknull(str).equals("")){
				Pattern num = Pattern.compile("(?![0-9]*$)[a-zA-Z0-9]+$");
				Matcher matcher = num.matcher(str);
				if(matcher.find()){
					return true;
				} else {
					return false;
				}
			}
			return true;
		}

	 public static boolean isIfsc(String s){
		 if(s.length()>11 || s.length()<11) {
			 String pattern= "^[A-Za-z]{4}[0]{1}[a-zA-Z0-9]{6}$";
		     if(s.matches(pattern)){
		    	 return true;
		     }
		     return false; 
		 }
		 return false;  
	}	 

	 /*
		 * Added by Rajendra to delete file from directory
		*/
		public static void deleteFile(File directory, String fname) {
			if (directory.isDirectory()) {
	            File[] children = directory.listFiles();
	            for (File child : children) {
	                //System.out.println(child.getAbsolutePath());
	                if((child.getAbsoluteFile().toString()).indexOf(fname) != -1 ){
	                	//System.out.println(" 111111111 "+fname);
	                	child.getAbsoluteFile().delete();	
	                }
	                
	            }
	         }
		}
		
		/*
		 * Added by Rajendra to get File size into MB
		*/
		public static double convertFileBytToMB(double file_len) {
			return file_len / (1024 * 1024);
		}
		/*
		 * Added by Rajendra to get File size into KB
		*/
		public static double convertFileBytToKB(double file_len) {
			return file_len / 1024;
		}
		
		/*
		 * Added by Rajendra to get document size from cparam table  
		 */
		  public static String getFileSize(String cd, String srl, String dc, String p){
			Connection conn=null;
			PreparedStatement psmt = null;
			ResultSet rst=null;
			String qry="", doc_size="";
			try{
			   conn=DBConnection.getConnection();
			   qry = "select pdoc, descp1 from cparam where code=? and serial=? and doc=? and param1 = ? ";
			   psmt=conn.prepareStatement(qry);
			   psmt.setString(1, cd);
			   psmt.setString(2, srl);
			   psmt.setString(3, dc);
			   psmt.setString(4, p);
			   rst=psmt.executeQuery();
			   while(rst.next()){
				 doc_size = rst.getInt("DESCP1")+"~"+checknull(rst.getString("pdoc"));
			   }
			}catch(Exception e){
				System.out.println("Error in General[getFileSize] : "+e.getMessage());
				return doc_size;
			}finally{
			 try{
				  conn.close();
				  psmt.close();
			  }catch(Exception  ex){
				  ex.printStackTrace();
			  }
			}
		 return doc_size;
		}	

		  public static Image generateEANBarcode(PdfWriter writer,String text){
				Image codeEANImage=null;
				try {
					PdfContentByte cb = writer.getDirectContent();
					BarcodeEAN barcodeEAN = new BarcodeEAN();
					barcodeEAN.setCode(text);
					barcodeEAN.setCodeType(Barcode.EAN13);
					
					barcodeEAN.setFont(null);
					codeEANImage = barcodeEAN.createImageWithBarcode(cb, null, null);
				} catch (Exception e) {
					System.out.println("Error in General[generateEANBarcode] : "+e.getMessage());
					e.printStackTrace();
				}
				return codeEANImage;
			}	
		  
		  
			public static Connection  updtDeletedData(String tname, String cn1, String cn2, String cn3, String cn4, String cn5, String cn6, String cv1, String cv2, String cv3, String cv4, String cv5, String cv6, String ip, String uid, String schema, Connection conn){
				PreparedStatement pstmt = null;
				String cSql="";
				int cnt=0;
				try {
					if(!checknull(schema).equals(""))
						schema = schema+".";
						
					
					cSql=" update "+schema+tname+" set DELETED_BY = '"+uid+"', DELETED_IP = '"+ip+"', DELETED_DT = now() ";
					cSql+= " where "+cn1+"='"+cv1+"'";
					if(!checknull(cn2).equals("") && !checknull(cv2).equals("")) {
						cSql+= " and "+cn2+"='"+cv2+"'";
					}
					if(!checknull(cn3).equals("") && !checknull(cv3).equals("")) {
						cSql+= " and "+cn3+"='"+cv3+"'";
					}
					if(!checknull(cn4).equals("") && !checknull(cv4).equals("")) {
						cSql+= " and "+cn4+"='"+cv4+"'";
					}
					if(!checknull(cn5).equals("") && !checknull(cv5).equals("")) {
						cSql+= " and "+cn5+"='"+cv5+"'";
					}
					if(!checknull(cn6).equals("") && !checknull(cv6).equals("")) {
						cSql+= " and "+cn6+"='"+cv6+"'";
					}
					//System.out.println("Exception in General(cSql) " + cSql);
					pstmt = conn.prepareStatement(cSql);
					cnt = pstmt.executeUpdate();
					if(cnt == 0) {
						conn = null;
					}
				} catch (Exception e) {
					System.out.println("Exception in General(updtDeletedData) " + e.getMessage());
				} finally {
					try {
						if (pstmt != null) {
							pstmt.close();
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}			
				}
				return conn;
			}
			
			public static String getFileExtension(String string) {
				String fileName = string;
				if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
					return fileName.substring(fileName.lastIndexOf(".") + 1);
				else
					return "";
			}

			public static String getFileName(String string) {
				String fileName = string;
				if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
					return fileName.replace(fileName.substring(fileName.lastIndexOf(".") + 1), "");
				else
					return "";
			}

}


