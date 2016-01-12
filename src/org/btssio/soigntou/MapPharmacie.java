package org.btssio.soigntou;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapPharmacie extends Activity {

	static final LatLng MELUN = new LatLng(48.543433, 2.655573);
	static final LatLng LYCEE = new LatLng(48.552296, 2.659521);
	static final LatLng POSITION = null;
	private GoogleMap map;
	private Location myPosition;
	JSONObject jsonResponse;
	JSONObject jsonObj;
	private String jsonString;
	Double lat;
	Double lng;
	Location posPharmacie;
	int rayon;
	Intent intentHome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		intentHome = new Intent(MapPharmacie.this, MainActivity.class);
		
		setContentView(R.layout.activity_map_pharmacie);
		myPosition = getPosition(this);
		LatLng POSITION = new LatLng(myPosition.getLatitude(),
				myPosition.getLongitude());
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		Marker pos = map.addMarker(new MarkerOptions().position(POSITION)
				.title("Moi")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.school_map)));
		rayon = this.getIntent().getExtras().getInt("rayon");
		jsonString = lireJSON();
		
		
		try {
			jsonResponse = new JSONObject(jsonString);

			for (int i = 0; i < jsonResponse.length(); i++) {
				
				jsonObj = jsonResponse.getJSONObject(i + "");
				JSONObject jsonObj2 = jsonObj.getJSONObject("fields");
				lat = jsonObj2.getDouble("lat");
				lng = jsonObj2.getDouble("lng");
				posPharmacie = new Location("Pharmacie");
				posPharmacie.setLatitude(lat);
				posPharmacie.setLongitude(lng);
				Log.i("posPharmacie", posPharmacie+"");
				if ((myPosition.distanceTo(posPharmacie) / 1000) <= rayon) {
					LatLng pharma = new LatLng(posPharmacie.getLatitude(),posPharmacie.getLongitude());
					pos = map.addMarker(new MarkerOptions()
							.position(pharma)
							.title(jsonObj2.getString("rs"))
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacie_map)));
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// Ajuste la camera sur ma position avec un zoom de 15
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(POSITION, 15));

		// Zoom possible
		map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
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

	public String lireJSON() {
		InputStream is = getResources().openRawResource(R.raw.datafinale);
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];

		try {
			Reader reader = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_pharmacie, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.ic_home) {
			startActivity(intentHome);
			return true;
		}

		return super.onOptionsItemSelected(item);

	}
}
