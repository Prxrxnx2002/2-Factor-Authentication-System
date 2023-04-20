import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class Registerdata
 */
@WebServlet("/Registerdata")
public class Registerdata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registerdata() {
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
		
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		
		String url = "jdbc:mysql://localhost:3306/eauthentication";
		String uname = "root";
		String pswd = "root";
		Connection conn=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, uname, pswd);
			String query = "insert into user values (?, ?, ?, ?, ?,NULL)";
			String q2="insert into attendance values (?, NULL,NULL,NULL)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, username);
			stmt.setString(3, email);
			stmt.setString(4, phone);
			stmt.setString(5, password);
			int i = stmt.executeUpdate();
			PreparedStatement stmt1 = conn.prepareStatement(q2);
			stmt1.setString(1, username);
			stmt1.executeUpdate();
			if (i>0) {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Registerd!');");
				out.println("</script>");
				 RequestDispatcher rd = request.getRequestDispatcher("Registration.html");
				 rd.include(request,response);
				 }
			else {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Username taken');");
				out.println("</script>");
			}
			
			
	}catch(Exception e) {

}
}
}