package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import model.id.ProducOperationtId;

@Entity
@Table (name="Pr_op_sequence")
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.operation",
        joinColumns = @JoinColumn(name = "operation_id")),
    @AssociationOverride(name = "primaryKey.product",
        joinColumns = @JoinColumn(name = "model")) })

public class Pr_op_sequence implements Serializable{
	private static final long serialVersionUID = 2880808350977578906L;
	
	@EmbeddedId
	private ProducOperationtId primaryKey = new ProducOperationtId();
	private int number;
	@OneToMany
	@JoinColumns(value= {@JoinColumn(name="operation_id"),@JoinColumn(name="model")})
	private Set<Done_work> doneWork = new HashSet<>();
	
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
	public Product getProduct() {
		return getPrimaryKey().getProduct();
	}

	public void setProduct(Product product) {
		getPrimaryKey().setProduct(product);
	}

	 @Transient
	public Operation getOperation() {
		return getPrimaryKey().getOperation();
	}

	public void setOperation(Operation operation) {
		getPrimaryKey().setOperation(operation);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doneWork == null) ? 0 : doneWork.hashCode());
		result = prime * result + ((primaryKey == null) ? 0 : primaryKey.hashCode());
		result = prime * result + number;
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
		Pr_op_sequence other = (Pr_op_sequence) obj;
		if (doneWork == null) {
			if (other.doneWork != null)
				return false;
		} else if (!doneWork.equals(other.doneWork))
			return false;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if (!primaryKey.equals(other.primaryKey))
			return false;
		if (number != other.number)
			return false;
		return true;
	}

	public Set<Done_work> getDoneWork() {
		return doneWork;
	}

	public void setDoneWork(Set<Done_work> doneWork) {
		this.doneWork = doneWork;
	}

	public ProducOperationtId getPrimaryKey() {
		return primaryKey;
	}

	public void setId(ProducOperationtId id) {
		this.primaryKey = id;
	}

}
