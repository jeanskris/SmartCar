package com.SCC.SmartCar.model;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param uurl
     *            发送请求的URL
     * @param obj
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static JSONObject sendGet(String uurl, Object obj) {
        JSONObject jsonObject=null;
        try {
            // 创建url
            URL url = new URL(uurl);
            // 使用httpurlconnection 进行请求的发送
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            // 设置相关属性,如果是post请求 请求参数在正文中所以允许输出流，如果是get        请求不用输出了因为参数是在url之后

            con.setDoOutput(true);

            // 响应数据的接受，所以我们要允许输入流

            con.setDoInput(true);

            // 设置是否要缓存

            con.setUseCaches(false);

             //Java对象的序列化，进行流的传递

            //con.setRequestProperty(URLEncoder.encode("Content-type","UTF-8"),URLEncoder.encode("application/x-java-serialized-object","UTF-8"));

            // 设置请求方式

            con.setRequestMethod("GET");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type", "application/json");

            //上面这些都是请求的一些属性的配置，也就是规则的定制

            //建立一个tcp连接
            con.connect();
            //读入数据
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
            jsonObject=new JSONObject(response.toString());
            return jsonObject;

        }catch(Exception e) {
            e.printStackTrace();
            return jsonObject;
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param obj
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static JSONObject sendPost(String url, Object obj) {
        JSONObject jsonObject=null;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpsURLConnection conn = (HttpsURLConnection) realUrl.openConnection();

            // 设置通用的请求属性
            //add reuqest header
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(obj);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            jsonObject=new JSONObject(result);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return  jsonObject;
    }
}