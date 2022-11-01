package controller;

public class Payment {
	private Integer id;
	private String description;
	private Boolean card;

	public Payment() {
	}

	public Payment(Integer id, String description, Boolean card) {
		this.id = id;
		this.description = description;
		this.card = card;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getCard() {
		return card;
	}

	public void setCard(Boolean card) {
		this.card = card;
	}

}
