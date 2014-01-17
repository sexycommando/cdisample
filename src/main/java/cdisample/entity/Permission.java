package cdisample.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="PERMISSIONS")
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="\"ACTION\"")
	private String action;

	private String datatype;

	private String username;

    public Permission() {
    }

	public Permission(String action, String datatype) {
		this.action = action;
		this.datatype = datatype;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDatatype() {
		return this.datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result
				+ ((datatype == null) ? 0 : datatype.hashCode());
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
		Permission other = (Permission) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (datatype == null) {
			if (other.datatype != null)
				return false;
		} else if (!datatype.equals(other.datatype))
			return false;
		return true;
	}
}