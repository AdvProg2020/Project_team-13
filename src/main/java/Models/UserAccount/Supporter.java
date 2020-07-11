package Models.UserAccount;

public class Supporter extends UserAccount{
    public Supporter(String username, String password, String firstName, String lastName, String email, String phoneNumber, double credit) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
        this.type = "@Supporter";
    }

    @Override
    public String viewPersonalInfo() {
        String personalInfo = "Supporter\n";
        personalInfo += "User name: " + this.username + "\n";
        personalInfo += "First Name: " + this.firstName + "\n";
        personalInfo += "Last name: " + this.lastName + "\n";
        personalInfo += "Email: " + this.email + "\n";
        personalInfo += "PhoneNumber: " + this.phoneNumber;
        return personalInfo;
    }
}
