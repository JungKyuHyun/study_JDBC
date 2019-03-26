import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * JDBC
* 1. JAVA 언어(APP)를 통해서 Oracle(소프트웨어) 연결하고 CRUD 작업
* 2. JAVA APP 의사결정 (어떤 RDBMS 사용) : Oracle, My-sql, Ms-sql
* 2-1. 프린터기 구매: 컴퓨터 - 프린터기 연결
* CASE 1: 정상 동작 (컴퓨터 프린터의 드라이버 내장)
* CASE 2: 프린터 회사 사이트 >> 드라이버 다운로드 >> 통합(제품)
* 2.2 my-sql (https://dev.mysql.com/downloads/connector/) zip 다운로드 ...
* 2.3 Oracle (C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib)
* 		>>ojdbc6.jar
* 
* 3. 드라이버 참조 방법 (작업하는 프로젝트에서 ...)(Java Project -> 드라이버 참조)
* 3.1 java >> 속성 >> build path >> jar (추가)
* 3.2 드라이버 사용 준비 완료 >> 메모리에 Load 사용 가능하도록(new...)
* 3.3 class.forName("클래스 이름")
* 
* 4. JAVA CODE (JDBC API)
* 4.1 import java.sql.*; >> 아래에 interface, class 제공
* 4.2 개발자는 interface 해석을 통해서 작업
* POINT (why : interface 형태로 제공 >> JDBC API : orcle, mysql ..>>  )
* oracle 사도 JDBC API 구현 .... (드라이버)
* mysql JDBC API 구현 .... (드라이버)
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

5. 작업순서
5.1 DB연결 -> 명령 -> 실행 -> 처리 -> 자원 해제
5.2 명령 : DDL(create, drop, alter)
		  CRUD(insert, select, update, delete)
5.3 실행 : DB 서버에게 전달(구문)
5.4 처리 : 화면 출력, 다른 곳에 전달 개발자가 ...
5.5 자원해제(성능)

TIP)
연결 문자열(connectionString) 설정
네트워크 통해서 DB접근
(DB서버 IP, PORT(1521, 3306), SID(전역DB: xe, orcl), 접속계정, 접속 비번
* 
 */

public class Ex01_Oracle_Connection {
	public static void main(String[] args) throws Exception {
		//DB연결
		Class.forName("oracle.jdbc.OracleDriver");
		System.out.println("오라클 드라이버 로딩");
		
		//getConnection Connection 구현한 객체의 주소값을 리턴
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","1004");
		System.out.println(conn.isClosed() + "정상(FALSE)");
		
		Statement stmt = conn.createStatement(); //명령 객체 얻어오기 
		
		//CRUD
		String sql = "select empno, ename, sal from emp";
		ResultSet rs = stmt.executeQuery(sql); //실행결과를 반환 받기
		
		//처리(화면처리)
		while(rs.next()) {
			System.out.println(rs.getInt("empno") + "/" + //인덱스로 접근해도 됨 
								rs.getString("ename") + "/" +
								rs.getInt("sal"));
																				
		}
		
		stmt.close();
		rs.close();
		conn.close();
		System.out.println("DB연결 : " + conn.isClosed());
	}
}
