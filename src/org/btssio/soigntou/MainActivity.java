package org.btssio.soigntou;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	Button btnR;
	Intent intentList;
	EditText rayon;
	String distance;
	int distance2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnR = (Button) findViewById(R.id.btnR);
		btnR.setOnClickListener(this);
		rayon = (EditText) findViewById(R.id.textRayon);
		intentList = new Intent(MainActivity.this, ListPharmacie.class);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		distance = rayon.getText().toString();
		if(distance.equals("")){
			new AlertDialog.Builder(this)
			.setTitle("Attention !")
			.setMessage("Veuillez entrer un rayon correcte !")
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
	
				}
				
			})
			.setIcon(android.R.drawable.ic_dialog_alert)
			.show();
		}
		else {
			distance2 = Integer.parseInt(distance);
			intentList.putExtra("rayon", distance2);
			startActivity(intentList);
		}
	}
}
