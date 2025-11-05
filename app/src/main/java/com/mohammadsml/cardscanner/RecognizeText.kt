package com.mohammadsml.cardscanner

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException
import java.util.regex.Pattern
import org.iban4j.Iban
import org.iban4j.IbanFormat
import org.iban4j.IbanUtil


class RecognizeText() {
    private var items: ArrayList<RecognizeItem> = arrayListOf()

    fun handleSendText(intent: Intent, onFindText: (List<RecognizeItem>) -> Unit) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let { text ->
            val cardItems: List<String>? = checkPan(text)
            val ibanItems: List<String>? = checkIban(text)
            val phoneItems: List<String>? = checkPhoneNumber(text)
            cardItems?.forEach { item ->
                items.add(
                    RecognizeItem(
                        source = item,
                        transferType = RecognizeItem.TransferType.CARD,
                        sourceImage = Uri.EMPTY,
                        sourceText = text,
                        detectType = RecognizeItem.DetectType.TEXT,
                        bankLogo = item.substring(0, 6).getBankKey().getResourceId(),
                        bankKey = item.substring(0, 6).getBankKey(),
                        colorLight = "" ,
                        colorDark = "",
                        name = "",
                    )
                )
            }
            ibanItems?.forEach { item ->
                items.add(
                    RecognizeItem(
                        source = item,
                        transferType = RecognizeItem.TransferType.IBAN,
                        sourceImage = Uri.EMPTY,
                        sourceText = text,
                        detectType = RecognizeItem.DetectType.TEXT,
                        bankLogo = "ic_iban".getResourceId(),
                        bankKey = "ic_iban",
                        colorLight = "" ,
                        colorDark = "",
                        name = "",
                    )
                )
            }
            phoneItems?.forEach { item ->
                items.add(
                    RecognizeItem(
                        source = item,
                        transferType = RecognizeItem.TransferType.PHONE,
                        sourceImage = Uri.EMPTY,
                        sourceText = text,
                        detectType = RecognizeItem.DetectType.TEXT,
                        bankLogo = "".getResourceId(),
                        bankKey = "",
                        colorLight = "" ,
                        colorDark = "",
                        name = "",
                    )
                )
            }
            onFindText(items)
        }
    }

    fun handleSendImage(
        intent: Intent,
        context: Context,
        onFindText: (List<RecognizeItem>) -> Unit
    ) {
        (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
            val image = try {
                InputImage.fromFilePath(context, it)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
            if (image != null) {
                imageProcess(image, onFindText = { text ->
                    val cardItems: List<String>? = checkPan(text)
                    val ibanItems: List<String>? = checkIban(text)
                    val phoneItems: List<String>? = checkPhoneNumber(text)
                    cardItems?.forEach { item ->
                        items.add(
                            RecognizeItem(
                                source = item,
                                transferType = RecognizeItem.TransferType.CARD,
                                sourceImage = it,
                                sourceText = text,
                                detectType = RecognizeItem.DetectType.IMAGE,
                                bankLogo = item.substring(0, 6).getBankKey().getResourceId(),
                                bankKey = item.substring(0, 6).getBankKey(),
                                colorLight = "" ,
                                colorDark = "",
                                name = "",
                            )
                        )
                    }
                    ibanItems?.forEach { item ->
                        items.add(
                            RecognizeItem(
                                source = item,
                                transferType = RecognizeItem.TransferType.IBAN,
                                sourceImage = it,
                                sourceText = text,
                                detectType = RecognizeItem.DetectType.IMAGE,
                                bankLogo = "ic_iban".getResourceId(),
                                bankKey = "ic_iban",
                                colorLight = "" ,
                                colorDark = "",
                                name = "",
                            )
                        )
                    }
                    phoneItems?.forEach { item ->
                        items.add(
                            RecognizeItem(
                                source = item,
                                transferType = RecognizeItem.TransferType.PHONE,
                                sourceImage = it,
                                sourceText = text,
                                detectType = RecognizeItem.DetectType.IMAGE,
                                bankLogo = "ic_iban".getResourceId(),
                                bankKey = "ic_iban",
                                colorLight = "" ,
                                colorDark = "",
                                name = "",
                            )
                        )
                    }
                    onFindText(items)
                }
                )
            }
        }
    }

    private fun imageProcess(image: InputImage, onFindText: (String) -> Unit) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                onFindText(visionText.text)
            }
            .addOnFailureListener { e ->
                onFindText(e.message.toString())
            }
    }



    private fun String.getBankKey(isWhite: Boolean = false): String {
        return if (this.isNotEmpty()) {
            val name = when (this) {
                "585983",
                "627353" -> "ic_tejarat"

                "502938" -> "ic_dey"
                "502806" -> "ic_city"
                "627412" -> "ic_eghtesadenovin"
                "505416" -> "ic_gardeshgari"
                "505785" -> "ic_iranzamin"
                "ic_khavaremiyane" -> "ic_khavaremiyane"
                "628023" -> "ic_maskan"
                "603799" -> "ic_meli"
                "622106",
                "627884",
                "639194" -> "ic_parsian"

                "627760" -> "ic_post"
                "603769" -> "ic_saderat"
                "621986" -> "ic_saman"
                "627961" -> "ic_sanatmadan"
                "639607" -> "ic_sarmaye"
                "639346" -> "ic_sina"
                "ic_ayandeh" -> "ic_ayandeh"
                "502229",
                "639347" -> "ic_pasargad"

                "589463" -> "ic_refah"
                "502910",
                "627488" -> "ic_karafarin"

                "603770",
                "639217" -> "ic_keshavarzi"

                "606373" -> "ic_mehreiran"
                "610433",
                "991975" -> "ic_mellat"

                "504172" -> "ic_resalat"
                "589210" -> "ic_sepah"
                "628157",
                "502908" -> "ic_tosee"

                "207177",
                "627648" -> "ic_toseesaderat"

                else -> "ic_tejarat"
            }

            name
        } else {
            "ic_tejarat"
        }
    }

    private fun checkPan(message: String): List<String>? {
        var processedMessage = message
            .replace("\n", "")
            .replace("\r", "")
            .replace(" ", "")
            .replace("b", "0")
            .replace("p", "0")
            .replace("q", "0")
            .replace("d", "0")
            .replace(Regex("[^\\d-]"), "")
            .replace(Regex("[A-Z]"), "")

        processedMessage = convertP2EDigits(processedMessage)
        val result = mutableListOf<String>()
        val patterns = listOf(
            "((585983)([0-9]{10}))",
            "((585647)([0-9]{10}))",
            "((585947)([0-9]{10}))",
            "((502938)([0-9]{10}))",
            "((505416)([0-9]{10}))",
            "((627381)([0-9]{10}))",
            "((606373)([0-9]{10}))",
            "((502806)([0-9]{10}))",
            "((504706)([0-9]{10}))",
            "((621986)([0-9]{10}))",
            "((502910)([0-9]{10}))",
            "((627488)([0-9]{10}))",
            "((639599)([0-9]{10}))",
            "((502229)([0-9]{10}))",
            "((639347)([0-9]{10}))",
            "((639607)([0-9]{10}))",
            "((628157)([0-9]{10}))",
            "((639346)([0-9]{10}))",
            "((622106)([0-9]{10}))",
            "((627884)([0-9]{10}))",
            "((639194)([0-9]{10}))",
            "((636795)([0-9]{10}))",
            "((502908)([0-9]{10}))",
            "((627760)([0-9]{10}))",
            "((207177)([0-9]{10}))",
            "((627648)([0-9]{10}))",
            "((603769)([0-9]{10}))",
            "((627353)([0-9]{10}))",
            "((603799)([0-9]{10}))",
            "((603770)([0-9]{10}))",
            "((639217)([0-9]{10}))",
            "((589210)([0-9]{10}))",
            "((627961)([0-9]{10}))",
            "((589463)([0-9]{10}))",
            "((628023)([0-9]{10}))",
            "((610433)([0-9]{10}))",
            "((991975)([0-9]{10}))",
            "((505785)([0-9]{10}))",
            "((627412)([0-9]{10}))",
            "((636214)([0-9]{10}))",
            "((636949)([0-9]{10}))",
            "((505801)([0-9]{10}))",
            "((504172)([0-9]{10}))"
        )

        for (pattern in patterns) {
            val compiledPattern = Pattern.compile(pattern, Pattern.MULTILINE)
            val matcher = compiledPattern.matcher(processedMessage)
            while (matcher.find()) {
                if (matcher.group(1).isValidCard()){
                    result.add(matcher.group(1))
                }
            }
        }
        return if (result.isEmpty()) null else result
    }

    fun checkIban(message: String): List<String>? {
        var cleanedMessage = message
            .replace("\n", "")
            .replace("\r", "")
            .replace(" ", "")
            .replace("b", "0")
            .replace("p", "0")
            .replace("q", "0")
            .replace("d", "0")
            .replace(":", "")

        cleanedMessage = convertP2EDigits(cleanedMessage)
        val result = mutableListOf<String>()
        val patterns = listOf(
            "((IR|ir)([0-9]{2})(078)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(066)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(064)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(063)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(060)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(061)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(056)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(053)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(052)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(057)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(058)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(051)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(059)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(054)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(010)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(022)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(021)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(020)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(018)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(017)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(015)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(011)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(013)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(014)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(012)([0-9]{19}))",
            "((IR|ir)([0-9]{2})(070)([0-9]{19}))",
            /*"(([0-9]{2})(078)([0-9]{19}))",
            "(([0-9]{2})(066)([0-9]{19}))",
            "(([0-9]{2})(064)([0-9]{19}))",
            "(([0-9]{2})(063)([0-9]{19}))",
            "(([0-9]{2})(060)([0-9]{19}))",
            "(([0-9]{2})(061)([0-9]{19}))",
            "(([0-9]{2})(056)([0-9]{19}))",
            "(([0-9]{2})(053)([0-9]{19}))",
            "(([0-9]{2})(052)([0-9]{19}))",
            "(([0-9]{2})(057)([0-9]{19}))",
            "(([0-9]{2})(058)([0-9]{19}))",
            "(([0-9]{2})(051)([0-9]{19}))",
            "(([0-9]{2})(059)([0-9]{19}))",
            "(([0-9]{2})(054)([0-9]{19}))",
            "(([0-9]{2})(010)([0-9]{19}))",
            "(([0-9]{2})(022)([0-9]{19}))",
            "(([0-9]{2})(021)([0-9]{19}))",
            "(([0-9]{2})(020)([0-9]{19}))",
            "(([0-9]{2})(018)([0-9]{19}))",
            "(([0-9]{2})(017)([0-9]{19}))",
            "(([0-9]{2})(015)([0-9]{19}))",
            "(([0-9]{2})(011)([0-9]{19}))",
            "(([0-9]{2})(013)([0-9]{19}))",
            "(([0-9]{2})(014)([0-9]{19}))",
            "(([0-9]{2})(012)([0-9]{19}))",
            "(([0-9]{2})(070)([0-9]{19}))"*/
        )

        for (pattern in patterns) {
            val compiledPattern = Pattern.compile(pattern, Pattern.MULTILINE)
            val matcher = compiledPattern.matcher(cleanedMessage)
            while (matcher.find()) {
                if (matcher.group(1).isIban()) {
                    val textResult = matcher.group(1)
                    result.add(textResult)
                }
            }
        }
        return if (result.isEmpty()) null else result
    }

    private fun checkPhoneNumber(message: String): List<String>? {
        var modifiedMessage = message.replace(" ", "")

        modifiedMessage = convertP2EDigits(modifiedMessage)
        val result = mutableListOf<String>()
        val patterns = listOf(
            "(?<!\\d)((989)([0-9]{9}))(?!\\d)",
            "(?<!\\d)((09)([0-9]{9}))(?!\\d)"
        )

        for (pattern in patterns) {
            val compiledPattern = Pattern.compile(pattern, Pattern.MULTILINE)
            val matcher = compiledPattern.matcher(modifiedMessage)
            while (matcher.find()) {
                var textResult = matcher.group(1)
                if (textResult.startsWith("989")) {
                    textResult = "0" + textResult.substring(2)
                    result.add(textResult)
                } else if (textResult.startsWith("09")) {
                    result.add(textResult)
                }
            }
        }
        return if (result.isEmpty()) null else result
    }

    private fun convertP2EDigits(digits: String): String {
        return digits
            // For persian digits
            .replace("۰", "0")
            .replace("۱", "1")
            .replace("۲", "2")
            .replace("۳", "3")
            .replace("۴", "4")
            .replace("۵", "5")
            .replace("۶", "6")
            .replace("۷", "7")
            .replace("۸", "8")
            .replace("۹", "9")
            // For arabic digits
            .replace("٠", "0")
            .replace("١", "1")
            .replace("٢", "2")
            .replace("٣", "3")
            .replace("٤", "4")
            .replace("٥", "5")
            .replace("٦", "6")
            .replace("٧", "7")
            .replace("٨", "8")
            .replace("٩", "9")
    }

}
fun String.getResourceId(isWhite: Boolean = false): Int {
    return if (this.isNotEmpty()) {
        val resourceId = when (this) {
            "ic_tejarat" -> if (isWhite) R.drawable.ic_tejarat_white else R.drawable.ic_tejarat
            "ic_dey" -> if (isWhite) R.drawable.ic_dey_white else R.drawable.ic_dey
            "ic_city" -> if (isWhite) R.drawable.ic_city_white else R.drawable.ic_city
            "ic_eghtesadenovin" -> if (isWhite) R.drawable.ic_eghtesadenovin_white else R.drawable.ic_eghtesadenovin
            "ic_gardeshgari" -> if (isWhite) R.drawable.ic_gardeshgari_white else R.drawable.ic_gardeshgari
            "ic_iranzamin" -> if (isWhite) R.drawable.ic_iranzamin_white else R.drawable.ic_iranzamin
            "ic_khavaremiyane" -> if (isWhite) R.drawable.ic_khavaremiyane_white else R.drawable.ic_khavaremiyane
            "ic_maskan" -> if (isWhite) R.drawable.ic_maskan_white else R.drawable.ic_maskan
            "ic_meli" -> if (isWhite) R.drawable.ic_meli_white else R.drawable.ic_meli
            "ic_parsian" -> if (isWhite) R.drawable.ic_parsian_white else R.drawable.ic_parsian
            "ic_post" -> if (isWhite) R.drawable.ic_post_white else R.drawable.ic_post
            "ic_saderat" -> if (isWhite) R.drawable.ic_saderat_white else R.drawable.ic_saderat
            "ic_saman" -> if (isWhite) R.drawable.ic_saman_white else R.drawable.ic_saman
            "ic_sanatmadan" -> if (isWhite) R.drawable.ic_sanatmadan_white else R.drawable.ic_sanatmadan
            "ic_sarmaye" -> if (isWhite) R.drawable.ic_sarmaye_white else R.drawable.ic_sarmaye
            "ic_sina" -> if (isWhite) R.drawable.ic_sina_white else R.drawable.ic_sina
            "ic_ayandeh" -> if (isWhite) R.drawable.ic_ayandeh_white else R.drawable.ic_ayandeh
            "ic_pasargad" -> if (isWhite) R.drawable.ic_pasargad_white else R.drawable.ic_pasargad
            "ic_refah" -> if (isWhite) R.drawable.ic_refah_white else R.drawable.ic_refah
            "ic_karafarin" -> if (isWhite) R.drawable.ic_karafarin_white else R.drawable.ic_karafarin
            "ic_keshavarzi" -> if (isWhite) R.drawable.ic_keshavarzi_white else R.drawable.ic_keshavarzi
            "ic_mehreiran" -> if (isWhite) R.drawable.ic_mehreiran_white else R.drawable.ic_mehreiran
            "ic_mellat" -> if (isWhite) R.drawable.ic_mellat_white else R.drawable.ic_mellat
            "ic_resalat" -> if (isWhite) R.drawable.ic_resalat_white else R.drawable.ic_resalat
            "ic_sepah" -> if (isWhite) R.drawable.ic_sepah_white else R.drawable.ic_sepah
            "ic_tosee" -> if (isWhite) R.drawable.ic_tosee_white else R.drawable.ic_tosee
            "ic_toseesaderat" -> if (isWhite) R.drawable.ic_toseesaderat_white else R.drawable.ic_toseesaderat
            "ic_iban" -> if (isWhite) R.drawable.ic_iban else R.drawable.ic_iban
            else -> if (isWhite) R.drawable.ic_iban else R.drawable.ic_iban
        }

        resourceId
    } else {
        if (isWhite) R.drawable.ic_tejarat_white else R.drawable.ic_tejarat
    }
}

fun String.panFormatter(space: Int = 1, isDigitOnly: Boolean = true): String {
    val blockLengths = intArrayOf(4, 4, 4, 4)
    var unFormattedPan: String
    if (this.isEmpty()) return ""
    val unFormattedSeq: String = if (isDigitOnly) this.replace("[^0-9]".toRegex(), "") else this
    if (unFormattedSeq.isEmpty()) {
        return ""
    }
    unFormattedPan = unFormattedSeq
    if (unFormattedPan.length > 16) {
        unFormattedPan = unFormattedSeq.substring(0, 16)
    }
    val formatted = StringBuilder()
    formatted.append("\u200E")
    var blockIndex = 0
    var currentBlock = 0
    for (element in unFormattedPan) {
        if (currentBlock == blockLengths[blockIndex]) {
            repeat((0..space).count()) {
                formatted.append(" ")
            }
            currentBlock = 0
            blockIndex++
        }
        formatted.append(element)
        currentBlock++
    }
    return formatted.toString()
}

fun String.isValidCard(): Boolean {
    return if (this.length == 16) {
        var sum = 0
        var isOdd = true
        for (char in this.toCharArray()) {
            val digit: Int = try {
                char.toString().toInt()
            } catch (e: NumberFormatException) {
                return false
            }
            sum += if (isOdd) (if (digit * 2 > 9) digit * 2 - 9 else digit * 2) else digit
            isOdd = !isOdd
        }
        sum % 10 == 0
    } else {
        false
    }
}

fun String.isIban(): Boolean {
    return try {

        IbanUtil.validate(this.uppercase())
        true
    } catch (exception: Exception) {
        false
    }
}

fun String.ibanFormatter(): String {
    return try {
        val iban = Iban.valueOf(this, IbanFormat.None)
        iban.toFormattedString()
    } catch (exception: Exception) {
        ""
    }
}
