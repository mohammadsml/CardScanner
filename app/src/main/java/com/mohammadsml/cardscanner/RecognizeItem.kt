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
            source = "810180000000008275510468",
            transferType = TransferType.IBAN,
            sourceImage = Uri.parse("content://media/external/images/media/1000000043"),
            bankKey = "ic_mellat",
            sourceText =
            "IR2 10120020000009563015030 \n" +
                    "6 104338926968989 \n" +
                    "585983108294593 1 \n" +
                    "IR810180000000008275510468 \n" +
                    "09153008 183 \n" +
                    "6221064200288526 \n" +
                    "09151234713 \n",
            bankLogo = "ic_mellat".getResourceId(),
            detectType = DetectType.TEXT,

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

