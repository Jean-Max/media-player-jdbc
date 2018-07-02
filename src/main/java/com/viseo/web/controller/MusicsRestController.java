package com.viseo.web.controller;

import com.viseo.dao.MusicDao;
import com.viseo.dao.utils.MusicDAOUtils;
import com.viseo.model.Music;
import com.viseo.model.Musics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/rest")
public class MusicsRestController {

    private static final Logger logger = LoggerFactory.getLogger(MusicsRestController.class);
    public static final String MESSAGE = "msg";
    public static final String ELEMENTS_TO_DISPLAY = "musics";
    public static final String VIEW_NAME = "presentation";

    private Musics response = new Musics();

    @Autowired
    MusicDao musicDao;

    @GetMapping(value = "/musics", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
    public ResponseEntity<Musics> getMusics() {
        response.setMusics(musicDao.findAllMusics());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/music", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
    public ResponseEntity<Musics> getMusicById(@RequestParam(value = "name", required = true) String name) {
        List<Music> listMusics = new ArrayList<>();
        listMusics.add(musicDao.findByName(name));
        response.setMusics(listMusics);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value= "/music/generate", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
    public ResponseEntity.BodyBuilder generateMusic () {
        musicDao.generateAllMusics();
        return ResponseEntity.ok();
    }
}