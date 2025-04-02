package com.example.gameproject.Media;

import com.example.gameproject.R;

public class MediaSongs {

    private final song[] songs = {
            new song("Peaceful Recycling", R.raw.peaceful_recycling, 0),
            new song("Peaceful", R.raw.peaceful, 1),
            new song("Chill", R.raw.chill, 2),
            new song("Aquatic", R.raw.aquatic, 3),
            new song("Mystical", R.raw.mystical, 4),
            new song("Good Time", R.raw.good_time, 5),
            new song("Lament", R.raw.lament, 6),
            new song("Story", R.raw.story, 7),
            new song("Adventure Begin", R.raw.adventure_begin, 8),
            new song("Dark Castle", R.raw.dark_castle, 9),
            new song("Fight", R.raw.fight, 10),
            new song("Tension", R.raw.tension, 11),
            new song("Final Area", R.raw.final_area, 12),
            new song("Curse", R.raw.curse, 13),
            new song("End Theme", R.raw.end_theme, 14),
            new song("End Theme 2", R.raw.end_theme_2, 15),
            new song("Story (short)", R.raw.story_short, 16),
            new song("Ruins", R.raw.ruins, 17),
            new song("Village", R.raw.village, 18)
    };


    public MediaSongs() {

    }

    public int getSongLength() {
        return songs.length;
    }

    public song getSong(int id) {
        for (song s : songs) {
            if (s.id == id) {
                return s;
            }
        }
        return songs[0];
    }

    public record song(String name, int path, int id) {
    }

}
