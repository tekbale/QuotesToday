package quotestoday.tek.com.quotestoday.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.util.Random;

import quotestoday.tek.com.quotestoday.R;

/**
 * Created by bukia on 2/10/2016.
 */
public class ImageUtils {
    public static synchronized Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public synchronized static Bitmap createBitmap(Context context,String quote, String author, int width, int height,
                                                   Typeface typeface, int textSize, int backgroundColor) {
        if(backgroundColor<=0)
            backgroundColor=Color.parseColor(ColorUtils.getBackgroudColor());

        Bitmap bitmap=Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c=new Canvas(bitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.LINEAR_TEXT_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backgroundColor);
        c.drawPaint(paint);

        c.save();

        Matrix matrix=new Matrix();
        matrix.setScale(0.5f,0.5f);
        Bitmap bmp= BitmapFactory.decodeResource(context.getResources(), R.drawable.transparent_logo);
        c.translate(0,bitmap.getHeight()-bmp.getHeight()/2);
        Paint p = new Paint(Paint.FILTER_BITMAP_FLAG);
        c.drawBitmap(bmp, matrix, p);

        c.restore();

        String text1=quote;//"Hello world how are you doing sir i'm fine thank you. :) how will you go to school today sir?";

        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG|Paint.LINEAR_TEXT_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(typeface);

        Rect bounds = new Rect();
        Paint paint1 = new Paint();
        paint1.setTextSize(textSize);
        paint1.getTextBounds(text1, 0, text1.length(), bounds);
        int height1=(int)Math.ceil(bounds.height());
        int width1=(int)textPaint.measureText(text1);//(int)Math.ceil(bounds.width());

        int xOffset1 = (bitmap.getWidth()-width1-20)/2;
        int yOffset1 = (bitmap.getHeight()-height1)/4;
        c.translate(20, yOffset1);

        StaticLayout textLayout=new StaticLayout(text1,textPaint,bitmap.getWidth()-20, Layout.Alignment.ALIGN_CENTER,1.0f,1.0f,false);

        textLayout.draw(c);

        //c.restore();

        String text2=author;//+" Mark Zuckerberg ANthony hopikns hosldkfjlk";// lskdjflkj lskjdflkj sldkfjl skdf lkjsdl fkjsldf kjsdlf";//"- by author";
        Rect bounds2 = new Rect();
        Paint  paint2 = new Paint();
        paint2.setTextSize(textSize);
        paint2.getTextBounds(text2, 0, text2.length(), bounds2);
        int width2=(int)textPaint.measureText(text2);//(int)Math.ceil(bounds2.width());

        /**** get single line height ****/
        String test1="Hello";
        Rect boundsTest1=new Rect();
        Paint paintTest1=new Paint();
        paintTest1.setTextSize(textSize);
        paintTest1.getTextBounds(test1, 0, test1.length(), boundsTest1);
        int heightSingleLine=(int)Math.ceil(boundsTest1.height());


        if((width2+40)<bitmap.getWidth()) {
            c.translate(bitmap.getWidth() - width2 - 30, yOffset1+height1+heightSingleLine);
            StaticLayout textLayout2=new StaticLayout(text2,textPaint,bitmap.getWidth(), Layout.Alignment.ALIGN_NORMAL,1.0f,1.0f,false);
            textLayout2.draw(c);
        } else {
            c.translate(bitmap.getWidth() / 2 - 30, yOffset1+height1+heightSingleLine);
            StaticLayout textLayout2=new StaticLayout(text2,textPaint,bitmap.getWidth()/2, Layout.Alignment.ALIGN_NORMAL,1.0f,1.0f,false);
            textLayout2.draw(c);
        }

        c.save();

        return bitmap;
    }

    public synchronized static int getToolbarDrawableId() {
        Random random=new Random(System.currentTimeMillis());
        int index=1+random.nextInt(11);
        return getDrawable(index);
    }

    private static int getDrawable(int index) {
        if(index==1)
            return R.drawable.landscape_1;
        else if(index==2)
            return R.drawable.landscape_2;
        else if(index==3)
            return R.drawable.landscape_3;
        else if(index==4)
            return R.drawable.landscape_4;
        else if(index==5)
            return R.drawable.landscape_5;
        else if(index==6)
            return R.drawable.landscape_6;
        else if(index==7)
            return R.drawable.landscape_7;
        else if(index==8)
            return R.drawable.landscape_8;
        else if(index==9)
            return R.drawable.landscape_9;
        else if(index==10)
            return R.drawable.landscape_10;
        else if(index==11)
            return R.drawable.landscape_11;
        else
            return R.drawable.landscape_2;
    }
}
