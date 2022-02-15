package com.dicoding.picodiploma.mywidgets

import java.util.Random

/**
 * Created by dicoding on 1/3/2017.
 */

internal object NumberGenerator {
    fun generate(max: Int): Int {
        val random = Random()
        return random.nextInt(max)
    }
}
