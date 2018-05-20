package model.id;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import model.Operation;
import model.Order_product;
import model.Worker;

@Embeddable
public class DoneWorkId implements Serializable {
	@EmbeddedId
	@ManyToOne
	private Order_product model;
	private Operation operation_id;
	private Worker worker_id;

	private static final long serialVersionUID = 1L;

	public DoneWorkId() {
		// TODO Auto-generated constructor stub
	}

	@ManyToOne
	public Worker getWorker_id() {
		return worker_id;
	}

	public void setWorker_id(Worker worker_id) {
		this.worker_id = worker_id;
	}

	@ManyToOne
	@JoinColumn(name = "operation_id")
	public Operation getOperation_id() {
		return operation_id;
	}

	public void setOperation_id(Operation operation) {
		this.operation_id = operation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		 result = prime * result + ((operation_id == null) ? 0 :
		 operation_id.hashCode());
		 result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((worker_id == null) ? 0 : worker_id.hashCode());
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
		DoneWorkId other = (DoneWorkId) obj;
		 if (operation_id == null) {
		 if (other.operation_id != null)
		 return false;
		 } else if (!operation_id.equals(other.operation_id))
		 return false;
		 if (model == null) {
		 if (other.model != null)
		 return false;
		 } else if (!model.equals(other.model))
		 return false;
		if (worker_id == null) {
			if (other.worker_id != null)
				return false;
		} else if (!worker_id.equals(other.worker_id))
			return false;
		return true;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "model"),
			@JoinColumn(name = "order_id") })
	public Order_product getModel() {
		return model;
	}
	//
	// @ManyToOne
	// @Transient
	// public Order getOrder_id() {
	// return getModel().getOrder_id();
	// }

	public void setModel(Order_product model) {
		this.model = model;
	}
	//
}
