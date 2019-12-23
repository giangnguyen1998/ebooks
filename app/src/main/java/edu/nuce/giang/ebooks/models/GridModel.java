package edu.nuce.giang.ebooks.models;

public class GridModel {
    String fieldName;
    Integer fieldImage;
    boolean isSelected = false;

    public GridModel(String fieldName, Integer fieldImage) {
        this.fieldName = fieldName;
        this.fieldImage = fieldImage;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getFieldImage() {
        return fieldImage;
    }

    public void setFieldImage(Integer fieldImage) {
        this.fieldImage = fieldImage;
    }


}
