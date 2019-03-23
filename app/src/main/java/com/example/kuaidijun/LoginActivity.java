package com.example.kuaidijun;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
    //获取存储对象
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String accountStr;
    String passwordStr;

    public EditText account;
    public EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取登录信息控件
        account=findViewById(R.id.account);
        password=findViewById(R.id.password);
        //获取存储对象
        sp=getSharedPreferences("login",MODE_PRIVATE);
        editor=sp.edit();

        //查询是否存在本地登录信息
        isLogined();
        //获取注册按键对象
        TextView register=findViewById(R.id.register);
        //获取登录按键对象
        Button loginBtn=findViewById(R.id.login);
        //注册事件
        register.setOnClickListener(listenRegister);
        //登录时验证
        loginBtn.setOnClickListener(listenLogin);
        Log.d("000000000000", "onCreate: ");

    }
    //判断是否有本地登录信息
    public void isLogined(){
        //如果存在登录信息则同步到信息栏
        if(sp.getString("account","000000")!=null&&
                sp.getString("password","000000")!=null){
            accountStr=sp.getString("account","");
            passwordStr=sp.getString("password","");
            account.setText(accountStr);
            password.setText(passwordStr);
        }
    }
    //注册事件
    View.OnClickListener listenRegister=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    };
    //登录事件
    View.OnClickListener listenLogin=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            accountStr=account.getText().toString();
            passwordStr=password.getText().toString();
            if("".equals(accountStr)||"".equals(passwordStr)){
                Toast.makeText(LoginActivity.this, "请输入完整的登录信息！",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                //登陆信息验证
                if("123456".equals(accountStr)&&"123456".equals(passwordStr)){
                    //登录信息正确，保存数据到本地
                    editor.putString("account",accountStr);
                    editor.putString("password",passwordStr);
                    editor.commit();
                    //通过intent指定跳转的页面，并销毁当前activity'
                    Intent intent=new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(LoginActivity.this,
                            "账号或密码错误", Toast.LENGTH_LONG).show();
            }
        }
    };
}
