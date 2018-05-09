package model.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

import model.Order_product;
import model.Pr_op_sequence;
import model.Worker;

@Embeddable
public class DoneWorkId implements Serializable {
	private Order_product product;
	private Pr_op_sequence operation;
	private Worker worker;

	private static final long serialVersionUID = 1L;
	public DoneWorkId() {
		// TODO Auto-generated constructor stub
	}

	public Order_product getProduct() {
		return product;
	}

	public void setProduct(Order_product product) {
		this.product = product;
	}

	public Pr_op_sequence getOperation() {
		return operation;
	}

	public void setOperation(Pr_op_sequence operation) {
		this.operation = operation;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((worker == null) ? 0 : worker.hashCode());
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
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (worker == null) {
			if (other.worker != null)
				return false;
		} else if (!worker.equals(other.worker))
			return false;
		return true;
	}
	
}
