package quotestoday.tek.com.quotestoday.recyclers;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import quotestoday.tek.com.quotestoday.R;
import quotestoday.tek.com.quotestoday.quotestoday.ScrollingActivity;
import quotestoday.tek.com.quotestoday.rssparser.RssItem;
import quotestoday.tek.com.quotestoday.utils.ColorUtils;

/**
 * Created by Tek on 2/14/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RssItemViewHolder> {
    List<RssItem> rssItems;
    static int toolbarDrawableId;

    public RecyclerAdapter(List<RssItem> rssItems, int toolbarDrawableId) {
        this.rssItems = rssItems;
        this.toolbarDrawableId=toolbarDrawableId;
    }

    @Override
    public RssItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        RssItemViewHolder viewHolder = new RssItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RssItemViewHolder holder, int position) {
        holder.cardView=holder.cardView;
        holder.txtvCardQuote.setText(rssItems.get(position).getDesc());
        holder.txtvCardAuthor.setText(" - "+rssItems.get(position).getTitle());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        if(this.rssItems!=null)
            return rssItems.size();
        return 0;
    }

    public static class RssItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView txtvCardQuote;
        TextView txtvCardAuthor;

        RssItemViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cvSingleRow);
            txtvCardQuote = (TextView) itemView.findViewById(R.id.txtvCardQuote);
            txtvCardAuthor = (TextView) itemView.findViewById(R.id.txtvCardAuthor);
            if (cardView != null) {
                RelativeLayout relaycv=(RelativeLayout)cardView.findViewById(R.id.relaycv);
                relaycv.setBackgroundColor(Color.parseColor(ColorUtils.getBackgroudColor()));
                cardView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            try {
                Intent intent = new Intent(v.getContext(), ScrollingActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("quote", txtvCardQuote.getText().toString());
                bundle.putString("author", txtvCardAuthor.getText().toString());
                bundle.putInt("toolbarDrawableId", toolbarDrawableId);
                intent.putExtra("extras", bundle);

                v.getContext().startActivity(intent);
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }

        private Bitmap getBackground(View cardView) {
            Bitmap bitmap = Bitmap.createBitmap(cardView.getWidth(), cardView.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bitmap);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor(ColorUtils.getBackgroudColor()));
            c.drawPaint(paint);

            c.save();

            return bitmap;
        }
    }
}
