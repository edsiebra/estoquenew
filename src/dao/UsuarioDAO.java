package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UsuarioDAO {

	DataBaseConnection connection = new DataBaseConnection();
	Connection connectDB = connection.getConnection();
	
	
	public boolean findUsuario(String login, String senha) {
		
		String sql = "SELECT id FROM usuario WHERE login = '" + login + "' and senha = '" + senha + "'";
		
		try {
			Statement statement = connectDB.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) {
				if(result.getBoolean(0)) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	
}
