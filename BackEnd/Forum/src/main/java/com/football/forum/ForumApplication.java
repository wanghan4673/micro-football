package com.football.forum;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.football.common.config.UserConfig;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.football.mfapi")
@Import(UserConfig.class)
public class ForumApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForumApplication.class, args);
    }
    @Bean
    public ElasticsearchClient client(){
        RestClient restClient = RestClient.builder(new HttpHost("43.142.145.176",9200)
        ).build();
        ElasticsearchTransport transport = new RestClientTransport(
                restClient,new JacksonJsonpMapper()
        );
        return new ElasticsearchClient(transport);
    }
}
