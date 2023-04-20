import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;  
import javax.mail.*;  
import javax.mail.internet.*;  
//import javax.activation.*;

public class OTP {
	public static StringBuilder genOTP() {
		String str = "0123456789";
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(str.length());
            otp.append(str.charAt(index));
        }
		return otp;
	}
	
public static void storeotp(StringBuilder otp1, String username) {
	String otp = otp1.toString();
	String url = "jdbc:mysql://localhost:3306/eauthentication";
	String uname = "root";
	String pswd = "root";
	Connection conn=null;
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, uname, pswd);
		String str = "UPDATE user SET otp= ? WHERE username = ?;";
		PreparedStatement statement = conn.prepareStatement(str);
		statement.setString(1, otp);
		statement.setString(2, username);
		int i = statement.executeUpdate();
		if (i>0) {
			System.out.println("OTP stored");
		}
	}catch(Exception e) {
		}
}

public static void sendotp(StringBuilder otp1, String username) {
	String otp = otp1.toString();
	String url = "jdbc:mysql://localhost:3306/eauthentication";
	String uname = "root";
	String pswd = "root";
	Connection conn=null;
	String email=null;	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, uname, pswd);
		String str = "SELECT email FROM user WHERE username=?";
		PreparedStatement statement = conn.prepareStatement(str);
		statement.setString(1, username);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			email=rs.getString("email");
		}
		String to = email;
		System.out.println(to);
		String from = "prxrxnxcp@gmail.com";
		String password = "rugzhlvkpmdwjhdc"; //secret key
		String host ="smtp.gmail.com";
		
		//Session  object
		Properties prop = new Properties();
	    prop.put("mail.smtp.auth", true);
	    prop.put("mail.smtp.starttls.enable", "true");
	    prop.put("mail.smtp.host", host);
	    prop.put("mail.smtp.port", 587);
	    prop.put("mail.smtp.ssl.trust", host);
	    prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		Session session = Session.getInstance(prop,  
			    new javax.mail.Authenticator() {  
			      protected PasswordAuthentication getPasswordAuthentication() {
			    	  return new PasswordAuthentication(from,password);  
			      }  
			    }); 
		//debug session issues
		session.setDebug(true);
		//compose message
		try {
		 MimeMessage message = new MimeMessage(session);  
	     message.setFrom(new InternetAddress(from));  
	     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
	     message.setSubject("OTP for verification");  
	     message.setText(otp); 
	     Transport.send(message);
	     System.out.println("message sent");
		}catch(MessagingException mex) {mex.printStackTrace();}
}catch(Exception e) {}
	}
}