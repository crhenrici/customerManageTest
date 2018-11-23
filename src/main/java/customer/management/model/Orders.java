package customer.management.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Orders {
	
	@Id
	@GeneratedValue
	private Long id;
	private String productDescription;
	private String customerName;
	
	public Orders(String productDescription, String customerName) {
		this.productDescription = productDescription;
		this.customerName = customerName;
	}
	
	public Orders() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	public String getProductDescription() {
		return productDescription;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	@Override
	public String toString() {
		return String.format("Orders[id=%d, productDescription='%s', customerName='%s']",
				id, productDescription, customerName);
	}

}
