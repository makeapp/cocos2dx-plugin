/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:11-3-14 ÏÂÎç2:29 $
 *          $Id$
 */
public class PersistenceUnitEntity
{
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    private List<EntityClass> entityClasses = new ArrayList<EntityClass>();

    public void addClass(String clazzName)
    {
        try {
            EntityClass clazz = new EntityClass();
            clazz.setClazz(Class.forName(clazzName));
            clazz.setClassName(clazzName);
            entityClasses.add(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public EntityClass getEntityClass(String className)
    {
        for (EntityClass ec : entityClasses) {
            if (ec.getClassName().equals(className)) {
                return ec;
            }
        }
        return null;
    }

    public List<EntityClass> getEntityClasses()
    {
        return entityClasses;
    }

    private Map<String, String> properties = new HashMap();

    public void setProperty(String key, String value)
    {
        properties.put(key, value);
    }

    public String getProperty(String key)
    {
        return properties.get(key);
    }
}
