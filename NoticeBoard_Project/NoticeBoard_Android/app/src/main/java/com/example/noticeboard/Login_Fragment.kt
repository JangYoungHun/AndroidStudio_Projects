package com.example.noticeboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_login.view.*


class Login_Fragment : Fragment() {

    lateinit var navController:NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        navController = requireActivity().findNavController(R.id.nav_host_fragment)
        val v = (inflater.inflate(R.layout.fragment_login, container, false))


        //화원가입 버튼 동작

        v.btn_join.setOnClickListener {
            navController.navigate(R.id.action_login_Fragment_to_createAccount_Fragment)
        }
        //로그인 버튼 동작
        v.btn_login.setOnClickListener {

            //아이디 비밀번호 조건 확인 ex) 특수문자 등
            //아이디 중복확인
            HttpRequest.connect()
        }

        return v
    }


}