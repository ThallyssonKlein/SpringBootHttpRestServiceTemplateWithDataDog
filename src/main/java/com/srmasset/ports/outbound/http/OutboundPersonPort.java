package com.srmasset.ports.outbound.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srmasset.ports.outbound.http.dto.OutboundPersonDTO;
import com.srmasset.ports.outbound.http.error.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;

@Component
public class OutboundPersonPort {

    @Value("${ports.outbound.http.person.url}")
    private String personApiBaseURL;

    @Autowired
    private HttpClientSingleton httpClientSingleton;

    @Autowired
    private ObjectMapper objectMapper;

    public OutboundPersonDTO getPersonById(Long id) throws IOException, InterruptedException, HttpException {
        var request = httpClientSingleton.createRequest(personApiBaseURL + "/person/" + id);

        try {
            HttpResponse<String> response = httpClientSingleton.getInstance().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 400) {
                throw new HttpException(response.statusCode(), "HTTP error occurred");
            }
            return objectMapper.readValue(response.body(), OutboundPersonDTO.class);
        } catch (HttpException e) {
            if (e.getStatusCode() == 404) {
                return null;
            }
            throw e;
        }
    }
}