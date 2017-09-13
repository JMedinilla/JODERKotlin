package ncatz.jvm.jodergenerator

import android.annotation.SuppressLint
import kotlinx.coroutines.experimental.launch
import java.util.*
import java.util.concurrent.ForkJoinPool
import kotlin.concurrent.thread

class JODER {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var main: MainActivity

        private var J = ""
        private var O = ""
        private var D = ""
        private var E = ""
        private var R = ""

        private var JODERbools: BooleanArray = booleanArrayOf(false, false, false, false, false)

        fun generate(min: Int, max: Int, mainActivity: MainActivity) {
            main = mainActivity

            JODERbools = booleanArrayOf(false, false, false, false, false)
            J = ""; O = ""; D = ""; E = ""; R = ""

            val Jcount: Int
            val Ocount: Int
            val Dcount: Int
            val Ecount: Int
            val Rcount: Int

            val length = Random().nextInt((max - min) + 1) + min
            var toUse = length - 5
            Jcount = Random().nextInt(toUse + 1)
            toUse -= Jcount
            Ocount = Random().nextInt(toUse + 1)
            toUse -= Ocount
            Dcount = Random().nextInt(toUse + 1)
            toUse -= Dcount
            Ecount = Random().nextInt(toUse + 1)
            toUse -= Ecount
            Rcount = toUse

            thread(start = true) {
                var j = 0
                while (j <= Jcount) {
                    J += "J"
                    j++
                }
                JODERbools[0] = true
                checkAllLetters()
            }

            thread(start = true) {
                var o = 0
                while (o <= Ocount) {
                    O += "O"
                    o++
                }
                JODERbools[1] = true
                checkAllLetters()
            }

            thread(start = true) {
                var d = 0
                while (d <= Dcount) {
                    D += "D"
                    d++
                }
                JODERbools[2] = true
                checkAllLetters()
            }

            thread(start = true) {
                var e = 0
                while (e <= Ecount) {
                    E += "E"
                    e++
                }
                JODERbools[3] = true
                checkAllLetters()
            }

            thread(start = true) {
                var r = 0
                while (r <= Rcount) {
                    R += "R"
                    r++
                }
                JODERbools[4] = true
                checkAllLetters()
            }
        }

        private fun checkAllLetters() {
            if (!JODERbools.contains(false)) {
                MainActivity.setJODER(J + O + D + E + R, main)
            }
        }
    }
}