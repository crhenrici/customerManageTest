/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package customer.manage.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

import customer.manage.ui.views.customer.CustomersList;
import customer.manage.ui.views.product.ProductsEditor;

/**
 * The main layout contains the header with the navigation buttons, and the
 * child views below that.
 */
@Route("")
public class MainLayout extends Div implements RouterLayout {

	public MainLayout() {

		H2 title = new H2("Customer Verwaltung");
		title.addClassName("main-layout__title");

		RouterLink customers = new RouterLink("Customers", CustomersList.class);
		customers.add(new Icon(VaadinIcon.LIST), new Text("Customers"));
		customers.addClassName("main-layout__nav-item");
		// Only show as active for the exact URL, but not for sub paths

		RouterLink products = new RouterLink(null, ProductsEditor.class);
		products.add(new Icon(VaadinIcon.ARCHIVES), new Text("Products"));
		products.addClassName("main-layout__nav-item");

		Div navigation = new Div(customers, products);
		navigation.addClassName("main-layout__nav");

		Div header = new Div(title, navigation);
		header.addClassName("main-layout__header");
		add(header);

		addClassName("main-layout");
	}

}
