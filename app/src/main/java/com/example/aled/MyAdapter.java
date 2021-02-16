package com.example.aled;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<String> titles;
    private ArrayList<Integer> up;
    private ArrayList<Integer> down;
    private ArrayList<Integer> com;
    private ArrayList<String> urls;
    private ArrayList<String> hashes_image;
    private ArrayList<String> hashes_album;
    private boolean deleteCheck;

    Context context;

    public MyAdapter(Context ct, ArrayList<String> titleText,ArrayList<Integer> upvotes, ArrayList<Integer> downvotes, ArrayList<Integer> comments,
                     ArrayList<String> images, ArrayList<String> hash_images, ArrayList<String> hash_albums, boolean del) {
        context = ct;
        titles = titleText;
        up = upvotes;
        down = downvotes;
        com = comments;
        urls = images;
        hashes_image = hash_images;
        hashes_album = hash_albums;
        deleteCheck = del;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_component, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ColorFilter filter_white = new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        ColorFilter filter_yellow = new PorterDuffColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
        ColorFilter filter_cyan = new PorterDuffColorFilter(Color.CYAN, PorterDuff.Mode.SRC_IN);
        ColorFilter filter_red = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        holder.title.setText(titles.get(position));
        holder.upvotes.setText(String.valueOf(up.get(position)));
        holder.downvotes.setText(String.valueOf(down.get(position)));
        holder.comments.setText(String.valueOf(com.get(position)));
        Glide.with(context)
                .load(urls.get(position))
                .into(holder.image);
        holder.btn_up.setColorFilter(filter_white);
        holder.btn_down.setColorFilter(filter_white);
        holder.btn_fav.setColorFilter(filter_white);
        if (deleteCheck = false) {
            holder.btn_delete.setVisibility(View.INVISIBLE);
        }
        holder.btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.btn_up.getColorFilter() == filter_white) {
                    holder.btn_up.setColorFilter(filter_cyan);
                    holder.btn_down.setColorFilter(filter_white);
                }
                else if (holder.btn_up.getColorFilter() == filter_cyan)
                    holder.btn_up.setColorFilter(filter_white);
                MainActivity.my_api.add_up_vote(hashes_album.get(position));
            }
        });
        holder.btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.btn_down.getColorFilter() == filter_white) {
                    holder.btn_down.setColorFilter(filter_red);
                    holder.btn_up.setColorFilter(filter_white);
                }
                else if (holder.btn_down.getColorFilter() == filter_red)
                    holder.btn_down.setColorFilter(filter_white);
                MainActivity.my_api.add_down_vote(hashes_album.get(position));
            }
        });
        holder.btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.btn_fav.getColorFilter() == filter_white)
                    holder.btn_fav.setColorFilter(filter_yellow);
                else if (holder.btn_fav.getColorFilter() == filter_yellow)
                    holder.btn_fav.setColorFilter(filter_white);
                MainActivity.my_api.add_to_fav(hashes_image.get(position));
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, upvotes, downvotes, comments;
        ImageView image;
        ImageButton btn_up, btn_down, btn_fav, btn_delete, btn_comment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title_text);
            upvotes = itemView.findViewById(R.id.card_upvotes);
            downvotes = itemView.findViewById(R.id.card_downvotes);
            image = itemView.findViewById(R.id.card_image);
            btn_up = itemView.findViewById(R.id.card_button_upvote);
            btn_down = itemView.findViewById(R.id.card_button_downvote);
            btn_comment = itemView.findViewById(R.id.card_button_comment);
            comments = itemView.findViewById(R.id.card_comment_text);
            btn_fav = itemView.findViewById(R.id.card_button_fav);
            btn_delete = itemView.findViewById(R.id.card_button_delete);
        }
    }
}
