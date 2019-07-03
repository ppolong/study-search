package com.test.search.common;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.AIMDBackoffManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultBackoffStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Configuration
public class AppConfig{

    @Bean
    public RestTemplateCustomizer restTemplateCustomizer(HttpClient httpClient) {
        return restTemplate -> {
            final ClientHttpRequestFactory requestFactory = restTemplate.getRequestFactory();
            if (requestFactory instanceof HttpComponentsClientHttpRequestFactory) {
                final HttpComponentsClientHttpRequestFactory factory = (HttpComponentsClientHttpRequestFactory) requestFactory;
                factory.setConnectTimeout(3000);
                factory.setReadTimeout(5000);
                factory.setHttpClient(httpClient);
            }
        };
    }


    @Bean(destroyMethod = "close")
    public CloseableHttpClient httpClient() {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(10);
        connManager.setDefaultMaxPerRoute(5);
        return HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .setBackoffManager(new AIMDBackoffManager(connManager))
                .setConnectionBackoffStrategy(new DefaultBackoffStrategy())
                .build();
    }

}
