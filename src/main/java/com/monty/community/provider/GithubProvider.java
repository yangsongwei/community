package com.monty.community.provider;

import com.alibaba.fastjson.JSON;
import com.monty.community.dto.AccessTokenDTO;
import com.monty.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String url="https://github.com/login/oauth/access_token";
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        try (Response response = client.newCall(request).execute()) {
            String middleResult=response.body().string();
            String result=middleResult.split("&")[0].split("=")[1];
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getGithubUser(String accesstoken){
        OkHttpClient client = new OkHttpClient();
        String url="https://api.github.com/user?access_token="+accesstoken;
        Request request = new Request.Builder()
                    .url(url)
                    .build();

        try (Response response = client.newCall(request).execute()) {
            GithubUser githubUser = JSON.parseObject(response.body().string(), GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
