package Controller.Server;

import Controller.Client.ClientController;
import Models.UserAccount.UserAccount;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;

public class ServerController {
    private static ServerController serverController;
    private ServerSocket serverSocket;
    private Map<DataOutputStream, String> allClients;

    private ServerController() {
        allClients = new HashMap<>();
    }
    private HashMap<String, Integer> onlineSupporters = new HashMap<>();

    public DataOutputStream findDataStreamWithUsername(String username) {
        for (DataOutputStream dataOutputStream : allClients.keySet()) {
            if (allClients.get(dataOutputStream).equals(username)) {
                return dataOutputStream;
            }
        }
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
//        AuctionCenter.getInstance().runAuctionServerSockets();
    }



    public void getMessageFromClient(Socket socket) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        while (true) {
            String string;
            try {
                do {
                    string = dataInputStream.readUTF();
                } while (string.isEmpty());
                ServerMessageController.getInstance().processMessage(string, dataOutputStream);
            } catch (IOException e) {
                System.out.println("Error in Connection...");
                break;
            }
        }
        dataInputStream.close();
        dataOutputStream.close();
    }

    public void sendMessageToClient(String message, DataOutputStream dataOutputStream) {
        String codedMessage;
        if (message.matches("@Login as \\w+@")) {
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
