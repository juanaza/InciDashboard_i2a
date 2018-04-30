package com.uniovi.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import com.uniovi.entities.types.OperatorKind;

@Document(collection = "operators")
public class Operator {
	@Id
	private ObjectId id;
	
	private String email;
	private String password;
	private OperatorKind kind;
	
	private String role;
	public boolean mapAccess;
	public boolean chartAccess;
	public boolean modifyAccess;
	
	public Operator() {
		
	}

	public Operator(String email, String password) {
		super();
		setEmail(email);
		setPassword(password);
	}
	
	public Operator(String email, String password, OperatorKind kind, String role) {
		this(email,password);
		setKind(kind);
		setRole(role);		
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public boolean isAdmin() {
		return this.role.equals("ROLE_ADMIN");
	}
	
	public void modifyOperatorRole(boolean asAdmin) {
		if(asAdmin) {
			setRole("ROLE_ADMIN");
		}
		else {
			setRole("ROLE_OPERATOR");
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public OperatorKind getKind() {
		return kind;
	}

	public void setKind(OperatorKind kind) {
		this.kind = kind;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public boolean hasMapAccess() {
		return mapAccess;
	}
	
	public void setMapAccess(boolean mapAccess) {
		this.mapAccess = mapAccess;
	}
	
	public boolean hasChartAccess() {
		return chartAccess;
	}
	
	public void setChartAccess(boolean chartAccess) {
		this.chartAccess = chartAccess;
	}
	
	public boolean hasModifyAccess() {
		return modifyAccess;
	}
	
	public void setModifyAccess(boolean modifyAccess) {
		this.modifyAccess = modifyAccess;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operator other = (Operator) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Operator [id=" + id + ", email=" + email + ", password=" + password + ", kind=" + kind + ", role="
				+ role + ", mapAccess=" + mapAccess + ", chartAccess=" + chartAccess + ", modifyAccess=" + modifyAccess
				+ "]";
	}
	
}
