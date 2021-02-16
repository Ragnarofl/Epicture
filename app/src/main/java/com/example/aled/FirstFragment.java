package com.example.aled;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private boolean menu_clicked = false;
    private  List<Data> list = MainActivity.my_api.get_img_list();
    private MyAdapter mAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycler = view.findViewById(R.id.search_recycle);

        ArrayList<String> imgList = new ArrayList<String>();
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<Integer> upvotes = new ArrayList<Integer>();
        ArrayList<Integer> downvotes = new ArrayList<Integer>();
        ArrayList<Integer> comments = new ArrayList<Integer>();
        ArrayList<String> hash_image = new ArrayList<String>();
        ArrayList<String> hash_album = new ArrayList<String>();
        for (Data tmp : list) {
            titles.add(tmp.get_title());
            upvotes.add(tmp.get_up_vote());
            downvotes.add(tmp.get_down_vote());
            comments.add(tmp.get_nb_comment());
            imgList.add(tmp.get_url());
            hash_image.add(tmp.get_hash_image());
            hash_album.add(tmp.get_hash_album());
        }

        mAdapter = new MyAdapter(getContext(), titles, upvotes, downvotes, comments, imgList, hash_image, hash_album, false);
        recycler.setAdapter(mAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        SearchView search = view.findViewById(R.id.searchBar);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.equals("reset")) {
                    list = MainActivity.my_api.get_img_list();
                } else
                    list = MainActivity.my_api.get_image_search(query);
                titles.clear();
                upvotes.clear();
                downvotes.clear();
                comments.clear();
                imgList.clear();
                hash_image.clear();
                hash_album.clear();
                for (Data tmp : list) {
                    titles.add(tmp.get_title());
                    upvotes.add(tmp.get_up_vote());
                    downvotes.add(tmp.get_down_vote());
                    comments.add(tmp.get_nb_comment());
                    imgList.add(tmp.get_url());
                    hash_image.add(tmp.get_hash_image());
                    hash_album.add(tmp.get_hash_album());
                }
                mAdapter = new MyAdapter(getContext(), titles, upvotes, downvotes, comments, imgList, hash_image, hash_album, false);
                recycler.setAdapter(mAdapter);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        FloatingActionButton refresh = view.findViewById(R.id.btn_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list = MainActivity.my_api.get_img_list();
                titles.clear();
                upvotes.clear();
                downvotes.clear();
                comments.clear();
                imgList.clear();
                hash_image.clear();
                hash_album.clear();
                for (Data tmp : list) {
                    titles.add(tmp.get_title());
                    upvotes.add(tmp.get_up_vote());
                    downvotes.add(tmp.get_down_vote());
                    comments.add(tmp.get_nb_comment());
                    imgList.add(tmp.get_url());
                    hash_image.add(tmp.get_hash_image());
                    hash_album.add(tmp.get_hash_album());
                }
                mAdapter = new MyAdapter(getContext(), titles, upvotes, downvotes, comments, imgList, hash_image, hash_album, false);
                recycler.setAdapter(mAdapter);
            }
        });
    }
}