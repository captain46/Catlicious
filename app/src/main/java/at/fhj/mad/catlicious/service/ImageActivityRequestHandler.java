package at.fhj.mad.catlicious.service;

import at.fhj.mad.catlicious.data.Image;
import at.fhj.mad.catlicious.data.ImageActivityRequest;
import at.fhj.mad.catlicious.service.exception.RequestNotSatisfiableException;

/**
 * Abstract representation of an handler in the chain of responsibility. Further handlers
 * must extend this class and must be added to {@link ImageActivityRequestChainInvoker#buildChain()}.
 *
 * @author bnjm@harmless.ninja - 5/8/17.
 */
public abstract class ImageActivityRequestHandler {

    private ImageActivityRequestHandler next;

    ImageActivityRequestHandler(ImageActivityRequestHandler next) {
        this.next = next;
    }

    public Image handle(ImageActivityRequest request) throws RequestNotSatisfiableException {
        if (next != null) {
            return next.handle(request);
        }
        throw new RequestNotSatisfiableException("Could not satisfy request. (Aborted?)");
    }
}
