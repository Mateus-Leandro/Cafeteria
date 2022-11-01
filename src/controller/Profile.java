package controller;

public class Profile {
	private Integer id;
	private String description;

	public Profile() {
	}

	public Profile(Integer id, String description) {
		this.id = id;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
	
}
