package com.roryok.PlaylistPurge;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class PlaylistPurge extends ListActivity {
	
	private List<String> list = new ArrayList<String>();
	private final String [] STAR= {"*"};
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        ListAdapter adapter = createAdapter();
        setListAdapter(adapter);
    }
 
    /**
     * Creates and returns a list adapter for the current list activity
     * @return
     */
    protected ListAdapter createAdapter()
    {
    	// return play-lists
    	Uri playlist_uri= MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;    
	    Cursor cursor= managedQuery(playlist_uri, STAR, null,null,null);
	    cursor.moveToFirst();
	    for(int r= 0; r<cursor.getCount(); r++, cursor.moveToNext()){
	    	int i = cursor.getInt(0);
        	int l = cursor.getString(1).length();
        	if(l>0){
    			// keep these, and let me know
        		list.add("Keeping : " + cursor.getString(2) + " : id(" + i + ")");
        	}else{
        		// delete any play-lists with a data length of '0'
        		Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, i);
        		getContentResolver().delete(uri, null, null);
        		list.add("Deleted : " + cursor.getString(2) + " : id(" + i + ")");
        	}
	    }	    
	    cursor.close();
    	// Create a simple array adapter (of type string) with the test values
    	ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
 
    	return adapter;
    }
}