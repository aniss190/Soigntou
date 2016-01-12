package org.btssio.soigntou;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Produit1 extends Fragment {

	private ImageView img;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.produit,container,false);
		
		img = (ImageView) view.findViewById(R.id.imgProduit);
		img.setImageResource(R.drawable.img1);
		return view;
	}

}
