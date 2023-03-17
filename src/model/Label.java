package model;

/**
 * Model of Label, contains getters/setters for fields of class
 * @author ilestegor
 */
public class Label {
    private String label;

    public Label(String label) {
        this.label = label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
