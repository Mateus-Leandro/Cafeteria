package controller;

import java.util.Date;

public class Store {
	private int id;
	private String name;
	private String cnpj;
	private String street;
	private String streetNumber;
	private String district;
	private String city;
	private Date creationDate;
	
	public Store() {
	}
	
	
	public Store(int id, String name, String cnpj, String street, String streetNumber, String district, String city,
			Date creationDate) {
		super();
		this.id = id;
		this.name = name;
		this.cnpj = cnpj;
		this.street = street;
		this.streetNumber = streetNumber;
		this.district = district;
		this.city = city;
		this.creationDate = creationDate;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public int getId() {
		return id;
	}	
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Store other = (Store) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
