package common.data;

import java.io.Serializable;

/**
 * Класс домов для квартиры
 */
public class House implements Serializable {
    private String name; //Поле не может быть null
    private Long year; //Максимальное значение поля: 846, Значение поля должно быть больше 0
    private Long numberOfFloors; //Значение поля должно быть больше 0
    public House(String name,Long year,Long numberOfFloors){
        this.name=name;
        this.year=year;
        this.numberOfFloors=numberOfFloors;
    }
    public String getName(){
        return name;
    }
    public Long getYear(){
        return year;
    }
    public Long getNumberOfFloors(){
        return numberOfFloors;
    }

    @Override
    public String toString() {
        return "Название комплекса: " + name  +
                ", год сдачи: " + (2021-Integer.parseInt(year.toString())) +
                ", количество этажей: " + numberOfFloors ;
    }
}
