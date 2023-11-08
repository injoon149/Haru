package com.example.music.controller;


import com.example.music.dto.MusicDto;
import com.example.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MusicController {
    private final MusicService musicService;

    @GetMapping("/musicSearch")
    @ResponseBody
    public List<MusicDto> musicSearch(@RequestParam("genre") String q) throws ParseException {
        String musicInfo = musicService.search(musicService.accesstoken(), q);
        //Json Parsing해서 원하는 데이터 추출 필요.
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(musicInfo);
        JSONObject trackbody = (JSONObject) jsonObject.get("tracks");
        System.out.println("trackbody = " + trackbody);
        List<MusicDto> list = new ArrayList<>();
        for(int i = 0; i< 4; i++){
            //JSONObject trackbody = (JSONObject) track.get(i);
            JSONArray itembody = (JSONArray) trackbody.get("items");
            JSONObject albumbody = (JSONObject) itembody.get(i);
            System.out.println("albumbody = " + albumbody);

            JSONArray artist = (JSONArray) albumbody.get("artists");
            JSONObject artistbody = (JSONObject) artist.get(0);
            MusicDto dto = new MusicDto();
            dto.setArtist(artistbody.get("name").toString());
            dto.setTitle(albumbody.get("name").toString());
//            dto.setYear(albumbody.get("release_date").toString());
            list.add(dto);
        }
        return list;
    }


}




