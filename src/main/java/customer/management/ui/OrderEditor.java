package customer.management.ui;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.internal.nodefeature.ModelList;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import customer.management.db.OrderRepository;
import customer.management.model.Orders;

import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class OrderEditor extends VerticalLayout implements KeyNotifier {
	
	private final OrderRepository repository;
	private Orders order;
	
	TextField productDescription = new TextField("Modell"); 
	TextField customerName = new TextField("Last Name");
	
	Button save = new Button("Save", VaadinIcon.CHECK.create());
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", VaadinIcon.TRASH.create());
	
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
	
	Binder<Orders> binder = new Binder<>(Orders.class);
	private ChangeHandler changeHandler;
	
	
	@Autowired
	public OrderEditor(OrderRepository repository) {
		this.repository = repository;
		
		add(productDescription, customerName, actions);
		
		binder.bindInstanceFields(this);
		
		setSpacing(true);
		
		 save.getElement().getThemeList().add("primary");
		 delete.getElement().getThemeList().add("error");
		 
		 addKeyPressListener(Key.ENTER, e -> save());
		 
		 save.addClickListener(e -> save());
		 delete.addClickListener(e -> delete());
		 cancel.addClickListener(e -> editOrders(order));
		 setVisible(false);
		 
	}
	
	void delete() {
		repository.delete(order);
		changeHandler.onChange();
	}
	
	void save() {
		repository.save(order);
		changeHandler.onChange();
	}
	public interface ChangeHandler {
		void onChange();
	}
	
	public final void editOrders(Orders o) {
		if(o == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = o.getId() != null;
		if(persisted) {
			order = repository.findById(o.getId()).get();
		}
		else {
			order = o;
		}
		cancel.setVisible(persisted);
		binder.setBean(order);
		setVisible(true);
		productDescription.focus();
	}
	
	public void setChangedHandler(ChangeHandler h) {
		changeHandler = h;
	}
	

}
