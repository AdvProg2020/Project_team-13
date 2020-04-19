package Models.UserAccount;

public class Manager extends UserAccount{

    public Manager(String username, String password, String firstName, String lastName, String email, String phoneNumber, int credit) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
    }

    @Override
    public String viewPersonalInfo() {
        String personalInfo="";
        return personalInfo;
    }
}
