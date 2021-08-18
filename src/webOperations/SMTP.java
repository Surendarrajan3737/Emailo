package webOperations;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import databaseOperations.DBOperations;

/**
 * Servlet implementation class SMTP
 */
@WebServlet("/SMTP")
@MultipartConfig
public class SMTP extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String frommail = request.getParameter("frommail");
		String tomail = request.getParameter("tomail");
		String mailsub = request.getParameter("mailsub");
		String mailcnt = request.getParameter("mailcnt");
		String serverResponse = "default IDT";
		String serverResponse1 = "default IDT";
		String json = JSONBuilder.CreateJSON("smail", frommail);
		json = JSONBuilder.addJSONPair(json, "sname", DBOperations.getName(frommail));
		json = JSONBuilder.addJSONPair(json, "subject", mailsub);
		json = JSONBuilder.addJSONPair(json, "message", mailcnt);
		
		String json1 = JSONBuilder.CreateJSON("smail", tomail);
		json1 = JSONBuilder.addJSONPair(json1, "sname", DBOperations.getName(tomail));
		json1 = JSONBuilder.addJSONPair(json1, "subject", mailsub);
		json1 = JSONBuilder.addJSONPair(json1, "message", mailcnt);
		System.out.println(json);
		System.out.println(json1);
		try {
			serverResponse = DBOperations.createMailEntry(tomail, json, "inbox", "unread");
			serverResponse1 = DBOperations.createMailEntry(frommail, json1, "sent", "unread");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String jsonArray = JSONBuilder.CreateJSON("response", serverResponse);
		jsonArray = JSONBuilder.addJSONPair(jsonArray,"response", serverResponse1);
		System.out.println(jsonArray);
		response.getWriter().write(jsonArray);
	}

}
