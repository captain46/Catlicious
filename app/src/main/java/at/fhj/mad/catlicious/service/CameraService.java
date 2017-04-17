package at.fhj.mad.catlicious.service;

import android.app.Fragment;
import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by Simone on 17.04.2017.
 */

public interface CameraService {

    /**
     * creates a new intent for capturing images by the camera
     * @param fragment - the current fragment of the caller
     */
    void captureImageFromCamera(Fragment fragment);

    /**
     * creates a new intent which opens the gallery for selecting an image
     * @param fragment - the current fragment of the caller
     */
    void selectImageFromGallery(Fragment fragment);

    /**
     * reads an image from the given uri and displays it
     * @param uri - path of the captured / selected image
     * @param fragment - the current fragment of the caller
     * @param imageView - view component on which the image should be shown
     */
    void showImage(Uri uri, Fragment fragment, ImageView imageView);
}
