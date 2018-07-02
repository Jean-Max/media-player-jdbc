package com.viseo.dao;

import com.viseo.dao.impl.MusicDaoImpl;
import com.viseo.dao.impl.MusicResultSetExtractorDAOImpl;
import com.viseo.dao.impl.MusicRowCallBackHandlerDAOImpl;
import com.viseo.dao.impl.MusicRowMapperDAOImpl;
import com.viseo.dao.utils.MusicDAOUtils;
import com.viseo.dao.utils.MusicTypes;
import com.viseo.model.Music;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MusicDaoTest {

    private EmbeddedDatabase db;

    MusicDaoImpl musicDao;
	JdbcTemplate jdbcTemplate;
	MusicRowMapperDAO musicRowMapperDAO;
	MusicRowCallBackHandlerDAO musicRowCallBackHandlerDAO;
	MusicResultSetExtractorDAO musicResultSetExtractorDAO;

    @Before
    public void setUp() {
		// la version xml est présente dans db-hsql-config.xml
    	db = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.HSQL)
    		.addScript(MusicDAOUtils.SQL_SCRIPT_CREATE_DB_PATH)
    		.addScript(MusicDAOUtils.SQL_SCRIPT_INSERT_DATA_PATH)
    		.build();

		jdbcTemplate = new JdbcTemplate(db);
		musicRowMapperDAO = new MusicRowMapperDAOImpl();
		musicRowCallBackHandlerDAO = new MusicRowCallBackHandlerDAOImpl();
		musicResultSetExtractorDAO = new MusicResultSetExtractorDAOImpl();

		musicDao = new MusicDaoImpl();
		musicDao.setJdbcTemplate(jdbcTemplate);
		musicDao.setMusicRowMapperDAO(musicRowMapperDAO);
		musicDao.setMusicResultSetExtractorDAO(musicResultSetExtractorDAO);
		musicDao.setMusicRowCallBackHandlerDAO(musicRowCallBackHandlerDAO);
    }

	@After
	public void tearDown() {
		db.shutdown();
	}

	@Test
	public void findMusicByName() {
		Music result = musicDao.findByName(MusicDAOUtils.U2);

		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.getId());
		Assert.assertEquals(MusicDAOUtils.U2, result.getName());
		Assert.assertEquals(MusicTypes.ROCK.toString(), result.getCategory());
	}

    @Test
    public void findAllMusics() {
		List<Music> actualMusics = musicDao.findAllMusics();
		List <Music> expectedMusics = getExpectedMusics();
		compareMusics(actualMusics, expectedMusics);
	}

	@Test
	public void generateAllMusics() {

		musicDao.generateAllMusics();

		try {
			List <String> lines = Files.readAllLines(Paths.get(MusicDAOUtils.FILE_PATH));

			List <Music> actualMusics = getActualMusics(lines);
			List <Music> expectedMusics = getExpectedMusics();
			compareMusics(actualMusics, expectedMusics);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<Music> getActualMusics (List <String> lines) {
		List<Music> actualMusics = new ArrayList<>();
    	for (String line : lines) {
    		String [] values = line.split(",");
			actualMusics.add(new Music(Integer.parseInt(values[0]), values[1], values[2]));
		}
    	return actualMusics;
	}

    private List<Music> getExpectedMusics () {
		List<Music> musics = new ArrayList<>();
		musics.add(new Music(MusicDAOUtils.COLUMN_ID_U2, MusicDAOUtils.U2, MusicTypes.ROCK.toString()));
		musics.add(new Music(MusicDAOUtils.COLUMN_ID_TEXAS, MusicDAOUtils.TEXAS, MusicTypes.POPROCK.toString()));
		musics.add(new Music(MusicDAOUtils.COLUMN_ID_OASIS, MusicDAOUtils.OASIS, MusicTypes.POP.toString()));
		return musics;
	}

	private void compareMusics (List <Music> expected, List <Music> actuals) {
    	for (int i = 0; i < expected.size(); i++) {
			Assert.assertEquals(expected.get(i), actuals.get(i));
		}
	}

}