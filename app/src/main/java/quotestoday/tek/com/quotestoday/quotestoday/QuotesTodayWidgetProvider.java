package quotestoday.tek.com.quotestoday.quotestoday;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.widget.RemoteViews;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

import quotestoday.tek.com.quotestoday.R;
import quotestoday.tek.com.quotestoday.rssparser.RssFeed;
import quotestoday.tek.com.quotestoday.rssparser.RssItem;
import quotestoday.tek.com.quotestoday.rssparser.RssParser;
import quotestoday.tek.com.quotestoday.utils.DateUtils;


/**
 * Created by bukia on 2/8/2016.
 */
public class QuotesTodayWidgetProvider extends AppWidgetProvider {
    final String url = "http://feeds.feedburner.com/quotationspage/qotd";
    final String LINK_CLICK = "LinkClick";
    final String ALARM_INTENT = "AlarmIntent";
    final String WAKE_LOCK_TAG = "QUOTES_WAKE_LOCK";
    Random random=new Random();

    final long ONE_DAY_MILLIS=43200000;//86400000;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
       // System.out.println("onUpdate() called.");
        try {
            GetRssFeed asy = new GetRssFeed(context, appWidgetManager, appWidgetIds);
            asy.execute();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void dynamicUpdateViewFlipper(Context context,AppWidgetManager appWidgetManager, RemoteViews remoteViews, RssFeed rssFeed) throws ParseException {

        if(rssFeed!=null && rssFeed.getItems()!=null) {
            for(RssItem item : rssFeed.getItems()) {
                RemoteViews newView = new RemoteViews(context.getPackageName(), R.layout.single_quote_row);
                newView.setTextViewText(R.id.txtvDesc, item.getDesc());
                newView.setTextViewText(R.id.txtvAuthor, "- " + item.getTitle());

                Intent urlOpenIntent = new Intent(context,QuotesTodayWidgetProvider.class);
                urlOpenIntent.setAction(LINK_CLICK);
                urlOpenIntent.putExtra("extra", item.getLink());

                Bundle bundle=new Bundle();
                bundle.putString("quote",item.getDesc());
                bundle.putString("author", " - "+item.getTitle());
                urlOpenIntent.putExtra("extras",bundle);

                PendingIntent pi = PendingIntent.getBroadcast(context,random.nextInt(),urlOpenIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                newView.setOnClickPendingIntent(R.id.news_row, pi);

                remoteViews.addView(R.id.viewFlipper, newView);
            }
        }
    }

    public void updateNewsView(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, RssFeed rssFeed) throws ParseException {

        for(int i=0;i<appWidgetIds.length;i++) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            dynamicUpdateViewFlipper(context, appWidgetManager, remoteViews, rssFeed);

            Intent intent = new Intent(context, QuotesTodayWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.btnRefresh, pi);

            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
    }

    public RssFeed getRssFeed() {
        RssParser rssParser=new RssParser(this.url);
        RssFeed feed = rssParser.parseRss();

        /*if(feed!=null && feed.getItems()!=null) {
            for(RssItem item : feed.getItems())
                System.out.println(item);
        }*/
        return feed;
    }

    class GetRssFeed extends AsyncTask<Void, Integer, RssFeed> {
        Context context;
        AppWidgetManager appWidgetManager;
        int[] appWidgetIds;
        Handler handler;
        Runnable runnable;
        final long time=100;
        int imageIndex=1;

        public GetRssFeed(Context contextx, final AppWidgetManager appWidgetManagerx, final int[] appWidgetIdsx) {
            this.context=contextx;
            this.appWidgetManager=appWidgetManagerx;
            this.appWidgetIds=appWidgetIdsx;
            handler=new Handler();
            runnable=new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<appWidgetIds.length;i++) {
                        RemoteViews rvs=new RemoteViews(context.getPackageName(),R.layout.widget_layout);
                        Bitmap bmp= BitmapFactory.decodeResource(context.getResources(),getDrawableIdx(imageIndex));
                        rvs.setBitmap(R.id.btnRefresh, "setImageBitmap", bmp);

                        imageIndex++;
                        if(imageIndex>6)
                            imageIndex=1;

                        appWidgetManager.updateAppWidget(appWidgetIds[i],rvs);
                    }

                    handler.postDelayed(runnable,time);
                }
            };
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            handler.postDelayed(runnable,time);
        }

        @Override
        protected RssFeed doInBackground(Void... params) {
            return getRssFeed();
        }

        @Override
        protected void onPostExecute(RssFeed rssFeed) {
            //System.out.println("onPostExecute()");
            try {
                updateNewsView(context,appWidgetManager,appWidgetIds,rssFeed);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            handler.removeCallbacks(runnable);
        }
    }

    public int getDrawableIdx(int index) {
        if(index==1)
            return R.drawable.ic_popup_sync_1;
        else if(index==2)
            return R.drawable.ic_popup_sync_2;
        else if(index==3)
            return R.drawable.ic_popup_sync_3;
        else if(index==4)
            return R.drawable.ic_popup_sync_4;
        else if(index==5)
            return R.drawable.ic_popup_sync_5;
        else if(index==6)
            return R.drawable.ic_popup_sync_6;
        else
            return R.drawable.ic_popup_sync_1;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        //System.out.println("onReceive()");
        if(intent.getAction()!=null) {
            if (intent.getAction().equals(LINK_CLICK)) {
                try {
                    Intent urlIntent = new Intent(context,ScrollingActivity.class);//Intent.ACTION_VIEW);
                    urlIntent.setData(Uri.parse(intent.getStringExtra("extra")));
                    urlIntent.putExtra("extras",intent.getBundleExtra("extras"));
                    urlIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(urlIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (intent.getAction().equals(ALARM_INTENT)) {
                //System.out.println("ALARM INTENT");
                PowerManager powerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_TAG);
                wakeLock.acquire();

                try {
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    ComponentName currentWidget=new ComponentName(context, QuotesTodayWidgetProvider.class);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(currentWidget);

                    GetRssFeed asy = new GetRssFeed(context, appWidgetManager, appWidgetIds);
                    asy.execute();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }

                wakeLock.release();
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
       // System.out.println("onEnabled()");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context, QuotesTodayWidgetProvider.class);
        intent.setAction(ALARM_INTENT);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);

        long offset=DateUtils.getOffsetToMidnight();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+offset,ONE_DAY_MILLIS,pi);//86400000=1day
    }

    @Override
    public void onDisabled(Context context) {
        //System.out.println("onDisabled()");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context, QuotesTodayWidgetProvider.class);
        intent.setAction(ALARM_INTENT);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmManager.cancel(pi);

        super.onDisabled(context);
    }
}
