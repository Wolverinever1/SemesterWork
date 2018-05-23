package controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Customer;
import model.Order;
import model.Order_product;
import model.Product;
import model.dao.CustomerDAO;
import model.dao.OrderDAO;
import model.dao.OrderProductsDAO;
import model.dao.ProductDAO;

public class OrderController implements Initializable {

	protected class Wrapper {
		private Product product;
		private TextField field;
		private Button button;

		public Wrapper(Product product, String label) {
			this.product = product;
			button = new Button(label);
			field = new TextField();
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		public TextField getField() {
			return field;
		}

		public void setField(TextField field) {
			this.field = field;
		}

		public Button getButton() {
			return button;
		}

		public void setButton(Button button) {
			this.button = button;
		}
	}

	@FXML
	TableView<Wrapper> orderProductsTable;
	@FXML
	TableView<Wrapper> productsTable;
	@FXML
	TableView<Customer> customersTable;

	@FXML
	TextArea customerField;

	@FXML
	DatePicker date;
	@FXML
	Text totalPrice;

	@FXML
	TableColumn<Wrapper, Label> productNameColumn;
	@FXML
	TableColumn<Wrapper, String> productIdColumn;
	@FXML
	TableColumn<Wrapper, Button> addProductColumn;
	@FXML
	TableColumn<Customer, Integer> customerIdColumn;
	@FXML
	TableColumn<Customer, String> customerNameColumn;

	@FXML
	TableColumn<Wrapper, Label> productSelectedNameColumn;
	@FXML
	TableColumn<Wrapper, TextField> productSelectedCountColumn;
	@FXML
	TableColumn<Wrapper, Button> removeProductSelectedColumn;
	@FXML
	TableColumn<Wrapper, String> productSelectedIdColumn;
	
	private static Function<Order, Boolean> RefreshTables;

	public static void setRefreshTables(Function<Order, Boolean> refreshTables) {
		RefreshTables = refreshTables;
	}

	private ObservableList<Wrapper> listProduct;
	private ObservableList<Wrapper> listSelectedProduct;
	private ObservableList<Customer> listCustomers;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		date.valueProperty().setValue(LocalDate.now());
		productNameColumn = new TableColumn<Wrapper, Label>("Назва");
		productIdColumn = new TableColumn<Wrapper, String>("Модель");
		addProductColumn = new TableColumn<Wrapper, Button>();

		productNameColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Wrapper, Label>, ObservableValue<Label>>() {

					@Override
					public ObservableValue<Label> call(CellDataFeatures<Wrapper, Label> param) {
						return new ReadOnlyObjectWrapper<Label>(new Label(param.getValue().getProduct().getName()));
					}
				});
		productIdColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Wrapper, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Wrapper, String> param) {
						return new ReadOnlyObjectWrapper<String>(
								new Integer(param.getValue().getProduct().getModel()).toString());
					}
				});
		addProductColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Wrapper, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Wrapper, Button> param) {
						param.getValue().getButton().setOnAction(event -> addProduct(param.getValue()));
						param.getValue().getButton().setText("+");
						return new ReadOnlyObjectWrapper<Button>(param.getValue().getButton());
					}
				});
		List<Wrapper> wrappers = new LinkedList<>();
		List<Product> products = ProductDAO.selectAll();
		for (Product p : products) {
			wrappers.add(new Wrapper(p, " + "));
		}
		listProduct = FXCollections.observableArrayList(wrappers);
		productsTable.getColumns().addAll(productIdColumn, productNameColumn, addProductColumn);
		productsTable.setItems(listProduct);
		//
		productSelectedNameColumn = new TableColumn<Wrapper, Label>("Назва");
		productSelectedCountColumn = new TableColumn<Wrapper, TextField>("Кількість");
		removeProductSelectedColumn = new TableColumn<Wrapper, Button>();
		productSelectedIdColumn = new TableColumn<Wrapper, String>("Модель");

		productSelectedNameColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Wrapper, Label>, ObservableValue<Label>>() {

					@Override
					public ObservableValue<Label> call(CellDataFeatures<Wrapper, Label> param) {
						return new ReadOnlyObjectWrapper<Label>(new Label(param.getValue().getProduct().getName()));
					}
				});
		productSelectedCountColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Wrapper, TextField>, ObservableValue<TextField>>() {

					@Override
					public ObservableValue<TextField> call(CellDataFeatures<Wrapper, TextField> param) {
						return new ReadOnlyObjectWrapper<TextField>(param.getValue().getField());
					}
				});
		productSelectedNameColumn.prefWidthProperty().bind(orderProductsTable.widthProperty().divide(2));
		productSelectedCountColumn.prefWidthProperty().bind(orderProductsTable.widthProperty().divide(4));
		removeProductSelectedColumn.prefWidthProperty().bind(orderProductsTable.widthProperty().divide(8));
		productSelectedIdColumn.prefWidthProperty().bind(orderProductsTable.widthProperty().divide(8));

		removeProductSelectedColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Wrapper, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Wrapper, Button> param) {
						param.getValue().getButton().setOnAction(event -> removeProduct(param.getValue()));
						param.getValue().getButton().setText("-");
						param.getValue().getButton().prefWidthProperty()
								.bind(orderProductsTable.widthProperty().divide(9));
						return new ReadOnlyObjectWrapper<Button>(param.getValue().getButton());
					}
				});

		productSelectedIdColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Wrapper, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Wrapper, String> param) {
						return new ReadOnlyObjectWrapper<String>(
								new Integer(param.getValue().getProduct().getModel()).toString());
					}
				});

		listSelectedProduct = FXCollections.observableArrayList();
		orderProductsTable.getColumns().addAll(productSelectedIdColumn, productSelectedNameColumn,
				productSelectedCountColumn, removeProductSelectedColumn);
		orderProductsTable.setItems(listSelectedProduct);

		customerIdColumn = new TableColumn<Customer, Integer>("Номер");
		customerNameColumn = new TableColumn<Customer, String>("Ім'я замовника");
		customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		customersTable.getColumns().addAll(customerIdColumn, customerNameColumn);
		listCustomers = FXCollections.observableArrayList(CustomerDAO.selectAll());
		customersTable.setItems(listCustomers);
	}

	public void addProduct(Wrapper product) {
		listProduct.remove(product);
		productsTable.refresh();
		listSelectedProduct.add(product);
		orderProductsTable.refresh();
	}

	public void removeProduct(Wrapper product) {
		listSelectedProduct.remove(product);
		listProduct.add(product);
		productsTable.refresh();
		orderProductsTable.refresh();
	}

	@FXML
	public void addOrder(ActionEvent e) {
		try {
			Order o = new Order();
			o.setCustomer(customersTable.getSelectionModel().getSelectedItem());
			o.setOrderDate(Date.valueOf(date.getValue()));
			Set<Order_product> products = new HashSet<>();
			for (Wrapper wrapper : listSelectedProduct) {
				Order_product op = new Order_product();
				Integer count = new Integer(wrapper.getField().getText());
				if (count.intValue() <= 0)
					throw new NumberFormatException();
				op.setCount(count);
				op.setModel(wrapper.getProduct());
				op.setOrder(o);
				products.add(op);
			}
			OrderDAO.Add(o);
			products.forEach(x ->OrderProductsDAO.Add(x));
//			o.setProducts(products);
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					RefreshTables.apply(o);
				}
			});
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setContentText("Замовлення успішно додано.");
			alert.showAndWait();
			Stage stage = (Stage)orderProductsTable.getScene().getWindow();
			stage.close();
		} catch (NumberFormatException ex) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Будь ласка, перевірте введену кількість.");
			alert.showAndWait();
		}
	}

	@FXML
	public void selectCustomer() {
		customerField.setText(customersTable.getSelectionModel().getSelectedItem().getCustomerName());
	}
	
}
