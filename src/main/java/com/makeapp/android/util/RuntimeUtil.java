package com.makeapp.android.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-6-28
 * Time: ионГ11:35
 */
public class RuntimeUtil
{
    public static void exec(String cmdLine)
    {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("su");
            OutputStream out = p.getOutputStream();
            out.write(cmdLine.getBytes());
            out.write("\n".getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
