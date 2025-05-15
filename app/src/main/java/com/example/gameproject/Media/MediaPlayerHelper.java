package com.example.gameproject.Media;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MediaPlayerHelper {
    private final MediaSongs mSongs;
    private final Context context;
    private final ExecutorService executorService;
    private MediaPlayer mPlayer;
    private MediaPlayer EffectPlayer;
    private int currentSongId;
    private float leftVolume = 1.0f;
    private float rightVolume = 1.0f;


    public MediaPlayerHelper(Context context) {
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
                mPlayer.setVolume(leftVolume, rightVolume);

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

    private void fadeIn(final MediaPlayer player, final float targetVolume, int durationMs) {
        if (player == null) {
            Log.e("MPHelper", "MediaPlayer is null during fadeIn");
            return;
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        final int steps = 20;
        final long delay = durationMs / steps;
        final float delta = targetVolume / steps;

        try {
            player.setVolume(0f, 0f);
        } catch (IllegalStateException e) {
            Log.e("MPHelper", "MediaPlayer is in an invalid state during fadeIn", e);
            return;
        }

        try {
            for (int i = 1; i <= steps; i++) {
                final float volume = delta * i;
                handler.postDelayed(() -> {
                    try {
                        if (player.isPlaying()) {
                            player.setVolume(volume, volume);
                        }
                    } catch (IllegalStateException e) {
                        Log.e("MPHelper", "MediaPlayer is in an invalid state during fadeIn", e);
                    }
                }, delay * i);
            }
        }catch (Exception ignored){}
    }

    private void fadeOut(final MediaPlayer player, int durationMs, Runnable onComplete) {
        if (player == null) {
            Log.e("MPHelper", "MediaPlayer is null during fadeOut");
            return;
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        final int steps = 20;
        final long delay = durationMs / steps;
        final float[] currentVolume = {leftVolume};

        try{
        for (int i = 0; i <= steps; i++) {
            final float volume = currentVolume[0] - (currentVolume[0] / steps) * i;
            handler.postDelayed(() -> {
                try {
                    if (player.isPlaying()) {
                        player.setVolume(volume, volume);
                    }
                } catch (IllegalStateException e) {
                    Log.e("MPHelper", "MediaPlayer is in an invalid state during fadeOut", e);
                }
                if (volume <= 0.01f) {
                    try {
                        player.stop();
                        player.release();
                        if (onComplete != null) onComplete.run();
                    } catch (IllegalStateException e) {
                        Log.e("MPHelper", "Error stopping or releasing MediaPlayer during fadeOut", e);
                    }
                }
            }, delay * i);
        }
        }catch (Exception ignored){}
    }


    public void setVolume(float left, float right) {
        leftVolume = left;
        rightVolume = right;
        try {
            if (mPlayer != null && mPlayer.isPlaying()) mPlayer.setVolume(left, right);
            if (EffectPlayer != null && EffectPlayer.isPlaying()) EffectPlayer.setVolume(left, right);
        } catch (Exception e) {
            Log.e("MPHelper", "Error setting volume", e);
        }
    }


    public void play() {
        if (mPlayer != null) {
            mPlayer.start();
        }
    }

    public void stop() {
        if (mPlayer != null)
            fadeOut(mPlayer, 500, () -> mPlayer = null);
    }


    public void playNextSong() {
        playSong(context, (currentSongId + 1) % mSongs.getSongLength());
    }

    public void playPreviousSong() {
        playSong(context, (currentSongId - 1 + mSongs.getSongLength()) % mSongs.getSongLength());
    }

   private void playSong(Context context, int idSong) {
        if (mPlayer != null && mPlayer.isPlaying()) {
            fadeOut(mPlayer, 500, () -> {
                currentSongId = idSong;
                mPlayer = MediaPlayer.create(context, mSongs.getSong(currentSongId).path());
                if (mPlayer != null) {
                    mPlayer.setOnCompletionListener(mp -> playNextSong());
                    mPlayer.start();
                    fadeIn(mPlayer, leftVolume, 500);
                } else {
                    Log.e("MPHelper", "Failed to create MediaPlayer for song: " + idSong);
                }
            });
        } else {
            currentSongId = idSong;
            mPlayer = MediaPlayer.create(context, mSongs.getSong(currentSongId).path());
            if (mPlayer != null) {
                mPlayer.setOnCompletionListener(mp -> playNextSong());
                mPlayer.start();
                fadeIn(mPlayer, leftVolume, 500);
            } else {
                Log.e("MPHelper", "Failed to create MediaPlayer for song: " + idSong);
            }
        }
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
        if (mPlayer != null && mPlayer.isPlaying()) mPlayer.pause();

        if (EffectPlayer != null) EffectPlayer.release();
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
