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

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;

public final class String32FW extends StringFW
{
    private static final int FIELD_SIZE_LENGTH = BitUtil.SIZE_OF_INT;

    private final ByteOrder byteOrder;

    private final DirectBuffer valueRO = new UnsafeBuffer(0L, 0);

    public String32FW()
    {
        this.byteOrder = ByteOrder.nativeOrder();
    }

    public String32FW(ByteOrder byteOrder)
    {
        this.byteOrder = byteOrder;
    }

    public String32FW(String value)
    {
        this(value, StandardCharsets.UTF_8);
    }

    public String32FW(
        String value,
        Charset charset)
    {
        this.byteOrder = ByteOrder.nativeOrder();
        final byte[] encoded = value.getBytes(charset);
        final MutableDirectBuffer buffer = new UnsafeBuffer(new byte[FIELD_SIZE_LENGTH + encoded.length]);
        buffer.putShort(0, (short) (encoded.length & 0xFFFF));
        buffer.putBytes(FIELD_SIZE_LENGTH, encoded);
        wrap(buffer, 0, buffer.capacity());
    }

    @Override
    public int limit()
    {
        return offset() + FIELD_SIZE_LENGTH + Math.max(length(), 0);
    }

    public DirectBuffer value()
    {
        return length() == -1 ? null : valueRO;
    }

    @Override
    public String asString()
    {
        if (maxLimit() == offset() || length() == -1)
        {
            return null;
        }
        return buffer().getStringWithoutLengthUtf8(offset() + FIELD_SIZE_LENGTH, length());
    }

    @Override
    public String32FW tryWrap(
        DirectBuffer buffer,
        int offset,
        int maxLimit)
    {
        if (null == super.tryWrap(buffer, offset, maxLimit) || offset + FIELD_SIZE_LENGTH > maxLimit() || limit() > maxLimit)
        {
            return null;
        }
        int length = length();
        if (length != -1)
        {
            valueRO.wrap(buffer, offset + FIELD_SIZE_LENGTH, length);
        }
        return this;
    }

    @Override
    public String32FW wrap(
        DirectBuffer buffer,
        int offset,
        int maxLimit)
    {
        super.wrap(buffer, offset, maxLimit);
        checkLimit(offset + FIELD_SIZE_LENGTH, maxLimit);
        checkLimit(limit(), maxLimit);
        int length = length();
        if (length != -1)
        {
            valueRO.wrap(buffer, offset + FIELD_SIZE_LENGTH, length);
        }
        return this;
    }

    @Override
    public String toString()
    {
        return maxLimit() == offset() ? "null" : String.format("\"%s\"", asString());
    }

    public int length()
    {
        int length = buffer().getInt(offset(), byteOrder) & 0xFFFFFFFF;
        return length < 0 ? -1 : length;
    }

    public static final class Builder extends StringFW.Builder<String32FW>
    {
        private final ByteOrder byteOrder;

        public Builder()
        {
            super(new String32FW());
            this.byteOrder = ByteOrder.nativeOrder();
        }

        public Builder(ByteOrder byteOrder)
        {
            super(new String32FW(byteOrder));
            this.byteOrder = byteOrder;
        }

        @Override
        public Builder wrap(
            MutableDirectBuffer buffer,
            int offset,
            int maxLimit)
        {
            checkLimit(offset + FIELD_SIZE_LENGTH, maxLimit);
            super.wrap(buffer, offset, maxLimit);
            return this;
        }

        public Builder set(
            String32FW value)
        {
            if (value == null)
            {
                int newLimit = offset() + FIELD_SIZE_LENGTH;
                checkLimit(newLimit, maxLimit());
                buffer().putInt(offset(), -1, byteOrder);
                limit(newLimit);
            }
            else
            {
                int newLimit = offset() + value.sizeof();
                checkLimit(newLimit, maxLimit());
                buffer().putInt(offset(), value.length(), byteOrder);
                buffer().putBytes(offset() + 4, value.buffer(), value.offset() + 4, value.length());
                limit(newLimit);
            }
            super.set(value);
            return this;
        }

        public Builder set(
            DirectBuffer srcBuffer,
            int srcOffset,
            int length)
        {
            checkLength(length);
            int offset = offset();
            int newLimit = offset + length + FIELD_SIZE_LENGTH;
            checkLimit(newLimit, maxLimit());
            buffer().putInt(offset, length, byteOrder);
            buffer().putBytes(offset + 4, srcBuffer, srcOffset, length);
            limit(newLimit);
            super.set(srcBuffer, srcOffset, length);
            return this;
        }

        public Builder set(
            String value,
            Charset charset)
        {
            if (value == null)
            {
                int newLimit = offset() + FIELD_SIZE_LENGTH;
                checkLimit(newLimit, maxLimit());
                buffer().putInt(offset(), -1, byteOrder);
                limit(newLimit);
            }
            else
            {
                byte[] charBytes = value.getBytes(charset);
                checkLength(charBytes.length);
                int newLimit = offset() + FIELD_SIZE_LENGTH + charBytes.length;
                checkLimit(newLimit, maxLimit());
                buffer().putInt(offset(), charBytes.length, byteOrder);
                buffer().putBytes(offset() + 4, charBytes);
                limit(newLimit);
            }
            super.set(value, charset);
            return this;
        }

        private static void checkLength(
            int length)
        {
            final int maxLength = Integer.MAX_VALUE - 1;
            if (length > maxLength)
            {
                final String msg = String.format("length=%d is beyond maximum length=%d", length, maxLength);
                throw new IllegalArgumentException(msg);
            }
        }

        @Override
        public String32FW build()
        {
            return super.build();
        }
    }
}
