package com.ffl.nytimesearch.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ffl.nytimesearch.R;
import com.ffl.nytimesearch.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by PJS on 7/28/2017.
 */

public class ArticleArrayAdapter extends RecyclerView.Adapter<ArticleArrayAdapter.ViewHolder>{
    public  static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView thumnail;
        TextView headline;
        public ViewHolder(View itemView)
        {
            super(itemView);
            thumnail=(ImageView)itemView.findViewById(R.id.ivImage);
            headline=(TextView)itemView.findViewById(R.id.tvTitle);
        }
    }
    //Store a member variable for the contatcs
    private  Context context;
    private  List<Article> mArticles;
//PAss in the contact array for the contacts
    public ArticleArrayAdapter (List<Article> articles)
    {
        mArticles=articles;
    }
    public ArticleArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype)
    {
      context=parent.getContext();
       LayoutInflater inflater=LayoutInflater.from(context);

        //inlfate the custom layout
        View articelView=inflater.inflate(R.layout.item_article_result,parent);

        //Return the new view holder instance
        ViewHolder viewHolder=new ViewHolder(articelView);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleArrayAdapter.ViewHolder viewholder, int position) {
        //Get the data model based on position
        Article article=mArticles.get(position);

        //Clear out recycled image
        viewholder.thumnail.setImageResource(0);
        viewholder.headline.setText(article.getHeadline());

        String thumbnail= article.getThumNail();
        if(!TextUtils.isEmpty(thumbnail))
        {
            viewholder.thumnail.setVisibility(View.VISIBLE);
            Picasso.with(context).load(thumbnail).into(viewholder.thumnail);
        }else
        {
            viewholder.thumnail.setVisibility(View.GONE);
        }
    }
    public void swap(List<Article> articles){
        mArticles.clear();
        mArticles.addAll(articles);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }


}
