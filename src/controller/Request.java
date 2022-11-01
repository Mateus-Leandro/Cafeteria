package controller;

import java.util.Date;

public class Request {
	private Integer id;
	private User user;
	private Payment payment;
	private Double amount;
	private Boolean finished;
	private Date creationDate;

	public Request() {

	}

	public Request(Integer id, User user, Payment payment, Double amount, Boolean finished, Date creationDate) {
		this.id = id;
		this.user = user;
		this.payment = payment;
		this.amount = amount;
		this.finished = finished;
		this.creationDate = creationDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
