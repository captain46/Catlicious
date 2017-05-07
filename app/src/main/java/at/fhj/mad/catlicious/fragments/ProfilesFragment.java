package at.fhj.mad.catlicious.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.data.entity.Animal;
import at.fhj.mad.catlicious.data.entity.AnimalFoodProfile;
import at.fhj.mad.catlicious.data.entity.Profile;
import at.fhj.mad.catlicious.service.AnimalDAOService;
import at.fhj.mad.catlicious.service.AnimalDAOServiceImpl;

/**
 * Created by Simone on 17.04.2017.
 */

public class ProfilesFragment extends Fragment{

    private ListView listView;
    private AnimalDAOService animalDAOService;
    private Context context;

    public ProfilesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        animalDAOService = new AnimalDAOServiceImpl();
        context = container.getContext();

        View view = inflater.inflate(R.layout.fragment_profiles, container, false);

        initListeners(view);

        showAllAnimals();

        return view;
    }

    public void initListeners(View view) {
        listView = (ListView) view.findViewById(R.id.allAnimals_profiles);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animal animal = (Animal) parent.getAdapter().getItem(position);
                List<Profile> profiles = animalDAOService.getProfiles(animal, context);
                AnimalFoodProfile animalFoodProfile = convertListToAnimalFoodProfile(profiles);
                Fragment fragment = new SingleProfileFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("animalFoodProfile", animalFoodProfile);
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void showAllAnimals() {
        List<Animal> animalList = animalDAOService.getAllAnimals(context);
        ArrayAdapter<Animal> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, animalList);
        listView.setAdapter(arrayAdapter);
    }

    private AnimalFoodProfile convertListToAnimalFoodProfile(List<Profile> profiles) {
        AnimalFoodProfile animalFoodProfile = new AnimalFoodProfile();
        animalFoodProfile.setProfiles(profiles);
        return animalFoodProfile;
    }
}
