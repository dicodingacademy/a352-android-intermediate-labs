package com.dicoding.picodiploma.productdetail.helper

import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val rupiahExchangeRate  = 12495.95
private const val euroExchangeRate = 0.88

fun String.withNumberingFormat(): String {
    return NumberFormat.getNumberInstance().format(this.toDouble())
}

fun String.withDateFormat(): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    val date = format.parse(this) as Date
    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}

fun String.withCurrencyFormat(): String {

    var priceOnDollar = this.toDouble() / rupiahExchangeRate

    var mCurrencyFormat = NumberFormat.getCurrencyInstance()
    val deviceLocale = Locale.getDefault().country
    when {
        deviceLocale.equals("ES") -> {
            priceOnDollar *= euroExchangeRate
        }
        deviceLocale.equals("ID") -> {
            priceOnDollar *= rupiahExchangeRate
        }
        else -> {
            mCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
        }
    }

    return mCurrencyFormat.format(priceOnDollar)
}