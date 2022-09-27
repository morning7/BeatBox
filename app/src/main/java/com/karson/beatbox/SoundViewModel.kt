package com.karson.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel(private var beatBoxManager: BeatBoxManager): BaseObservable() {

    var sound: Sound? = null
        set(sound) {
            field = sound
            notifyChange()
        }

    @get:Bindable
    val title: String?
        get() = sound?.name

    fun onButtonClick() {
        sound?.let {
            beatBoxManager.play(it)
        }
    }



}