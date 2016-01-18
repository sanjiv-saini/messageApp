package com.sanjusingh.mymessage;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by sanju singh on 1/18/2016.
 */
public class MessageAdapter extends CursorAdapter {

    public MessageAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.message_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView message = (TextView) view.findViewById(R.id.message);

        String person = cursor.getString(MainActivity.COL_PERSON);
        String messageText = cursor.getString(MainActivity.COL_BODY);

        message.setText(person + ":\n" + messageText);
    }
}
