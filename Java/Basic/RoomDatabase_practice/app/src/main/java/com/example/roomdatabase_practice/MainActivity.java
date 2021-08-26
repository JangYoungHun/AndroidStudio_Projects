package com.example.roomdatabase_practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UserDatabase database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "TestDB")
                .fallbackToDestructiveMigration()    //스키마 버전 변경 가능
                .allowMainThreadQueries()            //  Main Thread 에서 DB IO 가능
                .build();

        userDao = database.userDao();

        //데이터 삽입
    /*  User user = new User();
        user.setName("홍길동");
        user.setAge("20");
        user.setPhoneNumber("010-XXXX-XXXX");

        userDao.insert(user);
    */
        //데이터 조회
        List<User> userDatas = userDao.userDatas();
        for (int i = 0; i < userDatas.size(); i++) {
            Log.d("DATA READ", userDatas.get(i).name + "  "
                    + userDatas.get(i).age + "  "
                    + userDatas.get(i).phoneNumber);
        }

        //데이터 수정
        User user2 = new User();
        user2.setId(1);   // 바꿀 항목의 id
        user2.setName("홍길공");
        user2.setAge("18");
        user2.setPhoneNumber("010-1234-5678");

        userDao.update(user2);


        //데이터 삭제
        User user3 = new User();
        user3.setId(2);   // 삭제 항목의 id
        userDao.delete(user3);

    }
}