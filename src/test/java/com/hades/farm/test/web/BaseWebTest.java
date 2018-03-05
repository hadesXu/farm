package com.hades.farm.test.web;

import com.hades.farm.Application;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest("server.port:0")
public class BaseWebTest {

    protected String host = "http://localhost:";

    protected RestTemplate template = new RestTemplate();

    @Value("${local.server.port}")// 注入端口号
    protected int port;

    protected String post(String url, Map<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(type);
        MultiValueMap<String, String> map = conveter(params);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        return template.postForObject(url, entity, String.class);
    }

    protected String get(String url, Map<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(type);

        MultiValueMap<String, String> valueMap = conveter(params);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(valueMap);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<String> result = template.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);
        return result.getBody();

    }

    protected MultiValueMap<String, String> conveter(Map<String, String> maps) {
        MultiValueMap<String, String> paramMaps = new LinkedMultiValueMap<String, String>();
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                paramMaps.add(entry.getKey(), entry.getValue());
            }
        }
        return paramMaps;
    }

}
