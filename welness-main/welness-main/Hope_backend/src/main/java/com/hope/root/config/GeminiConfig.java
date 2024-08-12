package com.hope.root.config;

import com.auth0.jwt.JWT;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.util.Date;
import java.util.Map;

@Configuration
public class GeminiConfig {

    private String accessToken = null;

    @Bean
    public RestTemplate restTemplate() throws Exception {
        // Create a custom SSL context that trusts all certificates
        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustStrategy() {
                    public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        return true; // Trust all certificates
                    }
                })
                .build();

        // Configure the custom SSL context for the HttpClient
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        // Use the custom HttpClient in the HttpComponentsClientHttpRequestFactory
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);
    }



    private boolean isTokenExpired() {
        if (this.accessToken != null) {
            return JWT.decode(this.accessToken).getExpiresAt().before(new Date());
        }
        return true;
    }

    public String getAuthToken() {
        if (isTokenExpired())
            this.accessToken = null;//this.getNewAuthToken();
        return this.accessToken;
    }


}
