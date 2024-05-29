package com.srmasset.ports.outbound.observability;

import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import com.timgroup.statsd.StatsDClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class MetricCollector {

    @Value("${spring.application.name}")
    private String applicationPrefix;

    @Value("${datadog.agent.host}")
    private String datadogAgentHost;

    @Value("${datadog.agent.port}")
    private int datadogAgentPort;

    private StatsDClient statsDClient;

    @PostConstruct
    public void init() {
        this.statsDClient = new NonBlockingStatsDClientBuilder()
                .prefix(this.applicationPrefix)
                .hostname(this.datadogAgentHost)
                .port(this.datadogAgentPort)
                .build();
    }

    public void incrementMetric(String metricName, HashMap<String, String> tags) {
        List<String> formattedTags = tags.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .toList();

        this.statsDClient.count(metricName, 1, formattedTags.toArray(new String[0]));
    }

    public void incrementMetric(String metricName) {
        this.statsDClient.count(metricName, 1);
    }
}