package at.fhj.mad.catlicious.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.fhj.mad.catlicious.R;

/**
 * Created by Simone on 26.04.2017.
 */

public class EditAnimalFragment extends Fragment {

    public EditAnimalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_animal, container, false);

        return view;
    }
}
