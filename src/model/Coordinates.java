package model;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Класс коордиант
 */
public class Coordinates{
    private Long x;
    private float y;

    public Coordinates() {
    }

    public void setX(Long x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Coordinates(Long x, float y){
        this.x = x;
        this.y = y;
    }


    public Long getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Коордианаты: " +
                "x= " + x +
                ", y= " + y;
    }
}
