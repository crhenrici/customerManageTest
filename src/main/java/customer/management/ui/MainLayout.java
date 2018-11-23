package customer.management.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

@Route("")
public class MainLayout extends Div implements RouterLayout {
	
	public MainLayout() {
		
	H2 title = new H2("Customer Verwaltung");
	title.addClassName("main-layout__title");

	RouterLink customers = new RouterLink("Customers", MainViewCustomer.class);
	customers.add(new Icon(VaadinIcon.LIST));
	customers.addClassName("main-layout__nav-item");
	
	RouterLink products = new RouterLink("Products", MainViewProduct.class);
	products.add(new Icon(VaadinIcon.ARCHIVES));
	products.addClassName("main-layout__nav-item");
	
	RouterLink orders = new RouterLink("Orders", MainViewOrders.class);
	orders.add(new Icon(VaadinIcon.BOOK));
	orders.addClassName("main-layout__nav-item");
	
	Div navigation = new Div(customers, products, orders);
	navigation.addClassName("main-layout__nav");
	
	Div header = new Div(title, navigation);
	header.addClassName("main-layout__header");
	add(header);
	
	addClassName("main-layout");
	
	}

}
