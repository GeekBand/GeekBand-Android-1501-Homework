package com.example.mo.horizontallistview;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * Created by mo on 15/10/18.
 *
 * 导入sdk下recyclerview.jar包
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private String[] mDataset;
    public RecyclerViewAdapter(String[] dataset) {
        mDataset = dataset;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }


    @Override
    public int getItemCount() {
        return mDataset.length;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position]);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),
                android.R.layout.simple_list_item_1, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
}