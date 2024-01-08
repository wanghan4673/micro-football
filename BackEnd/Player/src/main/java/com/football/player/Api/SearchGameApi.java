package com.football.player.Api;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

public class SearchGameApi {

    private final RestTemplate restTemplate=new RestTemplate();

//    private final String api_key="bfd7a84e6c17a2010889853305a34463";
    private final String api_key="130e3aed8e5e9780537823a7db871865";
    private final String url="https://v3.football.api-sports.io/fixtures";

    /**
     * @param date 赛事日期
     * @return 赛事列表
     */
    public ResponseEntity<?> getGamesByDate(@RequestParam @Nonnull String date,
                                            @RequestParam @Nonnull Integer leagueId,
                                            @RequestParam @Nonnull Integer season){
//        String timeZone="UTC";
        System.out.println("data is "+date);
        System.out.println("leagueId is "+leagueId);
        System.out.println("season is "+season);

//        封装参数
        String targetUrl=this.url+"?date="+date+"&league="+leagueId+"&season="+season;
//        targetUrl=this.url+"?date="+date;
//        封装请求头
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("x-rapidapi-key",api_key);

        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(targetUrl, HttpMethod.GET,requestEntity,String.class);
    }



    /**
     * @param id 赛事id
     * @return 赛事列表
     */
    public ResponseEntity<?> getGameDetailById(@RequestParam @Nonnull Integer id){

//        封装参数
        String targetUrl=this.url+"?id="+id;
//        封装请求头
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("x-rapidapi-key",api_key);

        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(targetUrl, HttpMethod.GET,requestEntity,String.class);
    }


}
