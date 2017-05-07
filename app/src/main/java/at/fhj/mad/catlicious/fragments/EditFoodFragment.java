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
import android.widget.ImageView;

import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.data.entity.Food;
import at.fhj.mad.catlicious.service.CameraService;
import at.fhj.mad.catlicious.service.CameraServiceImpl;
import at.fhj.mad.catlicious.service.FoodDAOService;
import at.fhj.mad.catlicious.service.FoodDAOServiceImpl;
import at.fhj.mad.catlicious.utils.ImageUtil;

/**
 * Created by Simone on 22.04.2017.
 */

public class EditFoodFragment extends Fragment {

    private CameraService cameraService;
    private Fragment currentFragment;
    private FoodDAOService foodDaoService;
    private Context context;
    private EditText editBrand;
    private EditText editSort;
    private ImageView editImage;
    private Food food;

    public EditFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cameraService = new CameraServiceImpl();
        foodDaoService = new FoodDAOServiceImpl();
        context = container.getContext();

        currentFragment = this;
        View view = inflater.inflate(R.layout.fragment_edit_food, container, false);

        Bundle bundle = currentFragment.getArguments();
        food = (Food) bundle.getSerializable("food");

        initFields (view);

        displayFood(food);

        Button btUpdate = (Button) view.findViewById(R.id.update_food);
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFood();
            }
        });

        return view;
    }

    /**
     * inits the view components depending on their values
     * @param view
     */
    public void initFields(View view) {
        editImage = (ImageView) view.findViewById(R.id.edit_food_image);
        editBrand = (EditText) view.findViewById(R.id.edit_brand_name);
        editSort = (EditText) view.findViewById(R.id.edit_sort_name);
    }

    /**
     * updates a selected food entity
     */
    public void updateFood() {
        food.setBrand(editBrand.getText().toString());
        food.setSort(editSort.getText().toString());
        foodDaoService.updateFood(food, context);

        // go back and show the list of all food entities
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        fragmentManager.popBackStack();
    }

    /**
     * shows the selected food entity in the edit menu
     * @param food
     */
    public void displayFood(Food food) {
        editBrand.setText(food.getBrand());
        editSort.setText(food.getSort());
        if(food.getImage() != null) {
            editImage.setImageBitmap(ImageUtil.convertByteArrayToBitmap(food.getImage()));
        }
    }
}
