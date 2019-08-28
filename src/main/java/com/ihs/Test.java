package com.ihs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class Test {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println("###### date: " + date);
        date = DateUtils.setHours(date, 23);
        date = DateUtils.setMinutes(date, 59);
        date = DateUtils.setSeconds(date, 0);
        System.out.println("###### date hour: " + date);
    }

    public static String sendSMS(String mobileNumber, String totalDonatedAmount, String receiptNumber) {

        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        String result = null;
        try {

            StringBuffer messageTextSb = new StringBuffer();
            messageTextSb.append("شكرا لتبرعكم لهيئة الأعمال الخيرية")
                .append("\n")
                .append("تم استلام مبلغ")
                .append(" ")
                .append(totalDonatedAmount)
                .append(" ")
                .append("درهم")
                .append("\n")
                .append("رقم الإيصال ")
                .append(receiptNumber);
            String messageText = URLEncoder.encode(messageTextSb.toString(), "UTF-8");
            URL url = new URL("http://getway.uaedes.ae/sendurlcomma.aspx?user=20080361&pwd=6bkgg4&senderid=HumanAppeal&mobileno=" + mobileNumber + "&msgtext=" + messageText + "&priority=High&CountryCode=All");
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000 * 30);
            connection.setReadTimeout(1000 * 30);
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.connect();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line)
                    .append("\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            if (bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }

}
