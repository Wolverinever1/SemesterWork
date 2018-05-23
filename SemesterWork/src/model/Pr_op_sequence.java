package model;

import java.io.Serializable;

import javax.persistence.*;

import model.id.ProducOperationtId;

@Entity
@Table(name = "Pr_op_sequence")
@AssociationOverrides({
		@AssociationOverride(name = "primaryKey.operation", joinColumns = @JoinColumn(name = "operation_id")),
		@AssociationOverride(name = "primaryKey.product", joinColumns = @JoinColumn(name = "model")) })

public class Pr_op_sequence implements Serializable {

	private static final long serialVersionUID = 2880808350977578906L;

	@EmbeddedId
	private ProducOperationtId primaryKey = new ProducOperationtId();
	private int number;

	public Pr_op_sequence() {
		// TODO Auto-generated constructor stub
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Transient
	public Product getModel() {
		return getPrimaryKey().getModel();
	}

	public void setModel(Product product) {
		getPrimaryKey().setModel(product);
	}

	@Transient
	public Operation getOperation() {
		return getPrimaryKey().getOperation_id();
	}

	public void setOperation(Operation operation) {
		getPrimaryKey().setOperation_id(operation);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		// result = prime * result + ((doneWork == null) ? 0 : doneWork.hashCode());
		result = prime * result + ((primaryKey == null) ? 0 : primaryKey.hashCode());
		result = prime * result + number;
		return result;
	}

	// @Override
	// public boolean equals(Object obj) {
	// if (this == obj)
	// return true;
	// if (obj == null)
	// return false;
	// if (getClass() != obj.getClass())
	// return false;
	// Pr_op_sequence other = (Pr_op_sequence) obj;
	// if (primaryKey == null) {
	// if (other.primaryKey != null)
	// return false;
	// } else if (!primaryKey.equals(other.primaryKey))
	// return false;
	// return true;
	// }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pr_op_sequence other = (Pr_op_sequence) obj;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if ((this.primaryKey.getModel().getModel() == other.primaryKey.getModel().getModel()) && primaryKey.getOperation_id()
				.getOperationId() == other.primaryKey.getOperation_id().getOperationId())
			return true;
		return false;
	}

	@EmbeddedId
	public ProducOperationtId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(ProducOperationtId id) {
		this.primaryKey = id;
	}

}
