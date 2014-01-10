/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ImageSpan;
import android.text.style.ParagraphStyle;
import android.text.style.QuoteSpan;
import android.widget.EditText;
import com.makeapp.javase.lang.StringUtil;

/**
 * @author <a href="mailto:shigang@shqianzhi.com">shigang</a>
 * @version $Date:11-4-14 ����6:34 $
 *          $Id$
 */
public class EditTextUtil extends TextViewUtil
{

    /**
     * @param activity
     * @param editTextId
     */
    @Deprecated
    public static String getEditTextString(Object activity, int editTextId)
    {
        EditText textView = (EditText) ViewUtil.findViewById(activity, editTextId);

        if (textView != null) {
            return textView.getText().toString().trim();
        }
        return null;
    }

    public static Editable getEditable(Object activity, int editTextId)
    {
        EditText textView = (EditText) ViewUtil.findViewById(activity, editTextId);

        if (textView != null) {
            return textView.getText();
        }
        return null;
    }

    public static String getEditHtmlAttachment(Context context, EditText editText, List<String> attachments)
        {
            StringBuilder contentBuf = new StringBuilder();
            Spanned text = editText.getText();
            int len = text.length();

            int next;
            for (int i = 0; i < text.length(); i = next) {
                next = text.nextSpanTransition(i, len, ParagraphStyle.class);
                withinDiv(contentBuf, attachments, text, i, next);
            }

            String content = contentBuf.toString();//filterHtml(Html.toHtml(editText.getText()));
            if (StringUtil.isInvalid(content)) {
                return null;
            }

            return content;
        }

        private static void withinDiv(StringBuilder out, List<String> attachments, Spanned text,
                                      int start, int end)
        {
            int next;
            for (int i = start; i < end; i = next) {
                next = text.nextSpanTransition(i, end, QuoteSpan.class);
                QuoteSpan[] quotes = text.getSpans(i, next, QuoteSpan.class);

    //                for (QuoteSpan quote: quotes) {
    //                    out.append("<blockquote>");
    //                }

                withinBlockquote(out, attachments, text, i, next);

    //                for (QuoteSpan quote: quotes) {
    //                    out.append("</blockquote>\n");
    //                }
            }
        }

        private static void withinBlockquote(StringBuilder out, List<String> attachments, Spanned text,
                                             int start, int end)
        {
    //            out.append("<p>");

            int next;
            for (int i = start; i < end; i = next) {
                next = TextUtils.indexOf(text, '\n', i, end);
                if (next < 0) {
                    next = end;
                }

                int nl = 0;

                while (next < end && text.charAt(next) == '\n') {
                    nl++;
                    next++;
                }

                withinParagraph(out, attachments, text, i, next - nl, nl, next == end);
            }

    //            out.append("</p>\n");
        }

        private static void withinParagraph(StringBuilder out, List<String> attachments, Spanned text,
                                            int start, int end, int nl,
                                            boolean last)
        {
            int next;
            for (int i = start; i < end; i = next) {
                next = text.nextSpanTransition(i, end, CharacterStyle.class);
                CharacterStyle[] style = text.getSpans(i, next, CharacterStyle.class);

                for (int j = 0; j < style.length; j++) {
    //                if (style[j] instanceof StyleSpan) {
    //                    int s = ((StyleSpan) style[j]).getStyle();
    //
    //                    if ((s & Typeface.BOLD) != 0) {
    //                        out.append("<b>");
    //                    }
    //                    if ((s & Typeface.ITALIC) != 0) {
    //                        out.append("<i>");
    //                    }
    //                }
    //                if (style[j] instanceof TypefaceSpan) {
    //                    String s = ((TypefaceSpan) style[j]).getFamily();
    //
    //                    if (s.equals("monospace")) {
    //                        out.append("<tt>");
    //                    }
    //                }
    //                if (style[j] instanceof SuperscriptSpan) {
    //                    out.append("<sup>");
    //                }
    //                if (style[j] instanceof SubscriptSpan) {
    //                    out.append("<sub>");
    //                }
    //                if (style[j] instanceof UnderlineSpan) {
    //                    out.append("<u>");
    //                }
    //                if (style[j] instanceof StrikethroughSpan) {
    //                    out.append("<strike>");
    //                }
    //                if (style[j] instanceof URLSpan) {
    //                    out.append("<a href=\"");
    //                    out.append(((URLSpan) style[j]).getURL());
    //                    out.append("\">");
    //                }
                    if (style[j] instanceof ImageSpan) {
                        out.append("img:{");
                        out.append(attachments.size());
                        out.append("}");
                        attachments.add(((ImageSpan) style[j]).getSource());

                        i = next;
                    }
    //                if (style[j] instanceof AbsoluteSizeSpan) {
    //                    out.append("<font size =\"");
    //                    out.append(((AbsoluteSizeSpan) style[j]).getSize() / 6);
    //                    out.append("\">");
    //                }
    //                if (style[j] instanceof ForegroundColorSpan) {
    //                    out.append("<font color =\"#");
    //                    String color = Integer.toHexString(((ForegroundColorSpan)
    //                        style[j]).getForegroundColor() + 0x01000000);
    //                    while (color.length() < 6) {
    //                        color = "0" + color;
    //                    }
    //                    out.append(color);
    //                    out.append("\">");
    //                }
                }

                withinStyle(out, text, i, next);

    //            for (int j = style.length - 1; j >= 0; j--) {
    //                if (style[j] instanceof ForegroundColorSpan) {
    //                    out.append("</font>");
    //                }
    //                if (style[j] instanceof AbsoluteSizeSpan) {
    //                    out.append("</font>");
    //                }
    //                if (style[j] instanceof URLSpan) {
    //                    out.append("</a>");
    //                }
    //                if (style[j] instanceof StrikethroughSpan) {
    //                    out.append("</strike>");
    //                }
    //                if (style[j] instanceof UnderlineSpan) {
    //                    out.append("</u>");
    //                }
    //                if (style[j] instanceof SubscriptSpan) {
    //                    out.append("</sub>");
    //                }
    //                if (style[j] instanceof SuperscriptSpan) {
    //                    out.append("</sup>");
    //                }
    //                if (style[j] instanceof TypefaceSpan) {
    //                    String s = ((TypefaceSpan) style[j]).getFamily();
    //
    //                    if (s.equals("monospace")) {
    //                        out.append("</tt>");
    //                    }
    //                }
    //                if (style[j] instanceof StyleSpan) {
    //                    int s = ((StyleSpan) style[j]).getStyle();
    //
    //                    if ((s & Typeface.BOLD) != 0) {
    //                        out.append("</b>");
    //                    }
    //                    if ((s & Typeface.ITALIC) != 0) {
    //                        out.append("</i>");
    //                    }
    //                }
    //            }
            }

            String p = last ? "" : "</p>\n<p>";

            if (nl == 1) {
                out.append("<br>\n");
            }
            else if (nl == 2) {
                out.append(p);
            }
            else {
                for (int i = 2; i < nl; i++) {
                    out.append("<br>");
                }

                out.append(p);
            }
        }

        private static void withinStyle(StringBuilder out, Spanned text,
                                        int start, int end)
        {
            for (int i = start; i < end; i++) {
                char c = text.charAt(i);

                if (c == '<') {
                    out.append("&lt;");
                }
                else if (c == '>') {
                    out.append("&gt;");
                }
                else if (c == '&') {
                    out.append("&amp;");
                }
                else if (c > 0x7E || c < ' ') {
                    out.append(c);
    //                out.append("&#" + ((int) c) + ";");
                }
                else if (c == ' ') {
                    while (i + 1 < end && text.charAt(i + 1) == ' ') {
                        out.append("&nbsp;");
                        i++;
                    }

                    out.append(' ');
                }
                else {
                    out.append(c);
                }
            }
        }
}
