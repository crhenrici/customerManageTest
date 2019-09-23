package customer.manage.db;

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
	
	public Products(String modell, String manufacteur ) {
		this.modell = modell;
		this.manufacteur = manufacteur;
	}
	
	public Products() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public String getModell() {
		return modell;
	}
	
	public String getManufacteur() {
		return manufacteur;
	}
	
	public void setModell(String modell) {
		this.modell = modell;
	}
	
	public void setManufacteur(String manufacteur) {
		this.manufacteur = manufacteur;
	}
	
	@Override
	public String toString() {
		return String.format("Products[id=%d, model='%s', manufacteur='%s']", id, modell, manufacteur);
	}

}
