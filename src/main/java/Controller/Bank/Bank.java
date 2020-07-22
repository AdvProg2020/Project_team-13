package Controller.Bank;

import Models.Account;
import Models.Receipt;
import Models.ReceiptType;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bank {
    private ServerSocket clientsServerSocket;
    private ServerSocket marketServerSocket;
    private List<Account> allAccounts;
    private Account marketAccount;
    private static Bank bank;
    private int numberOfConnectedClients;
    private String lastAccountId;
    private Algorithm algorithm;
    private Map<String, String> tokenMapper;
    private String lastReceiptId;

    private Bank() {
        allAccounts = new ArrayList<>();
        try {
            algorithm = Algorithm.HMAC256(new String(Files.readAllBytes(Paths.get("secret.txt"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tokenMapper = new HashMap<>();
        setAllAccounts();
    }

    public void setLastReceiptId(String lastReceiptId) {
        this.lastReceiptId = lastReceiptId;
    }

    private static Bank getInstance() {
        if (bank == null) {
            synchronized (Bank.class) {
                if (bank == null) {
                    bank = new Bank();
                }
            }
        }
        return bank;
    }

    public static void main(String[] args) throws IOException {
        Bank.getInstance().handleClientsConnection();
    }

    private synchronized String createNewAccount(String firstName, String lastName, String userName, String passWord, String repeatedPassword) {
        if (userExitsWithThisUserName(userName)) {
            return "username is not available";
        }
        if (!passWord.equals(repeatedPassword)) {
            return "password do not match";
        }
        String accountId = getTheLastAccountId();
        allAccounts.add(new Account(accountId, firstName, lastName, userName, passWord, 100000000));
        updateAllAccounts(new Gson().toJson(allAccounts));
        return accountId;
    }

    public void setMarketAccount(Account marketAccount) {
        this.marketAccount = marketAccount;
    }

    private boolean userExitsWithThisUserName(String userName) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }


    private boolean passWordIsValid(String userName, String passWord) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(userName) && account.getPassWord().equals(passWord)) {
                return true;
            }
        }
        return false;
    }

    private Account getAccountById(String accountId) {
        for (Account account : allAccounts) {
            if (account.getAccountId().equals(accountId)) {
                return account;
            }
        }
        return null;
    }

    private void handleClientsConnection() {
        try {
            clientsServerSocket = new ServerSocket(3030);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket socket = clientsServerSocket.accept();
                new Thread(() -> handleClientRequests(socket)).start();
            } catch (IOException e) {
                System.out.println("Error in Client's Acceptance ...");
                break;
            }
        }

    }

    private void handleClientRequests(Socket socket) {
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error in Starting Connection...");
            return;
        }
        while (true) {
            try {
                String response = "";
                String command = dataInputStream.readUTF();
                if (command.startsWith("create_account")) {
                    String[] commands = command.split("\\s");
                    if (commands.length == 6) {
                        response = createNewAccount(commands[1], commands[2], commands[3], commands[4], commands[5]);
                    } else {
                        response = "invalid input";
                    }
                } else if (command.startsWith("get_token")) {
                    String[] commands = command.split("\\s");
                    if (commands.length == 3) {
                        response = getToken(commands[1], commands[2]);
                    } else {
                        response = "invalid input";
                    }
                } else if (command.startsWith("create_receipt")) {
                    String[] commands = command.split("\\s");
                    if (commands.length == 7) {
                        response = createReceipt(commands[1], commands[2], commands[3], commands[4], commands[5], commands[6]);
                    } else {
                        response = "invalid input";
                    }
                } else if (command.startsWith("get_transactions")) {
                    String[] commands = command.split("\\s");
                    if (commands.length == 4) {
                        response = getTheTransactions(commands[1], commands[2], commands[3]);
                    } else if (commands.length == 3) {
                        response = getTheTransactions(commands[1], commands[2], "");
                    } else {
                        response = "invalid input";
                    }
                } else if (command.startsWith("pay")) {
                    String[] commands = command.split("\\s");
                    if (commands.length == 2) {
                        response = pay(commands[1]);
                    } else {
                        response = "invalid input";
                    }
                } else if (command.startsWith("get_balance")) {
                    String[] commands = command.split("\\s");
                    if (commands.length == 2) {
                        response = getBalance(commands[1]);
                    } else {
                        response = "invalid input";
                    }
                } else if (command.startsWith("exit")) {
                    exit();
                } else if (command.startsWith("processTransaction")) {
                    String[] commands = command.split("\\s");
                    response = processTransactionForMarket(commands[1], commands[2]);
                } else {
                    response = "invalid input";
                }
                dataOutputStream.writeUTF(response);
                dataOutputStream.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        try {
            dataInputStream.close();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String processTransactionForMarket(String type, String amount) {
        switch (ReceiptType.valueOf(type.toUpperCase())) {
            case WITHDRAW:
                marketAccount.setAmount(marketAccount.getAmount() - Double.parseDouble(amount));
                break;
            case DEPOSIT:
                marketAccount.setAmount(marketAccount.getAmount() + Double.parseDouble(amount));
                break;
            default:
                break;

        }
        updateMarketAccount(new Gson().toJson(marketAccount));
        return "done successfully for market";
    }

    private void updateMarketAccount(String json) {
        try {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("marketAccount.txt")));
            printWriter.print(json);
            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error in Database...");
        }
    }


    private String getToken(String userName, String passWord) {
        if (!userExitsWithThisUserName(userName)) {
            return "username is invalid";
        }
        if (!passWordIsValid(userName, passWord)) {
            return "password is Invalid";
        }
        Account account = getAccountWithUserName(userName);
        assert account != null;
        String jwt = JWT.create().withIssuer(userName + "//" + passWord + "//" + account.getAccountId()).withExpiresAt(new Date(new Date().getTime() + 3600000)).sign(algorithm);
        if (userAlreadyHasThisToken(userName, passWord)) {
            tokenMapper.remove(getTheTokenByUserNameAndPassWord(userName, passWord), userName + "//" + passWord);
        }
        tokenMapper.put(jwt, userName + "//" + passWord);
        return jwt;
    }

    private Account getAccountWithUserName(String userName) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(userName)) {
                return account;
            }
        }
        return null;
    }


    private boolean userAlreadyHasThisToken(String userName, String passWord) {
        for (String token : tokenMapper.keySet()) {
            String[] details = tokenMapper.get(token).split("//");
            if (details[0].equals(userName) && details[1].equals(passWord)) {
                return true;
            }
        }
        return false;
    }

    private String getTheTokenByUserNameAndPassWord(String userName, String passWord) {
        for (String token : tokenMapper.keySet()) {
            String[] details = tokenMapper.get(token).split("//");
            if (details[0].equals(userName) && details[1].equals(passWord)) {
                return token;
            }
        }
        return null;
    }

    private String createReceipt(String token, String receiptType, String money, String sourceId, String destinationId, String description) {
        boolean equals1 = receiptType.equals(String.valueOf(ReceiptType.DEPOSIT).toLowerCase());
        boolean equals2 = receiptType.equals(String.valueOf(ReceiptType.WITHDRAW).toLowerCase());
        boolean equals5 = receiptType.equals(String.valueOf(ReceiptType.MOVE).toLowerCase());
        if (!(equals1 ||
                equals2 || equals5)) {
            return "invalid receipt type";
        }
        if (!money.matches("\\d+")) {
            return "invalid money";
        }
        if (!((sourceId.matches("@a\\d{5}") && destinationId.equals("-1") ||
                (sourceId.equals("-1") && destinationId.matches("@a\\d{5}")) ||
                (sourceId.matches("@a\\d{5}") && destinationId.matches("(@a\\d{5})|(@a231234@)") &&
                        equals5)))) {
            return "invalid parameters passed";
        }
        if ((!equals1 && sourceId.equals("-1") ||
                (!equals2 && destinationId.equals("-1")))) {
            return "account id invalid";
        }
        if (!equals1 && !isValidAccount(sourceId)) {
            return "source account id is invalid";
        }
        if (!equals2 && !isValidAccount(destinationId)) {
            return "destination account id is invalid";
        }
        if (sourceId.equals(destinationId)) {
            return "equal source and dest account";
        }

        Pattern pattern = Pattern.compile("\\W");
        Matcher matcher = pattern.matcher(description);
        if (matcher.find()) {
            return "your input contains invalid characters";
        }
        try {
            String string = equals1 ? destinationId : sourceId;
            JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer(tokenMapper.get(token) + "//" + string).build();
            jwtVerifier.verify(token);

        } catch (TokenExpiredException e) {
            return "token is expired";
        } catch (JWTVerificationException e) {
            return "token is invalid";
        }
        synchronized (this) {
            String id = getTheLastReceiptId();
            Receipt receipt = new Receipt(description, ReceiptType.valueOf(receiptType.toUpperCase()), Double.parseDouble(money), sourceId, id, destinationId, "0");
            Objects.requireNonNull(getAccountWithUserName(tokenMapper.get(token).split("//")[0])).getAllReceipts().add(receipt);
            updateAllAccounts(new Gson().toJson(allAccounts));
            return id;
        }

    }


    private String getTheLastReceiptId() {
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader("lastReceiptId.txt")));
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            scanner.close();
            setLastReceiptId(String.valueOf(stringBuilder));
            this.lastReceiptId = ("@r" + (Integer.parseInt(lastReceiptId.substring(2)) + 1));
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("lastReceiptId.txt")));
            printWriter.print(lastReceiptId);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastReceiptId;
    }


    private boolean isValidAccount(String accountId) {
        for (Account account : allAccounts) {
            if (account.getAccountId().equals(accountId)) {
                return true;
            }
        }
        return false;
    }

    private String getTheTransactions(String token, String type, String receiptId) {
        Account account = getAccountWithUserName(tokenMapper.get(token).split("//")[0]);
        try {
            JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer(tokenMapper.get(token) + "//" + account.getAccountId()).build();
            jwtVerifier.verify(token);
            if (!receiptId.equals("") && !isValidReceiptForThisAccount(receiptId)) {
                throw new NoSuchReceiptException();
            }
        } catch (TokenExpiredException e) {
            return "token expired";
        } catch (JWTVerificationException e) {
            return "token is invalid";
        } catch (NoSuchReceiptException e) {
            return "invalid receipt id";
        }
        if (!receiptId.equals("")) {
            return new Gson().toJson(getReceiptById(receiptId));
        }
        if (type.equals("*")) {
            return new Gson().toJson(account.getAllReceipts()).replace("[{", "{").replace("}]", "}").replace("},{", "}*{");
        } else if (type.equals("-")) {
            return new Gson().toJson(getAllTransactionsThatYourAccountIsSource(account.getAccountId())).replace("[{", "{").replace("}]", "}").replace("},{", "}*{");
        } else if (type.equals("+")) {
            return new Gson().toJson(getAllTransactionsThatYourAccountIsDestination(account.getAccountId())).replace("[{", "{").replace("}]", "}").replace("},{", "}*{");
        }
        return "invalid input";
    }


    private List<Receipt> getAllTransactionsThatYourAccountIsDestination(String accountId) {
        List<Receipt> allReceipt = new ArrayList<>();
        for (Account account : allAccounts) {
            for (Receipt receipt : account.getAllReceipts()) {
                if (receipt.getDestinationId().equals(accountId)) {
                    allReceipt.add(receipt);
                }
            }
        }

        return allReceipt;
    }


    private List<Receipt> getAllTransactionsThatYourAccountIsSource(String accountId) {
        List<Receipt> allReceipt = new ArrayList<>();
        for (Account account : allAccounts) {
            for (Receipt receipt : account.getAllReceipts()) {
                if (receipt.getSourceId().equals(accountId)) {
                    allReceipt.add(receipt);
                }
            }
        }
        return allReceipt;

    }


    private Receipt getReceiptById(String receiptId) {
        for (Account account : allAccounts) {
            for (Receipt receipt : account.getAllReceipts()) {
                if (receipt.getReceiptId().equals(receiptId)) {
                    return receipt;
                }
            }
        }
        return null;
    }


    private boolean isValidReceiptForThisAccount(String receiptId) {
        for (Account account : allAccounts) {
            for (Receipt receipt : account.getAllReceipts()) {
                if (receipt.getReceiptId().equals(receiptId)) {
                    return true;
                }
            }
        }
        return false;
    }


    private synchronized String pay(String receiptId) {
        if (!isValidReceiptForThisAccount(receiptId)) {
            return "invalid receipt id";
        }
        if (!isValidAccount(getReceiptById(receiptId).getSourceId()) && !isValidAccount(Objects.requireNonNull(getReceiptById(receiptId)).getDestinationId())) {
            return "invalid account id";
        }
        if (Objects.requireNonNull(getReceiptById(receiptId)).getPaid().equals("1")) {
            return "receipt is paid before";
        }
        if (!getReceiptById(receiptId).getReceiptType().equals(ReceiptType.DEPOSIT) && Objects.requireNonNull(getReceiptById(receiptId)).getMoney() > Objects.requireNonNull(getAccountById(Objects.requireNonNull(getReceiptById(receiptId)).getSourceId())).getAmount()) {
            return "source account does not have enough money";
        }
        switch (Objects.requireNonNull(getReceiptById(receiptId)).getReceiptType()) {
            case MOVE:
                Objects.requireNonNull(getAccountById(getReceiptById(receiptId).getSourceId())).setAmount(Objects.requireNonNull(getAccountById(getReceiptById(receiptId).getSourceId())).getAmount() - getReceiptById(receiptId).getMoney());
                Objects.requireNonNull(getAccountById(Objects.requireNonNull(getReceiptById(receiptId)).getDestinationId())).setAmount(Objects.requireNonNull(getAccountById(Objects.requireNonNull(getReceiptById(receiptId)).getDestinationId())).getAmount() + getReceiptById(receiptId).getMoney());
                break;
            case DEPOSIT:
                Objects.requireNonNull(getAccountById(Objects.requireNonNull(getReceiptById(receiptId)).getDestinationId())).setAmount(Objects.requireNonNull(getAccountById(Objects.requireNonNull(getReceiptById(receiptId)).getDestinationId())).getAmount() + Objects.requireNonNull(getReceiptById(receiptId)).getMoney());
                break;
            case WITHDRAW:
                getAccountById(getReceiptById(receiptId).getSourceId()).setAmount(getAccountById(getReceiptById(receiptId).getSourceId()).getAmount() - getReceiptById(receiptId).getMoney());
                break;
            default:
                break;

        }
        Objects.requireNonNull(getReceiptById(receiptId)).setPaid("1");
        updateAllAccounts(new Gson().toJson(allAccounts));
        return "done successfully";
    }

    private String getBalance(String token) {
        Account account = getAccountWithUserName(tokenMapper.get(token).split("//")[0]);
        try {
            JWTVerifier jwtVerifier = null;
            if (account != null) {
                jwtVerifier = JWT.require(algorithm).withIssuer(tokenMapper.get(token) + "//" + account.getAccountId()).build();
            }
            if (jwtVerifier != null) {
                jwtVerifier.verify(token);
            }
        } catch (TokenExpiredException e) {
            return "token expired";
        } catch (JWTVerificationException | NullPointerException e) {
            return "token is invalid";
        }
        assert account != null;
        return String.valueOf(account.getAmount());
    }


    private void exit() throws IOException {
        throw new IOException("Connection is Closed...");
    }


    private void setAllAccounts() {
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader("allAccounts.txt")));
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            scanner.close();
            Type accountType = new TypeToken<ArrayList<Account>>() {
            }.getType();
            setAllAccounts(new Gson().fromJson(String.valueOf(stringBuilder), accountType));
            Scanner scanner1 = new Scanner(new BufferedReader(new FileReader("marketAccount.txt")));
            StringBuilder stringBuilder1 = new StringBuilder();
            while (scanner1.hasNextLine()) {
                stringBuilder1.append(scanner1.nextLine());
            }
            setMarketAccount(new Gson().fromJson(String.valueOf(stringBuilder1), Account.class));
            scanner1.close();
        } catch (IOException e) {
            System.out.println("Error in Database Connection...");
        }
    }

    private void setAllAccounts(List<Account> allAccounts) {
        this.allAccounts = allAccounts;
    }

    private String getTheLastAccountId() {
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader("lastAccountId.txt")));
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            scanner.close();
            setLastAccountId(String.valueOf(stringBuilder));
            this.lastAccountId = "@a" + (Integer.parseInt(lastAccountId.substring(2)) + 1);
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("lastAccountId.txt")));
            printWriter.print(lastAccountId);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastAccountId;
    }

    private void setLastAccountId(String lastAccountId) {
        this.lastAccountId = lastAccountId;
    }

    private void updateAllAccounts(String json) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new BufferedWriter(new FileWriter("allAccounts.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (printWriter != null) {
            printWriter.print(json);
        }
        assert printWriter != null;
        printWriter.close();
    }

}


class NoSuchReceiptException extends Exception {
    public NoSuchReceiptException() {
    }
}

