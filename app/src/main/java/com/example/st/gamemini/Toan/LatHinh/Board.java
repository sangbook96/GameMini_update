package com.example.st.gamemini.Toan.LatHinh;

import com.example.st.gamemini.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by ST on 10/16/2017.
 */

public class Board {
    protected ArrayList<Hinh>arr=new ArrayList<Hinh>();
    protected int luotClick=0,maxitem=0;
    protected int getpos=-1,kiemtra=-1,soHang=0,sumItem,diem=0,diemcao;
    protected Random rd=new Random();
    protected int[] mangHinh = new int[]{
            R.drawable.animals_1, R.drawable.animals_2,
            R.drawable.animals_3, R.drawable.animals_4,
            R.drawable.animals_5, R.drawable.animals_6,
            R.drawable.animals_7, R.drawable.animals_8,
            R.drawable.animals_9, R.drawable.animals_10,
            R.drawable.animals_11, R.drawable.animals_12,
            R.drawable.animals_13, R.drawable.animals_14,
            R.drawable.animals_15, R.drawable.animals_16,
            R.drawable.animals_17, R.drawable.animals_18,
            R.drawable.animals_19, R.drawable.animals_20,
            R.drawable.animals_21, R.drawable.animals_22,
            R.drawable.animals_23, R.drawable.animals_24,
            R.drawable.animals_25, R.drawable.animals_26,
            R.drawable.animals_27, R.drawable.animals_28,
    };
    public ArrayList<Hinh> random(int b) {
        int temp = 0;
        //thêm toàn bộ các phần tử vào list (tạo mảng tạm)
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < mangHinh.length; i++) {
            list.add(i);
        }

        //random và xóa phần tử đã thêm trong list đi
        for (int i = temp; i < b; i++) {
            temp = rd.nextInt(list.size());
            arr.add(new Hinh(mangHinh[i], false, false));
            arr.add(new Hinh(mangHinh[i], false, false));
            list.remove(temp);
        }
        Collections.shuffle(arr);//xáo trộn các item
        maxitem=arr.size()+4;
        return arr;
    }
    public void Level(int soHang) {

        int soCot=0;
        soCot=(soHang*soHang)/2;
        int b = 0;random(soCot);
        if (soHang>2&&soHang%2==0){
            soCot=soHang*soHang;
        }else if (soHang>2&&soHang%2!=0){
            soCot=soHang*2+soHang;
        }

    }
    /*public void KiemTra(int postion) {
        //kiểm tra số lượt click
        if (luotClick < (arr.size() + 4)) {
            luotClick++;
            maxitem--;
        } else {

        }
        Log.d("Số lượt click ", "" + luotClick);
        if (getpos == -1) {

            getpos = postion;
            kiemtra = arr.get(postion).getHinh();

            Log.d("kt", "" + kiemtra);
        } else {
            if (getpos != postion) {
                arr.get(postion).setSelected(true);
                if (kiemtra == arr.get(postion).getHinh()) {
                    Log.d("", "" + getpos + " " + postion);
                    //arr.get(postion).setSelected(true);
                    arr.get(postion).setSelectedView(true);
                    arr.get(getpos).setSelectedView(true);
                    getpos = -1;

                } else {
                    if (getpos != -1) {
                        Log.d("Item chọn sai", "" + getpos + "   " + postion);
                        arr.get(getpos).setSelected(false);
                        arr.get(postion).setSelected(false);
                        getpos = -1;
                    }
                }
            }
        }
    }*/


}
