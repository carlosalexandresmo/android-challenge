package helabs.myandroidchallenge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import helabs.myandroidchallenge.R;

import helabs.myandroidchallenge.activity.DetailPhotoActivity;
import helabs.myandroidchallenge.activity.DetailPhotoActivity_;
import helabs.myandroidchallenge.model.Photo;

/**
 * Created by Carlos Alexandre on 11/06/2015.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

    private ArrayList<Photo> mPhotos;
    private LayoutInflater mLayoutInflater;
    private Context context;
    private String urlImage = "", id_photo = "";
    public static final String sufix = "_q";


    public PhotoAdapter(Context context, ArrayList<Photo> photos){
        this.mPhotos = photos;
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addListItem(Photo p ,int postion){
        mPhotos.add(p);
        notifyItemInserted(postion);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.i("LOG", "onCreateViewHolder");
        View view = mLayoutInflater.inflate(R.layout.item_photo_recent, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder,int i) {
        Photo photo = mPhotos.get(i);
        setUrlImage(String.valueOf(photo.getFarm()),photo.getServer(),photo.getId(),photo.getSecret());

        Glide.with(context)
                .load(getUrlImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(myViewHolder.ivItemPhoto);

        myViewHolder.tvItemName.setText(photo.getTitle());
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    public void setUrlImage(String farm, String server, String id, String secret){
        urlImage = "https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+sufix+".jpg";
    }

    public String getUrlImage(){
        return urlImage;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivItemPhoto;
        private TextView tvItemName;

        public MyViewHolder(View v){
            super(v);
            ivItemPhoto = (ImageView)v.findViewById(R.id.ivItemPhoto);
            tvItemName = (TextView)v.findViewById(R.id.tvItemName);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailPhotoActivity_.class);
                    intent.putExtra("id", mPhotos.get(getPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }


    }
}

