import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class User {
    
    public void enroll(String ID, String pw){
    	// Check ID is valid.
        if (ID.isEmpty()) {
        	JOptionPane.showMessageDialog(null, "The userID can't be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        	return;
        	
        } else if (ID.length() != 9) {
        	JOptionPane.showMessageDialog(null, "The userID should be 9 letters.", "Error", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        
        // Check Password is valid.
        if (pw.length() != 4) {
        	JOptionPane.showMessageDialog(null, "The password should be 4 letters.", "Error", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        
        // Check User is already existed or not.
        if (checkUserExist(ID)) {
        	JOptionPane.showMessageDialog(null, "The userID has existed.", "Error", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        
        
        if (!addNewUser(ID, pw)) {
        	JOptionPane.showMessageDialog(null, "Add new user failed!", "Error", JOptionPane.ERROR_MESSAGE);
        	return;
        }
    }

    public boolean login(String ID, String pw) {
    	// Check ID is valid.
        if (ID.isEmpty()) {
        	JOptionPane.showMessageDialog(null, "The userID can't be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        	
        } else if (ID.length() != 9) {
        	JOptionPane.showMessageDialog(null, "The userID should be 9 letters.", "Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
        
        // Check Password is valid.
        if (pw.length() != 4) {
        	JOptionPane.showMessageDialog(null, "The password should be 4 letters.", "Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
        
        if (checkUserIsVaild(ID, pw)) {
        	return true;
        }
        else {
        	JOptionPane.showMessageDialog(null, "Login failed!", "Error", JOptionPane.ERROR_MESSAGE);
        	return false;
        	
        }
    }
    
    
    private boolean checkUserExist(String ID){

    	try {
    		Statement stat = FP.conn.createStatement();
    		String query = "SELECT ID FROM Profile WHERE ID = " + ID;
    		if (stat.execute(query)) {
    			ResultSet rs = stat.getResultSet();
    			if (rs.next()) {
    				return true;
    			}
    		}
    	}
    	catch (SQLException se) {
    		JOptionPane.showMessageDialog(null, se.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
		return false;
		
    }

    private boolean addNewUser(String ID, String pw) {
    	try {
    		Statement stat = FP.conn.createStatement();
    		String query = String.format("INSERT INTO Profile (ID, PW) VALUES (%d, %d);", 
    									 Integer.parseInt(ID), 
    									 Integer.parseInt(pw));
			
    		stat.execute(query);
    		return true;
    	}
    	catch (SQLException se) {
    		JOptionPane.showMessageDialog(null, se.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
		return false;
    }
    
    private boolean checkUserIsVaild(String ID, String pw) {
    	
    	try {
    		Statement stat = FP.conn.createStatement();
    		String query = "SELECT * FROM Profile WHERE ID = " + ID + " AND PW = " + pw + ";";
    		if (stat.execute(query)) {
    			ResultSet rs = stat.getResultSet();
    			if (rs.next()) {
    				return true;
    			}
    		}
    	}
    	catch (SQLException se) {
    		JOptionPane.showMessageDialog(null, se.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
		return false;
    }
}

