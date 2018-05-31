package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Done_work;
import model.Order;
import model.Worker;
import model.dao.DoneWorkDAO;
import model.dao.OrderDAO;
import model.dao.OrderProductsDAO;

public class DoneWorkWorkerController implements Initializable {
	@FXML
	TableView<Done_work> doneWorkTable;

	@FXML
	TableColumn<Done_work, Integer> columnOperationNo;
	@FXML
	TableColumn<Done_work, String> columnOperationName;
	@FXML
	TableColumn<Done_work, BigDecimal> columnPrice;
	@FXML
	TableColumn<Done_work, BigDecimal> columnTime;
	@FXML
	TableColumn<Done_work, Integer> columnCount;
	@FXML
	TableColumn<Done_work, Integer> order_Id;
	@FXML
	TableColumn<Done_work, Integer> model;
	@FXML
	TextField countField;
	@FXML
	Text dataText;
	@FXML
	ChoiceBox<Order> ordersNoChoiseBox;

	@FXML
	CheckBox justThisOrder;
	@FXML
	CheckBox justActive;

	private ObservableList<Done_work> listAllDoneWork;
	public static int worker_id;
	public static String login;

	@FXML
	public void calculateSalary() {
		Stage stage = new Stage();
		AnchorPane root;
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Salary.fxml"));
			Scene scene = new Scene(root, 310, 260);
			Worker w = new Worker();
			w.setWorker_id(worker_id);
			scene.setUserData(w);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Зарплата");
			stage.setScene(scene);
			stage.resizableProperty().set(false);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void changeData() {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ChangeData.fxml"));
			Scene scene = new Scene(root, 400, 300);
			scene.setUserData(login);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Зміна паролю");
			primaryStage.setResizable(false);
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void doneWorkOnMouseClicked() {
		Done_work d_w = doneWorkTable.getSelectionModel().getSelectedItem();
		if (d_w != null) {
			dataText.setText(d_w.getOperation_id().getName() + " - " + d_w.getWorker_id().getLName() + " "
					+ d_w.getWorker_id().getFName() + " " + d_w.getWorker_id().getMName());
			countField.setText(new Integer(d_w.getCount_done()).toString());
		}

	}

	@FXML
	public void updateCount() {
		try {
			Done_work d_w = doneWorkTable.getSelectionModel().getSelectedItem();
			if (d_w != null) {
				Integer order_id = d_w.getModel().getOrder().getOrder_id();
				Integer model = d_w.getModel().getModel().getModel();
				Integer count = new Integer(countField.getText());
				int orderCount = OrderProductsDAO.getCount(new Integer(order_id), new Integer(model));
				BigDecimal doneCount = DoneWorkDAO.doneCount(new Integer(model), new Integer(order_id),
						doneWorkTable.getSelectionModel().getSelectedItem().getCount_done());
				if (doneCount.add(new BigDecimal(count)).compareTo(new BigDecimal(orderCount)) == 1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("");
					alert.setHeaderText("Увага!");
					alert.setContentText("Значення перебільшує максимально можливе.");
					alert.showAndWait();
				} else {
					doneWorkTable.getSelectionModel().getSelectedItem().setCount_done(count);
					DoneWorkDAO.Update(doneWorkTable.getSelectionModel().getSelectedItem());
					doneWorkTable.refresh();
				}
			}
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText(
					"Перевірте введені дані. Значення не може бути меншим, або дорівнювати 0, або містити символи, відмінні від цифр.");
			alert.showAndWait();
		}
	}

	public void groupBy() {
		if (justThisOrder.isSelected() && (!justActive.isSelected())) {
			List<Done_work> justThisOrderAll = DoneWorkDAO
					.selectJustThisOrder(ordersNoChoiseBox.getSelectionModel().getSelectedItem().getOrder_id());
			List<Done_work> justThisOrder = new LinkedList<>();
			for (Done_work d_w : justThisOrderAll) {
				if (d_w.getWorker_id().getWorker_id() == worker_id)
					justThisOrder.add(d_w);
			}
			ObservableList<Done_work> justThisOrderList = FXCollections.observableArrayList(justThisOrder);
			doneWorkTable.setItems(justThisOrderList);

		} else if ((!justThisOrder.isSelected()) && (!justActive.isSelected())) {
			doneWorkTable.setItems(listAllDoneWork);
		} else if ((!justThisOrder.isSelected()) && justActive.isSelected()) {
			List<Done_work> active = DoneWorkDAO.selectActive();
			List<Done_work> activeThisOrder = new LinkedList<>();
			for (Done_work d_w : active) {
				if (d_w.getWorker_id().getWorker_id() == worker_id)
					activeThisOrder.add(d_w);
			}
			ObservableList<Done_work> justThisOrderAndActive = FXCollections.observableArrayList(activeThisOrder);
			doneWorkTable.setItems(justThisOrderAndActive);
		} else if ((justThisOrder.isSelected()) && justActive.isSelected()) {
			List<Done_work> active = DoneWorkDAO.selectActive();
			List<Done_work> activeThisOrder = new LinkedList<>();
			for (Done_work d_w : active) {
				if (d_w.getModel().getOrder().getOrder_id() == ordersNoChoiseBox.getSelectionModel().getSelectedItem()
						.getOrder_id() && d_w.getWorker_id().getWorker_id() == worker_id)
					activeThisOrder.add(d_w);
			}
			ObservableList<Done_work> justThisOrderAndActive = FXCollections.observableArrayList(activeThisOrder);
			doneWorkTable.setItems(justThisOrderAndActive);
		} else if ((!justThisOrder.isSelected()) && (!justActive.isSelected())) {
			doneWorkTable.setItems(listAllDoneWork);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(worker_id);
		ordersNoChoiseBox.setItems(FXCollections.observableArrayList(OrderDAO.SelectAll()));
		ordersNoChoiseBox.getSelectionModel().select(0);
		ordersNoChoiseBox.setConverter(new StringConverter<Order>() {

			@Override
			public String toString(Order object) {
				return object != null ? object.getOrder_id() + "" : "";
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

		model = new TableColumn<Done_work, Integer>("Модель");
		columnOperationNo = new TableColumn<Done_work, Integer>("Код\nоперації");
		columnOperationName = new TableColumn<Done_work, String>("Назва операції");
		columnPrice = new TableColumn<Done_work, BigDecimal>("Ціна");
		columnTime = new TableColumn<Done_work, BigDecimal>("Час");
		columnCount = new TableColumn<Done_work, Integer>("Кількість");
		order_Id = new TableColumn<Done_work, Integer>("№ замовлення");

		columnOperationNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Done_work, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Done_work, Integer> param) {
						return new ReadOnlyObjectWrapper<>(param.getValue().getOperation_id().getOperationId());
					}
				});
		columnOperationName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Done_work, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Done_work, String> param) {
						return new ReadOnlyObjectWrapper<>(param.getValue().getOperation_id().getName());
					}
				});
		columnPrice.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Done_work, BigDecimal>, ObservableValue<BigDecimal>>() {

					@Override
					public ObservableValue<BigDecimal> call(CellDataFeatures<Done_work, BigDecimal> param) {
						return new ReadOnlyObjectWrapper<>(param.getValue().getOperation_id().getPrice());
					}
				});
		columnTime.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Done_work, BigDecimal>, ObservableValue<BigDecimal>>() {

					@Override
					public ObservableValue<BigDecimal> call(CellDataFeatures<Done_work, BigDecimal> param) {
						return new ReadOnlyObjectWrapper<>(param.getValue().getOperation_id().getTime());
					}
				});

		columnCount.setCellValueFactory(new PropertyValueFactory<>("count_done"));
		order_Id.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Done_work, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Done_work, Integer> param) {
						return new ReadOnlyObjectWrapper<>(param.getValue().getModel().getOrder().getOrder_id());
					}
				});

		model.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Done_work, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Done_work, Integer> param) {
						return new ReadOnlyObjectWrapper<>(param.getValue().getModel().getModel().getModel());
					}
				});
		doneWorkTable.getColumns().addAll(order_Id, model, columnOperationNo, columnOperationName, columnPrice,
				columnTime, columnCount);
		listAllDoneWork = FXCollections.observableArrayList(DoneWorkDAO.selectWorkersAll(worker_id));
		doneWorkTable.setItems(listAllDoneWork);

	}

}
