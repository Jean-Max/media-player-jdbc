package com.viseo.dao;

import com.viseo.model.Music;

import java.util.List;

public interface MusicDao {

	Music findByName(String name);

	List<Music> findAllMusics();

	void generateAllMusics ();
}