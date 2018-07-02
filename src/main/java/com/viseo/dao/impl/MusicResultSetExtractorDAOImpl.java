package com.viseo.dao.impl;

import com.viseo.dao.MusicResultSetExtractorDAO;
import com.viseo.dao.utils.MusicDAOUtils;
import com.viseo.model.Music;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MusicResultSetExtractorDAOImpl implements MusicResultSetExtractorDAO {

    private static final Logger log = LoggerFactory.getLogger(MusicResultSetExtractorDAOImpl.class);

    @Override
    public List<Music> findAllMusics(JdbcTemplate jdbcTemplate) {
        return findAllMusicsWithInnerClass(jdbcTemplate);
    }

    private List<Music> findAllMusicsWithInnerClass(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.query(MusicDAOUtils.SELECT_ALL_MUSIC, new ResultSetExtractor<List<Music>>() {
            @Override
            public List<Music> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Music> list = new ArrayList<>();
                while (rs.next()) {                    ;
                    list.add(new Music(rs.getInt(MusicDAOUtils.COLUMN_ID), rs.getString(MusicDAOUtils.COLUMN_NAME), rs.getString(MusicDAOUtils.COLUMN_CATEGORY)));
                }
                return list;
            }
        });
    }

    private List<Music> findAllMusicsWithLambda(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.query(MusicDAOUtils.SELECT_ALL_MUSIC,
                (rs) -> {
                    List<Music> list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(new Music(rs.getInt(MusicDAOUtils.COLUMN_ID), rs.getString(MusicDAOUtils.COLUMN_NAME), rs.getString(MusicDAOUtils.COLUMN_CATEGORY)));
                    }
                    return list;
                }
        );
    }
}
