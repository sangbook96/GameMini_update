package com.example.st.gamemini.Toan.LatHinh;

/**
 * Created by ST on 9/18/2017.
 */

public class Hinh {
    int hinh;
    boolean selected = false;
    boolean selectedView=false;

    public Hinh(int hinh, boolean selected, boolean selectedView) {

        this.hinh = hinh;
        this.selected = selected ;
        this.selectedView = selectedView;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelectedView() {
        return selectedView;
    }

    public void setSelectedView(boolean selectedView) {
        this.selectedView = selectedView;
    }
}
