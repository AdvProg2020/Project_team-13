package View.UserMenu.Manager;

import Controller.Client.ClientController;
import Controller.Client.DiscountController;
import Controller.Client.ManagerController;
import Models.DiscountCode;
import Models.UserAccount.Customer;
import View.Menu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateDiscountCodeMenu extends Menu {
    public CreateDiscountCodeMenu(Menu parentMenu) {
        super(parentMenu);
    }

    private HashMap<String, Integer> maxUsingTime=new HashMap<>();
    private HashMap<String, Integer> remainingTimesForEachCustomer=new HashMap<>();
    private ArrayList<String> allUsers = new ArrayList<>();

    @Override
    public void help() {

    }

    @Override
    public void execute() {
        ManagerController.getInstance().getAllUserFromServer();
        Date startTime, endTime;
        int discountPercent;
        double maxDiscountAmount;
        maxDiscountAmount = getMaxDiscountAmount();
        discountPercent = getDiscountPercent();
        startTime = getStartTime().getTime();
        endTime = getEndTime(startTime).getTime();
        getUsersThatHaveDiscountCode();
        DiscountCode discountCode = new DiscountCode(startTime, endTime, allUsers, discountPercent, maxDiscountAmount, maxUsingTime, remainingTimesForEachCustomer);
        DiscountController.getInstance().createDiscountCode(discountCode);
        back();
    }

    private void getUsersThatHaveDiscountCode() {
        String username="";
        String count;
        while (!username.equals("end!")) {
            System.out.println("Enter a Customer's Username or enter end!");
            username = scanner.nextLine().trim();
            if(ManagerController.getInstance().isThereCustomerWithThisUsername(username)){
                System.out.println("Enter the number of times that customer can use it");
                count = scanner.nextLine().trim();
                if(count.matches("\\d+")){
                    allUsers.add(username);
                    remainingTimesForEachCustomer.put(username, Integer.parseInt(count));
                    maxUsingTime.put(username,Integer.parseInt(count));
                }else printError("count is Invalid");
            }else{
                printError("there is no user with this username");
            }
        }

    }

    private double getMaxDiscountAmount() {
        String maxDiscountAmount;
        while (true) {
            System.out.println("Enter Max Discount Amount");
            maxDiscountAmount = scanner.nextLine().trim();
            if (Pattern.matches("\\d+\\.?\\d+", maxDiscountAmount)) {
                break;
            } else {
                System.out.println("Max Discount Amount is Invalid");
            }
        }
        return Double.parseDouble(maxDiscountAmount);
    }

    private int getDiscountPercent() {
        String discountPercent;
        while (true) {
            System.out.println("Enter Discount Percent");
            discountPercent = scanner.nextLine().trim();
            if (Pattern.matches("\\d+", discountPercent)) {
                break;
            } else {
                System.out.println("Discount Percent is Invalid");
            }
        }
        return Integer.parseInt(discountPercent);
    }


    private Calendar getStartTime() {
        String startTime;
        Calendar calendar = Calendar.getInstance();
        while (true) {
            System.out.println("Enter start Date of Discount Code (dd/mm/yyyy)");
            startTime = scanner.nextLine().trim();
            if (startTime.matches("([12]?\\d|30|31)\\/([12]?\\d|30|31)\\/(2\\d\\d\\d)")) {
                Pattern pattern = Pattern.compile("([12]?\\d|30|31)\\/([12]?\\d|30|31)\\/(2\\d\\d\\d)");
                Matcher matcher = pattern.matcher(startTime);
                matcher.find();
                calendar.set(Calendar.DATE, Integer.parseInt(matcher.group(1)));
                calendar.set(Calendar.MONTH, Integer.parseInt(matcher.group(2)));
                calendar.set(Calendar.YEAR, Integer.parseInt(matcher.group(3)));
                if (calendar.getTime().after(Calendar.getInstance().getTime())) {
                    break;
                } else {
                    System.out.println("You can't choose a date from the past");
                }
            } else {
                System.out.println("Please enter a valid date");
            }
        }
        while (true) {
            System.out.println("Enter start time of Discount Code (hh:mm:ss)");
            startTime = scanner.nextLine().trim();
            if (startTime.matches("([01]\\d|20|21|22|23):([012345]\\d):([012345]\\d)")) {
                Pattern pattern = Pattern.compile("([01]\\d|20|21|22|23):([012345]\\d):([012345]\\d)");
                Matcher matcher = pattern.matcher(startTime);
                matcher.find();
                calendar.set(Calendar.HOUR, Integer.parseInt(matcher.group(1)));
                calendar.set(Calendar.MINUTE, Integer.parseInt(matcher.group(2)));
                calendar.set(Calendar.SECOND, Integer.parseInt(matcher.group(3)));
                if (calendar.getTime().after(Calendar.getInstance().getTime())) {
                    break;
                } else {
                    System.out.println("You can't choose a time from the past");
                }
            } else {
                System.out.println("Please enter a valid time");
            }
        }
        return calendar;
    }

    private Calendar getEndTime(Date startTime) {
        String endTime;
        Calendar calendar = Calendar.getInstance();
        while (true) {
            System.out.println("Enter end Date of Discount Code (dd/mm/yyyy)");
            endTime = scanner.nextLine().trim();
            if (endTime.matches("([12]?\\d|30|31)\\/([12]?\\d|30|31)\\/(2\\d\\d\\d)")) {
                Pattern pattern = Pattern.compile("([12]?\\d|30|31)\\/([12]?\\d|30|31)\\/(2\\d\\d\\d)");
                Matcher matcher = pattern.matcher(endTime);
                matcher.find();
                calendar.set(Calendar.DATE, Integer.parseInt(matcher.group(1)));
                calendar.set(Calendar.MONTH, Integer.parseInt(matcher.group(2)));
                calendar.set(Calendar.YEAR, Integer.parseInt(matcher.group(3)));
                if (calendar.getTime().after(startTime)) {
                    break;
                } else {
                    System.out.println("You should enter a date after the start date");
                }
            } else {
                System.out.println("Please enter a valid date");
            }
        }
        while (true) {
            System.out.println("Enter end time of Discount Code (hh:mm:ss)");
            endTime = scanner.nextLine().trim();
            if (endTime.matches("([01]\\d|20|21|22|23):([012345]\\d):([012345]\\d)")) {
                Pattern pattern = Pattern.compile("([01]\\d|20|21|22|23):([012345]\\d):([012345]\\d)");
                Matcher matcher = pattern.matcher(endTime);
                matcher.find();
                calendar.set(Calendar.HOUR, Integer.parseInt(matcher.group(1)));
                calendar.set(Calendar.MINUTE, Integer.parseInt(matcher.group(2)));
                calendar.set(Calendar.SECOND, Integer.parseInt(matcher.group(3)));
                if (calendar.getTime().after(startTime)) {
                    break;
                } else {
                    System.out.println("You should enter a time after the start time");
                }
            } else {
                System.out.println("Please enter a valid time");
            }
        }
        return calendar;
    }

}
