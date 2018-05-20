package controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import model.Operation;
import model.Pr_op_sequence;
import model.Product;
import model.ProductSaver;
import model.dao.OperationDAO;
import model.dao.ProductDAO;
import model.dao.ProductOperationsDAO;

public class ProductController implements Initializable {

	@FXML
	TableView<Operation> allOperationsTable;
	@FXML
	TableView<Operation> selectedOperationsTable;
	@FXML
	TableColumn<Operation, String> operationsAllColumn;
	@FXML
	TableColumn<Operation, String> operationsSelectedColumn;
	@FXML
	Button addAll;
	@FXML
	Button addSelected;
	@FXML
	Button removeAll;
	@FXML
	Button removeSelected;
	@FXML
	TextField productModelTextField;
	@FXML
	TextField productNameTextField;
	private Product product;

	ObservableList<Operation> listAll;
	ObservableList<Operation> listSelected;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		operationsAllColumn = new TableColumn<Operation, String>("Список операцій");
		operationsSelectedColumn = new TableColumn<Operation, String>("Операції даної моделі");
		operationsAllColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Operation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Operation, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getName());
					}
				});
		operationsSelectedColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Operation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Operation, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getName());
					}
				});
		allOperationsTable.getColumns().add(operationsAllColumn);
		selectedOperationsTable.getColumns().add(operationsSelectedColumn);
		operationsAllColumn.prefWidthProperty().bind(allOperationsTable.widthProperty());
		operationsSelectedColumn.prefWidthProperty().bind(selectedOperationsTable.widthProperty());
		product = ProductSaver.getProduct();
		productModelTextField.setText(new Integer(product.getModel()).toString());
		productNameTextField.setText(product.getName());
		List<Pr_op_sequence> operations = ProductOperationsDAO.selectOperationSequense(product);
		listAll = FXCollections.observableArrayList(OperationDAO.selectAll());
		listSelected = FXCollections.observableArrayList();
		for (int i = 0; i < operations.size(); i++)
			listSelected.add(operations.get(i).getOperation());
		if(operations.isEmpty()) {
			removeSelected.setDisable(true);
			removeAll.setDisable(true);
		}
		listAll.removeAll(listSelected);
		allOperationsTable.setItems(listAll);
		selectedOperationsTable.setItems(listSelected);
		if(listAll.isEmpty()) {
			addSelected.setDisable(true);
			addAll.setDisable(true);
		}
		allOperationsTable.getSelectionModel().select(0);
		selectedOperationsTable.getSelectionModel().select(0);

	}

	@FXML
	public void addSelectedAction() {
		ObservableList<Operation> selectedOp = allOperationsTable.getSelectionModel().getSelectedItems();
		listSelected.addAll(selectedOp);
		listAll.removeAll(selectedOp);
		allOperationsTable.refresh();
		selectedOperationsTable.refresh();
		if (removeSelected.isDisable()) {
			removeSelected.setDisable(false);
			removeAll.setDisable(false);
		}
		if (listAll.size() == 0) {
			addSelected.setDisable(true);
			addAll.setDisable(true);
		}
	}

	@FXML
	public void addAllAction() {
		listSelected.addAll(listAll);
		listAll.removeAll(listAll);
		allOperationsTable.refresh();
		selectedOperationsTable.refresh();
		addSelected.setDisable(true);
		addAll.setDisable(true);
		removeSelected.setDisable(false);
		removeAll.setDisable(false);
		selectedOperationsTable.getSelectionModel().select(0);
	}

	@FXML
	public void removeSelectedAction() {
		ObservableList<Operation> selectedOp = selectedOperationsTable.getSelectionModel().getSelectedItems();
		listAll.removeAll(selectedOp);
		listAll.addAll(selectedOp);
		listSelected.removeAll(selectedOp);
		allOperationsTable.refresh();
		selectedOperationsTable.refresh();
		if (addSelected.isDisable()) {
			addSelected.setDisable(false);
			addAll.setDisable(false);
		}
		if (listSelected.size() == 0) {
			removeSelected.setDisable(true);
			removeAll.setDisable(true);
		}
	}

	@FXML
	public void removeAllAction() {
		listAll.removeAll(listSelected);
		listAll.addAll(listSelected);
		listSelected.removeAll(listSelected);
		allOperationsTable.refresh();
		selectedOperationsTable.refresh();
		addSelected.setDisable(false);
		addAll.setDisable(false);
		removeSelected.setDisable(true);
		removeAll.setDisable(true);
		allOperationsTable.getSelectionModel().select(0);
	}

	@FXML
	public void createProduct() {
		try {
			product.setModel(new Integer(productModelTextField.getText().trim()));
			product.setName(productNameTextField.getText().trim());
			for (Pr_op_sequence operation : ProductOperationsDAO.selectOperationSequense(product)) {
				ProductOperationsDAO.Delete(operation);
			}
			List<Pr_op_sequence> operations = new LinkedList<>();
			if (!ProductSaver.isUpdate()) {
				ProductDAO.Add(product);
			}
			for (Operation operation : listSelected) {
				Pr_op_sequence op = new Pr_op_sequence();
				op.setModel(product);
				op.setOperation(operation);
				op.setNumber(listSelected.indexOf(operation) + 1);
				ProductOperationsDAO.Add(op);
				operations.add(op);
			}
//			product.setOperations(operations);
			ProductDAO.Update(product);
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введені дані. Дані про обладнання не оновлено.");
			alert.showAndWait();
		}
	}
}
