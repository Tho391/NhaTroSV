package com.example.android.nhatrosv.utils

import com.example.android.nhatrosv.views.adapter.ApartmentAdapter
import java.util.*

object CharacterTransform {
    private val charA = charArrayOf(
        'à', 'á', 'ạ', 'ả', 'ã',  // 0-&gt;16
        'â', 'ầ', 'ấ', 'ậ', 'ẩ', 'ẫ', 'ă', 'ằ', 'ắ', 'ặ', 'ẳ', 'ẵ'
    ) // a,// ă,// â

    private val charE = charArrayOf(
        'ê', 'ề', 'ế', 'ệ', 'ể', 'ễ',  // 17-&gt;27
        'è', 'é', 'ẹ', 'ẻ', 'ẽ'
    ) // e

    private val charI = charArrayOf('ì', 'í', 'ị', 'ỉ', 'ĩ') // i 28-&gt;32

    private val charO = charArrayOf(
        'ò', 'ó', 'ọ', 'ỏ', 'õ',  // o 33-&gt;49
        'ô', 'ồ', 'ố', 'ộ', 'ổ', 'ỗ',  // ô
        'ơ', 'ờ', 'ớ', 'ợ', 'ở', 'ỡ'
    ) // ơ

    private val charU = charArrayOf(
        'ù', 'ú', 'ụ', 'ủ', 'ũ',  // u 50-&gt;60
        'ư', 'ừ', 'ứ', 'ự', 'ử', 'ữ'
    ) // ư

    private val charY = charArrayOf('ỳ', 'ý', 'ỵ', 'ỷ', 'ỹ') // y 61-&gt;65

    private val charD = charArrayOf('đ', ' ') // 66-67

    var charact = String(charA, 0, charA.size) +
            String(charE, 0, charE.size) +
            String(charI, 0, charI.size) +
            String(charO, 0, charO.size) +
            String(charU, 0, charU.size) +
            String(charY, 0, charY.size) +
            String(charD, 0, charD.size)

    private fun getAlterChar(pC: Char): Char {
        if (pC.toInt() == 32) {
            return ' '
        }
        val tam = pC.toLowerCase() // Character.toLowerCase(pC);
        var i = 0
        while (i < charact.length && charact[i] != tam)
            i++
        return when (i) {
            in 0..16 -> 'a'
            in 17..27 -> 'e'
            in 28..32 -> 'i'
            in 33..49 -> 'o'
            in 50..60 -> 'u'
            in 61..65 -> 'y'
            66 -> 'd'
            else -> pC
        }
    }

    fun convertString(pStr: String): String {
        var convertString = pStr.toLowerCase(Locale.getDefault())
        for (i in convertString) {
            if (i.toInt() !in 97..122) {
                val tam1 = getAlterChar(i)
                if (i.toInt() != 32)
                    convertString = convertString.replace(i, tam1)
            }
        }
        return convertString
    }
}