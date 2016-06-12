package com.nearor.commonui.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;

/**
 *
 */
public class Utils {

    public static boolean isSoftKeyboardVisiable(Context context) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isAcceptingText() && imm.isActive();
    }

    public static void showSoftKeyboard(final View view, final Context context) {
        if (view != null && context != null && view.requestFocus()) {
            // 延迟 200ms 执行，防止其它动画影响
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    mInputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                }
            }, 200);
        }
    }

    public static void hideSoftKeyboard(View view, Context context) {
        if (view != null && context != null && view.getWindowToken() != null) {
            InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 判断点 (x, y) 是否在 view 里面。（x, y） 为屏幕绝对坐标
     * @param x 相对于屏幕绝对坐标 x
     * @param y 相对于屏幕绝对坐标 y
     */
    public static boolean inViewInBounds(View view, int x, int y) {
        if (view != null) {
            Rect outRect = new Rect();
            view.getDrawingRect(outRect);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            outRect.offset(location[0], location[1]);
            return outRect.contains(x, y);
        } else {
            return false;
        }
    }

    /**
     * 该方法可能改变 parent view 的 FocusableInTouchMode 状态
     * @param view 需要被 clearFocus 的 View
     * @return 成功 true, 失败 false
     */
    public static boolean forceClearFocus(View view) {
        view.clearFocus();
        while (view.hasFocus()) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof View) {
                ((View) parent).setFocusableInTouchMode(true);
                ((View) parent).requestFocus();
            } else {
                break;
            }
        }
        return !view.hasFocus();
    }

}
