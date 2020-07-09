package Controller.Server;
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
        while (true) {
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            System.out.println("Hello Client...");
            String string;
            try {
                do {
                    string = dataInputStream.readUTF();
                } while (string.isEmpty());
              System.out.println(string);
              ServerMessageController.getInstance().processMessage(string, dataOutputStream);
            } catch (IOException e) {
                System.out.println("Error in Connection...");
                try {
                    dataInputStream.close();
                    dataOutputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }

    public void sendMessageToClient(String message, DataOutputStream dataOutputStream) {
        System.out.println("@Sen" + message);
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            System.out.println("Error in Sending Packets...");
        }
    }

    public synchronized void passTime(DataOutputStream dataOutputStream) {
        DiscountCodeCenter.getIncstance().passTime(dataOutputStream);
        OffCenter.getInstance().passTime();
    }
}
