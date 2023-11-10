package com.example.music.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class MusicService {
    private static final String CLIENT_ID = "d56a0b4be00a4e59b44bee7955e72dad";
    private static final String CLIENT_SECRET = "fb6b2ba7b83f4d5f9d53eb6c78a3d857";
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET).build();

    public static String accesstoken() {
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            return spotifyApi.getAccessToken();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return "error";
        }
    }

    public String search(String accessToken, String q) { //q는 검색어

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        ;
        headers.add("Host", "api.spotify.com");
        headers.add("Content-type", "application/json");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://api.spotify.com/v1/search?year=2023&type=track&market=KR&limit=10&q=" + q, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value(); //상태 코드가 들어갈 status 변수
        String response = responseEntity.getBody();

        return response;
    }
    public String RecentFeeling(List<String> feeling){
        double feelNum = 1;
        System.out.println(feeling.size());
        for(int i = 0; i<feeling.size(); i++)
        {
            if(feeling.get(i).equals("happy"))
            {
                feelNum *= 2;
            }
            else if(Objects.equals(feeling.get(i), "sad")) feelNum *= 0.3;
            else if(Objects.equals(feeling.get(i), "tired")) feelNum *= 0.5;
            else if(Objects.equals(feeling.get(i), "peaceful")) feelNum *= 1;
            else if(Objects.equals(feeling.get(i), "excited")) feelNum *= 3;
            else if(Objects.equals(feeling.get(i), "angry")) feelNum *= 0.2;
        }
        double logValue = Math.log(feelNum);
        if(logValue <0.0)
        {
            return "sad";
        }
        else return "happy";
    }
}



