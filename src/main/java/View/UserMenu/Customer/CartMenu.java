package View.UserMenu.Customer;

import Controller.Client.ClientController;
import Models.UserAccount.Customer;
import View.Menu;
import View.ProductsAndOffsMenus.ProductMenu;

public class CartMenu extends Menu {
    public CartMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {
        String userMenuOptions = "";
        userMenuOptions += "1.Show Products\n";
        userMenuOptions += "2.View Product [productID]\n";
        userMenuOptions += "3.Increase Product [productID]\n";
        userMenuOptions += "4.Decrease Product [productID]\n";
        userMenuOptions += "5.Show Total Price\n";
        userMenuOptions += "6.Purchase\n";
        userMenuOptions += "7.Help\n";
        userMenuOptions += "4.Back\n";
        userMenuOptions += "5.LogOut\n";
        System.out.println(userMenuOptions);

    }

    @Override
    public void execute() {
        Customer customer = (Customer) ClientController.getInstance().getCurrentUser();
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("Show Products")) {
                customer.getCart().showProducts();
            } else if (command.matches("view product \\S+")) {
                if (command.matches("view product @p\\d+")) {
                    ClientController.getInstance().setCurrentProduct(customer.getCart().findProductWithID(command.split("\\s")[2]));
                    if(ClientController.getInstance().getCurrentProduct()!=null) {
                        Menu menu = new ProductMenu(this).setScanner(scanner);
                        ClientController.getInstance().setCurrentMenu(menu);
                        menu.execute();
                    }else {
                        printError("there is no product with this ID in your Cart");
                    }
                } else {
                    printError("this ID isn't a productID");
                }
            } else if (command.matches("increase \\S+")) {
                if (command.matches("increase @p\\d+")) {
                    System.out.println("How many do you want to increase it?");
                    int count=scanner.nextInt();
                    customer.getCart().changeCountOfProduct(command.split("\\s")[1],count);
                }else{
                    printError("this ID isn't a productID");
                }
            }else if (command.matches("decrease \\S+")) {
                if (command.matches("decrease @p\\d+")) {
                    System.out.println("How many do you want to decrease it?");
                    int count=scanner.nextInt();
                    customer.getCart().changeCountOfProduct(command.split("\\s")[1],-count);
                }else{
                    printError("this ID isn't a productID");
                }
            }else if (command.equalsIgnoreCase("show total price")) {
                System.out.println(((Customer) ClientController.getInstance().getCurrentUser()).getCart().getTotalPrice());
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else if (command.equalsIgnoreCase("logout")) {
                ClientController.getInstance().setCurrentUser(null);
                System.out.println("You Logged out!!");
                parentMenu.execute();
            } else {
                System.out.println("Invalid Command");
            }
        }
        back();
    }

}
