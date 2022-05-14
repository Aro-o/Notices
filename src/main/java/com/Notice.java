package com;

import java.sql.*;

public class Notice {

    public Connection connect(){
    	
        //database connection details
        String dbDriver = "com.mysql.jdbc.Driver";
        String dbURL = "jdbc:mysql://localhost:3306/";
        String dbName = "powergridsystem";
        String dbUsername = "root";
        String dbPassword = "";
        
        Connection conn = null;
        
        try {
        	//connecting the database
        	Class.forName(dbDriver);
        	conn = DriverManager.getConnection(dbURL+dbName, dbUsername, dbPassword);
        	
        	//if successfully connected this will be printed in the terminal
        	System.out.print("Database connected successfully");
        } catch(Exception e) {
        	e.printStackTrace();
        }
        
        return conn;
    }
    
    
    //method to insert data
    public String insertNotice(String topic, String areasAffected, String date, String details) {
    	Connection conn = connect();
    	
    	String Output = "";
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "INSERT INTO notices (topic,areasAffected,date,details) VALUES (?,?,?,?)";
        	
        	//binding data to SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setString(1, topic);
        	preparedStatement.setString(2, areasAffected);
        	preparedStatement.setString(3, date);
        	preparedStatement.setString(4, details);
        	
        	//execute the SQL statement
        	preparedStatement.execute();
        	conn.close();
        	
        	Output = "Notice inserted successfully";
        	
    	} catch(Exception e) {
    		Output = "Failed to insert the notice";
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    //method to update data
    public String updateNotice(String id, String topic, String areasAffected, String date, String details) {
    	Connection conn = connect();
    	
    	String Output = "";
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "UPDATE notices SET topic = ?,areasAffected = ?,date = ?,details = ? WHERE id = ?";
        	
        	//binding data to SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setString(1, topic);
        	preparedStatement.setString(2, areasAffected);
        	preparedStatement.setString(3, date);
        	preparedStatement.setString(4, details);
        	preparedStatement.setString(5, id);
        	
        	//execute the SQL statement
        	preparedStatement.executeUpdate();
        	conn.close();
        	
        	Output = "Notice updated successfully";
        	
    	} catch(Exception e) {
    		Output = "Failed to update the item";
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    
    //method to read data
    public String readNotices() {
    	Connection conn = connect();
    	
    	String Output = "";
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "SELECT * FROM notices";
        	
        	//executing the SQL query
        	Statement statement = conn.createStatement();
        	ResultSet resultSet = statement.executeQuery(query);
        	
        	// Prepare the HTML table to be displayed
    		Output = "<table border='1'><tr><th>Topic</th>" +"<th>Areas Affected</th><th>Date</th>"
    		+ "<th>Details</th>"
    		+ "<th>Update</th><th>Remove</th></tr>";
        	
        	while(resultSet.next()) {
        		String id = Integer.toString(resultSet.getInt("id"));
        		String topic = resultSet.getString("topic");
        		String areasAffected = resultSet.getString("areasAffected");
        		String date = resultSet.getString("date");
        		String details = resultSet.getString("details");
        		
        		// Add a row into the HTML table
        		Output += "<tr><td><input id='hidNoticeIDUpdate' name='hidNoticeIDUpdate' type='hidden' value='"+id+"'>" + topic + "</td>"; 
        		Output += "<td>" + areasAffected + "</td>"; 
        		Output += "<td>" + date + "</td>"; 
        		Output += "<td>" + details + "</td>";
        		
        		// buttons
        		Output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-sm btn-secondary'></td>" 
        				+ "<td><form method='post' action='notices.jsp'>"
        				+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-sm btn-danger'>"
        				+ "<input name='hidNoticeIDDelete' type='hidden' value='" + id + "'>"
        				+ "</form></td></tr>";
        	}

        	conn.close();
        	
        	// Complete the HTML table
        	Output += "</table>";
        	
    	} catch(Exception e) {
    		Output = "Failed to read the items";
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    //method to delete data
    public String deleteNotice(String id) {
    	String Output = "";
    	Connection conn = connect();
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "DELETE FROM notices WHERE id = ?";
        	
        	//binding data to the SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setInt(1, Integer.parseInt(id));
        	
        	//executing the SQL statement
        	preparedStatement.execute();
        	conn.close();
        	
        	Output = "Deleted successfully";
        	
    	} catch(Exception e) {
    		Output = "Failed to delete the item";
    		System.err.println(e.getMessage());
    	}
    	return Output;
    }
}
