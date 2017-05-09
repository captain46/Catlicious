package at.fhj.mad.catlicious.fragments;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.data.Image;
import at.fhj.mad.catlicious.data.ImageActivityRequest;
import at.fhj.mad.catlicious.data.entity.Animal;
import at.fhj.mad.catlicious.service.AnimalDAOService;
import at.fhj.mad.catlicious.service.AnimalDAOServiceImpl;
import at.fhj.mad.catlicious.service.CameraService;
import at.fhj.mad.catlicious.service.CameraServiceImpl;
import at.fhj.mad.catlicious.service.ImageActivityRequestChainInvoker;
import at.fhj.mad.catlicious.service.exception.RequestNotSatisfiableException;
import at.fhj.mad.catlicious.utils.ImageUtils;

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
                Toast.makeText(context, "Okay, we've updated your best friend! :)", Toast.LENGTH_LONG).show();
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
                editImageView.setImageResource(android.R.color.transparent); //clears the image view
                cameraService.captureImageFromCamera(currentFragment);
            }
        });

        Button selectImageButton = (Button) view.findViewById(R.id.edit_animal_select_image_gallery);
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
    }

    public void displayAnimal(Animal animal) {
        editAnimalName.setText(animal.getName());
        editImageView.setImageBitmap(ImageUtils.convertByteArrayToBitmap(animal.getImage()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageActivityRequest request = new ImageActivityRequest(requestCode, resultCode, data);
        ImageActivityRequestChainInvoker invoker = new ImageActivityRequestChainInvoker(context);
        Image image = null;
        try {
            image = invoker.deliver(request);
            editImageView.setImageBitmap(image.getBitmap());
            animal.setImage(image.getBytes());
        } catch (RequestNotSatisfiableException e) {
            Log.d("CAMERA", "Request aborted by user", e);
            editImageView.setImageBitmap(ImageUtils.convertByteArrayToBitmap(animal.getImage()));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == getActivity().getApplicationContext().getResources().getInteger(R.integer.PERMISSION_READ_EXT_STORAGE)) {
            cameraService.selectImageFromGallery(currentFragment);
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
