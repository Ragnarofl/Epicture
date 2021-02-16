package com.example.aled;

import net.azzerial.jmgur.api.entities.subentities.Vote;

import org.jetbrains.annotations.Nullable;

public class Post {

    boolean check = false;
    private String url = "aa";
    private String author_name = " ";
    private String title = "default";

    public Post(String my_url, String my_author_name, String my_title) {
        url = my_url;
        author_name = my_author_name;
        if (my_title != null)
            title = my_title;
    }

    public String get_url() {
        return url;
    }

    public String get_author_name() {
        return author_name;
    }

    public String get_title() {
        return title;
    }
}
