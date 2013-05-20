/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.task;

import java.util.HashMap;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-10-31 ÏÂÎç2:05 $
 *          $Id$
 */
public class AsyncTaskResponse
    extends AsyncTaskParameters
{
    int statusCode = 0;
    private Exception exception;
    private String message;

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public Exception getException()
    {
        return exception;
    }

    public void setException(Exception exception)
    {
        this.exception = exception;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public boolean isOK()
    {
        return statusCode == 0 && exception == null && message == null;
    }
}
