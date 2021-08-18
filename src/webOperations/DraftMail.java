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
 * Servlet implementation class DraftMail
 */
@WebServlet("/DraftMail")
@MultipartConfig
public class DraftMail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String frommail = request.getParameter("frommail");
		String tomail = request.getParameter("tomail");
		String mailsub = request.getParameter("mailsub");
		String mailcnt = request.getParameter("mailcnt");
		String serverResponse1 = "default IDT";
		String json1 = JSONBuilder.CreateJSON("smail", tomail);
		json1 = JSONBuilder.addJSONPair(json1, "sname", DBOperations.getName(tomail));
		json1 = JSONBuilder.addJSONPair(json1, "subject", mailsub);
		json1 = JSONBuilder.addJSONPair(json1, "message", mailcnt);
		try {
			serverResponse1 = DBOperations.createMailEntry(frommail, json1, "draft", "unread");
		} catch (Exception e) {

			e.printStackTrace();
		}

		System.out.println(serverResponse1);
		response.getWriter().write(serverResponse1);
	}

}
