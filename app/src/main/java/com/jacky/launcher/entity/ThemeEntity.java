package com.jacky.launcher.entity;

public class ThemeEntity {

    private int res;
    private boolean isSelected;

    public ThemeEntity(int res, boolean isSelected) {
        this.res = res;
        this.isSelected = isSelected;
    }

    public ThemeEntity() {

    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
