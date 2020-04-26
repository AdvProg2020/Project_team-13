package Models.UserAccount;

public class Manager extends UserAccount {

    public Manager(String username, String password, String firstName, String lastName, String email, String phoneNumber, double credit) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
        this.type="@Manager";
    }

    @Override
    public String viewPersonalInfo() {
        String personalInfo = "";
        personalInfo += this.username + "\n";
        personalInfo += this.firstName + "\n";
        personalInfo += this.lastName + "\n";
        personalInfo += this.email + "\n";
        personalInfo += this.phoneNumber ;
        return personalInfo;
    }
}
