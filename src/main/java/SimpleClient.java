import com.google.gson.internal.$Gson$Preconditions;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) {
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket("localhost", 3030);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
             dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
             dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try{
                System.out.println(dataInputStream.readUTF());
                dataOutputStream.writeUTF(scanner.nextLine());
                dataOutputStream.flush();
            }catch (IOException e){
                System.out.println("Connection is Closed...");
                break;
            }
        }
    }
}
