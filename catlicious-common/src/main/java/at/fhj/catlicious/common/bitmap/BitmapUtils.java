package at.fhj.catlicious.common.bitmap;

import android.graphics.Bitmap;

/**
 * Scales a Bitmap larger than full hd to a fixed size.
 * @author bnjm@harmless.ninja - 5/7/17.
 */
abstract public class BitmapUtils {
    public static Bitmap downscale(Bitmap original) {
        if(original.getWidth() > 0x780 || original.getHeight() > 0x438) {
            return Bitmap.createScaledBitmap(original, 0x3c0, 0x21c, true);
        }
        return original;
    }
}
