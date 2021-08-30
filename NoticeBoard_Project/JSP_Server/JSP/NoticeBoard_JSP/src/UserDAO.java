

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class UserDAO
 */
@WebServlet("/UserDAO")
public class UserDAO extends HttpServlet {


	
	
	private static final long serialVersionUID = 1L;
	
	// mysql 주소
	private static final String SQL_URL = "jdbc:mysql://localhost:3306/noticeboard";
	private static final String SQL_ID = "admin";
	private static final String SQL_PWD = "admin";
	Connection connection;
	PreparedStatement statement;
	ResultSet resultset;
	
	PrintWriter printWriter;
	
    public UserDAO() {    	   

    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	
    	// 연결 테스트용 param
    	String test = request.getParameter("test");
    	if(test!=null)
    		System.out.println(test);
    	else
    		System.out.println("test = null");
		//
    	
    	printWriter = new PrintWriter(response.getOutputStream());
    	
		String requestType = request.getParameter("type");
		
		// MYSQL 쿼리  
		String query = "";
		
		//요청 확인
		if(requestType != null) {
			//MYSQL 연결 초기화
	    	init_JDBC();
			switch(requestType) {
				case "isExist": {
					String id = request.getParameter("id");
					isExist(id);
				} break;
			}			


		}
		
		
		else {
			//type parameter = null 요청 정보가 없음
			System.out.println("type parameter = null 요청 타입 오류");
		}
		
    }
    
    
    //MYSQL 연결 
    void init_JDBC() {
    	
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(SQL_URL,SQL_ID,SQL_PWD);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB연결 문제 발생");
			
		}
    	
    }
    
    void isExist(String id) {
    	try {
        	String query = "SELECT id FROM USER WHERE id=?";
        	statement = connection.prepareStatement(query);
        	statement.setString(1, id);
        	resultset = statement.executeQuery();
        	
        	// 이미 동일한 id가 존재
        	if(resultset.next()) { 
        		printWriter.write("ID_EXIST");
        	}
        	//해당 아이디가 존재하지 않는다 -> 생성 가능
        	else {
        		printWriter.write("ID_NOT_EXIST");    		
        	}
        	
        	printWriter.flush();
        	printWriter.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

    }
    

}
