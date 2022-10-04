package Entidades;

import Entidades.Color;
import Entidades.Location;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long height; //Поле не может быть null, Значение поля должно быть больше 0
    private String passportID; //Строка не может быть пустой, Длина строки должна быть не меньше 4, Поле не может быть null
    private Color eyeColor; //Поле может быть null
    private Location location; //Поле может быть null
    public Person(String name,Long height,String passportID,Color eyeColor,Location location){
        this.name=name;
        this.height=height;
        this.passportID=passportID;
        this.eyeColor=eyeColor;
        this.location=location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}