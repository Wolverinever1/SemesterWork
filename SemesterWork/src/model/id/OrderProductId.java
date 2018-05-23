package model.id;

import java.io.Serializable;

import javax.persistence.*;

import model.Order;
import model.Product;

@Embeddable
public class OrderProductId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Order order;
	private Product model;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
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
		OrderProductId other = (OrderProductId) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		return true;
	}

	
	public OrderProductId() {
		// TODO Auto-generated constructor stub
	}
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="model")
	public Product getModel() {
		return model;
	}

	public void setModel(Product product) {
		this.model = product;
	}
}
