package com.example.newscastpro;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.newscastpro.sports_news.Sports_articles;

import java.util.List;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {
Context context;
List<Sports_articles> sports_articlesList;

    public Adapter2(Context context, List<Sports_articles> sports_articlesList) {
        this.context = context;
        this.sports_articlesList = sports_articlesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sports_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
       Sports_articles data = sports_articlesList.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
       holder.author.setText(data.getAuthor());
       holder.desc.setText(data.getDescription());
       holder.title.setText(data.getTitle());
       final String newUrl = data.getUrl();
        String mdate ;
        mdate = data.getPublishedAt().toString();
        String res =mdate.substring(0,10);
        holder.date.setText(res);
        String url = data.getUrlToImage();
        Glide.with(context)
                .load(data.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.bar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.bar.setVisibility(View.GONE);
                        return false;
                    }
                }).transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Business_detailed_activity.class);

                intent.putExtra("Url",newUrl);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sports_articlesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView author,title,source,date,desc;
        ImageView imageView;
        ProgressBar bar;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.sports_author);
            title = itemView.findViewById(R.id.sports_title);
            source = itemView.findViewById(R.id.sports_source);
            date  = itemView.findViewById(R.id.sports_date);
            desc = itemView.findViewById(R.id.sports_Description);
            imageView = itemView.findViewById(R.id.sports_img);
            bar = itemView.findViewById(R.id.sports_progress_bar);
            cardView = itemView.findViewById(R.id.sports_card);
        }
    }
}
