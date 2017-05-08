package at.fhj.mad.catlicious.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import at.fhj.catlicious.common.bitmap.BitmapUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Thomas on 07.05.2017.
 */

public abstract class ImageUtils {

    public static byte[] getByteFromBitmap(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] byteArrayToBeCOnvertedIntoBitMap) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(
                byteArrayToBeCOnvertedIntoBitMap, 0,
                byteArrayToBeCOnvertedIntoBitMap.length);
        if (bitmap == null) {
            return Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        }
        return bitmap;
    }

    /**
     * Returns an byte aray resolved out of the given {@link Uri}.
     * If the the image could not be resolved null is returned
     *
     * @param context
     * @param uri
     * @param downscale downscales images larger than full hd {@link BitmapUtils#downscale(Bitmap)}
     * @return
     */
    public static Bitmap resolveFromContentResolver(Context context, Uri uri, boolean downscale) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

            if (downscale) {
                return BitmapUtils.downscale(bitmap);
            }
            return bitmap;
        } catch (IOException e) {
            Log.d("GALLERY", "Could not open image from gallery", e);
        }
        return null;
    }
}