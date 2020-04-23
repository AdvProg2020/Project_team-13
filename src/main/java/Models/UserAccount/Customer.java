package Models.UserAccount;

public class Customer extends UserAccount{

    public Customer(String username, String password, String firstName, String lastName, String email, String phoneNumber, int credit) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
    }

    @Override
    public String viewPersonalInfo() {
        return null;
    }
}
