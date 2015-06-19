package helabs.myandroidchallenge.activity;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import helabs.myandroidchallenge.R;
import helabs.myandroidchallenge.adapter.ListCommentAdapter;
import helabs.myandroidchallenge.app.MyApplication;
import helabs.myandroidchallenge.model.Comment;
import helabs.myandroidchallenge.model.InfoPhoto;
import helabs.myandroidchallenge.util.Constants;
import helabs.myandroidchallenge.util.Util;

/**
 * Created by Carlos Alexandre on 11/06/2015.
 */

@EActivity(R.layout.activity_detail_photo)
public class DetailPhotoActivity extends ActionBarActivity {

    private static String TAG = DetailPhotoActivity.class.getSimpleName();

    private String URLphoto, URLcomments;
    private Comment comment;
    private ListCommentAdapter commentAdapter;
    private ArrayList<Comment> arrComments;

    @ViewById(R.id.toolbar_detail)
    Toolbar toolbar_detail;

    @ViewById(R.id.tvUser)
    TextView tvUser;

    @ViewById(R.id.ivPhoto)
    ImageView ivPhoto;

    @ViewById(R.id.loadingPanel)
    RelativeLayout loadingPanel;

    @ViewById(R.id.tvTitle)
    TextView tvTitle;

    @ViewById(R.id.listComments)
    ListView listComments;

    @ViewById(R.id.tvNumberViews)
    TextView tvNumberViews;

    @ViewById(R.id.tvDescription)
    TextView tvDescription;

    @Extra("id")
    String idPhoto;

    @AfterViews
    void init() {
        setSupportActionBar(toolbar_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUrlPhoto(idPhoto);
        loadInfoPhoto();
    }

    private void loadInfoPhoto(){
        if (Util.isConnectingToInternet(this)){
            getInfoPhotoRequest();
        } else {
            Util.toastShort(this, "Internet indisponível.");
        }
    }

    @OptionsItem(android.R.id.home)
    boolean homeSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, getIntent());
        }
        return true;
    }

    private void getInfoPhotoRequest(){

        final InfoPhoto infoPhoto = new InfoPhoto();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,
                getURLphoto(),
                (String) null,
                new Response.Listener<JSONObject>() {



                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject photo = response.getJSONObject("photo");
                            infoPhoto.id = photo.getString("id");
                            infoPhoto.secret = photo.getString("secret");
                            infoPhoto.server = photo.getString("server");
                            infoPhoto.farm = photo.getString("farm");
                            photo.getString("dateuploaded");
                            photo.getString("isfavorite");
                            photo.getString("license");
                            photo.getString("safety_level");
                            photo.getString("rotation");

                            if (photo.has("originalsecret"))photo.getString("originalsecret");
                            if (photo.has("originalformat"))photo.getString("originalformat");

                            JSONObject owner = photo.getJSONObject("owner");
                            owner.getString("nsid");
                            infoPhoto.username = owner.getString("username");
                            owner.getString("realname");
                            owner.getString("location");
                            owner.getString("iconserver");
                            owner.getString("iconfarm");
                            owner.getString("path_alias");

                            JSONObject title = photo.getJSONObject("title");
                            infoPhoto.title = title.getString("_content");

                            JSONObject description = photo.getJSONObject("description");
                            infoPhoto.description = description.getString("_content");

                            JSONObject visibility = photo.getJSONObject("visibility");
                            visibility.getString("ispublic");
                            visibility.getString("isfriend");
                            visibility.getString("isfamily");

                            JSONObject dates = photo.getJSONObject("dates");
                            dates.getString("posted");
                            dates.getString("taken");
                            dates.getString("takengranularity");
                            dates.getString("takenunknown");
                            dates.getString("lastupdate");

                            infoPhoto.views = photo.getString("views");

                            JSONObject editability = photo.getJSONObject("editability");
                            editability.getString("cancomment");
                            editability.getString("canaddmeta");

                            JSONObject publiceditability = photo.getJSONObject("publiceditability");
                            publiceditability.getString("cancomment");
                            publiceditability.getString("canaddmeta");

                            JSONObject usage = photo.getJSONObject("usage");
                            usage.getString("candownload");
                            usage.getString("canblog");
                            usage.getString("canprint");
                            usage.getString("canshare");

                            JSONObject comments = photo.getJSONObject("comments");
                            infoPhoto.comments= comments.getString("_content");

                            JSONObject notes = photo.getJSONObject("notes");
                            JSONArray note = notes.getJSONArray("note");

                            /*

                             */
                            JSONObject people = photo.getJSONObject("people");
                            people.getString("haspeople");

                            JSONObject tags = photo.getJSONObject("tags");
                            JSONArray tag = tags.getJSONArray("tag");

                            for (int i = 0; i < tag.length(); i++) {

                                JSONObject tg = tag.getJSONObject(i);
                                tg.getString("id");
                                tg.getString("author");
                                tg.getString("authorname");
                                tg.getString("raw");
                                tg.getString("_content");
                                tg.getString("machine_tag");
                            }

                            JSONObject urls = photo.getJSONObject("urls");
                            JSONArray url = urls.getJSONArray("url");

                            for (int i = 0; i < url.length(); i++) {

                                JSONObject jobj = url.getJSONObject(i);
                                jobj.getString("type");
                                //urlImg = jobj.getString("_content");
                            }

                            photo.getString("media");
                            response.getString("stat");

                            setWidgets(infoPhoto);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void getCommentsRequest(){

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                getURLcomments(),
                (String) null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject comments = response.getJSONObject("comments");
                            comments.getString("photo_id");

                            JSONArray arrComment = comments.getJSONArray("comment");

                            arrComments = new ArrayList<>();

                            for (int i = 0; i < arrComment.length(); i++) {

                                comment = new Comment();

                                JSONObject jsonObject = arrComment.getJSONObject(i);
                                comment.setId(jsonObject.getString("id"));
                                comment.setAuthor(jsonObject.getString("author"));
                                comment.setAuthorname(jsonObject.getString("authorname"));
                                comment.setIconserver(jsonObject.getString("iconserver"));
                                comment.setIconfarm(jsonObject.getString("iconfarm"));
                                comment.setDatecreate(jsonObject.getString("datecreate"));
                                comment.setPermalink(jsonObject.getString("permalink"));
                                comment.setPath_alias(jsonObject.getString("path_alias"));
                                comment.setRealname(jsonObject.getString("realname"));
                                comment.setContent(jsonObject.getString("_content"));

                                arrComments.add(comment);
                            }
                            response.getString("stat");

                            commentAdapter = new ListCommentAdapter(DetailPhotoActivity.this,arrComments);
                            listComments.setAdapter(commentAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjReq);
    }

    void setWidgets(InfoPhoto photo){

        String strUrlPhoto = "https://farm"+photo.farm+".staticflickr.com/"+photo.server+"/"+photo.id+"_"+photo.secret+".jpg";

        Glide.with(DetailPhotoActivity.this)
                .load(strUrlPhoto)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivPhoto);
        loadingPanel.setVisibility(View.GONE);

        tvUser.setText(photo.username); // autor
        tvNumberViews.setText(photo.views +" views");
        tvTitle.setText(photo.title);
        tvDescription.setText(Html.fromHtml(photo.description));

        if(Integer.parseInt(photo.comments)!=0){
            setURLcomments(photo.id);
             getCommentsRequest();
         }
    }

    void setUrlPhoto(String idPhoto){
        URLphoto=Constants.URL_BASE+"flickr.photos.getInfo&api_key=" +
                Constants.KEY+"&photo_id="+idPhoto+"&format=json&nojsoncallback=1";
        Log.w(TAG,URLphoto);
    }

    String getURLphoto(){
        return URLphoto;
    }

    void setURLcomments(String idPhoto){
        URLcomments=Constants.URL_BASE+"flickr.photos.comments.getList&api_key=" +
                Constants.KEY+"&photo_id="+idPhoto+"&format=json&nojsoncallback=1";
        Log.w(TAG,URLphoto);
    }

    String getURLcomments(){
        return URLcomments;
    }
}