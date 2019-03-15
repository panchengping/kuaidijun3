package com.example.kuaidijun;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class OtherFragment extends Fragment {
    Button logout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.other_fragment,null);
        return view;
    }

    //fragment的事件要添加在onActivityCreated中，不可以在onCreateView中
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //退出登录按键
        logout=getActivity().findViewById(R.id.logout);
        logout.setOnClickListener(listenLogout);
    }
    //退出登录事件
    View.OnClickListener listenLogout= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(getActivity(),LoginActivity.class);
            startActivity(intent);
        }
    };

}
