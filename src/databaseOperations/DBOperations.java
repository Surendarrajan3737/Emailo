package databaseOperations;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOperations {

	public static final String URL = "jdbc:sqlserver://thundermail.database.windows.net:1433;database=thundermail;user=Yogesh@thundermail;password=Bootathon2.0;";
	public static final String USER_TABLE = "IF OBJECT_ID('[dbo].[User_table]', 'U') IS NOT NULL"
			+ "DROP TABLE [dbo].[User_table]" + "GO" + "CREATE TABLE [dbo].[User_table] " + "( "
			+ "Email VARCHAR(50) NOT NULL PRIMARY KEY, -- Primary Key column " + "User_fname VARCHAR(50) NOT NULL, "
			+ "User_lname VARCHAR(50) NOT NULL, " + "User_pass_hash BINARY(64) NOT NULL, " + "Salt UNIQUEIDENTIFIER,"
			+ "User_number VARCHAR(15) NOT NULL," + "User_dob DATE NOT NULL " + "); " + "GO ";
	public static final String ADD_USER = "CREATE PROCEDURE dbo.AddUser" + "@pemail VARCHAR(50), "
			+ "@pPassword VARCHAR(50), " + "@pFirstName VARCHAR(40) = NULL, " + "@pLastName VARCHAR(40) = NULL, "
			+ "@pNumber VARCHAR(15)=NULL, " + "@pDOB DATE, " + "@responseMessage VARCHAR(250) OUTPUT " + "AS" + "BEGIN"
			+ "SET NOCOUNT ON" +

			"DECLARE @salt UNIQUEIDENTIFIER=NEWID()" + "BEGIN TRY" +

			"INSERT INTO dbo.[User_table] (Email,User_fname, User_lname,User_passhash,Salt,User_address,User_number,User_dob)"
			+ "VALUES(@pemail, @pFirstName, @pLastName,HASHBYTES('SHA2_512', @pPassword+CAST(@salt AS VARCHAR(36))), @salt,@pAddress,@pNumber,@pDOB)"
			+

			"SET @responseMessage='Success' " +

			"END TRY " + "BEGIN CATCH " + "SET @responseMessage=ERROR_MESSAGE() " + "END CATCH " +

			"END";

	static Connection createConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}


	public static String getImage(String email) {
		System.out.println("Trying to fetch image from :" + email);
		Connection conn = createConnection();
		String sql = "SELECT Ver_image FROM dbo.Image_table where Email=?";
		PreparedStatement pStatement = null;
		ResultSet rSet = null;
		String response = "default DBO";
		try {
			pStatement = conn.prepareCall(sql);
			pStatement.setString(1, email);
			rSet = pStatement.executeQuery();
			while (rSet.next())
				response = rSet.getString("Ver_image");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(response);
		return response;

	}

	public static String getName(String email) {
		System.out.println("Trying to fetch Name from :" + email);
		Connection conn = createConnection();
		String sql = "SELECT User_fname, User_lname FROM dbo.User_table where Email=?";
		PreparedStatement pStatement = null;
		ResultSet rSet = null;
		String response = "default DBO";
		try {
			pStatement = conn.prepareCall(sql);
			pStatement.setString(1, email);
			rSet = pStatement.executeQuery();
			while (rSet.next())
				response = rSet.getString("User_fname")+" "+rSet.getString("User_lname");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(response);
		return response;

	}
	
	public static String pushImage(String email, String image) {
		System.out.println("Trying to push image to :" + email);
		Connection conn = createConnection();
		String sql = "INSERT INTO [dbo].[Image_table] ([Email], [Ver_image]) VALUES (?, ?) ";
		PreparedStatement pStatement = null;
		String response = " default pushImage";
		try {
			pStatement = conn.prepareCall(sql);
			pStatement.setString(1, email);
			pStatement.setString(2, image);
			pStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(response);
		return response;

	}

	public static String authUser(String userID, String userPass) throws Exception {
		System.out.println("Trying to auth :" + userID + " @ " + userPass);
		Connection conn = createConnection();
		String sql = "EXEC dbo.authLogin @pEmail = ?,@pPassword = ?,@responseMessage=?";
		CallableStatement cStatement = null;
		try {
			cStatement = conn.prepareCall(sql);
			cStatement.setString(1, userID);
			cStatement.setString(2, userPass);
			cStatement.registerOutParameter(3, java.sql.Types.NVARCHAR);
			cStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String response = " default DBO";
		response = cStatement.getString("responseMessage");
		System.out.println(response);
		return response;

	}

	@SuppressWarnings("deprecation")
	public static String createMailEntry(String emailID, String mailContent, String mailtype, String mailstate)
			throws Exception {
		System.out.println("adding to mail :" + emailID);
		Connection conn = createConnection();
		String sql = "EXEC dbo.AddMailEntry @pemail = ?,@pmailcontent = ?,@pmailtype=?,@pmailstate=?,@pmailDT=?,@responseMessage=?";
		CallableStatement cStatement = null;
		try {
			cStatement = conn.prepareCall(sql);
			cStatement.setString(1, emailID);
			cStatement.setString(2, mailContent);
			cStatement.setString(3, mailtype);
			cStatement.setString(4, mailstate);
			cStatement.setString(5, new java.util.Date().toLocaleString());
			cStatement.registerOutParameter(6, java.sql.Types.NVARCHAR);
			cStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String response = " default DBO";
		response = cStatement.getString("responseMessage");
		System.out.println(response);
		return response;

	}

	public static void createAdminUser() throws SQLException {
		Connection connection = createConnection();
		String sql = "DECLARE @responseMessage VARCHAR(250) " + "EXEC dbo.AddUser " + "@pemail ='admin@emailo',"
				+ "@pPassword ='Admin@Password', " + "@pFirstName ='Yogeshwaran', " + "@pLastName ='Rajendran', "
				+ "@pNumber ='9876543210', "+ "@pDOB = ?,"
				+ "@responseMessage=@responseMessage OUTPUT;";
		PreparedStatement pStatement = null;
		pStatement = connection.prepareStatement(sql);
		pStatement.setDate(1, new Date(new java.util.Date().getTime()));
		pStatement.execute();
	}
	public static void createPradUser() throws SQLException {
		Connection connection = createConnection();
		String sql = "DECLARE @responseMessage VARCHAR(250) " + "EXEC dbo.AddUser " + "@pemail ='prad@emailo',"
				+ "@pPassword ='Prad@Password', " + "@pFirstName ='Pradakshina', " + "@pLastName ='Manickam', "
				+ "@pNumber ='9876543210', "+ "@pDOB = ?,"
				+ "@responseMessage=@responseMessage OUTPUT;";
		PreparedStatement pStatement = null;
		pStatement = connection.prepareStatement(sql);
		pStatement.setDate(1, new Date(new java.util.Date().getTime()));
		pStatement.execute();
	}
	public static void createUser(String fname,	String lname,String email,String date,String password,String number) throws SQLException {
		Connection connection = createConnection();
		String sql = "DECLARE @responseMessage VARCHAR(250) " + "EXEC dbo.AddUser " + "@pemail =?,"
				+ "@pPassword =?, " + "@pFirstName =?, " + "@pLastName =?, "
				+ "@pNumber =?, "+ "@pDOB = ?,"
				+ "@responseMessage=@responseMessage OUTPUT;";
		PreparedStatement pStatement = null;
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, email);
		pStatement.setString(2, password);
		pStatement.setString(3, fname);
		pStatement.setString(4, lname);
		pStatement.setString(5, number);
		pStatement.setString(6, date);
		pStatement.execute();
	}
}
