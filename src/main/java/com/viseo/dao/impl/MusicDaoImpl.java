package com.viseo.dao.impl;

import com.viseo.dao.MusicDao;
import com.viseo.dao.MusicResultSetExtractorDAO;
import com.viseo.dao.MusicRowCallBackHandlerDAO;
import com.viseo.dao.MusicRowMapperDAO;
import com.viseo.dao.utils.MusicDAOUtils;
import com.viseo.model.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MusicDaoImpl implements MusicDao {

	JdbcTemplate jdbcTemplate;

	@Autowired
	private MusicRowMapperDAO musicRowMapperDAO;

	@Autowired
	private MusicRowCallBackHandlerDAO musicRowCallBackHandlerDAO;

	@Autowired
	private MusicResultSetExtractorDAO musicResultSetExtractorDAO;

	@Autowired
	public void setMusicRowMapperDAO(MusicRowMapperDAO musicRowMapperDAO){
		this.musicRowMapperDAO = musicRowMapperDAO;
	}

	@Autowired
	public void setMusicResultSetExtractorDAO(MusicResultSetExtractorDAO musicResultSetExtractorDAO) {
		this.musicResultSetExtractorDAO = musicResultSetExtractorDAO;
	}

	@Autowired
	public void setMusicRowCallBackHandlerDAO(MusicRowCallBackHandlerDAO musicRowCallBackHandlerDAO) {
		this.musicRowCallBackHandlerDAO = musicRowCallBackHandlerDAO;
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Music findByName(String name) {
		return musicRowMapperDAO.findByName(name, jdbcTemplate);
	}

	@Override
	public void generateAllMusics () {
		musicRowCallBackHandlerDAO.processAllMusics(jdbcTemplate);
	}

	@Override
	public List<Music> findAllMusics() {
		return musicResultSetExtractorDAO.findAllMusics(jdbcTemplate);
	}

}