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

    }

    @Override
    public void execute() {
        Seller seller=(Seller) ClientController.getInstance().getCurrentUser();
        System.out.println(seller.viewAllProducts());


        String command;
        while(!(command=scanner.nextLine()).trim().equalsIgnoreCase("back")){
           if(command.matches("view @p\\w+")){

           }else if(command.matches("view buyers @p\\w+")){

           }else if(command.matches("edit @p\\w+")){

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
       execute();
    }
    @Override
    public void printError(String error) {
       super.printError(error);
       execute();
    }


}
