package com.example.rcpjt;

import android.util.Log;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestTest {

    private static final String HOST = "http://3.39.253.104/";

    public String httpConnPost() {
        String path = "test.php";
        HttpURLConnection conn = null;
        try {
            Log.v("conn", "success");
            String page = HOST + path;
            URL urls = new URL(page);
            conn = (HttpURLConnection) urls.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            if (conn != null) {
                Date now = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formatedTime = formatter.format(now);

                String jsonInputString = "{'time': "+formatedTime+", 'cmdString': 'go', 'argString':0}";

                try(OutputStream os = conn.getOutputStream()){
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                Log.v("insert", jsonInputString);
            }

            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}