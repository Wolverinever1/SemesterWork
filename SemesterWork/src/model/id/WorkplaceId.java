package model.id;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import model.Equipment;
import model.Worker;

@Embeddable
public class WorkplaceId implements Serializable{
	
	private static final long serialVersionUID = -7547386438633365107L;
	private Equipment equipment_id;
	private Worker worker_id;
	@Id
	private int machineNo;
	
	public WorkplaceId() {
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public Equipment getEquipment_id() {
		return equipment_id;
	}

	public void setEquipment_id(Equipment equipment_id) {
		this.equipment_id = equipment_id;
	}

	public int getMachineNo() {
		return machineNo;
	}
	
	public void setMachineNo(int machineNo) {
		this.machineNo = machineNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((equipment_id == null) ? 0 : equipment_id.hashCode());
		result = prime * result + machineNo;
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
		WorkplaceId other = (WorkplaceId) obj;
		if (equipment_id == null) {
			if (other.equipment_id != null)
				return false;
		} else if (!equipment_id.equals(other.equipment_id))
			return false;
		if (machineNo != other.machineNo)
			return false;
		return true;
	}

	@ManyToOne
	public Worker getWorker_id() {
		return worker_id;
	}

	public void setWorker_id(Worker worker_id) {
		this.worker_id = worker_id;
	}


}
