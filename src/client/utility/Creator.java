package client.utility;

import common.data.*;
import exceptions.IncorrectValueException;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Спрашивает пользователя значения для нового экземпдяра Flat
 */

public class Creator {
    private boolean t=true;
    private Scanner scanner;
    public Creator(Scanner scanner){
        this.scanner=scanner;
    }
    /**
     * Установка сканера для нового ввода пользователя
     * @param scanner сканнер
     */
    public void setScanner(Scanner scanner){
        this.scanner=scanner;
    }
    /**
     *
     * @return Scanner, который используется для пользовательсеого ввода
     */
    public Scanner getScanner(){
        return this.scanner;
    }

    /**
     * Узнает имя владельца квартиры
     * @return Имя пользователя
     */
    public Flat create(int id){
        return new Flat(id,newName(), newCoordinates(),  LocalDateTime.now(), newArea(),newNumberOfRooms(),newFurnish(),newView(),newTransport(),newHouse());
    }
    private String newName(){
        t=true;
        String name="";
        while (t) {
            try {

                System.out.println("Введите имя владельца");
                name = scanner.nextLine().trim();
                if (name.equals("")) throw new IncorrectValueException();
                t = false;
            } catch (IncorrectValueException e) {
                System.err.println("Имя не может быть пустой строкой");

            }
        }
        return name;}

    /**
     * Узнает координаты квартиры
     * @return Координаты квартиры
     */
    private Coordinates newCoordinates(){
        float x=5.5f;
        float y= 6.6f;
        t=true;
        System.out.println("Введите координаты местоположения");
        while(t){
            try{
                System.out.println("x:");
                x=Float.parseFloat(scanner.nextLine().trim());//!!!!потумкать над исключениями
                if (x<=-107) throw new IncorrectValueException();
                t=false;
            }catch (NumberFormatException e){
                System.err.println("Координата может быть только числом");
            }catch(IncorrectValueException e){
                System.err.println("Координата x должна быть больше -107");
            }
        }
        t=true;
        while(t){
            try{
                System.out.println("y:");
                y=Float.parseFloat(scanner.nextLine().trim());
                t=false;
            }catch (NumberFormatException e){
                System.err.println("Координата может быть только числом");
            }
        }return new Coordinates(x,y);

    }

    /**
     * Узнает площадь квартиры
     * @return площадь квартиры
     */
    private Float newArea(){
        t=true;
        Float area=5.0f;
        while(t){
            try{
                System.out.println("Введите площадь квартиры");
                area=Float.parseFloat(scanner.nextLine().trim());
                if (area<=0 || area>741) throw new IncorrectValueException();
                t=false;

            }catch(NumberFormatException e){
                System.err.println("Площадь может быть только  числом");
                System.out.println();
            }catch (IncorrectValueException e){
                System.err.println("Площадь задается в диапазоне от 1 до 741 включительно.");
            }
        }
        return area;
    }

    /**
     * Узнает количество комнат в квартире
     * @return количество комнат
     */
    private Integer newNumberOfRooms(){
        t=true;
        Integer rooms=5;
        while(t){
            try{
                System.out.println("Введите количество комнат");
                rooms=Integer.parseInt(scanner.nextLine().trim());
                if (rooms<=0 || rooms>11) throw new IncorrectValueException();
                t=false;
            }catch (NumberFormatException e){
                System.err.println("Количество комнат- это целое число");
            }catch(IncorrectValueException e){
                System.err.println("Количество комнат задается в диапазоне от 1 до 11 включительно. Большее количество позволено только Путину.");
            }
        }
        return rooms;

    }

    /**
     * Узнает состояние отделки
     * @return внешний вид
     */
    private Furnish newFurnish(){
        String furnish="";
        Furnish returnn=Furnish.FINE;
        for (Furnish f:Furnish.values()){
            furnish+="\n"+f.name();
        }
        t=true;
        while(t){
            try{
                System.out.println("Введите состояние отделки");
                System.out.println("Выберете из следующих вариантов: "+furnish);
                returnn= Furnish.valueOf(scanner.nextLine().trim().toUpperCase());
                t=false;
            }catch(IllegalArgumentException e){
                System.err.println("Значение должно быть выбрано из заданного списка");
                System.out.println();
            }
        }
        return returnn;
    }

    /**
     * Узнает вид из окна квартиры
     * @return вид из окна
     */
    private View newView(){
        String view="";
        for (View f : View.values()) {
            view += "\n" + f.name();
        }
        View returnn=View.BAD;
        t=true;
        while (t) {
            try {
                System.out.println("Введите вид из окна");
                System.out.println("Выберете из следующих вариантов: " + view);
                returnn=View.valueOf(scanner.nextLine().trim().toUpperCase());
                t=false;
            }catch(IllegalArgumentException e){
                System.err.println("Значение должно быть выбрано из заданного списка");
                System.out.println();
            }
        }
        return returnn;
    }

    /**
     * Узнает, как много транспорта ходит
     * @return количество транспорта
     */
    private Transport newTransport(){
        String transport="";
        for (Transport f:Transport.values()){
            transport+="\n"+f.name();
        }
        Transport returnn=Transport.LITTLE;
        t=true;
        while(t){
            try {
                System.out.println("Как много транспорта ходит?");
                System.out.println("Выберете из следующих вариантов: " + transport);
                returnn = Transport.valueOf(scanner.nextLine().trim().toUpperCase());
                t=false;
            }catch(IllegalArgumentException e){
                System.err.println("Значение должно быть выбрано из заданного списка");
                System.out.println();
            }
        }
        return returnn;
    }

    /**
     * Узнает дом, в котором находится квартира
     * @return Дом
     */
    private House newHouse(){
        boolean t=true;
        do {
            System.out.println("Хотите указать информацию о доме?(да/нет)");
            String read = scanner.nextLine().trim().toLowerCase();
            if (read.equals("да")) {
                t=false;
                String name = nameHouse();
                Long year = yearHouse();
                Long numberOfFloors = floorsHouse();
                return new House(name, year, numberOfFloors);
            } else if (read.equals("нет")) {
                t=false;
                return null;
            }
            System.err.println("Необходимо ввести \"да\" или \"нет\"");
        }while (t);
        return null;

    }

    /**
     * Узнает название комплекса, в котором находится квартира
     * @return название комплекса
     */
    private String nameHouse(){
        t=true;
        String nAme="";
        while(t){
            try{
                System.out.println("Введите название комплекса");
                nAme= scanner.nextLine().trim();
                if (nAme.equals("")) throw new IncorrectValueException();
                t=false;
            }catch(IncorrectValueException e){
                System.err.println("Название комплекса не может быть пустой строкой");
            }
        }
        return nAme;
    }

    /**
     * Узнает сколько лет назад был сдан дом
     * @return сколько лет назад был сдан дом
     */
    private Long yearHouse(){
        t=true;
        Long year=4L;
        while(t){
            try{
                System.out.println("Введите, сколько лет назад был сдан дом");
                year=Long.valueOf(scanner.nextLine().trim());
                if (year.compareTo(0L)<=0 || year.compareTo(846L)>0) throw new IncorrectValueException();
                t=false;
            }catch(NumberFormatException e){
                System.err.println("Формат введенного значения неверен");
            }catch(IncorrectValueException e){
                System.err.println("Значение задается из диапазона от 0 до 846 включительно");
            }
        }
        return year;
    }

    /**
     * Узнает количество этажей в доме
     * @return количсетво этажей
     */
    private Long floorsHouse(){
        t=true;
        Long floors=4L;
        while(t){
            try{
                System.out.println("Введите количество этажей в доме");
                floors=Long.valueOf(scanner.nextLine().trim());
                if (floors.compareTo(0L)<=0 ) throw new IncorrectValueException();
                t=false;
            }catch(NumberFormatException e){
                System.err.println("Формат введенного значения неверен");
            }
            catch(IncorrectValueException e){
                System.err.println("Значение должно быть больше 0");
            }
        }
        return floors;
    }

}
