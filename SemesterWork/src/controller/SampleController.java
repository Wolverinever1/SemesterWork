package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;

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
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Equipment;
import model.Worker;
import model.Workplace;
import model.dao.EquipmentDAO;
import model.dao.OrderDAO;
import model.dao.OrderProductsDAO;
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
	TreeTableColumn<OrdersCustomerProducts, String> orderIdColumn;
	@FXML
	TreeTableColumn<OrdersCustomerProducts,String> orderDateColumn;
	@FXML
	TreeTableColumn<OrdersCustomerProducts,String> customerNameColumn;
	@FXML
	TreeTableColumn<OrdersCustomerProducts,String> orderProductCountColumn;
	@FXML
	TreeTableColumn<OrdersCustomerProducts, String> orderProductModel;
	@FXML
	TreeTableColumn<OrdersCustomerProducts, String> orderProductName;
	
	
	@FXML
	TreeTableView<OrdersCustomerProducts> OrdersTreeView;
	
	@FXML
	ChoiceBox<Integer> gradeChoiseBox;
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

	private static ObservableList<Worker> list;
	private static ObservableList<Workplace> listWorkPlaces;

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
		
		orderIdColumn=new TreeTableColumn<>("Номер замовлення");
		orderDateColumn= new TreeTableColumn<OrdersCustomerProducts, String>("Дата замовлення");
		customerNameColumn = new TreeTableColumn<OrdersCustomerProducts, String>("Замовник");
		orderProductCountColumn = new TreeTableColumn<OrdersCustomerProducts, String>("Кількість");
		orderProductModel = new TreeTableColumn<OrdersCustomerProducts, String>("Модель");
		orderProductName = new TreeTableColumn<OrdersCustomerProducts, String>("Назва продукту");
		
		orderIdColumn.setCellValueFactory(new TreeItemPropertyValueFactory<OrdersCustomerProducts, String>("orderId"));
		orderDateColumn.setCellValueFactory(new TreeItemPropertyValueFactory<OrdersCustomerProducts, String>("orderDate"));
		customerNameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<OrdersCustomerProducts, String>("customerName"));
		orderProductCountColumn.setCellValueFactory(new TreeItemPropertyValueFactory<OrdersCustomerProducts,String>("count"));
		orderProductModel.setCellValueFactory(new TreeItemPropertyValueFactory<OrdersCustomerProducts,String>("model"));
		orderProductName.setCellValueFactory(new TreeItemPropertyValueFactory<OrdersCustomerProducts, String>("productName"));
		OrdersTreeView.getColumns().addAll(orderIdColumn,customerNameColumn, orderDateColumn, orderProductCountColumn,orderProductModel, orderProductName);
		
		TreeItem<OrdersCustomerProducts> root = new TreeItem<OrdersCustomerProducts>();
		List<Object[]> orders = OrderDAO.SelectOrderInfo();
		for(Object[] order : orders) {
			TreeItem<OrdersCustomerProducts> rootChild = new TreeItem<OrdersCustomerProducts>(new OrdersCustomerProducts(order[0].toString(),order[1].toString(),order[2].toString(),"","",""));
			List<Object[]> products = OrderProductsDAO.SelectOrderProductInfo(new Integer(order[0].toString()));
			for(Object[] product : products) {
				TreeItem<OrdersCustomerProducts> child = new TreeItem<OrdersCustomerProducts>(new OrdersCustomerProducts("","","",product[0].toString(),product[1].toString(),product[2].toString()));
				rootChild.getChildren().add(child);
			}
			root.getChildren().add(rootChild);
		}
		OrdersTreeView.setRoot(root);
		OrdersTreeView.setShowRoot(false);
		
	}

	@SuppressWarnings("unchecked")
	private void initList() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Worker> workers = session.createQuery("from Worker").list();
		list = FXCollections.observableArrayList(workers);
		session.getTransaction().commit();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gradeChoiseBox.getItems().addAll(1, 2, 3, 4, 5, 6);
		List<Integer> list = EquipmentDAO.getEquipmentId();
//		eqIdComboBox.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {
//			
//			@Override
//			public ListCell<Integer> call(ListView<Integer> arg0) {
//				return new ListCell<>();
//			}
//		});
//		eqIdComboBox = new ComboBox<>();
		eqIdComboBox.setItems(FXCollections.observableArrayList(list));
		eqIdComboBox.setButtonCell(new ListCell<>());
//		eqIdComboBox.getItems().addAll(list);
		eqIdComboBox.setConverter(new StringConverter<Integer>() {

			@Override
			public String toString(Integer object) {
				if(object!=null)
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
	}

	@FXML
	public void workersOnMouseCliked() {
		Worker w = workers.getSelectionModel().getSelectedItem();
		lNameField.setText(w.getLName());
		fNameField.setText(w.getFName());
		mNameField.setText(w.getMName());
//		gradeChoiseBox.getSelectionModel().select(new Integer(w.getGrade()));
		gradeChoiseBox.setValue(new Integer(w.getGrade()));
		System.out.println(w.getGrade());
		listWorkPlaces = null;
//		listWorkPlaces = FXCollections.observableArrayList(new LinkedList(w.getWorkplaces()));
		listWorkPlaces = FXCollections.observableArrayList(WorkerDAO.getWorkplaces(w));
		workerWPlacesTableView.setItems(listWorkPlaces);
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
		machineNoField.setText((new Integer(workerWPlacesTableView.getSelectionModel().getSelectedItem().getMachineNo())).toString());
		eqIdComboBox.setValue(new Integer( workerWPlacesTableView.getSelectionModel().getSelectedItem().getEquipment_id().getId()));
	}

	public void AddWorkerOperationAction() {

	}
	
	@FXML
	public void WorkPlaceDeleteOnWorker(){
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
			Equipment e = EquipmentDAO.getEquipment( eqIdComboBox.getSelectionModel().getSelectedItem());
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
}
