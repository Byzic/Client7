package client.utility;

import exceptions.IncorrectValueException;

import java.io.Console;
import java.util.Scanner;

public class AuthAsker {
    private Scanner userScanner;

    public AuthAsker(Scanner userScanner) {
        this.userScanner = userScanner;
    }
    public String askLogin(){
        String login;
        while (true){
            try{
                System.out.println("Введите логин");
                login = userScanner.nextLine().trim();
                if (login.equals("")) throw new IncorrectValueException();
                break;
            }catch(IncorrectValueException e){
                System.out.println("Логин не может быть пустым!");
            }

        }
        return login;
    }
    public String askPassword(){
        String password;
        while (true){
            try{
                System.out.println("Введите пароль");
                Console console = System.console();
                //char [] c = console.readPassword();
                password ="1234";//String.copyValueOf(c);
                if (password.equals("")) throw new IncorrectValueException();
                break;

            }catch(IncorrectValueException e){
                System.out.println("Пароль не может быть пустым");
            }

        }
        return password;
    }
    public boolean askQuestion(String question){
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                System.out.println(finalQuestion);
                System.out.print("> ");
                answer = userScanner.nextLine().trim();
                if (!answer.equals("+") && !answer.equals("-")) throw new IncorrectValueException();
                break;
            } catch (IncorrectValueException e) {
                System.out.println("Ответ должен быть либо '+', либо '-'!");
            }
        }
        return answer.equals("+");
    }

}
