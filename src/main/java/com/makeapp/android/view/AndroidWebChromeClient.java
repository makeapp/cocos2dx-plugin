package com.makeapp.android.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.makeapp.javase.log.LogUtil;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-6-10
 * Time: 上午10:55
 */
public class AndroidWebChromeClient extends WebChromeClient {
    private Activity context = null;

    int prom_dialog;

    public AndroidWebChromeClient(Activity context, int prom_dialog) {
        this.context = context;
        this.prom_dialog = prom_dialog;
    }

    public AndroidWebChromeClient(Activity context) {
        this.context = context;
    }

    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        //构建一个Builder来显示网页中的alert对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示对话框");
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                result.confirm();
            }

        });
        builder.setCancelable(false);
        builder.create();
        builder.show();

//        Dialog dialog = new Dialog(context);
//        dialog.setContentView(R.layout.alert);
//        dialog.show();
        return true;
    }

    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("带选择的对话框");
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                result.confirm();
            }

        });
        builder.setNeutralButton(android.R.string.cancel, new AlertDialog.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                result.cancel();
            }

        });
        builder.setCancelable(false);
        builder.create();
        builder.show();
        return true;
    }

    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(prom_dialog, null);
        //设置 TextView对应网页中的提示信息
//        ((TextView) v.findViewById(R.id.TextView_PROM)).setText(message);
        //设置EditText对应网页中的输入框
//        ((EditText) v.findViewById(R.id.EditText_PROM)).setText(defaultValue);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("带输入的对话框 ");
        builder.setView(v);
        builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
//                String value = ((EditText) v.findViewById(R.id.EditText_PROM)).getText().toString();
//                result.confirm(value);
            }

        });
        builder.setNegativeButton(android.R.string.cancel, new AlertDialog.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                result.cancel();
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialog) {
                result.cancel();
            }

        });
        builder.create();
        builder.show();
        return true;
    }

    //设置网页加载的进度条
    public void onProgressChanged(WebView view, int newProgress) {
        context.getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);
        super.onProgressChanged(view, newProgress);
    }

    //设置应用程序的标题
    public void onReceivedTitle(WebView view, String title) {
        context.setTitle(title);
        super.onReceivedTitle(view, title);
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
        LogUtil.info("onCreateWindow");
        return super.onCreateWindow(view, dialog, userGesture, resultMsg);
    }

    @Override
    public void onCloseWindow(WebView window) {
        LogUtil.info("onCloseWindow");
        super.onCloseWindow(window);
    }
}
