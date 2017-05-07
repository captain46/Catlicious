package at.fhj.mad.catlicious.fragments;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v13.app.FragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.data.entity.Animal;
import at.fhj.mad.catlicious.service.AnimalDAOService;
import at.fhj.mad.catlicious.service.AnimalDAOServiceImpl;
import at.fhj.mad.catlicious.service.CameraService;
import at.fhj.mad.catlicious.service.CameraServiceImpl;
import at.fhj.mad.catlicious.utils.ImageUtil;

import static android.app.Activity.RESULT_OK;
import static at.fhj.mad.catlicious.utils.RequestCode.CAMERA_REQUEST;
import static at.fhj.mad.catlicious.utils.RequestCode.GALLERY_REQUEST;

/**
 * Created by Simone on 26.04.2017.
 */

public class EditAnimalFragment extends Fragment {

    private Fragment currentFragment;
    private Context context;
    private AnimalDAOService animalDAOService;
    private EditText editAnimalName;
    private ImageView editImageView;
    private CameraService cameraService;
    private Animal animal;

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
        animal = (Animal) bundle.getSerializable("animal");

        cameraService = new CameraServiceImpl();

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

        editImageView = (ImageView) view.findViewById(R.id.edit_animal_image_from_camera);
        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraService.captureImageFromCamera(currentFragment);
            }
        });

        Button selectImageButton = (Button) view.findViewById(R.id.edit_animal_select_image_gallery);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraService.selectImageFromGallery(currentFragment);
            }
        });
    }

    public void displayAnimal(Animal animal) {
        editAnimalName.setText(animal.getName());
        editImageView.setImageBitmap(ImageUtil.convertByteArrayToBitmap(animal.getImage()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data != null) {
            //get the image from data
            Uri capturedImage = data.getData();
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            editImageView.setImageBitmap(photo);
            animal.setImage(ImageUtil.getByteFromBitmap(photo));
            //cameraService.showImage(capturedImage, currentFragment, imageView);
        }

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            //get the image from data
            Uri capturedImage = data.getData();
            editImageView.setImageURI(capturedImage);
            cameraService.showImage(capturedImage, currentFragment, editImageView);
            ContentResolver contentResolver = currentFragment.getActivity().getApplicationContext().getContentResolver();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, capturedImage);
                animal.setImage(ImageUtil.getByteFromBitmap(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void updateAnimal(Animal animal) {
        animal.setName(editAnimalName.getText().toString());
        animalDAOService.updateAnimal(animal, context);

        // go back and show the list of all food entities
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        fragmentManager.popBackStack();
    }
}
