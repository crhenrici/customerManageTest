package customer.management.ui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import customer.management.db.CustomerRepository;
import customer.management.db.OrderRepository;
import customer.management.db.ProductRepository;
import customer.management.model.Customer;
import customer.management.model.Orders;
import customer.management.model.Products;
import customer.management.service.PriceCalculationService;

@SpringComponent
@UIScope
public class OrderEditor extends VerticalLayout implements KeyNotifier {

	public interface ChangeHandler {
		void onChange();
	}

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final CustomerRepository customerRepository;
	private final PriceCalculationService priceCalculation;

	private Orders order;
	private ComboBox<String> productDescription;
	private ComboBox<String> customerName;
	
	private Button saveButton = new Button("Save", VaadinIcon.CHECK.create());
	private Button cancelButton = new Button("Cancel");

	private Button deleteButton = new Button("Delete", VaadinIcon.TRASH.create());

	private HorizontalLayout actions = new HorizontalLayout(saveButton, cancelButton, deleteButton);
	private Binder<Orders> binder = new Binder<>(Orders.class);
	private boolean loading = false;
	private ChangeHandler changeHandler;

	@Autowired
	public OrderEditor(OrderRepository repository, ProductRepository productRepository,
			CustomerRepository customerRepository, PriceCalculationService priceCalculation) {
		this.orderRepository = repository;
		this.productRepository = productRepository;
		this.customerRepository = customerRepository;
		this.priceCalculation = priceCalculation;

		productDescription = new ComboBox<String>("Modell",loadProductDescription(productRepository));
		productDescription.addValueChangeListener(e -> comboBoxProductListener(e.getValue()));
		productDescription.setAllowCustomValue(false);

		customerName = new ComboBox<String>("Name",loadCustomerName(customerRepository));
		customerName.addValueChangeListener(e -> comboBoxCustomerListener(e.getValue()));
		customerName.setAllowCustomValue(false);

		binder.forField(productDescription).bind(Orders::getProductDescription, Orders::setProductDescription);
		binder.forField(customerName).bind(Orders::getCustomerName, Orders::setCustomerName);
		add(productDescription, customerName, actions);
		
		setSpacing(true);

		saveButton.getElement().getThemeList().add("primary");
		deleteButton.getElement().getThemeList().add("error");

		addKeyPressListener(Key.ENTER, e -> saveOrder());

		saveButton.addClickListener(e -> saveOrder());
		deleteButton.addClickListener(e -> deleteOrder());
		cancelButton.addClickListener(e -> OrderEditor.this.setVisible(false));
		setVisible(false);

	}

	private void comboBoxProductListener(String prodDesc) {
		if (loading) return;
		order.setProductDescription(prodDesc);
	}

	private List<String> loadCustomerName(CustomerRepository customerRepository) {
		loading = true;
		List<String> customerList = new ArrayList<String>();
		for (Customer customer : customerRepository.findAll()) {
			customerList.add(customer.getLastName());
		}
		loading = false;
		return customerList;
	}

	private List<String> loadProductDescription(ProductRepository productRepository) {
		
		loading = true;
		List<String> productList = new ArrayList<String>();
		for (Products products : productRepository.findAll()) {
			productList.add(products.getModell());
		}
		loading = false;
		return productList;
	}

	public void comboBoxCustomerListener(String customerName) {
		if (loading) return;
		order.setCustomerName(customerName);
	}
	
	public void deleteOrder() {
		orderRepository.delete(order);
		changeHandler.onChange();
	}

	public final void editOrders(Orders o) {
		if (o == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = o.getId() != null;
		if (persisted) {
			order = orderRepository.findById(o.getId()).get();
		} else {
			order = o;
		}
		productDescription.setItems(loadProductDescription(productRepository));
		customerName.setItems(loadCustomerName(customerRepository));
		binder.setBean(order);
		cancelButton.setVisible(persisted);
		setVisible(true);
		productDescription.focus();
	}

	public void saveOrder() {
		order.setPrice(priceCalculation.calculatePrice(order.getProductDescription(), order.getCustomerName()));
		orderRepository.save(order);
		changeHandler.onChange();
	}

	public void setChangedHandler(ChangeHandler h) {
		changeHandler = h;
	}

}
