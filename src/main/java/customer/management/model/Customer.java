package customer.management.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author cristian
 *Customer class
 */
@Entity
public class Customer {

	@Id
	@GeneratedValue
	private Long id;
	private String firstName;
	private String lastName;
	private Boolean isStudent;

	protected Customer() {

	}

	/**
	 * constructor
	 * @param firstName
	 * @param lastName
	 * @param isStudent
	 */
	public Customer(String firstName, String lastName, Boolean isStudent) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.isStudent = isStudent;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getIsStudent() {
		return isStudent;
	}

	public void setIsStudent(Boolean isStudent) {
		this.isStudent = isStudent;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", isStudent=" + isStudent
				+ "]";
	}

	

}
