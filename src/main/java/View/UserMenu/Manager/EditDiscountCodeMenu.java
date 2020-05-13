package View.UserMenu.Manager;

import Controller.Client.ClientController;
import Controller.Client.DiscountController;
import Controller.Client.ManagerController;
import Models.DiscountCode;
import View.Menu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditDiscountCodeMenu extends Menu {
    private DiscountCode discountCode;

    public EditDiscountCodeMenu(Menu parentMenu) {
        super(parentMenu);
    }

    public void setDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }

    @Override
    public void help() {
        String managerMenuOptions = "";
        managerMenuOptions += "1.Edit Max Discount Amount\n";
        managerMenuOptions += "2.Edit Discount Percent\n";
        managerMenuOptions += "3.Edit Discount Start date and time\n";
        managerMenuOptions += "4.Edit Discount End date and time\n";
        managerMenuOptions += "5.Add Discount Code to a Customer\n";
        managerMenuOptions += "6.LogOut\n";
        managerMenuOptions += "7.Help\n";
        managerMenuOptions += "8.Back";
        System.out.println(managerMenuOptions);
    }

    @Override
    public void execute() {
        setDiscountCode(ClientController.getInstance().getCurrentDiscountCode());
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("Edit Max Discount Amount")) {
                discountCode.setMaxDiscountAmount(getMaxDiscountAmount());
                DiscountController.getInstance().editDiscountCode(discountCode);
            } else if (command.equalsIgnoreCase("Edit Discount Percent")) {
                discountCode.setDiscountPercent(getDiscountPercent());
                DiscountController.getInstance().editDiscountCode(discountCode);
            } else if (command.equalsIgnoreCase("Edit Discount Start date and time")) {
                discountCode.setStartTime(getStartTime().getTime());
                DiscountController.getInstance().editDiscountCode(discountCode);
            } else if (command.equalsIgnoreCase("Edit Discount End date and time")) {
                discountCode.setStartTime(getEndTime(discountCode.getStartTime()).getTime());
                DiscountController.getInstance().editDiscountCode(discountCode);
            } else if (command.equalsIgnoreCase("Add Discount Code to a Customer")) {
                ManagerController.getInstance().getAllUserFromServer();
                getUsersThatHaveDiscountCode();
                DiscountController.getInstance().editDiscountCode(discountCode);
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else {
                System.out.println("Invalid Command");
            }
        }
        back();

    }

    private void getUsersThatHaveDiscountCode() {
        HashMap<String, Integer> maxUsingTime = discountCode.getMaxUsingTime();
        HashMap<String, Integer> remainingTimesForEachCustomer = discountCode.getRemainingTimesForEachCustomer();
        ArrayList<String> allUsers = discountCode.getAllUserAccountsThatHaveDiscount();
        String username = "";
        String count;
        System.out.println("Enter a Customer's Username or enter end!");
        username= scanner.nextLine().trim();
        while (!username.equals("end!")) {
            if (ManagerController.getInstance().isThereCustomerWithThisUsername(username)) {
                if (!allUsers.contains(username)) {
                    System.out.println("Enter the number of times that customer can use it");
                    count = scanner.nextLine().trim();
                    if (count.matches("\\d+")) {
                        allUsers.add(username);
                        remainingTimesForEachCustomer.put(username, Integer.parseInt(count));
                        maxUsingTime.put(username, Integer.parseInt(count));
                    } else printError("count is Invalid");
                } else {
                    ClientController.getInstance().getCurrentMenu().printError("this user has this discount code");
                }
            } else {
                printError("there is no user with this username");
            }
            System.out.println("Enter a Customer's Username or enter end!");
        }
        discountCode.setAllUserAccountsThatHaveDiscount(allUsers);
        discountCode.setMaxUsingTime(maxUsingTime);
        discountCode.setRemainingTimesForEachCustomer(remainingTimesForEachCustomer);
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
                calendar.set(Calendar.MONTH, Integer.parseInt(matcher.group(2)) - 1);
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
                calendar.set(Calendar.MONTH, Integer.parseInt(matcher.group(2)) - 1);
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
