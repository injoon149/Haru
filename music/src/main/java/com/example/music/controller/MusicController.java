package com.example.music.controller;


import com.example.music.dto.MusicDto;
import com.example.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MusicController {
    private final MusicService musicService;

    public List<String> feelingList = new ArrayList<>();

    @GetMapping("/musicSearch")
    @ResponseBody
    public List<MusicDto> musicSearch(@RequestParam("genre") String q) throws ParseException {
        String musicInfo = musicService.search(musicService.accesstoken(), q);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(musicInfo);
        JSONObject trackbody = (JSONObject) jsonObject.get("tracks");
        List<MusicDto> list = new ArrayList<>();
        for(int i = 0; i< 6; i++){
            JSONArray itembody = (JSONArray) trackbody.get("items");
            JSONObject albumbody = (JSONObject) itembody.get(i);
            JSONObject album = (JSONObject)albumbody.get("album");
            JSONArray images = (JSONArray) album.get("images");
            JSONObject imageObject = (JSONObject) images.get(0);
            JSONArray artist = (JSONArray) albumbody.get("artists");
            JSONObject artistbody = (JSONObject) artist.get(0);
            MusicDto dto = new MusicDto();
            dto.setArtist(artistbody.get("name").toString());
            dto.setTitle(albumbody.get("name").toString());
            dto.setUrl(imageObject.get("url").toString());
            list.add(dto);
        }
        return list;
    }

    @GetMapping("/addList")
    public List<String> addList(String feeling){
        if(feelingList.size() == 10){
            feelingList.remove(feelingList.get(0));
            feelingList.add(feeling);
        }
        else feelingList.add(feeling);
        return feelingList;
    }

    @GetMapping("/deleteList")
    public String deleteList(){
        feelingList.clear();
        return "success";
    }

    @GetMapping("/RecentFeeling")
    public List<String> RecentFeeling(){
        List<String> newList = new ArrayList<>();
        newList.add(musicService.RecentFeeling(feelingList));
        return newList;
    }





}




