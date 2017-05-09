package at.fhj.mad.catlicious.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;

import at.fhj.catlicious.common.Assert;
import at.fhj.catlicious.common.bitmap.BitmapUtils;
import at.fhj.mad.catlicious.data.Image;
import at.fhj.mad.catlicious.data.ImageActivityRequest;
import at.fhj.mad.catlicious.service.exception.ContentProviderFileNotFoundException;
import at.fhj.mad.catlicious.service.exception.RequestNotSatisfiableException;
import at.fhj.mad.catlicious.utils.ImageUtils;

import static android.app.Activity.RESULT_OK;
import static at.fhj.mad.catlicious.utils.RequestCode.GALLERY_REQUEST;

/**
 * RequestHandler of the {@link ImageActivityRequestHandler} chain of responsibility.
 * This handlers purpose is to answer requests to the Android Gallery. This allows a client
 * to choose an image from the gallery and set it as an image eg. in a ImageView.
 *
 * @author bnjm@harmless.ninja - 5/8/17.
 */
public class ContentProviderImageDeliveryService extends ImageActivityRequestHandler {

    private Context context;

    public ContentProviderImageDeliveryService(ImageActivityRequestHandler next, Context context) {
        super(next);
        this.context = context;
    }


    @Override
    public Image handle(ImageActivityRequest request) throws RequestNotSatisfiableException {
        if (request.getRequestCode() == GALLERY_REQUEST && request.getResultCode() == RESULT_OK) {
            sanityCheckIntent(request.getReceivingIntent());
            Bitmap bitmap = resolveFromContentResolver(context, request.getReceivingIntent().getData(), true);
            byte[] bytes = ImageUtils.getByteFromBitmap(bitmap);

            return new Image(bytes, bitmap);
        }
        return super.handle(request);
    }

    private void sanityCheckIntent(Intent receivingIntent) {
        Assert.notNull(receivingIntent);
        Assert.notNull(receivingIntent.getData());
    }

    private Bitmap resolveFromContentResolver(Context context, Uri uri, boolean downscale) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

            if (downscale) {
                return BitmapUtils.downscale(bitmap);
            }
            return bitmap;
        } catch (IOException e) {
            Log.d("GALLERY", "Could not open image from gallery", e);
            throw new ContentProviderFileNotFoundException("ContentResolver could not resolve: " + uri.getPath());
        }
    }
}
