package com.tarkalabs.rajiv.redditreader.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tarkalabs.rajiv.redditreader.R;
import com.tarkalabs.rajiv.redditreader.database.ReaderContract.ReaderTable;

/**
 * Created by Rajiv M on 13/04/16.
 */
public class ReaderListAdapter extends CursorAdapter {

    private Context context;

    public ReaderListAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(R.layout.item_reader, parent, false);
    }

    @Override
    public void bindView(View itemView, Context context, Cursor cursor) {
        TextView title = (TextView) itemView.findViewById(R.id.reader_title_tv);
        title.setText(cursor.getString(cursor.getColumnIndex(ReaderTable.COLUMN_TITLE)));

        TextView domain = (TextView) itemView.findViewById(R.id.reader_domain_tv);
        String domainName = context.getString(R.string.reader_open_brace) + cursor.getString(cursor.getColumnIndex(ReaderTable.COLUMN_DOMAIN)) + context.getString(R.string.reader_close_brace);
        domain.setText(domainName);

        TextView noOfCommentsSubReddit = (TextView) itemView.findViewById(R.id.reader_number_comments_and_subreddit_tv);
        String commentsCountnSubReddit = cursor.getString(cursor.getColumnIndex(ReaderTable.COLUMN_NO_COMMENTS)) + context.getString(R.string.reader_comments) + cursor.getString(cursor.getColumnIndex(ReaderTable.COLUMN_SUB_REDDIT));
        noOfCommentsSubReddit.setText(commentsCountnSubReddit);

        ImageView thumbnail = (ImageView) itemView.findViewById(R.id.reader_thumbnail_img);
        String url = cursor.getString(cursor.getColumnIndex(ReaderTable.COLUMN_THUMBNAIL));
        if (!TextUtils.isEmpty(url))
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(thumbnail);

    }

}
