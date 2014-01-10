/**
 *  Copyright(c) Shanghai YiJun Network Technologies Inc. All right reserved.
 */
package com.makeapp.android.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:13-8-7 下午3:34 $
 *          $Id$
 */
public abstract class SimpleExpandableListAdapter
    <Group, Item> extends BaseExpandableListAdapter
{
    Context context;

    LayoutInflater inflater = null;

    private List<Group> groups = new ArrayList();

    private Map<Group, List<Item>> items = new HashMap();

    public SimpleExpandableListAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addGroup(Group group)
    {
        groups.add(group);
    }

    public void addGroupNotify(Group group)
    {
        addGroup(group);

        notifyDataSetChanged();
    }

    public void addAllGroupNotify(Collection<Group> group)
    {

        groups.addAll(group);
        notifyDataSetChanged();
    }

    public void addItem(Group group, Item item)
    {
        List<Item> groupItems = items.get(group);
        if (groupItems == null) {
            groupItems = new ArrayList();
            items.put(group, groupItems);
        }
        groupItems.add(item);
    }

    public void addItemNotify(Group group, Item item)
    {
        addItem(group, item);

        notifyDataSetChanged();
    }

    public void clean()
    {
        items.clear();
        groups.clear();
    }

    public void cleanNotify()
    {
        clean();
        notifyDataSetChanged();
    }

    public void cleanItems()
    {
        items.clear();
    }

    public void cleanItemsNotify()
    {
        items.clear();
        notifyDataSetChanged();
    }

    public void cleanAllItemNotify(Group group)
    {
        if (group == null) {
            return;
        }
        items.remove(group);
        notifyDataSetChanged();
    }

    public void addAllItemNotify(Group group, Collection<Item> item)
    {
        if (item == null) {
            return;
        }
        addAllItem(group, item);
        notifyDataSetChanged();
    }

    public void addAllItem(Group group, Collection<Item> item)
    {
        if (item == null) {
            return;
        }
        List<Item> groupItems = items.get(group);
        if (groupItems == null) {
            groupItems = new ArrayList();
            items.put(group, groupItems);
        }
        groupItems.addAll(item);
    }

    public void cleanAddAllItemNotify(Group group, Collection<Item> item)
    {
        if (item == null) {
            return;
        }

        List<Item> groupItems = items.get(group);
        if (groupItems == null) {
            groupItems = new ArrayList();
            items.put(group, groupItems);
        }
        groupItems.clear();
        groupItems.addAll(item);
        notifyDataSetChanged();
    }

    public int getGroupCount()
    {
        return groups.size();
    }

    public int getChildrenCount(int groupPosition)
    {
        Object group = getGroup(groupPosition);
        if (group != null) {
            List groupItems = items.get(group);
            if (groupItems != null && groupItems.size() > 0) {
                return groupItems.size();
            }
        }
        return 0;
    }

    public Object getGroup(int groupPosition)
    {
        return groups.get(groupPosition);
    }

    public Object getChild(int groupPosition, int childPosition)
    {
        Object group = getGroup(groupPosition);
        if (group != null) {
            List groupItems = items.get(group);
            if (groupItems != null && groupItems.size() > 0) {
                return groupItems.get(childPosition);
            }
        }
        return null;
    }

    public long getGroupId(int groupPosition)
    {
        return 0;
    }

    public long getChildId(int groupPosition, int childPosition)
    {
        return 0;
    }

    public boolean hasStableIds()
    {
        return false;
    }

    public abstract View makeGroupView(LayoutInflater inflater, Group groupData, int groupPosition);


    public abstract void fillGroupView(ViewGroup parent, View convertView, Group groupData, int groupPosition, boolean isExpanded);

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        Group group = (Group) getGroup(groupPosition);

//        if (convertView == null) {
        convertView = makeGroupView(inflater, group, groupPosition);
//        }
        fillGroupView(parent, convertView, group, groupPosition, isExpanded);
        return convertView;
    }

    public abstract View makeItemView(LayoutInflater inflater, Group groupData, int groupPosition, Item itemData, int itemPosition);


    public abstract void fillItemView(ViewGroup parent, View convertView, Group groupData, int groupPosition, Item itemData, int itemPosition, boolean isLastChild);

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        Group group = (Group) getGroup(groupPosition);
        Item item = (Item) getChild(groupPosition, childPosition);

//        if (convertView == null) {
        convertView = makeItemView(inflater, group, groupPosition, item, childPosition);
//        }

        fillItemView(parent, convertView, group, groupPosition, item, childPosition, isLastChild);

        return convertView;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}
