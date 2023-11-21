package com.example.rcpjt;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    final static private String url = "http://10.0.2.2/test.php";
    public static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TEST 버튼
        Button testBtn = (Button)findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Thread th = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        Log.v("run", "Success");
                        httpGetConn("go", "0");
                    }
                });

                th.start();
            }


        });
    }

    public static void httpGetConn(String cmdString, String argString){
        if(cmdString != null && cmdString.length() > 0 && argString != null && argString.length() > 0){
            try {
                Log.v("conn", "success");
                String totalURL = url + "?cmdString="+cmdString+"&argString="+argString;
                URL urls = new URL(totalURL);
                Log.v("url", totalURL);

                // 연결 기본 관리
                HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
                conn.setRequestMethod("GET");

                // 요청 실시
                conn.connect();

                // 응답 데이터 버퍼에 쌓음
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuffer sb = new StringBuffer();
                String responseData;

                while((responseData = br.readLine()) != null){
                    sb.append(responseData);
                }

                Log.v("response", sb.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}