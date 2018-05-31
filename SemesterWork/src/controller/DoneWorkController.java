package controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Done_work;
import model.Operation;
import model.Order_product;
import model.Pr_op_sequence;
import model.Worker;
import model.Workplace;
import model.dao.DoneWorkDAO;
import model.dao.OperationDAO;
import model.dao.OrderProductsDAO;
import model.dao.ProductOperationsDAO;
import model.dao.WorkerDAO;
import model.dao.WorkplaceDAO;

public class DoneWorkController implements Initializable {

	@FXML
	TableView<Done_work> doneWorkTable;

	@FXML
	TableColumn<Done_work, Integer> columnWorkerId;
	@FXML
	TableColumn<Done_work, String> columnLMF;
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
	TextField countField;
	@FXML
	Text dataText;

	@FXML
	CheckBox justActive;

	private ObservableList<Done_work> listAllDoneWork;

	private static String order_id;
	private static String model;

	public static String getOrder_id() {
		return order_id;
	}

	public static void setOrder_id(String order_id) {
		DoneWorkController.order_id = order_id;
	}

	public static String getModel() {
		return model;
	}

	public static void setModel(String model) {
		DoneWorkController.model = model;
	}

	public void groupBy() {
//		if (justThisOrder.isSelected() && (!justActive.isSelected())) {
//			ObservableList<Done_work> justThisOrder = FXCollections
//					.observableArrayList(DoneWorkDAO.selectJustThisOrder(new Integer(order_id)));
//			doneWorkTable.setItems(justThisOrder);
//		} else if ((!justThisOrder.isSelected()) && (!justActive.isSelected())) {
//			doneWorkTable.setItems(listAllDoneWork);
//		} else if ((!justThisOrder.isSelected()) && justActive.isSelected()) {
//			
//		} else if ((justThisOrder.isSelected()) && justActive.isSelected()) {
//			List<Done_work> active = DoneWorkDAO.selectActive();
//			List<Done_work> activeThisOrder = new LinkedList<>();
//			for (Done_work d_w : active) {
//				if (d_w.getModel().getModel().getModel() == new Integer(model).intValue()
//						&& d_w.getModel().getOrder().getOrder_id() == new Integer(order_id).intValue())
//					activeThisOrder.add(d_w);
//			}
//			ObservableList<Done_work> justThisOrderAndActive = FXCollections.observableArrayList(activeThisOrder);
//			doneWorkTable.setItems(justThisOrderAndActive);
//		}
		if(justActive.isSelected()) {
			List<Done_work> active = DoneWorkDAO.selectActive();
			List<Done_work> activeThisOrder = new LinkedList<>();
			for (Done_work d_w : active) {
				if (d_w.getModel().getModel().getModel() == new Integer(model).intValue()
						&& d_w.getModel().getOrder().getOrder_id() == new Integer(order_id).intValue())
					activeThisOrder.add(d_w);
			}
			ObservableList<Done_work> justThisOrderAndActive = FXCollections.observableArrayList(activeThisOrder);
			doneWorkTable.setItems(justThisOrderAndActive);
		}else {
			doneWorkTable.setItems(listAllDoneWork);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		columnWorkerId = new TableColumn<Done_work, Integer>("Код\nпрацівника");
		columnLMF = new TableColumn<Done_work, String>("ПІБ");
		columnOperationNo = new TableColumn<Done_work, Integer>("Код\nоперації");
		columnOperationName = new TableColumn<Done_work, String>("Назва операції");
		columnPrice = new TableColumn<Done_work, BigDecimal>("Ціна");
		columnTime = new TableColumn<Done_work, BigDecimal>("Час");
		columnCount = new TableColumn<Done_work, Integer>("Кількість");
		order_Id = new TableColumn<Done_work, Integer>("№ замовлення");

		columnWorkerId.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Done_work, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Done_work, Integer> param) {
						return new ReadOnlyObjectWrapper<Integer>(param.getValue().getWorker_id().getWorker_id());
					}
				});
		columnLMF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Done_work, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Done_work, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getWorker_id().getLName() + " "
								+ param.getValue().getWorker_id().getFName() + " "
								+ param.getValue().getWorker_id().getMName());
					}
				});
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

		doneWorkTable.getColumns().addAll(order_Id, columnWorkerId, columnLMF, columnOperationNo, columnOperationName,
				columnPrice, columnTime, columnCount);
		listAllDoneWork = FXCollections.observableArrayList(DoneWorkDAO.selectAll(order_id, model));
		doneWorkTable.setItems(listAllDoneWork);

	}

	public class OperationWrapper {
		private int order_id;
		private int model;
		private int operation_id;
		private int equipment_id;
		private int grade;
		private BigDecimal time;

		public OperationWrapper(Done_work d_w) {

			order_id = d_w.getModel().getOrder().getOrder_id();
			model = d_w.getModel().getModel().getModel();
			operation_id = d_w.getOperation_id().getOperationId();
			List<Object[]> oInfo = OperationDAO.Info(operation_id);
			equipment_id = new Integer(oInfo.get(0)[0].toString());
			grade = new Integer(oInfo.get(0)[1].toString());
			time = new BigDecimal(oInfo.get(0)[2].toString());
		}

		public OperationWrapper(int order_id, int model, Pr_op_sequence o) {
			this.order_id = order_id;
			this.model = model;
			operation_id = o.getOperation().getOperationId();
			equipment_id = o.getOperation().getEquipment().getId();
			grade = o.getOperation().getGrade();
			time = o.getOperation().getTime();
		}

		public int getOrder_id() {
			return order_id;
		}

		public void setOrder_id(int order_id) {
			this.order_id = order_id;
		}

		public int getModel() {
			return model;
		}

		public void setModel(int model) {
			this.model = model;
		}

		public int getOperation_id() {
			return operation_id;
		}

		public void setOperation_id(int operation_id) {
			this.operation_id = operation_id;
		}

		public int getEquipment_id() {
			return equipment_id;
		}

		public void setEquipment_id(int equipment_id) {
			this.equipment_id = equipment_id;
		}

		public int getGrade() {
			return grade;
		}

		public void setGrade(int grade) {
			this.grade = grade;
		}

		public BigDecimal getTime() {
			return time;
		}

		public void setTime(BigDecimal time) {
			this.time = time;
		}
	}

	protected class WorkerWrapper {
		private int id;
		private Set<Integer> equipment = new HashSet<>();
		private BigDecimal busyTime;
		private int grade;

		public WorkerWrapper(Worker worker, List<OperationWrapper> active) {
			id = worker.getWorker_id();
			grade = worker.getGrade();
			for (Workplace wp : WorkplaceDAO.selectEquipmentId(worker)) {
				equipment.add(wp.getEquipment_id().getId());
			}
			if (active.size() > 0)
				busyTime = WorkerDAO.getBusyTime(active, id);
			else
				busyTime = new BigDecimal("0.0");
			System.out.println(id + " = >>" + busyTime);
		}

		public Set<Integer> getEquipment() {
			return equipment;
		}

		public void setEquipment(Set<Integer> equipment) {
			this.equipment = equipment;
		}

		public BigDecimal getBusyTime() {
			return busyTime;
		}

		public void setBusyTime(BigDecimal busyTime) {
			this.busyTime = busyTime;
		}

		public int getGrade() {
			return grade;
		}

		public void setGrade(int grade) {
			this.grade = grade;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	}

	@FXML
	public void divideOperations() {
		List<Done_work> dw = DoneWorkDAO.selectActive();
		// List<Object[]> dw = DoneWorkDAO.FindActive();
		MathContext mc = new MathContext(4, RoundingMode.HALF_UP);
		BigDecimal tact = ProductOperationsDAO.ModelTime(model).divide(new BigDecimal(WorkerDAO.getWorkerCount()),mc);
		System.out.println("TACT " + tact);
		ProductOperationsDAO.selectOperationSequense(model);
		List<OperationWrapper> active = new LinkedList<>();
		for (Done_work o : dw) {
			OperationWrapper wrapper = new OperationWrapper(o);
			active.add(wrapper);
		}
		List<OperationWrapper> allOperations = new LinkedList<>();
		List<Pr_op_sequence> pr_op = ProductOperationsDAO.selectOperationSequense(model);
		for (Pr_op_sequence o : pr_op) {
			OperationWrapper wrapper = new OperationWrapper(new Integer(order_id), new Integer(model), o);
			allOperations.add(wrapper);
		}
		List<OperationWrapper> activeThisModel = new LinkedList<>();
		for (OperationWrapper wrapper : active) {
			if (wrapper.model == new Integer(model).intValue() && wrapper.order_id == new Integer(order_id).intValue())
				activeThisModel.add(wrapper);
		}
		allOperations.removeAll(activeThisModel);
		List<WorkerWrapper> workers = new LinkedList<>();
		List<Worker> workersInfo = WorkerDAO.selectAllOrderByGrade();
		for (Worker w : workersInfo) {
			WorkerWrapper wrapper = new WorkerWrapper(w, active);
			workers.add(wrapper);
		}
		for (int i = 0; i < allOperations.size(); i++) {
			BigDecimal opTime = new BigDecimal(allOperations.get(i).getTime().toString());
			for (int j = 0; j < workers.size(); j++) {
				if (workers.get(j).getGrade() >= allOperations.get(i).getGrade()
						&& workers.get(j).getEquipment().contains(allOperations.get(i).getEquipment_id())) {
					Order_product o_p = OrderProductsDAO.select(new Integer(order_id), new Integer(model));
					Worker worker = WorkerDAO.getWorker(workers.get(j).id);
					Operation operation = OperationDAO.getOperation(allOperations.get(i).operation_id);
					Done_work done = new Done_work(0, o_p, operation, worker);
					if (!listAllDoneWork.contains(done)) {
						DoneWorkDAO.Add(done);
						listAllDoneWork.add(done);
						doneWorkTable.refresh();
						opTime.subtract(tact.subtract(workers.get(j).getBusyTime()));
						if (workers.get(j).getBusyTime().equals(new BigDecimal(0.0))) {
							workers.remove(j);
							j--;
						}
					}
				}
			}
			if (opTime.compareTo(new BigDecimal(0.0)) == 1 && (!workers.isEmpty())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("");
				alert.setHeaderText("Увага!");
				alert.setContentText("Для продовження необхідно додати працівнику робоче місце з номером обладнання "
						+ allOperations.get(i).equipment_id + " і розрядом не менше " + allOperations.get(i).grade);
				alert.showAndWait();
				return;
			} else if (workers.isEmpty()) {
				break;
			}

		}
		if (!workers.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Можна запускати іншу модель.");
			alert.showAndWait();
		}
	}

	@FXML
	public void updateCount() {
		try {
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
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("Увага!");
			alert.setContentText("Перевірте введені дані. Значення не може бути меншим, або дорівнювати 0, або містити символи, відмінні від цифр.");
			alert.showAndWait();
		}
	}

	@FXML
	public void doneWorkOnMouseClicked() {
		Done_work d_w = doneWorkTable.getSelectionModel().getSelectedItem();
		if (d_w != null) {
			dataText.setText(d_w.getOperation_id().getName() + " - " +d_w.getWorker_id().getLName()+" " +d_w.getWorker_id().getFName()+" "+d_w.getWorker_id().getMName());
			countField.setText(new Integer(d_w.getCount_done()).toString());
		}

	}
}
