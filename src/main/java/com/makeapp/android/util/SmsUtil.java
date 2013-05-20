/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.gsm.SmsManager;
import android.util.Log;

/**
 * @author <a href="mailto:shigang@shqianzhi.com">shigang</a>
 * @version $Date:11-4-13 ����5:49 $
 *          $Id$
 */
public class SmsUtil
{
    public static void sendSms(Context context, String to, String content)
    {
        Log.i("SmsUtil", "Send sms to " + to + " " + content);
        SmsManager smsMgr = SmsManager.getDefault();
        Intent i = new Intent("cc.androidos.smsdemo.IGNORE_ME");
        PendingIntent dummyEvent = PendingIntent.getBroadcast(context, 0, i, 0);
        try {
            ArrayList<String> contents = smsMgr.divideMessage(content);
            for (String msg : contents) {
                smsMgr.sendTextMessage(to, null, msg, dummyEvent, dummyEvent);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
