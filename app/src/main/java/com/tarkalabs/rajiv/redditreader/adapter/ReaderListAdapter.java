package com.tarkalabs.rajiv.redditreader.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.tarkalabs.rajiv.redditreader.R;
import com.tarkalabs.rajiv.redditreader.model.Child;
import com.tarkalabs.rajiv.redditreader.model.Data_;

import java.util.List;

/**
 * Created by vagmi on 12/04/16.
 */
public class ReaderListAdapter extends RecyclerView.Adapter<ReaderListAdapter.ViewHolder> implements View.OnClickListener {


    private List<Child> items;

    @Override
    public void onClick(View v) {

    }


    public ReaderListAdapter(List<Child> items){
        this.items = items;
    }

    public void setItems(List<Child> items) {
        this.items = items;
        Log.d("RECYCLERADAPTER", "items has been set");
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reader, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Child child = items.get(position);
        Log.i("test",child.getData().getTitle());
        holder.title.setText(child.getData().getTitle());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.reader_title_tv);

        }
    }
}
