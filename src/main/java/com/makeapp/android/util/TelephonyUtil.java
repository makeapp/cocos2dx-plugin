/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import com.android.internal.telephony.ITelephony;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-11-5 下午5:37 $
 *          $Id$
 */
public class TelephonyUtil
{
    /**
     * 获取 手机 唯一标识
     *
     * @param context
     */
    public static String getImieStatus(Context context)
    {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String getDeviceId(Context context)
    {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 手机制式和网络类型 GSM/EDGE/CDMA/WCDMA 判断sim卡类型 sim/uim/usim
     *
     * @param context
     */
    public static int getNetworkType(Context context)
    {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        //获得手机SIMType
        return tm.getNetworkType();
    }

    public static int getPhoneType(Context context)
    {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        //获得手机SIMType
        return tm.getPhoneType();
    }

    public static synchronized boolean dial(String s)
    {
        ITelephony iTelephony = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
        try {
            iTelephony.dial(s);
        }
        catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static synchronized boolean call(String s)
    {
        ITelephony iTelephony = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
        try {
            iTelephony.call(s);
        }
        catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static synchronized boolean endCall()
    {
        ITelephony iTelephony = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
        try {
            iTelephony.endCall();
        }
        catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static synchronized void postListen(Context context, final PhoneStateAdapter listener){
        postListen(context, listener,PhoneStateListener.LISTEN_CALL_STATE);
    }

    public static synchronized void postListen(Context context, final PhoneStateAdapter listener,final int status)
    {
        final TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        HandlerUtil.postMain(new Runnable()
        {
            public void run()
            {
                telMgr.listen(new PhoneStateListener()
                {
                    public void onCallStateChanged(int state, String incomingNumber)
                    {
                        super.onCallStateChanged(state, incomingNumber);
                        listener.onCallStateChanged(state, incomingNumber);
                    }

                    public void onDataActivity(int direction)
                    {
                        super.onDataActivity(direction);
                        listener.onDataActivity(direction);
                    }

                    public void onDataConnectionStateChanged(int state, int networkType)
                    {
                        super.onDataConnectionStateChanged(state, networkType);
                        listener.onDataConnectionStateChanged(state,networkType);
                    }

                    public void onCellLocationChanged(CellLocation location)
                    {
                        super.onCellLocationChanged(location);
                        listener.onCellLocationChanged(location);
                    }

                    public void onMessageWaitingIndicatorChanged(boolean mwi)
                    {
                        super.onMessageWaitingIndicatorChanged(mwi);
                        listener.onMessageWaitingIndicatorChanged(mwi);
                    }

                    public void onCallForwardingIndicatorChanged(boolean cfi)
                    {
                        super.onCallForwardingIndicatorChanged(cfi);
                        listener.onCallForwardingIndicatorChanged(cfi);
                    }

                    public void onDataConnectionStateChanged(int state)
                    {
                        super.onDataConnectionStateChanged(state);
                        listener.onDataConnectionStateChanged(state);
                    }

                    public void onServiceStateChanged(ServiceState serviceState)
                    {
                        super.onServiceStateChanged(serviceState);
                        listener.onServiceStateChanged(serviceState);
                    }

                    public void onSignalStrengthChanged(int asu)
                    {
                        super.onSignalStrengthChanged(asu);
                        listener.onSignalStrengthChanged(asu);
                    }
                }, status);
            }
        });
    }

    public static synchronized void answerRingingCall(Context context)
    {

        //据说该方法只能用于Android2.3及2.3以上的版本上，但本人在2.2上测试可以使用
        try {
            Intent localIntent1 = new Intent(Intent.ACTION_HEADSET_PLUG);
            localIntent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            localIntent1.putExtra("state", 1);
            localIntent1.putExtra("microphone", 1);
            localIntent1.putExtra("name", "Headset");
            context.sendOrderedBroadcast(localIntent1, "android.permission.CALL_PRIVILEGED");
            Intent localIntent2 = new Intent(Intent.ACTION_MEDIA_BUTTON);
            KeyEvent localKeyEvent1 = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK);
            localIntent2.putExtra("android.intent.extra.KEY_EVENT", localKeyEvent1);
            context.sendOrderedBroadcast(localIntent2, "android.permission.CALL_PRIVILEGED");
            Intent localIntent3 = new Intent(Intent.ACTION_MEDIA_BUTTON);
            KeyEvent localKeyEvent2 = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
            localIntent3.putExtra("android.intent.extra.KEY_EVENT", localKeyEvent2);
            context.sendOrderedBroadcast(localIntent3, "android.permission.CALL_PRIVILEGED");
            Intent localIntent4 = new Intent(Intent.ACTION_HEADSET_PLUG);
            localIntent4.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            localIntent4.putExtra("state", 0);
            localIntent4.putExtra("microphone", 1);
            localIntent4.putExtra("name", "Headset");
            context.sendOrderedBroadcast(localIntent4, "android.permission.CALL_PRIVILEGED");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
