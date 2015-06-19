package helabs.myandroidchallenge.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import helabs.myandroidchallenge.R;
import helabs.myandroidchallenge.adapter.PhotoAdapter;
import helabs.myandroidchallenge.app.MyApplication;
import helabs.myandroidchallenge.model.NumberPhoto;
import helabs.myandroidchallenge.model.Photo;
import helabs.myandroidchallenge.util.Constants;
import helabs.myandroidchallenge.util.Util;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    public ArrayList<Photo> mPhotos;
    Photo photo;
    NumberPhoto numberPhoto;
    private ProgressDialog pDialog;
    private int page = 1;

    @ViewById(R.id.toolbar_main)
    Toolbar mToolbar;

    @ViewById(R.id.reclyclerView)
    RecyclerView mRecyclerView;

    @AfterViews
    void init(){
        setSupportActionBar(mToolbar);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Carregando fotos recentes...");
        pDialog.setCancelable(false);

        if (Util.isConnectingToInternet(this)){
            getListPhotosRequest(page, false);
        } else {
            Util.toastShort(this, "Internet indisponível.");
        }

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager lln = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                if (mPhotos.size() == lln.findLastCompletelyVisibleItemPosition() + 1) {
                    Log.w(TAG, "Ultimo Item");
                    getListPhotosRequest(page++, true);
                }
            }
        });
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void loadRecyclerView(ArrayList<Photo> photos){
        PhotoAdapter photoAdapter = new PhotoAdapter(this, photos);
        mRecyclerView.setAdapter(photoAdapter);
    }

    private void getListPhotosRequest(int page, final boolean load){

        Log.w(TAG,""+page);

        final ArrayList<Photo> listPhotos = new ArrayList<>();
        showpDialog();
        numberPhoto = new NumberPhoto();

        String URL = Constants.URL_BASE+"flickr.photos.getRecent" +
                "&api_key=013df90574ef9fab4bd6695cc79a2034" +
                "&page="+page+
                "&format=json&nojsoncallback=1";

        Log.w(TAG,"Url: "+URL);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL,
                (String) null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        if (response.length()>0){
                            try {
                                JSONObject photos = response.getJSONObject("photos");
                                numberPhoto.page = Integer.parseInt(photos.getString("page"));
                                numberPhoto.pages = Integer.parseInt(photos.getString("pages"));
                                numberPhoto.perpage = Integer.parseInt(photos.getString("perpage"));
                                numberPhoto.total = Integer.parseInt(photos.getString("total"));

                                JSONArray jsonArray = photos.getJSONArray("photo");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    photo = new Photo();

                                    JSONObject jobjPhoto = jsonArray.getJSONObject(i);
                                    photo.setId(jobjPhoto.getString("id"));
                                    photo.setOwner(jobjPhoto.getString("owner"));
                                    photo.setSecret(jobjPhoto.getString("secret"));
                                    photo.setServer(jobjPhoto.getString("server"));
                                    photo.setFarm(Integer.parseInt(jobjPhoto.getString("farm")));
                                    photo.setTitle(jobjPhoto.getString("title"));
                                    photo.setIspublic(Integer.parseInt(jobjPhoto.getString("ispublic")));
                                    photo.setIsfriend(Integer.parseInt(jobjPhoto.getString("isfriend")));
                                    photo.setIsfamily(Integer.parseInt(jobjPhoto.getString("isfamily")));

                                    listPhotos.add(photo);
                                }
                                response.getString("stat");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            hidepDialog();

                            if (load){
                                PhotoAdapter photoAdapter = (PhotoAdapter) mRecyclerView.getAdapter();
                                ArrayList<Photo> listAux = listPhotos;

                                for (int i = 0; i < listAux.size(); i++) {
                                    photoAdapter.addListItem(listAux.get(i), mPhotos.size());
                                }

                            } else {
                                mPhotos = listPhotos;
                                loadRecyclerView(mPhotos);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjReq);
    }
}
