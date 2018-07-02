package com.viseo.dao.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MusicDAOUtils {

    public static final String U2 = "U2";
    public static final String TEXAS = "TEXAS";
    public static final String OASIS = "OASIS";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_CATEGORY = "CATEGORY";

    public static final int COLUMN_ID_U2 = 1;
    public static final int COLUMN_ID_TEXAS = 2;
    public static final int COLUMN_ID_OASIS = 3;

    public static final String GET_MUSIC_BY_NAME = "SELECT * FROM MUSICS WHERE NAME = ?";
    public static final String SELECT_ALL_MUSIC = "SELECT * FROM MUSICS";

    public static final String FILE_PATH = "src/main/resources/media.txt";

    public static final String SQL_SCRIPT_CREATE_DB_PATH = "db/sql/create-db.sql";
    public static final String SQL_SCRIPT_INSERT_DATA_PATH = "db/sql/insert-data.sql";


    /**
     * Method that writes from database result each line into a text file
     * @param lines
     */
    public static void writeMusicToTextFile(List<String> lines) throws IOException {
        Path path = Paths.get(FILE_PATH);
        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    public static void deleteFile() {
        File file = new File(MusicDAOUtils.FILE_PATH);
        if (file.delete()) {
            System.out.println(file.getName() + " is deleted!");
        } else {
            System.out.println("Delete operation is failed.");
        }

    }

}
