package com.camunda.triporganization.helper

import com.camunda.triporganization.R

object FlagHelper {

    fun getFlagForCountry(alpha2: String) =
        when (alpha2) {
            "fr" -> R.drawable.fr
            "es" -> R.drawable.es
            "it" -> R.drawable.it
            else -> R.drawable.it
        }
}
