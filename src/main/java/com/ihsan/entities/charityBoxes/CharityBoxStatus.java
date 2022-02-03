package com.ihsan.entities.charityBoxes;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "FND_LOOKUP_VALUES")
@Where(clause = "LOOKUP_TYPE='BOXES_STATUS'")
public class CharityBoxStatus {

	@Id
	@Column(name = "LOOKUP_CODE")
	private String id;

	@Column(name = "ARABIC_VALUE")
	private String name;

	@Column(name = "LOOKUP_TYPE")
	private String type = "BOXES_STATUS";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CharityBoxStatus() {

	}

	public CharityBoxStatus(String id) {
		this.id = id;
		this.type = "BOXES_STATUS";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharityBoxStatus other = (CharityBoxStatus) obj;
		return Objects.equals(id, other.id) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "CharityBoxStatus [id=" + id + ", name=" + name + ", type=" + type + "]";
	}

}
