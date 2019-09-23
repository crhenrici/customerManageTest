package customer.manage.ui.views.customer;

import org.springframework.util.StringUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import customer.manage.db.Customer;
import customer.manage.db.CustomerRepository;

@SpringComponent
@UIScope
@Route(value = "customers")
public  class CustomersList  extends VerticalLayout {
	
	private final CustomerRepository repo;
	private final CustomerEditor editor;
	final Grid<Customer> grid;
	final TextField filter;
	private final Button addNewBtn;
	
	
	public CustomersList(CustomerRepository repo, CustomerEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Customer.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New Customer", VaadinIcon.PLUS.create());
		Tabs tabs =  new Tabs();
		Tab tab1 = new Tab("Customers");
		Tab tab2 = new Tab("Products");
		tabs.add(tab1, tab2);
		
		
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, editor);
		
		grid.setHeight("300px");
		grid.setColumns("id", "firstName", "lastName");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
		
		filter.setPlaceholder("Filter by last name");
		
		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));
		
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCustomer(e.getValue());
		});
		
		addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));
		
		editor.setChangedHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});
		
		listCustomers(null);
	}
	
//	private void listCustomers() {
//		grid.setItems(repo.findAll());
//	}
	
	void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
		}
	}

}
