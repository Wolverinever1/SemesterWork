package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {

	public Account(String password, int is_dressmaker, Worker worker_id) {
		this.login = worker_id.getEmail();
		this.password = password;
		this.is_dressmaker = is_dressmaker;
		this.worker_id = worker_id==null?null:worker_id.getWorker_id();
	}

	public Account() {
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = -2187053659640625361L;
	@Id 
	@Column(name="login")
	private String login;
	
	@Column(name="password")
	private String password;
	
	@Column(name="is_dressmaker")
	private int is_dressmaker;
	
	private Integer worker_id;
	
	@GenericGenerator(name = "generator", strategy = "foreign", parameters= {@Parameter(value="property", name = "worker")})
			@GeneratedValue(generator = "generator")
			@Column(name = "worker_id", unique = true, nullable = true)
			public Integer getWorker_id() {
				return this.getWorker_id();
			}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + is_dressmaker;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((worker_id == null) ? 0 : worker_id.hashCode());
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
		Account other = (Account) obj;
		if (is_dressmaker != other.is_dressmaker)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (worker_id == null) {
			if (other.worker_id != null)
				return false;
		} else if (!worker_id.equals(other.worker_id))
			return false;
		return true;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIs_dressmaker() {
		return is_dressmaker;
	}

	public void setIs_dressmaker(int is_dressmaker) {
		this.is_dressmaker = is_dressmaker;
	}

}
