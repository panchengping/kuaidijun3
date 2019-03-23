package com.example.kuaidijun;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppointmentFragment extends Fragment {
    private DBOpenHelper dbOpenHelper;   //定义DBOpenHelper
    ListView listView;
    Button addAppointmentBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.appointment_fragment,null);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //创建DBOpenHelper对象,指定名称、版本号并保存在databases目录下
        dbOpenHelper = new DBOpenHelper(getActivity(), "expressTrade.db", null, 1);
        //获取显示结果的ListView
        listView = getActivity().findViewById(R.id.oppointment_list_view);
        //获取跳转添加界面的按钮
        addAppointmentBtn =getActivity().findViewById(R.id.add_appointemnt_btn);
        //单击添加生词按钮，实现跳转到添加生词的界面
        addAppointmentBtn.setOnClickListener(l);
        //自动查询数据库
        loading();

    }
    View.OnClickListener l=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), AddActivity.class);
            startActivity(intent);
        }
    };
    //加载数据库
    public void loading(){
        Cursor cursor=dbOpenHelper.getReadableDatabase().query("expressTrade",null
                ,null,null,null,null,null);

        //创建ArrayList对象，用于保存查询出的结果
        ArrayList<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        // 遍历Cursor结果集
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<>();  // 将结果集中的数据存入HashMap中
            // 取出查询记录中第2列、第3列的值
            map.put("编号", cursor.getString(0));
            map.put("日期", cursor.getString(1));
            map.put("状态",cursor.getString(2));
            map.put("操作","删除");
            resultList.add(map);                        //将查询出的数据存入ArrayList中
        }
        if (resultList == null && resultList.size() == 0) {  //如果数据库中没有数据
            // 显示提示信息，没有相关记录
            Toast.makeText(getActivity(),
                    "很遗憾，没有相关记录！", Toast.LENGTH_LONG).show();
        } else {
            // 否则将查询的结果显示到ListView列表中
            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), resultList,
                    R.layout.appointment_list_item,
                    new String[]{"编号", "日期","状态","操作"}, new int[]{
                    R.id.trade_id, R.id.order_date,R.id.status,R.id.operation});
            listView.setAdapter(simpleAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //获取选择项的值
                    Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
//                    final int key=Integer.parseInt(map.get("编号").toString());
                    final String key=map.get("编号").toString();
                    new AlertDialog.Builder(getActivity()).setTitle("确认要删除吗？")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                //确认事件
                                public void onClick(DialogInterface dialog, int which) {
                                    int ok = dbOpenHelper.getReadableDatabase().delete("expressTrade",
                                            "_id=?", new String[]{key});
                                    Toast.makeText(getActivity(),"数据已经删除".toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                //不确认事件
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击“返回”后的操作,这里不设置没有任何操作
                                    Toast.makeText(getActivity(), "你点击了返回键", Toast.LENGTH_LONG).show();
                                }
                            }).show();

                }
            });
        }
    }

    //删除数据
    public void deleteData(){

    }
    @Override
    public void onDestroy() {  //实现退出应用时，关闭数据库连接
        super.onDestroy();
        if (dbOpenHelper != null) {   //如果数据库不为空时
            dbOpenHelper.close();     //关闭数据库连接
        }
    }
}
