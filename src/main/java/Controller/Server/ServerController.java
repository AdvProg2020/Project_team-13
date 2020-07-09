package Controller.Server;

import Controller.Client.ClientController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    private static ServerController serverController;
    private ServerSocket serverSocket;

    public static ServerController getInstance(){
        if(serverController == null){
            synchronized (ServerController.class) {
                if(serverController == null){
                    serverController = new ServerController();
                }
            }
        }
        return serverController;
    }

    public static void main(String[] args){
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
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                break;
            }
            System.out.println("Client Connected!!!");
            Socket finalSocket = socket;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        getMessageFromClient(finalSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void runServer(){
        DataBase.getInstance().setAllUsersListFromDateBase();
        DataBase.getInstance().setAllRequestsListFromDateBase();
        DataBase.getInstance().setAllDiscountCodesListFromDateBase();
        DataBase.getInstance().setLastRequestId();
        DataBase.getInstance().setLastDiscountCodeId();
        DataBase.getInstance().setLastLogId();
        DataBase.getInstance().setAllProductsFormDataBase();
        DataBase.getInstance().setAllCategoriesFormDataBase();
        DataBase.getInstance().setAllOffersFromDatabase();
    }



    public void getMessageFromClient(Socket socket) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        while (true) {
            String string;
            try {
                string = dataInputStream.readUTF();
                while (true) {
                    if (!string.isEmpty()) {
                        break;
                    }
                }
                ServerMessageController.getInstance().processMessage(string, dataOutputStream);
            } catch (IOException e) {
                System.out.println("Error in Connection...");
                try {
                    dataInputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }

    public void sendMessageToClient(String message, DataOutputStream dataOutputStream) {


    }

    public synchronized void passTime(DataOutputStream dataOutputStream) {
        DiscountCodeCenter.getIncstance().passTime(dataOutputStream);
        OffCenter.getInstance().passTime();
    }
}
