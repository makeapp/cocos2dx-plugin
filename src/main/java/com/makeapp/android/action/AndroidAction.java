package com.makeapp.android.action;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-10-17
 * Time: 下午2:15
 */
public interface AndroidAction {

    //public boolean download(String url);

    public boolean startActivity(String action, String uri);

    public boolean startActivity(String pkg, String name, int flags);

    public boolean startIntent(String action);

    public boolean call(String phoneNum);

    public boolean dial(String phoneNum);

    public boolean sendSms(String sms, String... phoneNum);

    public String getClipboardText();

    public void setClipboardText(String text);

    public void insertContent(String uri, Map<String, Object> map);

    public void updateContent(String uri, Map<String, Object> map, String where, String[] args);

    public void deleteContent(String uri, String where, String[] args);

    public List<Map<String, Object>> queryContent(String uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);
}
