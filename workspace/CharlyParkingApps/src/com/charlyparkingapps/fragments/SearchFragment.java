package com.charlyparkingapps.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.charlyparkingapps.R;




public class SearchFragment extends Fragment
{

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		final View V = inflater.inflate (R.layout.search_fragment, container,
				false);
		SearchView SV = (SearchView) V.findViewById (R.id.search_view_1);

		SV.setOnQueryTextFocusChangeListener (new View.OnFocusChangeListener ()
		{

			@Override
			public void onFocusChange (View v, boolean hasFocus)
			{
				if (hasFocus)
				{
					// TODO What happens when search is focussed ?
				}
				else
				{
				
				}
			}

		});
		// Inflate the layout for this fragment
		return V;
	}

}
