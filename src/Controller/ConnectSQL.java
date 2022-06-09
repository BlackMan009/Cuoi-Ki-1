package Controller;

import java.sql.*;

public class ConnectSQL {
	public static Connection openConnection() throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection connection = DriverManager
				.getConnection("jdbc:sqlserver://localhost:1433;databaseName=BaiTapLonJava;user=sa;password=20112003");

		return connection;
	}
}