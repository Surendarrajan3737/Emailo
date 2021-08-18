package databaseOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import webOperations.JSONBuilder;

public class MailOperations {

	public static String fetchMailContent(String mailID) {
		System.out.println("Trying to mail ID :" + mailID);
		Connection conn = DBOperations.createConnection();
		String sql = "SELECT Mail_content,Mail_state,MailDateTime FROM dbo.Mail_table WHERE MailID=?";
		PreparedStatement pStatement = null;
		ResultSet rSet = null;
		String response = JSONBuilder.CreateJSON("response", "defaultDBO");
//		String JSONout=null;
		try {
			pStatement = conn.prepareCall(sql);
			pStatement.setString(1, mailID);
			rSet = pStatement.executeQuery();
			while (rSet.next()) {

				response = JSONBuilder.CreateJSON("mailstate", rSet.getString("Mail_state"));
				response = JSONBuilder.addJSONPairobject(response, "mailcontent", rSet.getString("Mail_content"));
				response = JSONBuilder.addJSONPair(response, "maildate", rSet.getDate("MailDateTime").toString());
//				if(JSONout==null)
//				{
//					JSONout=JSONBuilder.getJSONArray(response);
//				}
//				else {
//					JSONout=JSONBuilder.addJSONObject(JSONout, response);
//				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(response);
		if (response == null) {
			response = "{}";
		}
		return response;

	}

	public static String fetchMail(String mailID, String type) {
		System.out.println("Trying to fetch mail from :" + mailID);
		Connection conn = DBOperations.createConnection();
		String sql = "SELECT MailID,Mail_content,Mail_state,MailDateTime FROM dbo.Mail_table WHERE EmailID=? AND Mail_type=? ORDER BY MailDateTime";
		PreparedStatement pStatement = null;
		ResultSet rSet = null;
		String response = JSONBuilder.CreateJSON("response", "defaultDBO");
		String JSONout = null;
		try {
			pStatement = conn.prepareCall(sql);
			pStatement.setString(1, mailID);
			pStatement.setString(2, type);
			rSet = pStatement.executeQuery();
			while (rSet.next()) {
				response = JSONBuilder.CreateJSON("mailid", rSet.getString("MailID"));
				response = JSONBuilder.addJSONPairobject(response, "mailcontent", rSet.getString("Mail_content"));
				response = JSONBuilder.addJSONPair(response, "mailstate", rSet.getString("Mail_state"));
				response = JSONBuilder.addJSONPair(response, "maildate", rSet.getDate("MailDateTime").toString());
				if (JSONout == null) {
					JSONout = JSONBuilder.getJSONArray(response);
				} else {
					JSONout = JSONBuilder.addJSONObject(JSONout, response);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(JSONout);
		if (JSONout == null) {
			JSONout = "[]";
		}
		return JSONout;

	}

	public static String deleteMail(String mailID) {
		System.out.println("Trying to Update mail on :" + mailID);
		Connection conn = DBOperations.createConnection();
		String sql = "DELETE FROM dbo.Mail_table WHERE mailID=?";
		PreparedStatement pStatement = null;
		String response = JSONBuilder.CreateJSON("response", "defaultDBO");
		boolean result = true;
		try {
			pStatement = conn.prepareCall(sql);
			pStatement.setString(1, mailID);
			result = pStatement.execute();
			if (result) {
				response = JSONBuilder.CreateJSON("response", "fail");
			} else {
				response = JSONBuilder.CreateJSON("response", "success");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(response);
		return response;

	}

	public static String moveMailToBin(String mailID) {
		System.out.println("Trying to Update mail on :" + mailID);
		Connection conn = DBOperations.createConnection();
		String sql = "UPDATE dbo.Mail_table SET Mail_type='trash' WHERE mailID=?";
		PreparedStatement pStatement = null;
		String response = JSONBuilder.CreateJSON("response", "defaultDBO");
		boolean result = true;
		try {
			pStatement = conn.prepareCall(sql);
			pStatement.setString(1, mailID);
			result = pStatement.execute();
			if (result) {
				response = JSONBuilder.CreateJSON("response", "fail");
			} else {
				response = JSONBuilder.CreateJSON("response", "success");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(response);
		return response;

	}

	public static String moveMailToStarred(String mailID) {
		System.out.println("Trying to Update mail on :" + mailID);
		Connection conn = DBOperations.createConnection();
		String sql = "UPDATE dbo.Mail_table SET Mail_type='starred' WHERE mailID=?";
		PreparedStatement pStatement = null;
		String response = JSONBuilder.CreateJSON("response", "defaultDBO");
		boolean result = true;
		try {
			pStatement = conn.prepareCall(sql);
			pStatement.setString(1, mailID);
			result = pStatement.execute();
			if (result) {
				response = JSONBuilder.CreateJSON("response", "fail");
			} else {
				response = JSONBuilder.CreateJSON("response", "success");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(response);
		return response;

	}

	public static boolean verifyMail(String mailID) {
		System.out.println("Trying to verify mail on :" + mailID);
		Connection conn = DBOperations.createConnection();
		String sql = "SELECT * FROM dbo.User_table WHERE Email=?";
		PreparedStatement pStatement = null;
		String response = JSONBuilder.CreateJSON("response", "defaultDBO");
		boolean result = true;
		try {
			pStatement = conn.prepareCall(sql);
			pStatement.setString(1, mailID);
			ResultSet rst = pStatement.executeQuery();
			if (rst.next()) {
				result = false;
				response = JSONBuilder.CreateJSON("response", "fail");
			} else {
				response = JSONBuilder.CreateJSON("response", "success");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(response);
		return result;

	}

}
