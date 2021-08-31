
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
	ResultSet resultSet;

	PrintWriter printWriter;

	public UserDAO() {

	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * // ���� �׽�Ʈ�� param String test = request.getParameter("test"); if(test!=null)
		 * System.out.println(test); else System.out.println("test = null"); //
		 */

		printWriter = new PrintWriter(response.getOutputStream());

		String requestType = request.getParameter("type");

		// MYSQL ����
		String query = "";

		// ��û Ȯ��
		try {
			if (requestType != null) {
				// MYSQL ���� �ʱ�ȭ
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
				// type parameter = ����
				System.out.println("type parameter ��û Ÿ�� ����");
				printWriter.write("SERVER_ERROR");
				printWriter.flush();
				printWriter.close();
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("��� ó�� �� ���� �߻�");
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

	// MYSQL ����
	void init_JDBC() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(SQL_URL, SQL_ID, SQL_PWD);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB���� ���� �߻�");

		}

	}

	// id �ߺ�Ȯ��
	void isExist(String id) throws SQLException {

		String query = "SELECT id FROM USER WHERE BINARY id=?";
		statement = connection.prepareStatement(query);
		statement.setString(1, id);
		resultSet = statement.executeQuery();

		// �̹� ������ id�� ����
		if (resultSet.next()) {
			printWriter.write("ID_EXIST");
		}
		// �ش� ���̵� �������� �ʴ´� -> ���� ����
		else {
			printWriter.write("ID_NOT_EXIST");
		}

		printWriter.flush();
		printWriter.close();
	}

	// ȸ������ -> Database �� ���
	void register(String id, String pwd) throws SQLException {
		
		try {
		String query = "INSERT INTO user(id,pwd,created) VALUES(?,?,NOW())";
		statement = connection.prepareStatement(query);
		// password�� �״�� �������� ���� hashCode �� �����ϴ� ���� ���� �߰� �ʿ�
		statement.setString(1, id);
		statement.setString(2, pwd);
		statement.executeUpdate();

		printWriter.write("SUCCESS");
		System.out.println("id ��� ����");
		}
		catch (Exception e) {
			printWriter.write("FAILED");
			System.out.println("id ��� ����");
		}		
		printWriter.flush();
		printWriter.close();
	}

	void login(String id, String pwd) throws SQLException {

		String query = "SELECT pwd FROM user WHERE BINARY id=?";
		statement = connection.prepareStatement(query);
		// password�� �״�� �������� ���� hashCode �� �����ϴ� ���� ���� �߰� �ʿ�
		statement.setString(1, id);
		resultSet =  statement.executeQuery();
		
		String password = "";
		if(resultSet.next()) {
			password = resultSet.getString("pwd");
		}
		
		if(password.equals(pwd)) {
		printWriter.write("SUCCESS");
		System.out.println("�α��� ����");
		}
		else {
		printWriter.write("FAILED");
		System.out.println("�α��� ����");
		}
		
		printWriter.flush();
		printWriter.close();
	}
	
}
