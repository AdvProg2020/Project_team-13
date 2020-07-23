package Controller.Server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ServerController {
    private static ServerController serverController;
    private ServerSocket serverSocket;
    private Map<DataOutputStream, String> allClients;
    private HashMap<String, DataOutputStream> SellerSockets = new HashMap<>();
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private HashMap<String, ArrayList<Long>> ipDosChecker = new HashMap<>();
    private HashMap<Socket, String> socketIp = new HashMap<>();
    private HashMap<DataOutputStream, Socket> socketDataOutputStreamHashMap = new HashMap<>();
    private ArrayList<String> blackList = new ArrayList<>();
    private HashMap<String, Long> temporaryBlackList = new HashMap<>();
    private HashMap<String, ArrayList<Long>> errorCounterForIp = new HashMap<>();

    public Map<String, DataOutputStream> getSellerSockets() {
        return SellerSockets;
    }

    private ServerController() {
        socket = null;
        try {
            socket = new Socket("localhost", 3030);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            handleBankConnection("sendKey");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        allClients = new HashMap<>();
    }

    private HashMap<String, Integer> onlineSupporters = new HashMap<>();

    public DataOutputStream findDataStreamWithUsername(String username) {
        for (DataOutputStream dataOutputStream : allClients.keySet()) {
            if (allClients.get(dataOutputStream).equals(username)) {
                System.out.println("found it!!!");
                return dataOutputStream;
            }
        }
        System.out.println("NOT found it!!!");
        return null;
    }

    public static ServerController getInstance() {
        if (serverController == null) {
            synchronized (ServerController.class) {
                if (serverController == null) {
                    serverController = new ServerController();
                }
            }
        }
        return serverController;
    }

    public HashMap<String, Integer> getOnlineSupporters() {
        return onlineSupporters;
    }

    public static void main(String[] args) {
        ServerController.getInstance().runServer();
        ServerController.getInstance().startProcess();
    }

    public String handleBankConnection(String data) {
        String response = null;
        if (data.equals("sendKey")) {
            try {
                data = new Gson().toJson(RSASecretGeneratorForBank.getInstance().getPublicKey());
                dataOutputStream.writeUTF(data);
                dataOutputStream.flush();
                System.out.println("aaaaaaaaaaaaaaaaa");
                response = dataInputStream.readUTF();
                System.out.println("bbbbbbbbbbb");
                Type keyType = new TypeToken<Key<BigInteger, BigInteger>>() {
                }.getType();
                RSASecretGeneratorForBank.getInstance().setAnotherPublicKey(new Gson().fromJson(response, keyType));
                String string = RSASecretGeneratorForBank.getInstance()
                        .getTheEncodedWithRSA("0@@getTime@", RSASecretGeneratorForBank.getInstance().getAnotherPublicKey());
                System.out.println("ServerSideMessageSending");
                System.out.println(string);
                System.out.println("ServerSideMessageSending");
                dataOutputStream.writeUTF(string);
                dataOutputStream.flush();
                String time = dataInputStream.readUTF();
                time = RSASecretGeneratorForBank.getInstance().getTheDecodedMessageViaRSA(time.split(" /// ")[0]);
                System.out.println("ServerSideMessageReceiving");
                System.out.println(time);
                System.out.println("ServerSideMessageReceiving");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String string = RSASecretGeneratorForBank.getInstance()
                        .getTheEncodedWithRSA("0@@getTime@", RSASecretGeneratorForBank.getInstance().getAnotherPublicKey());
                dataOutputStream.writeUTF(string);
                dataOutputStream.flush();
                String time = dataInputStream.readUTF();
                time = RSASecretGeneratorForBank.getInstance().getTheDecodedMessageViaRSA(time.split(" /// ")[0]);
                String string1 = RSASecretGeneratorForBank.getInstance()
                        .getTheEncodedWithRSA(time + "@" + data, RSASecretGeneratorForBank.getInstance().getAnotherPublicKey());
                dataOutputStream.writeUTF(string1);
                dataOutputStream.flush();
                response = dataInputStream.readUTF();
                response = RSASecretGeneratorForBank.getInstance().getTheDecodedMessageViaRSA(response.split(" /// ")[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    private void startProcess() {
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            System.out.println("Waiting for Client...");
            Socket socket;
            try {
                socket = serverSocket.accept();
                InetSocketAddress sockaddr = (InetSocketAddress) socket.getRemoteSocketAddress();
                InetAddress inaddr = sockaddr.getAddress();
                Inet4Address in4addr = (Inet4Address) inaddr;
                String ip4string = in4addr.toString();
                socketIp.put(socket, ip4string);
                if (!ipDosChecker.containsKey(socketIp.get(socket)))
                    ipDosChecker.put(socketIp.get(socket), new ArrayList<Long>());
                if (!errorCounterForIp.containsKey(socketIp.get(socket)))
                    errorCounterForIp.put(socketIp.get(socket), new ArrayList<>());
            } catch (IOException e) {
                break;
            }
            System.out.println("Client Connected!!!");
            Socket finalSocket = socket;
            new Thread(() -> {
                try {
                    getMessageFromClient(finalSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void runServer() {
        DataBase.getInstance().setAllUsersListFromDateBase();
        DataBase.getInstance().setAllRequestsListFromDateBase();
        DataBase.getInstance().setAllDiscountCodesListFromDateBase();
        DataBase.getInstance().setLastRequestId();
        DataBase.getInstance().setLastDiscountCodeId();
        DataBase.getInstance().setLastLogId();
        DataBase.getInstance().setAllProductsFormDataBase();
        DataBase.getInstance().setAllCategoriesFormDataBase();
        DataBase.getInstance().setAllOffersFromDatabase();
        DataBase.getInstance().getWagePercent();
        AuctionCenter.getInstance().runAuctionServerSockets();
    }


    public void getMessageFromClient(Socket socket) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        socketDataOutputStreamHashMap.put(dataOutputStream, socket);
        while (true) {
            String string;
            try {
                string = dataInputStream.readUTF();
                long time = new Date().getTime();
                System.out.println("message controller : "  +TokenGenerator.getInstance().getTheDecodedMessage(string) + "\ntime: "+ time );
                if(!TokenGenerator.getInstance().getTheDecodedMessage(string).equals("0@getTime@"))
                ipDosChecker.get(socketIp.get(socket)).add(time);
                if (checkDosAttack(socketIp.get(socket))) {
                    if (!blackList.contains(socketIp.get(socket))) {
                        blackList.add(socketIp.get(socket));
                    }
                    ArrayList<Socket> blockedSockets = new ArrayList<>();
                    sendMessageToClient("@Error@" + "You are rushing take it easy little boy.", dataOutputStream);
                    System.out.println("123");
                    socket.close();
                } else if (blackList.contains(socketIp.get(socket))) {
                    sendMessageToClient("@Error@" + "You are rushing take it easy little boy.", dataOutputStream);
                    System.out.println("12345");
                    socket.close();
                } else if (temporaryBlackList.containsKey(socketIp.get(socket))) {
                    if (new Date().getTime() - temporaryBlackList.get(socketIp.get(socket)) > 300000) {
                        temporaryBlackList.remove(socketIp.get(socket));
                        errorCounterForIp.get(socketIp.get(socket)).clear();
                        ServerMessageController.getInstance().processMessage(string, dataOutputStream, socket);
                    } else {
                        sendMessageToClient("@Error@You are temporary banned for " + (300000 - (new Date().getTime() - temporaryBlackList.get(socketIp.get(socket)))) + " milliseconds", dataOutputStream);
                    }
                } else {
                    ServerMessageController.getInstance().processMessage(string, dataOutputStream, socket);
                }
            } catch (IOException e) {
                System.out.println("Error in Connection...");
                break;
            }
        }
        dataInputStream.close();
        dataOutputStream.close();
    }

    public boolean checkDosAttack(String ip) {
        if (ipDosChecker.containsKey(ip)) {
            if (ipDosChecker.get(ip).size() > 10) {
                if (ipDosChecker.get(ip).get(ipDosChecker.get(ip).size() - 1) - ipDosChecker.get(ip).get(ipDosChecker.get(ip).size() - 10) < 800) {
                    System.out.println("Dos time checker" + String.valueOf(ipDosChecker.get(ip).get(ipDosChecker.get(ip).size() - 1) - ipDosChecker.get(ip).get(ipDosChecker.get(ip).size() - 10)));
                    System.out.println("\u001B[35m" + (ipDosChecker.get(ip).get(ipDosChecker.get(ip).size() - 1) - ipDosChecker.get(ip).get(ipDosChecker.get(ip).size() - 9)) + "\u001B[0m");
                    return true;
                }
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

    public void sendMessageToClient(String message, DataOutputStream dataOutputStream) {
        String codedMessage;
        if (message.startsWith("@Error") && !temporaryBlackList.containsKey(socketIp.get(socketDataOutputStreamHashMap.get(dataOutputStream)))) {
            errorCounterForIp.get(socketIp.get(socketDataOutputStreamHashMap.get(dataOutputStream))).add(new Date().getTime());
            if (checkBruteForce(socketIp.get(socketDataOutputStreamHashMap.get(dataOutputStream)))) {
                codedMessage = TokenGenerator.getInstance().getTheCodedMessage(dataOutputStream, "@Error@You are banned for 5 minutes.");
                try {
                    dataOutputStream.writeUTF(codedMessage);
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
        if (message.matches("@Login as \\w+@.*")) {
            codedMessage = TokenGenerator.getInstance().getTheToken(ServerController.getInstance().getAllClients().get(dataOutputStream), message);
        } else {
            codedMessage = TokenGenerator.getInstance().getTheCodedMessage(dataOutputStream, message);
        }
        try {
            dataOutputStream.writeUTF(codedMessage);
            dataOutputStream.flush();
        } catch (IOException e) {
            System.out.println("Error in Sending Packets...");
            try {
                dataOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized void passTime(DataOutputStream dataOutputStream) {
        DiscountCodeCenter.getIncstance().passTime(dataOutputStream);
        OffCenter.getInstance().passTime();
        UserCenter.getIncstance().passTime();
    }

    public Map<DataOutputStream, String> getAllClients() {
        return allClients;
    }
}
