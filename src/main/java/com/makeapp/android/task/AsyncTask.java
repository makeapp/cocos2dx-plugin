/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.makeapp.android.task;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import android.os.Handler;
import android.os.Message;
import android.os.Process;

/**
 * <p>AsyncTask enables proper and easy use of the UI thread. This class allows to
 * perform background operations and publish results on the UI thread without
 * having to manipulate threads and/or handlers.</p>
 * <p/>
 * <p>An asynchronous task is defined by a computation that runs on a background thread and
 * whose result is published on the UI thread. An asynchronous task is defined by 3 generic
 * types, called <code>Params</code>, <code>Progress</code> and <code>Result</code>,
 * and 4 steps, called <code>begin</code>, <code>doInBackground</code>,
 * <code>processProgress</code> and <code>end</code>.</p>
 * <p/>
 * <h2>Usage</h2>
 * <p>AsyncTask must be subclassed to be used. The subclass will override at least
 * one method ({@link #doInBackground}), and most often will override a
 * second one ({@link #onPostExecute}.)</p>
 * <p/>
 * <p>Here is an example of subclassing:</p>
 * <pre class="prettyprint">
 * private class DownloadFilesTask extends AsyncTask&lt;URL, Integer, Long&gt; {
 * protected Long doInBackground(URL... urls) {
 * int count = urls.length;
 * long totalSize = 0;
 * for (int i = 0; i < count; i++) {
 * totalSize += Downloader.downloadFile(urls[i]);
 * publishProgress((int) ((i / (float) count) * 100));
 * }
 * return totalSize;
 * }
 * <p/>
 * protected void onProgressUpdate(Integer... progress) {
 * setProgressPercent(progress[0]);
 * }
 * <p/>
 * protected void onPostExecute(Long result) {
 * showDialog("Downloaded " + result + " bytes");
 * }
 * }
 * </pre>
 * <p/>
 * <p>Once created, a task is executed very simply:</p>
 * <pre class="prettyprint">
 * new DownloadFilesTask().execute(url1, url2, url3);
 * </pre>
 * <p/>
 * <h2>AsyncTask's generic types</h2>
 * <p>The three types used by an asynchronous task are the following:</p>
 * <ol>
 * <li><code>Params</code>, the type of the parameters sent to the task upon
 * execution.</li>
 * <li><code>Progress</code>, the type of the progress units published during
 * the background computation.</li>
 * <li><code>Result</code>, the type of the result of the background
 * computation.</li>
 * </ol>
 * <p>Not all types are always used by an asynchronous task. To mark a type as unused,
 * simply use the type {@link Void}:</p>
 * <pre>
 * private class MyTask extends AsyncTask&lt;Void, Void, Void&gt; { ... }
 * </pre>
 * <p/>
 * <h2>The 4 steps</h2>
 * <p>When an asynchronous task is executed, the task goes through 4 steps:</p>
 * <ol>
 * <li>{@link #onPreExecute()}, invoked on the UI thread immediately after the task
 * is executed. This step is normally used to setup the task, for instance by
 * showing a progress bar in the user interface.</li>
 * <li>{@link #doInBackground}, invoked on the background thread
 * immediately after {@link #onPreExecute()} finishes executing. This step is used
 * to perform background computation that can take a long time. The parameters
 * of the asynchronous task are passed to this step. The result of the computation must
 * be returned by this step and will be passed back to the last step. This step
 * can also use {@link #publishProgress} to publish one or more units
 * of progress. These values are published on the UI thread, in the
 * {@link #onProgressUpdate} step.</li>
 * <li>{@link #onProgressUpdate}, invoked on the UI thread after a
 * call to {@link #publishProgress}. The timing of the execution is
 * undefined. This method is used to display any form of progress in the user
 * interface while the background computation is still executing. For instance,
 * it can be used to animate a progress bar or show logs in a text field.</li>
 * <li>{@link #onPostExecute}, invoked on the UI thread after the background
 * computation finishes. The result of the background computation is passed to
 * this step as a parameter.</li>
 * </ol>
 * <p/>
 * <h2>Threading rules</h2>
 * <p>There are a few threading rules that must be followed for this class to
 * work properly:</p>
 * <ul>
 * <li>The task instance must be created on the UI thread.</li>
 * <li>{@link #execute} must be invoked on the UI thread.</li>
 * <li>Do not call {@link #onPreExecute()}, {@link #onPostExecute},
 * {@link #doInBackground}, {@link #onProgressUpdate} manually.</li>
 * <li>The task can be executed only once (an exception will be thrown if
 * a second execution is attempted.)</li>
 * </ul>
 */
public abstract class AsyncTask<Params, Progress, Result>
{
    private static final String LOG_TAG = "AsyncTask";
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int KEEP_ALIVE = 1;
    private static final BlockingQueue<Runnable> sWorkQueue =
        new LinkedBlockingQueue<Runnable>(10);
    private static final ThreadFactory sThreadFactory = new ThreadFactory()
    {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r)
        {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };
    private static final ThreadPoolExecutor sExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
        MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sWorkQueue, sThreadFactory);
    private static final int MESSAGE_POST_RESULT = 0x1;
    private static final int MESSAGE_POST_PROGRESS = 0x2;
    private static final int MESSAGE_POST_CANCEL = 0x3;
    private static final int MESSAGE_POST_TIMEOUT = 0x4;
    private static final int MESSAGE_POST_EXCEPTION = 0x5;
    private static final InternalHandler sHandler = new InternalHandler();
    private WorkerRunnable<Params, Result> mWorker;
    private FutureTask<Result> mFuture;
    Timer timer;
    private FutureTask<Result> timeoutFuture;
    private volatile Status mStatus = Status.PENDING;

    /**
     * Creates a new asynchronous task. This constructor must be invoked on the UI thread.
     */
    public AsyncTask()
    {
        addWorker();
    }

    public void addWorker()
    {
        mWorker = new WorkerRunnable<Params, Result>()
        {
            public Result call() throws Exception
            {
                android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                return doInBackground(mParams);
            }
        };

        mFuture = new FutureTask<Result>(mWorker)
        {
            @Override
            protected void done()
            {
                Message message;
                Result result = null;

                try {
                    result = get();
                }
                catch (InterruptedException e) {
                    android.util.Log.w(LOG_TAG, e);
                }
                catch (ExecutionException e) {
                    message = sHandler.obtainMessage(MESSAGE_POST_EXCEPTION, new AsyncTaskResult<Result>(AsyncTask.this, (Result[]) null, e.getCause()));
                    message.sendToTarget();
                    return;
                }
                catch (CancellationException e) {
                    message = sHandler.obtainMessage(MESSAGE_POST_CANCEL,
                        new AsyncTaskResult<Result>(AsyncTask.this, (Result[]) null));
                    message.sendToTarget();
                    return;
                }
                catch (Throwable t) {
                    message = sHandler.obtainMessage(MESSAGE_POST_EXCEPTION, new AsyncTaskResult<Result>(AsyncTask.this, (Result[]) null, t));
                    message.sendToTarget();
                    return;
                }
                if (timeoutFuture != null) {
                    timeoutFuture.cancel(true);
                }
                message = sHandler.obtainMessage(MESSAGE_POST_RESULT,
                    new AsyncTaskResult<Result>(AsyncTask.this, result));
                message.sendToTarget();
            }
        };
    }

    /**
     * Returns the current status of this task.
     *
     * @return The current status.
     */
    public final Status getStatus()
    {
        return mStatus;
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     *
     * @return A result, defined by the subclass of this task.
     *
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    protected abstract Result doInBackground(Params... params);

    /**
     * Runs on the UI thread before {@link #doInBackground}.
     *
     * @see #onPostExecute
     * @see #doInBackground
     */
    protected void onPreExecute()
    {
    }

    /**
     * Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}
     * or null if the task was cancelled or an exception occured.
     *
     * @param result The result of the operation computed by {@link #doInBackground}.
     *
     * @see #onPreExecute
     * @see #doInBackground
     */
    @SuppressWarnings({"UnusedDeclaration"})
    protected void onPostExecute(Result result)
    {
    }

    /**
     * Runs on the UI thread after {@link #publishProgress} is invoked.
     * The specified values are the values passed to {@link #publishProgress}.
     *
     * @param values The values indicating progress.
     *
     * @see #publishProgress
     * @see #doInBackground
     */
    @SuppressWarnings({"UnusedDeclaration"})
    protected void onProgressUpdate(Progress... values)
    {
    }

    /**
     * Runs on the UI thread after {@link #cancel(boolean)} is invoked.
     *
     * @see #cancel(boolean)
     * @see #isCancelled()
     */
    protected void onCancelled()
    {
    }

    protected void onTimeout()
    {

    }

    protected void onException(Throwable throwable)
    {
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }


    /**
     * Returns <tt>true</tt> if this task was cancelled before it completed
     * normally.
     *
     * @return <tt>true</tt> if task was cancelled before it completed
     *
     * @see #cancel(boolean)
     */
    public final boolean isCancelled()
    {
        return mFuture.isCancelled();
    }

    /**
     * Attempts to cancel execution of this task.  This attempt will
     * fail if the task has already completed, already been cancelled,
     * or could not be cancelled for some other reason. If successful,
     * and this task has not started when <tt>cancel</tt> is called,
     * this task should never run.  If the task has already started,
     * then the <tt>mayInterruptIfRunning</tt> parameter determines
     * whether the thread executing this task should be interrupted in
     * an attempt to stop the task.
     *
     * @param mayInterruptIfRunning <tt>true</tt> if the thread executing this
     *                              task should be interrupted; otherwise, in-progress tasks are allowed
     *                              to complete.
     *
     * @return <tt>false</tt> if the task could not be cancelled,
     *         typically because it has already completed normally;
     *         <tt>true</tt> otherwise
     *
     * @see #isCancelled()
     * @see #onCancelled()
     */
    public final boolean cancel(boolean mayInterruptIfRunning)
    {
        return mFuture.cancel(mayInterruptIfRunning);
    }

    /**
     * Waits if necessary for the computation to complete, and then
     * retrieves its result.
     *
     * @return The computed result.
     *
     * @throws java.util.concurrent.CancellationException If the computation was cancelled.
     * @throws java.util.concurrent.ExecutionException    If the computation threw an exception.
     * @throws InterruptedException                       If the current thread was interrupted
     *                                                    while waiting.
     */
    public final Result get() throws InterruptedException, ExecutionException
    {
        return mFuture.get();
    }

    /**
     * Waits if necessary for at most the given time for the computation
     * to complete, and then retrieves its result.
     *
     * @param timeout Time to wait before cancelling the operation.
     * @param unit    The time unit for the timeout.
     *
     * @return The computed result.
     *
     * @throws java.util.concurrent.CancellationException If the computation was cancelled.
     * @throws java.util.concurrent.ExecutionException    If the computation threw an exception.
     * @throws InterruptedException                       If the current thread was interrupted
     *                                                    while waiting.
     * @throws java.util.concurrent.TimeoutException      If the wait timed out.
     */
    public final Result get(long timeout, TimeUnit unit)
        throws InterruptedException,
        ExecutionException, TimeoutException
    {
        return mFuture.get(timeout, unit);
    }

    /**
     * Executes the task with the specified parameters. The task returns
     * itself (this) so that the caller can keep a reference to it.
     * <p/>
     * This method must be invoked on the UI thread.
     *
     * @param params The parameters of the task.
     *
     * @return This instance of AsyncTask.
     *
     * @throws IllegalStateException If {@link #getStatus()} returns either
     *                               {@link com.makeapp.android.task.AsyncTask.Status#RUNNING} or {@link com.makeapp.android.task.AsyncTask.Status#FINISHED}.
     */
    public final AsyncTask<Params, Progress, Result> execute(Params... params)
    {
        return execute(-1, false, params);
    }

    public void schedule(long delay, Params... params)
    {
        schedule(delay, -1, false, params);
    }

    public void schedule(Date time, Params... params)
    {
        schedule(time, -1, false, params);
    }

    public final void schedule(long delay, final long timeout, final boolean mayInterruptIfRunning, final Params... params)
    {
        schedule(delay, -1, timeout, mayInterruptIfRunning, params);
    }

    public final void schedule(long delay, long period, final long timeout, final boolean mayInterruptIfRunning, final Params... params)
    {
//        if (timer != null) {
//            return;
//        }

        if (delay > 0) {
            TimerTask timerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    if (mStatus == Status.FINISHED || mStatus == Status.PENDING) {
                        mStatus = Status.PENDING;
                        execute(timeout, mayInterruptIfRunning, params);
                    }
                }
            };
            timer = new Timer();
            if (period > 0) {
                timer.schedule(timerTask, delay, period);
            }
            else {
                timer.schedule(timerTask, delay);
            }
        }
        else {
            execute(timeout, mayInterruptIfRunning, params);
        }
    }

    public final void schedule(Date datetime, final long timeout, final boolean mayInterruptIfRunning, final Params... params)
    {
//        if (timer != null) {
//            return;
//        }
        if (datetime != null) {
            timer = new Timer();
            timer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    execute(timeout, mayInterruptIfRunning, params);
                }
            }, datetime);
        }
        else {
            execute(timeout, mayInterruptIfRunning, params);
        }
    }

    public void cancelSchedule()
    {
        if (timer != null) {
            timer.cancel();
        }
    }

    public final AsyncTask<Params, Progress, Result> execute(final long timeout, final boolean mayInterruptIfRunning, Params... params)
    {
        return executeTimeout(timeout, mayInterruptIfRunning, params);
    }

    public final AsyncTask<Params, Progress, Result> executeTimeout(final long timeout, final boolean mayInterruptIfRunning, Params... params)
    {
        if (mStatus != Status.PENDING) {
            switch (mStatus) {
                case RUNNING:
                    return this;
//                    throw new IllegalStateException("Cannot execute task: the task is already running.");
//                case FINISHED:
//                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }

        mStatus = Status.RUNNING;

        onPreExecute();
        addWorker();
        mWorker.mParams = params;
        sExecutor.execute(mFuture);
        if (timeout > 0) {
            Callable mWorker1 = new Callable()
            {
                public Object call() throws Exception
                {
                    try {
                        synchronized (this) {
                            this.wait(timeout);
                        }
                        synchronized (mFuture) {
                            if (!mFuture.isDone()) {
                                mFuture.cancel(mayInterruptIfRunning);
                            }
                            if (mFuture.isCancelled()) {
                                mStatus = Status.FINISHED;
                                Message message = sHandler.obtainMessage(MESSAGE_POST_TIMEOUT,
                                    new AsyncTaskResult<Result>(AsyncTask.this, (Result[]) null));
                                message.sendToTarget();
                            }
                        }
                    }
                    catch (Throwable e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            timeoutFuture = new FutureTask(mWorker1);
            sExecutor.execute(timeoutFuture);
        }
        return this;
    }

    /**
     * This method can be invoked from {@link #doInBackground} to
     * publish updates on the UI thread while the background computation is
     * still running. Each call to this method will trigger the execution of
     * {@link #onProgressUpdate} on the UI thread.
     *
     * @param values The progress values to update the UI with.
     *
     * @see #onProgressUpdate
     * @see #doInBackground
     */
    protected final void publishProgress(Progress... values)
    {
        sHandler.obtainMessage(MESSAGE_POST_PROGRESS,
            new AsyncTaskResult<Progress>(this, values)).sendToTarget();
    }

    private void finish(Result result)
    {
        if (isCancelled()) result = null;
        onPostExecute(result);
        mStatus = Status.FINISHED;
    }

    /**
     * Indicates the current status of the task. Each status will be set only once
     * during the lifetime of a task.
     */
    public enum Status
    {
        /**
         * Indicates that the task has not been executed yet.
         */
        PENDING,
        /**
         * Indicates that the task is running.
         */
        RUNNING,
        /**
         * Indicates that {@link com.makeapp.android.task.AsyncTask#onPostExecute} has finished.
         */
        FINISHED,
    }

    private static class InternalHandler extends Handler
    {
        @SuppressWarnings({"unchecked", "RawUseOfParameterizedType"})
        @Override
        public void handleMessage(Message msg)
        {
            AsyncTaskResult result = (AsyncTaskResult) msg.obj;
            switch (msg.what) {
                case MESSAGE_POST_RESULT:
                    // There is only one result
                    result.mTask.finish(result.mData[0]);
                    break;
                case MESSAGE_POST_PROGRESS:
                    result.mTask.onProgressUpdate(result.mData);
                    break;
                case MESSAGE_POST_CANCEL:
                    result.mTask.onCancelled();
                    result.mTask.mStatus = Status.FINISHED;
                    break;
                case MESSAGE_POST_TIMEOUT:
                    result.mTask.onTimeout();
                    result.mTask.mStatus = Status.FINISHED;
                    break;

                case MESSAGE_POST_EXCEPTION:
                    result.mTask.onException(result.getThrowable());
                    result.mTask.mStatus = Status.FINISHED;
                    break;
            }
        }
    }

    private static abstract class WorkerRunnable<Params, Result>
        implements Callable<Result>
    {
        Params[] mParams;
    }

    @SuppressWarnings({"RawUseOfParameterizedType"})
    private static class AsyncTaskResult<Data>
    {
        final AsyncTask mTask;
        final Data[] mData;
        Throwable throwable;

        private Throwable getThrowable()
        {
            return throwable;
        }

        private void setThrowable(Throwable throwable)
        {
            this.throwable = throwable;
        }

        private AsyncTaskResult(AsyncTask mTask, Data[] mData, Throwable throwable)
        {
            this.throwable = throwable;
            this.mData = mData;
            this.mTask = mTask;
        }

        AsyncTaskResult(AsyncTask task, Data... data)
        {
            mTask = task;
            mData = data;
        }
    }
}
