package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Account;
import model.Customer;
import model.Equipment;
import model.Operation;
import model.Order;
import model.OrdersCustomerProducts;
import model.Product;
import model.ProductSaver;
import model.Sender;
import model.Worker;
import model.Workplace;
import model.dao.AccountDAO;
import model.dao.CustomerDAO;
import model.dao.EquipmentDAO;
import model.dao.OperationDAO;
import model.dao.OrderDAO;
import model.dao.OrderProductsDAO;
import model.dao.ProductDAO;
import model.dao.WorkerDAO;
import model.dao.WorkplaceDAO;

public class SampleController implements Initializable {
	@FXML
	TabPane Tabs;
	@FXML
	Tab tabWorkers;

	@FXML
	TableView<Worker> workers;
	@FXML
	TableView<Workplace> workerWPlacesTableView;
	@FXML
	TableView<Customer> customersTableView;
	@FXML
	TableView<Operation> operationsTableView;
	@FXML
	TableView<Equipment> equipmentTableView;
	@FXML
	TableView<Workplace> workplacesTableView;
	@FXML
	TableView<Product> productTableView;

	@FXML
	TableColumn<Product, Integer> productModel;
	@FXML
	TableColumn<Product, String> productName;
	@FXML
	TableColumn<Workplace, Integer> machineNo;
	@FXML
	TableColumn<Workplace, Integer> equipmentNo;
	@FXML
	TableColumn<Workplace, String> equipmentDescription;
	@FXML
	TableColumn<Worker, Integer> workersID;
	@FXML
	TableColumn<Worker, String> fName;
	@FXML
	TableColumn<Worker, String> mName;
	@FXML
	TableColumn<Worker, String> lName;
	@FXML
	TableColumn<Worker, Integer> grade;
	@FXML
	TableColumn<Worker, String> eMail;
	@FXML
	TableColumn<Customer, Integer> customerId;
	@FXML
	TableColumn<Customer, String> customerName;
	@FXML
	TableColumn<Customer, String> customerAddress;
	@FXML
	TableColumn<Customer, String> customerPhone;
	@FXML
	TableColumn<Operation, Integer> operationIdColumn;
	@FXML
	TableColumn<Operation, String> operationNameColumn;
	@FXML
	TableColumn<Operation, String> operationEquipmentIdColumn;
	@FXML
	TableColumn<Operation, BigDecimal> operationPriceColumn;
	@FXML
	TableColumn<Operation, BigDecimal> operationTimeColumn;
	@FXML
	TableColumn<Operation, Integer> operationGradeColumn;
	@FXML
	TableColumn<Equipment, Integer> equipmentIdColumn;
	@FXML
	TableColumn<Equipment, String> equipmentDescriptionColumn;
	@FXML
	TableColumn<Workplace, Integer> workplaceIdColumn;
	@FXML
	TableColumn<Workplace, Integer> workplaceEqIdColumn;
	@FXML
	TableColumn<Workplace, String> workplaceWorkerIdColumn;
	@FXML
	TableColumn<Workplace, String> workplaceWorkerFNColumn;
	@FXML
	TableColumn<Workplace, String> workplaceWorkerMNColumn;
	@FXML
	TableColumn<Workplace, String> workplaceWorkerLNColumn;

	@FXML
	TreeTableColumn<OrdersCustomerProducts, String> orderIdColumn;
	@FXML
	TreeTableColumn<OrdersCustomerProducts, String> orderDateColumn;
	@FXML
	TreeTableColumn<OrdersCustomerProducts, String> customerNameColumn;
	@FXML
	TreeTableColumn<OrdersCustomerProducts, String> orderProductCountColumn;
	@FXML
	TreeTableColumn<OrdersCustomerProducts, String> orderProductModel;
	@FXML
	TreeTableColumn<OrdersCustomerProducts, String> orderProductName;

	@FXML
	TreeTableView<OrdersCustomerProducts> OrdersTreeView;

	@FXML
	ChoiceBox<Integer> gradeChoiseBox;
	@FXML
	ChoiceBox<Integer> operationGradeChoiseBox;
	@FXML
	ChoiceBox<Equipment> operationEquipmentChoiseBox;
	@FXML
	ChoiceBox<Equipment> workplaceEquipmentChoiceBox;
	@FXML
	ChoiceBox<Worker> workplaceWorkerChoiceBox;

	@FXML
	CheckBox currentOrdersCheckBox;

	@FXML
	Button showDoneWorkButton;
	@FXML
	Button deleteOrderButton;

	@FXML
	TextField lNameField;
	@FXML
	TextField fNameField;
	@FXML
	TextField mNameField;
	@FXML
	TextField machineNoField;
	@FXML
	TextField customerPhoneTextArea;
	@FXML
	TextField operationTimeTextField;
	@FXML
	TextField operationPriceTextField;
	@FXML
	TextField operationNameTextField;
	@FXML
	TextField equipmentIdTextField;
	@FXML
	TextField equipmentDescriptionTextField;
	@FXML
	TextField workplaceMachineNoTextField;
	@FXML
	TextField productModelTextField;
	@FXML
	TextField productNameTextField;
	@FXML
	TextField email;

	@FXML
	TextArea customerAddressTextArea;
	@FXML
	TextArea customerNameTextArea;

	private ObservableList<Worker> list;
	private ObservableList<Workplace> listWorkPlaces;
	private ObservableList<Workplace> listAllWorkPlaces;
	private ObservableList<Customer> listCustomers;
	private ObservableList<Operation> listOperations;
	private ObservableList<Equipment> listEquipment;
	private ObservableList<Product> listProduct;
	public static String login;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gradeChoiseBox.getItems().addAll(1, 2, 3, 4, 5, 6);
		operationGradeChoiseBox.getItems().addAll(1, 2, 3, 4, 5, 6);
		workplaceEquipmentChoiceBox.getItems().addAll(EquipmentDAO.selectAll());
		workplaceWorkerChoiceBox.getItems().add(null);
		workplaceWorkerChoiceBox.getItems().addAll(WorkerDAO.selectAll());
		workplaceEquipmentChoiceBox.setConverter(new StringConverter<Equipment>() {

			@Override
			public String toString(Equipment object) {
				return object.getId() + " - " + object.getDescription();
			}

			@Override
			public Equipment fromString(String string) {
				Scanner s = new Scanner(string);

				Equipment e = EquipmentDAO.getEquipment(s.nextInt());
				s.close();
				return e;

			}
		});
		workplaceWorkerChoiceBox.setConverter(new StringConverter<Worker>() {

			@Override
			public String toString(Worker object) {
				return object != null
						? object.getWorker_id() + " - " + object.getLName() + " " + object.getFName() + " "
								+ object.getMName()
						: "";
			}

			@Override
			public Worker fromString(String string) {
				Scanner s = new Scanner(string);
				try {
					Worker e = WorkerDAO.getWorker(s.nextInt());
					s.close();
					return e;
				} catch (Exception e) {
					return null;
				}
			}
		});
		operationEquipmentChoiseBox.getItems().addAll(EquipmentDAO.selectAll());
		operationEquipmentChoiseBox.setConverter(new StringConverter<Equipment>() {

			@Override
			public String toString(Equipment object) {
				return object.getId() + "";
			}

			@Override
			public Equipment fromString(String string) {
				Scanner s = new Scanner(string);

				Equipment e = EquipmentDAO.getEquipment(s.nextInt());
				s.close();
				return e;

			}
		});
		initTable();
		initTreeView();
		initCustomers();
		initOperationsTable();
		initEquipmentList();
		intitWorkplaceTable();
		initProductTable();
	}

	private void initTable() {

		initList();
		listWorkPlaces = FXCollections.observableArrayList();
		workerWPlacesTableView.setItems(listWorkPlaces);
		workers.setItems(list);
		workersID = new TableColumn<>("ID");
		workersID.setCellValueFactory(new PropertyValueFactory<>("worker_id"));
		fName = new TableColumn<>("Ім'я");
		fName.setCellValueFactory(new PropertyValueFactory<>("fName"));
		mName = new TableColumn<>("По батькові");
		mName.setCellValueFactory(new PropertyValueFactory<>("mName"));
		lName = new TableColumn<>("Прізвище");
		lName.setCellValueFactory(new PropertyValueFactory<>("lName"));
		grade = new TableColumn<>("Розряд");
		grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
		eMail = new TableColumn<Worker, String>("Email");
		eMail.setCellValueFactory(new PropertyValueFactory<>("email"));
		workers.getColumns().add(workersID);
		workers.getColumns().add(lName);
		workers.getColumns().add(fName);
		workers.getColumns().add(mName);
		workers.getColumns().add(grade);
		workers.getColumns().add(eMail);
		workersID.setPrefWidth(50);
		grade.setPrefWidth(50);
		lName.prefWidthProperty().bind(workers.widthProperty().subtract(110).divide(4));
		fName.prefWidthProperty().bind(workers.widthProperty().subtract(110).divide(4));
		mName.prefWidthProperty().bind(workers.widthProperty().subtract(110).divide(4));
		eMail.prefWidthProperty().bind(workers.widthProperty().subtract(110).divide(4));
		machineNo = new TableColumn<Workplace, Integer>("Номер машинки");
		machineNo.setCellValueFactory(new PropertyValueFactory<>("machineNo"));
		equipmentNo = new TableColumn<Workplace, Integer>("ID обладнання");
		equipmentNo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Workplace, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Workplace, Integer> param) {
						return new ReadOnlyObjectWrapper<Integer>(param.getValue().getEquipment_id().getId());
					}
				});
		equipmentDescription = new TableColumn<Workplace, String>("Опис");
		equipmentDescription.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Workplace, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Workplace, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getEquipment_id().getDescription());
					}
				});
		machineNo.setPrefWidth(110);
		equipmentNo.setPrefWidth(110);
		workerWPlacesTableView.getColumns().add(machineNo);
		workerWPlacesTableView.getColumns().add(equipmentNo);
		workerWPlacesTableView.getColumns().add(equipmentDescription);
	}

	@SuppressWarnings("unchecked")
	public void initTreeView() {

		orderIdColumn = new TreeTableColumn<>("Номер замовлення");
		orderDateColumn = new TreeTableColumn<OrdersCustomerProducts, String>("Дата замовлення");
		customerNameColumn = new TreeTableColumn<OrdersCustomerProducts, String>("Замовник");
		orderProductCountColumn = new TreeTableColumn<OrdersCustomerProducts, String>("Кількість");
		orderProductModel = new TreeTableColumn<OrdersCustomerProducts, String>("Модель");
		orderProductName = new TreeTableColumn<OrdersCustomerProducts, String>("Назва продукту");

		orderIdColumn.setCellValueFactory(new TreeItemPropertyValueFactory<OrdersCustomerProducts, String>("orderId"));
		orderDateColumn
				.setCellValueFactory(new TreeItemPropertyValueFactory<OrdersCustomerProducts, String>("orderDate"));
		customerNameColumn
				.setCellValueFactory(new TreeItemPropertyValueFactory<OrdersCustomerProducts, String>("customerName"));
		orderProductCountColumn
				.setCellValueFactory(new TreeItemPropertyValueFactory<OrdersCustomerProducts, String>("count"));
		orderProductModel
				.setCellValueFactory(new TreeItemPropertyValueFactory<OrdersCustomerProducts, String>("model"));
		orderProductName
				.setCellValueFactory(new TreeItemPropertyValueFactory<OrdersCustomerProducts, String>("productName"));
		OrdersTreeView.getColumns().addAll(orderIdColumn, customerNameColumn, orderDateColumn, orderProductCountColumn,
				orderProductModel, orderProductName);
		currentOrdersCheckBox();
	}

	@SuppressWarnings("unchecked")
	private void initOperationsTable() {

		operationIdColumn = new TableColumn<Operation, Integer>("№");
		operationNameColumn = new TableColumn<Operation, String>("Назва");
		operationEquipmentIdColumn = new TableColumn<Operation, String>("Обладнання");
		operationPriceColumn = new TableColumn<Operation, BigDecimal>("Вартість");
		operationTimeColumn = new TableColumn<Operation, BigDecimal>("Час");
		operationGradeColumn = new TableColumn<Operation, Integer>("Розряд");

		operationIdColumn.setCellValueFactory(new PropertyValueFactory<>("operationId"));
		operationNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		operationEquipmentIdColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Operation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Operation, String> param) {
						return param.getValue().getEquipment() == null ? new ReadOnlyObjectWrapper<String>("")
								: new ReadOnlyObjectWrapper<String>(
										new Integer(param.getValue().getEquipment().getId()).toString());
					}
				});
		operationPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		operationTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
		operationGradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
		operationNameColumn.prefWidthProperty()
				.bind(operationsTableView.widthProperty()
						.subtract(operationIdColumn.getWidth() + operationEquipmentIdColumn.getWidth()
								+ operationPriceColumn.getWidth() + operationTimeColumn.getWidth()
								+ operationGradeColumn.getWidth()));

		operationsTableView.getColumns().addAll(operationIdColumn, operationNameColumn, operationEquipmentIdColumn,
				operationPriceColumn, operationTimeColumn, operationGradeColumn);
		listOperations = FXCollections.observableArrayList(OperationDAO.selectAll());
		operationsTableView.setItems(listOperations);
	}

	@SuppressWarnings("unchecked")
	public void initProductTable() {
		productModel = new TableColumn<Product, Integer>("Модель");
		productName = new TableColumn<Product, String>("Назва");
		productModel.setCellValueFactory(new PropertyValueFactory<>("model"));
		productName.setCellValueFactory(new PropertyValueFactory<>("name"));
		productTableView.getColumns().addAll(productModel, productName);
		listProduct = FXCollections.observableArrayList(ProductDAO.selectAll());
		productTableView.setItems(listProduct);
		productTableView.getSelectionModel().select(0);
		productModel.setPrefWidth(70);
		productName.prefWidthProperty().bind(productTableView.widthProperty().subtract(70));
		for (Product p : listProduct) {
			ProductSaver.getId().add(p.getModel());
		}
	}

	@SuppressWarnings("unchecked")
	private void initEquipmentList() {
		equipmentIdColumn = new TableColumn<Equipment, Integer>("ID Обладнання");
		equipmentDescriptionColumn = new TableColumn<Equipment, String>("Опис");
		equipmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		equipmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		equipmentTableView.getColumns().addAll(equipmentIdColumn, equipmentDescriptionColumn);
		listEquipment = FXCollections.observableArrayList(EquipmentDAO.selectAll());
		equipmentTableView.setItems(listEquipment);
	}

	private void initList() {
		list = FXCollections.observableArrayList(WorkerDAO.selectAll());
	}

	private void initCustomersList() {
		listCustomers = FXCollections.observableArrayList(CustomerDAO.selectAll());
	}

	@SuppressWarnings("unchecked")
	private void initCustomers() {
		customerId = new TableColumn<Customer, Integer>("ID");
		customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		customerName = new TableColumn<Customer, String>("Ім'я замовника");
		customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		customerAddress = new TableColumn<Customer, String>("Адреса");
		customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		customerPhone = new TableColumn<Customer, String>("Телефон");
		customerPhone.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Customer, String> arg0) {
						return new ReadOnlyObjectWrapper<String>(new String(arg0.getValue().getPhone()));
					}
				});
		customersTableView.getColumns().addAll(customerId, customerName, customerAddress, customerPhone);
		initCustomersList();
		customersTableView.setItems(listCustomers);
	}

	@SuppressWarnings("unchecked")
	private void intitWorkplaceTable() {
		workplaceIdColumn = new TableColumn<Workplace, Integer>("Номер машинки");
		workplaceEqIdColumn = new TableColumn<Workplace, Integer>("Обладнання");
		workplaceWorkerIdColumn = new TableColumn<Workplace, String>("Код працівника");
		workplaceWorkerFNColumn = new TableColumn<Workplace, String>("Ім'я");
		workplaceWorkerMNColumn = new TableColumn<Workplace, String>("По-батькові");
		workplaceWorkerLNColumn = new TableColumn<Workplace, String>("Прізвище");

		workplaceIdColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Workplace, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Workplace, Integer> param) {

						return param.getValue() == null ? new ReadOnlyObjectWrapper<>(new Integer(0))
								: new ReadOnlyObjectWrapper<>(param.getValue().getMachineNo());
					}
				});
		workplaceEqIdColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Workplace, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Workplace, Integer> param) {
						return param.getValue() == null ? new ReadOnlyObjectWrapper<>(new Integer(0))
								: new ReadOnlyObjectWrapper<>(param.getValue().getEquipment_id().getId());
					}
				});
		workplaceWorkerIdColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Workplace, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Workplace, String> param) {
						return param.getValue().getWorker_id() == null ? new ReadOnlyObjectWrapper<>("")
								: new ReadOnlyObjectWrapper<>(
										new Integer(param.getValue().getWorker_id().getWorker_id()).toString());
					}
				});
		workplaceWorkerFNColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Workplace, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Workplace, String> param) {
						return param.getValue().getWorker_id() == null ? new ReadOnlyObjectWrapper<>("")
								: new ReadOnlyObjectWrapper<>(param.getValue().getWorker_id().getFName());
					}
				});
		workplaceWorkerMNColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Workplace, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Workplace, String> param) {
						return param.getValue().getWorker_id() == null ? new ReadOnlyObjectWrapper<>("")
								: new ReadOnlyObjectWrapper<>(param.getValue().getWorker_id().getMName());
					}
				});
		workplaceWorkerLNColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Workplace, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Workplace, String> param) {
						return param.getValue().getWorker_id() == null ? new ReadOnlyObjectWrapper<>("")
								: new ReadOnlyObjectWrapper<>(param.getValue().getWorker_id().getLName());
					}
				});
		workplacesTableView.getColumns().addAll(workplaceIdColumn, workplaceEqIdColumn, workplaceWorkerIdColumn,
				workplaceWorkerLNColumn, workplaceWorkerFNColumn, workplaceWorkerMNColumn);
		listAllWorkPlaces = FXCollections.observableArrayList(WorkplaceDAO.selectAll());
		workplacesTableView.setItems(listAllWorkPlaces);
	}

	@FXML
	public void workersOnMouseCliked() {
		Worker w = workers.getSelectionModel().getSelectedItem();
		if (w != null) {
			lNameField.setText(w.getLName());
			fNameField.setText(w.getFName());
			mNameField.setText(w.getMName());
			email.setText(w.getEmail());
			gradeChoiseBox.setValue(new Integer(w.getGrade()));
			listWorkPlaces = null;
			listWorkPlaces = FXCollections.observableArrayList(WorkerDAO.getWorkplaces(w));
			workerWPlacesTableView.setItems(listWorkPlaces);
		}
	}

	@FXML
	public void operationsTableViewOnMouseClicked() {
		Operation o = operationsTableView.getSelectionModel().getSelectedItem();
		if (o != null) {
			operationTimeTextField.setText(o.getTime().toString());
			operationPriceTextField.setText(o.getPrice().toString());
			operationNameTextField.setText(o.getName());
			operationGradeChoiseBox.setValue(o.getGrade());
			try {
				operationEquipmentChoiseBox.setValue(EquipmentDAO.getEquipment(o.getEquipment().getId()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@FXML
	public void customerTextAreaOnMouseClicked() {
		Customer c = customersTableView.getSelectionModel().getSelectedItem();
		if (c != null) {
			customerNameTextArea.setText(c.getCustomerName());
			customerAddressTextArea.setText(c.getAddress());
			customerPhoneTextArea.setText(new String(c.getPhone()));
		}
	}

	@FXML
	public void workplaceOnMouseClicked() {
		Workplace wp = workplacesTableView.getSelectionModel().getSelectedItem();
		if (wp != null) {
			workplaceEquipmentChoiceBox.setValue(wp.getEquipment_id());
			workplaceMachineNoTextField.setText(new Integer(wp.getMachineNo()).toString());
			workplaceWorkerChoiceBox.setValue(wp.getWorker_id());
		}
	}

	@FXML
	public void insertButtonAction() {
		try {
			String fName = fNameField.getText().trim();
			String mName = mNameField.getText().trim();
			String lName = lNameField.getText().trim();
			String eMailNew = email.getText().trim();
			int grade = gradeChoiseBox.getValue();
			Pattern pattern = Pattern.compile("^([a-z0-9_-])*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");
			Matcher matcher = pattern.matcher(eMailNew);
			if (fName.equals("") || mName.equals("") || lName.equals("") || !matcher.find())
				throw new NullPointerException();
			Worker w = new Worker(fName, mName, lName, grade, eMailNew);
			for (Worker worker : list) {
				if (worker.getEmail() != null && worker.getEmail().equals(w.getEmail())) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("");
					alert.setHeaderText("Увага!");
					alert.setContentText(
							"Адреса електронної пошти не може повторюватися у різних працівників. Працівника не додано.");
					alert.showAndWait();
					return;
				}
			}
			WorkerDAO.Add(w);
			list.add(w);
			workplaceWorkerChoiceBox.getItems().add(w);
			Sender.sendMesage(w);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("На адресу " + eMailNew + " було відправлено лист з тимчасовим логіном і паролем.");
			alert.showAndWait();
		} catch (NullPointerException ex) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введені дані. Ви заповнили не всі поля. Працівника не додано.");
			alert.showAndWait();
		}
	}

	@FXML
	public void deleteOrder() {
		TreeItem<OrdersCustomerProducts> item = OrdersTreeView.getSelectionModel().getSelectedItem();
		List<Object[]> activeOrders = OrderDAO.SelectActiveOrders();
		for (Object[] o : activeOrders) {
			if (o[0].toString().equals(item.getValue().getOrderId())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("");
				alert.setHeaderText("Увага!");
				alert.setContentText("Ви не можете видалити активне замовлення.");
				alert.showAndWait();
				return;
			}
		}
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle("");
		alert.setContentText(
				"При видаленні замовлення зі списку, дані про виконану роботу будуть видалені. Ви дійсно бажаєте продовжити?");
		ButtonType yes = new ButtonType("Так");
		ButtonType no = new ButtonType("Ні");
		alert.getButtonTypes().add(yes);
		alert.getButtonTypes().add(no);
		alert.showAndWait().ifPresent(response -> {
			if (response == yes) {
				Order o = new Order();
				o.setOrder_id(
						new Integer(OrdersTreeView.getSelectionModel().getSelectedItem().getValue().getOrderId()));
				OrderDAO.Delete(o);
				OrdersTreeView.getRoot().getChildren().remove(OrdersTreeView.getSelectionModel().getSelectedItem());
				OrdersTreeView.refresh();
			}
		});
	}

	@FXML
	public void updateButtonAction() {
		try {
			int index = workers.getSelectionModel().getSelectedIndex();
			String fName = fNameField.getText().trim();
			String mName = mNameField.getText().trim();
			String lName = lNameField.getText().trim();
			String eMailNew = email.getText().trim();
			Pattern pattern = Pattern
					.compile("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");
			Matcher matcher = pattern.matcher(eMailNew);
			if (fName.equals("") || mName.equals("") || lName.equals("") || !matcher.find())
				throw new NullPointerException();
			String oldMail = list.get(index).getEmail();
			list.get(index).setfName(fName);
			list.get(index).setmName(mName);
			list.get(index).setlName(lName);
			list.get(index).setGrade(gradeChoiseBox.getValue());
			if (!oldMail.equals(eMailNew)) {
				for (Worker worker : list) {
					if (worker.getEmail() != null && worker.getEmail().equals(eMailNew)) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("");
						alert.setHeaderText("Увага!");
						alert.setContentText(
								"Адреса електронної пошти не може повторюватися у різних працівників. Дані не оновлено.");
						alert.showAndWait();
						return;
					}
				}
				Alert alert = new Alert(AlertType.NONE);
				alert.setTitle("");
				alert.setContentText(
						"Надіслати повторно лист з іменем користувача і паролем? Обліковий запис з логіном " + oldMail
								+ " буде видалено. Продовжити?");
				ButtonType yes = new ButtonType("Так");
				ButtonType no = new ButtonType("Ні");
				alert.getButtonTypes().add(yes);
				alert.getButtonTypes().add(no);
				alert.showAndWait().ifPresent(response -> {
					if (response == yes) {
						Account oldAcc = new Account();
						oldAcc.setLogin(oldMail);
						AccountDAO.Delete(oldAcc);
						list.get(index).setEmail(eMailNew);
						Sender.sendMesage(list.get(index));
						Alert alert1 = new Alert(AlertType.INFORMATION);
						alert1.setTitle("");
						alert1.setHeaderText("Увага!");
						alert1.setContentText(
								"На адресу " + eMailNew + " було відправлено лист з тимчасовим логіном і паролем.");
						alert1.showAndWait();
						return;
					}
				});
			}
			workers.refresh();

			WorkerDAO.Update(list.get(index));
		} catch (ArrayIndexOutOfBoundsException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Працівника не обрано!");
			alert.showAndWait();
		}
	}

	@FXML
	public void deleteButtonAction() {
		if (workers.getSelectionModel().getSelectedIndex() != -1) {
			Worker w = workers.getSelectionModel().getSelectedItem();
			workplaceWorkerChoiceBox.getItems().removeIf(new Predicate<Worker>() {

				@Override
				public boolean test(Worker t) {
					return t != null && t.getWorker_id() == w.getWorker_id() ? true : false;
				}
			});
			WorkerDAO.Delete(workers.getSelectionModel().getSelectedItem());
			list.remove(workers.getSelectionModel().getSelectedItem());
			workers.refresh();
			listWorkPlaces = FXCollections.observableArrayList(WorkplaceDAO.selectAll());
			workplacesTableView.setItems(listWorkPlaces);
			workplacesTableView.refresh();
		}
	}

	@FXML
	public void WorkersWorkPlaceOnMouseClicked() {
		if (workerWPlacesTableView.getSelectionModel().getSelectedItem() != null) {
			machineNoField
					.setText((new Integer(workerWPlacesTableView.getSelectionModel().getSelectedItem().getMachineNo()))
							.toString());
		}
	}

	@FXML
	public void productsOnMouseClicked() {
		if (productTableView.getSelectionModel().getSelectedItem() != null) {
			productModelTextField
					.setText(new Integer(productTableView.getSelectionModel().getSelectedItem().getModel()).toString());
			productNameTextField.setText(productTableView.getSelectionModel().getSelectedItem().getName());
		}
	}

	@FXML
	public void equipmentListOnMouseClicked() {
		Equipment e = equipmentTableView.getSelectionModel().getSelectedItem();
		if (e != null) {
			equipmentIdTextField.setText((new Integer(e.getId()).toString()));
			equipmentDescriptionTextField.setText(e.getDescription());
		}
	}

	@FXML
	public void WorkPlaceDeleteOnWorker() {
		if (workerWPlacesTableView.getSelectionModel().getSelectedIndex() != -1) {
			WorkplaceDAO.Delete(workerWPlacesTableView.getSelectionModel().getSelectedItem());
			listAllWorkPlaces.remove(workerWPlacesTableView.getSelectionModel().getSelectedItem());
			workerWPlacesTableView.refresh();
		}
	}

	@FXML
	public void insertCustomerButtonAction() {
		String name = customerNameTextArea.getText().trim();
		String address = customerAddressTextArea.getText().trim();
		String phone = customerPhoneTextArea.getText().trim();
		Pattern pattern = Pattern
				.compile("^((\\+?380|0?)(\\([0-9]{4}\\)[0-9]{5}|[0-9]{9}|[0-9]{2}\\s?[0-9]{3}\\s?[0-9]{4}))$");
		Matcher matcher = pattern.matcher(phone);
		if (matcher.find()) {

			if (name.equals("") || address.equals("") || phone.equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("");
				alert.setHeaderText("Увага!");
				alert.setContentText("Будь ласка, перевірте введені дані. Ви заповнили не всі поля. Дані не додано.");
				alert.showAndWait();

			} else {
				Customer c = new Customer(name, phone, address);
				CustomerDAO.Add(c);
				listCustomers.add(c);
				customersTableView.refresh();
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте правильність введення номеру телефону. Дані не додано.");
			alert.showAndWait();
		}
	}

	@FXML
	public void updateCustomerButtonAction() {
		try {
			int index = customersTableView.getSelectionModel().getSelectedIndex();
			String address = customerAddressTextArea.getText().trim();
			String name = customerNameTextArea.getText().trim();
			String phone = customerPhoneTextArea.getText().trim();
			Pattern pattern = Pattern
					.compile("^((\\+?380|0?)(\\([0-9]{4}\\)[0-9]{5}|[0-9]{9}|[0-9]{2}\\s?[0-9]{3}\\s?[0-9]{4}))$");
			Matcher matcher = pattern.matcher(phone);
			if (matcher.find()) {
				if (address.equals("") || name.equals(""))
					throw new NullPointerException();
				listCustomers.get(index).setAddress(address);
				listCustomers.get(index).setCustomerName(name);
				listCustomers.get(index).setPhone(phone);
				CustomerDAO.Update(listCustomers.get(index));
				customersTableView.refresh();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("");
				alert.setHeaderText("Увага!");
				alert.setContentText("Будь ласка, перевірте правильність введення номеру телефону. Дані не додано.");
				alert.showAndWait();
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Замовника не обрано!");
			alert.showAndWait();
		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введені дані. Ви заповнили не всі поля. Замовника не додано.");
			alert.showAndWait();
		}
	}

	@FXML
	public void deleteCustomerButtonAction() {
		if (customersTableView.getSelectionModel().getSelectedIndex() != -1) {
			Alert alert = new Alert(AlertType.NONE);
			alert.setTitle("");
			alert.setContentText(
					"При видаленні замовника зі списку, дані про замовлення, а отже і дані про виконану роботу будуть видалені. Ви дійсно бажаєте продовжити?");
			ButtonType yes = new ButtonType("Так");
			ButtonType no = new ButtonType("Ні");
			alert.getButtonTypes().add(yes);
			alert.getButtonTypes().add(no);
			alert.showAndWait().ifPresent(response -> {
				if (response == yes) {
					CustomerDAO.Delete(customersTableView.getSelectionModel().getSelectedItem());
					listCustomers.remove(customersTableView.getSelectionModel().getSelectedItem());
					customersTableView.refresh();
				}
			});
			currentOrdersCheckBox();
		}
	}

	@FXML
	public void deleteOperationButtonAction() {
		if (operationsTableView.getSelectionModel().getSelectedIndex() != -1) {
			Alert alert = new Alert(AlertType.NONE);
			alert.setTitle("");
			alert.setContentText(
					"При видаленні операції зі списку, дані про виконану роботу будуть видалені. Також ця операція буде видалена з усіх послідовностей. Ви дійсно бажаєте продовжити?");
			ButtonType yes = new ButtonType("Так");
			ButtonType no = new ButtonType("Ні");
			alert.getButtonTypes().add(yes);
			alert.getButtonTypes().add(no);
			alert.showAndWait().ifPresent(response -> {
				if (response == yes) {
					OperationDAO.Delete(operationsTableView.getSelectionModel().getSelectedItem());
					listOperations.remove(operationsTableView.getSelectionModel().getSelectedItem());
					operationsTableView.refresh();
				}
			});

		}
	}

	@FXML
	public void updateOperationButtonAction() {
		int index = operationsTableView.getSelectionModel().getSelectedIndex();
		try {
			listOperations.get(index).setPrice(new BigDecimal(operationPriceTextField.getText().trim()).setScale(4));
			listOperations.get(index).setTime(new BigDecimal(operationTimeTextField.getText().trim()).setScale(4));
			listOperations.get(index).setEquipment(operationEquipmentChoiseBox.getValue());
			listOperations.get(index).setGrade(operationGradeChoiseBox.getValue());
			listOperations.get(index).setName(operationNameTextField.getText().trim());
			OperationDAO.Update(listOperations.get(index));
			operationsTableView.refresh();
		} catch (ArithmeticException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText(
					"Рекомендована точність 4 знаки після коми. Вами було введено більше 4-х знаків після коми. Значення буде округлено до меншого.");
			alert.showAndWait();
			listOperations.get(index)
					.setPrice(new BigDecimal(operationPriceTextField.getText().trim()).setScale(4, RoundingMode.DOWN));
			listOperations.get(index)
					.setTime(new BigDecimal(operationTimeTextField.getText().trim()).setScale(4, RoundingMode.DOWN));

		} catch (NumberFormatException e1) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введені дані. Операцію не оновлено.");
			alert.showAndWait();
		} catch (ArrayIndexOutOfBoundsException ex) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Операцію не обрано!");
			alert.showAndWait();
		}
	}

	@FXML
	public void insertOperationButtonAction() {
		try {
			String opName = operationNameTextField.getText().trim();
			if (opName.equals(""))
				throw new NullPointerException();
			Equipment e = operationEquipmentChoiseBox.getValue();
			Operation o = new Operation(new BigDecimal(operationPriceTextField.getText().trim()),
					new BigDecimal(operationTimeTextField.getText().trim()), opName, operationGradeChoiseBox.getValue(),
					e);
			OperationDAO.Add(o);
			listOperations.add(o);
			operationsTableView.refresh();
		} catch (NumberFormatException e1) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введені дані. Операцію не додано.");
			alert.showAndWait();
		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введені дані. Ви заповнили не всі поля. Операцію не додано.");
			alert.showAndWait();
		}
	}

	@FXML
	public void addEquipment() {
		try {
			Equipment e = new Equipment(new Integer(equipmentIdTextField.getText().trim()),
					equipmentDescriptionTextField.getText().trim());
			for (Equipment eq : listEquipment) {
				if (eq.getId() == e.getId()) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("");
					alert.setHeaderText("Увага!");
					alert.setContentText("Обладнання має мати унікальний ідентифікатор. Обладнання не додано.");
					alert.showAndWait();
					return;
				}
			}
			EquipmentDAO.Add(e);
			listEquipment.add(e);
			operationEquipmentChoiseBox.getItems().add(e);
			workplaceEquipmentChoiceBox.getItems().add(e);
			equipmentTableView.refresh();
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введені дані. Обладнання не додано.");
			alert.showAndWait();
		}
	}

	@FXML
	public void updateEquipment() {
		try {
			int index = equipmentTableView.getSelectionModel().getSelectedIndex();
			listEquipment.get(index).setId(new Integer(equipmentIdTextField.getText().trim()));
			listEquipment.get(index).setDescription(equipmentDescriptionTextField.getText().trim());
			EquipmentDAO.Update(listEquipment.get(index));
			equipmentTableView.refresh();
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введені дані. Дані про обладнання не оновлено.");
			alert.showAndWait();
		} catch (ArrayIndexOutOfBoundsException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Обладнання не обрано!");
			alert.showAndWait();
		}
	}

	@FXML
	public void deleteEquipment() {
		if (equipmentTableView.getSelectionModel().getSelectedIndex() != -1) {
			Alert alert = new Alert(AlertType.NONE);
			alert.setTitle("");
			alert.setContentText(
					"При видаленні обладнання зі списку, дані про робочі місця, і дані про операції будуть видалені. Ви дійсно бажаєте продовжити?");
			ButtonType yes = new ButtonType("Так");
			ButtonType no = new ButtonType("Ні");
			alert.getButtonTypes().add(yes);
			alert.getButtonTypes().add(no);
			alert.showAndWait().ifPresent(response -> {
				if (response == yes) {
					workplaceEquipmentChoiceBox.getItems()
							.remove(equipmentTableView.getSelectionModel().getSelectedItem());
					operationEquipmentChoiseBox.getItems()
							.remove(equipmentTableView.getSelectionModel().getSelectedItem());
					listAllWorkPlaces.removeIf(new Predicate<Workplace>() {

						@Override
						public boolean test(Workplace t) {
							return t.getEquipment_id().equals(equipmentTableView.getSelectionModel().getSelectedItem());
						}
					});
					EquipmentDAO.Delete(equipmentTableView.getSelectionModel().getSelectedItem());
					listEquipment.remove(equipmentTableView.getSelectionModel().getSelectedItem());
					equipmentTableView.refresh();
					listOperations = FXCollections.observableArrayList(OperationDAO.selectAll());
					operationsTableView.setItems(listOperations);
					operationsTableView.refresh();
				}
			});

		}
	}

	@FXML
	public void deleteWorkPlace() {
		if (workplacesTableView.getSelectionModel().getSelectedIndex() != -1) {
			WorkplaceDAO.Delete(workplacesTableView.getSelectionModel().getSelectedItem());
			listAllWorkPlaces.remove(workplacesTableView.getSelectionModel().getSelectedItem());
			workplacesTableView.refresh();
		}
	}

	@FXML
	public void updateWorkPlace() {
		try {
			if (workplacesTableView.getSelectionModel().getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("");
				alert.setHeaderText("Увага!");
				alert.setContentText("Будь ласка, виберіть робоче місце. Дані про робоче місце не оновлено.");
				alert.showAndWait();
				return;
			}
			if (!workplaceEquipmentChoiceBox.getValue()
					.equals(workplacesTableView.getSelectionModel().getSelectedItem().getEquipment_id())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("");
				alert.setHeaderText("Увага!");
				alert.setContentText("Ви не можете змінити тип обладнання робочого місця.");
				alert.showAndWait();
			} else {
				workplacesTableView.getSelectionModel().getSelectedItem()
						.setMachineNo(new Integer(workplaceMachineNoTextField.getText().trim()));
				workplaceEquipmentChoiceBox
						.setValue(workplacesTableView.getSelectionModel().getSelectedItem().getEquipment_id());
				workplacesTableView.getSelectionModel().getSelectedItem()
						.setWorker_id(workplaceWorkerChoiceBox.getValue());
				System.out.println("update");
				WorkplaceDAO.Update(workplacesTableView.getSelectionModel().getSelectedItem());
				workplacesTableView.refresh();
			}
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введені дані. Дані про робоче місце не оновлено.");
			alert.showAndWait();
		}
	}

	@FXML
	public void addWorkPlace() {
		try {
			Integer machineNo = new Integer(workplaceMachineNoTextField.getText().trim());
			for (Workplace w : listAllWorkPlaces) {
				if (w.getMachineNo() == machineNo.intValue()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("");
					alert.setHeaderText("Увага!");
					alert.setContentText("Машинка під таким номером вже існує. Будь ласка, введіть інший номер");
					alert.showAndWait();
					return;
				}
			}
			Workplace wp = new Workplace(workplaceWorkerChoiceBox.getValue(), workplaceEquipmentChoiceBox.getValue(),
					machineNo);
			WorkplaceDAO.Add(wp);
			listAllWorkPlaces.add(wp);
			workplacesTableView.refresh();
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введені дані. Дані про робоче місце не додано.");
			alert.showAndWait();
		}
	}

	@FXML
	public void addProduct() {
		try {
			ProductSaver.setUpdate(false);
			ProductSaver.setProduct(null);
			Stage stage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/product.fxml"));
			Scene scene = new Scene(root, 800, 580);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			ProductController.setRefreshTables(x -> RefreshTable(x));
			stage.setTitle("Новий продукт");
			stage.setScene(scene);
			stage.resizableProperty().set(false);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void updateProduct() {
		try {
			productTableView.getSelectionModel().getSelectedItem()
					.setModel(new Integer(productModelTextField.getText().trim()));
			productTableView.getSelectionModel().getSelectedItem().setName(productNameTextField.getText().trim());
			ProductDAO.Update(productTableView.getSelectionModel().getSelectedItem());
			productTableView.refresh();
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText(
					"Будь ласка, перевірте введені дані. Дані про продукцію не оновлено. Значення моделі має бути унікальним, і містити лише цифри.");
			alert.showAndWait();
		} catch (Exception e1) {

		}
	}

	@FXML
	public void deleteProduct() {
		if (productTableView.getSelectionModel().getSelectedIndex() != -1) {
			Alert alert = new Alert(AlertType.NONE);
			alert.setTitle("");
			alert.setContentText(
					"При видаленні продуку зі списку, дані про всі замовлення, які містять цей продукт, і дані про виконану роботу будуть видалені. Ви дійсно бажаєте продовжити?");
			ButtonType yes = new ButtonType("Так");
			ButtonType no = new ButtonType("Ні");
			alert.getButtonTypes().add(yes);
			alert.getButtonTypes().add(no);
			alert.showAndWait().ifPresent(response -> {
				if (response == yes) {
					ProductSaver.getId().remove(productTableView.getSelectionModel().getSelectedItem().getModel());
					ProductDAO.Delete(productTableView.getSelectionModel().getSelectedItem());
					listProduct.remove(productTableView.getSelectionModel().getSelectedItem());
					productTableView.refresh();
				}
			});

		}
	}

	@FXML
	public void productAddOperationSequence() {
		try {
			ProductSaver.setProduct(productTableView.getSelectionModel().getSelectedItem());
			ProductSaver.setUpdate(true);
			Stage stage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/product.fxml"));
			Scene scene = new Scene(root, 800, 580);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			ProductController.setRefreshTables(x -> RefreshTable(x));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Додавання операцій");
			stage.setScene(scene);
			stage.resizableProperty().set(false);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void createOrder() {
		try {
			Stage stage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Order.fxml"));
			Scene scene = new Scene(root, 800, 580);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			OrderController.setRefreshTables(x -> RefreshTables(x));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Нове замовлення");
			stage.setScene(scene);
			stage.resizableProperty().set(false);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void currentOrdersCheckBox() {
		TreeItem<OrdersCustomerProducts> root = new TreeItem<OrdersCustomerProducts>();
		List<Object[]> orders;
		if (currentOrdersCheckBox.isSelected()) {
			orders = OrderDAO.SelectActiveOrders();
		} else {
			orders = OrderDAO.SelectOrderInfo();
		}
		for (Object[] order : orders) {
			TreeItem<OrdersCustomerProducts> rootChild = new TreeItem<OrdersCustomerProducts>(
					new OrdersCustomerProducts(order[0].toString(), order[1].toString(), order[2].toString(), "", "",
							""));
			List<Object[]> products = OrderProductsDAO.SelectOrderProductInfo(new Integer(order[0].toString()));
			for (Object[] product : products) {
				TreeItem<OrdersCustomerProducts> child = new TreeItem<OrdersCustomerProducts>(
						new OrdersCustomerProducts("", "", "", product[0].toString(), product[1].toString(),
								product[2].toString()));
				rootChild.getChildren().add(child);
			}
			root.getChildren().add(rootChild);
		}
		OrdersTreeView.setRoot(root);
		OrdersTreeView.setShowRoot(false);
	}

	public boolean RefreshTables(Order o) {
		currentOrdersCheckBox();
		return false;
	}

	public boolean RefreshTable(Product p) {
		if (p != null)
			listProduct.add(p);
		productTableView.refresh();
		return false;
	}

	@FXML
	public void showDataOnMouseClicked() {
		if (OrdersTreeView.getSelectionModel().getSelectedItem() != null
				&& OrdersTreeView.getSelectionModel().getSelectedItem().getValue().getOrderId().equals("")) {
			showDoneWorkButton.setDisable(false);
			deleteOrderButton.setDisable(true);
		} else {
			showDoneWorkButton.setDisable(true);
			deleteOrderButton.setDisable(false);
		}
	}

	@FXML
	public void showDoneWork() {
		try {
			for (Operation o : listOperations) {
				if (o.getEquipment() == null) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("");
					alert.setHeaderText("Увага!");
					alert.setContentText(
							"Будь ласка, заповніть повністю таблицю \"Операції\". Без типу обладнання неможливий розподіл операцій");
					alert.showAndWait();
					return;
				}
			}
			TreeItem<OrdersCustomerProducts> item = OrdersTreeView.getSelectionModel().getSelectedItem();
			DoneWorkController.setOrder_id(item.getParent().getValue().getOrderId());
			DoneWorkController.setModel(item.getValue().getModel());
			Stage stage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Done_work.fxml"));
			Scene scene = new Scene(root, 800, 580);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			stage.setTitle("Замовлення " + item.getParent().getValue().getOrderId() + " , модель "
					+ item.getValue().getModel());
			stage.initModality(Modality.APPLICATION_MODAL);
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
			e.printStackTrace();
		}
	}
}
