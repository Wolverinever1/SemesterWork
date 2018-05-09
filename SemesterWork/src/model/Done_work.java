package model;

import javax.persistence.*;

import model.id.DoneWorkId;

@Entity
@Table(name = "done_work")
@AssociationOverrides({ @AssociationOverride(name = "primaryKey.worker", joinColumns = @JoinColumn(name = "worker_id")),
	@AssociationOverride(name = "primaryKey.product", joinColumns = {@JoinColumn(name = "model"), @JoinColumn(name = "order_id") }),
	@AssociationOverride(name = "primaryKey.operation", joinColumns = @JoinColumn(name = "operation_id"))})
public class Done_work {
	@Column(name = "count_done")
	private int countDone;
	@EmbeddedId
	private DoneWorkId primaryKey = new DoneWorkId();

	public Done_work() {
		// TODO Auto-generated constructor stub
	}

	public int getCountDone() {
		return countDone;
	}

	public void setCountDone(int countDone) {
		this.countDone = countDone;
	}

	@Transient
	public Order_product getProduct() {
		return getPrimaryKey().getProduct();
	}

	public void setProduct(Order_product product) {
		getPrimaryKey().setProduct(product);
	}

	@Transient
	public Pr_op_sequence getOperation() {
		return getPrimaryKey().getOperation();
	}

	public void setOperation(Pr_op_sequence operation) {
		getPrimaryKey().setOperation(operation);
	}

	@Transient
	public Worker getWorker() {
		return getPrimaryKey().getWorker();
	}

	public void setWorker(Worker worker) {
		getPrimaryKey().setWorker(worker);
	}

	public DoneWorkId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(DoneWorkId primaryKey) {
		this.primaryKey = primaryKey;
	}

}
