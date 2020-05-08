package View.UserMenu.Manager;

import Controller.Client.ClientController;
import Controller.Client.ManagerController;
import Models.UserAccount.Manager;
import View.Menu;

import java.util.regex.Pattern;

public class CreateManagerMenu extends Menu {
    public CreateManagerMenu(Menu parentMenu) {
        super(parentMenu);
    }

    @Override
    public void help() {

    }

    @Override
    public void execute() {
        String command;
        String username, password, firstName, lastName, email, phoneNumber, credit;
        username = getUserNamer();
        password = getPassword();
        firstName = getFirstName();
        lastName = getLastName();
        email = getEmail();
        phoneNumber = getPhoneNumber();
        credit = getCredit();
        Manager manager = new Manager(username, password, firstName, lastName, email, phoneNumber, Double.parseDouble(credit));
        ManagerController.getInstance().createManagerProfile(manager);
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

    private String getUserNamer() {
        String username;
        while (true) {
            System.out.println("Enter UserName");
            username = scanner.nextLine().trim();
            if (checkuserNameIsvalid(username)) {
                break;
            } else {
                System.out.println("Username is invalid:use alphabetCharacters and at least 4words");
            }
        }
        return username;
    }

    private boolean checkuserNameIsvalid(String word) {
        if (Pattern.matches("\\w+", word) && word.length() >= 4) {
            return true;
        }
        return false;
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
