package quotestoday.tek.com.quotestoday.quotestoday;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import quotestoday.tek.com.quotestoday.R;
import quotestoday.tek.com.quotestoday.recyclers.MyLayoutManager;
import quotestoday.tek.com.quotestoday.recyclers.RecyclerAdapter;
import quotestoday.tek.com.quotestoday.rssparser.RssFeed;
import quotestoday.tek.com.quotestoday.rssparser.RssItem;
import quotestoday.tek.com.quotestoday.rssparser.RssParser;
import quotestoday.tek.com.quotestoday.utils.ImageUtils;

/**
 * Created by bukia on 2/11/2016.
 */
public class QuotesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyLayoutManager layoutManager;
    RecyclerAdapter recyclerAdapter;

    CollapsingToolbarLayout toolbarLayout;
    Toolbar toolbar;

    List<RssItem> rssItems;
    final String url = "http://feeds.feedburner.com/quotationspage/qotd";
    int toolbarDrawableId;

    Handler animHandler;
    boolean toggle=true;
    boolean ended=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundleExtras=getIntent().getBundleExtra("extras");
        if(bundleExtras!=null) {
            toolbarDrawableId=bundleExtras.getInt("toolbarDrawableId");
        }

        setContentView(R.layout.recycler_view_quotes_activity);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        if(recyclerView!=null) {
            System.out.println("recycler view found");
        } else {
            System.out.println("recycler view not found");
        }

        layoutManager = new MyLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        new GetRssFeed(this).execute();

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        if(toolbarDrawableId<=0)
            toolbarDrawableId= ImageUtils.getToolbarDrawableId();
        Drawable drawable = ContextCompat.getDrawable(this,toolbarDrawableId);
        if(drawable!=null)
            toolbarLayout.setBackgroundDrawable(drawable);
        //animateToolbarDrawable();
    }

    private void animateToolbarDrawable() {
        Animation anim = AnimationUtils.loadAnimation(QuotesActivity.this, R.anim.fadeout_anim);

        Drawable drawable = ContextCompat.getDrawable(QuotesActivity.this,ImageUtils.getToolbarDrawableId());
        if(drawable!=null)
            toolbarLayout.setBackgroundDrawable(drawable);

        toolbarLayout.setAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("Animation start, "+toggle);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("Animation end, "+toggle);
                toggle=(!toggle);
                Animation anim;
                if(toggle) {
                    anim = AnimationUtils.loadAnimation(QuotesActivity.this, R.anim.fadeout_anim);
                } else {
                    anim = AnimationUtils.loadAnimation(QuotesActivity.this, R.anim.fadein_anim);
                    Drawable drawable = ContextCompat.getDrawable(QuotesActivity.this,ImageUtils.getToolbarDrawableId());
                    if(drawable!=null)
                        toolbarLayout.setBackgroundDrawable(drawable);
                }
                anim.setAnimationListener(this);
                toolbarLayout.setAnimation(anim);

                toolbarLayout.animate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        toolbarLayout.animate();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quotes_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
           /* case R.id.action_widget:
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                //ComponentName currentWidget=new ComponentName(this, QuotesTodayWidgetProvider.class);
                //int[] appWidgetIds = appWidgetManager.getAppWidgetIds(currentWidget);
                AppWidgetHost appWidgetHost=new AppWidgetHost(this,5);

                Intent intent=new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetHost.allocateAppWidgetId());
                startActivityForResult(intent,1999);

                break;*/

            case R.id.action_about:
                Intent intent=new Intent(QuotesActivity.this, AboutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("toolbarDrawableId", toolbarDrawableId);
                intent.putExtra("extras", bundle);

                startActivity(intent);
                break;

            case R.id.action_contact:
                Intent emailIntent=new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:wongdusoft@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hiya Dev");
                startActivity(Intent.createChooser(emailIntent,"Email picker"));
                break;

           /* case R.id.action_settings:
                Toast.makeText(this,"Settings clicked", Toast.LENGTH_LONG).show();
                break;*/

            default:
                break;
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("reqCode="+requestCode+", resultCode="+resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1999:
                    break;
            }
        }
    }

    /* void addAppWidget(Intent data) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        AppWidgetProviderInfo appWidget = appWidgetManager.getAppWidgetInfo(appWidgetId);

        if (appWidget.configure != null) {
            // Launch over to configure widget, if needed
            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
            intent.setComponent(appWidget.configure);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            startActivityForResult(intent, 1999);
        } else {
            // Otherwise just add it
            onActivityResult(1999, Activity.RESULT_OK, data);
        }
    }*/

    private void initData(RssFeed rssFeed) {
        rssItems = new ArrayList<>();
        if(rssFeed!=null && rssFeed.getItems()!=null) {
            for(RssItem item:rssFeed.getItems())
                rssItems.add(item);
        }

        recyclerAdapter=new RecyclerAdapter(this.rssItems, toolbarDrawableId);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public RssFeed getRssFeed(String url) {
        RssParser rssParser=new RssParser(url);
        RssFeed feed = rssParser.parseRss();

        /*if(feed!=null && feed.getItems()!=null) {
            for(RssItem item : feed.getItems())
                System.out.println(item);
        }*/
        return feed;
    }

    class GetRssFeed extends AsyncTask<Void, Integer, RssFeed> {
        Context context;

        public GetRssFeed(Context contextx) {
            this.context=contextx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected RssFeed doInBackground(Void... params) {
            return getRssFeed(url);
        }

        @Override
        protected void onPostExecute(RssFeed rssFeed) {
            //System.out.println("onPostExecute()");
            initData(rssFeed);
        }
    }
}
