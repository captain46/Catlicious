package at.fhj.mad.catlicious.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.data.Image;
import at.fhj.mad.catlicious.data.ImageActivityRequest;
import at.fhj.mad.catlicious.data.entity.Food;
import at.fhj.mad.catlicious.service.CameraService;
import at.fhj.mad.catlicious.service.CameraServiceImpl;
import at.fhj.mad.catlicious.service.FoodDAOService;
import at.fhj.mad.catlicious.service.FoodDAOServiceImpl;
import at.fhj.mad.catlicious.service.ImageActivityRequestChainInvoker;
import at.fhj.mad.catlicious.service.exception.RequestNotSatisfiableException;

/**
 * Created by Simone on 17.04.2017.
 */

public class AddFoodFragment extends Fragment {

    private CameraService cameraService;
    private Fragment currentFragment;
    private ImageView imageView;
    private EditText brandEditText;
    private EditText sortEditText;
    private FoodDAOService foodDaoService;
    private Context context;
    private Food food;

    public AddFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        foodDaoService = new FoodDAOServiceImpl();
        context = container.getContext();
        food = new Food();

        currentFragment = this;
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);

        cameraService = new CameraServiceImpl();

        initListeners(view);

        initFields(view);

        return view;
    }

    /**
     * inits the listeners for the view components
     *
     * @param view
     */
    private void initListeners(View view) {
        imageView = (ImageView) view.findViewById(R.id.food_image_from_camera);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraService.captureImageFromCamera(currentFragment);
            }
        });

        Button btSaveFood = (Button) view.findViewById(R.id.save_food);
        btSaveFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFood();
            }
        });
    }

    /**
     * inits the view components depending on their values
     *
     * @param view
     */
    private void initFields(View view) {
        brandEditText = (EditText) view.findViewById(R.id.brand_name);
        sortEditText = (EditText) view.findViewById(R.id.sort_name);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageActivityRequest request = new ImageActivityRequest(requestCode, resultCode, data);
        ImageActivityRequestChainInvoker invoker = new ImageActivityRequestChainInvoker(context);
        Image image = null;
        try {
            image = invoker.deliver(request);
            imageView.setImageBitmap(image.getBitmap());
        } catch (RequestNotSatisfiableException e) {
            Log.d("CAMERA", "Request aborted by user", e);
        }
    }

    /**
     * saves the food entity to the database
     */
    public void saveFood() {
        food.setBrand(brandEditText.getText().toString());
        food.setSort(sortEditText.getText().toString());
        foodDaoService.addFood(food, context);
        Toast.makeText(context, "Yummy! Food successfully added!", Toast.LENGTH_LONG).show();
        // go back and show the list of all food entities
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        fragmentManager.popBackStack();
    }
}
