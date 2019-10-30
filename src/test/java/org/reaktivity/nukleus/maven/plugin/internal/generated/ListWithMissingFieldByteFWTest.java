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

import static java.nio.ByteBuffer.allocateDirect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.junit.Test;
import org.reaktivity.reaktor.internal.test.types.inner.EnumWithInt8;
import org.reaktivity.reaktor.internal.test.types.inner.EnumWithUint32;
import org.reaktivity.reaktor.internal.test.types.inner.ListWithMissingFieldByteFW;

public class ListWithMissingFieldByteFWTest
{
    private final MutableDirectBuffer buffer = new UnsafeBuffer(allocateDirect(100))
    {
        {
            // Make sure the code is not secretly relying upon memory being initialized to 0
            setMemory(0, capacity(), (byte) 0xab);
        }
    };
    private final ListWithMissingFieldByteFW.Builder listWithMissingFieldByteRW = new ListWithMissingFieldByteFW.Builder();
    private final ListWithMissingFieldByteFW listWithMissingFieldByteRO = new ListWithMissingFieldByteFW();
    private final int physicalLengthSize = Integer.BYTES;
    private final int logicalLengthSize = Integer.BYTES;

    private void setAllFields(
        MutableDirectBuffer buffer)
    {
        int physicalLength = 47;
        int logicalLength = 4;
        int offsetPhysicalLength = 10;
        buffer.putInt(offsetPhysicalLength, physicalLength);
        int offsetLogicalLength = offsetPhysicalLength + physicalLengthSize;
        buffer.putInt(offsetLogicalLength, logicalLength);

        int offsetVariantOfString1Kind = offsetLogicalLength + logicalLengthSize;
        buffer.putByte(offsetVariantOfString1Kind, EnumWithInt8.ONE.value());
        int offsetVariantOfString1Length = offsetVariantOfString1Kind + Byte.BYTES;
        buffer.putByte(offsetVariantOfString1Length, (byte) "string1".length());
        int offsetVariantOfString1 = offsetVariantOfString1Length + Byte.BYTES;
        buffer.putBytes(offsetVariantOfString1, "string1".getBytes());

        int offsetVariantOfString2Kind = offsetVariantOfString1 + "string1".length();
        buffer.putByte(offsetVariantOfString2Kind, EnumWithInt8.ONE.value());
        int offsetVariantOfString2Length = offsetVariantOfString2Kind + Byte.BYTES;
        buffer.putByte(offsetVariantOfString2Length, (byte) "string2".length());
        int offsetVariantOfString2 = offsetVariantOfString2Length + Byte.BYTES;
        buffer.putBytes(offsetVariantOfString2, "string2".getBytes());

        int offsetVariantOfUintKind = offsetVariantOfString2 + "string2".length();
        buffer.putLong(offsetVariantOfUintKind, EnumWithUint32.NI.value());
        int offsetVariantOfUint = offsetVariantOfUintKind + Long.BYTES;
        buffer.putLong(offsetVariantOfUint, 4000000000L);

        int offsetVariantOfIntKind = offsetVariantOfUint + Long.BYTES;
        buffer.putByte(offsetVariantOfIntKind, EnumWithInt8.THREE.value());
        int offsetVariantOfInt = offsetVariantOfIntKind + Byte.BYTES;
        buffer.putInt(offsetVariantOfInt, -2000000000);
    }

    @Test
    public void shouldNotWrapWhenLengthInsufficientForMinimumRequiredLength()
    {
        int physicalLength = 47;
        setAllFields(buffer);
        for (int maxLimit = 10; maxLimit <= physicalLength; maxLimit++)
        {
            try
            {
                listWithMissingFieldByteRO.wrap(buffer,  10, maxLimit);
                fail("Exception not thrown");
            }
            catch (Exception e)
            {
                if (!(e instanceof IndexOutOfBoundsException))
                {
                    fail("Unexpected exception " + e);
                }
            }
        }
    }

    @Test
    public void shouldNotTryWrapWhenLengthInsufficientForMinimumRequiredLength()
    {
        int physicalLength = 47;
        int offsetPhysicalLength = 10;
        setAllFields(buffer);
        for (int maxLimit = 10; maxLimit <= physicalLength; maxLimit++)
        {
            assertNull(listWithMissingFieldByteRO.tryWrap(buffer,  offsetPhysicalLength, maxLimit));
        }
    }

    @Test
    public void shouldWrapWhenLengthSufficientForMinimumRequiredLength()
    {
        int physicalLength = 47;
        int logicalLength = 4;
        int offsetPhysicalLength = 10;
        setAllFields(buffer);

        assertSame(listWithMissingFieldByteRO, listWithMissingFieldByteRO.wrap(buffer, offsetPhysicalLength,
            offsetPhysicalLength + physicalLength));
        assertEquals(physicalLength, listWithMissingFieldByteRO.limit() - offsetPhysicalLength);
        assertEquals(logicalLength, listWithMissingFieldByteRO.length());
        assertEquals("string1", listWithMissingFieldByteRO.variantOfString1());
        assertEquals("string2", listWithMissingFieldByteRO.variantOfString2());
        assertEquals(4000000000L, listWithMissingFieldByteRO.variantOfUint());
        assertEquals(-2000000000, listWithMissingFieldByteRO.variantOfInt());
    }

    @Test
    public void shouldTryWrapWhenLengthSufficientForMinimumRequiredLength()
    {
        int physicalLength = 47;
        int logicalLength = 4;
        int offsetPhysicalLength = 10;
        setAllFields(buffer);

        assertSame(listWithMissingFieldByteRO, listWithMissingFieldByteRO.tryWrap(buffer, offsetPhysicalLength,
            offsetPhysicalLength + physicalLength));
        assertEquals(physicalLength, listWithMissingFieldByteRO.limit() - offsetPhysicalLength);
        assertEquals(logicalLength, listWithMissingFieldByteRO.length());
        assertEquals("string1", listWithMissingFieldByteRO.variantOfString1());
        assertEquals("string2", listWithMissingFieldByteRO.variantOfString2());
        assertEquals(4000000000L, listWithMissingFieldByteRO.variantOfUint());
        assertEquals(-2000000000, listWithMissingFieldByteRO.variantOfInt());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldFailToSetString1WithInsufficientSpace() throws Exception
    {
        listWithMissingFieldByteRW.wrap(buffer, 10, 17)
            .variantOfString1("string1");
    }

    @Test(expected = AssertionError.class)
    public void shouldFailWhenFieldIsSetOutOfOrder() throws Exception
    {
        listWithMissingFieldByteRW.wrap(buffer, 0, buffer.capacity())
            .variantOfString1("string1")
            .variantOfUint(4000000000L)
            .variantOfString2("string2")
            .build();
    }

    @Test(expected = AssertionError.class)
    public void shouldFailWhenSameFieldIsSetMoreThanOnce() throws Exception
    {
        listWithMissingFieldByteRW.wrap(buffer, 0, buffer.capacity())
            .variantOfString1("string1")
            .variantOfString1("string2")
            .build();
    }

    @Test(expected = AssertionError.class)
    public void shouldFailWhenRequiredFieldIsNotSet() throws Exception
    {
        listWithMissingFieldByteRW.wrap(buffer, 0, buffer.capacity())
            .variantOfString2("string2")
            .build();
    }

    @Test(expected = AssertionError.class)
    public void shouldAssertErrorWhenValueNotPresent() throws Exception
    {
        int limit = listWithMissingFieldByteRW.wrap(buffer, 0, buffer.capacity())
            .variantOfString1("string1")
            .build()
            .limit();
        listWithMissingFieldByteRO.wrap(buffer,  0,  limit);
        assertEquals("string2", listWithMissingFieldByteRO.variantOfString2());
    }

    @Test
    public void shouldSetOnlyRequiredFields() throws Exception
    {
        int limit = listWithMissingFieldByteRW.wrap(buffer, 0, buffer.capacity())
            .variantOfString1("string1")
            .build()
            .limit();
        listWithMissingFieldByteRO.wrap(buffer,  0,  limit);
        assertEquals("string1", listWithMissingFieldByteRO.variantOfString1());
        assertEquals(4000000000L, listWithMissingFieldByteRO.variantOfUint());
    }

    @Test
    public void shouldSetSomeFields() throws Exception
    {
        int limit = listWithMissingFieldByteRW.wrap(buffer, 0, buffer.capacity())
            .variantOfString1("string1")
            .variantOfUint(4000000000L)
            .build()
            .limit();
        listWithMissingFieldByteRO.wrap(buffer,  0,  limit);
        assertEquals("string1", listWithMissingFieldByteRO.variantOfString1());
        assertEquals(4000000000L, listWithMissingFieldByteRO.variantOfUint());
    }

    @Test
    public void shouldSetAllFields() throws Exception
    {
        int limit = listWithMissingFieldByteRW.wrap(buffer, 0, buffer.capacity())
            .variantOfString1("string1")
            .variantOfString2("string2")
            .variantOfUint(4000000000L)
            .variantOfInt(-2000000000)
            .build()
            .limit();
        listWithMissingFieldByteRO.wrap(buffer,  0,  limit);
        assertEquals("string1", listWithMissingFieldByteRO.variantOfString1());
        assertEquals("string2", listWithMissingFieldByteRO.variantOfString2());
        assertEquals(4000000000L, listWithMissingFieldByteRO.variantOfUint());
        assertEquals(-2000000000, listWithMissingFieldByteRO.variantOfInt());
    }
}