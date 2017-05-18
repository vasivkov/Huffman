package com.training.vasivkov;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by vasya on 27/04/17.
 */
public class HuffmanTreeTest {

    //        z - 00, y - 0111, a - 10, b - 11, e - 0110, l - 010;

    @Test
    public void testFindCodes() throws Exception {
        HuffmanTree ht = new HuffmanTree();

        List<Frequencies> listForTest = new ArrayList<>();
        listForTest.add(new Frequencies("z", 13));
        listForTest.add(new Frequencies("y", 2));
        listForTest.add(new Frequencies("a", 9));
        listForTest.add(new Frequencies("b", 7));
        listForTest.add(new Frequencies("e", 3));
        listForTest.add(new Frequencies("l", 5));


        Map<String, String> mapForTest = ht.findCodes(listForTest);

        assertEquals("00", mapForTest.get("z"));
        assertEquals("0111", mapForTest.get("y"));
        assertEquals("10", mapForTest.get("a"));
        assertEquals("11", mapForTest.get("b"));
        assertEquals("0110", mapForTest.get("e"));
        assertEquals("010", mapForTest.get("l"));

    }



    @Test
    public void testFindCodesForOneSymbol() throws Exception {
        HuffmanTree ht = new HuffmanTree();
        List<Frequencies> listForTest = new ArrayList<>();
        listForTest.add(new Frequencies("z", 13));

        Map<String, String> mapForTest = ht.findCodes(listForTest);

        assertEquals("0", mapForTest.get("z"));
    }

    @Test
    public void testTextToByteArray() throws Exception {
        String fileName = getClass().getClassLoader().getResource("test1.txt").getFile();
        Map<String, String> codeMap  = new HashMap<>();

        //TODO fill codeMap - codeMap.put()....

        HuffmanTree ht = new HuffmanTree();
        List<Byte> bytes = ht.textToByteArray(null, new File(fileName));

        //TODO asserts for bytes
    }

    @Test
    public void testMapToFile() throws Exception {
        HuffmanTree ht = new HuffmanTree();
        Map<String, String> mapForTest = new HashMap<>();
        mapForTest.put("a", "1");
        mapForTest.put("b", "2");
        mapForTest.put("c", "3");
        mapForTest.put("d", "4");
        mapForTest.put("e", "5");
        mapForTest.put("f", "6");

        ArrayList<String> listForTest = (ArrayList<String>) ht.mapToList(mapForTest);


    }

    @Test
    public void testMapFromFile() throws Exception {
        HuffmanTree ht = new HuffmanTree();
        List<String> listForTest = ht.listFromFile(new File("/users/Vasya/Documents/testFile.txt"));
        Map<String, String> mapForTest = ht.listToMap(listForTest);
        System.out.println(mapForTest);
    }


    @Test
    public void bigTest() throws Exception {
        HuffmanTree ht = new HuffmanTree();
        ht.encode("/users/Vasya/Documents/WINTER NIGHT.txt");
        ht.decode("huffmanCode_WINTER NIGHT.txt", "codeMap_WINTER NIGHT.txt", "out.txt");

    }

}