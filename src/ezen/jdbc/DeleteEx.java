package ezen.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteEx {
	// db 연결정보 상수처리
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe"; //oracle
	private static final String user= "hr";
	private static final String password = "hr";
	

	public static void DeleteDepartment(int departmentID) throws ClassNotFoundException {
		Class.forName(driver);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			StringBuilder sb = new StringBuilder();
			sb.append(" DELETE FROM departments")
			.append(" WHERE department_id = ?");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, departmentID); //몇번째 바인딩?
			int count = pstmt.executeUpdate();
			System.out.println(count+"개의 행이 삭제되었습니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	

	public static void main(String[] args) {
			try {
				DeleteDepartment(730);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		
	}

}
