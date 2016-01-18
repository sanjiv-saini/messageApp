package com.sanjusingh.mymessage;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private final static int MessageLoader = 0;

    private Context context;


    private String[] projection= {
            Telephony.TextBasedSmsColumns.PERSON,
            Telephony.TextBasedSmsColumns.BODY
    };

    public static final int COL_PERSON = 0;
    public static final int COL_BODY = 1;

    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        messageAdapter = new MessageAdapter(this, null, 0);

        ListView messageList = (ListView) findViewById(R.id.messageList);

        messageList.setAdapter(messageAdapter);

        getLoaderManager().initLoader(MessageLoader, null, this);

        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    Intent intent = new Intent(context, DetailMessageActivity.class);
                    intent.putExtra("message", cursor.getString(COL_BODY));
                    startActivity(intent);
                }
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                Telephony.Sms.Inbox.CONTENT_URI,
                projection,
                null,
                null,
                Telephony.Sms.Inbox.DEFAULT_SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        messageAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        messageAdapter.swapCursor(null);
    }
}
