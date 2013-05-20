package com.makeapp.android.action;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.text.ClipboardManager;
import com.makeapp.android.util.ServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-10-17
 * Time: 下午3:50
 */
public class AndroidClientAction implements AndroidAction {

    Context context;

    public AndroidClientAction(Context context) {
        this.context = context;
    }

    public boolean startActivity(String action, String uri) {
        Intent intent = new Intent(action, Uri.parse(uri));
        context.startActivity(intent);
        return true;
    }

    public boolean startActivity(String pkg, String name, int flags) {
        Intent intent = new Intent();
        intent.setClassName(pkg, name);
        intent.setFlags(flags);
        context.startActivity(intent);
        return true;
    }

    public boolean startIntent(String action) {
        Intent intent = new Intent(action);
        context.startActivity(intent);
        return true;
    }

    public boolean call(String phoneNum) {
//        final ITelephony phone = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
//        try {
//            phone.call(phoneNum);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
        return false;
    }

    public boolean dial(String phoneNum) {
        return false;
    }

    public boolean sendSms(String sms, String... phoneNum) {
        SmsManager smsMgr = SmsManager.getDefault();
        Intent i = new Intent("cc.androidos.smsdemo.IGNORE_ME");
        PendingIntent dummyEvent = PendingIntent.getBroadcast(context, 0, i, 0);
        for (String pn : phoneNum) {
            smsMgr.sendTextMessage(pn, null, sms, dummyEvent, dummyEvent);
        }
        return true;
    }

    public String getClipboardText() {
        ClipboardManager cm = ServiceUtil.getClipboardManager(context);

        if (cm.getText() != null)
            return cm.getText().toString();
        return null;
    }

    public void setClipboardText(String text) {
        ClipboardManager cm = ServiceUtil.getClipboardManager(context);
        cm.setText(text);
    }

    public void insertContent(String uri, Map<String, Object> map) {
        ContentValues cv = getContentValues(map);
        context.getContentResolver().insert(Uri.parse(uri), cv);
    }

    private ContentValues getContentValues(Map<String, Object> map) {
        ContentValues cv = new ContentValues();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                cv.put(entry.getKey(), (String) entry.getValue());
            } else if (value instanceof Long) {
                cv.put(entry.getKey(), (Long) entry.getValue());
            } else if (value instanceof Integer) {
                cv.put(entry.getKey(), (Integer) entry.getValue());
            } else if (value instanceof Byte) {
                cv.put(entry.getKey(), (Byte) entry.getValue());
            } else if (value instanceof Short) {
                cv.put(entry.getKey(), (Short) entry.getValue());
            } else if (value instanceof Float) {
                cv.put(entry.getKey(), (Float) entry.getValue());
            } else if (value instanceof Double) {
                cv.put(entry.getKey(), (Double) entry.getValue());
            } else if (value instanceof Boolean) {
                cv.put(entry.getKey(), (Boolean) entry.getValue());
            } else if (value instanceof byte[]) {
                cv.put(entry.getKey(), (byte[]) entry.getValue());
            }
        }
        return cv;
    }

    public void updateContent(String uri, Map<String, Object> map, String where, String[] args) {
        ContentValues cv = getContentValues(map);
        context.getContentResolver().update(Uri.parse(uri), cv, where, args);
    }

    public void deleteContent(String uri, String where, String[] args) {
        context.getContentResolver().delete(Uri.parse(uri), where, args);
    }

    public List<Map<String, Object>> queryContent(String uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = context.getContentResolver().query(Uri.parse(uri), projection, selection, selectionArgs, sortOrder);
        List result = new ArrayList();
        if (cursor.getCount() > 0) {
            do {
                Map data = new HashMap();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    data.put(cursor.getColumnName(i), cursor.getString(i));
                }
                result.add(data);
            }
            while (cursor.moveToNext());
        }
        return result;
    }
}
