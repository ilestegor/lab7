package model;

import manager.validator.ModelValidator;

/**
 * Model of Label, contains getters/setters for fields of class
 *
 * @author ilestegor
 */
public class Label {
    private String label;
    private final ModelValidator modelValidator = new ModelValidator();

    public Label(String label) {
        this.label = label;
    }

    public void setLabel(String label) {
        if (modelValidator.validateLabel(label)) {
            this.label = label;
        }
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
