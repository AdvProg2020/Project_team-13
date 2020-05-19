package Models.UserAccount;

public class Manager extends UserAccount {

    public Manager(String username, String password, String firstName, String lastName, String email, String phoneNumber, double credit) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
        this.type = "@Manager";
    }

    @Override
    public String viewPersonalInfo() {
        String personalInfo = "";
        personalInfo += "User name: " + this.username + "\n";
        personalInfo += "First Name: " + this.firstName + "\n";
        personalInfo += "Last name: " + this.lastName + "\n";
        personalInfo += "Email: " + this.email + "\n";
        personalInfo += "PhoneNumber: " + this.phoneNumber;
        return personalInfo;
    }
}
