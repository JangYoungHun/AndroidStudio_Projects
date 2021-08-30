

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
	
	// mysql �ּ�
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
    	
    	
    	// ���� �׽�Ʈ�� param
    	String test = request.getParameter("test");
    	if(test!=null)
    		System.out.println(test);
    	else
    		System.out.println("test = null");
		//
    	
    	printWriter = new PrintWriter(response.getOutputStream());
    	
		String requestType = request.getParameter("type");
		
		// MYSQL ����  
		String query = "";
		
		//��û Ȯ��
		if(requestType != null) {
			//MYSQL ���� �ʱ�ȭ
	    	init_JDBC();
			switch(requestType) {
				case "isExist": {
					String id = request.getParameter("id");
					isExist(id);
				} break;
			}			


		}
		
		
		else {
			//type parameter = null ��û ������ ����
			System.out.println("type parameter = null ��û Ÿ�� ����");
		}
		
    }
    
    
    //MYSQL ���� 
    void init_JDBC() {
    	
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(SQL_URL,SQL_ID,SQL_PWD);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB���� ���� �߻�");
			
		}
    	
    }
    
    void isExist(String id) {
    	try {
        	String query = "SELECT id FROM USER WHERE id=?";
        	statement = connection.prepareStatement(query);
        	statement.setString(1, id);
        	resultset = statement.executeQuery();
        	
        	// �̹� ������ id�� ����
        	if(resultset.next()) { 
        		printWriter.write("ID_EXIST");
        	}
        	//�ش� ���̵� �������� �ʴ´� -> ���� ����
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
