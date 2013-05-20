/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.task;

import java.util.HashMap;

import com.makeapp.javase.util.HashMapParameters;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-10-31 обнГ2:04 $
 *          $Id$
 */
public class AsyncTaskParameters
    extends HashMapParameters
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
}
