package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
@Entity
@Table(name="product")
public class Product implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	private int model;
	private String name;
	@OneToMany
    @JoinColumn(name="model",insertable = false, updatable=false)
	private Set<Order_product> orders = new HashSet<>();
//	@OneToMany
//    @JoinColumn(name="model")
//	private List<Pr_op_sequence> operations = new LinkedList<>();
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

	public int getModel() {
		return model;
	}


	public void setModel(int model) {
		this.model = model;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Set<Order_product> getOrders() {
		return orders;
	}


	public void setOrders(Set<Order_product> orders) {
		this.orders = orders;
	}

//	public List<Pr_op_sequence> getOperations() {
//		return operations;
//	}
//
//
//	public void setOperations(List<Pr_op_sequence> operations) {
//		this.operations = operations;
//	}



}
