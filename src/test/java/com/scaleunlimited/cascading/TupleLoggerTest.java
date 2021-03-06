/**
 * Copyright 2010 TransPac Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.scaleunlimited.cascading;

import org.apache.hadoop.io.BytesWritable;
import org.junit.Test;

import com.scaleunlimited.cascading.TupleLogger;

import cascading.tuple.Tuple;
import static org.junit.Assert.*;

public class TupleLoggerTest {

    @Test
    public void testLimitStringLength() {
        assertEquals("abc", TupleLogger.printObject("abcdefg", 3));
    }
    
    @Test
    public void testLimitBytesWritable() {
        BytesWritable bw = new BytesWritable("0123".getBytes());
        
        assertEquals("30 31 32", TupleLogger.printObject(bw, 10));
    }
    
    @Test
    public void testRemovingCRLF() {
        assertEquals("ab cd", TupleLogger.printObject("ab\rcd", 10));
    }
    
    @Test
    public void testEmptyBytesWritable() {
        assertEquals("", TupleLogger.printObject(new BytesWritable(), 10));
    }
    
    @Test
    public void testPrintingTupleInTuple() {
        TupleLogger tl = new TupleLogger(true);
        tl.setMaxPrintLength(10);
        
        BytesWritable bw = new BytesWritable("0123456789".getBytes());

        Tuple tuple = new Tuple("A long string", 1000, bw, new Tuple("a", "b"));
        StringBuilder result = new StringBuilder();
        result = tl.printTuple(result, tuple);
        
        assertEquals("['A long str', '1000', '30 31 32', ['a', 'b']]", result.toString());
    }
    
    @Test
    public void testPrintingNull() {
        TupleLogger tl = new TupleLogger(true);
        Tuple tuple = new Tuple("a", null);
        StringBuilder result = new StringBuilder();
        result = tl.printTuple(result, tuple);
        
        assertEquals("['a', 'null']", result.toString());
    }
}
