package customer.management.ui;

import org.springframework.util.StringUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import customer.management.db.CustomerRepository;
import customer.management.model.Customer;

@SpringComponent
@UIScope
@Route(value = "customers")
class MainViewCustomer extends VerticalLayout {

	private final CustomerRepository repo;
	private final CustomerEditor editor;
	final Grid<Customer> grid;
	final TextField filter;
	private final Button addNewBtn;

	public MainViewCustomer(CustomerRepository repo, CustomerEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Customer.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New Customer", VaadinIcon.PLUS.create());

		RouterLink products = new RouterLink("Products", MainViewProduct.class);
		products.add(new Icon(VaadinIcon.ARCHIVES));
		products.addClassName("main-layout__nav-item");

		RouterLink orders = new RouterLink("Orders", MainViewOrders.class);
		orders.add(new Icon(VaadinIcon.BOOK));
		orders.addClassName("main-layout__nav-item");

		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn, products, orders);
		add(actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("id", "firstName", "lastName", "isStudent");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filter by last name");

		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCustomer(e.getValue());
		});

		addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "", false)));

		editor.setChangedHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});
		listCustomers(null);
	}

	public void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		} else {
			grid.setItems(repo.findByLastName(filterText));
		}
	}

}
