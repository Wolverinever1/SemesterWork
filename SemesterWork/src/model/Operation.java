package model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Table(name = "operation")
public class Operation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "operation_id")
	private int operationId;
	@Column(name = "price",precision = 19, scale = 4,columnDefinition="DECIMAL(19,4)")
	private BigDecimal price;
	@Column(name = "time",precision = 19, scale = 4, columnDefinition="DECIMAL(19,4)")
	private BigDecimal time;
	@Column(name = "op_name")
	private String name;
	@Column(name = "op_grade")
	private int grade;

	@ManyToOne
	@JoinColumn(name = "Equipment_id")
	private Equipment equipment;

	public Operation() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Operation(int operationId, BigDecimal price, BigDecimal time, String name, int grade, Equipment equipment) {
		this.operationId = operationId;
		this.price = price;
		this.time = time;
		this.name = name;
		this.grade = grade;
		this.equipment = equipment;
		this.price.setScale(4);
		this.time.setScale(4);
	}

	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTime() {
		return time;
	}

	public void setTime(BigDecimal time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

}
