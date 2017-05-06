package at.fhj.mad.catlicious.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.data.entity.Animal;
import at.fhj.mad.catlicious.service.AnimalDAOService;
import at.fhj.mad.catlicious.service.AnimalDAOServiceImpl;

/**
 * Created by Simone on 26.04.2017.
 */

public class EditAnimalFragment extends Fragment {

    private Fragment currentFragment;
    private Context context;
    private AnimalDAOService animalDAOService;
    private EditText editAnimalName;

    public EditAnimalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        animalDAOService = new AnimalDAOServiceImpl();
        context = container.getContext();
        currentFragment = this;
        View view = inflater.inflate(R.layout.fragment_edit_animal, container, false);

        Bundle bundle = currentFragment.getArguments();
        final Animal animal = (Animal) bundle.getSerializable("animal");

        initFields(view);

        displayAnimal(animal);

        Button btUpdate = (Button) view.findViewById(R.id.update_animal);
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnimal(animal);
            }
        });

        return view;
    }

    public void initFields(View view) {
        editAnimalName = (EditText) view.findViewById(R.id.edit_animalName);
    }

    public void displayAnimal(Animal animal) {
        editAnimalName.setText(animal.getName());
    }

    public void updateAnimal(Animal animal) {
        animal.setName(editAnimalName.getText().toString());
        animalDAOService.updateAnimal(animal, context);

        // go back and show the list of all food entities
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        fragmentManager.popBackStack();
    }
}
