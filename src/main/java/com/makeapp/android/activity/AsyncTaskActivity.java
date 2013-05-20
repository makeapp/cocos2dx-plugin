package com.makeapp.android.activity;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import com.makeapp.android.task.AsyncTask;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-10-19
 */
public class AsyncTaskActivity<P, R> extends Activity
{

    AsyncTaskExe taskExe;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        taskExe = new AsyncTaskExe();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        taskExe.cancelSchedule();
        if (!taskExe.isCancelled()) {
            taskExe.cancel(true);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        taskExe.cancelSchedule();
        if (!taskExe.isCancelled()) {
            taskExe.cancel(true);
        }
    }


    protected void onTaskPreExecute()
    {
    }


    protected R doTaskInBackground(P... p)
    {
        return null;
    }

    protected void onTaskPostExecute(R o)
    {

    }

    protected void onTaskTimeout()
    {

    }

    protected void onTaskProgressUpdate(Integer... values)
    {

    }

    protected void onTaskCancelled()
    {
    }

    public void publishTaskProgress(Integer... values)
    {
        taskExe.publishTaskProgress(values);
    }

    public void executeTask(P... p)
    {
        executeTask(-1, false, p);
    }

    public void executeTask(long timeout, boolean mayInterruptIfRunning, P... p)
    {
        if (taskExe.getStatus() == AsyncTask.Status.FINISHED) {
            taskExe = new AsyncTaskExe();
        }
        else if (taskExe.getStatus() == AsyncTask.Status.RUNNING) {
            return;
        }
        if (timeout > 0) {
            taskExe.execute(timeout, mayInterruptIfRunning, p);
        }
        else {
            taskExe.execute(p);
        }
    }

    public void scheduleTask(long delay, P... p)
    {
        scheduleTask(delay, -1, false, p);
    }

    public void scheduleTask(Date time, P... p)
    {
        scheduleTask(time, -1, false, p);
    }

    public void scheduleTask(long delay, long timeout, boolean mayInterruptIfRunning, P... p)
    {
        scheduleTask(delay, -1, timeout, mayInterruptIfRunning, p);
    }

    public void scheduleTask(long delay, long period, long timeout, boolean mayInterruptIfRunning, P... p)
    {
        taskExe.schedule(delay, period, timeout, mayInterruptIfRunning, p);
    }

    public void scheduleTask(Date time, long timeout, boolean mayInterruptIfRunning, P... p)
    {
        if (timeout > 0) {
            taskExe.schedule(time, timeout, mayInterruptIfRunning, p);
        }
        else {
            taskExe.schedule(time, p);
        }
    }

    public void cancelTask()
    {
        if (!taskExe.isCancelled()) {
            taskExe.cancel(true);
        }
    }

    public void cancelSchedule()
    {
        if (taskExe != null && !taskExe.isCancelled()) {
            taskExe.cancelSchedule();
        }
    }

    public class AsyncTaskExe extends AsyncTask<P, Integer, R>
    {

        public void publishTaskProgress(Integer... values)
        {
            this.publishProgress(values);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            AsyncTaskActivity.this.onTaskPreExecute();
        }

        @Override
        protected R doInBackground(P... params)
        {
            return AsyncTaskActivity.this.doTaskInBackground(params);
        }

        @Override
        protected void onPostExecute(R o)
        {
            super.onPostExecute(o);
            AsyncTaskActivity.this.onTaskPostExecute(o);
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            AsyncTaskActivity.this.onTaskProgressUpdate(values);
        }

        @Override
        protected void onCancelled()
        {
            super.onCancelled();
            AsyncTaskActivity.this.onTaskCancelled();
        }

        @Override
        protected void onTimeout()
        {
            super.onTimeout();
            AsyncTaskActivity.this.onTaskTimeout();
        }
    }
}
