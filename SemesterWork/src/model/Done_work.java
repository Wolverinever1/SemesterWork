package model;

import java.io.Serializable;

import javax.persistence.*;

import model.id.DoneWorkId;

@Entity
@Table(name = "done_work")
@AssociationOverrides({
		@AssociationOverride(name = "primaryKey.worker_id", joinColumns = @JoinColumn(name = "worker_id")),
		@AssociationOverride(name = "primaryKey.model", joinColumns = { @JoinColumn(name = "model"),
				@JoinColumn(name = "order_id") }),
 @AssociationOverride(name = "primaryKey.operation_id", joinColumns =
 @JoinColumn(name = "operation_id"))})
public class Done_work implements Serializable {

	private static final long serialVersionUID = -4681729637023306383L;

	@Column(name = "count_done")
	private int countDone;

	@EmbeddedId
	private DoneWorkId primaryKey = new DoneWorkId();

	public Done_work() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + countDone;
		result = prime * result + ((primaryKey == null) ? 0 : primaryKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Done_work other = (Done_work) obj;
		if (countDone != other.countDone)
			return false;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if (!primaryKey.equals(other.primaryKey))
			return false;
		return true;
	}

	public int getCount_done() {
		return countDone;
	}

	public void setCount_done(int countDone) {
		this.countDone = countDone;
	}

	@Transient
	public Order_product getModel() {
		return getPrimaryKey().getModel();
	}

	public void setModel(Order_product product) {
		getPrimaryKey().setModel(product);
	}

	@Transient
	public Operation getOperation_id() {
		return getPrimaryKey().getOperation_id();
	}

	public void setOperation_id(Operation operation) {
		getPrimaryKey().setOperation_id(operation);
	}

	@Transient
	public Worker getWorker_id() {
		return getPrimaryKey().getWorker_id();
	}

	public void setWorker(Worker worker) {
		getPrimaryKey().setWorker_id(worker);
	}

	@EmbeddedId
	public DoneWorkId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(DoneWorkId primaryKey) {
		this.primaryKey = primaryKey;
	}

}
