package com.nearor.myroleapp.pm;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nearor.commonui.PickImageDialogFragment;
import com.nearor.commonui.fragment.AppFragment;
import com.nearor.framwork.bitmap.SimpleImageLoader;
import com.nearor.framwork.network.APICallBack;
import com.nearor.framwork.network.APIResponse;
import com.nearor.framwork.util.ToastUtil;
import com.nearor.myroleapp.R;
import com.nearor.myroleapp.api.MeService;
import com.nearor.myroleapp.api.entity.MeCenterData;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends AppFragment implements View.OnClickListener{

    private MeService mMeService = new MeService(this);
    private MeCenterData mMeCenterData;
    private ImageView mIcon;
    private TextView mName;
    private TextView mPhone;
    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initViews(view);
        serService();
        return view;
    }

    private void initViews(View container) {
        mIcon = (ImageView) container.findViewById(R.id.pm_me_icon);
        mName = (TextView) container.findViewById(R.id.pm_me_name);
        mPhone = (TextView) container.findViewById(R.id.pm_me_phone);

        mIcon.setOnClickListener(this);
    }

    private void initData(){
        //用户头像
        if (mMeCenterData.getImg()!= null) {
            SimpleImageLoader.display(mMeCenterData.getImg(),mIcon,R.mipmap.pm_me_origin);
        }
        //用户姓名
        if(mMeCenterData.getName()!=null){
            mName.setText(mMeCenterData.getName());
        }else {
            mName.setVisibility(View.GONE);
        }
        //用户电话
        if(mMeCenterData.getMobilephone()!=null){
            mPhone.setText(mMeCenterData.getMobilephone());
        }else {
            mPhone.setVisibility(View.GONE);
        }


    }

    /**
     * 连接接口
     */
    private void serService(){
        mMeService.getData(new APICallBack<MeCenterData>() {
            @Override
            public void onSuccess(@NonNull MeCenterData object) {
                mMeCenterData = object;
                initData();
            }

            @Override
            public void onError(Throwable throwable, @Nullable APIResponse apiResponse) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == mIcon){
            ToastUtil.showMessage("改变图像");
            PickImageDialogFragment.getInstance(MeFragment.this,"设置头像").show(getFragmentManager(),"chooseAvatar");
        }

    }
}
