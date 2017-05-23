package com.training.vasivkov;


import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.*;


public class HuffmanCode {

    public void encode(String fileName, String destCodeMap, String destHuffmanCode) throws Exception {
        File file = new File(fileName);
        List<Frequencies> frequenciesList = readFile(file);
        Map<String, String> codeMap = findCodes(frequenciesList);
        List<Byte> listHuffmanCode = textToByteArray(codeMap, file);
        writeCodeToFile(listHuffmanCode, destHuffmanCode);

        List<String> listForCodeMap = mapToList(codeMap);
        listToFile(listForCodeMap, destCodeMap);
    }

    public void encode(String fileName) throws Exception {
        File file = new File(fileName);
        encode(fileName, "codeMap_" + file.getName(), "huffmanCode_" + file.getName());
    }

    List<Frequencies> readFile(File file) {
        Map<String, Integer> mapOfProbabilities = new HashMap<>();
        try(BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file))){
            while (fis.available() > 0) {
                char ch = (char) fis.read();
                String symbol = String.valueOf(ch);
                if (!mapOfProbabilities.containsKey(symbol)) {
                    mapOfProbabilities.put(symbol, 1);
                } else {
                    mapOfProbabilities.put(symbol, mapOfProbabilities.get(symbol) + 1);
                }
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }


        List<Frequencies> frequenciesList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : mapOfProbabilities.entrySet()) {
            frequenciesList.add(new Frequencies(entry.getKey(), entry.getValue()));
        }


        return frequenciesList;
    }


    Map<String, String> findCodes(List<Frequencies> frequenciesList) throws IOException {

        Map<String, String> codeMap = new HashMap<>();
        if (frequenciesList.size() == 0) {
            return codeMap;
        }

        if (frequenciesList.size() == 1) {
            codeMap.put(frequenciesList.get(0).getSymbol(), "0");
            return codeMap;
        }

        for (Frequencies aFrequenciesList : frequenciesList) {
            codeMap.put(aFrequenciesList.getSymbol(), "");
        }

        while (frequenciesList.size() > 1) {
            Collections.sort(frequenciesList);

            Frequencies sumFrequenices = Frequencies.sum(frequenciesList.get(frequenciesList.size() - 1), frequenciesList.get(frequenciesList.size() - 2));

            String[] letters = frequenciesList.remove(frequenciesList.size() - 1).getSymbol().split(""); // разбиваю набор символов на отдельные символы
            for (int j = 0; j < letters.length; j++) {
                codeMap.put(letters[j], "1" + codeMap.get(letters[j])); // изменяю значения в карте добавляя вперед 1
            }

            letters = frequenciesList.remove(frequenciesList.size() - 1).getSymbol().split(""); // те же действия еще раз, но добавляю 0;
            for (int j = 0; j < letters.length; j++) {
                codeMap.put(letters[j], "0" + codeMap.get(letters[j]));
            }

            frequenciesList.add(sumFrequenices); // вставляю в лист результирующее значение
        }

        return codeMap;
    }


    List<Byte> textToByteArray(Map<String, String> codeMap, File file)  {
        List<Byte> byteCode = new ArrayList<>();
        StringBuilder tmpString = new StringBuilder();
        try(BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file))){
            while (fis.available() > 0) {
                char ch = (char) fis.read();
                String symbol = String.valueOf(ch);

                tmpString.append(codeMap.get(symbol));
                while (tmpString.length() > 7) {
                    String tmp = tmpString.substring(0, 7);
                    byteCode.add(Byte.valueOf(tmp, 2)); // объединяю по 7 и периодически сбрасываю в список ввиде байт
                    tmpString.delete(0, 7); // удаляю из временной строки этот диапазон
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        int incompleteGroup = tmpString.length(); // запоминаю кол-во элементов в неполной группе
        byteCode.add(Byte.valueOf(tmpString.toString(), 2));
        codeMap.put("" + incompleteGroup, "count");
        return byteCode;
    }

    private void writeCodeToFile(List<Byte> listByte, String fileName) {
        Byte[] arrayOfByte = listByte.toArray(new Byte[listByte.size()]);
        byte[] bytes = ArrayUtils.toPrimitive(arrayOfByte);
        try(FileOutputStream fos = new FileOutputStream(fileName); ){
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> mapToList(Map<String, String> map) {
        List<String> list = new ArrayList<>();
        for (Map.Entry entry : map.entrySet()) {
            list.add((String) entry.getValue());
            list.add((String) entry.getKey());
        }
        return list;
    }

    private void listToFile(List<String> list, String fileName) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)));) {
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // ++++++++++++ DECODER +++++++++++++++++++++++++++++

    private List<String> listFromFile(File file) {
        List<String> list = null;
        try( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            list = (ArrayList<String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }


    private Map<String, String> listToMap(List<String> list) {
        Map<String, String> map = new HashMap<>();
        for (int i = 1; i < list.size(); i++) {
            if (i % 2 != 0) {
                map.put(list.get(i - 1), list.get(i));
            }

        }
        return map;
    }

    private List<Byte> readCodeFromFile(String fileName) {
        List<Byte> listFromFile = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(new File(fileName))){
            while (fis.available() > 0) {
                listFromFile.add((byte) fis.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listFromFile;
    }


    public void decode(String huffmanCodeFile, String codeMapFile, String destinationFile) {
        Map<String, String> decoderMap = listToMap(listFromFile(new File(codeMapFile)));
        List<Byte> listFromFile = readCodeFromFile(huffmanCodeFile);

        decoder(listFromFile, decoderMap, destinationFile);
    }

    private void decoder(List<Byte> resultArray, Map<String, String> decodeMap, String destinationFile) {
        try (FileWriter fw = new FileWriter(destinationFile) ){

            StringBuilder tmpString = new StringBuilder();
            StringBuilder stringForFindCode = new StringBuilder();

            for (int i = 0; i < resultArray.size(); i++) {
                if (i == resultArray.size() - 1) {// последний байт, который может быть неполным
                    String shortString = oneZeroString(resultArray.get(i));
                    int count = Integer.parseInt(decodeMap.get("count"));

                    tmpString.append(shortString.substring(7 - count, 7));

                } else {
                    tmpString.append(oneZeroString(resultArray.get(i)));
                }

                for (int j = 0; j < tmpString.length(); j++) {

                    stringForFindCode.append(tmpString.charAt(j));
                    if (decodeMap.containsKey(stringForFindCode.toString())) {
                        fw.write(decodeMap.get(stringForFindCode.toString()));
                        stringForFindCode.delete(0, stringForFindCode.length());
                    }
                }
                tmpString.delete(0, 7);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String oneZeroString(Byte b) {
        StringBuilder tmpString = new StringBuilder();
        StringBuilder oneZeroString = new StringBuilder();
        tmpString.append("0000000");
        tmpString.append(Integer.toBinaryString(b)); // байты, преобразованные в двоичный код

        oneZeroString.append(tmpString.substring(tmpString.length() - 7, tmpString.length()));
        return oneZeroString.toString();
    }


}



