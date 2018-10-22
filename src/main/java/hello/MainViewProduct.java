package hello;

import org.springframework.util.StringUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route
class MainViewProduct {
	
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
		
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		actions.add(grid, editor);
		
		grid.setHeight("300px");
		grid.setColumns("id", "modell", "manufacteur");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
		
		filter.setPlaceholder("Filter by Modell");
		
		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listProducts(e.getValue()));
		
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editProduct(e.getValue());
		});
		
		addNewBtn.addClickListener(e -> editor.editProduct(new Products("", "")));
		
		editor.setChangedHandler(() -> {
			editor.setVisible(false);
			listProducts(filter.getValue());
		});
		listProducts(null);
	}
	
	void listProducts(String filterText) {
		if(StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			grid.setItems(repo.findByModell(filterText));
		}
	}

}
