package model;

import java.io.Serializable;

import javax.persistence.*;

import model.id.WorkplaceId;

@Entity
@Table(name="workplace")
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.machineNo",
        joinColumns = @JoinColumn(name = "machineNo")),
    @AssociationOverride(name = "primaryKey.equipment_id",
        joinColumns = @JoinColumn(name = "Equipment_id")),
    @AssociationOverride(name = "primaryKey.worker_id",
    joinColumns = @JoinColumn(name = "worker_id")),
   })

public class Workplace implements Serializable {
	private static final long serialVersionUID = -6425106669200558622L;

	@EmbeddedId
	private WorkplaceId primaryKey = new WorkplaceId();
	
//	@ManyToOne
//	@JoinColumn(name="worker_id")
//	private Worker worker_id;

	public Workplace() {
	}
	
//	public Worker getWorker_id() {
//		return worker_id;
//	}
//	
//	public void setWorker_id(Worker worker_id) {
//		this.worker_id = worker_id;
//	}

	@Transient
	public int getMachineNo() {
		return getPrimaryKey().getMachineNo();
	}
	
	@Transient
	public void setMachineNo(int no) {
		getPrimaryKey().setMachineNo(no);
	}

	@Transient
	public Equipment getEquipment_id() {
		return getPrimaryKey().getEquipment_id();
	}

	@Transient
	public Worker getWorker_id() {
		return getPrimaryKey().getWorker_id();
	}
	
	public void setWorker_id(Worker equipment) {
		getPrimaryKey().setWorker_id(equipment);
	}
	
	@Transient
	public void setEquipment_id(Equipment equipment) {
		getPrimaryKey().setEquipment_id(equipment);
	}

	 @EmbeddedId
	public WorkplaceId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(WorkplaceId primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((primaryKey == null) ? 0 : primaryKey.hashCode());
//		result = prime * result + ((worker_id == null) ? 0 : worker_id.hashCode());
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
		Workplace other = (Workplace) obj;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if (!primaryKey.equals(other.primaryKey))
			return false;
//		if (worker_id == null) {
//			if (other.worker_id != null)
//				return false;
//		} else if (!worker_id.equals(other.worker_id))
//			return false;
		return true;
	}

}
