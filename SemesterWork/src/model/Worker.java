package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="worker")
public class Worker implements Serializable{

	private static final long serialVersionUID = -1294904422298251548L;
	@Id
	@GenericGenerator(strategy="increment", name = "increment")
	@GeneratedValue(generator="increment")
	@Column(name="worker_id")
	private int worker_id;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doneWork == null) ? 0 : doneWork.hashCode());
		result = prime * result + ((fName == null) ? 0 : fName.hashCode());
		result = prime * result + grade;
		result = prime * result + ((lName == null) ? 0 : lName.hashCode());
		result = prime * result + ((mName == null) ? 0 : mName.hashCode());
		result = prime * result + worker_id;
		result = prime * result + ((workplaces == null) ? 0 : workplaces.hashCode());
		return result;
	}
	
	@Column(name="FName")
	private String fName;
	@Column(name="MName")
	private String mName;
	@Column(name="LName")
	private String lName;
	@Column(name="grade")
	private int grade;
	@OneToMany
	@JoinColumn(name="worker_id")
	private Set<Done_work> doneWork = new HashSet<>();
	@OneToMany
	@JoinColumn(name="worker_id")
	private Set<Workplace> workplaces = new HashSet<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Worker other = (Worker) obj;
		if (doneWork == null) {
			if (other.doneWork != null)
				return false;
		} else if (!doneWork.equals(other.doneWork))
			return false;
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (!fName.equals(other.fName))
			return false;
		if (grade != other.grade)
			return false;
		if (lName == null) {
			if (other.lName != null)
				return false;
		} else if (!lName.equals(other.lName))
			return false;
		if (mName == null) {
			if (other.mName != null)
				return false;
		} else if (!mName.equals(other.mName))
			return false;
		if (worker_id != other.worker_id)
			return false;
		if (workplaces == null) {
			if (other.workplaces != null)
				return false;
		} else if (!workplaces.equals(other.workplaces))
			return false;
		return true;
	}


	
	public Worker(int id, String fName, String mName, String lName, int grade, Set<Done_work> doneWork,
			Set<Workplace> workplaces) {
		this.worker_id = id;
		this.fName = fName;
		this.mName = mName;
		this.lName = lName;
		this.grade = grade;
		this.doneWork = doneWork;
		this.workplaces = workplaces;
	}

	public Worker() {
		// TODO Auto-generated constructor stub
	}

	public Worker(String fName, String mName, String lName, int grade) {
		this.fName = fName;
		this.mName = mName;
		this.lName = lName;
		this.grade = grade;
	}


	public int getWorker_id() {
		return worker_id;
	}


	public void setWorker_id(int id) {
		this.worker_id = id;
	}


	public String getFName() {
		return fName;
	}


	public void setfName(String fName) {
		this.fName = fName;
	}


	public String getMName() {
		return mName;
	}


	public void setmName(String mName) {
		this.mName = mName;
	}


	public String getLName() {
		return lName;
	}


	public void setlName(String lName) {
		this.lName = lName;
	}


	public int getGrade() {
		return grade;
	}


	public void setGrade(int grade) {
		this.grade = grade;
	}


	public Set<Done_work> getDoneWork() {
		return doneWork;
	}


	public void setDoneWork(Set<Done_work> doneWork) {
		this.doneWork = doneWork;
	}


	public Set<Workplace> getWorkplaces() {
		return workplaces;
	}


	public void setWorkplaces(Set<Workplace> workplaces) {
		this.workplaces = workplaces;
	}
	
}
