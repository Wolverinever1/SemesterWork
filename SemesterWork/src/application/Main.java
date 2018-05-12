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
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/Sample.fxml"));
			Scene scene = new Scene(root,751,578);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
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
