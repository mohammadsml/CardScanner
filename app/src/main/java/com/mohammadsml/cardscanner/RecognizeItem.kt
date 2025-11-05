package com.mohammadsml.cardscanner


import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecognizeItem(
    val name: String,
    val colorLight: String,
    val colorDark: String,
    val source: String,
    val transferType: TransferType,
    val sourceImage: Uri?,
    val sourceText: String,
    val bankLogo: Int,
    val bankKey: String?,
    val detectType: DetectType,
) : Parcelable {

    companion object {
        fun init() = RecognizeItem(
            name = "",
            colorLight = "",
            colorDark = "",
            source = "6104330000008989",
            transferType = TransferType.CARD,
            sourceImage = Uri.parse(""),
            bankKey = "ic_mellat",
            sourceText = "",
            bankLogo = "ic_mellat".getResourceId(),
            detectType = DetectType.TEXT,
        )

        fun test() = listOf(
            RecognizeItem(
                name = "",
                colorLight = "",
                colorDark = "",
                source = "6104330000008989",
                transferType = TransferType.CARD,
                sourceImage = Uri.parse(""),
                bankKey = "ic_mellat",
                sourceText = "",
                bankLogo = "ic_mellat".getResourceId(),
                detectType = DetectType.TEXT,
            ),
            RecognizeItem(
                name = "",
                colorLight = "",
                colorDark = "",
                source = "6219860000003881",
                transferType = TransferType.CARD,
                sourceImage = Uri.parse(""),
                bankKey = "ic_saman",
                sourceText = "",
                bankLogo = "ic_saman".getResourceId(),
                detectType = DetectType.TEXT,
            ),
            RecognizeItem(
                name = "",
                colorLight = "",
                colorDark = "",
                source = "IR210120020000000000015030",
                transferType = TransferType.IBAN,
                sourceImage = Uri.parse(""),
                bankKey = "ic_iban",
                sourceText = "",
                bankLogo = "ic_iban".getResourceId(),
                detectType = DetectType.TEXT,
            ),
            RecognizeItem(
                name = "",
                colorLight = "",
                colorDark = "",
                source = "6393470000003830",
                transferType = TransferType.CARD,
                sourceImage = Uri.parse(""),
                bankKey = "ic_pasargad",
                sourceText = "",
                bankLogo = "ic_pasargad".getResourceId(),
                detectType = DetectType.TEXT,
            ),
        )
    }

    enum class TransferType {
        CARD,
        IBAN,
        PHONE,
    }

    enum class DetectType {
        IMAGE,
        TEXT
    }
}

