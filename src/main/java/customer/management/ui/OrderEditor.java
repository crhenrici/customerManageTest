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
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import customer.management.db.CustomerRepository;
import customer.management.db.OrderRepository;
import customer.management.db.ProductRepository;
import customer.management.model.Customer;
import customer.management.model.Orders;
import customer.management.model.Products;
import customer.management.service.PriceCalculationService;

/**
 * @author cristian
 *OrderEditor class extended by VerticalLayout and implements KeyNotifier
 */
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
	private ChangeHandler changeHandler;

	/**Constructor
	 * @param repository
	 * @param productRepository
	 * @param customerRepository
	 * @param priceCalculation
	 */
	@Autowired
	public OrderEditor(OrderRepository repository, ProductRepository productRepository,
			CustomerRepository customerRepository, PriceCalculationService priceCalculation) {
		this.orderRepository = repository;
		this.productRepository = productRepository;
		this.customerRepository = customerRepository;
		this.priceCalculation = priceCalculation;

		//Initialise new ComboBox for productDescription
		productDescription = new ComboBox<String>("Modell");
		productDescription.addValueChangeListener(e -> comboBoxProductListener(e.getValue()));
		DataProvider<String, Void> productDescDataProvider = DataProvider.fromCallbacks(
				query -> loadProductDescription(productRepository).stream(),
				query -> loadProductDescription(productRepository).size());
		productDescription.setDataProvider(productDescDataProvider);
		productDescription.setAllowCustomValue(false);

		//Initialise new ComboBox for customerName
		customerName = new ComboBox<String>("Name");
		customerName.addValueChangeListener(e -> comboBoxCustomerListener(e.getValue()));
		DataProvider<String, Void> customerNameDataProvider = DataProvider.fromCallbacks(
				query -> loadCustomerName(customerRepository).stream(),
				query -> loadCustomerName(customerRepository).size());
		customerName.setDataProvider(customerNameDataProvider);
		customerName.setAllowCustomValue(false);

		//bind values to their respective ComboBox
		binder.forField(productDescription).bind(Orders::getProductDescription, Orders::setProductDescription);
		binder.forField(customerName).bind(Orders::getCustomerName, Orders::setCustomerName);
		add(productDescription, customerName, actions);
		
		setSpacing(true);

		saveButton.getElement().getThemeList().add("primary");
		deleteButton.getElement().getThemeList().add("error");

		addKeyPressListener(Key.ENTER, e -> saveOrder());

		//add listener to the buttons
		saveButton.addClickListener(e -> saveOrder());
		deleteButton.addClickListener(e -> deleteOrder());
		cancelButton.addClickListener(e -> OrderEditor.this.setVisible(false));
		setVisible(false);

	}

	/**Listener for comboBox of productDescription
	 * @param prodDesc
	 */
	private void comboBoxProductListener(String prodDesc) {
		order.setProductDescription(prodDesc);
	}

	/**
	 * @param customerRepository
	 * @return list of customers
	 */
	private List<String> loadCustomerName(CustomerRepository customerRepository) {
		List<String> customerList = new ArrayList<String>();
		for (Customer customer : customerRepository.findAll()) {
			customerList.add(customer.getLastName());
		}
		return customerList;
	}

	/**
	 * @param productRepository
	 * @return list of products
	 */
	private List<String> loadProductDescription(ProductRepository productRepository) {
		List<String> productList = new ArrayList<String>();
		for (Products products : productRepository.findAll()) {
			productList.add(products.getModell());
		}
		return productList;
	}

	/** listener for comboBox of customerName
	 * @param customerName
	 */
	public void comboBoxCustomerListener(String customerName) {
		order.setCustomerName(customerName);
	}
	
	/**
	 * deletes current order
	 */
	public void deleteOrder() {
		orderRepository.delete(order);
		changeHandler.onChange();
	}

	/**
	 * edits current order
	 * @param o
	 */
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
		
		binder.setBean(order);
		cancelButton.setVisible(persisted);
		productDescription.getDataProvider().refreshAll();
		customerName.getDataProvider().refreshAll();
		setVisible(true);
		productDescription.focus();
	}

	/**
	 * saves current order
	 */
	public void saveOrder() {
		order.setPrice(priceCalculation.calculatePrice(order.getProductDescription(), order.getCustomerName()));
		orderRepository.save(order);
		changeHandler.onChange();
	}

	/**
	 * @param h
	 */
	public void setChangedHandler(ChangeHandler h) {
		changeHandler = h;
	}

}
