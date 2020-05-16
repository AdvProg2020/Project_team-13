package View.ProductsAndOffsMenus;

import Controller.Client.OffsController;
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
