package model.id;

import java.io.Serializable;

import javax.persistence.*;

import model.Operation;
import model.Product;

@Embeddable
public class ProducOperationtId implements Serializable{
	
	private static final long serialVersionUID = -8933048132687588463L;
	private Product product;
	private Operation operation;
	
	@ManyToOne(cascade = CascadeType.ALL)
	public Product getProduct() {
		return product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		return true;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public ProducOperationtId() {
		// TODO Auto-generated constructor stub
	}

}
