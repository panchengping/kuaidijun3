package com.example.kuaidijun;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.express_fragment,null);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        loadList();
    }

    //加载列表项
    public void loadList(){
        ListView listview = getActivity().findViewById(R.id.express_list_view); // 获取列表视图
        String tradeId[]={"1","2","3","4","5"};
        String orderDate[]={"2019-03-12","2019-03-12","2019-03-13","2019-03-14","2019-03-15"};
        String status[]={"未完成","未完成","未完成","未完成","未完成"};
        String operation[]={"取消","取消","取消","取消","取消"};

        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>(); // 创建一个list集合
        // 通过for循环将图片id和列表项文字放到Map中，并添加到list集合中
        for (int i = 0; i < tradeId.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>(); // 实例化Map对象
            map.put("订单编号", tradeId[i]);
            map.put("下单日期", orderDate[i]);
            map.put("订单状态",status[i]);
            map.put("操作",operation[i]);
            listItems.add(map); // 将map对象添加到List集合中
        }
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), listItems,
                R.layout.express_list_item, new String[]{"订单状态","下单日期", "订单编号","操作"}, new int[]{
                R.id.express_status,R.id.express_order_date,R.id.express_trade_id,R.id.express_operation}); // 创建SimpleAdapter
        listview.setAdapter(adapter); // 将适配器与ListView关联
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取选择项的值
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), map.get("订单编号").
                        toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


