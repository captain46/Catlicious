package at.fhj.mad.catlicious.data;

import android.graphics.Bitmap;

/**
 * Tuple containing a byte and bitmap representation of an image
 *
 * @author bnjm@harmless.ninja - 5/8/17.
 */
public class Image {
    private final byte[] bytes;
    private final Bitmap bitmap;


    public Image(byte[] bytes, Bitmap bitmap) {
        this.bytes = bytes;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
