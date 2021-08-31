package com.example.noticeboard

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

object HttpRequest {

    enum class Action {
        CHECK_DUPLICATION,
        REGISTER_ACCOUNT,
        LOGIN
    }
    enum class ConnectResult {
        SERVER_ERROR,
        SUCCESS,
        FAILED,
        DUPLICATE,
        NOT_DUPLICATE
    }

   // private const val SERVER_URI: String = "http://172.30.1.40:8888/UserDAO"
    private const val SERVER_URI: String = "http://mrjangsserver.ddns.net:8888/UserDAO"

    private const val TAG: String = "HttpRequestLOG"


    fun connect(action: Action, userData: UserData): ConnectResult {
        // 서버 켜져있는지 확인 코드 추가 필요
        val connectThread = ConnectThread(action, userData)

        connectThread.start()
        //차후 다른 방법있는지 공부후 변경
        // 최대 기다리는 시간 3초
        connectThread.join(3000)
        return connectThread.getResult()
    }

    class ConnectThread(private var action: Action, private  var userData: UserData) : Thread() {

        private var result = HttpRequest.ConnectResult.SERVER_ERROR
        lateinit var conn: HttpURLConnection

        private var type = ""

        fun getResult(): ConnectResult = result

        override fun run() {
            try {
                val url: URL = URL(SERVER_URI)
                conn = (url.openConnection() as HttpURLConnection).apply {
                    requestMethod = "POST"
                    setRequestProperty("Accept-Charset", "UTF-8") // Accept-Charset 설정.
                    setRequestProperty(
                        "Context_Type",
                        "application/x-www-form-urlencoded;cahrset=UTF-8"
                    )
                    connectTimeout = 10000;
                    doOutput = true
                }


                var message: String = when (action) {
                    Action.CHECK_DUPLICATION -> {
                        "type=isExist&id=${userData.id}"
                    }
                    Action.REGISTER_ACCOUNT -> {
                        "type=register&id=${userData.id}&pwd=${userData.pwd}"
                    }
                    Action.LOGIN ->{
                        "type=login&id=${userData.id}&pwd=${userData.pwd}"
                    }
                }


                var out = conn.outputStream
                out.write(message.toByteArray(Charsets.UTF_8))
                out.flush()
                out.close()


                if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                    // 요청 성공
                    Log.d(TAG, "ERROR 요청 성공")
                    val br = BufferedReader(InputStreamReader(conn.inputStream))
                    val str: String = br.readLine()
                    if (str != null) {
                        Log.d(TAG, " Response 응답 : $str")
                    }
                    br.close()

                    result = when (action) {
                        Action.CHECK_DUPLICATION -> {
                            when (str) {
                                "ID_NOT_EXIST" -> HttpRequest.ConnectResult.NOT_DUPLICATE
                                "ID_EXIST" -> HttpRequest.ConnectResult.DUPLICATE
                                else -> HttpRequest.ConnectResult.FAILED
                            }
                        }
                        Action.REGISTER_ACCOUNT -> {
                            when (str) {
                                "SUCCESS" -> HttpRequest.ConnectResult.SUCCESS
                                "FAILED" -> HttpRequest.ConnectResult.FAILED
                                else -> HttpRequest.ConnectResult.FAILED
                            }
                        }
                        Action.LOGIN-> {
                            when (str) {
                                "SUCCESS" -> HttpRequest.ConnectResult.SUCCESS
                                "FAILED" -> HttpRequest.ConnectResult.FAILED
                                else -> HttpRequest.ConnectResult.FAILED
                            }
                        }
                    }
                } else {
                    Log.d(TAG, "ERROR 요청 실패")

                }
                conn.disconnect()


            }
            catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "SERVER 연결 실패")
                return
            }
        }

    }


}