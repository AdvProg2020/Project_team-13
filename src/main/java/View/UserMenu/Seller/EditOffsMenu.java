package View.UserMenu.Seller;

import Controller.Client.ClientController;
import Controller.Client.OffsController;
import Models.Offer;
import Models.Product.Product;
import Models.UserAccount.Seller;
import View.Menu;
import com.google.gson.internal.bind.util.ISO8601Utils;
import java.util.ArrayList;
import java.util.Date;

public class EditOffsMenu extends Menu {
    private Offer offer;
    private boolean back;
    public EditOffsMenu(Menu parentMenu) {
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
        String EditOffsMenusHelp="";
        EditOffsMenusHelp+="1. Edit Offer's Amount\n";
        EditOffsMenusHelp+="2. Edit Offer's MaxDiscountAmount\n";
        EditOffsMenusHelp+="3. Edit Exact Start Time\n";
        EditOffsMenusHelp+="4. Edit Offer's MaxDiscountAmount\n";
        EditOffsMenusHelp+="5. Edit Exact End Time\n";
        EditOffsMenusHelp+="6. Edit Products\n";
        EditOffsMenusHelp+="7. Help\n";
        EditOffsMenusHelp+="8. LogOut\n";
        EditOffsMenusHelp+="9. Back\n";
        System.out.println(EditOffsMenusHelp);
    }

    @Override
    public void execute() {
        ViewOffsMenu viewOffsMenu=(ViewOffsMenu)parentMenu;
        Seller seller=(Seller) ClientController.getInstance().getCurrentUser();
        if(!seller.offerExists(viewOffsMenu.getTheIdForEdit())){
            System.out.println("There Is No Offer With This Id");
            back();
            return;
        }
        offer=seller.getOfferById(viewOffsMenu.getTheIdForEdit());
        OffsController.getInstance().printOffById((Seller) ClientController.getInstance().getCurrentUser(), viewOffsMenu.getTheIdForEdit());
        String command;
        do{
            System.out.println("Enter The Feature That You Want To Edit You Can Use The Help For Your Commands: ");
            command=scanner.nextLine().trim();
            if(command.equalsIgnoreCase("Edit Offer's Amount")){
                double amount=getTheAmount();
                if(isBack()) {
                  continue;
                }
                offer.setAmount(amount);
                OffsController.getInstance().editOff(offer);
            }else if(command.equalsIgnoreCase("Edit Offer's MaxDiscountAmount")){
                double maxDiscountAmount=getTheMaxDiscountAmount();
                if(isBack()) {
                   continue;
                }
                offer.setMaxDiscountAmount(maxDiscountAmount);
                OffsController.getInstance().editOff(offer);
            }else if (command.equalsIgnoreCase("Edit Exact Start Time")){
                Date startDate=getTheStartDate(offer, null);
                if(isBack()){
                    continue;
                }
                offer.setStartTime(startDate);
                OffsController.getInstance().editOff(offer);
            }else if(command.equalsIgnoreCase("Edit Exact End Time")){
                Date endDate=getTheEndDate(offer,null);
                if(isBack()){
                    continue;
                }
                offer.setEndTime(endDate);
                OffsController.getInstance().editOff(offer);
            }else if(command.equalsIgnoreCase("Edit Products")){
                ArrayList<Product> allproducts=getEditedProducts(offer);
                if(isBack()){
                    continue;
                }
                offer.setProducts(allproducts);
//                OffsController.getInstance().editOff();
            }else if(command.equalsIgnoreCase("help")){
                help();
            }else {
                System.out.println("invalid command");
            }
        }while (!(command.equalsIgnoreCase("back")));
        back();
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    private double getTheAmount(){
        String amount;
        while(!(amount=scanner.nextLine().trim()).equalsIgnoreCase("back")){
            System.out.println("Enter The Amount: ");
            if(amount.matches("\\d+(\\.\\d+)?")){
                return Double.parseDouble(amount);
            }else{
                System.out.println("Please Enter A Correct Number");
            }
        }
        setBack(true);
        return 0;
    }
    private double getTheMaxDiscountAmount(){
        String maxDiscountAmount;
        while(!(maxDiscountAmount=scanner.nextLine().trim()).equalsIgnoreCase("back")){
            System.out.println("Enter The MaxDiscountAmount: ");
            if(maxDiscountAmount.matches("\\d+(\\.\\d+)?")){
                return Double.parseDouble(maxDiscountAmount);
            }else{
                System.out.println("Please Enter A Correct Number");
            }
        }
        setBack(true);
        return 0;
    }
    private Date getTheStartDate(Offer offer, Date endDate){
        String startDate;
        Date date=new Date();
        Date start;
        if(endDate!=null){
            while(true){
                System.out.println("You Should Change The Start Date Too To Exit From This Path");
                startDate=scanner.nextLine().trim();
                if(startDate.matches("[1-9]\\d{3}/([1-9]|(1[0-2]))/([1-9]|([1-2][0-9])|30)")){
                    start=new Date(startDate);
                    if(start.after(endDate)){
                        return start;
                    }else {
                        System.out.println("You Should Enter The Correct Time");
                    }
                }else{
                    System.out.println("You Should Enter A Correct Date");
                }
            }
        }
        while(!(startDate=scanner.nextLine().trim()).equalsIgnoreCase("back")){
            System.out.println("Enter The Start Date (yy/mm/dd): ");
            if(startDate.matches("[1-9]\\d{3}/([1-9]|(1[0-2]))/([1-9]|([1-2][0-9])|30)")){
                start=new Date(startDate);
                if(start.before(date)){
                    System.out.println("You Can't Choose The Time Before Now");
                }else if(start.after(offer.getEndTime())){
                    Date endDateTime=getTheEndDate(offer, start);
                    offer.setEndTime(endDate);
                    return start;
                }else {
                    return start;
                }
            }else{
                System.out.println("Please Enter A Correct Date");
            }
        }
        setBack(true);
        return null;
    }
    private Date getTheEndDate(Offer offer, Date start){
        String endDate;
        Date started=new Date();
        Date end;
        if(start!=null){
            while(true){
                System.out.println("You Should Change The End Date Too To Exit From This Path");
                endDate=scanner.nextLine().trim();
                if(endDate.matches("[1-9]\\d{3}/([1-9]|(1[0-2]))/([1-9]|([1-2][0-9])|30)")){
                    end=new Date(endDate);
                    if(end.after(start)){
                        return end;
                    }else {
                        System.out.println("You Should Enter The Correct Time");
                    }
                }else{
                    System.out.println("You Should Enter A Correct Date");
                }
            }
        }
        do{
            System.out.println("Enter The End Date:");
            endDate=scanner.nextLine().trim();
            if(endDate.matches("[1-9]\\d{3}/([1-9]|(1[0-2]))/([1-9]|([1-2][0-9])|30)")){
                end=new Date(endDate);
                if(end.before(started)){
                    System.out.println("You Can't Choose The Time Before Now");
                }else if(end.before(offer.getStartTime())){
                    Date startDate=getTheEndDate(offer, end);
                    offer.setStartTime(startDate);
                    return end;
                }else{
                    return end;
                }
            }else{
                System.out.println("Enter A Valid Date");
            }
        }while(!(endDate.equalsIgnoreCase("back")));
        setBack(true);
        return  null;
    }
    private ArrayList<Product> getEditedProducts(Offer offer){
        Seller seller=(Seller)ClientController.getInstance().getCurrentUser();
        String command;
        ArrayList<Product> allProducts=new ArrayList<>();
        do{
            System.out.println("1. Add Product [product Id]\n2. Remove Product [product id]");
            command=scanner.nextLine().trim();
            if(command.equals("Add Product")){
//                if(){
//                    System.out.println("The Product Does Not Exist In The List.");
//                }else{
//
//                }
            }else if(command.equals("Remove Product")){

            }else{
                System.out.println("Enter The Write Command");
            }
        }while (!(command.equalsIgnoreCase("back")));
        setBack(true);
        return null;
    }
}
