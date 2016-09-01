package quotestoday.tek.com.quotestoday.quotestoday;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import quotestoday.tek.com.quotestoday.R;
import quotestoday.tek.com.quotestoday.utils.ColorUtils;

/**
 * Created by bukia on 2/11/2016.
 */
public class TestBitmap extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_bitmap);

        String text1="Hello world how are you doing sir i'm fine thank you. :) how will you go to school today sir?";
        String text2="- by author";

        Typeface danielTypeface=Typeface.createFromAsset(getAssets(),"fonts/daniel.ttf");

        Bitmap bitmap=createBitmap(text1, text2, 800, 500, danielTypeface, 40);
        ImageView imageView=(ImageView)findViewById(R.id.imgTestBitmap);
        imageView.setImageBitmap(bitmap);
    }

    public Bitmap createBitmap(String quote, String author, int width, int height, Typeface typeface, int textSize) {
        Bitmap bitmap=Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c=new Canvas(bitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.LINEAR_TEXT_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor(ColorUtils.getBackgroudColor()));
        c.drawPaint(paint);

        c.save();

        Matrix matrix=new Matrix();
        matrix.setScale(0.5f,0.5f);
        Bitmap bmp= BitmapFactory.decodeResource(getResources(),R.drawable.transparent_logo);
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
}
