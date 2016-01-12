package org.btssio.soigntou;

import java.util.List;
import java.util.Vector;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

public class Promo extends ActionBarActivity {

private PagerAdapter mPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_promo);
		

		// Création de la liste de Fragments que fera défiler le PagerAdapter
		List<Fragment> fragments = new Vector<Fragment>();

		// Ajout des Fragments dans la liste
		fragments.add(Fragment.instantiate(this,Produit1.class.getName()));
		fragments.add(Fragment.instantiate(this,Produit2.class.getName()));
		fragments.add(Fragment.instantiate(this,Produit3.class.getName()));

		// Création de l'adapteur qui s'occupera de l'affichage de la liste de fragments
		this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);

		// Création du pager
		ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
		
		// Affectation de l'adapteur au ViewPager
		pager.setAdapter(this.mPagerAdapter);
		pager.setCurrentItem(0);
		
	}
}
