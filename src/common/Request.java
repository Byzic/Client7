package common;

import common.data.Flat;

import java.io.Serializable;

//Класс для формирования запроса
// екземпляр состоит из названия команды, аргумента, экземпдяра квартиры(если есть)
public class Request implements Serializable {
    private String commandName;
    private String argument;
    private Flat objectArgument;
    private User user;

    public Request(String commandName, String argument, Flat objectArgument, User user) {
        this.commandName = commandName;
        this.argument = argument;
        this.objectArgument =  objectArgument;
        this.user = user;
    }

    public Request(String commandName, String argument, Flat objectArgument) {
        this.commandName = commandName;
        this.argument = argument;
        this.objectArgument = objectArgument;
    }
    public Request(String commandName, String argument, User user) {
        this.commandName = commandName;
        this.argument = argument;
        this.objectArgument = null;
        this.user = user;
    }

    public Request(String commandName, String argument) {
        this.commandName = commandName;
        this.argument = argument;
        this.objectArgument = null;
    }

    public Request(User user) {
        this.commandName = "";
        this.argument = "";
        this.objectArgument = null;
        this.user=user;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgument() {
        return argument;
    }

    public Flat getObjectArgument() {
        return objectArgument;
    }
    public User getUser() {
        return user;
    }

    public boolean isEmpty() {
        return commandName.isEmpty() && argument.isEmpty() && objectArgument == null;
    }
}
