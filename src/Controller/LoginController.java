package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import dao.DataBaseConnection;
//import dao.LoginDAO;
//import dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.Criptografia;

public class LoginController implements Initializable{
	
	//private LoginDAO _loginDAO = null;
	//private UsuarioDAO _usuarioDAO = null;
	
	
	@FXML
	private TextField txtUser;
	
	@FXML
	private PasswordField pfPassword;
	
	@FXML
	private Button btLogin;
	
	@FXML
	private Button btCancel;
	
	@FXML
	private Label lbError;
	
	@FXML
	private ImageView imgLogo;
	
	@FXML
	private Button btConfig;
	
	
	@FXML
	private void onBtLoginAction() {
		
		if(!txtUser.getText().isEmpty()) {
			if(!pfPassword.getText().isEmpty()) {
				validateLogin();
			} else {
				lbError.setText("Coloque a senha");
			}
		} else {
			lbError.setText("Coloque o usuário");
		}
		
		
	}
	
	@FXML
	private void onBtCancelAction() {
		Stage stage = (Stage) btCancel.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void onBtConfigConexao() {
		openConfig("/view/ConexaoView.fxml");
	}

	private void openConfig(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Configurações da conexão");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.showAndWait();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		lbError.setText("");
		Image img;
		img = new Image("img/controle-de-estoque.jpg");
		imgLogo.setImage(img);
		
	}
	
	public void validateLogin() {
		//_usuarioDAO = new UsuarioDAO();
		Criptografia cript = new Criptografia();
		DataBaseConnection connection = new DataBaseConnection();
		Connection connectDB = connection.getConnection();
		
		String senha = cript.criptografar(pfPassword.getText());
		
		//boolean usuario = _usuarioDAO.findUsuario(txtUser.getText(), senha);
		
		String verifylogin = "SELECT count(1) FROM usuario WHERE login = '" + txtUser.getText() + "' and senha = '" + senha + "'";
		
		try {
			Statement statement = connectDB.createStatement();
			ResultSet result = statement.executeQuery(verifylogin);
			
			while(result.next()) {
				if(result.getInt(1) == 1) {
					lbError.setText("conectado");
				} else {
					lbError.setText("Usuário ou a senha não conferem");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*if(usuario) {
			lbError.setText("conectado");
		} else {
			lbError.setText("Usuário ou a senha não conferem");
		}*/
	}

}
