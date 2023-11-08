package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import utils.Criptografia;


public class DataBaseConnection {
	public Connection databaseLink;
	
	private String txtDB = null;
	private String txtUser = null;
	private String txtPsw = null;
	private String txtPorta = null;
	private String txtHost = null;
	private boolean recalcular = false;
	
	
	public Connection getConnection() {
		Criptografia gript = new Criptografia();
		
		String senha = null;
		
		Properties props = loadPrperties();
		if(props != null) {
			txtHost = props.getProperty("iphost");
			txtPorta = props.getProperty("porta");
			txtDB = props.getProperty("banco");
			txtUser = props.getProperty("user");
			txtPsw = props.getProperty("senha");
			if(props.getProperty("recalcular") == null) {
				recalcular = true;
			} else {
				recalcular = Boolean.parseBoolean(props.getProperty("recalcular"));
			}
		} else {
			txtHost = "localhost";
			txtPorta ="5432";
			txtDB = "estoque";
			txtUser = "postgres";
			txtPsw = "database";
			recalcular = true;
		}
		
		if(recalcular) {
			senha = txtPsw;
			txtPsw = gript.criptografar(txtPsw);
			
			try {
				FileOutputStream fos = new FileOutputStream("conect.properties");
				props.store(fos, "propiedades_conexao");
				fos.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			senha = gript.deserializa(txtPsw);
		}
		
		
		String url = "jdbc:postgresql://" + txtHost + ":" + txtPorta + "/" + txtDB;
		
		try {
			Class.forName("org.postgresql.Driver");
			databaseLink = DriverManager.getConnection(url, txtUser, senha);
			
		}catch (ClassNotFoundException  e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return databaseLink;
	}
	
	private Properties loadPrperties() {
		try (FileInputStream fs = new FileInputStream("conect.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}