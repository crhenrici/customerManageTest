package customer.management.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import customer.management.db.CustomerRepository;
import customer.management.model.Customer;

/**
 * @author cristian
 *CustomerEditor class extended by VerticalLayout and implements KeyNotifier
 */
@SpringComponent
@UIScope
public class CustomerEditor extends VerticalLayout implements KeyNotifier {

	public interface ChangeHandler {
		void onChange();
	}

	private final CustomerRepository repository;

	private Customer customer;
	private TextField firstName = new TextField("First name");
	private TextField lastName = new TextField("Last name");
	private Checkbox isStudent = new Checkbox("Student");
	
	private Button saveButton = new Button("Save", VaadinIcon.CHECK.create());
	private Button cancelButton = new Button("Cancel");
	private Button deleteButton = new Button("Delete", VaadinIcon.TRASH.create());

	private HorizontalLayout actions = new HorizontalLayout(saveButton, cancelButton, deleteButton);
	private Binder<Customer> binder = new Binder<>(Customer.class);

	private ChangeHandler changeHandler;

	/**
	 * @param repository
	 * constructor
	 */
	@Autowired
	public CustomerEditor(CustomerRepository repository) {
		this.repository = repository;

		add(firstName, lastName, isStudent, actions);

		//bind values to their respective fields
		binder.bindInstanceFields(this);

		setSpacing(true);

		saveButton.getElement().getThemeList().add("primary");
		deleteButton.getElement().getThemeList().add("error");

		addKeyPressListener(Key.ENTER, e -> saveCustomer());

		//add listeners to button
		saveButton.addClickListener(e -> saveCustomer());
		deleteButton.addClickListener(e -> deleteCustomer());
		cancelButton.addClickListener(e -> CustomerEditor.this.setVisible(false));
		setVisible(false);

	}

	/**
	 * deletes current customer
	 */
	public void deleteCustomer() {
		repository.delete(customer);
		changeHandler.onChange();
	}

	/**
	 * @param c
	 * edits current customer
	 */
	public final void editCustomer(Customer c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			customer = repository.findById(c.getId()).get();
		} else {
			customer = c;
		}
		cancelButton.setVisible(persisted);

		binder.setBean(customer);

		setVisible(true);

		firstName.focus();
	}

	/**
	 * saves current customer
	 */
	public void saveCustomer() {
		repository.save(customer);
		changeHandler.onChange();
	}

	public void setChangedHandler(ChangeHandler h) {
		changeHandler = h;
	}
}
