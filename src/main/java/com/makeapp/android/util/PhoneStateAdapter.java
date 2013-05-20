/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.telephony.CellLocation;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-12-6 ÏÂÎç7:18 $
 *          $Id$
 */
public class PhoneStateAdapter
{
    /**
     * Callback invoked when device service state changes.
     *
     * @see android.telephony.ServiceState#STATE_EMERGENCY_ONLY
     * @see android.telephony.ServiceState#STATE_IN_SERVICE
     * @see android.telephony.ServiceState#STATE_OUT_OF_SERVICE
     * @see android.telephony.ServiceState#STATE_POWER_OFF
     */
    public void onServiceStateChanged(ServiceState serviceState){

    }

    /**
     * Callback invoked when network signal strength changes.
     *
     * @see ServiceState#STATE_EMERGENCY_ONLY
     * @see ServiceState#STATE_IN_SERVICE
     * @see ServiceState#STATE_OUT_OF_SERVICE
     * @see ServiceState#STATE_POWER_OFF
     * @deprecated Use {@link #onSignalStrengthsChanged(android.telephony.SignalStrength)}
     */
    @Deprecated
    public void onSignalStrengthChanged(int asu){

    }

    /**
     * Callback invoked when the message-waiting indicator changes.
     */
    public void onMessageWaitingIndicatorChanged(boolean mwi) {

    }

    /**
     * Callback invoked when the call-forwarding indicator changes.
     */
    public void onCallForwardingIndicatorChanged(boolean cfi){

    }

    /**
     * Callback invoked when device cell location changes.
     */
    public void onCellLocationChanged(CellLocation location){

    }

    /**
     * Callback invoked when device call state changes.
     *
     * @see android.telephony.TelephonyManager#CALL_STATE_IDLE
     * @see android.telephony.TelephonyManager#CALL_STATE_RINGING
     * @see android.telephony.TelephonyManager#CALL_STATE_OFFHOOK
     */
    public void onCallStateChanged(int state, String incomingNumber){

    }

    /**
     * Callback invoked when connection state changes.
     *
     * @see android.telephony.TelephonyManager#DATA_DISCONNECTED
     * @see android.telephony.TelephonyManager#DATA_CONNECTING
     * @see android.telephony.TelephonyManager#DATA_CONNECTED
     * @see android.telephony.TelephonyManager#DATA_SUSPENDED
     */
    public void onDataConnectionStateChanged(int state){

    }

    /**
     * same as above, but with the network type.  Both called.
     */
    public void onDataConnectionStateChanged(int state, int networkType){

    }
    /**
     * Callback invoked when data activity state changes.
     *
     * @see android.telephony.TelephonyManager#DATA_ACTIVITY_NONE
     * @see android.telephony.TelephonyManager#DATA_ACTIVITY_IN
     * @see android.telephony.TelephonyManager#DATA_ACTIVITY_OUT
     * @see android.telephony.TelephonyManager#DATA_ACTIVITY_INOUT
     * @see android.telephony.TelephonyManager#DATA_ACTIVITY_DORMANT
     */
    public void onDataActivity(int direction){

    }

    /**
     * Callback invoked when network signal strengths changes.
     *
     * @see ServiceState#STATE_EMERGENCY_ONLY
     * @see ServiceState#STATE_IN_SERVICE
     * @see ServiceState#STATE_OUT_OF_SERVICE
     * @see ServiceState#STATE_POWER_OFF
     */
    public void onSignalStrengthsChanged(SignalStrength signalStrength){

    }

}
