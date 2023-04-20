import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//import jakarta.servlet.RequestDispatcher;
public class Login {

	public static boolean validate(String username, String password) {
		boolean status = false;
		
		String url = "jdbc:mysql://localhost:3306/eauthentication";
		String uname = "root";
		String pswd = "root";
		Connection conn=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, uname, pswd);
			String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			
			ResultSet rs = statement.executeQuery();
			status = rs.next();
			
	}catch(Exception e) {
		
	}
		return status;
	}
}
