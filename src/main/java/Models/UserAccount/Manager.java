package Models.UserAccount;

public class Manager extends UserAccount {

    public Manager(String username, String password, String firstName, String lastName, String email, String phoneNumber, double credit) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
        this.type = "@Manager";
    }

    @Override
    public String viewPersonalInfo() {
        String personalInfo = "";
        personalInfo += "\u001B[34mUser name: \u001B[0m" + this.username + "\n";
        personalInfo += "\u001B[34mFirst Name: \u001B[0m" + this.firstName + "\n";
        personalInfo += "\u001B[34mLast name: \u001B[0m" + this.lastName + "\n";
        personalInfo += "\u001B[34mEmail: \u001B[0m" + this.email + "\n";
        personalInfo += "\u001B[34mPhoneNumber: \u001B[0m" + this.phoneNumber;
        return personalInfo;
    }
}
