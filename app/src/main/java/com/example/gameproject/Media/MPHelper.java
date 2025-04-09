package com.example.gameproject.Media;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MPHelper {
    private final MediaSongs mSongs;
    private final Context context;
    private final ExecutorService executorService;
    private MediaPlayer mPlayer;
    private MediaPlayer EffectPlayer;
    private int currentSongId;


    public MPHelper(Context context) {
        this.context = context;
        mSongs = new MediaSongs();
        currentSongId = 0;
        mPlayer = MediaPlayer.create(context, mSongs.getSong(currentSongId).path());
        this.executorService = Executors.newSingleThreadExecutor();


    }

    public void initializeMediaPlayerAsync(final MediaPlayerReadyCallback callback) {
        executorService.execute(() -> {
            try {
                mPlayer = MediaPlayer.create(context, mSongs.getSong(currentSongId).path());
                if (context instanceof Activity) {
                    ((Activity) context).runOnUiThread(() -> {
                        if (mPlayer != null) {
                            mPlayer.setOnCompletionListener(mp -> playNextSong());
                            callback.onMediaPlayerReady();
                        } else {
                            Log.e("MPHelper", "Failed to initialize MediaPlayer");
                        }
                    });
                }
            } catch (Exception e) {
                Log.e("MPHelper", "Error initializing MediaPlayer", e);
            }
        });
    }

    public void setVolume(float leftVolume, float rightVolume) {
        try {
            if (mPlayer != null && mPlayer.isPlaying()) {
                mPlayer.setVolume(leftVolume, rightVolume);
                EffectPlayer.setVolume(leftVolume, rightVolume);
            }
        } catch (IllegalStateException e) {
            Log.e("MPHelper", "Error setting volume: MediaPlayer is in an invalid state", e);
        }
    }

    public void play() {
        if (mPlayer != null) {
            mPlayer.start();
        }
    }

    public void stop() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying())
                mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void playNextSong() {
        playSong(context, (currentSongId + 1) % mSongs.getSongLength());
    }

    public void playPreviousSong() {
        playSong(context, (currentSongId - 1 + mSongs.getSongLength()) % mSongs.getSongLength());
    }

    private void playSong(Context context, int idSong) {
        stop();
        Log.d("MPHelper", "playNextSong: " + currentSongId + " name: " + mSongs.getSong(currentSongId).name());

        currentSongId = idSong;

        mPlayer = MediaPlayer.create(context, mSongs.getSong(currentSongId).path());
        play();

        mPlayer.setOnCompletionListener(mp -> playNextSong());
    }

        public void playPickItemSound() {
            if (EffectPlayer != null) {
                EffectPlayer.release();
            }
            EffectPlayer = MediaPlayer.create(context, mSongs.getCoinSound().path());
            EffectPlayer.setVolume(1.0f, 1.0f);
            EffectPlayer.setOnCompletionListener(mp -> {
                EffectPlayer.release();
                EffectPlayer = null;
            });
            EffectPlayer.start();
        }

    public void playGameOverSound() {
        if (mPlayer != null && mPlayer.isPlaying())
            mPlayer.pause();

        if (EffectPlayer != null)
            EffectPlayer.release();
        EffectPlayer = MediaPlayer.create(context, mSongs.getGameOverSound().path());
        EffectPlayer.setOnCompletionListener(mp -> {
            EffectPlayer.release();
            EffectPlayer = null;
        });
        EffectPlayer.start();
    }

    public MediaSongs.song getCurrSong() {
        return mSongs.getSong(currentSongId);
    }

    public interface MediaPlayerReadyCallback {
        void onMediaPlayerReady();
    }

}
