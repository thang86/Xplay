package thang86.github.io.xplay.activity.popular_movie.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import thang86.github.io.xplay.R;
import thang86.github.io.xplay.model.MoviePopular;
import thang86.github.io.xplay.utils.UrlBuilder;

/**
 * Created by TranThang on 2/9/2020.
 */

public class PopularAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int VIEW_ITEM = 1;
    private List<MoviePopular> list;
    private OnMovieClickListener onMovieClickListener;

    public PopularAdapter(List<MoviePopular> movies) {
        this.list = movies;
    }

    public void setOnMovieClickListener(OnMovieClickListener listener) {
        this.onMovieClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PopularHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_popular, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PopularHolder) {
            PopularHolder viewHolder = (PopularHolder) holder;
            MoviePopular media = list.get(position);

            RxView.clicks(viewHolder.itemView)
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            onMovieClickListener.onMovieClicked(media, viewHolder.itemView);
                        }
                    });

//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            onMovieClickListener.onMovieClicked(media, viewHolder.itemView);
//                        }
//                    }, 200);
//                }
//            });

            viewHolder.title.setText(media.getTitle());
            viewHolder.year.setText(media.getReleaseDate().split("-")[0]);
            Glide.clear(viewHolder.poster);
            holder.itemView.setBackgroundColor(ContextCompat.getColor(viewHolder.poster.getContext(), R.color.colorPrimary));

            Glide.with(viewHolder.poster.getContext())
                    .load(UrlBuilder.getPosterUrl(media.getPosterPath()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            Bitmap bitmap = ((GlideBitmapDrawable) resource.getCurrent()).getBitmap();
                            Palette palette = new Palette.Builder(bitmap).generate();
                            int defaultColor = 0xFF333333;
                            int color = palette.getDarkMutedColor(defaultColor);
                            list.get(position).setBackgroundColor(color);
                            holder.itemView.setBackgroundColor(color);
                            return false;
                        }
                    })
                    .into(viewHolder.poster);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(List<MoviePopular> items) {
        int previousDataSize = this.list.size();
        this.list.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    public static class PopularHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title;
        TextView year;

        PopularHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            year = (TextView) itemView.findViewById(R.id.year);
            poster = (ImageView) itemView.findViewById(R.id.poster);
        }
    }

    public interface OnMovieClickListener {
        void onMovieClicked(MoviePopular movie, View view);
    }
}
