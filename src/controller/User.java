package controller;

public class User {
	private Integer id;
	private Profile profile;
	private String name;
	private String zipCode;
	private String telephone;
	private String cpf;

	public User() {
		
	}

	public User(Integer id, Profile profile, String name, String zipCode, String telephone, String cpf) {
		this.id = id;
		this.profile = profile;
		this.name = name;
		this.zipCode = zipCode;
		this.telephone = telephone;
		this.cpf = cpf;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
