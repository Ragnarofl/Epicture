package com.example.aled;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.widget.ImageView;

import net.azzerial.jmgur.api.*;
import net.azzerial.jmgur.api.entities.GalleryAlbum;
import net.azzerial.jmgur.api.entities.GalleryElement;
import net.azzerial.jmgur.api.entities.GalleryImage;
import net.azzerial.jmgur.api.entities.Image;
import net.azzerial.jmgur.api.entities.dto.ImageUploadDTO;
import net.azzerial.jmgur.api.entities.subentities.Vote;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Api {
    private static final String CLIENT_ID = "53ede3b1bc77a8b";
    private static final String TAG = "JMGUR_APP_TAG";
    private List<GalleryElement> wallList = null;
    private List<Image>  my_list = null;
    private Jmgur api = null;


    public Jmgur getApi() {
        return api;
    }

    public boolean set_client_id(WebResourceRequest request) {
        final String url = request.getUrl().toString();
        try {
            final OAuth2 oauth = OAuth2.fromUrl(url);

            api = JmgurBuilder
                    .of(CLIENT_ID)
                    .setOAuth(oauth)
                    .build();
            return true;
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
            return false;
        }
    }

    public void getAccount() {
        api.ACCOUNT.getSelfAccount().queue(
                account -> Log.i(TAG, "Logged in account: " + account),
                Throwable::printStackTrace
        );
    }

    public List<Data> get_img_list() {
        wallList = null;
        //PagedRestAction<List<GalleryElement>> pages = api.GALLERY.getGallery();
        api.GALLERY.getGallery().get().queue(galleryElements -> {
            wallList = galleryElements;
        });
        try
        {
            Thread.sleep(400);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        //while (wallList == null) ;
        List<Data> listData = new ArrayList<Data>();
        if (wallList == null) {
            listData = get_img_list();
            return listData;
        }
        int i = 0;
        for (GalleryElement it : wallList) {
            if (it == null)
                break;
            Log.i(TAG, (it == null ? "null" : "pas null"));
            final GalleryAlbum post;
            if (it instanceof GalleryAlbum) {
                post = (GalleryAlbum) it;
                if (post.getImages().isEmpty())
                    continue;
                final String imgHash = post.getCoverHash();
                GalleryImage postImage = null;
                String test = post.getTitle();
                for (GalleryImage image : post.getImages()) {
                    if (image.getHash().equals(imgHash)) {
                        postImage = image;
                    } else {
                        continue;
                    }
                    if (postImage == null)
                        postImage = post.getImages().get(0);
                    if (postImage.isAnimated())
                        continue;
                    Data tmp = new Data(postImage.getUrl(), post.getAuthorName(), post.getTitle(), post.getScore(), test, post.getCommentCount()
                            , post.getPoints(), post.getUps(), post.getDowns(), postImage.getHash(), post.getHash());
                    if (tmp != null) {
                        listData.add(tmp);
                    }
                }
            } else {
                Data tmp = new Data(it.getUrl(), it.getAuthorName(), it.getTitle(), it.getScore()
                        , it.getDescription(), it.getCommentCount(), it.getPoints(), it.getUps(), it.getDowns(), it.getHash(), "Empty");
                if (tmp != null) {
                    listData.add(tmp);
                }
            }
        }
        if (null != listData)
            Log.i("Nb : ", String.valueOf(listData.size()));
        else
            Log.i("Nb ; ", "0");
        for (Data tmp : listData) {
            if (tmp != null) {
                Log.i("URL", tmp.get_url());
                Log.i("Title", tmp.get_title());
                Log.i("Author", tmp.get_author_name());
                Log.i("Score", Integer.toString(tmp.get_score()));
                //Log.i("Description", tmp.get_description());
                Log.i("Nb_comment", Integer.toString(tmp.get_nb_comment()));
                Log.i("Points", Integer.toString(tmp.get_points()));
                Log.i("Up_vote", Integer.toString(tmp.get_up_vote()));
                Log.i("Down_vote", Integer.toString(tmp.get_down_vote()));
                Log.i("Hash1", tmp.get_hash_image());
                Log.i("Hash2", tmp.get_hash_album());
            }
        }
        return listData;
    }

    public List<Data> get_image_search(String search) {
        wallList = null;
        api.GALLERY.searchGallery(search).get().queue(galleryElements -> {
            wallList = galleryElements;
        });
        try
        {
            Thread.sleep(400);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        List<Data> listData = new ArrayList<Data>();
        if (wallList == null) {
            listData = get_image_search(search);
            return listData;
        }
        for (GalleryElement it : wallList) {
            if (it == null)
                break;
            Log.i(TAG, (it == null ? "null" : "pas null"));
            final GalleryAlbum post;
            if (it instanceof GalleryAlbum) {
                post = (GalleryAlbum) it;
                if (post.getImages().isEmpty())
                    continue;
                final String imgHash = post.getCoverHash();
                GalleryImage postImage = null;
                String test = post.getTitle();
                for (GalleryImage image : post.getImages()) {
                    if (image.getHash().equals(imgHash)) {
                        postImage = image;
                    } else {
                        continue;
                    }
                    if (postImage == null)
                        postImage = post.getImages().get(0);
                    if (postImage.isAnimated())
                        continue;
                    Data tmp = new Data(postImage.getUrl(), post.getAuthorName(), post.getTitle(), post.getScore(), test, post.getCommentCount()
                            , post.getPoints(), post.getUps(), post.getDowns(), postImage.getHash(), post.getHash());
                    if (tmp != null) {
                        listData.add(tmp);
                    }
                }
            } else {
                Data tmp = new Data(it.getUrl(), it.getAuthorName(), it.getTitle(), it.getScore()
                        , it.getDescription(), it.getCommentCount(), it.getPoints(), it.getUps(), it.getDowns(), it.getHash(), "Empty");
                if (tmp != null) {
                    listData.add(tmp);
                }
            }
        }
        if (null != listData)
            Log.i("Nb : ", String.valueOf(listData.size()));
        Log.i("Search", "True");
        for (Data tmp : listData) {
            if (tmp != null) {
                Log.i("URL", tmp.get_url());
                Log.i("Title", tmp.get_title());
                Log.i("Author", tmp.get_author_name());
                Log.i("Score", Integer.toString(tmp.get_score()));
                //Log.i("Description", tmp.get_description());
                Log.i("Nb_comment", Integer.toString(tmp.get_nb_comment()));
                Log.i("Points", Integer.toString(tmp.get_points()));
                Log.i("Up_vote", Integer.toString(tmp.get_up_vote()));
                Log.i("Down_vote", Integer.toString(tmp.get_down_vote()));
                Log.i("Hash1", tmp.get_hash_image());
                Log.i("Hash2", tmp.get_hash_album());

            }
        }
        return listData;
    }

    public List<Post> get_list_my_image() {
        api.ACCOUNT.getSelfImages().get().queue(imageList -> {
            my_list = imageList;
        });
        try
        {
            Thread.sleep(400);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        List<Post> list = new ArrayList<Post>();
        if (wallList == null) {
            list = get_list_my_image();
            return list;
        }
        if (my_list == null)
            return null;
        for (Image it : my_list) {
            if (it == null)
                break;
            Post tmp = new Post(it.getUrl(), it.getAuthorName(), it.getTitle());
            list.add(tmp);
            Log.i("Url", tmp.get_url());
            Log.i("Author", tmp.get_author_name());
            Log.i("Title", tmp.get_title());
        }
        return list;
    }

    public List<Data> get_list_favoris() {
        Log.i("Get Fav", "Enter");
        wallList = null;
        api.ACCOUNT.getSelfFavorites().get().queue(galleryElements -> {
            wallList = galleryElements;
        });
        try
        {
            Thread.sleep(400);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        List<Data> listData = new ArrayList<Data>();
        if (wallList == null) {
            listData = get_list_favoris();
            return listData;
        }
        int i = 0;
        for (GalleryElement it : wallList) {
            if (it == null)
                break;
            Log.i(TAG, (it == null ? "null" : "pas null"));
            final GalleryAlbum post;
            if (it instanceof GalleryAlbum) {
                Log.i("If", "1");
                post = (GalleryAlbum) it;
                if (post.getImages().isEmpty())
                    continue;
                final String imgHash = post.getCoverHash();
                GalleryImage postImage = null;
                String test = post.getTitle();
                for (GalleryImage image : post.getImages()) {
                    if (image.getHash().equals(imgHash)) {
                        postImage = image;
                    } else {
                        continue;
                    }
                    if (postImage == null)
                        postImage = post.getImages().get(0);
                    if (postImage.isAnimated())
                        continue;
                    Data tmp = new Data(postImage.getUrl(), post.getAuthorName(), post.getTitle(), post.getScore(), test, post.getCommentCount()
                            , post.getPoints(), post.getUps(), post.getDowns(), postImage.getHash(), post.getHash());
                    if (tmp != null) {
                        listData.add(tmp);
                    }
                }
            } else {
                Log.i("Else", "1");
                Data tmp = new Data(it.getUrl(), it.getAuthorName(), it.getTitle(), it.getScore()
                        , it.getDescription(), it.getCommentCount(), it.getPoints(), it.getUps(), it.getDowns(), it.getHash(), "Empty");
                if (tmp != null) {
                    listData.add(tmp);
                }
            }
        }
        if (null != listData)
            Log.i("Nb : ", String.valueOf(listData.size()));
        else
            Log.i("Nb ; ", "0");
        for (Data tmp : listData) {
            if (tmp != null) {
                Log.i("URL", tmp.get_url());
                Log.i("Title", tmp.get_title());
                Log.i("Author", tmp.get_author_name());
                Log.i("Score", Integer.toString(tmp.get_score()));
                //Log.i("Description", tmp.get_description());
                Log.i("Nb_comment", Integer.toString(tmp.get_nb_comment()));
                Log.i("Points", Integer.toString(tmp.get_points()));
                Log.i("Up_vote", Integer.toString(tmp.get_up_vote()));
                Log.i("Down_vote", Integer.toString(tmp.get_down_vote()));
            }
        }
        return listData;
    }

    public void add_image_binary(String path, String title, String description) {
        final File file = new File(path);

        final ImageUploadDTO image = ImageUploadDTO.create()
                .binaryFile(file)
                .setTitle(title)
                .setDescription(description);

        api.IMAGE.uploadImage(image).queue(
                System.out::println,
                Throwable::printStackTrace
        );
    }

    public void add_image_url(String path, String title, String description) {
        final ImageUploadDTO image = ImageUploadDTO.create()
                .url(path)
                .setTitle(title)
                .setDescription(description);

        api.IMAGE.uploadImage(image).queue(
                System.out::println,
                Throwable::printStackTrace
        );
    }

    public void add_image_bitmap(ImageView img, String title, String description) {
        Log.i("Upload", "done");
        final Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        Log.i("Upload", "done1");
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
        Log.i("Upload", "done2");
        final ImageUploadDTO image = ImageUploadDTO.create()
                .base64(base64)
                .setTitle("Jmgur upload test")
                .setDescription("This image got uploaded with: https://github.com/Azzerial/Jmgur");
        Log.i("Upload", "done3");
        api.IMAGE.uploadImage(image).queue(
                System.out::println,
                Throwable::printStackTrace
        );
        Log.i("Upload", "done4");
    }

    public List<Data> get_my_post() {
        wallList = null;
        api.ACCOUNT.getUserSubmissions().get().queue(galleryElements -> {
            wallList = galleryElements;
        });
        try
        {
            Thread.sleep(400);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        List<Data> listData = new ArrayList<Data>();
        if (wallList == null) {
            listData = get_my_post();
            return listData;
        }
        int i = 0;
        for (GalleryElement it : wallList) {
            if (it == null)
                break;
            Log.i(TAG, (it == null ? "null" : "pas null"));
            final GalleryAlbum post;
            if (it instanceof GalleryAlbum) {
                post = (GalleryAlbum) it;
                if (post.getImages().isEmpty())
                    continue;
                //final String imgHash = post.getCoverHash();
                //GalleryImage postImage = null;
                String test = post.getTitle();
                for (GalleryImage postImage : post.getImages()) {
                    /*if (image.getHash().equals(imgHash)) {
                        postImage = image;
                    } else {
                        continue;
                    }*/
                    if (postImage == null)
                        postImage = post.getImages().get(0);
                    if (postImage.isAnimated())
                        continue;
                    Data tmp = new Data(postImage.getUrl(), post.getAuthorName(), post.getTitle(), post.getScore(), test, post.getCommentCount()
                            , post.getPoints(), post.getUps(), post.getDowns(), postImage.getHash(), post.getHash());
                    if (tmp != null) {
                        listData.add(tmp);
                    }
                }
            } else {
                Data tmp = new Data(it.getUrl(), it.getAuthorName(), it.getTitle(), it.getScore()
                        , it.getDescription(), it.getCommentCount(), it.getPoints(), it.getUps(), it.getDowns(), it.getHash(), "Empty");
                if (tmp != null) {
                    listData.add(tmp);
                }
            }
            for (Data tmp : listData) {
                if (tmp != null) {
                    Log.i("URL", tmp.get_url());
                    Log.i("Title", tmp.get_title());
                    Log.i("Author", tmp.get_author_name());
                    Log.i("Score", Integer.toString(tmp.get_score()));
                    //Log.i("Description", tmp.get_description());
                    Log.i("Nb_comment", Integer.toString(tmp.get_nb_comment()));
                    Log.i("Points", Integer.toString(tmp.get_points()));
                    Log.i("Up_vote", Integer.toString(tmp.get_up_vote()));
                    Log.i("Down_vote", Integer.toString(tmp.get_down_vote()));
                    Log.i("Hash1", tmp.get_hash_image());
                    Log.i("Hash2", tmp.get_hash_album());
                }
            }
        }
        return listData;
    }

    public void add_to_fav(String hash) {
        api.IMAGE.favoriteImage(hash).queue();
    }

    public void delete_img(String hash) {
        api.IMAGE.deleteImage(hash).queue();
    }

    public void delete_my_img(String hash) {
        api.ACCOUNT.deleteSelfImage(hash).queue();
    }

    public void add_comment(String hash, String comment) {
        api.GALLERY.postCommentOnGalleryPost(hash, comment).queue();
    }

    public void add_up_vote(String hash) {
        Vote vote = Vote.UP;
        api.GALLERY.updateGalleryPostVote(hash, vote).queue();
        Log.i("Hash", hash);
    }

    public void add_down_vote(String hash) {
        Vote vote = Vote.DOWN;
        api.GALLERY.updateGalleryPostVote(hash, vote).queue();
        Log.i("Hash", hash);
    }
}
