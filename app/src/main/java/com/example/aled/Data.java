package com.example.aled;

public class Data {
    boolean check = false;
    private String  url = "aa";
    private String  author_name = " ";
    private String  title = " ";
    private int score = 0;
    private String  description = " ";
    private int nb_comment = 0;
    private int points = 0;
    private int up_vote = 0;
    private int down_vote = 0;
    private String hash_image = "";
    private String hash_album = "";


    public Data(String my_url, String my_author_name, String my_title, int my_score, String my_description, int my_nb_comment, int my_points, int my_up_vote, int my_down_vote, String my_hash, String my_hash2) {
        url = my_url;
        author_name = my_author_name;
        title = my_title;
        score = my_score;
        description = my_description;
        nb_comment = my_nb_comment;
        points = my_points;
        up_vote = my_up_vote;
        down_vote = my_down_vote;
        hash_image = my_hash;
        hash_album = my_hash2;
        check = true;
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

    public int get_score() {
        return score;
    }

    public String get_description() {
        return description;
    }

    public int get_nb_comment() {
        return nb_comment;
    }

    public int get_points() {
        return points;
    }

    public int get_up_vote() {
        return up_vote;
    }

    public int get_down_vote() {
        return down_vote;
    }

    public String get_hash_image() { return hash_image; }

    public String get_hash_album() {
        if (!hash_album.equals("Empty"))
            return hash_album;
        else
            return hash_image;
    }

    public boolean get_check() {return check;}
}
