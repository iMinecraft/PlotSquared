////////////////////////////////////////////////////////////////////////////////////////////////////
// PlotSquared - A plot manager and world generator for the Bukkit API                             /
// Copyright (c) 2014 IntellectualSites/IntellectualCrafters                                       /
//                                                                                                 /
// This program is free software; you can redistribute it and/or modify                            /
// it under the terms of the GNU General Public License as published by                            /
// the Free Software Foundation; either version 3 of the License, or                               /
// (at your option) any later version.                                                             /
//                                                                                                 /
// This program is distributed in the hope that it will be useful,                                 /
// but WITHOUT ANY WARRANTY; without even the implied warranty of                                  /
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                   /
// GNU General Public License for more details.                                                    /
//                                                                                                 /
// You should have received a copy of the GNU General Public License                               /
// along with this program; if not, write to the Free Software Foundation,                         /
// Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA                               /
//                                                                                                 /
// You can contact us via: support@intellectualsites.com                                           /
////////////////////////////////////////////////////////////////////////////////////////////////////

package com.intellectualcrafters.plot.flag;

import com.intellectualcrafters.plot.object.Plot;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Flag Manager Utility
 *
 * @author Citymonstret
 * @author Empire92
 */
@SuppressWarnings("unused")
public class FlagManager {

    // TODO add some flags
    // - Plot clear interval
    // - Mob cap
    // - customized plot composition

    private static ArrayList<AbstractFlag> flags = new ArrayList<>();

    /**
     * Register an AbstractFlag with PlotSquared
     *
     * @param flag Flag to register
     * @return success?
     */
    public static boolean addFlag(final AbstractFlag flag) {
        return getFlag(flag.getKey()) == null && flags.add(flag);
    }

    public static Flag[] removeFlag(final Flag[] flags, final String r) {
        final Flag[] f = new Flag[flags.length - 1];
        int index = 0;
        for (final Flag flag : flags) {
            if (!flag.getKey().equals(r)) {
                f[index++] = flag;
            }
        }
        return f;
    }

    public static Flag[] removeFlag(final Set<Flag> flags, final String r) {
        final Flag[] flagArray = new Flag[flags.size() - 1];
        int index = 0;
        for (final Flag flag : flags) {
            if (!flag.getKey().equals(r)) {
                flagArray[index++] = flag;
            }
        }
        return flagArray;
    }

    /**
     * Get a list of registered AbstractFlag objects
     *
     * @return List (AbstractFlag)
     */
    public static List<AbstractFlag> getFlags() {
        return flags;
    }

    /**
     * Get a list of registerd AbstragFlag objects based on player permissions
     *
     * @param player with permissions
     * @return List (AbstractFlag)
     */
    public static List<AbstractFlag> getFlags(final Player player) {
        final List<AbstractFlag> returnFlags = new ArrayList<>();
        for (final AbstractFlag flag : flags) {
            if (player.hasPermission("plots.set.flag." + flag.getKey().toLowerCase())) {
                returnFlags.add(flag);
            }
        }
        return returnFlags;
    }

    /**
     * Get an AbstractFlag by a string Returns null if flag does not exist
     *
     * @param string Flag Key
     * @return AbstractFlag
     */
    public static AbstractFlag getFlag(final String string) {
        for (final AbstractFlag flag : flags) {
            if (flag.getKey().equalsIgnoreCase(string)) {
                return flag;
            }
        }
        return null;
    }

    /**
     * Get an AbstractFlag by a string
     *
     * @param string Flag Key
     * @param create If to create the flag if it does not exist
     * @return AbstractFlag
     */
    public static AbstractFlag getFlag(final String string, final boolean create) {
        if ((getFlag(string) == null) && create) {
            final AbstractFlag flag = new AbstractFlag(string);
            addFlag(flag);
            return flag;
        }
        return getFlag(string);
    }

    /**
     * Remove a registered AbstractFlag
     *
     * @param flag Flag Key
     * @return boolean Result of operation
     */
    public static boolean removeFlag(final AbstractFlag flag) {
        return flags.remove(flag);
    }

    public static Flag[] parseFlags(final List<String> flagstrings) {
        final Flag[] flags = new Flag[flagstrings.size()];
        for (int i = 0; i < flagstrings.size(); i++) {
            final String[] split = flagstrings.get(i).split(";");
            if (split.length == 1) {
                flags[i] = new Flag(getFlag(split[0], true), "");
            } else {
                flags[i] = new Flag(getFlag(split[0], true), split[1]);
            }
        }
        return flags;
    }

    /**
     * Get the flags for a plot
     *
     * @param plot Plot to search in
     * @return List (AbstractFlag)
     */
    public static List<AbstractFlag> getPlotFlags(final Plot plot) {
        final Set<Flag> plotFlags = plot.settings.getFlags();
        final List<AbstractFlag> flags = new ArrayList<>();
        for (final Flag flag : plotFlags) {
            flags.add(flag.getAbstractFlag());
        }
        return flags;
    }
}
