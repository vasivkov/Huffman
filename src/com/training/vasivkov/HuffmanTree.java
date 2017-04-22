package com.training.vasivkov;

import java.io.File;
import java.util.*;


/**
 * Created by vasya on 19/04/17.
 */
public class HuffmanTree {

    public List<Frequencies> readFile(File file) {
        return null;
    }

    public Map<String, String> findCodes(List<Frequencies> frequenciesList) {
        Map<String, String> codeMap = new HashMap<>();
        for (Frequencies aFrequenciesList : frequenciesList) { // забиваю карту ключами из frequenciesList и "пустыми" значеиями;
            codeMap.put(aFrequenciesList.getSymbol(), "");
        }

        int countOfSymbols = frequenciesList.size();


        for (int i = 0; i <= countOfSymbols; i++) {
            if (frequenciesList.size() == 1) { // чтобы осталось как минимум 2 заначения, чтобы было что объединять
                System.out.println(frequenciesList);
                break;
            }

            frequenciesList.sort(new Comparator<Frequencies>() { // сортирую лист по частоте символов
                @Override
                public int compare(Frequencies o1, Frequencies o2) {
                    return o2.getCountOfSymbol() - o1.getCountOfSymbol();
                }
            });

            System.out.println(frequenciesList); // ПОТОМ УБРАТЬ!!!
            // нахожу сумму вероятностей 2х последних символов (самые редкие)
            int sumCount = frequenciesList.get(frequenciesList.size() - 1).getCountOfSymbol() + frequenciesList.get(frequenciesList.size() - 2).getCountOfSymbol();
            // конкатенирую 2 самых редких символа (чтобы как-то обозвать новую пару). НЕ ДОРОГАЯ ЛИ ЭТО ОПЕРАЦИЯ???
            String sumSmbols = frequenciesList.get(frequenciesList.size() - 1).getSymbol() + frequenciesList.get(frequenciesList.size() - 2).getSymbol(); //

            String[] letters = frequenciesList.get(frequenciesList.size() - 1).getSymbol().split(""); // разбиваю набор символов на отдельные символы
            for (int j = 0; j < letters.length; j++) {
                codeMap.put(letters [j], "1" + codeMap.get(letters [j])  ); // изменяю значения в карте добавляя вперед 1
            }
            frequenciesList.remove(frequenciesList.size() - 1); // удаляю последнее значение из листа

            letters = frequenciesList.get(frequenciesList.size() - 1).getSymbol().split(""); // те же действия еще раз, но добавляю 0;
            for (int j = 0; j < letters.length; j++) {
                codeMap.put(letters [j], "0" + codeMap.get(letters [j])  );
            }
            frequenciesList.remove(frequenciesList.size() - 1);

            frequenciesList.add(new Frequencies(sumSmbols, sumCount)); // вставляю в лист результирующее значение

        }
        return codeMap;
    }

    public static void main(String[] args) {

        Map<String, String> codeMap = new HashMap<>(); // карта, где ключ - символ. В значении будет формироваться код символа

        List<Frequencies> frequenciesList = new ArrayList<>(); // Лист, который содержит пары значений: символ и его частоту

        frequenciesList.add(new Frequencies("a", 7));
        frequenciesList.add(new Frequencies("o", 12));
        frequenciesList.add(new Frequencies("s", 3));
        frequenciesList.add(new Frequencies("d", 5));
        frequenciesList.add(new Frequencies("n", 3));
        frequenciesList.add(new Frequencies("l", 8));
        frequenciesList.add(new Frequencies(" ", 14));
        frequenciesList.add(new Frequencies("r", 8));
        frequenciesList.add(new Frequencies("e", 7));
        frequenciesList.add(new Frequencies("q", 10));

        for (Frequencies aFrequenciesList : frequenciesList) { // забиваю карту ключами из frequenciesList и "пустыми" значеиями;
            codeMap.put(aFrequenciesList.getSymbol(), "");
        }

        int countOfSymbols = frequenciesList.size(); // для "докуда бегать for-у"


        for (int i = 0; i <= countOfSymbols; i++) {
            if (frequenciesList.size() == 1) { // чтобы осталось как минимум 2 заначения, чтобы было что объединять
                System.out.println(frequenciesList);
                break;
            }

            frequenciesList.sort(new Comparator<Frequencies>() { // сортирую лист по частоте символов
                @Override
                public int compare(Frequencies o1, Frequencies o2) {
                    return o2.getCountOfSymbol() - o1.getCountOfSymbol();
                }
            });

            System.out.println(frequenciesList); // ПОТОМ УБРАТЬ!!!
            // нахожу сумму вероятностей 2х последних символов (самые редкие)
            int sumCount = frequenciesList.get(frequenciesList.size() - 1).getCountOfSymbol() + frequenciesList.get(frequenciesList.size() - 2).getCountOfSymbol();
            // конкатенирую 2 самых редких символа (чтобы как-то обозвать новую пару). НЕ ДОРОГАЯ ЛИ ЭТО ОПЕРАЦИЯ???
            String sumSmbols = frequenciesList.get(frequenciesList.size() - 1).getSymbol() + frequenciesList.get(frequenciesList.size() - 2).getSymbol(); //

            String[] letters = frequenciesList.get(frequenciesList.size() - 1).getSymbol().split(""); // разбиваю набор символов на отдельные символы
            for (int j = 0; j < letters.length; j++) {
                codeMap.put(letters [j], "1" + codeMap.get(letters [j])  ); // изменяю значения в карте добавляя вперед 1
            }
            frequenciesList.remove(frequenciesList.size() - 1); // удаляю последнее значение из листа

            letters = frequenciesList.get(frequenciesList.size() - 1).getSymbol().split(""); // те же действия еще раз, но добавляю 0;
            for (int j = 0; j < letters.length; j++) {
                codeMap.put(letters [j], "0" + codeMap.get(letters [j])  );
            }
            frequenciesList.remove(frequenciesList.size() - 1);

            frequenciesList.add(new Frequencies(sumSmbols, sumCount)); // вставляю в лист результирующее значение

        }
        System.out.println(codeMap); // ПОТОМ УБРАТЬ!!!
    }

}


class Frequencies {
    private String symbol;
    private Integer countOfSymbol;

    public Frequencies(String symbol, Integer countOfSymbol) {
        this.symbol = symbol;
        this.countOfSymbol = countOfSymbol;
    }

    public Integer getCountOfSymbol() {
        return countOfSymbol;
    }

    public void setCountOfSymbol(Integer countOfSymbol) {
        this.countOfSymbol = countOfSymbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol + " - " + countOfSymbol;
    }
}