package com.nearor.myroleapp.pm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearor.commonui.fragment.AppFragment;
import com.nearor.myroleapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImFragment extends AppFragment {


    public ImFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_im,container,false);
        return view;
    }

}
