package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DQL_Databaseconnection {

	public static void main(String[] args) 
	{
		String url="jdbc:mysql://localhost:3306/java10am";
     	String un="root";
		String pw="root";
		Connection con=null;
		Statement s=null;
		ResultSet r=null;
		PreparedStatement p=null;
		Scanner sc=new Scanner(System.in);
// without scanner class
		
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			System.out.println("loaded and registred successfully");
//			con=DriverManager.getConnection(url, un, pw);
//			s=con.createStatement();
//			String query="select * from stu";
//			r=s.executeQuery(query);
//			while(r.next()==true)
//			{
////				System.out.println(r.getInt("sid"));
////				System.out.println(r.getString("sname"));
//				System.out.println(r.getInt(1));
//				System.out.println(r.getString(2));
//			}
//			
//		} 
//		
//		catch (ClassNotFoundException e) {
//			
//		} catch (SQLException e) {
//			
//		}
//		finally {
//			try {
//				s.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
//		}
//		try {
//			con.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	    
// with scanner class
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url, un, pw);
			System.out.println("established connection between database and server");
			
//			System.out.println( "enter the sid ");
//			int sid=sc.nextInt();
			System.out.println( "enter the name ");
			String sname=sc.next();
			
//			String query="select * from stu where sid=?";
			String query="select * from stu where sname=?";
			
			
			p=con.prepareStatement(query);
			//p.setInt(1,sid);
			
			//p.setString(1,sid);
			p.setString(1,sname);
			
			r=p.executeQuery();
			if(r.next()) {
            int studentId=r.getInt(1)	;
            String Studentname=r.getString(2);
            System.out.println("Student ID :"+ studentId+" , "+" studentId : "+Studentname);
			}
			else {
				System.out.println("no student records found");
			}
			
			
			
			
		} 
		catch (ClassNotFoundException | SQLException e) {
			
		}
		
		finally {
			sc.close();
			
			   if(r!=null)
				try {
					r.close();
				} catch (SQLException e) {
					
				}
				
				if(p!=null)
					try {
						p.close();
						
					} 
				catch (SQLException e) {
						
					}
				if(con!=null)
					try {
						con.close();
					}
				catch (SQLException e) {
						
					}
				
			
			
				
		
		
	
		
			
			
	
	
	
	}
}
}
	
        
	