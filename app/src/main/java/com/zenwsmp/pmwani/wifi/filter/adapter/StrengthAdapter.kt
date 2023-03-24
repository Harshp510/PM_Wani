/*
 * WiFiAnalyzer
 * Copyright (C) 2015 - 2022 VREM Software Development <VREMSoftwareDevelopment@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.zenwsmp.pmwani.wifi.filter.adapter

import com.zenwsmp.pmwani.wifi.settings.Settings
import com.zenwsmp.pmwani.wifi.model.Strength

class StrengthAdapter(selections: Set<Strength>) : EnumFilterAdapter<Strength>(selections, Strength.values()) {
    override fun color(selection: Strength): Int =
            if (selections.contains(selection)) selection.colorResource else Strength.colorResourceDefault

    override fun save(settings: Settings): Unit =
            settings.saveStrengths(selections)
}