package dao;

import java.sql.Connection;

public class LoginDAO {

	DataBaseConnection connection = new DataBaseConnection();
	Connection connectDB = connection.getConnection();
}
