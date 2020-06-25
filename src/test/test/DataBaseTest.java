package test;

import Controller.Server.DataBase;
import Models.UserAccount.Manager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {
    private DataBase dataBase = DataBase.getInstance();
    private ArrayList<Manager> allManagers;


    @Test
    void updateAllManagers() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("allManagers.txt"));
        Scanner scanner = new Scanner(bufferedReader);
        String anotherString;
        while(scanner.hasNextLine()){
            anotherString = scanner.nextLine();
            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<Manager>>() {
            }.getType();
            allManagers = gson.fromJson(anotherString, userListType);
        }
        scanner.close();
        assert allManagers != null;
        int size = allManagers.size();
        assertEquals(3, size);
        Gson gson = new Gson();
        allManagers.add(new Manager("Karim", "12345", "ali", "majidi", "majid@gmail.com", "09122197321", 21));
        String string = gson.toJson(allManagers);
        dataBase.updateAllManagers(string);
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader("allManagers.txt"));
        scanner = new Scanner(bufferedReader1);
        allManagers = new ArrayList<>();
        while(scanner.hasNextLine()){
            anotherString = scanner.nextLine();
            Gson gson1 = new Gson();
            Type userListType = new TypeToken<ArrayList<Manager>>() {
            }.getType();
            allManagers = gson1.fromJson(anotherString, userListType);
        }
        assertEquals(size + 1, allManagers.size());
        scanner.close();
    }
}