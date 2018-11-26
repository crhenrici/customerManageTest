package customer.management.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import customer.management.db.ProductRepository;
import customer.management.model.Products;

/**
 * @author cristian
 *ProductsEditor class extended by VerticalLayout and implements KeyNotifier
 */
@SpringComponent
@UIScope
public class ProductsEditor extends VerticalLayout implements KeyNotifier {

	public interface ChangeHandler {
		void onChange();
	}

	private final ProductRepository repo;

	private Products product;
	private TextField modell = new TextField("Modell");
	private TextField manufacteur = new TextField("Manufacteur");
	private TextField price = new TextField("Price");
	
	private Button saveButton = new Button("Save", VaadinIcon.CHECK.create());
	private Button cancelButton = new Button("Cancel");
	private Button deleteButton = new Button("Delete", VaadinIcon.TRASH.create());

	private HorizontalLayout actions = new HorizontalLayout(saveButton, cancelButton, deleteButton);
	private Binder<Products> binder = new Binder<>(Products.class);

	private ChangeHandler changeHandler;

	/**
	 * constructor
	 * @param repo
	 */
	@Autowired
	public ProductsEditor(ProductRepository repo) {
		this.repo = repo;

		add(actions, modell, manufacteur, price);

		//convert value to double and bind the values to their respective fields
		binder.forField(price)
		.withConverter(
		        new StringToDoubleConverter("Must enter a number"))
		.bind(Products::getPrice, Products::setPrice);
		binder.forField(modell).bind(Products::getModell, Products::setModell);
		binder.forField(manufacteur).bind(Products::getManufacteur, Products::setManufacteur);

		setSpacing(true);

		saveButton.getElement().getThemeList().add("primary");
		deleteButton.getElement().getThemeList().add("error");

		addKeyPressListener(Key.ENTER, e -> saveProduct());

		//add listener to buttons
		saveButton.addClickListener(e -> saveProduct());
		deleteButton.addClickListener(e -> deleteProduct());
		cancelButton.addClickListener(e -> ProductsEditor.this.setVisible(false));
		setVisible(false);
	}

	/**
	 * deletes current product
	 */
	public void deleteProduct() {
		repo.delete(product);
		changeHandler.onChange();
	}

	/**
	 * edits current product
	 * @param p
	 */
	public final void editProduct(Products p) {
		if (p == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = p.getId() != null;
		if (persisted) {
			product = repo.findById(p.getId()).get();
		} else {
			product = p;
		}
		cancelButton.setVisible(persisted);
		binder.setBean(product);
		setVisible(true);
		modell.focus();
	}

	/**
	 * saves current product
	 */
	public void saveProduct() {
		repo.save(product);
		changeHandler.onChange();
	}

	public void setChangedHandler(ChangeHandler h) {
		changeHandler = h;
	}

}
