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
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.net.URLDecoder

object HttpRequest {

    enum class Action {
        CHECK_DUPLICATION,
        REGISTER_ACCOUNT,
        LOGIN,
        ADD_NOTICE,
        READ_NOTICE
    }

    enum class ConnectResult {
        SERVER_ERROR,
        SUCCESS,
        FAILED,
        DUPLICATE,
        NOT_DUPLICATE
    }

    // private const val SERVER_URI: String = "http://172.30.1.40:8888/UserDAO"
    private const val SERVER_URI: String = "http://mrjangsserver.ddns.net:8888/Service"

    private const val TAG: String = "HttpRequestLOG"


    fun connect(action: Action, userData: UserData): ConnectResult {
        // 서버 켜져있는지 확인 코드 추가 필요
        val connectThread = ConnectThread(action, userData)
        try {
            connectThread.start()
            //차후 다른 방법있는지 공부후 변경
            // 최대 기다리는 시간 3초
            connectThread.join(3000)
            return connectThread.getResult()
        } catch (e: Exception) {
            return ConnectResult.SERVER_ERROR
        }
    }

    fun connect(action: Action, noticeItem: NoticeItem): ConnectResult {
        // 서버 켜져있는지 확인 코드 추가 필요
        val connectThread = ConnectThread(action, noticeItem)
        try {
            connectThread.start()
            //차후 다른 방법있는지 공부후 변경
            // 최대 기다리는 시간 3초
            connectThread.join(3000)
            return connectThread.getResult()
        } catch (e: Exception) {
            return ConnectResult.SERVER_ERROR
        }
    }
    fun readNotice(action:Action): String{
        val connectThread = ConnectThread(action)
        connectThread.start()
        connectThread.join(5000)
        return URLDecoder.decode(connectThread.getResultStr(),"utf-8")
    }



    class ConnectThread(var action: Action) : Thread() {


        lateinit var userData: UserData
        lateinit var noticeItem: NoticeItem
        private var result = HttpRequest.ConnectResult.SERVER_ERROR
        lateinit var conn: HttpURLConnection

        private var type = ""
        private var responseStr = ""

        constructor(action: Action, userData: UserData) : this(action) {
            this.userData = userData
        }

        constructor(action: Action, noticeItem: NoticeItem) : this(action) {
            this.noticeItem = noticeItem
        }

        fun getResult(): ConnectResult = result
        fun getResultStr(): String = responseStr

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
                        "type=isIdExist&id=${userData.id}"
                    }
                    Action.REGISTER_ACCOUNT -> {
                        "type=register&id=${userData.id}&pwd=${userData.pwd}"
                    }
                    Action.LOGIN -> {
                        "type=login&id=${userData.id}&pwd=${userData.pwd}"
                    }
                    Action.ADD_NOTICE -> {
                        "type=addNotice&title=${noticeItem.title}&author=${noticeItem.author}&body=${noticeItem.body}&date=${noticeItem.date}"
                    }
                    Action.READ_NOTICE -> {
                        "type=readNotice"
                    }
                }

                try {
                    var out = conn.outputStream
                    out.write(message.toByteArray(Charsets.UTF_8))
                    out.flush()
                    out.close()
                } catch (e: Exception) {
                    throw e
                }


                if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                    // 요청 성공
                    try {
                        Log.d(TAG, "ERROR 요청 성공")
                        val br = BufferedReader(InputStreamReader(conn.inputStream,"UTF-8"))
                        var sb:StringBuilder = StringBuilder()

                        while (true) {
                            val str: String = br.readLine() ?: break
                            sb.append(str)
                        }
                        br.close()
                        responseStr = sb.toString()

                        if(action == Action.READ_NOTICE){
                            return
                        }
                        else {
                            result = when (action) {
                                Action.CHECK_DUPLICATION -> {
                                    when (responseStr) {
                                        "ID_NOT_EXIST" -> HttpRequest.ConnectResult.NOT_DUPLICATE
                                        "ID_EXIST" -> HttpRequest.ConnectResult.DUPLICATE
                                        else -> HttpRequest.ConnectResult.FAILED
                                    }
                                }
                                Action.REGISTER_ACCOUNT -> {
                                    when (responseStr) {
                                        "SUCCESS" -> HttpRequest.ConnectResult.SUCCESS
                                        "FAILED" -> HttpRequest.ConnectResult.FAILED
                                        else -> HttpRequest.ConnectResult.FAILED
                                    }
                                }
                                Action.LOGIN -> {
                                    when (responseStr) {
                                        "SUCCESS" -> HttpRequest.ConnectResult.SUCCESS
                                        "FAILED" -> HttpRequest.ConnectResult.FAILED
                                        else -> HttpRequest.ConnectResult.FAILED
                                    }
                                }
                                Action.ADD_NOTICE -> {
                                    when (responseStr) {
                                        "SUCCESS" -> HttpRequest.ConnectResult.SUCCESS
                                        "FAILED" -> HttpRequest.ConnectResult.FAILED
                                        else -> HttpRequest.ConnectResult.FAILED
                                    }
                                }
                                else -> HttpRequest.ConnectResult.FAILED

                            }
                        }
                    } catch (e: SocketTimeoutException) {
                        throw e
                    } catch (e: Exception) {
                        throw e
                    }


                } else {

                    Log.d(TAG, "ERROR 요청 실패")

                }
                conn.disconnect()


            }catch (e: SocketTimeoutException) {
                e.printStackTrace()
                Log.d(TAG, "SERVER 연결 실패")
                return
            }
            catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "SERVER 연결 실패")
                return
            }
        }

    }


}