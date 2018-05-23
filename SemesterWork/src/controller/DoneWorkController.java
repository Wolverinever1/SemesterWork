package controller;

import java.math.BigDecimal;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Done_work;
import model.Pr_op_sequence;
import model.Worker;
import model.Workplace;
import model.dao.DoneWorkDAO;
import model.dao.OperationDAO;
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

	private ObservableList<Done_work> listDoneWork;
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

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		columnWorkerId = new TableColumn<Done_work, Integer>("Код");
		columnLMF = new TableColumn<Done_work, String>("ПІБ");
		columnOperationNo = new TableColumn<Done_work, Integer>("Код");
		columnOperationName = new TableColumn<Done_work, String>("Назва операції");
		columnPrice = new TableColumn<Done_work, BigDecimal>("Ціна");
		columnTime = new TableColumn<Done_work, BigDecimal>("Час");
		columnCount = new TableColumn<Done_work, Integer>("Кількість");

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

		columnCount.setCellValueFactory(new PropertyValueFactory<>("countDone"));

		doneWorkTable.getColumns().addAll(columnWorkerId, columnLMF, columnOperationNo, columnOperationName,
				columnPrice, columnTime, columnCount);
		listDoneWork = FXCollections.observableArrayList(DoneWorkDAO.selectAll());
		doneWorkTable.setItems(listDoneWork);

	}

	protected class OperationWrapper {
		private int order_id;
		private int model;
		private int operation_id;
		private int equipment_id;
		private int grade;
		private BigDecimal time;

		public OperationWrapper(Object[] o) {

			order_id = new Integer(o[0].toString());
			model = new Integer(o[1].toString());
			operation_id = new Integer(o[2].toString());
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

		public WorkerWrapper(Worker worker) {
			id = worker.getWorker_id();
			grade = worker.getGrade();
			for (Workplace wp : WorkplaceDAO.selectEquipmentId(worker)) {
				equipment.add(wp.getEquipment_id().getId());
			}
			busyTime = new BigDecimal("0.0");
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
		List<Object[]> dw = DoneWorkDAO.FindActive();
		BigDecimal tact = ProductOperationsDAO.ModelTime(model);
		ProductOperationsDAO.selectOperationSequense(model);
		List<OperationWrapper> active = new LinkedList<>();
		for (Object[] o : dw) {
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
			if (wrapper.model == new Integer(model).intValue()&&wrapper.order_id == new Integer(order_id).intValue())
				activeThisModel.add(wrapper);
		}
		 allOperations.removeAll(activeThisModel);
		List<WorkerWrapper> workers = new LinkedList<>();
		List<Worker> workersInfo = WorkerDAO.selectAllOrderByGrade();
		for (Worker w : workersInfo) {
			WorkerWrapper wrapper = new WorkerWrapper(w);
			workers.add(wrapper);
		}
		for (int i = 0; i < allOperations.size(); i++) {
			BigDecimal opTime = new BigDecimal(allOperations.get(i).getTime().toString());
			for (int j = 0; j < workers.size(); j++) {
				if (workers.get(j).getGrade() >= allOperations.get(i).getGrade()
						&& workers.get(j).getEquipment().contains(allOperations.get(i).getEquipment_id())) {
					opTime.subtract(tact.subtract(workers.get(j).getBusyTime()));
					// save done_work
					System.out.println("Add work: order" + order_id + " -model- " + model
							+ allOperations.get(i).getOperation_id() + " -worker- " + workers.get(j).id + " -op_grade- "
							+ allOperations.get(i).grade + " -w_grade- " + workers.get(j).grade);
					if (workers.get(j).getBusyTime().equals(new BigDecimal(0.0))) {
						workers.remove(j);
						j--;
					}
				}
			}
			if (opTime.compareTo(new BigDecimal(0.0)) == 1 && (!workers.isEmpty())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("");
				alert.setHeaderText("Увага!");
				alert.setContentText("Необхідно додати працівнику робоче місце з номером обладнання "
						+ allOperations.get(i).equipment_id + " і розрядом не менше " + allOperations.get(i).grade);
				alert.showAndWait();
				Stage stage = (Stage) doneWorkTable.getScene().getWindow();
				stage.close();
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

}
