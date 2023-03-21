package model;

import manager.validator.CoordinateXValidator;
import manager.validator.CoordinateYValidator;

/**
 * Model of Coordinates, contains getters/setter for fields of clas
 *
 * @author ilestegor
 */
public class Coordinates {
    private Long x;
    private float y;

    public Coordinates() {
    }


    public Coordinates(Long x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(Long x) {
        if (CoordinateXValidator.validate(x)) {
            this.x = x;
        }
    }

    public void setY(float y) {
        if (CoordinateYValidator.validate(y)) {
            this.y = y;
        }
    }

    public Long getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "x= " + x + ", y= " + y;
    }
}
