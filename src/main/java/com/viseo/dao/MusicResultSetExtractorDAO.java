package com.viseo.dao;

import com.viseo.model.Music;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface MusicResultSetExtractorDAO {
    List<Music> findAllMusics(JdbcTemplate jdbcTemplate);
}
