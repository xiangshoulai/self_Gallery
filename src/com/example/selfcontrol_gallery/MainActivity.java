package com.example.selfcontrol_gallery;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {
	
	private xsl_gallery gallery;
	private ImageAdapter adapter;
	private int[] imageIDs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		
		imageIDs = new int[]{
	        		R.drawable.gz01,
	        		R.drawable.gz02,
	        		R.drawable.gz03,
	        		R.drawable.gz04,
	        		R.drawable.gz05,
	        		R.drawable.gz06,
	        		R.drawable.gz07,
	        		R.drawable.gz08,
	        		R.drawable.gz09,
	        		R.drawable.gz10,
	        		R.drawable.gz11,
	        		R.drawable.gz12,
	        		R.drawable.gz13,
	        		R.drawable.gz14,
	        		R.drawable.gz15
	        };
	        
	        gallery = (xsl_gallery) findViewById(R.id.gallery);
	        
	        adapter = new ImageAdapter(this, imageIDs);
	        
	        adapter.CreatBitMap();
	        
	        gallery.setAdapter(adapter);
	}


}
