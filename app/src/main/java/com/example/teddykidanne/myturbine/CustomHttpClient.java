package com.example.teddykidanne.myturbine;

/**
 * Created by Teddykidanne on 7/22/16.
 *
 */

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URI;

public class CustomHttpClient {

    public static final int HTTP_TIMEOUT = 20 * 1000; // milliseconds

    private static HttpClient mHttpClient;
    static HttpResponse response;



    private static HttpClient getHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();

            final HttpParams params = mHttpClient.getParams();

            ClientConnectionManager mgr = mHttpClient.getConnectionManager();


            mHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(params,
                    mgr.getSchemeRegistry()), params);

            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
        }
        return mHttpClient;
    }

    public static String executeHttpGet(String url) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        } catch (ConnectTimeoutException e) {
            return "ConnectTimeOut";
        } catch (SocketTimeoutException e) {
            return 0 + "";
        } catch (IOException e) {
            return "TimeOut";
        } finally {

            if (in != null) {
                try {
                    in.close();
                    //	entity.consumeContent();
                } catch (IOException e) {
                    return "finalxception";
                }
            }
        }
    }
}
