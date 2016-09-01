package quotestoday.tek.com.quotestoday.rssparser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by bukia on 2/8/2016.
 */
public class NetUtils {
    public String getRssString(URL url) throws IOException {
        url.openConnection();
        InputStream in = url.openStream();
        byte[] buff=new byte[2048];
        int i=0;
        StringBuilder sb=new StringBuilder();
        while((i=in.read(buff))>0) {
            sb.append(new String(buff,0,i));
        }
        return sb.toString();
    }
}
