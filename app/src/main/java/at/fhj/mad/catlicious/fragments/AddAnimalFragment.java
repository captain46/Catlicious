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

import static android.app.Activity.RESULT_OK;

/**
 * Created by Simone on 17.04.2017.
 */

public class AddAnimalFragment extends Fragment {

    private static final int CAMERA_REQUEST = 1000;
    private static final int GALLERY_REQUEST = 1001;
    private ImageView imageView;

    public AddAnimalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_animal, container, false);

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
                takeImageFromCamera(v);
            }
        });

        Button selectImageButton = (Button) view.findViewById(R.id.select_image_gallery);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();
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

    public void takeImageFromCamera(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void selectImageFromGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "select a picture"), GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            //get the image from data
            Uri capturedImage = data.getData();

            showImage(capturedImage);
        }

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            //get the image from data
            Uri selectedImage = data.getData();

            showImage(selectedImage);
        }
    }

    /**
     * reads the image from the given uri and displays it
     * @param uri
     */
    public void showImage(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), uri);

            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * saves the animal entity to the database
     */
    public void saveAnimal() {
       // TODO - please implement me
    }
}
