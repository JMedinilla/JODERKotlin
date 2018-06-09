package ncatz.jvm.jodergenerator

import org.greenrobot.eventbus.EventBus
import java.util.*

class JODER {

    fun generate(min: Int, max: Int) {
        Thread(Runnable {
            val length = Random().nextInt((max - min) + 1) + min
            var toUse = length - 5

            val jCount = Random().nextInt(toUse)
            toUse -= jCount
            val oCount = Random().nextInt(toUse)
            toUse -= oCount
            val dCount = Random().nextInt(toUse)
            toUse -= dCount
            val eCount = Random().nextInt(toUse)
            toUse -= eCount
            val rCount = toUse

            val jChars = CharArray(jCount + 1)
            val oChars = CharArray(oCount + 1)
            val dChars = CharArray(dCount + 1)
            val eChars = CharArray(eCount + 1)
            val rChars = CharArray(rCount + 1)

            Arrays.fill(jChars, 'J')
            Arrays.fill(oChars, 'O')
            Arrays.fill(dChars, 'D')
            Arrays.fill(eChars, 'E')
            Arrays.fill(rChars, 'R')

            val j = String(jChars)
            val o = String(oChars)
            val d = String(dChars)
            val e = String(eChars)
            val r = String(rChars)

            checkAllLetters(j, o, d, e, r)
        }).start()
    }

    private fun checkAllLetters(j: String, o: String, d: String, e: String, r: String) {
        val joderFull = j + o + d + e + r
        EventBus.getDefault().post(JoderEvent(joderFull))
    }

    class JoderEvent(joder: String) {
        var joderGenerated: String = joder
    }
}