package com.webapp.websiteportal.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.webapp.websiteportal.dto.GeolocationResponse;
import com.webapp.websiteportal.exception.GeolocationException;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GeolocationServiceImpl implements GeolocationService {

    @Value("${geo.api.url}")
    private String apiUrl;

    @Value("${geo.api.key}")
    private String apiKey;

//    @Override
//    @Async
//    public CompletableFuture<GeolocationResponse> getGeolocation(String ip) {
//        val future = new CompletableFuture<GeolocationResponse>();
//
//        try {
//            // Validate IP address
//            InetAddress.getByName(ip);
//
//            log.info("Getting geolocation for IP: {}", ip);
//
//            // Call geolocation API
//            val url = String.format("%s/%s/?token=%s", apiUrl, ip, apiKey);
//            val response = new RestTemplate()
//                    .getForObject(url, GeolocationResponse.class);
//
//            if (response == null) {
//                log.error("Failed to get geolocation for IP: {}", ip);
//                future.completeExceptionally(new GeolocationException(
//                        "Failed to get geolocation for IP: " + ip));
//            } else {
//                future.complete(response);
//            }
//
//        } catch (UnknownHostException e) {
//            log.error("Invalid IP address: {}", ip, e);
//            future.completeExceptionally(e);
//
//        } catch (RestClientException e) {
//            log.error("Failed to get geolocation for IP: {}", ip, e);
//            future.completeExceptionally(e);
//        }
//
//        return future;
//    }
    
    @Override
    @Async
    public CompletableFuture<GeolocationResponse> getGeolocation(String ip) {
        val future = new CompletableFuture<GeolocationResponse>();

        try {
            // Handle loopback address
            if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
                log.warn("IP address is loopback. Using default geolocation.");
                GeolocationResponse defaultResponse = new GeolocationResponse();
                GeolocationResponse.City defaultCity = new GeolocationResponse.City();
                defaultCity.setNames(Map.of("en", "Localhost"));
                defaultResponse.setCity(defaultCity);
                GeolocationResponse.Country defaultCountry = new GeolocationResponse.Country();
                defaultCountry.setNames(Map.of("en", "Local Network"));
                defaultResponse.setCountry(defaultCountry);
                future.complete(defaultResponse);
                return future;
            }

            // Validate IP address
            InetAddress.getByName(ip);

            log.info("Getting geolocation for IP: {}", ip);

            // Call geolocation API
            val url = String.format("%s/%s/?token=%s", apiUrl, ip, apiKey);
            val response = new RestTemplate()
                    .getForObject(url, GeolocationResponse.class);

            if (response == null) {
                log.error("Failed to get geolocation for IP: {}", ip);
                future.completeExceptionally(new GeolocationException(
                        "Failed to get geolocation for IP: " + ip));
            } else {
                future.complete(response);
            }

        } catch (UnknownHostException e) {
            log.error("Invalid IP address: {}", ip, e);
            future.completeExceptionally(e);

        } catch (RestClientException e) {
            log.error("Failed to get geolocation for IP: {}", ip, e);
            future.completeExceptionally(e);
        }

        return future;
    }


}
