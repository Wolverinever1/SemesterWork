package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "operation")
public class Operation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(strategy = "increment", name = "increment")
	@GeneratedValue(generator = "increment")
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
	@OneToMany
	@JoinColumn(name = "operation_id")
	private Set<Done_work> doneWork = new HashSet<>();

	@OneToMany
	@JoinColumn(name = "operation_id")
	public Set<Done_work> getDoneWork() {
		return doneWork;
	}

	public void setDoneWork(Set<Done_work> doneWork) {
		this.doneWork = doneWork;
	}
	

	@ManyToOne
	@JoinColumn(name = "Equipment_id")
	private Equipment equipment;

	public Operation() {
		// TODO Auto-generated constructor stub
	}

	public Operation(BigDecimal price, BigDecimal time, String name, int grade, Equipment equipment) {
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doneWork == null) ? 0 : doneWork.hashCode());
		result = prime * result + ((equipment == null) ? 0 : equipment.hashCode());
		result = prime * result + grade;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + operationId;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		Operation other = (Operation) obj;
//		if (doneWork == null) {
//			if (other.doneWork != null)
//				return false;
//		} else if (!doneWork.equals(other.doneWork))
//			return false;
		if (equipment == null) {
			if (other.equipment != null)
				return false;
		} else if (!equipment.equals(other.equipment))
			return false;
		if (grade != other.grade)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (operationId != other.operationId)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}



}
