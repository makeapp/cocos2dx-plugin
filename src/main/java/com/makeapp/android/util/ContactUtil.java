/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.makeapp.javase.lang.StringUtil;


/**
 * @author <a href="mailto:shigang-pc@shqianzhi.com">shigang-pc</a>
 * @version $Date:11-7-7 ����7:10 $
 *          $Id$
 */
public class ContactUtil
{
    private static final String[] PHONES_PROJECTION = new String[]{
               ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
               ContactsContract.CommonDataKinds.Phone.NUMBER,
               ContactsContract.CommonDataKinds.Photo.PHOTO_ID,
               ContactsContract.CommonDataKinds.Phone.CONTACT_ID};


    /**
     * ��ȡ��ϵ��
     *
     * @param activity
     *
     * @return �û��� �͵绰  map ����
     */
    public static Map<String, String> getContacts(Activity activity)
    {
        Map<String, String> map = new HashMap<String, String>();
        Cursor phoneCursor = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, "data2=2", null, null);
        List results = new ArrayList();
        List allNumbers = new ArrayList();
        while (phoneCursor != null && phoneCursor.moveToNext()) {
            //得到手机号码
            String phoneNumber = phoneCursor.getString(1);
            //当手机号码为空的或者为空字段 跳过当前循环
            if (StringUtil.isInvalid(phoneNumber))
                continue;
            if (phoneNumber.startsWith("+86")) {
                phoneNumber = phoneNumber.substring(3);
            }
            //得到联系人名称
            String contactName = phoneCursor.getString(0);
            //得到联系人ID
            Long contactId = phoneCursor.getLong(3);
            //得到联系人头像ID
            Long photoId = phoneCursor.getLong(2);

            Map value = new HashMap();
            value.put("name", contactName);
            value.put("number", phoneNumber);

            results.add(value);
            allNumbers.add(phoneNumber);

            //得到联系人头像Bitamp
            //                    Bitmap contactPhoto = null;
            //                    //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
            //                    if (photoid > 0) {
            //                        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
            //                        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(), uri);
            //                        contactPhoto = BitmapFactory.decodeStream(input);
            //                    }
            //                    else {
            //                        contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.contact_photo);
            //                    }
        }
        phoneCursor.close();
        return map;
    }
}
