package controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import model.Order;
import model.Worker;
import model.dao.DoneWorkDAO;
import model.dao.OrderDAO;

public class SalaryController implements Initializable{
	@FXML
	Label label;
	@FXML
	Label salary;
	@FXML
	ChoiceBox<Order> orders;
	
	@FXML
	public void calculate() {
		Worker w = (Worker)label.getScene().getUserData();
		label.setVisible(true);
		BigDecimal s = DoneWorkDAO.getSalary(orders.getSelectionModel().getSelectedItem().getOrder_id(), w.getWorker_id());
		salary.setText(s==null?"Ви не працювали\n над цим замовленням":s.toString());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orders.setItems(FXCollections.observableArrayList(OrderDAO.SelectAll()));
		orders.getSelectionModel().select(0);
		orders.setConverter(new StringConverter<Order>() {
			
			@Override
			public String toString(Order object) {
				return object!=null?object.getOrder_id()+"":"";
			}
			
			@Override
			public Order fromString(String string) {
				Scanner s = new Scanner(string);
				try {
					Order e = OrderDAO.getOrder(s.nextInt());
					s.close();
					return e;
				} catch (Exception e) {
					return null;
				}
			}
		});
	}
}
