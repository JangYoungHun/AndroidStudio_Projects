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

object HttpRequest{

    private const val SERVER_URI:String = "http://172.30.1.40:8888/UserDAO"
    private const val TAG:String = "HttpRequestLOG"


    fun connect(){
      val connectThread = ConnectThread()
        connectThread.start()
    }
    class ConnectThread:Thread(){

        override fun run(){
            try {
                val url: URL = URL(SERVER_URI)
                val conn = (url.openConnection() as HttpURLConnection).apply {
                    requestMethod = "POST"
                    setRequestProperty("Accept-Charset", "UTF-8") // Accept-Charset 설정.
                    setRequestProperty(
                        "Context_Type",
                        "application/x-www-form-urlencoded;cahrset=UTF-8"
                    )
                    connectTimeout = 15000;
                    doOutput = true
                }

                var out = conn.outputStream

                //val message = "test=Test_Message"
                val message = "type=isExist&id=abc"
                out.write(message.toByteArray(Charsets.UTF_8))
                out.close()

                if (conn.responseCode == HttpURLConnection.HTTP_OK){
                    // 요청 성공
                    Log.d(TAG,"ERROR 요청 성공")

                    val br =  BufferedReader(InputStreamReader(conn.inputStream))
                    val str:String = br.readLine()
                    if(str!= null){
                       Log.d(TAG," Response 응답 : $str")
                    }

                    br.close()
                }
                else{
                    Log.d(TAG,"ERROR 요청 실패")

                }


            }
            catch (e:Exception){
                e.printStackTrace()
                Log.d(TAG,"ERROR 요청 실패")
            }

        }

    }



}