package at.fhj.mad.catlicious.fragments;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import at.fhj.catlicious.common.bitmap.BitmapUtils;
import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.data.entity.Animal;
import at.fhj.mad.catlicious.service.AnimalDAOService;
import at.fhj.mad.catlicious.service.AnimalDAOServiceImpl;
import at.fhj.mad.catlicious.service.CameraService;
import at.fhj.mad.catlicious.service.CameraServiceImpl;
import at.fhj.mad.catlicious.utils.ImageUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
    private AnimalDAOService animalDAOService;
    private Animal animal;
    private Context context;
    private EditText animalNameEditText;

    public AddAnimalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        animalDAOService = new AnimalDAOServiceImpl();
        animal = new Animal();
        context = container.getContext();
        currentFragment = this;

        View view = inflater.inflate(R.layout.fragment_add_animal, container, false);

        cameraService = new CameraServiceImpl();

        initListeners(view);

        return view;
    }

    /**
     * inits the listeners for the view components
     *
     * @param view
     */
    public void initListeners(final View view) {
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
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //permission not granted, request it
                    FragmentCompat.requestPermissions(currentFragment, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            getActivity().getApplicationContext().getResources().getInteger(R.integer.PERMISSION_READ_EXT_STORAGE));
                } else {
                    cameraService.selectImageFromGallery(currentFragment);
                }
            }
        });

        animalNameEditText = (EditText) view.findViewById(R.id.animalName);
        animalNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDefaultValue(animalNameEditText);
            }
        });

        final Button saveAnimal = (Button) view.findViewById(R.id.save_animal);
        saveAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAnimal();
                Toast.makeText(v.getContext(), "New best friend added!", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void clearDefaultValue(EditText editText) {
        if (editText.getText().toString().equals("Name")) {
            editText.setText("");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            //get the image from data
            Uri capturedImage = data.getData();
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            animal.setImage(stream.toByteArray());

            //cameraService.showImage(capturedImage, currentFragment, imageView);
        }

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            //get the image from data
            Bitmap photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(this.context.getContentResolver(), data.getData());
                Bitmap scaledPhoto = BitmapUtils.downscale(photo);
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(scaledPhoto);
                animal.setImage(ImageUtil.getByteFromBitmap(scaledPhoto));
            } catch (IOException e) {
                Log.d("GALLERY", "Could not open image from gallery",e);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == getActivity().getApplicationContext().getResources().getInteger(R.integer.PERMISSION_READ_EXT_STORAGE)) {
            cameraService.selectImageFromGallery(currentFragment);
        }
    }

    /**
     * saves the animal entity to the database
     */
    public void saveAnimal() {
        animal.setName(animalNameEditText.getText().toString());
        animalDAOService.addAnimal(animal, context);

        // go back and show the list of all food entities
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        fragmentManager.popBackStack();
    }
}
