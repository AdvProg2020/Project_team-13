package View.UserMenu.Manager;

import Controller.Client.ClientController;
import Controller.Client.ProductController;
import View.Menu;

public class ManageProductsMenu extends Menu {
    public ManageProductsMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void printError(String error) {
        super.printError(error);
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
    }

    @Override
    public void help() {
       String ManageProductMenuHelp="";
       ManageProductMenuHelp+="1. remove [productId]";
       ManageProductMenuHelp+="2. help";
       ManageProductMenuHelp+="3. back";
       System.out.println(ManageProductMenuHelp);
    }

    @Override
    public void execute() {
      String command;
      ProductController.getInstance().getAllProductsFromServer();
      while(!(command=scanner.nextLine().trim()).equalsIgnoreCase("back")){
          if(command.matches("remove @p\\w+")){
             ProductController.getInstance().removeProductForManager(getTheProductId(command));
          }else if(command.equals("help")){
             help();
          }else{
             System.out.println("invalid command");
          }
      }
      back();
    }

    private String getTheProductId(String command){
        return command.substring(7,command.length());
    }
}
