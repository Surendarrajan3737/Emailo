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
 * Servlet implementation class FetchImage
 */
@WebServlet("/FetchImage")
@MultipartConfig
public class FetchImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String email=request.getParameter("uName");
		String image=DBOperations.getImage(email);
		System.out.println("Fetching image");
		String out=JSONBuilder.CreateJSON("image", image);
		System.out.println(out);
		response.getWriter().write(out);
	}

}
