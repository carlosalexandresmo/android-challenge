package helabs.myandroidchallenge.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import helabs.myandroidchallenge.R;
import helabs.myandroidchallenge.model.Comment;
import helabs.myandroidchallenge.util.Util;

/**
 * Created by Carlos Alexandre on 12/06/2015.
 */
public class ListCommentAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    ArrayList<Comment> mList;

    public ListCommentAdapter(Context context, ArrayList<Comment> list){

        this.mList = list;
        this.mContext = context;
        inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list_comment, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.tvUserComment = (TextView)view.findViewById(R.id.tvUserComment);
            viewHolder.tvComment = (TextView)view.findViewById(R.id.tvComment);
            viewHolder.tvDateComment = (TextView)view.findViewById(R.id.tvDateComment);

        }else {
            viewHolder = (ViewHolder)view.getTag();
        }

        Comment c = mList.get(i);

        viewHolder.tvUserComment.setText(c.getRealname());
        viewHolder.tvComment.setText(c.getContent());
        viewHolder.tvDateComment.setText(Util.convertTimestamp(c.getDatecreate()));

        return view;
    }

    class ViewHolder{
        TextView tvUserComment, tvComment, tvDateComment;
    }
}
