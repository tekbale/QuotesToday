package quotestoday.tek.com.quotestoday.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by bukia on 2/12/2016.
 */
public class ColorUtils {
    final static String[] colors=new String[]{
            "#303F9F",   "#448AFF",   "#3F51B5",   "#0288D1",
            "#03A9F4",   "#E040FB",   "#00796B",   "#009688",
            "#E040FB",   "#388E3C",   "#4CAF50",   "#FF5722",
            "#607D8B",   "#C2185B",   "#E91E63",   "#FFC107",
            "#FFC107",   "#F44336",   "#AFB42B",   "#CDDC39",
            "#795548"
        };
    static Random random;
    static String prevString=null;
    public static synchronized String getBackgroudColor() {
       /* List<String> list=Arrays.asList(colors);
        Collections.shuffle(list);
        list.toArray(colors);*/

        random=new Random(System.currentTimeMillis());
        int index=random.nextInt(colors.length-1);
        while(prevString!=null && prevString.equalsIgnoreCase(colors[index]))
            index=random.nextInt(colors.length-1);
        System.out.println("prevColor="+prevString+", curColor="+colors[index]);
        prevString=colors[index];
        return colors[index];
    }

    /*"#303F9F",   "#448AFF",   "#3F51B5",   "#0288D1",
            "#03A9F4",   "#E040FB",   "#00796B",   "#009688",
            "#E040FB",   "#388E3C",   "#4CAF50",   "#FF5722",
            "#607D8B",   "#C2185B",   "#E91E63",   "#FFC107",
            "#FFC107",   "#F44336",   "#AFB42B",   "#CDDC39",
            "#795548"*/

    /*0x303F9F,   0x448AFF,   0x3F51B5,   0x0288D1,
            0x03A9F4,   0xE040FB,   0x00796B,   0x009688,
            0xE040FB,   0x388E3C,   0x4CAF50,   0xFF5722,
            0x607D8B,   0xC2185B,   0xE91E63,   0xFFC107,
            0xFFC107,   0xF44336,   0xAFB42B,   0xCDDC39,
            0x795548*/
}
