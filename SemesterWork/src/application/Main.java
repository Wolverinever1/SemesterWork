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
//			Equipment e = new Equipment();
//			e.setId(409);
//			e.setDescription("general  machine");
//			session.save(e);
//			Workplace wp = new Workplace();
//			wp.setEquipment_id(e);
//			wp.setMachineNo(1);
//			session.save(wp);
//			session.getTransaction().commit();
			
//			Customer cust = new Customer(0, "Олена", new char[] {'1','2','3'}, "вулиця Щорсе 117"); 
//			Order o = new Order(0, new Date(0), cust);
//			OrderDAO.Add(o);
//			//
//			Order o = new Order(0, null, cust, null);
//			cust.getOrders().add(o);
//			session.save(cust);
//			session.save(o);
//			Worker w = new Worker(0, "Ставіп", "роогоргн", "оавіглпшгк", 3, null,null);
//			session.save(w);
//			EquipmentDAO.getWorkplaces(w);
			
//			session.createQuery("select workplaces from Equipment").list();s;
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
