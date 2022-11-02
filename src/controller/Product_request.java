package controller;

public class Product_request extends Product {
	private Double theAmount;
	private Double unitPrice;
	private Double totalPrice;

	public Product_request() {
	}

	public Product_request(Integer id, String name, String barCode, Double price, Double theAmount, Double unitPrice,
			Double totalPrice) {
		super(id, name, barCode);
		this.theAmount = theAmount;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
	}

	public Double getTheAmount() {
		return theAmount;
	}

	public void setTheAmount(Double theAmount) {
		this.theAmount = theAmount;
	}
	
	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
