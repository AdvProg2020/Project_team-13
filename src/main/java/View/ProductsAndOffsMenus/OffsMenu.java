package View.ProductsAndOffsMenus;

import Controller.Client.ClientController;
import Controller.Client.OffsController;
import Controller.Client.ProductController;
import View.Menu;
import View.UserMenu.Manager.ManagerMenu;

public class OffsMenu extends Menu {
    public OffsMenu(Menu parentMenu) {
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
       String offsMenuHelp="";
       offsMenuHelp+="1.show product [productId]";
       offsMenuHelp+="2.";
    }

    @Override
    public void execute() {
       OffsController.getInstance().showAllOffs();
       String command;
       while (!(command=scanner.nextLine().trim()).equalsIgnoreCase("back")){
           if(command.matches("show product @p\\d+")){
               if(OffsController.getInstance().isThereAnyProductWithThisId(command.substring(13))){
                   System.out.println("There Isn't Any Product With This Id In Offers.");
               }else{
                   Menu menu=new ProductMenu(this).setScanner(scanner);
                   ClientController.getInstance().setCurrentMenu(menu);
                   ClientController.getInstance().setCurrentProduct(OffsController.getInstance().getTheProductById(command.substring(13)));
                   menu.execute();
               }
//           }else if(){
//
//           }else if(){
//
//           }else{
//               System.out.println("invalid command");
           }
       }
       back();
    }
}
