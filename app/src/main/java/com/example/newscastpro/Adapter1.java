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
//import com.example.business_news.business_news.Business_Artcile;
import com.example.newscastpro.business_news.Business_Artcile;

import java.util.List;

public class Adapter1 extends RecyclerView.Adapter<Adapter1.ViewHolder> {
    Context context;
    List<Business_Artcile> business_artciles;

    public Adapter1(Context context, List<Business_Artcile> business_artciles) {
        this.context = context;
        this.business_artciles = business_artciles;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.business_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Business_Artcile data = business_artciles.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
        holder.author.setText(data.getAuthor());
        holder.desc.setText(data.getDescription());
        String mdate ;
        mdate = data.getPublishedAt().toString();
        String res =mdate.substring(0,10);
        holder.date.setText(res);
        holder.title.setText(data.getTitle());
        holder.source.setText(data.getBusiness_source().getName());
        String url = data.getUrlToImage();
        final String newUrl = data.getUrl();
      // Glide.with(context).load(url).into(holder.imageView);
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
        return business_artciles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView author,title,source,date,desc;
        ImageView imageView;
        ProgressBar bar;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.business_author);
            title = itemView.findViewById(R.id.business_title);
            source = itemView.findViewById(R.id.business_source);
            date  = itemView.findViewById(R.id.business_date);
            desc = itemView.findViewById(R.id.business_Description);
            imageView = itemView.findViewById(R.id.business_img);
            bar = itemView.findViewById(R.id.business_progress_bar);
            cardView = itemView.findViewById(R.id.business_card);
        }
    }
}



