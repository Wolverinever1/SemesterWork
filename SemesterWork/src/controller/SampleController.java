package controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Customer;
import model.Equipment;
import model.Operation;
import model.Worker;
import model.Workplace;
import model.dao.CustomerDAO;
import model.dao.EquipmentDAO;
import model.dao.OperationDAO;
import model.dao.OrderDAO;
import model.dao.OrderProductsDAO;
import model.dao.WorkerDAO;
import model.dao.WorkplaceDAO;

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
	ComboBox<Integer> eqIdComboBox;

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
	TextArea customerAddressTextArea;
	@FXML
	TextArea customerNameTextArea;

	private static ObservableList<Worker> list;
	private static ObservableList<Workplace> listWorkPlaces;
	private static ObservableList<Customer> listCustomers;
	private static ObservableList<Operation> listOperations;
	private static ObservableList<Equipment> listEquipment;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gradeChoiseBox.getItems().addAll(1, 2, 3, 4, 5, 6);
		operationGradeChoiseBox.getItems().addAll(1, 2, 3, 4, 5, 6);

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

		TreeItem<OrdersCustomerProducts> root = new TreeItem<OrdersCustomerProducts>();
		List<Object[]> orders = OrderDAO.SelectOrderInfo();
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

		operationsTableView.getColumns().addAll(operationIdColumn, operationNameColumn, operationEquipmentIdColumn,
				operationPriceColumn, operationTimeColumn, operationGradeColumn);
		listOperations = FXCollections.observableArrayList(OperationDAO.selectAll());
		operationsTableView.setItems(listOperations);
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
	public void insertButtonAction() {
		String fName = fNameField.getText();
		String mName = mNameField.getText();
		String lName = lNameField.getText();
		int grade = gradeChoiseBox.getValue();
		Worker w = new Worker(fName, mName, lName, grade);
		WorkerDAO.Add(w);
		list.add(w);
	}

	@FXML
	public void updateButtonAction() {
		int index = workers.getSelectionModel().getSelectedIndex();
		list.get(index).setfName(fNameField.getText());
		list.get(index).setmName(mNameField.getText());
		list.get(index).setlName(lNameField.getText());
		list.get(index).setGrade(gradeChoiseBox.getValue());
		WorkerDAO.Update(list.get(index));
		workers.refresh();
	}

	@FXML
	public void deleteButtonAction() {
		WorkerDAO.Delete(workers.getSelectionModel().getSelectedItem());
		list.remove(workers.getSelectionModel().getSelectedItem());
		workers.refresh();
	}

	@FXML
	public void WorkersWorkPlaceOnMouseClicked() {
		machineNoField.setText(
				(new Integer(workerWPlacesTableView.getSelectionModel().getSelectedItem().getMachineNo())).toString());
		eqIdComboBox.setValue(
				new Integer(workerWPlacesTableView.getSelectionModel().getSelectedItem().getEquipment_id().getId()));
	}

	// public void AddWorkerOperationAction() {
	//
	// }

	@FXML
	public void WorkPlaceDeleteOnWorker() {
		WorkplaceDAO.Delete(workerWPlacesTableView.getSelectionModel().getSelectedItem());
		listWorkPlaces.remove(workerWPlacesTableView.getSelectionModel().getSelectedItem());
		workerWPlacesTableView.refresh();
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
			int machineNo = Integer.parseInt(machineNoField.getText());
			wp.setMachineNo(new Integer(machineNo));
			WorkplaceDAO.Add(wp);
			listWorkPlaces.add(wp);
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Перевірте введені данні");

		}
	}

	@FXML
	public void insertCustomerButtonAction() {
		Customer c = new Customer(0, customerNameTextArea.getText(), customerPhoneTextArea.getText().toCharArray(),
				customerAddressTextArea.getText());
		CustomerDAO.Add(c);
		listCustomers.add(c);
		customersTableView.refresh();
	}

	@FXML
	public void updateCustomerButtonAction() {
		int index = customersTableView.getSelectionModel().getSelectedIndex();
		listCustomers.get(index).setAddress(customerAddressTextArea.getText());
		listCustomers.get(index).setCustomerName(customerNameTextArea.getText());
		listCustomers.get(index).setPhone(customerPhoneTextArea.getText().toCharArray());
		CustomerDAO.Update(listCustomers.get(index));
		customersTableView.refresh();
	}

	@FXML
	public void deleteCustomerButtonAction() {
		CustomerDAO.Delete(customersTableView.getSelectionModel().getSelectedItem());
		listCustomers.remove(customersTableView.getSelectionModel().getSelectedItem());
		customersTableView.refresh();
	}

	@FXML
	public void deleteOperationButtonAction() {
		OperationDAO.Delete(operationsTableView.getSelectionModel().getSelectedItem());
		listOperations.remove(operationsTableView.getSelectionModel().getSelectedItem());
		operationsTableView.refresh();
	}

	@FXML
	public void updateOperationButtonAction() {
		int index = operationsTableView.getSelectionModel().getSelectedIndex();
		try {
			listOperations.get(index).setPrice(new BigDecimal(operationPriceTextField.getText()).setScale(4));
			listOperations.get(index).setTime(new BigDecimal(operationTimeTextField.getText()).setScale(4));
			listOperations.get(index).setEquipment(EquipmentDAO.getEquipment(operationEquipmentChoiseBox.getValue()));
			listOperations.get(index).setGrade(operationGradeChoiseBox.getValue());
			listOperations.get(index).setName(operationNameTextField.getText());
		} catch (ArithmeticException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText(
					"Рекомендована точність 4 знаки після коми. Вами було введено більше 4-х знаків після коми. Значення буде округлено до меншого.");
			alert.showAndWait();
			listOperations.get(index)
					.setPrice(new BigDecimal(operationPriceTextField.getText()).setScale(4, RoundingMode.DOWN));
			listOperations.get(index)
					.setTime(new BigDecimal(operationTimeTextField.getText()).setScale(4, RoundingMode.DOWN));

		} catch (NumberFormatException e1) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введені дані.");
			alert.showAndWait();
			return;
		}
		OperationDAO.Update(listOperations.get(index));
		operationsTableView.refresh();
	}

	@FXML
	public void insertOperationButtonAction() {
		Equipment e = EquipmentDAO.getEquipment(operationEquipmentChoiseBox.getValue());
		Operation o = new Operation(0, new BigDecimal(operationPriceTextField.getText()),
				new BigDecimal(operationTimeTextField.getText()), operationNameTextField.getText(),
				operationGradeChoiseBox.getValue(), e);
		OperationDAO.Add(o);
	}
}
