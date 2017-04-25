package at.fhj.mad.catlicious.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.service.AnimalDAOServiceImpl;

/**
 * Created by Simone on 26.04.2017.
 */

public class SingleProfileFragment extends Fragment {

    public SingleProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profiles, container, false);

        return view;
    }
}
