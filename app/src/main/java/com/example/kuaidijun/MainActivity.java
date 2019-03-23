package com.example.kuaidijun;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class MainActivity extends Activity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    FragmentManager fm;   // 获取Fragment管理对象
    FragmentTransaction ft; // 开启一个事务

    Button expressBtn;
    Button helpBtn;
    Button appointmentBtn;
    Button otherBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取页面选择按钮对象
        expressBtn = (Button) findViewById(R.id.express);
        helpBtn = (Button) findViewById(R.id.help);
        appointmentBtn = (Button) findViewById(R.id.appointment);
        otherBtn = (Button) findViewById(R.id.other);


        //获取sharedpreferences对象
        sp=getSharedPreferences("login",MODE_PRIVATE);
        editor=sp.edit();

        //初始化快递碎片页面
        initFragment();
        //为碎片按键添加事件监听器
        expressBtn.setOnClickListener(listenFragment);
        helpBtn.setOnClickListener(listenFragment);
        appointmentBtn.setOnClickListener(listenFragment);
        otherBtn.setOnClickListener(listenFragment);

        //判断是否自动跳转
        isLogined();
    }
    //判断是否自动跳转到登录界面
    public void isLogined(){
        if (sp.getString("account","").equals("123456")&&
                sp.getString("password","").equals("123456")){
            Toast.makeText(this, "已经登录！", Toast.LENGTH_SHORT).show();
        }else{
            //通过intent指定跳转的页面,并销毁当前页面
            Intent intent=new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }

    //初始化快递碎片
    public void initFragment(){
        fm = getFragmentManager();   // 获取Fragment
        ft = fm.beginTransaction(); // 开启一个事务
        Fragment f=new ExpressFragment();
        ft.add(R.id.main_fragment,f);
        ft.commit();
    }
    //创建切换碎片事件监听器
    View.OnClickListener listenFragment = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            toSetColor(v);//调用颜色设置函数
            fm = getFragmentManager();   // 获取Fragment
            ft = fm.beginTransaction(); // 开启一个事务
            Fragment f = null; //为Fragment初始化
            switch (v.getId()) {    //通过获取点击的id判断加载那个Fragment
                case R.id.express:
                    f = new ExpressFragment(); //创建第一个Fragment
                    break;
                case R.id.help:
                    f = new HelpFragment();//创建第二个Fragment
                    break;
                case R.id.appointment:
                    f = new AppointmentFragment();//创建第三个Fragment
                    break;
                case R.id.other:
                    f = new OtherFragment();//创建第四个Fragment
                    break;
                default:
                    break;
            }
            ft.replace(R.id.main_fragment, f); //替换Fragment
            ft.commit(); //提交事务
        }
    };
    //设置被点击的按钮颜色
    public void toSetColor(View v){
        expressBtn.setTextColor(Color.parseColor("#999999"));
        helpBtn.setTextColor(Color.parseColor("#999999"));
        appointmentBtn.setTextColor(Color.parseColor("#999999"));
        otherBtn.setTextColor(Color.parseColor("#999999"));
        switch (v.getId()) {    //通过获取点击的id判断加载那个Fragment
            case R.id.express:
                expressBtn.setTextColor(Color.parseColor("#2f90b9"));
                break;
            case R.id.help:
                helpBtn.setTextColor(Color.parseColor("#2f90b9"));
                break;
            case R.id.appointment:
                appointmentBtn.setTextColor(Color.parseColor("#2f90b9"));
                break;
            case R.id.other:
                otherBtn.setTextColor(Color.parseColor("#2f90b9"));
                break;
            default:
                break;
        }
    }

}