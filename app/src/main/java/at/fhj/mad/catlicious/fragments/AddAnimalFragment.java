package at.fhj.mad.catlicious.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.nio.BufferUnderflowException;

import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.service.CameraService;
import at.fhj.mad.catlicious.service.CameraServiceImpl;

import static android.app.Activity.RESULT_OK;
import static at.fhj.mad.catlicious.utils.RequestCode.CAMERA_REQUEST;
import static at.fhj.mad.catlicious.utils.RequestCode.GALLERY_REQUEST;

/**
 * Created by Simone on 17.04.2017.
 */

public class AddAnimalFragment extends Fragment {

    private CameraService cameraService;
    private Fragment currentFragment;
    private ImageView imageView;

    public AddAnimalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentFragment = this;
        View view = inflater.inflate(R.layout.fragment_add_animal, container, false);

        cameraService = new CameraServiceImpl();

        initListeners(view);

        return view;
    }


    /**
     * inits the listeners for the view components
     * @param view
     */
    public void initListeners(View view) {
        imageView = (ImageView) view.findViewById(R.id.image_from_camera);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraService.captureImageFromCamera(currentFragment);
            }
        });

        Button selectImageButton = (Button) view.findViewById(R.id.select_image_gallery);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraService.selectImageFromGallery(currentFragment);
            }
        });

        final EditText animalName = (EditText) view.findViewById(R.id.animalName);
        animalName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDefaultValue(animalName);
            }
        });

        final Button saveAnimal = (Button) view.findViewById(R.id.save_animal);
        saveAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAnimal();
            }
        });

    }

    public void clearDefaultValue(EditText editText) {
        if(editText.getText().toString().equals("Name")) {
            editText.setText("");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            //get the image from data
            Uri capturedImage = data.getData();

            cameraService.showImage(capturedImage, currentFragment, imageView);
        }

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            //get the image from data
            Uri selectedImage = data.getData();

            cameraService.showImage(selectedImage, currentFragment, imageView);
        }
    }

    /**
     * saves the animal entity to the database
     */
    public void saveAnimal() {
       // TODO - please implement me
    }
}
