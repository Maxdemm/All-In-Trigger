package com.allInTrigger.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    private static SoundManager instance;

    public static SoundManager getInstance() {
        if (instance == null) instance = new SoundManager();
        return instance;
    }

    private Sound  clickSound;
    private Sound  coinSound;
    private Sound  shootSound;
    private Music  menuMusic;
    private Music  levelMusic;

    private float masterVolume = 1.0f;
    private float musicVolume  = 0.5f;
    private float sfxVolume    = 0.8f;

    private boolean musicEnabled = true;
    private boolean sfxEnabled   = true;
    private Music   currentMusic = null;

    private SoundManager() {
        load();
    }

    private void load() {
        try {
            clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click-button.wav"));
        } catch (Exception e) {
            Gdx.app.log("SoundManager", "Cannot load click-button.wav: " + e.getMessage());
        }
        try {
            coinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"));
        } catch (Exception e) {
            Gdx.app.log("SoundManager", "Cannot load coin.wav: " + e.getMessage());
        }
        try {
            shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.wav"));
        } catch (Exception e) {
            Gdx.app.log("SoundManager", "Cannot load shoot.wav: " + e.getMessage());
        }
        try {
            menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu-lobby.mp3"));
            menuMusic.setLooping(true);
            menuMusic.setVolume(musicVolume * masterVolume);
        } catch (Exception e) {
            Gdx.app.log("SoundManager", "Cannot load menu-lobby.mp3: " + e.getMessage());
        }
        try {
            levelMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/level1.mp3"));
            levelMusic.setLooping(true);
            levelMusic.setVolume(musicVolume * masterVolume);
        } catch (Exception e) {
            Gdx.app.log("SoundManager", "Cannot load level1.mp3: " + e.getMessage());
        }
    }

    public void playClick() {
        if (sfxEnabled && clickSound != null)
            clickSound.play(sfxVolume * masterVolume);
    }

    public void playCoin() {
        if (sfxEnabled && coinSound != null)
            coinSound.play(sfxVolume * masterVolume);
    }

    public void playShoot() {
        if (sfxEnabled && shootSound != null)
            shootSound.play(sfxVolume * masterVolume * 0.6f);
    }

    public void playMenuMusic() {
        if (!musicEnabled) return;
        if (currentMusic == menuMusic && menuMusic != null && menuMusic.isPlaying()) return;
        stopMusic();
        if (menuMusic != null) {
            menuMusic.setVolume(musicVolume * masterVolume);
            menuMusic.play();
            currentMusic = menuMusic;
        }
    }

    public void playLevelMusic() {
        if (!musicEnabled) return;
        if (currentMusic == levelMusic && levelMusic != null && levelMusic.isPlaying()) return;
        stopMusic();
        if (levelMusic != null) {
            levelMusic.setVolume(musicVolume * masterVolume);
            levelMusic.play();
            currentMusic = levelMusic;
        }
    }

    public void stopMusic() {
        if (menuMusic  != null && menuMusic.isPlaying())  menuMusic.stop();
        if (levelMusic != null && levelMusic.isPlaying()) levelMusic.stop();
        currentMusic = null;
    }

    public void pauseMusic() {
        if (currentMusic != null) currentMusic.pause();
    }

    public void resumeMusic() {
        if (musicEnabled && currentMusic != null) currentMusic.play();
    }

    public void setMasterVolume(float v) {
        masterVolume = clamp(v);
        applyMusicVolume();
    }

    public void setMusicVolume(float v) {
        musicVolume = clamp(v);
        applyMusicVolume();
    }

    public void setSfxVolume(float v) {
        sfxVolume = clamp(v);
    }

    public void setMusicEnabled(boolean enabled) {
        musicEnabled = enabled;
        if (!enabled) stopMusic();
    }

    public void setSfxEnabled(boolean enabled) {
        sfxEnabled = enabled;
    }

    public float getMasterVolume() { return masterVolume; }
    public float getMusicVolume()  { return musicVolume; }
    public float getSfxVolume()    { return sfxVolume; }
    public boolean isMusicEnabled(){ return musicEnabled; }
    public boolean isSfxEnabled()  { return sfxEnabled; }

    private void applyMusicVolume() {
        float v = musicVolume * masterVolume;
        if (menuMusic  != null) menuMusic.setVolume(v);
        if (levelMusic != null) levelMusic.setVolume(v);
    }

    private float clamp(float v) {
        return Math.max(0f, Math.min(1f, v));
    }

    public void dispose() {
        stopMusic();
        if (clickSound  != null) { clickSound.dispose();  clickSound  = null; }
        if (coinSound   != null) { coinSound.dispose();   coinSound   = null; }
        if (shootSound  != null) { shootSound.dispose();  shootSound  = null; }
        if (menuMusic   != null) { menuMusic.dispose();   menuMusic   = null; }
        if (levelMusic  != null) { levelMusic.dispose();  levelMusic  = null; }
        instance = null;
    }
}
