package com.viseo.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface MusicRowCallBackHandlerDAO {
    void processAllMusics(JdbcTemplate jdbcTemplate);
}
