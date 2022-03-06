package org.hionesoft.crudmaker.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/** Rest Api 호출 후 응답받은 ResponseEntity를 반환한다.
 * @data : 2021-03-31 오후 2:56
 * @author : hione
 * @version : 1.0.0
 **/
@Component
public class RestApiExchanger {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HttpHeaders headers;
    private final HttpComponentsClientHttpRequestFactory httpRequestFactory;


    @Autowired
    public RestApiExchanger() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        this.httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout(6000);
        httpRequestFactory.setConnectTimeout(3000);

        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(150)	// 연결을 유지할 최대숫자
                .setMaxConnPerRoute(100)	// 포트에 연결 최대숫자
                .evictIdleConnections(2000L, TimeUnit.MILLISECONDS) // 미사용 커넥션을 죽이는 간격
                .build();

        httpRequestFactory.setHttpClient(httpClient);
    }


    public ResponseEntity<String> exchage(
            String apiUrl,
            String accessToken,
            String refreshToken,
            HttpMethod httpMethod,
            String bodyStr) {

        ResponseEntity<String> apiResponse = null;

        try {
            this.headers.set("Authorization", "Bearer " + accessToken);

            UriComponents builder = UriComponentsBuilder
                    .fromHttpUrl(apiUrl).build(false);

            RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

            apiResponse = restTemplate.exchange(
                    builder.toUri(),
                    httpMethod,
                    new HttpEntity<String>(bodyStr, this.headers),
                    String.class);

            int statusCode = apiResponse.getStatusCodeValue();

            logger.info(apiUrl + " ::: response StatusCode::: " + statusCode);

        } catch (RestClientException e) {
            logger.info("[RestApiExchanger] Api 호출 과정에서 에러가 발생하였습니다.");
            return null;
        } catch (Exception e) {
            logger.info("[RestApiExchanger] Api 호출 과정에서 에러가 발생하였습니다.");
            return null;
        }

        return apiResponse;
    }
}
