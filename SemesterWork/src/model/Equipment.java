package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="equipment")
public class Equipment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1019860026101688630L;
	@Id
	@Column(name="Equipment_id")
	private int id;
	@Column(name="description")
	private String description;
	@OneToMany
	@JoinColumn(name="Equipment_id")
	private Set<Operation> operations = new HashSet<>();
	@OneToMany
	@JoinColumn(name="Equipment_id")
	private Set<Workplace> workplaces = new HashSet<>();
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((operations == null) ? 0 : operations.hashCode());
		result = prime * result + ((workplaces == null) ? 0 : workplaces.hashCode());
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
		Equipment other = (Equipment) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (operations == null) {
			if (other.operations != null)
				return false;
		} else if (!operations.equals(other.operations))
			return false;
		if (workplaces == null) {
			if (other.workplaces != null)
				return false;
		} else if (!workplaces.equals(other.workplaces))
			return false;
		return true;
	}

	/**
	 * 
	 */

	public Equipment() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	public Set<Workplace> getWorkplaces() {
		return workplaces;
	}

	public void setWorkplaces(Set<Workplace> workplaces) {
		this.workplaces = workplaces;
	}

}
