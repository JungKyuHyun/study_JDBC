import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.or.bit.utils.SingletonHelper;

/*
PreparedStatement (준비된 Statement )
 (1) 설명
    미리 SQL문이 셋팅된 Statement 가 DB가 전송되어져서   컴파일되어지고, SQL문의 ?만 나중에 추가 셋팅해서 실행
   이 되어지는 준비된 Statement
 (2) 장점
    <1> Statement 에 비해서 반복적인 SQL문을 사용할 경우에  더 빠르다. ( 특히, 검색문 )
    (특히, 검색문) : query 문장 DB서버에 보내서 컴파일...
          실행할떄마다 ? (parameter) 전송
 <2> DB컬럼타입과 상관없이 ?하나로 표시하면 되므로   개발자가 헷깔리지 않고 쉽다. ( 특히, INSERT문 )
      (이유: ?를 제외한 SQL문이 DB에서 미리 컴파일되어져서 대기)

 (3) 단점
    SQL문마다 PreparedStatement 객체를 각각 생성해야 하므로 재사용불가
    (but, Statement 객체는 SQL문이 달라지더라도 한 개만 생성해서  재사용이 가능하다. )

 (4) 특징
    <1> Statement stmt = con.createStatement(); //생성       stmt.execute(sql);//실행
 <2> PreparedStatement pstmt = con.prepareStatement(sql); //생성      pstmt.execute(); //실행

 (5) 주의
    DB 객체들(table, ..)의 뼈대(   테이블명 or 컬럼명 or 시퀀스명 등의 객체나 속성명)은
     ?로 표시할 수 없다.
    즉, data 자리에만 ?로 표시할 수 있다.
    cf) 그래서, DDL문에서는 PreparedStatement를 사용하지 않는다.

    장점 : 보안, 미리 쿼리문 컴파일 - parameter 만 전송 (네트워크 트래픽 감소)
    단점 : 쿼리문의 재사용 불가

4. CallableStatement ( 호출할 수 있는 Statement )
 (1) 설명
    DataBase 에 미리 컴파일되어 있는 Stored Procedure 를
   호출하기 위한 Statement

 (2) 생성 / 호출
    String sql = "{call incre(?,?)}";
    CallableStatement cstmt = con.prepareCall(sql);

   (ex: day3/JDBC12.java )



5. 동적 커서 이동
  (1) 설명
    JDBC 2.0 부터는 ResultSet 의 커서가 원하는 위치대로
 이동 가능 함
 -> 이용하려면 stmt 생성시 아래의 방법으로 옵션을 줌

  (2) 방법
    -> con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
       ResultSet.CONCUR_UPDATABLE);

    /* Statement createStatement(int resultSetType,
                         int resultSetConcurrency)
                         throws SQLException
 1.resultSetType
  (1) ResultSet.TYPE_FORWARD_ONLY,
  (2) ResultSet.TYPE_SCROLL_INSENSITIVE,
  (3) ResultSet.TYPE_SCROLL_SENSITIVE
 2.resultSetConcurrency
  (1) ResultSet.CONCUR_READ_ONLY
  (2) ResultSet.CONCUR_UPDATABLE

(3) 주요 ResultSet 의 커서 이동 메소드
   <1> rs.next(); //커서를 한칸씩 내림
   <2> rs.previous(); //커서를 한칸씩 올림
   <3> rs.beforeFirst(); //커서를 BOF에 위치
   <4> rs.afterLast(); // 커서를 EOF에 위치


*/
public class Ex06_Oracle_PreparedStatement {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = SingletonHelper.getConnection("oracle");
            String sql = "select empno, ename from emp where deptno=?";
            // where id? and name=?, and job=?
            // ? 개수만큼의 설정...
            
            //Statement stmt = con.createSTatement();
            
            
            //여기부터 써야함 (재사용 X)
            pstmt = conn.prepareStatement(sql); // 쿼리문을 oracle 보내서 컴파일...
            
            //이후에는 parameter 정보만 설정해서 실행
            pstmt.setInt(1, 30); //deptno=? >> deptno = 30
            
            //stmt.executeQuery(Sql)
            rs = pstmt.executeQuery();
            if(rs.next()) {
                do {
                    System.out.println(rs.getInt(1) + "/" + rs.getString(2));
                    
                    
                } while(rs.next());
            } else {
                System.out.println("조회된 데이터가 없어요");
            }
            
            
        } catch (Exception e) {
            
        } finally {
            SingletonHelper.close(rs);
            SingletonHelper.close(pstmt);
//            SingletonHelper.close(conn); APP이 살아있는 한 Static은 사라지지 않음
            }
        
    }
}