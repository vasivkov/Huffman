package com.training.vasivkov;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by vasya on 27/04/17.
 */
public class HuffmanCodeTest {


    @Test
    public void testFindCodes() throws Exception {
        HuffmanCode ht = new HuffmanCode();

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
        HuffmanCode ht = new HuffmanCode();
        List<Frequencies> listForTest = new ArrayList<>();
        listForTest.add(new Frequencies("z", 13));

        Map<String, String> mapForTest = ht.findCodes(listForTest);

        assertEquals("0", mapForTest.get("z"));
    }

    @Test
    public  void testReadFile() throws  Exception {
        HuffmanCode ht = new HuffmanCode();
        String fileName = getClass().getClassLoader().getResource("test1.txt").getFile();
        List<Frequencies> listForTest = ht.readFile(new File(fileName));
        Map<String, Integer> mapForTest = new HashMap<>();
        for (Frequencies freq: listForTest) {
            mapForTest.put(freq.getSymbol(), freq.getCountOfSymbol());
        }

        assertEquals(8, mapForTest.size());
        assertEquals(2, (int) mapForTest.get("t"));
        assertEquals(2, (int) mapForTest.get("o"));
        assertEquals(3, (int) mapForTest.get("e"));

    }

    @Test
    public void testTextToByteArray() throws Exception {
        String fileName = getClass().getClassLoader().getResource("test1.txt").getFile();
        Map<String, String> codeMap  = new HashMap<>();

        codeMap.put(" ", "01");
        codeMap.put("r", "0010");
        codeMap.put("t", "010");
        codeMap.put("e", "10");
        codeMap.put("w", "0011");
        codeMap.put("h", "0000");
        codeMap.put("n", "0001");
        codeMap.put("o", "011");

        HuffmanCode ht = new HuffmanCode();
        List<Byte> bytes = ht.textToByteArray(codeMap, new File(fileName));
        List<Byte> expectedBytes = Arrays.asList((byte)49, (byte)74, (byte)27, (byte)40, (byte)5, (byte)2);

        assertTrue(bytes.equals(expectedBytes));
    }


    @Test
    public void end2endTest() throws Exception {
        HuffmanCode ht = new HuffmanCode();

        String test2File = getClass().getClassLoader().getResource("test2.txt").getFile();
        String pathToResources = new File(test2File).getParent();

        String codeMapDestFile = pathToResources + "/codeMap";
        String huffmanCodeDestFile = pathToResources + "/huffmanCode";
        String decodedTextFilePath = pathToResources + "/output.txt";

        try {

            ht.encode(test2File, codeMapDestFile, huffmanCodeDestFile);
            ht.decode(huffmanCodeDestFile, codeMapDestFile, decodedTextFilePath);

            String originText = FileUtils.readFileToString(new File(test2File), "UTF-8");
            String decodedText = FileUtils.readFileToString(new File(decodedTextFilePath), "UTF-8");
            assertTrue(originText.equals(decodedText));
        } finally {
            FileUtils.forceDelete(new File(codeMapDestFile));
            FileUtils.forceDelete(new File(huffmanCodeDestFile));
            FileUtils.forceDelete(new File(decodedTextFilePath));
        }
    }

}