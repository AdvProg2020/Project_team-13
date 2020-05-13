package View.UserMenu.Seller;

import Controller.Client.ClientController;
import Controller.Client.OffsController;
import Models.UserAccount.Seller;
import View.Menu;
import com.google.gson.Gson;

public class ViewOffsMenu extends Menu {
    private String commandForEdit;
    public ViewOffsMenu(Menu parentMenu) {
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
       String ViewOffsMenuHelp="";
       ViewOffsMenuHelp+="1. view [offId]\n";
       ViewOffsMenuHelp+="2. edit [offId]\n";
       ViewOffsMenuHelp+="3. add off\n";
       ViewOffsMenuHelp+="4. help\n";
       ViewOffsMenuHelp+="5. back\n";
       System.out.println(ViewOffsMenuHelp);
    }

    @Override
    public void execute() {
       String command;
       OffsController.getInstance().printAllOffs((Seller)ClientController.getInstance().getCurrentUser());
       while(!(command=scanner.nextLine().trim()).equalsIgnoreCase("back")){
           if(command.matches("view @o\\w+")){
               OffsController.getInstance().printOffById((Seller)ClientController.getInstance().getCurrentUser(), getTheOffId(command));
           }else if(command.matches("edit @o\\w+")){
               commandForEdit=command;
               Menu menu=new EditOffsMenu(this).setScanner(scanner);
               ClientController.getInstance().setCurrentMenu(menu);
               menu.execute();
           }else if(command.equalsIgnoreCase("add off")){
               Menu menu=new AddOffsMenu(this).setScanner(scanner);
               ClientController.getInstance().setCurrentMenu(menu);
               menu.execute();
           }else if(command.equalsIgnoreCase("help")){
               help();
           }else{
               System.out.println("invalid command");
           }
       }
       back();
    }
    private String getTheOffId(String command){
        return command.substring(5,command.length());
    }

    public String getTheIdForEdit(){
        return commandForEdit;
    }
}
