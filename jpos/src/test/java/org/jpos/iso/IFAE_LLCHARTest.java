/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2019 jPOS Software SRL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jpos.iso;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * @author gregorioosorio
 */
public class IFAE_LLCHARTest {
    @Test
    public void testPack() throws Exception
    {
        ISOField field = new ISOField(12, "1234");
        IFAE_LLCHAR packager = new IFAE_LLCHAR(10, "Should be 041234");
        TestUtils.assertEquals(new byte[] {(byte)0x30, (byte)0x34, (byte)0xF1, (byte)0xF2, (byte)0xF3, (byte)0xF4},
                            packager.pack(field));
    }

    @Test
    public void testPackWithPackagerWithoutDescription() throws Exception
    {
        ISOField field = new ISOField(12, "1234");
        IFAE_LLCHAR packager = new IFAE_LLCHAR();
        packager.setLength(12);
        TestUtils.assertEquals(new byte[] {(byte)0x30, (byte)0x34, (byte)0xF1, (byte)0xF2, (byte)0xF3, (byte)0xF4},
                            packager.pack(field));
    }
    
    @Test
    public void testUnpack() throws Exception
    {
        byte[] raw = new byte[] {(byte)0x30, (byte)0x34, (byte)0xF1, (byte)0xF2, (byte)0xF3, (byte)0xF4};
        IFAE_LLCHAR packager = new IFAE_LLCHAR(10, "Should be 041234");
        ISOField field = new ISOField(12);
        packager.unpack(field, raw, 0);
        assertEquals("1234", (String) field.getValue());
    }

    @Test
    public void testUnpackWithPackagerWithoutDescription() throws Exception
    {
        byte[] raw = new byte[] {(byte)0x30, (byte)0x34, (byte)0xF1, (byte)0xF2, (byte)0xF3, (byte)0xF4};
        IFAE_LLCHAR packager = new IFAE_LLCHAR();
        packager.setLength(10);
        ISOField field = new ISOField(12);
        packager.unpack(field, raw, 0);
        assertEquals("1234", (String) field.getValue());
    }

    @Test
    public void testReversability() throws Exception
    {
        String origin = "Abc123:.-";
        ISOField field = new ISOField(12, origin);
        IFAE_LLCHAR packager = new IFAE_LLCHAR(10, "Should be Abc123:.-");

        ISOField unpack = new ISOField(12);
        packager.unpack(unpack, packager.pack(field), 0);
        assertEquals(origin, (String) unpack.getValue());
    }

}
