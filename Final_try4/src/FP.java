import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.*;

public class FP {
	
	public static Connection conn = null;
	
	public static void main(String[] args) {
		conn = DBConnector.getConnection();
		if (conn == null) {
			System.exit(-1);
		}
		else {
			System.out.println("Connection Success!");
		}
		LoginFrame frame = new LoginFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
