package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import model.dao.AccountDAO;

public class ChangePasswordController {
	@FXML
	PasswordField passwordField;
	@FXML
	PasswordField repeatePasswordField;
	@FXML
	PasswordField oldPasswordField;
	
	@FXML
	public void ChangeData(){
		if(passwordField.getText().equals("")||repeatePasswordField.getText().equals("")||oldPasswordField.getText().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Заповніть всі поля.");
			alert.showAndWait();
			return;
		}
		String login = (String)passwordField.getScene().getUserData();
		boolean check = AccountDAO.checkPassword(oldPasswordField.getText(), login);
		if(check) {
		if(passwordField.getText().equals(repeatePasswordField.getText())) {
			AccountDAO.changePassword(passwordField.getText(), login);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setContentText("Пароль змінено.");
			alert.showAndWait();
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setContentText("Паролі не співпадають.");
			alert.showAndWait();
		}
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Старий пароль не вірний.");
			alert.showAndWait();
		}
	}

}
