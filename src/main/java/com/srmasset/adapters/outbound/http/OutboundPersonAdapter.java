package com.srmasset.adapters.outbound.http;

import com.srmasset.ports.inbound.http.error.InternalErrorException;
import com.srmasset.ports.outbound.http.OutboundPersonPort;
import com.srmasset.ports.outbound.http.dto.OutboundPersonDTO;
import com.srmasset.ports.outbound.observability.MetricCollector;
import datadog.trace.api.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutboundPersonAdapter {

    @Autowired
    private OutboundPersonPort outboundPersonPort;

    @Autowired
    private MetricCollector metricCollector;

    @Trace(operationName = "OutboundPersonAdapter.findPersonById")
    public OutboundPersonDTO findPersonById(Long id) throws InternalErrorException {
        try {
            return this.outboundPersonPort.getPersonById(id);
        } catch (Exception e) {
            this.metricCollector.incrementMetric("unexpected_error_find_person_by_id");
            throw new InternalErrorException("Unexpected error finding person by id");
        }
    }
}
