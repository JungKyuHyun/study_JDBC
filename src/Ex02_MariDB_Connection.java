import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/*
1. 드라이브 참조(jar 추가)
2. 드라이버 로딩(JVM : Class.forName())
3. 연결객체 생성 -> DriverManager 클래스
4. 명령객체 생성 -> CRUD 작업 준비(Statement)
5. 명령실행 -> DQL(Select 1건, select multi row) => return ResultSet 객체
			 DML(insert, update, delete) => 결과 집합(ResultSet(x)) => 성공유무
6. 명령처리(2가지) : DQL(화면출력), DML(결과에 따른 처리 (성공, 실패))
7. 자원해제

JDBC API (인터페이스) : 표준화된 코드 사용(다형성 기반)
Connection
Statement
prepareStatement
ResultSet
 */
public class Ex02_MariDB_Connection {
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			//2. 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("MariaDB");
			
			//3. 연결객체 생성
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bit?useSSL=true&serverTimezone=UTC","root","1004");
			System.out.println(conn.isClosed() +"성공");
			
			//4. 명령객체 생성
			stmt = conn.createStatement();
			
			//4.1 실행문장
			String job = "";
			Scanner sc = new Scanner(System.in);
			System.out.println("직종입력 : ");
			job = sc.nextLine();
			
			String sql = "select empno, ename, job from emp where job='"+job+"'";
			//5. 명령실행
			//DQL (select) > stmt.executeQuery(sql) > return ResultSet 객체의 주소
			//DML (insert, delete, update) > 결과집합(x) > 반영결과(반영된 행의 수)
			//stmt.executeUpdate() >> return 반영된 행의 수
			//delete from emp; >> 14
			
			rs = stmt.executeQuery(sql);
			
			//6. 명령처리
			//DQL : 1. 결과가 없는 경우(where empno=9999)
			//		2. 결과가 1건이 경우(PK, Unique 걸려 있는 컬럼 : where empno=7788)
			//		3. 결과가 여러 개의 row >> select * from emp where deptno=20;
			
			//1. 간단하다(단순)
			//2. 결과 집합이 없는 경우 로직에 대한 처리가 안되요. 
//			while(rs.next()) {
//				System.out.println(rs.getInt("empno") + "," + 
//									rs.getString("ename") + "," +
//									rs.getString("job"));
//				
//			}
			
			//1. 결과가 있는 경우 없는 경우 처리 가능
			//2. Multi row Read가 안되요!!
//			if(rs.next()) {
//				System.out.println(rs.getInt("empno") + "," + 
//									rs.getString("ename") + "," +
//									rs.getString("job"));
//			}else {
//				System.out.println("조회된 데이터가 없습니다.");
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
		//두개의 장점 로직 만들어 보세요(공식 같이 쓰이는 로직)
		//1. 결과 집합이 없는 경우 처리 가능
		//2. 싱글 로우 읽기
		//3. 멀티 로우 읽기
		//위 3가지를 만족하는 코드 생성.
			if(rs.next()) {
				do {
					System.out.println(rs.getInt("empno") + "," + 
							rs.getString("ename") + "," +
							rs.getString("job"));
					}while(rs.next());
				}else {
				System.out.println("조회된 데이터가 없습니다.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally { //자원해제
			if(rs != null) try{rs.close();}catch(Exception e){}
			if(stmt != null) try{stmt.close();}catch(Exception e) {}
			if(conn != null) try{conn.close();}catch(Exception e) {}
		
		}
	}
}
