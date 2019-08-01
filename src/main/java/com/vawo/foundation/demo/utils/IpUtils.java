package com.vawo.foundation.demo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class IpUtils {
    public static String publicip() {
        URL url = null;
        URLConnection urlconn = null;
        BufferedReader br = null;
        try {
            url = new URL("http://2019.ip138.com/ic.asp");//爬取的网站是百度搜索ip时排名第一的那个
            urlconn = url.openConnection();
            br = new BufferedReader(new InputStreamReader(
                    urlconn.getInputStream()));
            String buf = null;
            String get = null;
            while ((buf = br.readLine()) != null) {
                get += buf;
            }
            int where, end;
            for (where = 0; where < get.length() && get.charAt(where) != '['; where++) ;
            for (end = where; end < get.length() && get.charAt(end) != ']'; end++) ;
            get = get.substring(where + 1, end);
            return get;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args){
        System.out.println(IpUtils.publicip());
    }
}
