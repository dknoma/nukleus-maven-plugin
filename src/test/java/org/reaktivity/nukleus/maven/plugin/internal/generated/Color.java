/**
 * Copyright 2016-2019 The Reaktivity Project
 *
 * The Reaktivity Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.reaktivity.nukleus.maven.plugin.internal.generated;

import org.reaktivity.reaktor.internal.test.types.StringFW;

// TODO: Will be removed
public enum Color
{
    RED("red"),

    BLUE("blue"),

    YELLOW("yellow");

    private final String value;

    Color(String value)
    {
        this.value = value;
    }

    public String value()
    {
        return value;
    }

    public static Color valueOf(StringFW value)
    {
        String kind = value.asString();
        switch (kind)
        {
        case "red":
            return RED;
        case "blue":
            return BLUE;
        case "yellow":
            return YELLOW;
        }
        return null;
    }
}