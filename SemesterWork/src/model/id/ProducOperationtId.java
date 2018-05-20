package model.id;

import java.io.Serializable;

import javax.persistence.*;

import model.Operation;
import model.Product;

@Embeddable
public class ProducOperationtId implements Serializable{
	
	private static final long serialVersionUID = -8933048132687588463L;
	private Product model;
	private Operation operation_id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="model")
	public Product getModel() {
		return model;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operation_id == null) ? 0 : operation_id.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
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
		ProducOperationtId other = (ProducOperationtId) obj;
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
		return true;
	}

	public void setModel(Product product) {
		this.model = product;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="operation_id")
	public Operation getOperation_id() {
		return operation_id;
	}

	public void setOperation_id(Operation operation) {
		this.operation_id = operation;
	}

	public ProducOperationtId() {
		// TODO Auto-generated constructor stub
	}

}
