package View.ProductsAndOffsMenus;

import Controller.Client.ClientController;
import Controller.Client.ProductController;
import View.Menu;
import com.sun.source.tree.PackageTree;

public class ProductsMenu extends Menu {
    public ProductsMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {

    }

    @Override
    public void execute() {

    }

    public void showAllProducts() {
        ProductController.getInstance().getAllProductsFromServer();
//        ProductController.getInstance()
    }
}
