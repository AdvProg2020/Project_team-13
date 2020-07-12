package Controller.Bank;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private ServerSocket serverSocket;
    private Socket socket;
    private List<Account> allAccounts;
    private Account marketAccount;
    private static final Bank bank = new Bank();

    private Bank(){
        allAccounts = new ArrayList<>();
        marketAccount = new Account();
    }

    private static Bank getInstance(){
        return bank;
    }

    public static void main(String[] args) {

    }

//    public void createNewAccount(String accountId, String )
//



}
