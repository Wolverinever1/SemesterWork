package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import model.id.OrderProductId;

@Entity
@Table(name = "order_product")
@AssociationOverrides({
		@AssociationOverride(name = "primaryKey.order_id", joinColumns = @JoinColumn(name = "order_id")),
		@AssociationOverride(name = "primaryKey.model", joinColumns = @JoinColumn(name = "model")) })
public class Order_product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int count;
	@EmbeddedId
	private OrderProductId primaryKey = new OrderProductId();

	@OneToMany
	@JoinColumns(value = { @JoinColumn(name = "order_id"), @JoinColumn(name = "model") })
	private Set<Done_work> doneWork = new HashSet<>();
	
	public Order_product() {
		// TODO Auto-generated constructor stub
	}

	@EmbeddedId
	public OrderProductId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(OrderProductId primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Transient
	public Order getOrder_id() {
		return getPrimaryKey().getOrder_id();
	}

	public void setOrder(Order order) {
		getPrimaryKey().setOrder_id(order);
	}

	@Transient
	public Product getModel() {
		return getPrimaryKey().getModel();
	}

	public void setModel(Product product) {
		getPrimaryKey().setModel(product);
	}


	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@OneToMany
	@JoinColumns({ @JoinColumn(name = "model"),
			@JoinColumn(name = "order_id") })
	public Set<Done_work> getDoneWork() {
		return doneWork;
	}

	public void setDoneWork(Set<Done_work> doneWork) {
		this.doneWork = doneWork;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + ((doneWork == null) ? 0 : doneWork.hashCode());
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
		Order_product other = (Order_product) obj;
		if (count != other.count)
			return false;
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
		return true;
	}
}
