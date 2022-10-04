package controller;

import java.util.Date;

public class Product {
	private Integer id;
	private String name;
	private String barCode;
	private Double price;
	private Integer inventory;
	private Date manufacturerDate;
	private Date validationDate;

	public Product() {
	}

	public Product(Integer id, String name, String barCode, Double price, Integer inventory, Date manufacturerDate, Date validationDate) {
		super();
		this.id = id;
		this.name = name;
		this.barCode = barCode;
		this.price = price;
		this.inventory = inventory;
		this.manufacturerDate = manufacturerDate;
		this.validationDate = validationDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Date getManufacturerDate() {
		return manufacturerDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
