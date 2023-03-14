package ezen.jdbc;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC api를 이용한 rdbms(오라클) 연동
 * @author 김민영
 * @Date   2023. 2. 17.
 */
public class JDBCEx {

	public static void main(String[] args) throws UnknownHostException, IOException, SQLException, ClassNotFoundException {
//		Socket socket = new Socket("localhost",1521);
//		System.out.println("오라클 서버와 연결됨");
		//오라클이 어떤 응용 프로토콜을 이용하여 데이터 송수신을 하는지 알수가 없다
		
		//JDBC 표준 api를 이용한 연결
		//#1 오라클 드라이버 메모리 로드
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; //oracle
		String user= "hr";
		String password = "hr";
		//동적 드라이버 메모리 로드.
		Class.forName(driver);
		//Driver oracleDriver = new OracleDriver();
		System.out.println("오라클 드라이버 로딩 완료");

		
		//#2 오라클 서버 연결
		Connection con = null;
		con = DriverManager.getConnection(url, user, password);
		System.out.println("오라클 서버와 연결됨");
		System.out.println("전달받은 객체" + con);

		
		//#3 SQL 전송
		String sql = "SELECT employee_id FROM employees";
		PreparedStatement stmt = con.prepareStatement(sql);
		System.out.println(stmt);
		ResultSet rs = stmt.executeQuery(sql);
		//#4 결과집합에서 원하는 데이터 추출 
		while(rs.next()) {
			//컬럼의 순서로 데이터 읽기
			//int employeeId = rs.getInt(1);
			//컬럼 이름으로 데이터 읽기
			int employeeId = rs.getInt("employee_Id");
			String lastName = rs.getString("last_name");
			System.out.println(employeeId + ", " + lastName);
		}
		con.close();
	}
}
