package com.example.backkeyexit;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

public class BackKeyClick {

    private long backkeyClickTime = 0;
    private Activity activity;

    public BackKeyClick(Activity activity) {
        this.activity = activity;
    }



    public void onBackPressd(){

        if(System.currentTimeMillis() > backkeyClickTime+2000) {
            backkeyClickTime =  System.currentTimeMillis();
            showToast();
            return;
        }
        if(System.currentTimeMillis()<= backkeyClickTime+2000){
            activity.finish();
        }

       }



    private void showToast() {
        Toast.makeText(activity , "뒤로가기 버튼을 한 번 더 누르면 종료합니다." , Toast.LENGTH_SHORT).show();
    }


}
