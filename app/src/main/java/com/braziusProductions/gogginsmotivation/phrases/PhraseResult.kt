package com.braziusProductions.gogginsmotivation.phrases

import com.braziusProductions.gogginsmotivation.PhraseData

class PhraseResult {
    internal var share: ((PhraseData) -> Unit)? = null
    internal var alarm: ((PhraseData) -> Unit)? = null
    internal var ringTone: ((PhraseData) -> Unit)? = null
    internal var play: ((PhraseData) -> Unit)? = null

    fun onShare(callback: (PhraseData) -> Unit) {
        share = callback
    }
    fun onPlay(callback: (PhraseData) -> Unit) {
        play = callback
    }
    fun onAlarmSet(callback: (PhraseData) -> Unit) {
        alarm = callback
    }

    fun onRingtoneSet(callback: (PhraseData) -> Unit) {
        ringTone = callback
    }
}
