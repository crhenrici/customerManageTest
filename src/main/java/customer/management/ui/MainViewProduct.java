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

import customer.management.db.ProductRepository;
import customer.management.model.Products;

@SpringComponent
@UIScope
@Route(value = "products")
class MainViewProduct extends VerticalLayout {

	private final ProductRepository repo;
	private final ProductsEditor editor;
	final Grid<Products> grid;
	final TextField filter;
	private final Button addNewBtn;

	public MainViewProduct(ProductRepository repo, ProductsEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Products.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New Product", VaadinIcon.PLUS.create());

		RouterLink customers = new RouterLink("Customer", MainViewCustomer.class);
		customers.add(new Icon(VaadinIcon.LIST));
		customers.addClassName("main-layout__nav-item");

		RouterLink orders = new RouterLink("Orders", MainViewOrders.class);
		orders.add(new Icon(VaadinIcon.BOOK));
		orders.addClassName("main-layout__nav-item");

		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn, customers, orders);
		add(actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("id", "modell", "manufacteur", "price");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filter by Modell");

		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listProducts(e.getValue()));

		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editProduct(e.getValue());
		});

		addNewBtn.addClickListener(e -> editor.editProduct(new Products("", "", 0.0)));

		editor.setChangedHandler(() -> {
			editor.setVisible(false);
			listProducts(filter.getValue());
		});
		listProducts(null);
	}

	void listProducts(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		} else {
			grid.setItems(repo.findByModell(filterText));
		}
	}

}
