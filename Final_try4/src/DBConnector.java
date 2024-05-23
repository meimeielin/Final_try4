import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	private static String server = "jdbc:mysql://140.119.19.73:3315/";
	private static String database = "112306010"; // change to your own database
	private static String url = server + database + "?useSSL=false";
	private static String dusername = "112306010"; // change to your own username
	private static String dpassword = "7ze1v"; // change to your own password
	
	public static String getServerURL() {
		return url;
	}
	
	public static String getUserName() {
		return dusername;
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, dusername, dpassword);
			return conn;
		}
		catch (SQLException se) {
			System.out.println(se.getMessage());
			return null;
		}
	}
}
