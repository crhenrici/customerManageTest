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

import customer.management.db.OrderRepository;
import customer.management.model.Orders;

/**
 * @author cristian
 *MainViewOrders class extended by VerticalLayout
 */
@SpringComponent
@UIScope
@Route(value = "orders")
class MainViewOrders extends VerticalLayout {

	private final OrderRepository repo;
	private final OrderEditor editor;
	final Grid<Orders> grid;
	final TextField filter;
	private final Button addNewOrderBtn;

	/**
	 * constructor
	 * @param repo
	 * @param editor
	 */
	public MainViewOrders(OrderRepository repo, OrderEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Orders.class);
		this.filter = new TextField();
		this.addNewOrderBtn = new Button("New Order", VaadinIcon.PLUS.create());

		//RouterLink for MainViewProduct
		RouterLink products = new RouterLink("Products", MainViewProduct.class);
		products.add(new Icon(VaadinIcon.ARCHIVES));
		products.addClassName("main-layout__nav-item");

		//RouterLink for MainViewCustomer
		RouterLink customers = new RouterLink("Customers", MainViewCustomer.class);
		customers.add(new Icon(VaadinIcon.LIST));
		customers.addClassName("main-layout__nav-item");

		//add elements to HorizontalLayout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewOrderBtn, products, customers);
		add(actions, grid, editor);

		//create grid columns
		grid.setHeight("300px");
		grid.setColumns("id", "productDescription", "customerName", "price");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("FilterByCustomer");

		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listOrders(e.getValue()));

		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editOrders(e.getValue());
		});

		//add a new order
		addNewOrderBtn.addClickListener(e -> editor.editOrders(new Orders(null, null, 0.0)));

		//changes editor if filtered
		editor.setChangedHandler(() -> {
			editor.setVisible(false);
			listOrders(filter.getValue());
		});
		listOrders(null);
	}

	//filters list off input
	void listOrders(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		} else {
			grid.setItems(repo.findByProductDescription(filterText));
		}
	}

}
