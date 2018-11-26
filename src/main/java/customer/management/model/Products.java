package customer.management.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author cristian
 *Product class
 */
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

	/**
	 * constructor
	 * @param modell
	 * @param manufacteur
	 * @param price
	 */
	public Products(String modell, String manufacteur, Double price) {
		this.modell = modell;
		this.manufacteur = manufacteur;
		this.price = price;
	}

	/**
	 * @return id of product
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return manufacteur of product
	 */
	public String getManufacteur() {
		return manufacteur;
	}

	/**
	 * @return modell of product
	 */
	public String getModell() {
		return modell;
	}

	/**
	 * sets manufacteur of product
	 * @param manufacteur
	 */
	public void setManufacteur(String manufacteur) {
		this.manufacteur = manufacteur;
	}

	/**
	 * sets modell of product
	 * @param modell
	 */
	public void setModell(String modell) {
		this.modell = modell;
	}
	
	/**
	 * @return price of product
	 */
	public Double getPrice() {
		return price;
	}
	
	/**
	 * sets price of product
	 * @param price
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Products [id=" + id + ", modell=" + modell + ", manufacteur=" + manufacteur + ", price=" + price + "]";
	}

	

}
