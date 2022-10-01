package Entidades;

public class Coordinates {
    private Double x; //Поле не может быть null
    private float y; //Значение поля должно быть больше -43
    public Coordinates(Double x,float y){
        this.x=x;
        this.y=y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
