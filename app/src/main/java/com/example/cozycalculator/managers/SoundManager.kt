package com.example.cozycalculator.managers

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import com.example.cozycalculator.R

class SoundManager(context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var soundPool: SoundPool? = null
    private var clickSoundId: Int = 0

    init {
        // Configuración para efectos cortos de baja latencia
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(audioAttributes)
            .build()

        // Cargar efecto de clic de botón
        clickSoundId = soundPool?.load(context, R.raw.btn_sound, 1) ?: 0

        // Cargar música de fondo
        mediaPlayer = MediaPlayer.create(context, R.raw.bg_music).apply {
            isLooping = true
            setVolume(0.20f, 0.20f) // Volumen tenue (20%)
        }
    }


    private var lastStreamId: Int = 0

    fun playClick(isSoundEnabled: Boolean) {
        if (isSoundEnabled && clickSoundId != 0) {
            soundPool?.play(clickSoundId, 0.3f, 0.3f, 0, 0, 1f)
            if (lastStreamId != 0) {
                soundPool?.stop(lastStreamId)
            }
            lastStreamId = soundPool?.play(clickSoundId, 0.3f, 0.3f, 0, 0, 1f) ?: 0
        }
    }

    fun toggleMusic(isSoundEnabled: Boolean) {
        if (isSoundEnabled) {
            if (mediaPlayer?.isPlaying == false) {
                mediaPlayer?.start()
            }
        } else {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        }
    }

    fun release() {
        soundPool?.release()
        soundPool = null
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}