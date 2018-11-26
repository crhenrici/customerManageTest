package customer.management.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author cristian
 *orders class
 */
@Entity
public class Orders {

	@Id
	@GeneratedValue
	private Long id;
	private String productDescription;
	private String customerName;
	private Double price;

	public Orders() {

	}

	/**
	 * constructor
	 * @param productDescription
	 * @param customerName
	 * @param price
	 */
	public Orders(String productDescription, String customerName, Double price) {
		this.productDescription = productDescription;
		this.customerName = customerName;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Orders [id=" + id + ", productDescription=" + productDescription + ", customerName=" + customerName
				+ ", price=" + price + "]";
	}
	
	

	

}
