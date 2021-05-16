package common.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс Квартир, хранится в коллекции
 */
public class Flat implements Comparable<Flat>, Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float area; //Максимальное значение поля: 741, Значение поля должно быть больше 0
    private Integer numberOfRooms; //Максимальное значение поля: 11, Значение поля должно быть больше 0
    private Furnish furnish; //Поле не может быть null
    private View view; //Поле не может быть null
    private Transport transport; //Поле не может быть null
    private House house; //Поле может быть null


    public Flat(int id, String name, Coordinates coordinates, java.time.LocalDateTime creationDate, Float area, Integer numberOfRooms, Furnish furnish, View view, Transport transport, House house){
        this.id=id;               //!!!!!!!!! переопределить equals в run.Coordinates и run.House
        this.name=name;
        this.coordinates=coordinates;
        this.creationDate=creationDate;
        this.area=area;
        this.numberOfRooms=numberOfRooms;
        this.furnish=furnish;
        this.view=view;
        this.transport=transport;
        this.house=house;
    }
    /**
     * @return ID квартиры
     */
    public Integer getID(){
        return id;
    }
    /**
     * @return Имя владельца квартиры
     */
    public String getName() {
        return name;
    }
    /**
     * @return координаты квартиры
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
    /**
     * @return дата занесения в протокол
     */
    public java.time.LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @return площадь квартиры
     */
    public Float getArea(){
        return area;
    }
    /**
     * @return Количество комнат в квартире.
     */
    public Integer getNumberOfRooms(){
        return numberOfRooms;
    }

    /**
     *
     * @return Состояние отделки
     */
    public Furnish getFurnish(){
        return furnish;
    }
    /**
     * @return вид из окна
     */
    public View getView(){
        return view;
    }
    /**
     * @return кол-во траспорта
     */
    public Transport getTransport(){
        return transport;
    }
    /**
     * @return Дом, в котором находится квартира
     */
    public House getHouse(){
        return house;
    }
    @Override
    public int compareTo(Flat f) {
        return area.compareTo(f.getArea());

    }
    @Override
    public String toString() {
        String info = "";
        info += "\u001B[37m"+"\u001B[36m"+"Квартира № " + id+"\u001B[36m"+"\u001B[37m";
        info += " (дата занесения в протокол " + creationDate.toLocalDate() + " " + creationDate.toLocalTime() + ")";
        info += "\n Имя владельца: " + name;
        info += "\n Местоположение: " + coordinates;
        info += "\n Площадь: " + area;
        info += "\n Количество комнат: " + numberOfRooms;
        info += "\n Отделка: " + furnish;
        info += "\n Вид из окна: " + view;
        info += "\n Как много транспорта ходит: " + transport;
        info += "\n Информация о доме: " + house;
        return info;
    }
    @Override
    public boolean equals(Object f) {
        if (this == f) return true;
        if (f == null || getClass() != f.getClass()) return false;
        Flat that = (Flat) f;
        return name.equals(that.getName()) && coordinates.equals(that.getCoordinates()) && creationDate.equals(that.getCreationDate()) &&
                (area.equals(that.getArea())) && (numberOfRooms == that.getNumberOfRooms()) &&
                (furnish == that.getFurnish()) && (view == that.getView()) &&
                (transport==that.getTransport()) && house.equals(that.getHouse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, area, numberOfRooms, furnish, view, transport, house);
    }
}
