package client;

import client.utility.AuthManager;
import client.utility.Console;
import client.utility.Creator;

import java.util.InputMismatchException;
import java.util.Scanner;

//главный класс для клиента. Создает клиента и не только и запускает
public class App {

    public static void main(String[] args) {
        System.out.println("Клиент запущен");
       // System.out.println("Введите порт");
        int port=8088;
        Scanner scanner = new Scanner(System.in);
        try{
            //port= scanner.nextInt();
            System.out.println("Значение установлено");
        } catch (InputMismatchException e) {
            System.out.println("Некорректное значение порта, Установлен порт по умолчанию (2810)");
        }
        Scanner in= new Scanner(System.in);
        Creator creator=new Creator(in);
        AuthManager authManager = new AuthManager(in);
        Console console = new Console(in,creator, authManager);
        Client client = new Client("localhost", port, console, authManager);
        client.run();
        scanner.close();
    }



}

