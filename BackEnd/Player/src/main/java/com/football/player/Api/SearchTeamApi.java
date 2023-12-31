package com.football.player.Api;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class SearchTeamApi {

    private HashMap<String,Integer> gameTypeMap=new HashMap<>(){
        {
            put("英超", 59);
            put("西甲", 107);
            put("意甲",71);
            put("德甲",78);
            put("法甲",61);
        }
    };
    private final RestTemplate restTemplate=new RestTemplate();
    private final String api_key="130e3aed8e5e9780537823a7db871865";
    private final String url="https://v3.football.api-sports.io/teams";

    /**
     *
     * @param id 球队ID
     * @return 接口返回的String对象->球队基本信息
     */
    public ResponseEntity<?> getResponseEntityById(@RequestParam @Nonnull Integer id){

//        封装参数
        String targetUrl=this.url+"?id="+id.toString();

//        封装请求头
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("x-rapidapi-key",api_key);


        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(targetUrl, HttpMethod.GET,requestEntity,String.class);
    }

    /**
     *
     * @param searchKey 球队关键字。匹配名称或国家名
     * @return 接口返回的String对象->球队基本信息
     */
    public ResponseEntity<?> getResponseEntityByKey(@RequestParam @Nonnull String searchKey){

//        封装参数
        String targetUrl=this.url+"?search="+searchKey;

//        封装请求头
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("x-rapidapi-key",api_key);


        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(targetUrl, HttpMethod.GET,requestEntity,String.class);
    }






}
