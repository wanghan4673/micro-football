package com.football.player.Api;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

public class SearchPlayerApi {
    private final RestTemplate restTemplate=new RestTemplate();
    private final String api_key="130e3aed8e5e9780537823a7db871865";
    private final String url="https://v3.football.api-sports.io/players";



    /**
     *
     * @param id 球员ID
     * @param season 赛季id
     * @return 接口返回的String对象->选定赛季信息
     */
    public ResponseEntity<?> getPlayerInfoByIdAndSeason(@RequestParam @Nonnull Integer id,
                                                   @RequestParam @Nonnull Integer season){

//        封装参数
        String targetUrl=this.url+"?id="+id.toString()+"&season="+season.toString();

//        封装请求头
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("x-rapidapi-key",api_key);


        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(targetUrl, HttpMethod.GET,requestEntity,String.class);
    }

    /**
     * @param team_id 所属球队ID
     * @param season 所选赛季
     * @return 接口返回的String对象->球员列表
     */
    public ResponseEntity<?> getPlayersByTeamId(@RequestParam @Nonnull Integer team_id,
                                                       @RequestParam @Nonnull Integer season){

//        封装参数
        String targetUrl=this.url+"?team="+team_id.toString()+"&season="+season.toString();

//        封装请求头
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("x-rapidapi-key",api_key);


        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(targetUrl, HttpMethod.GET,requestEntity,String.class);
    }

    /**
     *
     * @param searchKey 球员关键字。匹配球员名称.len>=4
     * @param league_id 联赛id
     * @return 接口返回的String对象->球员列表
     */
    public ResponseEntity<?> getPlayersByKeyAndLeague(@RequestParam @Nonnull String searchKey,
                                                    @RequestParam @Nonnull Integer league_id){

//        封装参数
        String targetUrl=this.url+"?search="+searchKey+"&league="+league_id.toString();

//        封装请求头
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("x-rapidapi-key",api_key);


        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(targetUrl, HttpMethod.GET,requestEntity,String.class);
    }




}
