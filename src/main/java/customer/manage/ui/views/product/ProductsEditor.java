package customer.manage.ui.views.product;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import customer.manage.db.ProductRepository;
import customer.manage.db.Products;

import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
@Route(value = "products")
public class ProductsEditor extends VerticalLayout implements KeyNotifier {

	private final ProductRepository repo;
	
	private Products product;
	
	TextField modell = new TextField("Modell");
	TextField manufacteur = new TextField("Manufacteur");
	
	Button save = new Button("Save", VaadinIcon.CHECK.create());
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
	
	Binder<Products> binder = new Binder<>(Products.class);
	private ChangeHandler changeHandler;
	
	@Autowired
	public ProductsEditor(ProductRepository repo) {
		this.repo = repo;
		
		add(modell, manufacteur);
		
		binder.bindInstanceFields(this);
		
		setSpacing(true);
		
		save.getElement().getThemeList().add("primary");
		delete.getElement().getThemeList().add("error");
		
		addKeyPressListener(Key.ENTER, e -> save());
		
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		cancel.addClickListener(e -> editProduct(product));
		setVisible(true);
	}
	
	void delete() {
		repo.delete(product);
		changeHandler.onChange();
	}
	
	void save() {
		repo.save(product);
		changeHandler.onChange();
	}
	
	public interface ChangeHandler {
		void onChange();
	}
	
	public final void editProduct(Products p) {
		if(p == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = p.getId() != null;
		if(persisted) {
			product = repo.findById(p.getId()).get();
		}
		else {
			product = p;
		}
		cancel.setVisible(persisted);
		binder.setBean(product);
		setVisible(true);
		modell.focus();
	}
	
	public void setChangedHandler(ChangeHandler h) {
		changeHandler = h;
	}
	
}
