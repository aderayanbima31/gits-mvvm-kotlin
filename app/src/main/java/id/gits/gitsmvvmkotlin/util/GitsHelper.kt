package id.co.gits.gitsdriver.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.telephony.PhoneNumberUtils
import android.text.TextUtils
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.gits.gitsmvvmkotlin.R
import id.gits.gitsmvvmkotlin.util.StringEncryptionTools
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class GitsHelper {

    object View {
        fun setCustomFont(view: android.view.View, fontName: String): Typeface = Typeface
                .createFromAsset(view.context.assets, "fonts/$fontName")

        fun showSnackbarDefault(view: android.view.View, message: String, duration: Int) {
            val finalMessage = if (TextUtils.isEmpty(message)) {
                GitsHelper.Const.SERVER_ERROR_MESSAGE_DEFAULT
            } else {
                message
            }

            val finalDuration = if (duration == 0) {
                GitsHelper.Const.SNACKBAR_TIMER_SHOWING_DEFAULT
            } else {
                duration
            }

            Snackbar.make(view, finalMessage, finalDuration).show()
        }

        fun showSnackbarWithCustomColor(view: android.view.View, message: String,
                                        textColor: Int, backgroundColor: Int,
                                        duration: Int) {
            val finalMessage = if (TextUtils.isEmpty(message)) {
                GitsHelper.Const.SERVER_ERROR_MESSAGE_DEFAULT
            } else {
                message
            }

            val finalDuration = if (duration == 0) {
                GitsHelper.Const.SNACKBAR_TIMER_SHOWING_DEFAULT
            } else {
                duration
            }

            val finalTextColor = if (textColor == 0) {
                ContextCompat.getColor(view.context, R.color.mainWhite)
            } else {
                textColor
            }

            val finalBackgroundColor = if (textColor == 0) {
                ContextCompat.getColor(view.context, R.color.greyBackgroundDefault)
            } else {
                backgroundColor
            }

            val snackView = Snackbar.make(view, finalMessage, finalDuration)
            snackView.setActionTextColor(finalTextColor)
            snackView.view.setBackgroundColor(finalBackgroundColor)
            snackView.show()
        }

        fun showToast(context: Context, message: String) {
            Toast.makeText(context, if (TextUtils.isEmpty(message))
                GitsHelper.Const.SERVER_ERROR_MESSAGE_DEFAULT else message, Toast.LENGTH_SHORT).show()
        }
    }

    object Func {
        fun isNetworkAvailable(context: Context): Boolean? {
            var isConnected: Boolean? = false // Initial Value
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected) isConnected = true
            return isConnected
        }

        fun TAG(nameTag: String) = nameTag

        fun encryptionText(message: String) = StringEncryptionTools().encryptText(message)

        fun decryptionText(encryptionText: String) = StringEncryptionTools().decryptText(encryptionText)

        fun saveBitmapToLocalFile(context: Context, imageBitmap: Bitmap, directoryName: String?) {
            val root = Environment.getExternalStorageDirectory().toString()

            val directoryNameDefault = if (TextUtils.isEmpty(directoryName)) {
                GitsHelper.Const.APP_FOLDER_DEFAULT
            } else {
                directoryName
            }

            val myDir = File("$root/$directoryNameDefault")

            if (!myDir.exists()) {
                myDir.mkdirs()
            }

            val random = Random()
            val numbers = 100
            val numberResult = random.nextInt(numbers)
            val imageFileName = "IMG_$numberResult.png"
            val existImageFile = File(myDir, imageFileName)
            val outStream: FileOutputStream
            val bitmap = imageBitmap
            var isSuccessFileSaving: Boolean

            try {
                outStream = FileOutputStream(existImageFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
                /* 100 to keep full quality of the image */
                outStream.flush()
                outStream.close()
                isSuccessFileSaving = true
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                isSuccessFileSaving = false
            } catch (e: IOException) {
                e.printStackTrace()
                isSuccessFileSaving = false
            }

            val message = if (isSuccessFileSaving) {
                Const.MESSAGE_SUCCESS_IMAGE_SAVE
            } else {
                Const.MESSAGE_FAILED_IMAGE_SAVE
            }

            View.showToast(context, message)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                val contentUri = Uri.fromFile(existImageFile)
                scanIntent.data = contentUri
                context.sendBroadcast(scanIntent)
            } else {
                context.sendBroadcast(Intent(Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse(Const.SDCARD_URI_PATH + Environment
                                .getExternalStorageDirectory())))
            }
        }

        fun currencyFormatToRupiah(data: Double): String {
            val kursIndonesia = DecimalFormat.getCurrencyInstance() as DecimalFormat
            val formatRp = DecimalFormatSymbols()

            formatRp.currencySymbol = "Rp. "
            formatRp.monetaryDecimalSeparator = '.'
            formatRp.groupingSeparator = ','

            kursIndonesia.decimalFormatSymbols = formatRp
            return kursIndonesia.format(data)
        }

        fun setClearWebviewContent(bodyHTML: String): String {
            val head = "<head><style>img{max-width: 100%; height: auto;} body { margin: 0; }" +
                    "iframe {display: block; background: #000; border-top: 4px solid #000; border-bottom: 4px solid #000;" +
                    "top:0;left:0;width:100%;height:235;}</style></head>"
            return "<html>$head<body>$bodyHTML</body></html>"
        }

        fun emailValidate(email: String): Boolean {
            val emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
            return emailPattern.matcher(email).matches()
        }

        fun phoneValidate(phone: String): Boolean {
            return PhoneNumberUtils.isGlobalPhoneNumber(phone)
        }

        fun getFirstWord(text: String): String {
            return if (text.indexOf(' ') > -1) { // Check if there is more than one word.
                text.substring(0, text.indexOf(' ')) // Extract first word.
            } else {
                text // Text is the first word itself.
            }
        }

        fun setCapitalFirstChar(text: String?): String {
            if (text == null) {
                return ""
            }
            val tempString = text.split(" ")

            return tempString.joinToString(" ") {
                it.capitalize()
            }
        }

        fun getTimeLongFromDate(date: String, dateFormat: String, isLocale: Boolean): Long {
            return if (!TextUtils.isEmpty(date)) SimpleDateFormat(dateFormat, isLocaleDate(isLocale))
                    .parse(date).time else GitsHelper.Const.NUMBER_DEFAULT.toLong()
        }

        fun dateFormatFromTimeLong(timeLong: Long, newFormat: String, isLocale: Boolean): String {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeLong

            return if (timeLong != 0.toLong() && timeLong != null) SimpleDateFormat(newFormat, isLocaleDate(isLocale))
                    .format(calendar.time) else SimpleDateFormat(newFormat, isLocaleDate(isLocale))
                    .format(System.currentTimeMillis())
        }

        fun dateFormatFromTimeString(date: String, oldFormat: String, newFormat: String, isLocale: Boolean): String {
            val dateTimeMillis = if (!TextUtils.isEmpty(date)) {
                SimpleDateFormat(oldFormat, isLocaleDate(isLocale)).parse(date).time
            } else {
                GitsHelper.Const.NUMBER_DEFAULT.toLong()
            }

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = dateTimeMillis

            return if (dateTimeMillis != 0.toLong() && dateTimeMillis != null) {
                SimpleDateFormat(newFormat, isLocaleDate(isLocale))
                        .format(calendar.time)
            } else {
                SimpleDateFormat(newFormat, isLocaleDate(isLocale))
                        .format(System.currentTimeMillis())
            }
        }

        private fun isLocaleDate(isLocale: Boolean): Locale {
            return if (isLocale) Locale("id", "ID")
            else Locale("en", "EN")
        }

        fun <T> jsonStringToList(jsonString: String): List<T> {
            val initList: List<T>

            val collectionType = object : TypeToken<List<T>>() {}.type
            initList = Gson().fromJson<List<T>>(jsonString, collectionType)

            return initList
        }
    }

    object Navigator {
        fun openAppWithPackageName(context: Context, packageManager: PackageManager, packageName: String) {
            val launchIntent = packageManager.getLaunchIntentForPackage(packageName.trim())
            context.startActivity(launchIntent)
        }

        fun openAppWithSpecificClassName(context: Context, packageName: String,
                                         fullPackageClassName: String) {
            val launchIntent = Intent(Intent.ACTION_MAIN)

            launchIntent.setClassName(packageName, fullPackageClassName)

            context.startActivity(launchIntent)
        }
    }

    object Const {
        // If getting data from intent
        const val EXTRA_MOVIE_ITEM = "EXTRA_MOVIE_ITEM"
        const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        const val EXTRA_GLOBAL = "EXTRA_GLOBAL"

        // If getting date from argument
        const val ARGUMENT_MOVIE_ITEM = "ARGUMENT_MOVIE_ITEM"
        const val ARGUMENT_MOVIE_ID = "ARGUMENT_MOVIE_ID"

        // If intent with package
        const val ACTION_OPEN_WHATSAPP = "{PACKAGE NAME}"

        // If getting data from shared preference
        const val PREF_USER_ID = "PREF_USER_ID"

        // If getting data from intent bundle
        const val BUNDLE_MOVIES = "BUNDLE_MOVIES"

        // General constanta
        const val NUMBER_DEFAULT = 0
        const val CURRENCY_VALUE_DEFAULT = 0.0
        const val WEBVIEW_TEXT_SIZE_DEFAULT = 15
        const val ENCRYPTION_ALGORITHM_NAME = "Blowfish"
        const val SNACKBAR_TIMER_SHOWING_DEFAULT = 3000
        const val GLIDE_FADE_ANIMATION_TIME_DEFAULT = 600
        const val APP_FOLDER_DEFAULT = "APP_FILE_DIRECTORY"
        const val MESSAGE_SUCCESS_IMAGE_SAVE = "Image saved successfully"
        const val MESSAGE_FAILED_IMAGE_SAVE = "Image failed to save"
        const val SDCARD_URI_PATH = "file://mnt/sdcard/"

        // Server side constanta
        const val SERVER_CODE_404 = 404
        const val SERVER_ERROR_MESSAGE_DEFAULT = "Data not found"
        const val BASE_IMAGE_URL_MOVIE_DB = "http://image.tmdb.org/t/p/w342"

        // Date format style
        // If you want convert to indonesia please using Locale("id", "ID") put into SimpleDateFormat
        const val DATE_TIME_GLOBAL = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" //
        const val DATE_TIME_STANDARD = "yyyy-MM-dd HH:mm:ss" // 2018-10-02 12:12:12
        const val DATE_ENGLISH_YYYY_MM_DD = "yyyy-MM-dd" // 2018-10-02
        const val DATE_ENGLISH_YYYY_MM_DD_CLEAR = "yyyy MM dd" // 2018 10 02
        const val DATE_LOCALE_DD_MM_YYYY = "dd-MM-yyyy" // 02-10-2018
        const val DATE_LOCALE_DD_MM_YYYY_CLEAR = "dd MM yyyy" // 02-10-2018
        const val TIME_GENERAL_HH_MM_SS = "HH:mm:ss" // 12:12:12
        const val TIME_GENERAL_HH_MM = "HH:mm" // 12:12
        const val DAY_WITH_DATE_TIME_ENGLISH = "EEE, MMM dd yyyy HH:mm" // Mon, Aug 12 2018 12:12
        const val DAY_WITH_DATE_TIME_LOCALE = "EEE, dd MMM yyyy HH:mm" // Sen, 12 Agt 2019 12:12
        const val DAY_WITH_DATE_TIME_ENGLISH_FULL = "EEEE, MMMM dd yyyy HH:mm" // Monday, August 12 2018 12:12
        const val DAY_WITH_DATE_TIME_LOCALE_FULL = "EEEE, dd MMMM yyyy HH:mm" // Senin, 12 Agustus 2018 12:12
    }

    /**
     * This class is used to change your application locale and persist this change for the next time
     * that your app is going to be used.
     *
     *
     * You can also change the locale of your application on the fly by using the setLocale method.
     *
     *
     * Created by gunhansancar on 07/10/15.
     */
    object SystemLocale {

        private val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

        fun onAttach(context: Context): Context {
            val lang = getPersistedData(context, Locale.getDefault().language)
            return setLocale(context, lang)
        }

        fun onAttach(context: Context, defaultLanguage: String): Context {
            val lang = getPersistedData(context, defaultLanguage)
            return setLocale(context, lang)
        }

        fun getLanguage(context: Context): String? {
            return getPersistedData(context, Locale.getDefault().language)
        }

        fun setLocale(context: Context, language: String?): Context {
            persist(context, language)

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateResources(context, language)
            } else updateResourcesLegacy(context, language)

        }

        private fun getPersistedData(context: Context, defaultLanguage: String): String? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
        }

        private fun persist(context: Context, language: String?) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()

            editor.putString(SELECTED_LANGUAGE, language)
            editor.apply()
        }

        @TargetApi(Build.VERSION_CODES.N)
        private fun updateResources(context: Context, language: String?): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)

            val configuration = context.resources.configuration
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)

            return context.createConfigurationContext(configuration)
        }

        private fun updateResourcesLegacy(context: Context, language: String?): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)

            val resources = context.resources

            val configuration = resources.configuration
            configuration.locale = locale
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLayoutDirection(locale)
            }

            resources.updateConfiguration(configuration, resources.displayMetrics)

            return context
        }
    }
}