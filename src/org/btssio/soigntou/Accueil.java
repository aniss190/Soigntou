package org.btssio.soigntou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Accueil extends Activity {

	private Button btnList;
	private Button btnPromo;
	private Intent intentPharmacie;
	private Intent intentPromo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accueil);
				
		intentPharmacie = new Intent(Accueil.this, MainActivity.class);
		intentPromo = new Intent(Accueil.this, Promo.class);
		
		btnList = (Button) findViewById(R.id.btnList);
		btnList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				startActivity(intentPharmacie);
			}
		});
		
		btnPromo = (Button) findViewById(R.id.btnPromo);
		btnPromo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				startActivity(intentPromo);
			}
		});
		

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accueil, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
