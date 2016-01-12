package org.btssio.soigntou;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListPharmacie extends ListActivity implements OnItemClickListener {

	String jsonString;
	JSONObject jsonResponse;
	JSONObject jsonObj;
	String nom;
	Double lat;
	Double lng;
	String info;
	List<String> listPharmacie = new ArrayList();
	List<String> listRenseignement = new ArrayList();
	int rayon;
	Location myPosition;
	Location posPharmacie;
	Location posUtilisateur;
	Intent intentMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		intentMap = new Intent(ListPharmacie.this, MapPharmacie.class);
		
		rayon = this.getIntent().getExtras().getInt("rayon");
		ListView lv = getListView();
		lv.setOnItemClickListener(this);

		jsonString = readJSON();	
		myPosition = getPosition(this);
		
		try{
			jsonResponse = new JSONObject(jsonString);
			Log.i("long tab",jsonResponse.length()+"");
			for(int i = 0; i < jsonResponse.length() ; i++)
			{
				jsonObj = jsonResponse.getJSONObject(i+"");
				JSONObject jsonObj2 = jsonObj.getJSONObject("fields");
				nom = jsonObj2.getString("rs");
				lat = jsonObj2.getDouble("lat");
				lng = jsonObj2.getDouble("lng");
				posPharmacie = new Location("Pharmacie");
				posPharmacie.setLatitude(lat);
				posPharmacie.setLongitude(lng);
				Log.i("myPosition", posUtilisateur+"");
				Log.i("posPharmacie", posPharmacie+"");
				
				//posUtilisateur = new Location ("Utilisateur");
				//posUtilisateur.setLatitude(myPosition.getLatitude());
				//posUtilisateur.setLongitude(myPosition.getLongitude());
				
				if((myPosition.distanceTo(posPharmacie)/1000)<= rayon){
					listPharmacie.add(nom); 
					info = jsonObj2.getString("telephone");
					listRenseignement.add(info);
				}

			}	
		} catch (JSONException e) {
			e.printStackTrace();
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_list_pharmacie, listPharmacie);
		setListAdapter(adapter);
	}
	

	public String readJSON() {
		InputStream is = getResources().openRawResource(R.raw.datafinale);
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];

		try {
			Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			int n;

			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return writer.toString();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
		.setTitle("Renseignements :")
		.setMessage("Téléphone : 0"+listRenseignement.get(position))
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				// continue
			}
		})
		.show();
	}
	
	public final static Location getPosition(Context _context) {
		Location location = null;
		try {
			LocationManager locationManager = (LocationManager)
					_context.getSystemService(Context.LOCATION_SERVICE);
			List<String> providers = locationManager.getProviders(true);
			for (int i = providers.size() - 1; i >= 0; i--) {
				location = locationManager.getLastKnownLocation(providers.get(i));
			}
		} catch (Exception e) {}
		return location;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_pharmacie, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.ic_map) {
			intentMap.putExtra("rayon", rayon);
			startActivity(intentMap);
			return true;
		}

		return super.onOptionsItemSelected(item);

	}
}
