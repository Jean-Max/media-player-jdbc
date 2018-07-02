package com.viseo.model;

import java.util.Objects;

public class Music {

	private int id;
	private String name;
	private String category;

	public Music(int id, String name, String category) {
		this.id = id;
		this.name = name;
		this.category = category;
	}

	public Music() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Music [id=" + id + ", name=" + name + ", category=" + category + "]";
	}

	@Override
	public boolean equals (Object obj) {
		if (obj == null) return false;
		if( ! (obj instanceof Music) ) return false;
		Music other = (Music) obj;

		return Objects.equals(this.name, other.name) &&
				Objects.equals(this.category, other.category);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id + name + category);
	}

}