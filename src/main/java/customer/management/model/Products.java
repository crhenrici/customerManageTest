package customer.management.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Products {

	@Id
	@GeneratedValue
	private Long id;
	private String modell;
	private String manufacteur;
	private Double price;

	public Products() {

	}

	public Products(String modell, String manufacteur, Double price) {
		this.modell = modell;
		this.manufacteur = manufacteur;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public String getManufacteur() {
		return manufacteur;
	}

	public String getModell() {
		return modell;
	}

	public void setManufacteur(String manufacteur) {
		this.manufacteur = manufacteur;
	}

	public void setModell(String modell) {
		this.modell = modell;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Products [id=" + id + ", modell=" + modell + ", manufacteur=" + manufacteur + ", price=" + price + "]";
	}

	

}
