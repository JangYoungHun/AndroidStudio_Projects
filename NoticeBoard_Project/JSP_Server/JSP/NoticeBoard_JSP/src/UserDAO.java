
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
	ResultSet resultSet;

	PrintWriter printWriter;

	public UserDAO() {

	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * // 연결 테스트용 param String test = request.getParameter("test"); if(test!=null)
		 * System.out.println(test); else System.out.println("test = null"); //
		 */

		printWriter = new PrintWriter(response.getOutputStream());

		String requestType = request.getParameter("type");

		// MYSQL 쿼리
		String query = "";

		// 요청 확인
		try {
			if (requestType != null) {
				// MYSQL 연결 초기화
				init_JDBC();
				switch (requestType) {
				case "isExist": {
					String id = request.getParameter("id");
					isExist(id);
				}
					break;
				case "register": {
					String id = request.getParameter("id");
					String pwd = request.getParameter("pwd");
					register(id, pwd);
				}
					break;
				case "login": {
					String id = request.getParameter("id");
					String pwd = request.getParameter("pwd");
					login(id, pwd);
				}
					break;
				}

			}

			else {
				// type parameter = 오류
				System.out.println("type parameter 요청 타입 오류");
				printWriter.write("SERVER_ERROR");
				printWriter.flush();
				printWriter.close();
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("명령 처리 중 에러 발생");
		}
	}

	@Override
	public void destroy() {

		try {
			if (connection != null)
				connection.close();

			if (printWriter != null)
				printWriter.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.destroy();
	}

	// MYSQL 연결
	void init_JDBC() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(SQL_URL, SQL_ID, SQL_PWD);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB연결 문제 발생");

		}

	}

	// id 중복확인
	void isExist(String id) throws SQLException {

		String query = "SELECT id FROM USER WHERE BINARY id=?";
		statement = connection.prepareStatement(query);
		statement.setString(1, id);
		resultSet = statement.executeQuery();

		// 이미 동일한 id가 존재
		if (resultSet.next()) {
			printWriter.write("ID_EXIST");
		}
		// 해당 아이디가 존재하지 않는다 -> 생성 가능
		else {
			printWriter.write("ID_NOT_EXIST");
		}

		printWriter.flush();
		printWriter.close();
	}

	// 회원가입 -> Database 에 등록
	void register(String id, String pwd) throws SQLException {
		
		try {
		String query = "INSERT INTO user(id,pwd,created) VALUES(?,?,NOW())";
		statement = connection.prepareStatement(query);
		// password를 그대로 저장하지 말고 hashCode 로 변경하는 로직 차후 추가 필요
		statement.setString(1, id);
		statement.setString(2, pwd);
		statement.executeUpdate();

		printWriter.write("SUCCESS");
		System.out.println("id 등록 성공");
		}
		catch (Exception e) {
			printWriter.write("FAILED");
			System.out.println("id 등록 실패");
		}		
		printWriter.flush();
		printWriter.close();
	}

	void login(String id, String pwd) throws SQLException {

		String query = "SELECT pwd FROM user WHERE BINARY id=?";
		statement = connection.prepareStatement(query);
		// password를 그대로 저장하지 말고 hashCode 로 변경하는 로직 차후 추가 필요
		statement.setString(1, id);
		resultSet =  statement.executeQuery();
		
		String password = "";
		if(resultSet.next()) {
			password = resultSet.getString("pwd");
		}
		
		if(password.equals(pwd)) {
		printWriter.write("SUCCESS");
		System.out.println("로그인 성공");
		}
		else {
		printWriter.write("FAILED");
		System.out.println("로그인 실패");
		}
		
		printWriter.flush();
		printWriter.close();
	}
	
}
