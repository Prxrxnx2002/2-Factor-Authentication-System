

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

/**
 * Servlet implementation class RecordAttendance
 */
@WebServlet("/RecordAttendance")
public class RecordAttendance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordAttendance() {
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
		System.out.println(username);
        String attendanceStatus = request.getParameter("attendanceStatus");
        String date = request.getParameter("date");
        String time = request.getParameter("time");        
        
        String url = "jdbc:mysql://localhost:3306/eauthentication";
		String uname = "root";
		String pswd = "root";
		Connection conn=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, uname, pswd);
			System.out.println("connected to mysql");
			String query = "UPDATE attendance SET date = ?, time = ?, attendance_status = ? WHERE username = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, date);
			stmt.setString(2, time);
			stmt.setString(3,attendanceStatus );
			stmt.setString(4, username);
			int i = stmt.executeUpdate();
			System.out.println(i);
			if (i>0) {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Attendance Recorded!');");
				out.println("</script>");
				RequestDispatcher rd = request.getRequestDispatcher("Registration.html");
				rd.include(request,response);
				 }
			else {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Invalid username, please try again!');");
				out.println("</script>");
				RequestDispatcher rd = request.getRequestDispatcher("Attendance.html");
				rd.include(request,response);
			}
		}catch(Exception e) {	}
}
}