package com.example.st.gamemini.Toan.Main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by ST on 23/08/2017.
 */

public class ImageConverter {
    public static Bitmap getRoundedCornerBitMap(Bitmap bitmap,int pixel){
        Bitmap out =Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(out);
        final int color=0xff424242;
        final Paint paint=new Paint();
        final Rect rect=new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        final RectF rectF=new RectF(rect);
        final float roundPx=pixel;

        paint.setAntiAlias(true);
        canvas.drawARGB(0,0,0,0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF,roundPx,roundPx,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,rect,rect,paint);
        return out;

    }
}
