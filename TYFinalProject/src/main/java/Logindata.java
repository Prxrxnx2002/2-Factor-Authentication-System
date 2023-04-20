import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
//import java.sql.Connection;

/**
 * Servlet implementation class Logindata
 */
@WebServlet("/Logindata")
public class Logindata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logindata() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("uname");
		String password = request.getParameter("pswd");
		//request.setAttribute("username", username);
		if(Login.validate(username, password)) {
			StringBuilder otp = OTP.genOTP();
			System.out.println(otp);
			OTP.storeotp(otp, username);
			OTP.sendotp(otp, username);
			RequestDispatcher rd = request.getRequestDispatcher("Authenticate.html");
			rd.include(request,response);
		}
		else {
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Invalid username or password');");
			out.println("</script>");
			RequestDispatcher rd = request.getRequestDispatcher("Registration.html");
			rd.include(request,response);
			}
	}
}