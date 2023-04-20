import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class VerifyOTP
 */
@WebServlet("/VerifyOTP")
public class VerifyOTP extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyOTP() {
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
		String otp = request.getParameter("otp");
		boolean status = false;
		
		String url = "jdbc:mysql://localhost:3306/eauthentication";
		String uname = "root";
		String pswd = "root";
		Connection conn=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, uname, pswd);
			String sql = "SELECT * FROM user WHERE otp=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, otp);
			
			ResultSet rs = statement.executeQuery();
			status = rs.next();
			if (status) {
				String query = "update user set otp=null;";
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.executeUpdate();
				RequestDispatcher rd = request.getRequestDispatcher("Attendance.html");
				rd.include(request,response);
			}
			else {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Invalid otp');");
				out.println("</script>");
				RequestDispatcher rd = request.getRequestDispatcher("Registration.html");
				rd.include(request,response);
			}
	}catch(Exception e) {
		
	}
	}
}