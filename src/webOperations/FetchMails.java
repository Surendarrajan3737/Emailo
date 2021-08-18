package webOperations;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import databaseOperations.MailOperations;

/**
 * Servlet implementation class FetchMails
 */
@WebServlet("/FetchMails")
@MultipartConfig
public class FetchMails extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String email=request.getParameter("emailID");
		String type=request.getParameter("mailtype");
		System.out.println("Fetching Emails");
		String out=MailOperations.fetchMail(email, type);
		System.out.println(out);
		response.getWriter().write(out);
	}

}
