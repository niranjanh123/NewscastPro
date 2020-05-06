package com.example.newscastpro;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.newscastpro.models.Article;
import java.util.List;

import okhttp3.internal.Util;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<Article> articles;
    Context context;
    OnItemClickListener onItemClickListener;

    public Adapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card,parent,false);
        return new ViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ViewHolder viewHolder = holder;
        Article data = articles.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
      holder.title.setText(data.getTitle());
      final String newUrl = data.getUrl();
      String mdate ;
      mdate = data.getPublishedAt().toString();
      String res =mdate.substring(0,10);
      holder.date.setText(res);
      holder.author.setText(data.getAuthor());
      holder.desc.setText(data.getDescription());
      holder.source.setText(data.getSource().getName());
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
        return articles.size();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener{
        void OnItemClick(View view,int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView author,title,source,date,desc;
        ImageView imageView;
        ProgressBar bar;
        CardView cardView;
        OnItemClickListener onItemClickListener;
        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            title = itemView.findViewById(R.id.title);
            source = itemView.findViewById(R.id.source);
            date  = itemView.findViewById(R.id.date);
            desc = itemView.findViewById(R.id.Description);
            imageView = itemView.findViewById(R.id.img);
            bar = itemView.findViewById(R.id.progress_bar);
            cardView = itemView.findViewById(R.id.card);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.OnItemClick(view,getAdapterPosition());
        }
    }


}


