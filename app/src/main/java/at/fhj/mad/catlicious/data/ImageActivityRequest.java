package at.fhj.mad.catlicious.data;

import android.content.Intent;

/**
 * @author bnjm@harmless.ninja - 5/8/17.
 */
public class ImageActivityRequest {
    private final int requestCode;
    private final int resultCode;
    private final Intent receivingIntent;

    public ImageActivityRequest(int requestCode, int resultCode, Intent receivingIntent) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.receivingIntent = receivingIntent;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public Intent getReceivingIntent() {
        return receivingIntent;
    }
}
