package com.football.player.Api;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

public class SearchSquadsApi {

    private final RestTemplate restTemplate=new RestTemplate();
    private final String api_key="130e3aed8e5e9780537823a7db871865";
    private final String url="https://v3.football.api-sports.io/players/squads";

    /**
     * @param id 球队ID
     * @return 球队的球员列表
     */
    public ResponseEntity<?> getTeamMembersById(@RequestParam @Nonnull Integer id){

//        封装参数
        String targetUrl=this.url+"?team="+id.toString();

//        封装请求头
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("x-rapidapi-key",api_key);


        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(targetUrl, HttpMethod.GET,requestEntity,String.class);
    }


}
