package JDBC;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
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

@WebServlet("/Service")
public class Service extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// mysql ���� ����
	private static final String SQL_URL = "jdbc:mysql://localhost:3306/noticeboard";
	private static final String SQL_ID = "admin";
	private static final String SQL_PWD = "admin";

	UserDao userDao;
	PrintWriter printWriter;

	//�ʱ�ȭ
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		userDao = UserDao.getInstance();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			request.setCharacterEncoding("UTF-8");
        	response.setContentType("text/html; charset=UTF-8");

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

				switch (requestType) {
				case "isIdExist": {
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
				case "addNotice": {
					String title = request.getParameter("title");
					String author = request.getParameter("author");
					String body = request.getParameter("body");
					String date = request.getParameter("date");
					addNotice(title,author,body,date);
				}
					break;
				case "readNotice": {
					readNotice();
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


	// id �ߺ�Ȯ��
	void isExist(String id) throws SQLException {

		int requestResult = userDao.isIdExist(id);
		// 0 : id �ߺ�
		// 1 : id �ߺ�X
		// -1 : Error
		String result = "";
		switch (requestResult) {
		case 0:
			result = "ID_EXIST";
			break;
		case 1:
			result = "ID_NOT_EXIST";
			break;
		case -1:
			result = "SERVER_ERROR";
			break;
		}

		printWriter.write(result);

		printWriter.flush();
		printWriter.close();

	}

	// ȸ������ -> Database �� ���
	void register(String id, String pwd) throws SQLException {

		boolean requestResult = userDao.register(id, pwd);
		String result = "";

		// ����
		if (requestResult) {
			result = "SUCCESS";
		}
		// ����
		else {
			result = "FAILED";
		}
		printWriter.write(result);
		printWriter.flush();
		printWriter.close();

	}

	//�α��� ���� Ȯ�� 
	void login(String id, String pwd) throws SQLException {

		int requestResult = userDao.login(id, pwd);
		// 0 : �α��� ����
		// 1 : �α��� ����
		// -1 : Error
		String result = "";
		switch (requestResult) {
		case 0:
			result = "FAILED";
			break;
		case 1:
			result = "SUCCESS";
			break;

		case -1:
			result = "SERVER_ERROR";
			break;
		}

		printWriter.write(result);

		printWriter.flush();
		printWriter.close();
	}

	//�޸� �߰�
	private void addNotice(String title, String author, String body, String date) {	
		boolean requestResult = userDao.addNotice(title, author, body, date);
		// true : �߰� ����
		// false : �߰� ����
		
		String result = "";
		
		result = requestResult ? "SUCCESS" : "FAILED";

		printWriter.write(result);

		printWriter.flush();
		printWriter.close();
	}
	//�޸� ���� �б�
	private void readNotice() {
		try {
		String result = userDao.readNotice();
		printWriter.write(URLEncoder.encode(result,"utf-8"));
		printWriter.flush();
		printWriter.close();
		}
		catch (Exception e) {
			printWriter.write("SERVER_ERROR");
		}
	}
	//�ڿ� ����
	@Override
	public void destroy() {
		userDao.destroy();
		super.destroy();
	}

}




