package entities;

import java.util.Date;

public class Solution {

	private int id;
	private int exercise_id;
	private int user_id;
	private Date created;
	private Date updated;
	private String description;

	public Solution() {

	}

	public int getId() {
		return id;
	}

	public int getExcercise_id() {
		return exercise_id;
	}

	public void setExcercise_id(int excercise_id) {
		this.exercise_id = excercise_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
