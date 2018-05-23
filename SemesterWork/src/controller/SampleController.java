package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.hibernate.Session;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
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
import model.Customer;
import model.Equipment;
import model.Operation;
import model.Order;
import model.OrdersCustomerProducts;
import model.Product;
import model.ProductSaver;
import model.Worker;
import model.Workplace;
import model.dao.CustomerDAO;
import model.dao.EquipmentDAO;
import model.dao.OperationDAO;
import model.dao.OrderDAO;
import model.dao.OrderProductsDAO;
import model.dao.ProductDAO;
import model.dao.WorkerDAO;
import model.dao.WorkplaceDAO;
import model.resources.HibernateUtil;

public class SampleController implements Initializable {
	@FXML
	TabPane tabs;
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
	TableColumn<Operation, Integer> operationEquipmentIdColumn;
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
	TableColumn<Workplace, Integer> workplaceWorkerIdColumn;
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
	ChoiceBox<Integer> operationEquipmentChoiseBox;
	@FXML
	ChoiceBox<Equipment> workplaceEquipmentChoiceBox;
	@FXML
	ChoiceBox<Worker> workplaceWorkerChoiceBox;

	@FXML
	ComboBox<Integer> eqIdComboBox;

	@FXML
	CheckBox currentOrdersCheckBox;

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
	TextArea customerAddressTextArea;
	@FXML
	TextArea customerNameTextArea;

	private ObservableList<Worker> list;
	private ObservableList<Workplace> listWorkPlaces;
	private ObservableList<Customer> listCustomers;
	private ObservableList<Operation> listOperations;
	private ObservableList<Equipment> listEquipment;
	private ObservableList<Product> listProduct;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// ProductOperationsDAO.selectAll();
		// OrderProductsDAO.selectAll();

		// dw.get(0).getWorker_id().getGrade();
		gradeChoiseBox.getItems().addAll(1, 2, 3, 4, 5, 6);
		operationGradeChoiseBox.getItems().addAll(1, 2, 3, 4, 5, 6);
		workplaceEquipmentChoiceBox.getItems().addAll(EquipmentDAO.selectAll());
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
				return object.getWorker_id() + " - " + object.getLName() + " " + object.getFName() + " "
						+ object.getMName();
			}

			@Override
			public Worker fromString(String string) {
				Scanner s = new Scanner(string);
				Worker e = WorkerDAO.getWorker(s.nextInt());
				s.close();
				return e;
			}
		});
		List<Integer> list = EquipmentDAO.getEquipmentId();
		// eqIdComboBox.setCellFactory(new Callback<ListView<Integer>,
		// ListCell<Integer>>() {
		//
		// @Override
		// public ListCell<Integer> call(ListView<Integer> arg0) {
		// return new ListCell<>();
		// }
		// });
		// eqIdComboBox = new ComboBox<>();
		operationEquipmentChoiseBox.setItems(FXCollections.observableArrayList(list));
		eqIdComboBox.setItems(FXCollections.observableArrayList(list));
		eqIdComboBox.setButtonCell(new ListCell<>());
		// eqIdComboBox.getItems().addAll(list);
		eqIdComboBox.setConverter(new StringConverter<Integer>() {

			@Override
			public String toString(Integer object) {
				if (object != null)
					return object.toString();
				else
					return "";
			}

			@Override
			public Integer fromString(String string) {
				return Integer.parseInt(string);
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
		eqIdComboBox.arm();
		listWorkPlaces = FXCollections.observableArrayList();
		workerWPlacesTableView.setItems(listWorkPlaces);
		workers.setItems(list);
		workersID = new TableColumn<>("Worker ID");
		workersID.setCellValueFactory(new PropertyValueFactory<>("worker_id"));
		fName = new TableColumn<>("First name");
		fName.setCellValueFactory(new PropertyValueFactory<>("fName"));
		mName = new TableColumn<>("Middle name");
		mName.setCellValueFactory(new PropertyValueFactory<>("mName"));
		lName = new TableColumn<>("Last name");
		lName.setCellValueFactory(new PropertyValueFactory<>("lName"));
		grade = new TableColumn<>("Grade");
		grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
		workers.getColumns().add(workersID);
		workers.getColumns().add(lName);
		workers.getColumns().add(mName);
		workers.getColumns().add(fName);
		workers.getColumns().add(grade);
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
		operationEquipmentIdColumn = new TableColumn<Operation, Integer>("Обладнання");
		operationPriceColumn = new TableColumn<Operation, BigDecimal>("Вартість");
		operationTimeColumn = new TableColumn<Operation, BigDecimal>("Час");
		operationGradeColumn = new TableColumn<Operation, Integer>("Розряд");

		operationIdColumn.setCellValueFactory(new PropertyValueFactory<>("operationId"));
		operationNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		operationEquipmentIdColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Operation, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Operation, Integer> param) {
						return new ReadOnlyObjectWrapper<Integer>(param.getValue().getEquipment().getId());
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
		workplaceWorkerIdColumn = new TableColumn<Workplace, Integer>("Код працівника");
		workplaceWorkerFNColumn = new TableColumn<Workplace, String>("Ім'я");
		workplaceWorkerMNColumn = new TableColumn<Workplace, String>("По-батькові");
		workplaceWorkerLNColumn = new TableColumn<Workplace, String>("Прізвище");

		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		workplaceIdColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Workplace, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Workplace, Integer> param) {
						
						return new ReadOnlyObjectWrapper<>(param.getValue().getMachineNo());
					}
				});
		workplaceEqIdColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Workplace, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Workplace, Integer> param) {
						return new ReadOnlyObjectWrapper<>(param.getValue().getEquipment_id().getId());
					}
				});
		workplaceWorkerIdColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Workplace, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Workplace, Integer> param) {
						return new ReadOnlyObjectWrapper<>(param.getValue().getWorker_id().getWorker_id());
					}
				});
		workplaceWorkerFNColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Workplace, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Workplace, String> param) {
						return new ReadOnlyObjectWrapper<>(param.getValue().getWorker_id().getFName());
					}
				});
		workplaceWorkerMNColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Workplace, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Workplace, String> param) {
						return new ReadOnlyObjectWrapper<>(param.getValue().getWorker_id().getMName());
					}
				});
		workplaceWorkerLNColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Workplace, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Workplace, String> param) {
						return new ReadOnlyObjectWrapper<>(param.getValue().getWorker_id().getLName());
					}
				});
		workplacesTableView.getColumns().addAll(workplaceIdColumn, workplaceEqIdColumn, workplaceWorkerIdColumn,
				workplaceWorkerLNColumn, workplaceWorkerFNColumn, workplaceWorkerMNColumn);
		listWorkPlaces = FXCollections.observableArrayList(WorkplaceDAO.selectAll());
		workplacesTableView.setItems(listWorkPlaces);
		s.close();
	}

	@FXML
	public void workersOnMouseCliked() {
		Worker w = workers.getSelectionModel().getSelectedItem();
		lNameField.setText(w.getLName());
		fNameField.setText(w.getFName());
		mNameField.setText(w.getMName());
		gradeChoiseBox.setValue(new Integer(w.getGrade()));
		System.out.println(w.getGrade());
		listWorkPlaces = null;
		listWorkPlaces = FXCollections.observableArrayList(WorkerDAO.getWorkplaces(w));
		workerWPlacesTableView.setItems(listWorkPlaces);
	}

	@FXML
	public void operationsTableViewOnMouseClicked() {
		Operation o = operationsTableView.getSelectionModel().getSelectedItem();
		operationTimeTextField.setText(o.getTime().toString());
		operationPriceTextField.setText(o.getPrice().toString());
		operationNameTextField.setText(o.getName());
		operationGradeChoiseBox.setValue(o.getGrade());
		operationEquipmentChoiseBox.setValue(o.getEquipment().getId());
	}

	@FXML
	public void customerTextAreaOnMouseClicked() {
		Customer c = customersTableView.getSelectionModel().getSelectedItem();
		customerNameTextArea.setText(c.getCustomerName());
		customerAddressTextArea.setText(c.getAddress());
		customerPhoneTextArea.setText(new String(c.getPhone()));
	}

	@FXML
	public void workplaceOnMouseClicked() {
		Workplace wp = workplacesTableView.getSelectionModel().getSelectedItem();
		workplaceEquipmentChoiceBox.setValue(wp.getEquipment_id());
		workplaceMachineNoTextField.setText(new Integer(wp.getMachineNo()).toString());
		workplaceWorkerChoiceBox.setValue(wp.getWorker_id());
	}

	@FXML
	public void insertButtonAction() {
		try {
			String fName = fNameField.getText().trim();
			String mName = mNameField.getText().trim();
			String lName = lNameField.getText().trim();
			if (fName.equals("") || mName.equals("") || lName.equals(""))
				throw new NullPointerException();
			int grade = gradeChoiseBox.getValue();
			Worker w = new Worker(fName, mName, lName, grade);
			WorkerDAO.Add(w);
			list.add(w);
		} catch (NullPointerException ex) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введені дані. Ви заповнили не всі поля. Працівника не додано.");
			alert.showAndWait();
		}
	}

	@FXML
	public void updateButtonAction() {
		try {
			int index = workers.getSelectionModel().getSelectedIndex();
			String fName = fNameField.getText().trim();
			String mName = mNameField.getText().trim();
			String lName = lNameField.getText().trim();
			if (fName.equals("") || mName.equals("") || lName.equals(""))
				throw new NullPointerException();
			list.get(index).setfName(fName);
			list.get(index).setmName(mName);
			list.get(index).setlName(lName);
			list.get(index).setGrade(gradeChoiseBox.getValue());
			WorkerDAO.Update(list.get(index));
			workers.refresh();
		} catch (ArrayIndexOutOfBoundsException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Працівника не обрано!");
			alert.showAndWait();////////////////
		} catch (NullPointerException ex) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введені дані. Ви заповнили не всі поля. Дані не оновлено.");///////////////////////////////
			alert.showAndWait();
		}
	}

	@FXML
	public void deleteButtonAction() {
		if (workers.getSelectionModel().getSelectedIndex() != -1) {
			WorkerDAO.Delete(workers.getSelectionModel().getSelectedItem());
			list.remove(workers.getSelectionModel().getSelectedItem());
			workers.refresh();
		}
	}

	@FXML
	public void WorkersWorkPlaceOnMouseClicked() {
		machineNoField.setText(
				(new Integer(workerWPlacesTableView.getSelectionModel().getSelectedItem().getMachineNo())).toString());
		eqIdComboBox.setValue(
				new Integer(workerWPlacesTableView.getSelectionModel().getSelectedItem().getEquipment_id().getId()));
	}

	@FXML
	public void productsOnMouseClicked() {
		productModelTextField
				.setText(new Integer(productTableView.getSelectionModel().getSelectedItem().getModel()).toString());
		productNameTextField.setText(productTableView.getSelectionModel().getSelectedItem().getName());
	}

	// public void AddWorkerOperationAction() {
	//
	// }

	@FXML
	public void equipmentListOnMouseClicked() {
		Equipment e = equipmentTableView.getSelectionModel().getSelectedItem();
		equipmentIdTextField.setText((new Integer(e.getId()).toString()));
		equipmentDescriptionTextField.setText(e.getDescription());
	}

	@FXML
	public void WorkPlaceDeleteOnWorker() {
		if (workerWPlacesTableView.getSelectionModel().getSelectedIndex() != -1) {
			WorkplaceDAO.Delete(workerWPlacesTableView.getSelectionModel().getSelectedItem());
			listWorkPlaces.remove(workerWPlacesTableView.getSelectionModel().getSelectedItem());
			workerWPlacesTableView.refresh();
		}
	}

	@FXML
	public void equipmentIdComboBoxKeyPressed() {
		// FilteredList<Integer> list = equipmentIdComboBox.getItems().filtered(new
		// Predicate<Integer>() {
		//
		// @Override
		// public boolean test(Integer t) {
		// System.out.println("heere");
		// if(t.toString().contains(equipmentIdComboBox.getAccessibleText()))
		// return true;
		// return false;
		// }
		// });
		// equipmentIdComboBox.setItems(list);
	}

	@FXML
	public void AddWorkerWorkplace() {
		Workplace wp = new Workplace();
		try {
			Equipment e = EquipmentDAO.getEquipment(eqIdComboBox.getSelectionModel().getSelectedItem());
			wp.setEquipment_id(e);
			wp.setWorker_id(workers.getSelectionModel().getSelectedItem());
			int machineNo = Integer.parseInt(machineNoField.getText().trim());
			wp.setMachineNo(new Integer(machineNo));
			WorkplaceDAO.Add(wp);
			listWorkPlaces.add(wp);
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Перевірте введені данні");
			alert.showAndWait();
		}
	}

	@FXML
	public void insertCustomerButtonAction() {
		String name = customerNameTextArea.getText().trim();
		String address = customerAddressTextArea.getText().trim();
		// CHECK PHONE NUMBER
		if (name.equals("") || address.equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введені дані. Ви заповнили не всі поля. Дані не додано.");
			alert.showAndWait();
		} else {
			Customer c = new Customer(name, customerPhoneTextArea.getText().trim().toCharArray(), address);
			CustomerDAO.Add(c);
			listCustomers.add(c);
			customersTableView.refresh();
		}
	}

	@FXML
	public void updateCustomerButtonAction() {
		try {
			int index = customersTableView.getSelectionModel().getSelectedIndex();
			String address = customerAddressTextArea.getText().trim();
			String name = customerNameTextArea.getText().trim();
			// CHECK PHONE
			if (address.equals("") || name.equals(""))
				throw new NullPointerException();
			listCustomers.get(index).setAddress(address);
			listCustomers.get(index).setCustomerName(name);
			listCustomers.get(index).setPhone(customerPhoneTextArea.getText().trim().toCharArray());
			CustomerDAO.Update(listCustomers.get(index));
			customersTableView.refresh();
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
			OperationDAO.Delete(operationsTableView.getSelectionModel().getSelectedItem());
			listOperations.remove(operationsTableView.getSelectionModel().getSelectedItem());
			operationsTableView.refresh();
		}
	}

	@FXML
	public void updateOperationButtonAction() {
		int index = operationsTableView.getSelectionModel().getSelectedIndex();
		try {
			listOperations.get(index).setPrice(new BigDecimal(operationPriceTextField.getText().trim()).setScale(4));
			listOperations.get(index).setTime(new BigDecimal(operationTimeTextField.getText().trim()).setScale(4));
			listOperations.get(index).setEquipment(EquipmentDAO.getEquipment(operationEquipmentChoiseBox.getValue()));
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
			Equipment e = EquipmentDAO.getEquipment(operationEquipmentChoiseBox.getValue());
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
			EquipmentDAO.Add(e);
			listEquipment.add(e);
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
		}catch (ArrayIndexOutOfBoundsException e) {
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
			EquipmentDAO.Delete(equipmentTableView.getSelectionModel().getSelectedItem());
			listEquipment.remove(equipmentTableView.getSelectionModel().getSelectedItem());
			equipmentTableView.refresh();
		}
	}

	@FXML
	public void deleteWorkPlace() {
		if (workplacesTableView.getSelectionModel().getSelectedIndex() != -1) {
			WorkplaceDAO.Delete(workplacesTableView.getSelectionModel().getSelectedItem());
			listWorkPlaces.remove(workplacesTableView.getSelectionModel().getSelectedItem());
			workplacesTableView.refresh();
		}
	}

	@FXML
	public void updateWorkPlace() {
		try {
			workplacesTableView.getSelectionModel().getSelectedItem()
					.setMachineNo(new Integer(workplaceMachineNoTextField.getText().trim()));
			workplacesTableView.getSelectionModel().getSelectedItem()
					.setEquipment_id(workplaceEquipmentChoiceBox.getValue());
			workplacesTableView.getSelectionModel().getSelectedItem().setWorker_id(workplaceWorkerChoiceBox.getValue());
			System.out.println("update");
			WorkplaceDAO.Update(workplacesTableView.getSelectionModel().getSelectedItem());
			workplacesTableView.refresh();
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
			for (Workplace w : listWorkPlaces) {
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
			listWorkPlaces.add(wp);
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
			Stage stage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/product.fxml"));
			Scene scene = new Scene(root, 800, 580);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			ProductController.setRefreshTables(x -> RefreshTable(x));
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
			ProductDAO.Delete(productTableView.getSelectionModel().getSelectedItem());
			listProduct.remove(productTableView.getSelectionModel().getSelectedItem());
			productTableView.refresh();
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
		listProduct.add(p);
		productTableView.refresh();
		return false;
	}

	// @FXML
	// public void deleteOrder(){
	// OrderDAO.DeleteWhere(OrdersTreeView.getSelectionModel().getSelectedItem().getValue().getOrderId());
	// OrdersTreeView.getTreeItem(OrdersTreeView.getSelectionModel().getSelectedIndex());
	// OrdersTreeView.getRoot().getChildren().remove(OrdersTreeView.getSelectionModel().getSelectedItem());
	// OrdersTreeView.refresh();
	// }

	@FXML
	public void updateDoneWork() {
		try {
			Stage stage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Done_work.fxml"));
			Scene scene = new Scene(root, 800, 580);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			TreeItem<OrdersCustomerProducts> item = OrdersTreeView.getSelectionModel().getSelectedItem();
			DoneWorkController.setOrder_id(item.getParent().getValue().getOrderId());
			DoneWorkController.setModel(item.getValue().getModel());
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.resizableProperty().set(false);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
