package View.UserMenu.Seller;

import Controller.Client.ClientController;
import View.Menu;

public class SellerMenu extends Menu {
    public SellerMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void execute(){
        String command;
        System.out.println(ClientController.getInstance().getCurrentUser().viewPersonalInfo());
        while(!(command=scanner.nextLine()).equalsIgnoreCase("back")){
            if(command.equals("view personal info")){
                System.out.println(ClientController.getInstance().getCurrentUser().viewPersonalInfo());
            }else if(command.equals("manage products")){
                Menu menu=new ManageProductMenu(this).setScanner(scanner);
                ClientController.getInstance().setCurrentMenu(menu);
                menu.execute();
            }else if(command.equals("help")){
                help();
            }else{
                System.out.println("Invalid command");
            }
        }
        back();
    }

    @Override
    public void help(){
        String sellerMenuOptions="";
        sellerMenuOptions+="view personal info\n";
        sellerMenuOptions+="view company information\n";
        sellerMenuOptions+="view Sales history\n";
        sellerMenuOptions+="view Sales History\n";
        sellerMenuOptions+="view Sales History\n";
        sellerMenuOptions+="view Sales History\n";
        sellerMenuOptions+="view Sales History\n";
        sellerMenuOptions+="view Sales History\n";
        sellerMenuOptions+="view Sales History\n";
        sellerMenuOptions+="view Sales History\n";
        System.out.println(sellerMenuOptions);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void printError(String error) {

    }

}
