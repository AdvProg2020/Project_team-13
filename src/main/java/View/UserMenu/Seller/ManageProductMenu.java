package View.UserMenu.Seller;

import Controller.Client.ClientController;
import Models.UserAccount.Seller;
import View.Menu;

public class ManageProductMenu extends Menu {
    public ManageProductMenu(Menu parentMenu) {
        super(parentMenu);
    }
    @Override
    public void help() {
        String ManageProductMenuOptions="";
        ManageProductMenuOptions+="1.view [product Id]\n";
        ManageProductMenuOptions+="2.view buyers [product Id]\n";
        ManageProductMenuOptions+="3.edit [product Id]\n";
        System.out.println(ManageProductMenuOptions);
    }

    @Override
    public void execute() {
        Seller seller=(Seller) ClientController.getInstance().getCurrentUser();
        System.out.println(seller.viewAllProducts());
        String command;
        while(!(command=scanner.nextLine()).trim().equalsIgnoreCase("back")){
           if(command.matches("view @p\\w+")){
              System.out.println(seller.viewProduct(getTheProductIdByCommand(command,1)));
           }else if(command.matches("view buyers @p\\w+")){
               System.out.println();
           }else if(command.matches("edit @p\\w+")){
               Menu menu=new EditProductInfoMenu(this).setScanner(scanner);
               ClientController.getInstance().setCurrentMenu(menu);
               menu.execute();
           }else if(command.equals("help")){
               help();
           }else{
               System.out.println("invalid command");
           }
       }
       back();
    }

    private String getTheProductIdByCommand(String command, int num){
       String[] array=command.split("\\s");
       return array[num];
    }
    @Override
    public void showMessage(String message) {
       super.showMessage(message);
    }
    @Override
    public void printError(String error) {
       super.printError(error);
    }


}
