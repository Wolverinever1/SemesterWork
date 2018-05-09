package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="orders")
public class Order implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="order_id")
	private int orderId;
	@Column(name="order_date")
	private Date orderDate;
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	public Order(int orderId, Date orderDate, Customer customer) {
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.customer = customer;
	}

	@OneToMany
    @JoinColumn(name="order_id")
	private Set<Order_product> products = new HashSet<>();
	
	public Order(int orderId, Date orderDate, Customer customer, Set<Order_product> products) {
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.customer = customer;
		this.products = products;
	}

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@OneToMany
    @JoinColumn(name="order_id")
	public Set<Order_product> getProducts() {
		return products;
	}

	public void setProducts(Set<Order_product> products) {
		this.products = products;
	}
	
	

}