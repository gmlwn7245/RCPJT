package com.example.rcpjt;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    final static private String url = "http://3.39.253.104/test.php";
    public static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버튼 모음
        TextView leftView = (TextView)findViewById(R.id.leftView);
        TextView rightView = (TextView)findViewById(R.id.rightView);
        TextView goView = (TextView)findViewById(R.id.goView);
        TextView backView = (TextView)findViewById(R.id.backView);
        TextView stopView = (TextView)findViewById(R.id.stopView);


        // 좌회전 버튼
        leftView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Thread th = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            Log.v("leftPressed", "success");
                            httpGetConn("left", "0");
                            leftView.setBackgroundColor(Color.parseColor("#2A450B"));
                        }
                        if(event.getAction() == MotionEvent.ACTION_UP){
                            Log.v("leftUnPressed", "success");
                            httpGetConn("mid", "0");
                            leftView.setBackgroundColor(Color.parseColor("#B6F66B"));
                            //pressed = false;
                        }
                    }
                });
                th.start();
                return true;
            }
        });

        rightView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Thread th = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            Log.v("rightPressed", "success");
                            httpGetConn("right", "0");
                            rightView.setBackgroundColor(Color.parseColor("#2A450B"));
                            //pressed = true;
                        }
                        if(event.getAction() == MotionEvent.ACTION_UP){
                            Log.v("rightUnPressed", "success");
                            httpGetConn("mid", "0");
                            rightView.setBackgroundColor(Color.parseColor("#B6F66B"));
                        }
                    }
                });
                th.start();

                return true;
            }
        });

        goView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Thread th = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            Log.v("goPressed", "success");
                            httpGetConn("go", "0");
                            goView.setBackgroundColor(Color.parseColor("#FA4747"));
                            // #FFADAD
                            //pressed = true;
                        }

                        if(event.getAction() == MotionEvent.ACTION_UP){
                            Log.v("rightUnPressed", "success");
                            //httpGetConn("stop", "0");
                            //pressed = false;
                            goView.setBackgroundColor(Color.parseColor("#FFADAD"));
                        }
                    }
                });
                th.start();

                return true;
            }
        });

        backView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Thread th = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            Log.v("goPressed", "success");
                            httpGetConn("back", "0");
                            backView.setBackgroundColor(Color.parseColor("#2F4DEF"));
                        }

                        if(event.getAction() == MotionEvent.ACTION_UP){
                            Log.v("rightUnPressed", "success");
                            //httpGetConn("stop", "0");
                            // #8494EA
                            backView.setBackgroundColor(Color.parseColor("#8494EA"));
                        }

                    }
                });
                th.start();

                return true;
            }
        });

        stopView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Thread th = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            Log.v("goPressed", "success");
                            httpGetConn("stop", "0");
                            stopView.setBackgroundColor(Color.parseColor("#FF3D00"));
                        }

                        if(event.getAction() == MotionEvent.ACTION_UP){
                            Log.v("rightUnPressed", "success");
                            //httpGetConn("stop", "0");
                            // #8494EA
                            stopView.setBackgroundColor(Color.parseColor("#FFFF0459"));
                        }
                        //
                    }
                });
                th.start();

                return true;
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