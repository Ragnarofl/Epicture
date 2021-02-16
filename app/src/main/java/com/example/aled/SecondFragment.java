package com.example.aled;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {

    private boolean menu_clicked = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycler = view.findViewById(R.id.user_recycler);

        ArrayList<String> imgList = new ArrayList<String>();
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<Integer> upvotes = new ArrayList<Integer>();
        ArrayList<Integer> downvotes = new ArrayList<Integer>();
        ArrayList<Integer> comments = new ArrayList<Integer>();
        ArrayList<String> hash_image = new ArrayList<String>();
        ArrayList<String> hash_album = new ArrayList<String>();
        List<Post> list = MainActivity.my_api.get_list_my_image();
        for (Post tmp : list) {
            titles.add(tmp.get_title());
            upvotes.add(0);
            downvotes.add(0);
            comments.add(0);
            imgList.add(tmp.get_url());
            hash_image.add("");
            hash_album.add("");
        }

        MyAdapter mAdapter = new MyAdapter(getContext(), titles, upvotes, downvotes, comments, imgList, hash_image, hash_album, true);
        recycler.setAdapter(mAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        final FloatingActionButton add = view.findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        Uri imageUri;
        ImageView imageView = new ImageView(getContext());
        if (resultCode == Activity.RESULT_OK && reqCode == 1){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            MainActivity.my_api.add_image_bitmap(imageView, "Review", "OK");
        }
    }
}