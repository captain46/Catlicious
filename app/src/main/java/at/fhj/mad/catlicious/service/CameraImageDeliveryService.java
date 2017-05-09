package at.fhj.mad.catlicious.service;

import android.content.Intent;
import android.graphics.Bitmap;

import at.fhj.catlicious.common.Assert;
import at.fhj.mad.catlicious.data.Image;
import at.fhj.mad.catlicious.data.ImageActivityRequest;
import at.fhj.mad.catlicious.service.exception.RequestNotSatisfiableException;
import at.fhj.mad.catlicious.utils.ImageUtils;
import at.fhj.mad.catlicious.utils.RequestCode;

/**
 * @author bnjm@harmless.ninja - 5/8/17.
 */
public class CameraImageDeliveryService extends ImageActivityRequestHandler {

    private final String DATA = "data";

    public CameraImageDeliveryService(ImageActivityRequestHandler next) {
        super(next);
    }

    @Override
    public Image handle(ImageActivityRequest request) throws RequestNotSatisfiableException {
        if (request.getRequestCode() == RequestCode.CAMERA_REQUEST && request.getResultCode() == -1) {
            sanityCheckIntent(request.getReceivingIntent());
            Bitmap bitmap = (Bitmap) request.getReceivingIntent().getExtras().get(DATA);
            byte[] bytes = ImageUtils.getByteFromBitmap(bitmap);

            return new Image(bytes, bitmap);
        }
        return super.handle(request);

    }

    private void sanityCheckIntent(Intent receivingIntent) {
        Assert.notNull(receivingIntent);
        Assert.notNull(receivingIntent.getExtras());
        Assert.notNull(receivingIntent.getExtras().get(DATA));
    }
}
