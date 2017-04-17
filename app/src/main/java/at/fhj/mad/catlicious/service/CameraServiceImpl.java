package at.fhj.mad.catlicious.service;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.IOException;

import static at.fhj.mad.catlicious.utils.RequestCode.CAMERA_REQUEST;
import static at.fhj.mad.catlicious.utils.RequestCode.GALLERY_REQUEST;

/**
 * Created by Simone on 17.04.2017.
 */

public class CameraServiceImpl implements CameraService {

    @Override
    public void captureImageFromCamera(Fragment fragment) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fragment.startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void selectImageFromGallery(Fragment fragment) {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        fragment.startActivityForResult(Intent.createChooser(galleryIntent, "select a picture"), GALLERY_REQUEST);
    }

    @Override
    public void showImage(Uri uri, Fragment fragment, ImageView imageView) {
        try {
            ContentResolver contentResolver = fragment.getActivity().getApplicationContext().getContentResolver();
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);

            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
