package com.example.kuaidijun;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExpressFragment extends Fragment {
    TextView dis;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.express_fragment,null);
        dis=view.findViewById(R.id.display);
        return view;
    }

}
