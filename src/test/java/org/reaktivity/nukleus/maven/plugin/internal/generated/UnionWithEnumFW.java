/**
 * Copyright 2016-2020 The Reaktivity Project
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

import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.reaktor.internal.test.types.Flyweight;

public final class UnionWithEnumFW extends Flyweight
{
    public static final EnumWithVariantOfUint64 KIND_VARIANT_OF_LIST = EnumWithVariantOfUint64.TYPE2;

    private final EnumWithVariantOfUint64FW enumWithVariantOfUint64RO = new EnumWithVariantOfUint64FW();

    private final ListWithEnumAndVariantWithDefaultFW listWithEnumAndVariantWithDefaultRO =
        new ListWithEnumAndVariantWithDefaultFW();

    public ListWithEnumAndVariantWithDefaultFW listWithEnumAndVariantWithDefault()
    {
        return listWithEnumAndVariantWithDefaultRO;
    }

    public EnumWithVariantOfUint64 kind()
    {
        return enumWithVariantOfUint64RO.get();
    }

    @Override
    public UnionWithEnumFW tryWrap(
        DirectBuffer buffer,
        int offset,
        int maxLimit)
    {
        if (super.tryWrap(buffer, offset, maxLimit) == null)
        {
            return null;
        }
        final EnumWithVariantOfUint64FW enumWithVariantOfUint64 = enumWithVariantOfUint64RO.tryWrap(buffer, offset, maxLimit);
        if (enumWithVariantOfUint64 == null)
        {
            return null;
        }

        switch (kind())
        {
        case TYPE2:
            if (listWithEnumAndVariantWithDefaultRO.tryWrap(buffer, offset + enumWithVariantOfUint64.sizeof(),
                maxLimit) == null)
            {
                return null;
            }
            break;
        default:
            break;
        }

        if (limit() > maxLimit)
        {
            return null;
        }
        return this;
    }

    @Override
    public UnionWithEnumFW wrap(
        DirectBuffer buffer,
        int offset,
        int maxLimit)
    {
        super.wrap(buffer, offset, maxLimit);
        final EnumWithVariantOfUint64FW enumWithVariantOfUint64 = enumWithVariantOfUint64RO.wrap(buffer, offset, maxLimit);
        switch (kind())
        {
        case TYPE2:
            listWithEnumAndVariantWithDefaultRO.wrap(buffer, offset + enumWithVariantOfUint64.sizeof(), maxLimit);
            break;
        default:
            break;
        }
        checkLimit(limit(), maxLimit);
        return this;
    }

    @Override
    public String toString()
    {
        switch (kind())
        {
        case TYPE2:
            return String.format("UNION_WITH_ENUM [listWithEnumAndVariantWithDefault=%s]", listWithEnumAndVariantWithDefault());
        default:
            return String.format("UNION_WITH_ENUM [unknown]");
        }
    }

    @Override
    public int limit()
    {
        switch (kind())
        {
        case TYPE2:
            return listWithEnumAndVariantWithDefault().limit();
        default:
            return offset();
        }
    }

    public static final class Builder extends Flyweight.Builder<UnionWithEnumFW>
    {
        private final EnumWithVariantOfUint64FW.Builder enumWithVariantOfUint64RW = new EnumWithVariantOfUint64FW.Builder();

        private final ListWithEnumAndVariantWithDefaultFW.Builder listWithEnumAndVariantWithDefaultRW =
            new ListWithEnumAndVariantWithDefaultFW.Builder();

        public Builder()
        {
            super(new UnionWithEnumFW());
        }

        public Builder kind(
            EnumWithVariantOfUint64 value)
        {
            enumWithVariantOfUint64RW.wrap(buffer(), offset(), maxLimit());
            enumWithVariantOfUint64RW.set(value);
            limit(enumWithVariantOfUint64RW.build().limit());
            return this;
        }

        private ListWithEnumAndVariantWithDefaultFW.Builder listWithEnumAndVariantWithDefault()
        {
            return listWithEnumAndVariantWithDefaultRW.wrap(buffer(), offset() + enumWithVariantOfUint64RW.sizeof(),
                maxLimit());
        }

        public Builder listWithEnumAndVariantWithDefault(
            ListWithEnumAndVariantWithDefaultFW value)
        {
            kind(KIND_VARIANT_OF_LIST);
            ListWithEnumAndVariantWithDefaultFW.Builder listWithEnumAndVariantWithDefault = listWithEnumAndVariantWithDefault();
            listWithEnumAndVariantWithDefault.fields(value.fieldCount(), value.fields(), 0, value.fields().capacity());
            limit(listWithEnumAndVariantWithDefault.build().limit());
            return this;
        }

        public Builder wrap(
            MutableDirectBuffer buffer,
            int offset,
            int maxLimit)
        {
            super.wrap(buffer, offset, maxLimit);
            return this;
        }
    }
}
