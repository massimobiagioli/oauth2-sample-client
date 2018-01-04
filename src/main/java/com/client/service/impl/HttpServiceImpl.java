package com.client.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.client.domain.Patient;
import com.client.service.HttpService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HttpServiceImpl implements HttpService{

    String credential = "Basic "+ Base64.getEncoder().encodeToString("acme:acmesecret".getBytes());

    public JSONObject getToken(String authCode) throws IOException, JSONException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://localhost:8081/oauth/token");

        httpPost.setHeader("Authorization", credential);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("client_id", "acme"));
        params.add(new BasicNameValuePair("redirect_uri", "http://127.0.0.1:8080/authCode"));
        params.add(new BasicNameValuePair("code", authCode));
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = client.execute(httpPost);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

        JSONObject jsonObject = new JSONObject(result.toString());

        client.close();

        return jsonObject;
    }

    public List<Patient> getResource(String token) throws IOException, JSONException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("http://localhost:8082/resource/patient/all");
        httpGet.setHeader("Authorization", "bearer "+token);
        CloseableHttpResponse response = client.execute(httpGet);

        return responseParser(response);

    }


    public List<Patient> responseParser(CloseableHttpResponse response) throws IOException, JSONException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        List<Patient> patientList=new ArrayList<>();

        JSONArray jsonarray = new JSONArray(result.toString());

        ObjectMapper mapper = new ObjectMapper();

        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            Patient patient = mapper.readValue(jsonobject.toString(), Patient.class);
            patientList.add(patient);
        }

        return patientList;

    }

    public String getPublicInfo() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("http://localhost:8082/public/resource");

        CloseableHttpResponse response = client.execute(httpGet);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

}
