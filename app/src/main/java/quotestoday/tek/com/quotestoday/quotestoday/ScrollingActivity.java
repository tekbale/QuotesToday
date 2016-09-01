package quotestoday.tek.com.quotestoday.quotestoday;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import quotestoday.tek.com.quotestoday.R;
import quotestoday.tek.com.quotestoday.utils.ImageUtils;

import com.facebook.FacebookSdk;

import java.util.Arrays;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {
    AdView adView1;
    AdView adView2;

    CallbackManager callbackManager;
    LoginManager loginManager;

    String author="";
    String quote="";

    CollapsingToolbarLayout toolbarLayout;
    int toolbarDrawableId;

    TransitionDrawable d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager= CallbackManager.Factory.create();

        Bundle bundleExtras=getIntent().getBundleExtra("extras");
        if(bundleExtras!=null) {
            quote=bundleExtras.getString("quote");
            author=bundleExtras.getString("author");
            toolbarDrawableId=bundleExtras.getInt("toolbarDrawableId");
        }

        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_menu_back);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ScrollingActivity.this,QuotesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putInt("toolbarDrawableId", toolbarDrawableId);
                intent.putExtra("extras", bundle);

                startActivity(intent);
                finish();
            }
        });

        if(toolbarDrawableId<=0)
            toolbarDrawableId=ImageUtils.getToolbarDrawableId();

        toolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);

        animateToolbarDrawable();

        TextView txtvQuote=(TextView)findViewById(R.id.txtvQuote);
        txtvQuote.setText(quote);
        TextView txtvAuthor=(TextView)findViewById(R.id.txtvAuthor);
        txtvAuthor.setText(author);


        adView1 = (AdView)findViewById(R.id.adView1);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        adView1.loadAd(adRequest1);

        adView2 = (AdView)findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        adView2.loadAd(adRequest2);
        ImageButton btn=(ImageButton)findViewById(R.id.btnFbShare);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("Share button onClick");
                try {
                    handleLogin();
                } catch(Exception e) {
                    System.out.println("error share");
                }
            }
        });
    }

    private void animateToolbarDrawable() {
        Drawable drawable = ContextCompat.getDrawable(this, toolbarDrawableId);
        if(drawable!=null)
            toolbarLayout.setBackgroundDrawable(drawable);

        /*Animation animOut=AnimationUtils.loadAnimation(this,R.anim.fadeout_anim);
        toolbarLayout.setAnimation(animOut);

        animOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("Animation start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("Animation end");
                Drawable drawable = ContextCompat.getDrawable(ScrollingActivity.this, ImageUtils.getToolbarDrawableId());
                if (drawable != null)
                    toolbarLayout.setBackgroundDrawable(drawable);

                Animation animIn = AnimationUtils.loadAnimation(ScrollingActivity.this, R.anim.fadein_anim);
                toolbarLayout.startAnimation(animIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        toolbarLayout.animate();*/
    }

    private void startShare() {
        Typeface danielTypeface=Typeface.createFromAsset(ScrollingActivity.this.getAssets(), "fonts/daniel.ttf");

        Bitmap bitmap= ImageUtils.createBitmap(ScrollingActivity.this, quote, author, 800, 500, danielTypeface, 40, toolbarDrawableId);

        SharePhoto photo = new SharePhoto.Builder().setBitmap(bitmap).build();
        SharePhotoContent photoContent = new SharePhotoContent.Builder().addPhoto(photo).build();

        ShareApi.share(photoContent, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                //System.out.println("share onSuccess");
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("Share onError()");
                System.out.println(error.getStackTrace());
            }
        });
    }

    private void handleLogin() {
        List<String> permissions= Arrays.asList("publish_actions");
        loginManager=LoginManager.getInstance();
        loginManager.logInWithPublishPermissions(ScrollingActivity.this, permissions);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
               // System.out.println("login onSuccess()");
                startShare();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("error");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    class MyAdListener extends AdListener {
        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            System.out.println("onAdLoaded()");
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            super.onAdFailedToLoad(errorCode);
            System.out.println("onAdFailedToLoad errCode="+errorCode);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }
}
