package snn.soluciones.com.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private static final Logger HTTP_LOG = LoggerFactory.getLogger("HTTP");

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory base = new SimpleClientHttpRequestFactory();
        base.setConnectTimeout(10_000);
        base.setReadTimeout(10_000);

        ClientHttpRequestFactory buffering = new BufferingClientHttpRequestFactory(base);

        RestTemplate rt = new RestTemplate(buffering);
        rt.setInterceptors(List.of(new LoggingInterceptor()));
        rt.setErrorHandler(new BodyLoggingErrorHandler());
        return rt;
    }

    static class LoggingInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(
            org.springframework.http.HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

            String reqBody = new String(body, StandardCharsets.UTF_8);
            HTTP_LOG.debug(">>> {} {}\n>>> Headers: {}\n>>> Body: {}",
                request.getMethod(), request.getURI(), request.getHeaders(), reqBody);

            ClientHttpResponse resp = execution.execute(request, body);

            HttpStatusCode status = resp.getStatusCode();
            HttpHeaders headers = resp.getHeaders();
            String respBody = StreamUtils.copyToString(resp.getBody(), StandardCharsets.UTF_8);

            HTTP_LOG.debug("<<< {} \n<<< Headers: {}\n<<< Body: {}",
                status, headers, respBody);

            return resp;
        }
    }

    static class BodyLoggingErrorHandler extends DefaultResponseErrorHandler {
        public void handleError(ClientHttpResponse response, HttpStatusCode statusCode) throws IOException {
            String body = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
            HttpHeaders headers = response.getHeaders();

            LoggerFactory.getLogger("HTTP")
                .error("API ERROR {}. Headers: {}. Body: {}", statusCode, headers, body);

            super.handleError(response);
        }
    }
}