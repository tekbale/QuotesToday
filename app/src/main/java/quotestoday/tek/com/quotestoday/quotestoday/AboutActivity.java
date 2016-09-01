package quotestoday.tek.com.quotestoday.quotestoday;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import quotestoday.tek.com.quotestoday.R;
import quotestoday.tek.com.quotestoday.utils.ImageUtils;

/**
 * Created by bukia on 2/15/2016.
 */
public class AboutActivity extends AppCompatActivity {

    CollapsingToolbarLayout toolbarLayout;
    Toolbar toolbar;

    int toolbarDrawableId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_about);

        Bundle bundleExtras=getIntent().getBundleExtra("extras");
        if(bundleExtras!=null) {
            toolbarDrawableId=bundleExtras.getInt("toolbarDrawableId");
        }

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_menu_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(toolbarDrawableId<=0)
            toolbarDrawableId=ImageUtils.getToolbarDrawableId();

        toolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);

        animateToolbarDrawable();
    }

    private void animateToolbarDrawable() {
        Drawable drawable = ContextCompat.getDrawable(this, toolbarDrawableId);
        if (drawable != null)
            toolbarLayout.setBackgroundDrawable(drawable);
    }
}
