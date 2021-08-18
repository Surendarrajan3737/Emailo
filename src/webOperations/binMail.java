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
 * Servlet implementation class binMail
 */
@WebServlet("/binMail")
@MultipartConfig
public class binMail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String mailID=request.getParameter("mailID");
		System.out.println("Moving Emails to bin");
		String out=MailOperations.moveMailToBin(mailID);
		System.out.println(out);
		response.getWriter().write(out);
	}

}
