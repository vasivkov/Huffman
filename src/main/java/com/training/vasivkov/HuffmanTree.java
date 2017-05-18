package com.training.vasivkov;


import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.*;


/**
 * Created by vasya on 19/04/17.
 */
public class HuffmanTree {

    List<Frequencies> readFile(File file) throws IOException {
        Map<String, Integer> mapOfProbabilities = new HashMap<>();
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));

        while (fis.available() > 0) {
            char ch = (char) fis.read();
            String symbol = String.valueOf(ch);
            if (!mapOfProbabilities.containsKey(symbol)) {
                mapOfProbabilities.put(symbol, 1);
            } else {
                mapOfProbabilities.put(symbol, mapOfProbabilities.get(symbol) + 1);
            }
        }

        List<Frequencies> frequenciesList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : mapOfProbabilities.entrySet()) {
            frequenciesList.add(new Frequencies(entry.getKey(), entry.getValue()));
        }

        fis.close();

        return frequenciesList;
    }


    Map<String, String> findCodes(List<Frequencies> frequenciesList) throws IOException {
        Map<String, String> codeMap = new HashMap<>();
        for (Frequencies aFrequenciesList : frequenciesList) {
            codeMap.put(aFrequenciesList.getSymbol(), "");
        }

        if (codeMap.size() == 1) {
            codeMap.put(frequenciesList.get(0).getSymbol(), "0");
            return codeMap;
        }

        for (int i = 0; i <= frequenciesList.size(); i++) {
            if (frequenciesList.size() == 1) {
                break;
            }

            frequenciesList.sort(new Comparator<Frequencies>() { // сортирую лист по частоте символов
                @Override
                public int compare(Frequencies o1, Frequencies o2) {
                    return o2.getCountOfSymbol() - o1.getCountOfSymbol();
                }
            });

            // нахожу сумму вероятностей 2х последних символов (самые редкие)
            int sumCount = frequenciesList.get(frequenciesList.size() - 1).getCountOfSymbol() + frequenciesList.get(frequenciesList.size() - 2).getCountOfSymbol();
            // конкатенирую 2 самых редких символа
            String sumSmbols = frequenciesList.get(frequenciesList.size() - 1).getSymbol() + frequenciesList.get(frequenciesList.size() - 2).getSymbol();

            String[] letters = frequenciesList.get(frequenciesList.size() - 1).getSymbol().split(""); // разбиваю набор символов на отдельные символы
            for (int j = 0; j < letters.length; j++) {
                codeMap.put(letters[j], "1" + codeMap.get(letters[j])); // изменяю значения в карте добавляя вперед 1
            }
            frequenciesList.remove(frequenciesList.size() - 1); // удаляю последнее значение из листа

            letters = frequenciesList.get(frequenciesList.size() - 1).getSymbol().split(""); // те же действия еще раз, но добавляю 0;
            for (int j = 0; j < letters.length; j++) {
                codeMap.put(letters[j], "0" + codeMap.get(letters[j]));
            }
            frequenciesList.remove(frequenciesList.size() - 1);

            frequenciesList.add(new Frequencies(sumSmbols, sumCount)); // вставляю в лист результирующее значение

        }
        return codeMap;
    }



    List<Byte> textToByteArray(Map<String, String> codeMap, File file) throws IOException {

        List<Byte> byteCode = new ArrayList<>();
        StringBuilder tmpString = new StringBuilder();

        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));

        while (fis.available() > 0) {
            char ch = (char) fis.read();
            String symbol = String.valueOf(ch);

            tmpString.append(codeMap.get(symbol));
            while (tmpString.length() > 7) {
                String tmp = tmpString.substring(0, 7);
                byteCode.add(Byte.valueOf(tmp, 2)); // объединяю по 7 и периодически сбрасываю в список ввиде байт
                System.out.print(tmpString.substring(0, 7));
                tmpString.delete(0, 7); // удаляю из временной строки этот диапазон
            }
        }
        int incompleteGroup = tmpString.length(); // запоминаю кол-во элементов в неполной группе
        byteCode.add(Byte.valueOf(tmpString.toString(), 2));
        codeMap.put("" + incompleteGroup, "count");
        return byteCode;
    }

    void writeCodeToFile(List<Byte> listByte) {
        Byte[] arrayOfByte = listByte.toArray(new Byte[listByte.size()]);
        byte[] bytes = ArrayUtils.toPrimitive(arrayOfByte);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("/users/Vasya/Documents/huffman_code.txt");
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    List<String> mapToList(Map<String, String> map) {
        List<String> list = new ArrayList<>();
        for (Map.Entry entry : map.entrySet()) {
            list.add((String) entry.getValue());
            list.add((String) entry.getKey());
        }
        return list;
    }

    void listToFile(List<String> list) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(new File("/users/Vasya/Documents/huffman_code_Map.txt")));
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert oos != null;
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    // ++++++++++++ DECODER +++++++++++++++++++++++++++++

    public List<String> ListFromFile(File file) {
        ObjectInputStream ois = null;
        List<String> list = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            list = (ArrayList<String>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert ois != null;
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    Map<String, String> ListToMap(List<String> list) {
        Map<String, String> map = new HashMap<>();
        for (int i = 1; i < list.size(); i++) {
            if (i % 2 != 0) {
                map.put(list.get(i - 1), list.get(i));
            }

        }
        return map;
    }

    List<Byte> readCodeFromFile() {
        List<Byte> listFromFile = new ArrayList<>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File("/users/Vasya/Documents/huffman_code.txt"));
            while (fis.available() > 0) {
                listFromFile.add((byte) fis.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listFromFile;
    }


    void decoder(List<Byte> resultArray, Map<String, String> decodeMap) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("/users/Vasya/Documents/file_out.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder tmpString = new StringBuilder();
        StringBuilder stringForFindCode = new StringBuilder();

        for (int i = 0; i < resultArray.size(); i++) {
            if (i == resultArray.size() - 1) {// последний байт, который может быть неполным
                String shortString = oneZeroString(resultArray.get(i));
                int count = Integer.parseInt(decodeMap.get("count"));

                tmpString.append(shortString.substring(7 - count, 7));

            }else {
                tmpString.append(oneZeroString(resultArray.get(i)));
            }

            for (int j = 0; j < tmpString.length() ; j++) {

                stringForFindCode.append(tmpString.charAt(j));
                if (decodeMap.containsKey(stringForFindCode.toString())) {
                    try {

                       fw.write(decodeMap.get(stringForFindCode.toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stringForFindCode.delete(0, stringForFindCode.length());
                }
            }
            tmpString.delete(0, 7);
        }
        try {
            assert fw != null;
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    String oneZeroString(Byte b) {
        StringBuilder tmpString = new StringBuilder();
        StringBuilder oneZeroString = new StringBuilder();
        tmpString.append("0000000");
        tmpString.append(Integer.toBinaryString(b)); // байты, преобразованные в двоичный код

        oneZeroString.append(tmpString.substring(tmpString.length() - 7, tmpString.length()));
        return oneZeroString.toString();
    }


}



