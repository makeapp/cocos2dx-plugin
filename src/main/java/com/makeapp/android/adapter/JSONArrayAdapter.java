/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-10-30 ÏÂÎç2:22 $
 *          $Id$
 */
public abstract class JSONArrayAdapter extends ArrayListAdapter<JSONObject>
{
    public JSONArrayAdapter(Context context, int textViewResourceId)
    {
        super(context, textViewResourceId);
    }

//    public JSONArrayAdapter(Context context, int resource, int textViewResourceId)
//    {
//        super(context, resource, textViewResourceId);
//    }

    public JSONArrayAdapter(Context context, int textViewResourceId, JSONObject[] objects)
    {
        super(context, textViewResourceId, objects);
    }

//    public JSONArrayAdapter(Context context, int resource, int textViewResourceId, JSONObject[] objects)
//    {
//        super(context, resource, textViewResourceId, objects);
//    }

    public JSONArrayAdapter(Context context, int textViewResourceId, JSONArray objects)
    {
        super(context, textViewResourceId, toList(objects));
    }

//    public JSONArrayAdapter(Context context, int resource, int textViewResourceId, JSONArray objects)
//    {
//        super(context, resource, textViewResourceId, toList(objects));
//    }

    private static List<JSONObject> toList(JSONArray array)
    {
        List list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            try {
                list.add(array.getJSONObject(i));
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
