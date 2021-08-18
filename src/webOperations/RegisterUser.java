package webOperations;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import databaseOperations.DBOperations;
import databaseOperations.MailOperations;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/RegisterUser")
@MultipartConfig
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fname=request.getParameter("fname");
		String lname=request.getParameter("lname");
		String email=request.getParameter("email");
		String date=request.getParameter("dob");
		String password=request.getParameter("password");
		String number=request.getParameter("pnumber");
		if(MailOperations.verifyMail(email))
		{
			try {
				DBOperations.createUser(fname,lname,email,date,password,number);
				System.out.println("Ver Success");
				response.getWriter().write(JSONBuilder.CreateJSON("response", "success"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			response.getWriter().write(JSONBuilder.CreateJSON("response", "failed"));
		}
		
	}

}
