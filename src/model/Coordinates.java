package model;

import exception.FiledIsNotCorrectException;
import manager.validator.ModelValidator;

/**
 * Model of Coordinates, contains getters/setter for fields of clas
 *
 * @author ilestegor
 */
public class Coordinates {
    private Long x;
    private float y;
    private final ModelValidator modelValidator = new ModelValidator();

    public Coordinates() {
    }

    public Coordinates(Long x, float y) {
        this.x = x;
        this.y = y;
    }


    public void setX(Long x) {
        if (modelValidator.validateCoordinateX(x)) {
            this.x = x;
        } else throw new FiledIsNotCorrectException();
    }

    public void setY(float y) {
        if (modelValidator.validateCoordinateY(y)) {
            this.y = y;
        } else throw new FiledIsNotCorrectException();
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
