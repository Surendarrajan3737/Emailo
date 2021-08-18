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
 * Servlet implementation class PushImage
 */
@WebServlet("/PushImage")
@MultipartConfig
public class PushImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String userID=request.getParameter("uName");
		String uImage=request.getParameter("uImage");
		System.out.println(uImage);
		DBOperations.pushImage(userID, uImage);
		response.getWriter().write(JSONBuilder.CreateJSON("success", "true"));
	}

}
