package at.fhj.mad.catlicious.service;

import android.content.Context;
import android.content.ContextWrapper;
import at.fhj.mad.catlicious.data.Image;
import at.fhj.mad.catlicious.data.ImageActivityRequest;
import at.fhj.mad.catlicious.service.exception.RequestNotSatisfiableException;

/**
 * @author bnjm@harmless.ninja - 5/8/17.
 */
public class ImageActivityRequestChainInvoker extends ContextWrapper {
    private ImageActivityRequestHandler chain;

    public ImageActivityRequestChainInvoker(Context base) {
        super(base);
        this.chain = buildChain();
    }

    private ImageActivityRequestHandler buildChain() {
        return new CameraImageDeliveryService(new ContentProviderImageDeliveryService(null, this));
    }

    public Image deliver(ImageActivityRequest request) throws RequestNotSatisfiableException {
        return this.chain.handle(request);
    }
}
