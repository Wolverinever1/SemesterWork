package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.resources.HibernateUtil;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
//		MessageDigest md;
//		try {
//			md = MessageDigest.getInstance("SHA-256");
//			md.update("2".getBytes());
//			byte[] digest = md.digest();
//			String passwordEncrypted = new String(digest);
//			Account account = new Account();
//			account.setLogin("2");
//			account.setIs_dressmaker(0);
//			account.setPassword(passwordEncrypted);
//			AccountDAO.Add(account);
//			System.out.println(passwordEncrypted);
//		} catch (NoSuchAlgorithmException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/Authentication.fxml"));
//			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/Sample.fxml"));
//			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/Done_work_worker.fxml"));
			Scene scene = new Scene(root, 360, 270);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Вхід");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		HibernateUtil.getSessionFactory().close();
		Platform.exit();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
