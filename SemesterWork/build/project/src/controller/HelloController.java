package controller;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.dao.AccountDAO;
import model.resources.HibernateUtil;

public class HelloController implements Initializable {
	@FXML
	Label enterNick;
	@FXML
	TextField nickField;
	@FXML
	Label enterPassword;
	@FXML
	PasswordField passwordField;
	@FXML
	Label less6Smbls;
	@FXML
	Button signIn;
	@FXML
	Button signUp;
	@FXML
	ChoiceBox<String> chLang;

	@FXML
	public void SignIn() {
		String login = nickField.getText();
		String password = passwordField.getText();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] digest = md.digest();
			String passwordEncrypted = new String(digest, Charset.forName("UTF-8"));
			Object[] w = AccountDAO.isRegister(login, passwordEncrypted);
			if (w == null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("");
				alert.setContentText("Перевірте логін і пароль!");
				alert.showAndWait();
			} else {
				if ((boolean) w[1]) {
					try {
						DoneWorkWorkerController.worker_id = (int) w[0];
						DoneWorkWorkerController.login = login;
						AnchorPane root = (AnchorPane) FXMLLoader
								.load(getClass().getResource("/view/Done_work_worker.fxml"));
						Scene scene = new Scene(root, 751, 578);
						scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
						Stage primaryStage = new Stage();
						primaryStage.setTitle("Ниточка & голочка");
						primaryStage.setScene(scene);
						primaryStage.initModality(Modality.APPLICATION_MODAL);
						nickField.setText("");
						passwordField.setText("");
						primaryStage.showAndWait();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						SampleController.login = login;
						AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Sample.fxml"));
						Scene scene = new Scene(root, 850, 578);
						scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
						Stage primaryStage = new Stage();
						primaryStage.setTitle("Ниточка & голочка");
						primaryStage.setScene(scene);
						primaryStage.initModality(Modality.APPLICATION_MODAL);
						nickField.setText("");
						passwordField.setText("");
						primaryStage.showAndWait();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
		HibernateUtil.getSessionFactory().getCurrentSession();
		}catch(Throwable ts) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setContentText("Відсутнє з'єднання з сервером бази даних");
			alert.showAndWait();
		}
	}
}
