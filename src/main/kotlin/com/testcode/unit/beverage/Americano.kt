/*
 * Copyright (C) 2025 NHN COMMERCE. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package com.testcode.unit.beverage

/**
 * @author Rasung Ki
 */
class Americano: Beverage {
    override fun getName(): String {
        return "아메리카노"
    }

    override fun getPrice(): Int {
        return 4000
    }
}
