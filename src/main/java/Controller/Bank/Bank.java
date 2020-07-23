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
import java.math.BigInteger;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bank {
    private ServerSocket clientsServerSocket;
    private ServerSocket marketServerSocket;
    private List<Account> allAccounts;
    private Account marketAccount;
    private static Bank bank;
    private String lastAccountId;
    private Algorithm algorithm;
    private Map<String, String> tokenMapper;
    private String lastReceiptId;
    private HashMap<String, ArrayList<Long>> ipDosChecker = new HashMap<>();
    private HashMap<Socket, String> socketIp = new HashMap<>();
    private HashMap<DataOutputStream, Socket> socketDataOutputStreamHashMap = new HashMap<>();
    private ArrayList<String> blackList = new ArrayList<>();
    private HashMap<String, Long> temporaryBlackList = new HashMap<>();
    private HashMap<String, ArrayList<Long>> errorCounterForIp = new HashMap<>();
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private Class<?> aclass;
    private final String DB_URL = "jdbc:ucanaccess://BankDatabase.accdb";

    {
        try {
            aclass = Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

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
        if (allAccounts == null) allAccounts = new ArrayList<>();
        allAccounts.add(new Account(accountId, firstName, lastName, userName, passWord, 100000000));
        updateAllAccounts(new Gson().toJson(allAccounts));
        return accountId;
    }

    public void setMarketAccount(Account marketAccount) {
        this.marketAccount = marketAccount;
    }

    private boolean userExitsWithThisUserName(String userName) {
        if (allAccounts == null) allAccounts = new ArrayList<>();
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
                InetSocketAddress sockaddr = (InetSocketAddress) socket.getRemoteSocketAddress();
                InetAddress inaddr = sockaddr.getAddress();
                Inet4Address in4addr = (Inet4Address) inaddr;
                String ip4string = in4addr.toString();
                socketIp.put(socket, ip4string);
                if (!ipDosChecker.containsKey(socketIp.get(socket)))
                    ipDosChecker.put(socketIp.get(socket), new ArrayList<Long>());
                if (!errorCounterForIp.containsKey(socketIp.get(socket)))
                    errorCounterForIp.put(socketIp.get(socket), new ArrayList<>());
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
            socketDataOutputStreamHashMap.put(dataOutputStream,socket);
        } catch (IOException e) {
            System.out.println("Error in Starting Connection...");
            return;
        }
        int i= 0;
        while (true) {
            try {
                String response = "";
                String command = dataInputStream.readUTF();
                long time = new Date().getTime();
                if(!RSASecretGenerator.getInstance().getTheDecodedMessageViaRSA(command.split(" /// ")[0]).equals("0@@getTime@"))
                ipDosChecker.get(socketIp.get(socket)).add(time);
                System.out.println("               algorithm");
                System.out.println("\u001B[35m" + time + "\u001B[0m");
                if(i==0)  {
                    Type keyType = new TypeToken<Key<BigInteger,BigInteger>>(){}.getType();
                    RSASecretGenerator.getInstance().setAnotherPublicKey(new Gson().fromJson(command,keyType));
                    System.out.println("aaaaaaaaaaaa");
                    dataOutputStream.writeUTF(new Gson().toJson(RSASecretGenerator.getInstance().getPublicKey()));
                    dataOutputStream.flush();
                    i++;
                    continue;
                }
                command = RSASecretGenerator.getInstance().getTheDecodedMessageViaRSA(command.split(" /// ")[0]);
                System.out.println("Receiving message from server "  + command);
                System.out.println("aaaaaaaaaaaaa123");
                if (checkDosAttack(socketIp.get(socket))) {
                    if (!blackList.contains(socketIp.get(socket))) {
                        blackList.add(socketIp.get(socket));
                    }
                    response = "@Errors@" + "You are rushing take it easy little boy.";
                    socket.close();
                } else if (blackList.contains(socketIp.get(socket))) {
                    response = "@Errors@" + "You are rushing take it easy little boy.";
                    socket.close();
                } else if (temporaryBlackList.containsKey(socketIp.get(socket))) {
                    if (new Date().getTime() - temporaryBlackList.get(socketIp.get(socket)) > 300000) {
                        temporaryBlackList.remove(socketIp.get(socket));
                        errorCounterForIp.get(socketIp.get(socket)).clear();
                        response = processMessage(command);
                    } else {
                        response = "@Errors@You are temporary banned for " + (300000 - (new Date().getTime() - temporaryBlackList.get(socketIp.get(socket)))) + " milliseconds";
                    }
                } else {
                    response = processMessage(command);
                }
                if(response.startsWith("@Error")&&!temporaryBlackList.containsKey(socketIp.get(socketDataOutputStreamHashMap.get(dataOutputStream)))){
                    errorCounterForIp.get(socketIp.get(socketDataOutputStreamHashMap.get(dataOutputStream))).add(new Date().getTime());
                    if(checkBruteForce(socketIp.get(socketDataOutputStreamHashMap.get(dataOutputStream)))) {
                        try {
                            dataOutputStream.writeUTF(response);
                            dataOutputStream.flush();
                            return;
                        } catch (IOException e) {
                            System.out.println("Error in Sending Packets...");
                            try {
                                dataOutputStream.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
                response = RSASecretGenerator.getInstance().getTheEncodedWithRSA(response,RSASecretGenerator.getInstance().getAnotherPublicKey());
                System.out.println("QQQQQQQQQQ");
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

    public boolean checkDosAttack(String ip) {
        if (ipDosChecker.get(ip).size() > 10) {
            if (ipDosChecker.get(ip).get(ipDosChecker.get(ip).size() - 1) - ipDosChecker.get(ip).get(ipDosChecker.get(ip).size() - 10) < 150) {
                System.out.println("\u001B[35m" + (ipDosChecker.get(ip).get(ipDosChecker.get(ip).size() - 1) - ipDosChecker.get(ip).get(ipDosChecker.get(ip).size() - 9)) + "\u001B[0m");
                return true;
            }
        }
        return false;
    }

    public boolean checkBruteForce(String ip) {
        if (errorCounterForIp.get(ip).size() > 10) {
            if (errorCounterForIp.get(ip).get(errorCounterForIp.get(ip).size() - 1) - errorCounterForIp.get(ip).get(errorCounterForIp.get(ip).size() - 9) < 20000) {
                System.out.println("\u001B[32m" + (errorCounterForIp.get(ip).get(errorCounterForIp.get(ip).size() - 1) - errorCounterForIp.get(ip).get(errorCounterForIp.get(ip).size() - 9)) + "\u001B[0m");
                if (!temporaryBlackList.containsKey(ip))
                    temporaryBlackList.put(ip, new Date().getTime());
                return true;
            }
        }
        return false;
    }

    private String processMessage(String command) throws IOException {
        String response = "";
        long date = 0;
        System.out.println("pro fuck: " + command);
        Pattern pattern = Pattern.compile("(\\d+)@?.*");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            String date1 = matcher.group(1);
            command = command.substring(date1.length());
            if (command.charAt(0) == '@') {
                command = command.substring(1);
            }
            date = Long.parseLong(date1);
        }
        System.out.println("fukh");
        System.out.println(date);
        if (new Date().getTime() - date > 20000 && !command.startsWith("@getTime@")) {
            System.out.println("fuck php");
            return "@Errors@InvalidMessage";
        }
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
        } else if (command.startsWith("@getTime@")) {
            response = String.valueOf(new Date().getTime());
        } else {
            response = "invalid input";
        }
        System.out.println("\u001B[35m" + response + "\u001B[0m");
        return response;
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
        Account account = new Gson().fromJson(json, Account.class);
        try{
          connection = DriverManager.getConnection(DB_URL);
          preparedStatement = connection.prepareStatement("UPDATE marketAccount SET amount = ?, allReceipts = ?");
          preparedStatement.setString(1, String.valueOf(account.getAmount()));
          preparedStatement.setString(2, new Gson().toJson(account.getAllReceipts()));
          preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                preparedStatement.close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
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
            connection = DriverManager.getConnection(DB_URL);
            preparedStatement = connection.prepareStatement("SELECT lastReceiptId FROM lastReceiptId");
            ResultSet resultSet = preparedStatement.executeQuery();
            StringBuilder last = new StringBuilder();
            while (resultSet.next()) {
                last.append(resultSet.getString("lastReceiptId"));
            }
            resultSet.close();
            setLastReceiptId(String.valueOf(last));
            this.lastReceiptId = "@a" + (Integer.parseInt(lastReceiptId.substring(2)) + 1);
            preparedStatement = connection.prepareStatement("UPDATE lastReceiptId SET lastReceiptId = ?");
            preparedStatement.setString(1, lastReceiptId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                preparedStatement.close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
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
        List<Account> allAccounts= new ArrayList<>();
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection(DB_URL);
            preparedStatement = connection.prepareStatement("SELECT * FROM allAccounts");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account(resultSet.getString("accountId"), resultSet.getString("firstName"), resultSet.getString("lastName"),
                        resultSet.getString("userName"), resultSet.getString("passWord"), Double.parseDouble(resultSet.getString("amount")));
               allAccounts.add(account);
               if(!resultSet.getString("allReceipts").equals("null")){
                   Type type = new TypeToken<ArrayList<Receipt>>(){}.getType();
                   account.setAllReceipts(new Gson().fromJson(resultSet.getString("allReceipts"), type));
               }
            }
            setAllAccounts(allAccounts);
            preparedStatement = connection.prepareStatement("SELECT * FROM marketAccount");
            resultSet = preparedStatement.executeQuery();
            Account account = null;
            while (resultSet.next()) {
                account = new Account(resultSet.getString("accountId"), resultSet.getString("companyName"), Double.parseDouble(resultSet.getString("amount")));
            }
            setMarketAccount(account);
        }catch (SQLException | NullPointerException e){
            e.printStackTrace();
        }finally {
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                preparedStatement.close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    private void setAllAccounts(List<Account> allAccounts) {
        this.allAccounts = allAccounts;
    }

    private String getTheLastAccountId() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            preparedStatement = connection.prepareStatement("SELECT lastAccountId FROM lastAccountId");
            ResultSet resultSet = preparedStatement.executeQuery();
            StringBuilder last = new StringBuilder();
            while (resultSet.next()) {
                last.append(resultSet.getString("lastAccountId"));
            }
            resultSet.close();
            setLastAccountId(String.valueOf(last));
            this.lastAccountId = "@a" + (Integer.parseInt(lastAccountId.substring(2)) + 1);
            preparedStatement = connection.prepareStatement("UPDATE lastAccountId SET lastAccountId = ?");
            preparedStatement.setString(1, lastAccountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                preparedStatement.close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return lastAccountId;
    }

    private void setLastAccountId(String lastAccountId) {
        this.lastAccountId = lastAccountId;
    }

    private void updateAllAccounts(String json) {
        Type type = new TypeToken<ArrayList<Account>>(){}.getType();
        ArrayList<Account> allAccounts = new Gson().fromJson(json, type);
        try{
            connection = DriverManager.getConnection(DB_URL);
            preparedStatement = connection.prepareStatement("DELETE FROM allAccounts");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO allAccounts (accountId, firstName, lastName, userName, passWord, amount, allReceipts, companyName)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            for (Account account : allAccounts) {
                preparedStatement.setString(1, account.getAccountId());
                preparedStatement.setString(2, account.getFirstName());
                preparedStatement.setString(3, account.getLastName());
                preparedStatement.setString(4, account.getUsername());
                preparedStatement.setString(5, account.getPassWord());
                preparedStatement.setString(6, String.valueOf(account.getAmount()));
                preparedStatement.setString(7, new Gson().toJson(account.getAllReceipts()));
                preparedStatement.setString(8, "null");
                preparedStatement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                preparedStatement.close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}


class NoSuchReceiptException extends Exception {
    public NoSuchReceiptException() {
    }
}

