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
 * Servlet implementation class validateUser
 */
@WebServlet("/validateUser")
@MultipartConfig
public class validateUser extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			response.setContentType("application/json");
			String userID=request.getParameter("Login_ID");
			String userPass=request.getParameter("Login_Pass");
			String serverResponse="default IDT";
			try {
				 serverResponse= DBOperations.authUser(userID, userPass);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String json=JSONBuilder.CreateJSON("response", serverResponse);
			System.out.println(json);
			response.getWriter().write(json);
	}
}
