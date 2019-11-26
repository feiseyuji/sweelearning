package com.womenhz.swee.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public TransportClient esClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "my-application")
//                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", true)
                .build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.157.7"), 9300));

        return client;
    }


}
