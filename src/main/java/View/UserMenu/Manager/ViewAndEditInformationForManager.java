package View.UserMenu.Manager;

import Controller.Client.ClientController;
import Controller.Client.ManagerController;
import Models.UserAccount.Manager;
import View.Menu;
import com.google.gson.Gson;

import java.util.regex.Pattern;

public class ViewAndEditInformationForManager extends Menu {
    public ViewAndEditInformationForManager(Menu parentMenu) {
        super(parentMenu);
    }
    @Override
    public void help() {
        String managerMenuOptions = "";
        managerMenuOptions += "1.Edit First Name\n";
        managerMenuOptions += "2.Edit Last Name\n";
        managerMenuOptions += "3.Edit Password\n";
        managerMenuOptions += "4.Edit PhoneNumber\n";
        managerMenuOptions += "5.Edit Email\n";
        managerMenuOptions += "5.Edit Credit\n";
        managerMenuOptions += "6.LogOut\n";
        managerMenuOptions += "7.Help\n";
        managerMenuOptions += "8.Back";
        System.out.println(managerMenuOptions);
    }

    @Override
    public void execute() {
        Manager manager=(Manager) ClientController.getInstance().getCurrentUser();
        System.out.println(manager.viewPersonalInfo());
        String command;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("Edit First Name")) {
                manager.setFirstName(getFirstName());
                ClientController.getInstance().sendMessageToServer("@editManager@"+new Gson().toJson(manager));
            } else if (command.equalsIgnoreCase("Edit Last Name")) {
                manager.setLastName(getLastName());
                ClientController.getInstance().sendMessageToServer("@editManager@"+new Gson().toJson(manager));
            } else if (command.equalsIgnoreCase("Edit Password")) {
                manager.setPassword(getPassword());
                ClientController.getInstance().sendMessageToServer("@editManager@"+new Gson().toJson(manager));
            } else if (command.equalsIgnoreCase("Edit PhoneNumber")) {
                manager.setPhoneNumber(getPhoneNumber());
                ClientController.getInstance().sendMessageToServer("@editManager@"+new Gson().toJson(manager));
            } else if (command.equalsIgnoreCase("Edit Email")) {
                manager.setEmail(getEmail());
                ClientController.getInstance().sendMessageToServer("@editManager@"+new Gson().toJson(manager));
            }else if (command.equalsIgnoreCase("Edit Credit")) {
                manager.setCredit(Double.parseDouble(getCredit()));
                ClientController.getInstance().sendMessageToServer("@editManager@"+new Gson().toJson(manager));
            } else if (command.equalsIgnoreCase("help")) {
                help();
            } else {
                System.out.println("Invalid Command");
            }
        }
        back();

    }
    private String getCredit() {
        String credit;
        while (true) {
            System.out.println("Enter beginning credit");
            credit = scanner.nextLine().trim();
            if (Pattern.matches("\\d+\\.?\\d+", credit)) {
                break;
            } else {
                System.out.println("credit is Invalid");
            }
        }
        return credit;
    }

    private String getPhoneNumber() {
        String phoneNumber;
        while (true) {
            System.out.println("Enter PhoneNumber");
            phoneNumber = scanner.nextLine().trim();
            if (Pattern.matches("09\\d+", phoneNumber) && phoneNumber.length() == 11) {
                break;
            } else {
                System.out.println("Please enter a valid phoneNumber.");
            }
        }
        return phoneNumber;
    }

    private String getEmail() {
        String email;
        while (true) {
            System.out.println("Enter email");
            email = scanner.nextLine().trim();
            if (checkEmailIsvalid(email)) {
                break;
            } else {
                System.out.println("Please enter a valid email address.");
            }
        }
        return email;
    }

    private String getLastName() {
        String lastName;
        while (true) {
            System.out.println("Enter lastName");
            lastName = scanner.nextLine().trim();
            if (checkNameIsvalid(lastName)) {
                break;
            } else {
                System.out.println("lastName is invalid:name must consistOf [a-z] and [A-Z]");
            }
        }
        return lastName;
    }

    private String getFirstName() {
        String firstName;
        while (true) {
            System.out.println("Enter firstName");
            firstName = scanner.nextLine().trim();
            if (checkNameIsvalid(firstName)) {
                break;
            } else {
                System.out.println("firstName is invalid:name must consistOf [a-z] and [A-Z]");
            }
        }
        return firstName;
    }

    private String getPassword() {
        String password;
        while (true) {
            System.out.println("Enter Password");
            password = scanner.nextLine().trim();
            if (checkPasswordIsvalid(password)) {
                break;
            } else {
                System.out.println("password is invalid:password must contains at least 8 characters and less than 19 characters");
            }
        }
        return password;
    }


    private boolean checkPasswordIsvalid(String word) {
        if (word.length() > 8 && word.length() < 18) {
            return true;
        }
        return false;
    }

    private boolean checkNameIsvalid(String name) {
        if (Pattern.matches("([a-z]|[A-Z])+", name) && !name.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public void printError(String error) {
        super.printError(error);
        execute();
    }

    private boolean checkEmailIsvalid(String email) {
        if (Pattern.matches("\\w+\\.?\\w*@\\w+\\.\\w+", email)) {
            return true;
        }
        return false;
    }
}
