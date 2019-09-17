/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package com.dj.xtool.utils.textplace;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;


class InterpolatorStringLookup extends AbstractStringLookup {

    /**
     * Defines the singleton for this class.
     *
     * @since 1.6
     */
    static final AbstractStringLookup INSTANCE = new InterpolatorStringLookup();

    /** Constant for the prefix separator. */
    private static final char PREFIX_SEPARATOR = ':';

    /** The default string lookup. */
    private final StringLookup defaultStringLookup;

    /** The map of String lookups keyed by prefix. */
    private final Map<String, StringLookup> stringLookupMap;


    InterpolatorStringLookup() {
        this((Map<String, String>) null);
    }


    <V> InterpolatorStringLookup(final Map<String, V> defaultMap) {
        this(MapStringLookup.on(defaultMap == null ? new HashMap<String, V>() : defaultMap));
    }

    /**
     * Creates an instance with the given lookup.
     *
     * @param defaultStringLookup
     *            the default lookup.
     */
    InterpolatorStringLookup(final StringLookup defaultStringLookup) {
        this(new HashMap(), defaultStringLookup, true);
    }

    /**
     * Creates a fully customized instance.
     *
     * @param stringLookupMap
     *            the map of string lookups.
     * @param defaultStringLookup
     *            the default string lookup.
     * @param addDefaultLookups
     *            whether the default lookups should be used.
     */
    InterpolatorStringLookup(final Map<String, StringLookup> stringLookupMap, final StringLookup defaultStringLookup,
            final boolean addDefaultLookups) {
        super();
        this.defaultStringLookup = defaultStringLookup;
        this.stringLookupMap = new HashMap<>(stringLookupMap.size());
        for (final Entry<String, StringLookup> entry : stringLookupMap.entrySet()) {
            this.stringLookupMap.put(toKey(entry.getKey()), entry.getValue());
        }
     /*   if (addDefaultLookups) {
            StringLookupFactory.INSTANCE.addDefaultStringLookups(this.stringLookupMap);
        }*/
    }

    /**
     * Gets the lookup map.
     *
     * @return The lookup map.
     */
    public Map<String, StringLookup> getStringLookupMap() {
        return stringLookupMap;
    }

    static String toKey(final String key) {
        return key.toLowerCase(Locale.ROOT);
    }

    /**
     * Resolves the specified variable. This implementation will try to extract a variable prefix from the given
     * variable name (the first colon (':') is used as prefix separator). It then passes the name of the variable with
     * the prefix stripped to the lookup object registered for this prefix. If no prefix can be found or if the
     * associated lookup object cannot resolve this variable, the default lookup object will be used.
     *
     * @param var
     *            the name of the variable whose value is to be looked up
     * @return The value of this variable or <b>null</b> if it cannot be resolved
     */
    @Override
    public String lookup(String var) {
        if (var == null) {
            return null;
        }

        final int prefixPos = var.indexOf(PREFIX_SEPARATOR);
        if (prefixPos >= 0) {
            final String prefix = toKey(var.substring(0, prefixPos));
            final String name = var.substring(prefixPos + 1);
            final StringLookup lookup = stringLookupMap.get(prefix);
            String value = null;
            if (lookup != null) {
                value = lookup.lookup(name);
            }

            if (value != null) {
                return value;
            }
            var = var.substring(prefixPos + 1);
        }
        if (defaultStringLookup != null) {
            return defaultStringLookup.lookup(var);
        }
        return null;
    }

    @Override
    public String toString() {
        return getClass().getName() + " [stringLookupMap=" + stringLookupMap + ", defaultStringLookup="
                + defaultStringLookup + "]";
    }
}
