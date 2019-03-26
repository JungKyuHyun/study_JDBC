package kr.or.bit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/*
전체 프로젝트 (회원 : 전체 조회, 조건조회, 삭제, 수정 페이지 각각)
각각의 페이지에 공통적으로 필요한 요소
1. 드라이버 로딩
2. 연결객체, 명령, 자원해제 ....

3. 반복적인 코드는 ...
4. Oracle, Mysql 사용 둘다 연동 ...
5. 공통적인 내용을 가지는 클래스 설계 
6. ConnectionHelper 설계 >> 기능(method) 자주 사용 >> static >> overloading
7. 패턴 >> 하나의 객체 공유 >> Singleton

 */

public class ConnectionHelper {
    //함수 parameter 특정 문자열 (oracle , mysql)
    public static Connection getConnection(String dsn) { //oracle , mysql
        Connection conn = null;
        try {
            if(dsn.equals("oracle")) {
                Class.forName("oracle.jdbc.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","bituser","1004");
            }else if(dsn.equals("mysql")){
                Class.forName("oracle.jdbc.OracleDriver");
                conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/sampledb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=true",
                        "root", "1004");
            }
        }catch (Exception e) {
        }
        return conn;
    }
    
    public static Connection getConnection(String dsn, String id , String pwd) { //oracle , mysql
        Connection conn = null;
        try {
            if(dsn.equals("oracle")) {
                Class.forName("oracle.jdbc.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",id,pwd);
            }else if(dsn.equals("mysql")){
                Class.forName("oracle.jdbc.OracleDriver");
                conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/sampledb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=true",
                        id, pwd);
            }
        }catch (Exception e) {
        	System.out.println(e.getMessage());        	
        }finally {
//    		if(stmt != null) try{stmt.close();}catch(Exception e) {}
//			if(conn != null) try{conn.close();}catch(Exception e) {}
        }
        return conn;
    }
    
    public static void close(Connection conn) {
    	if(conn !=null) {
    		try {
    			conn.close();
    		}catch(Exception e) {
    			System.out.println(e.getMessage());
    		}
    	}
    }
    
    public static void close(Statement stmt) {
    	if(stmt !=null) {
    		try {
    			stmt.close();
    		}catch(Exception e) {
    			System.out.println(e.getMessage());
    		}
    	}
    }
    
    public static void close(ResultSet rs) {
    	if(rs !=null) {
    		try {
    			rs.close();
    		}catch(Exception e) {
    			System.out.println(e.getMessage());
    		}
    	}
    }
    
    public static void close(PreparedStatement pstmt) {
    	if(pstmt !=null) {
    		try {
    			pstmt.close();
    		}catch(Exception e) {
    			System.out.println(e.getMessage());
    		}
    	}
    }
}