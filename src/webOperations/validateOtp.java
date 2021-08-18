package webOperations;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class validateOtp
 */
@WebServlet("/validateOtp")
@MultipartConfig
public class validateOtp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String generatedOtp=null;
		System.out.println("Validte.java");
		System.out.println("Otp generation success");
    	if(request.getParameter("phoneNumber")!=null)
    			generatedOtp= OtpVerification.getOTP(request.getParameter("phoneNumber"));
    			response.getWriter().write(generatedOtp);
    	}
}
