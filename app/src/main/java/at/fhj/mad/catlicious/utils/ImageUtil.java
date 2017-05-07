package at.fhj.mad.catlicious.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Thomas on 07.05.2017.
 */

public class ImageUtil {

    public static byte[] getByteFromBitmap(Bitmap bmp)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] byteArrayToBeCOnvertedIntoBitMap)
    {
        Bitmap bitMapImage = BitmapFactory.decodeByteArray(
                byteArrayToBeCOnvertedIntoBitMap, 0,
                byteArrayToBeCOnvertedIntoBitMap.length);
        return bitMapImage;
    }}