package work.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DAOBase {
	private Connection conn = null;
	public void closeDBResources(ResultSet rs, Statement stmt, 
			PreparedStatement pstmt, Connection conn) {
		
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // ojdbc6.jar 占쏙옙占쏙옙譴占쏙옙占� 占쌨모리울옙 占쏙옙占쏙옙
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoyi?verifyServerCertificate=false&amp; useSSL=false", 
					"root", "!djaak4124");
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙 占싱울옙占쏙옙 占쏙옙占쏙옙(connection) 占쏙옙체 占쏙옙占쏙옙
		return null;
	}

}
