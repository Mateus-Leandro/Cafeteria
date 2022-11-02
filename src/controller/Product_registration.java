package controller;

import java.util.Date;

public class Product_registration extends Product {
	private Double price;
	private Integer inventory;
	private Date manufacturerDate;
	private Date validationDate;

	public Product_registration() {
	}

	public Product_registration(Integer id, String name, String barCode, Double price, Integer inventory,
			Date manufacturerDate, Date validationDate) {
		super(id, name, barCode);
		this.price = price;
		this.inventory = inventory;
		this.manufacturerDate = manufacturerDate;
		this.validationDate = validationDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getManufacturerDate() {
		return manufacturerDate;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public void setManufacturerDate(Date manufacturerDate) {
		this.manufacturerDate = manufacturerDate;
	}

	public Date getValidationDate() {
		return validationDate;
	}

	public void setValidationDate(Date validationDate) {
		this.validationDate = validationDate;
	}
}
