package model.id;

import java.io.Serializable;

import javax.persistence.*;

import model.Order;
import model.Product;

@Embeddable
public class OrderProductId implements Serializable {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
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
		OrderProductId other = (OrderProductId) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;
	private Order order;
	private Product product;
	
	public OrderProductId() {
		// TODO Auto-generated constructor stub
	}
	@ManyToOne(cascade = CascadeType.ALL)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	@ManyToOne(cascade = CascadeType.ALL)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
