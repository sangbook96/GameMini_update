package com.example.st.gamemini.Toan.KuKuBe;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ST on 10/24/2017.
 */

public class TaoMau  {
    static int dapAn;
    Random rd=new Random();
    String string[]=new String[]{
            "#2b8cf9","#58ACFA"
            ,"#FF00FF","#F781F3"
            ,"#00BFFF","#58D3F7"
            ,"#9A2EFE","#BE81F7"
            ,"#FF0040","#FA5882"
            ,"#3ADF00","#64FE2E"
            ,"#FF00BF","#FA58D0"
    };
    public ArrayList taoBangMau(int n){
        ArrayList<String>a=new ArrayList<>();

        int x= rd.nextInt(n);
        dapAn=x;
        int y=rd.nextInt(string.length-1);
        if (y%2==1){
            y--;
        }
        for (int i=0;i<n;i++){
            if (i==x){
                a.add(string[y]);
            }else {
                a.add(string[y+1]);
            }
        }
        return a;
    }
}
