package com.nearor.commonui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

/**
 *
 * <p>不要直接使用构造方法初始化。</p>
 * <p>如果在 Activity 里面使用则采用 {@link #getInstance(String, boolean)} 系列方法创建实例</p>
 * <p>如果在 Fragment 里面使用则采用 {@link #getInstance(Fragment, String, boolean)} 系列方法创建实例</p>
 * <p>在 {@link #onActivityResult(int, int, Intent)} 里面获取结果</p>
 *
 * Created by Nearor on 16/6/15.
 */
public class PickImageDialogFragment extends DialogFragment {

    private static final String BUNDLE_KEY_TITLE = "Title";
    private static final String DEFAULT_TITLE = "选择图片";
    private static final String BUNDLE_KEY_NEED_FULL_SIZE_IMAGE = "NeedFullSizeImage";

    private String title = DEFAULT_TITLE;

    private Fragment hostFragment;

    public static PickImageDialogFragment getInstance(Fragment hostFragment,String title){
        PickImageDialogFragment pickImageDialogFragment = PickImageDialogFragment.getInstance(title,false);
        pickImageDialogFragment.hostFragment = hostFragment;
        return pickImageDialogFragment;
    }

    public static PickImageDialogFragment getInstance(String title,boolean needFullImageSiza){
        PickImageDialogFragment fragment = new PickImageDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_TITLE,title);
        bundle.putBoolean(BUNDLE_KEY_NEED_FULL_SIZE_IMAGE,needFullImageSiza);
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setItems(new String[]{"相册", "拍照"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:

                                break;
                            case 1:

                                break;
                        }

                    }
                })
                .setNegativeButton("取消",null);

        return builder.create();
    }
}
