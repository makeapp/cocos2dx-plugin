/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.makeapp.javase.util.HashMapParameters;
import com.makeapp.javase.util.Parameters;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;


/**
 * @author <a href="mailto:shigang-pc@shqianzhi.com">shigang-pc</a>
 * @version $Date:11-7-7 ����7:10 $
 *          $Id$
 */
public class ContactUtil
{
    /**
     * ��ȡ��ϵ��
     *
     * @param activity
     * @return �û��� �͵绰  map ����
     */
    public static Map<String, String> getContacts(Activity activity)
    {
        Map<String, String> map = new HashMap<String, String>();
        Cursor cursor = activity.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
        try {
            while (cursor.moveToNext()) {
                // ��ȡ�ֶε�ֵ
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String phoneNumber = "";
                String phoneType = "";

                if (hasPhone.compareTo("1") == 0) {
                    Cursor phones = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNumber = phoneNumber.replace("-", "");
//                        phoneType = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                    }
                    phones.close();
                }

                if (!"".equals(phoneNumber)) {
                    map.put(name, phoneNumber);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close(); //����ر�
        }
        return map;
    }
}
