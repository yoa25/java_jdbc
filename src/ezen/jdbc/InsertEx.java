package ezen.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertEx {
	// db 연결정보 상수처리
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe"; //oracle
	private static final String user= "hr";
	private static final String password = "hr";
	
	public static void addDepartment(String departmentName, int managerId) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, user, password);
		
		
		StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO departments (department_id, department_name, manager_id)")
//		.append(" VALUES(departments_seq.NEXTVAL, '"+departmentName+"')");
		.append(" VALUES(departments_seq.NEXTVAL, ?, ?)");
		
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setString(1, departmentName); //몇번째 바인딩?
		pstmt.setInt(2, managerId);
	
		int count = pstmt.executeUpdate();
		System.out.println(count+"개의 행이 추가되었습니다");
		con.close();
	}
	
	//위의 코드 정리하기
	public static void addDepartment2(String departmentName, int managerId) throws ClassNotFoundException {
		Class.forName(driver);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			//디폴트 값:true
			con.setAutoCommit(false);
			StringBuilder sb = new StringBuilder();
			sb.append(" INSERT INTO departments (department_id, department_name, manager_id)")
//		.append(" VALUES(departments_seq.NEXTVAL, '"+departmentName+"')");
			.append(" VALUES(departments_seq.NEXTVAL, ?, ?)");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, departmentName); //몇번째 바인딩?
			pstmt.setInt(2, managerId);
			int count = pstmt.executeUpdate();
			
			//트랜잭션 연습을 위한 UPDATE SQL 연습
			StringBuilder sb2 = new StringBuilder();
			sb2.append(" UPDATE departments")
			.append(" SET location_id = ?")
			.append(" WHERE department_id = ?");
			pstmt = con.prepareStatement(sb2.toString());
			pstmt.setInt(1, 1000);
			pstmt.setInt(2, 100);
			count = pstmt.executeUpdate();
			
			con.commit();
			System.out.println(count+"개의 행이 추가되었습니다");
			
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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
//				addDepartment("점심팀", 100);
				addDepartment2("식사후 팀", 200);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		
	}

}
