package com.example.music.controller;

import com.example.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MusicController {
    private final MusicService musicService;

    @GetMapping("/musicSearch")
    @ResponseBody
    public String musicSearch(@RequestParam("genre") String q) throws ParseException {
        String musicInfo = musicService.search(musicService.accesstoken(), q);
        return musicInfo;
        //Json Parsing해서 원하는 데이터 추출 필요.

    }

}
