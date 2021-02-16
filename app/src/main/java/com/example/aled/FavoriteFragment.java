package com.example.aled;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {

    private boolean menu_clicked = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycler = view.findViewById(R.id.favorite_recycler);

        ArrayList<String> imgList = new ArrayList<String>();
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<Integer> upvotes = new ArrayList<Integer>();
        ArrayList<Integer> downvotes = new ArrayList<Integer>();
        ArrayList<Integer> comments = new ArrayList<Integer>();
        ArrayList<String> hash_image = new ArrayList<String>();
        ArrayList<String> hash_album = new ArrayList<String>();
        List<Data> list = MainActivity.my_api.get_list_favoris();
        for (Data tmp : list) {
            titles.add(tmp.get_title());
            upvotes.add(tmp.get_up_vote());
            downvotes.add(tmp.get_down_vote());
            comments.add(tmp.get_nb_comment());
            imgList.add(tmp.get_url());
            hash_image.add(tmp.get_hash_image());
            hash_album.add(tmp.get_hash_album());
        }

        MyAdapter mAdapter = new MyAdapter(getContext(), titles, upvotes, downvotes, comments, imgList, hash_image, hash_album, false);
        recycler.setAdapter(mAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}