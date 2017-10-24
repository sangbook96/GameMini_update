package com.example.st.gamemini.Toan.HightScore;

/**
 * Created by ST on 10/18/2017.
 */

public class Score {
    int idDiem;
    String Name;
    int Diem;
    String ThoiGian;
    int Level;
    String LoaiGame;

    public Score(int idDiem, String name, int diem, String thoiGian, int level, String loaiGame) {
        this.idDiem = idDiem;
        Name = name;
        Diem = diem;
        ThoiGian = thoiGian;
        Level = level;
        LoaiGame = loaiGame;
    }

    public int getIdDiem() {
        return idDiem;
    }

    public void setIdDiem(int idDiem) {
        this.idDiem = idDiem;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getDiem() {
        return Diem;
    }

    public void setDiem(int diem) {
        Diem = diem;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String thoiGian) {
        ThoiGian = thoiGian;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public String getLoaiGame() {
        return LoaiGame;
    }

    public void setLoaiGame(String loaiGame) {
        LoaiGame = loaiGame;
    }
}
