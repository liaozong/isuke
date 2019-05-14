package com.example.liao.isuke.activity.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.databinding.ActivityStartBinding;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by liao on 2018/3/14.
 */

public class StartAty extends BaseAty {

    private ActivityStartBinding mBinding;
    //权限组内只需要申请一个权限，组内的其它权限系统会自动同意
    //private final int ACCESS_COARSE_LOCATION = 2;//允许一个程序访问CellID或WiFi热点来获取粗略的位置
    //private final int ACCESS_FINE_LOCATION = 1;//获取精确位置，通过GPS芯片接收卫星的定位信息，定位精度达10米以内
    private final int ACCESS_COARSE_LOCATION = 1;
    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_start);
    }

    @Override
    protected void initData() {
        super.initData();
        checkPermission();
    }

    //检查用户是否给予了权限，如果给予了，打开系统相册；如果没有给予权限，则向用户申请授权
    private void checkPermission() {
        // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
        //List<String> mPermissionList = new ArrayList<>();
        //String[] permissons = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        if (!(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)) { //权限没有被授予

            //Toasty.success(context,"权限未拥有，需要申请权限").show();
            /**申请授权
             * @param
             *  @param activity The target activity.（Activity|Fragment、）
             * @param permissions The requested permissions.（权限字符串数组）
             * @param requestCode Application specific request code to match with a result（int型申请码）
             *int, String[], int[]
             * */
            ActivityCompat.requestPermissions(StartAty.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    ACCESS_COARSE_LOCATION);

        } else {//权限被授予
            //Toasty.success(context,"已经拥有权限").show();
            //直接操作
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == ACCESS_COARSE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toasty.success(StartAty.this,"权限获取成功").show();

            } else { // Permission Denied

                Toasty.info(StartAty.this, "为了软件能正常运行，请前往设置页面手动授予软件定位权限").show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void initEvent() {
        mBinding.regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartAty.this, PhoneAty.class);
                intent.putExtra("type", "StartAty");
                startActivity(intent);
            }
        });
        mBinding.startLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartAty.this, LoginAty.class));
                finish();
            }
        });
    }
}
