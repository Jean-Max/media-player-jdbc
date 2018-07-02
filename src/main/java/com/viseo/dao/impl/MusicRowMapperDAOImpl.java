package com.viseo.dao.impl;

import com.viseo.dao.MusicRowMapperDAO;
import com.viseo.dao.utils.MusicDAOUtils;
import com.viseo.model.Music;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MusicRowMapperDAOImpl implements MusicRowMapperDAO {

    private static final Logger log = LoggerFactory.getLogger(MusicRowMapperDAOImpl.class);

    @Override
    public Music findByName(String id, JdbcTemplate jdbcTemplate) {
        return findByNameWithOuterClass(id, jdbcTemplate);
    }

    class MusicMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            return new Music(rs.getInt(MusicDAOUtils.COLUMN_ID), rs.getString(MusicDAOUtils.COLUMN_NAME), rs.getString(MusicDAOUtils.COLUMN_CATEGORY));
        }
    }

    private Music findByNameWithOuterClass(String id, JdbcTemplate jdbcTemplate) {
        Music result = (Music) jdbcTemplate.queryForObject(MusicDAOUtils.GET_MUSIC_BY_NAME, new Object[]{id}, new MusicMapper());
        return result;
    }

    private Music findByNameWithInnerClass(String name, JdbcTemplate jdbcTemplate) {
        Music result = jdbcTemplate.queryForObject(MusicDAOUtils.GET_MUSIC_BY_NAME, new Object[]{name}, new RowMapper<Music>() {
            @Override
            public Music mapRow(ResultSet rs, int i) throws SQLException {
                return new Music(rs.getInt(MusicDAOUtils.COLUMN_ID), rs.getString(MusicDAOUtils.COLUMN_NAME), rs.getString(MusicDAOUtils.COLUMN_CATEGORY));
            }
        });
        return result;
    }

    private Music findByNameWithLambda(String name, JdbcTemplate jdbcTemplate) {
        Music result = jdbcTemplate.queryForObject(
                MusicDAOUtils.GET_MUSIC_BY_NAME, new Object[] {name},
                (rs, rowNum) -> {
                    return new Music(rs.getInt(MusicDAOUtils.COLUMN_ID), rs.getString(MusicDAOUtils.COLUMN_NAME), rs.getString(MusicDAOUtils.COLUMN_CATEGORY));
                }
        );
        return result;
    }

}