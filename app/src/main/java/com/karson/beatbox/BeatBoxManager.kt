package com.karson.beatbox

import android.content.res.AssetManager
import android.media.SoundPool
import java.lang.Exception

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 2

class BeatBoxManager(private val assetManager: AssetManager) {
    private val soundPool = SoundPool.Builder().setMaxStreams(MAX_SOUNDS).build()

    val sounds: List<Sound>

    init {
        sounds = loadSounds()
    }

    fun play(sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    fun release() {
        soundPool.release()
    }

    private fun loadSounds(): List<Sound> {
        val soundNames: Array<String>
        try {
            soundNames = assetManager.list(SOUNDS_FOLDER)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()
        soundNames.forEach {
            val assetPath = "$SOUNDS_FOLDER/$it"
            val sound = Sound(assetPath)
            try {
                load(sound)
                sounds.add(sound)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return sounds
    }

    private fun load(sound: Sound) {
        val afd = assetManager.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }

}