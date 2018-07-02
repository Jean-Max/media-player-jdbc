package com.viseo.web.controller;

import com.viseo.dao.MusicDao;
import com.viseo.dao.utils.MusicDAOUtils;
import com.viseo.model.Music;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MusicsController {

    private static final Logger logger = LoggerFactory.getLogger(MusicsController.class);
    public static final String MESSAGE = "msg";
    public static final String ELEMENTS_TO_DISPLAY = "musics";
    public static final String VIEW_NAME = "presentation";

    @Autowired
    MusicDao musicDao;

    @RequestMapping(value = "/musics", method = RequestMethod.GET)
    public String getMusics(Model model) {
        List<Music> musics = musicDao.findAllMusics();
        model.addAttribute(MESSAGE, "Get ALL musics ");
        model.addAttribute(ELEMENTS_TO_DISPLAY, musics);
        return VIEW_NAME;
    }

    @RequestMapping(value = "/music", method = RequestMethod.GET)
    public String getMusicById(Model model, @RequestParam(value = "name", required = true) String name) {
        List<Music> musics = new ArrayList<>();
        Music music = musicDao.findByName(name);
        model.addAttribute(MESSAGE, "Find music by name ");
        musics.add(music);
        model.addAttribute(ELEMENTS_TO_DISPLAY, musics);
        return VIEW_NAME;
    }

    @RequestMapping(value= "/music/generate", method = RequestMethod.GET)
    public String generateMusic (Model model) {
        musicDao.generateAllMusics();
        model.addAttribute(MESSAGE, "Write ALL musics into text file : " + MusicDAOUtils.FILE_PATH);
        return VIEW_NAME;

    }
}