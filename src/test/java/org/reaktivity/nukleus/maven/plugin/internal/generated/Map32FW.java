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

import java.util.function.Consumer;
import java.util.function.Function;

import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.reaktor.internal.test.types.Flyweight;

public final class Map32FW<KV extends VariantFW<?, ?>, VV extends VariantFW<?, ?>> extends MapFW
{
    private static final int LENGTH_SIZE = BitUtil.SIZE_OF_INT;

    private static final int FIELD_COUNT_SIZE = BitUtil.SIZE_OF_INT;

    private static final int LENGTH_OFFSET = 0;

    private static final int FIELD_COUNT_OFFSET = LENGTH_OFFSET + LENGTH_SIZE;

    private static final int FIELDS_OFFSET = FIELD_COUNT_OFFSET + FIELD_COUNT_SIZE;

    private static final long LENGTH_MAX_VALUE = 0xFFFFFFFFL;

    private final KV keyRO;

    private final VV valueRO;

    public Map32FW(
        KV keyRO,
        VV valueRO)
    {
        this.keyRO = keyRO;
        this.valueRO = valueRO;
    }

    @Override
    public int length()
    {
        return buffer().getInt(offset() + LENGTH_OFFSET);
    }

    @Override
    public int fieldCount()
    {
        return buffer().getInt(offset() + FIELD_COUNT_OFFSET);
    }

    public void forEach(
        Function<KV, Consumer<VV>> consumer)
    {
        int offset = offset() + FIELDS_OFFSET;
        for (int i = 0; i < fieldCount() / 2; i++)
        {
            keyRO.wrap(buffer(), offset, limit());
            valueRO.wrap(buffer(), keyRO.limit(), limit());
            offset = valueRO.limit();
            consumer.apply(keyRO).accept(valueRO);
        }
    }

    @Override
    public Map32FW wrap(
        DirectBuffer buffer,
        int offset,
        int maxLimit)
    {
        super.wrap(buffer, offset, maxLimit);
        checkLimit(limit(), maxLimit);
        return this;
    }

    @Override
    public Map32FW tryWrap(
        DirectBuffer buffer,
        int offset,
        int maxLimit)
    {
        if (super.tryWrap(buffer, offset, maxLimit) == null)
        {
            return null;
        }

        if (limit() > maxLimit)
        {
            return null;
        }
        return this;
    }

    @Override
    public int limit()
    {
        return offset() + LENGTH_SIZE + length();
    }

    public static final class Builder<KB extends VariantFW.Builder<KV, KK, KO>, KV extends VariantFW<KK, KO>, KK,
        KO extends Flyweight,
        VB extends VariantFW.Builder<VV, VK, VO>, VV extends VariantFW<VK, VO>, VK, VO extends Flyweight>
        extends MapFW.Builder<Map32FW, KB, KV, KK, KO, VB, VV, VK, VO>
    {
        public Builder(
            KB keyRW,
            KV keyRO,
            VB valueRW,
            VV valueRO)
        {
            super(new Map32FW<>(keyRO, valueRO), keyRW, valueRW);
        }

        @Override
        public Builder<KB, KV, KK, KO, VB, VV, VK, VO> wrap(
            MutableDirectBuffer buffer,
            int offset,
            int maxLimit)
        {
            super.wrap(buffer, offset, maxLimit);
            int newLimit = offset + FIELDS_OFFSET;
            checkLimit(newLimit, maxLimit);
            limit(newLimit);
            return this;
        }

        @Override
        public Map32FW build()
        {
            assert !iskeySet() : "Key is set but value is not set";
            int length = limit() - offset() - FIELD_COUNT_OFFSET;
            buffer().putInt(offset() + LENGTH_OFFSET, length);
            buffer().putInt(offset() + FIELD_COUNT_OFFSET, fieldCount());
            return super.build();
        }
    }
}
