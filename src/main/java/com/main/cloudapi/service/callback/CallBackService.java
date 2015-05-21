package com.main.cloudapi.service.callback;

import com.main.cloudapi.entity.CallBackResult;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

/**
 * Created by mirxak on 20.05.15.
 */
@Service
public class CallBackService {

    public String get(String url, Map<String, String> headers){
        CallBackResult callbackResult;
        try {
            callbackResult = execResult(url, null, HttpMethod.GET, headers);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error. GET. Message: " + e.getMessage());
        }

        if (callbackResult.getStatus() != 200){
            throw new RuntimeException("Error. GET. Status: " + String.valueOf(callbackResult.getStatus()));
        }

        return callbackResult.getBody();
    }

    public String post(String url, String body, Map<String, String> headers){
        CallBackResult callbackResult;
        try {
            callbackResult = execResult(url, body, HttpMethod.POST, headers);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error. POST. Message: " + e.getMessage());
        }

        if (callbackResult.getStatus() != 200){
            throw new RuntimeException("Error. POST. Status: " + String.valueOf(callbackResult.getStatus()));
        }

        return callbackResult.getBody();
    }

    public CallBackResult execResult(String url, String body, HttpMethod httpMethod, Map<String, String> headers) throws IOException {
        if ((url == null) || (url.isEmpty())){
            throw new RuntimeException("Url is empty");
        }

        URL urlObj = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlObj.openConnection();
        if ((headers != null) && (!headers.isEmpty())){
            for (Map.Entry<String, String> entry : headers.entrySet()){
                httpURLConnection.addRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        try {
            httpURLConnection.setRequestMethod(httpMethod.toString());
        } catch (ProtocolException e) {
            e.printStackTrace();
            throw new RuntimeException("Unknown protocol");
        }



        if ((httpMethod.equals(HttpMethod.POST)) && (body != null) && (!body.isEmpty())){
            httpURLConnection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            outputStream.writeBytes(body);
            outputStream.flush();
            outputStream.close();
        }

        CallBackResult callbackResult = new CallBackResult();
        callbackResult.setStatus(httpURLConnection.getResponseCode());
        callbackResult.setBody(getStringFromStream(httpURLConnection.getInputStream()));

        return callbackResult;
    }


    private String getStringFromStream(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuffer response = new StringBuffer();
        try {
            while((inputLine = bufferedReader.readLine()) != null){
                response.append(inputLine);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading response from server");
        }
        return response.toString();
    }

}
