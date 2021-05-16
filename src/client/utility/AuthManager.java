package client.utility;

import common.Request;
import common.User;

import java.util.Scanner;

public class AuthManager {
    private final String loginCommand = "login";
    private final String registerCommand = "register";
    private Scanner scanner;
    public AuthManager(Scanner scanner){
        this.scanner=scanner;
    }
    public Request handle() {
        AuthAsker authAsker = new AuthAsker(scanner);
        String command = authAsker.askQuestion("У вас уже есть учетная запись?") ? loginCommand : registerCommand;
        User user = new User(authAsker.askLogin(), authAsker.askPassword());
        return new Request(command, "", user);
    }

}
