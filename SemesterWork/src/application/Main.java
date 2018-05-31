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
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/Authentication.fxml"));
//			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Sample.fxml"));
			Scene scene = new Scene(root, 360, 270);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
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
