package com.srmasset.ports.outbound.http;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

@Component
public class HttpClientSingleton {

    @Value("${ports.outbound.http.connect-timeout}")
    private long connectTimeout;

    @Value("${ports.outbound.http.read-timeout}")
    private long readTimeout;

    private HttpClient httpClient;

    @PostConstruct
    public void init() {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofMillis(this.connectTimeout))
                .build();
    }

    public HttpClient getInstance() {
        return this.httpClient;
    }

    public HttpRequest createRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMillis(this.readTimeout))
                .GET()
                .build();
    }
}