package com.viseo.dao;

import com.viseo.model.Music;
import org.springframework.jdbc.core.JdbcTemplate;

public interface MusicRowMapperDAO {
    Music findByName(String id, JdbcTemplate jdbcTemplate);
}