package com.nearor.commonui.ativity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nearor.common.route.Route;
import com.nearor.framwork.app.MyApplication;
import com.nearor.framwork.util.Lg;

import java.util.List;

/**
 * Created by Nearor on 16/8/18.
 *
 * 跳转 Activity。
 * 接收 android.intent.action.VIEW，根据传入的 URL（例如：zhk://home），跳转到相应 Activity
 */

public class ForwardActivity extends AppActivity {

    private static final String TAG = Lg.makeLogTag(ForwardActivity.class);

    private static final int REQUEST_CODE_FORWARD = 1;

    private TextView textView;
    private boolean hasBeenLaunched;
    private static final String HAS_BEEN_LAUNCHED_KEY = "hasBeenLaunched";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout rootView = new FrameLayout(this);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(-1,-1));
        setContentView(rootView);

        textView = new TextView(this);
        textView.setLayoutParams(new FrameLayout.LayoutParams(-2,-2,17));
        rootView.addView(textView);

        if (!(this.getApplication() instanceof MyApplication)) {
            textView.setText("无法载入页面");
        }else {
            forward();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(HAS_BEEN_LAUNCHED_KEY,hasBeenLaunched);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_FORWARD){
            setResult(resultCode,data);
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void forward() {
        if(!hasBeenLaunched){
            Intent intent = this.getIntent();
            Intent forwardIntent = new Intent(intent.getAction(),intent.getData());
            if(intent.getExtras() !=null){
                forwardIntent.putExtras(intent.getExtras());
            }
            Route.getSharedInstance().configIntentForModule(forwardIntent);
            try {
                List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(forwardIntent,0);
                if(resolveInfos.size() == 1){
                   ResolveInfo info = resolveInfos.get(0);
                    if(getPackageName().equals(info.activityInfo.packageName) &&
                            getClass().getName().equals(info.activityInfo.name))
                    {
                        throw new Exception("Infinite loop");
                    }
                }

                startActivityForResult(forwardIntent,REQUEST_CODE_FORWARD);
                this.hasBeenLaunched = true;

            }catch (Exception e){
                textView.setText("无法加载页面");
                textView.append(e.getMessage());
                textView.setLayoutParams(new FrameLayout.LayoutParams(-2,-2,17));

                Lg.e(TAG,"无法加载页面" + forwardIntent,e);
            }

        }
    }

}
