package client.utility;

import common.Request;
import common.Response;
import common.ResponseCode;
import common.User;
import common.data.Flat;
import exceptions.EmptyArgumentException;
import exceptions.IncorrectInputInScriptException;
import exceptions.IncorrectValueException;
import exceptions.ScriptRecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Класс, который управляет вводом команд
 */
public class Console {

    private Scanner scanner;
    Creator creator;
    public ArrayList<Response> rlist=new ArrayList<>();
    private Stack<File> scriptFileNames = new Stack<File>();
    private Stack<Scanner> scannerStack = new Stack<>();
    private AuthManager authManager;
    public Console(Scanner scanner, Creator creator,AuthManager authManager){
        this.scanner=scanner;
        this.creator=creator;
        this.authManager=authManager;


    }

    /**
     *Метод, который отвечает за ввод пользовательских команд
     */
    public void setList(ArrayList list){
        this.rlist=list;
    }
    public Request workMode(Response serverResponse, User user){
        ResponseCode serverResponseCode=null;
        String serverResponseBody=null;
        if (serverResponse!=null){
            serverResponseBody=serverResponse.getResponseBody();}
        if (serverResponse!=null){
        serverResponseCode=serverResponse.getResponseCode();}
        String userInput="";
        String[] userCommand = {"", ""};
        TypeOfCommand typeOfCommand;
        try {
            do {
                try {
                    if (!scannerStack.isEmpty() && (serverResponseCode == ResponseCode.SERVER_EXIT || (!scanner.hasNextLine() && serverResponseCode == ResponseCode.ERROR)) ){
                        throw new IncorrectInputInScriptException();
                    }
                    while (!scannerStack.isEmpty() && !scanner.hasNextLine()) {
                        scanner.close();
                        scanner = scannerStack.pop();
                        System.out.println("Возвращаюсь из скрипта '" + scriptFileNames.pop().getName() + "'!");
                    }
                    if (!scannerStack.isEmpty()) {

                        userInput = scanner.nextLine();
                        if (!userInput.isEmpty()) {
                            System.out.print("$ ");
                            System.out.println(userInput);
                        }}
                    if (scannerStack.isEmpty()) {
                        System.out.println("Введите желаемую команду");
                        userInput = scanner.nextLine();
                        scriptFileNames=new Stack<>();
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[0] = userCommand[0].toLowerCase();
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    System.out.println("Произошла ошибка при вводе команды!");
                    userCommand = new String[]{"", ""};
                }
                typeOfCommand = checkCommand(userCommand[0], userCommand[1]);


            } while (typeOfCommand == TypeOfCommand.ERROR && scannerStack.isEmpty() || userCommand[0].isEmpty());
            try {
                switch (typeOfCommand) {
                    case OBJECT:
                        Flat flat = creator.create(Integer.parseInt(userCommand[1]));
                        return new Request(userCommand[0], userCommand[1], flat,user);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        //System.out.println(scriptFileNames);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptFileNames.isEmpty() && scriptFileNames.search(scriptFile) != -1) {
                            throw new ScriptRecursionException();
                        }
                        scannerStack.push(scanner);
                        scriptFileNames.push(scriptFile);
                        scanner = new Scanner(scriptFile);
                        System.out.println("Выполняю скрипт '" + scriptFile.getName() + "'...");
                        break;
                }
            } catch (NumberFormatException e){

            }
            catch (FileNotFoundException exception) {
                System.out.println("Файл со скриптом не найден!");
            } catch (ScriptRecursionException e) {
                System.out.println("Скрипты не могут вызываться рекурсивно!");
                throw new IncorrectInputInScriptException();
            }
            }catch(IncorrectInputInScriptException e){
            if (!rlist.isEmpty()){
                System.out.println(rlist.get(rlist.size()-1).getResponseBody());
                rlist.remove(rlist.size()-1);
            }
            System.out.println("Выполнение скрипта прервано!");
            while (!scannerStack.isEmpty()) {
                scanner.close();
                scanner = scannerStack.pop();
            }
        }
        return new Request(userCommand[0], userCommand[1],user);




    }
    private TypeOfCommand checkCommand(String command, String argument) {
        try {
            switch (command) {
                case "":
                    return TypeOfCommand.ERROR;
                case "help":
                case "show":
                case "info":
                case "clear":
                case "exit":

                    if (!argument.isEmpty()) throw new IncorrectValueException();
                    return TypeOfCommand.OK;

                case "replace_if_lower":
                case "insert":
                case "replace_if_greater":
                case "update":
                    if (argument.isEmpty()) throw new EmptyArgumentException();
                    return TypeOfCommand.OBJECT;
                case "remove_key":
                case "remove_lower_key":
                case "remove_all_by_number_of_rooms":
                case "count_greater_than_furnish":
                case "filter_starts_with_name":
                    if (argument.isEmpty()) throw new EmptyArgumentException();
                    return TypeOfCommand.OK;
                case "execute_script":
                    if (argument.isEmpty()) throw new EmptyArgumentException();
                    return TypeOfCommand.SCRIPT;
                default:
                    System.out.println("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
                    return TypeOfCommand.ERROR;
            }
        } catch (IncorrectValueException e) {
            //if (!scannerStack.isEmpty()) System.err.println("У данной команды нет аргумента!");

        }
        catch (EmptyArgumentException e) {
            //if (!scannerStack.isEmpty()) System.err.println("У данной команды должен быть аргумент");

        }
        return TypeOfCommand.OK;
    }
}












