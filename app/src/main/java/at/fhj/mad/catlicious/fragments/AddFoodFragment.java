package at.fhj.mad.catlicious.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.service.CameraService;
import at.fhj.mad.catlicious.service.CameraServiceImpl;

import static android.app.Activity.RESULT_OK;
import static at.fhj.mad.catlicious.utils.RequestCode.CAMERA_REQUEST;

/**
 * Created by Simone on 17.04.2017.
 */

public class AddFoodFragment extends Fragment {

    private CameraService cameraService;
    private Fragment currentFragment;
    private ImageView imageView;

    public AddFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentFragment = this;
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);

        cameraService = new CameraServiceImpl();

        initListeners(view);

        return view;
    }

    /**
     * inits the listeners for the view components
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Uri capturedImage = data.getData();
            cameraService.showImage(capturedImage, currentFragment, imageView);
        }
    }

    /**
     * saves the food entity to the database
     */
    public void saveFood() {
        // TODO - please implement me
    }
}
