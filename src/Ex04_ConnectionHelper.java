import java.sql.Connection;

import kr.or.bit.utils.ConnectionHelper;

public class Ex04_ConnectionHelper {
	public static void main(String[] args) throws Exception {
		
		Connection conn = null;
		conn = ConnectionHelper.getConnection("oracle");
		System.out.println(conn.toString());
		System.out.println(conn.getMetaData().getDatabaseProductName());
		System.out.println(conn.getMetaData().getDatabaseProductVersion());
		System.out.println(conn.isClosed());
		ConnectionHelper.close(conn);
		System.out.println("연결여부 : " + conn.isClosed());
	
		//1. return : oracle.jdbc.driver.T4CConnection@4aa8f0b4
		//2. return : oracle.jdbc.driver.T4CConnection@4aa8f0b4
		//result : getConnection() 함수 호출시마다 새로운 객체 생성
		//하나의 APP 연결 객체 하나만 사용해도 되지 않을까 ...
		//한번 만들어서 재사용?? 
		
		Connection myconn = null;
		myconn = ConnectionHelper.getConnection("mysql", "root", "1004");
		System.out.println(myconn.toString());
		System.out.println(myconn.getMetaData().getDatabaseProductName());
		System.out.println(myconn.getMetaData().getDatabaseProductVersion());
		System.out.println(myconn.isClosed());
		ConnectionHelper.close(myconn);
		System.out.println("연결여부 : " + myconn.isClosed());
	}

}
