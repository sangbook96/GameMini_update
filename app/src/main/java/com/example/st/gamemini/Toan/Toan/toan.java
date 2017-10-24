package com.example.st.gamemini.Toan.Toan;

/**
 * Created by ST on 9/14/2017.
 */

public class toan {

    int number;
    boolean selected = false;
    boolean selectedView=false;

    public toan(int number, boolean selected,boolean selectedView) {
        this.number = number;
        this.selected = selected;
        this.selectedView=selectedView;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getNumber() {

        return number;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isSelectedView() {
        return selectedView;
    }

    public void setSelectedView(boolean selectedView) {
        this.selectedView = selectedView;
    }
}
