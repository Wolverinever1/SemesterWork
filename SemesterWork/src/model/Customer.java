package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
@Entity
@Table(name="customer")
public class Customer {
	@Id
	@Column(name="customer_id")
	private int customerId;
	@Column(name="customer_name")
	private String customerName;
	@Column(name="phone")
	private char[] phone;
	@Column(name="address")
	private String address;
	
	@OneToMany
    @JoinColumn(name="order_id")
	private Set<Order> orders = new HashSet<>();

	public Customer(int customerId, String customerName, char[] phone, String address) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.phone = phone;
		this.address = address;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public char[] getPhone() {
		return phone;
	}

	public void setPhone(char[] phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Customer() {
		// TODO Auto-generated constructor stub
	}

}
