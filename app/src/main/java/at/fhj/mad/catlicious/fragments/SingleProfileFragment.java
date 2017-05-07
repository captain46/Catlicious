package at.fhj.mad.catlicious.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.data.entity.AnimalFoodProfile;
import at.fhj.mad.catlicious.data.entity.FoodRating;
import at.fhj.mad.catlicious.data.entity.Profile;
import at.fhj.mad.catlicious.utils.RatingListViewAdapter;

/**
 * Created by Simone on 26.04.2017.
 */

public class SingleProfileFragment extends Fragment {

    private Fragment currentFragment;
    private ListView listView;
    private ArrayAdapter<FoodRating> arrayAdapter;

    public SingleProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentFragment = this;
        View view = inflater.inflate(R.layout.fragment_singleprofile, container, false);

        Bundle bundle = currentFragment.getArguments();
        AnimalFoodProfile animalFoodProfile = (AnimalFoodProfile) bundle.getSerializable("animalFoodProfile");

        listView = (ListView) view.findViewById(R.id.food_rating_listview);
        List<FoodRating> foodRatingList = setListData(animalFoodProfile);

        arrayAdapter = new RatingListViewAdapter(getActivity() , R.layout.rating_listview, foodRatingList);
        listView.setAdapter(arrayAdapter);

        return view;
    }

    private List<FoodRating> setListData(AnimalFoodProfile animalFoodProfile) {
        List<FoodRating> foodRatingList = new ArrayList<>();

        for(Profile profile : animalFoodProfile.getProfiles()) {
            foodRatingList.add(new FoodRating(profile.getAnimal(), profile.getRating(), profile.getFood()));
        }

        return foodRatingList;
    }
}
