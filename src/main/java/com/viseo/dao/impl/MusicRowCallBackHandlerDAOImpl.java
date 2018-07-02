package com.viseo.dao.impl;

import com.viseo.dao.MusicRowCallBackHandlerDAO;
import com.viseo.dao.utils.MusicDAOUtils;
import com.viseo.model.Music;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MusicRowCallBackHandlerDAOImpl implements MusicRowCallBackHandlerDAO {

    private static final Logger LOG = LoggerFactory.getLogger(MusicRowCallBackHandlerDAOImpl.class);

    // Each line/string contains a music (id,name,category)
    private static final List<String> LINES = new ArrayList();

    @Override
    public void processAllMusics(JdbcTemplate jdbcTemplate) {

        MusicDAOUtils.deleteFile();

        processAllMusicsWithLambda(jdbcTemplate);
        //processAllMusicsWithInnerClass(jdbcTemplate);
        //processAllMusicsWithOuterClass(jdbcTemplate);
        try {
            MusicDAOUtils.writeMusicToTextFile(LINES);
        } catch (IOException e) {
            MusicRowCallBackHandlerDAOImpl.LOG.error("problem while writing result to text file");
        }

    }

    class MusicReportWriter implements RowCallbackHandler {
        public void processRow(ResultSet rs) throws SQLException {
            LINES.add(
                    "ID : " + rs.getInt(MusicDAOUtils.COLUMN_ID)
                    + ", NAME : " + rs.getString(MusicDAOUtils.COLUMN_NAME)
                    + ", CATEGORY : "+ rs.getString(MusicDAOUtils.COLUMN_CATEGORY)
            );
        }
    }

    private void processAllMusicsWithOuterClass(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.query(MusicDAOUtils.SELECT_ALL_MUSIC, new MusicReportWriter());
    }

    private void processAllMusicsWithInnerClass(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.query(MusicDAOUtils.SELECT_ALL_MUSIC, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                LINES.add(
                        "ID : " + rs.getInt(MusicDAOUtils.COLUMN_ID)
                        + ", NAME : " + rs.getString(MusicDAOUtils.COLUMN_NAME)
                        + ", CATEGORY : "+ rs.getString(MusicDAOUtils.COLUMN_CATEGORY)
                );
            }
        });
    }

    private void processAllMusicsWithLambda(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.query(MusicDAOUtils.SELECT_ALL_MUSIC, new Object[]{},
                (rs) -> {
                    LINES.add(
                            rs.getInt(MusicDAOUtils.COLUMN_ID)
                            + "," + rs.getString(MusicDAOUtils.COLUMN_NAME)
                            + ","+ rs.getString(MusicDAOUtils.COLUMN_CATEGORY)
                    );
                }
        );
    }



}
