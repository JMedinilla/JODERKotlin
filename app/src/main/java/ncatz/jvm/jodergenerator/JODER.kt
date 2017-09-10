package ncatz.jvm.jodergenerator

import java.util.*

class JODER {
    companion object {
        fun generate(min: Int, max: Int, mainActivity: MainActivity) {
            var J = ""
            var O = ""
            var D = ""
            var E = ""
            var R = ""

            var Jbool = false
            var Obool = false
            var Dbool = false
            var Ebool = false
            var Rbool = false

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

            var j = 0
            while (j <= Jcount) {
                J += "J"
                j++
            }
            Jbool = true
            if (Jbool && Obool && Dbool && Ebool && Rbool) {
                MainActivity.setJODER(J + O + D + E + R, mainActivity)
            }

            var o = 0
            while (o <= Ocount) {
                O += "O"
                o++
            }
            Obool = true
            if (Jbool && Obool && Dbool && Ebool && Rbool) {
                MainActivity.setJODER(J + O + D + E + R, mainActivity)
            }

            var d = 0
            while (d <= Dcount) {
                D += "D"
                d++
            }
            Dbool = true
            if (Jbool && Obool && Dbool && Ebool && Rbool) {
                MainActivity.setJODER(J + O + D + E + R, mainActivity)
            }

            var e = 0
            while (e <= Ecount) {
                E += "E"
                e++
            }
            Ebool = true
            if (Jbool && Obool && Dbool && Ebool && Rbool) {
                MainActivity.setJODER(J + O + D + E + R, mainActivity)
            }

            var r = 0
            while (r <= Rcount) {
                R += "R"
                r++
            }
            Rbool = true
            if (Jbool && Obool && Dbool && Ebool && Rbool) {
                MainActivity.setJODER(J + O + D + E + R, mainActivity)
            }
        }
    }
}