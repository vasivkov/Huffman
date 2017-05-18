package com.training.vasivkov;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
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
        List<String> listForTest = ht.ListFromFile(new File("/users/Vasya/Documents/testFile.txt"));
        Map<String, String> mapForTest = ht.ListToMap(listForTest);
        System.out.println(mapForTest);
    }


    @Test
    public void bigTest() throws Exception {
        HuffmanTree ht = new HuffmanTree();
        File file = new File("/users/Vasya/Documents/Winter's evening.txt");
        List<Frequencies> frequenciesList = ht.readFile(file);
        Map<String, String> codeMap = ht.findCodes(frequenciesList);
        List<Byte> listHuffmanCode = ht.textToByteArray(codeMap, file);
        ht.writeCodeToFile(listHuffmanCode);

        List<String> listForCodeMap = ht.mapToList(codeMap);
        ht.listToFile(listForCodeMap);

        Map<String, String> decoderMap = ht.ListToMap(ht.ListFromFile(new File("/users/Vasya/Documents/huffman_code_Map.txt")));
        List<Byte> listFromFile = ht.readCodeFromFile();

        ht.decoder(listFromFile, decoderMap);


    }


}