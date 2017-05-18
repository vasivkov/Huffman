package com.training.vasivkov;

/**
 * Created by vasya on 18/05/17.
 */
class Frequencies implements Comparable<Frequencies> {
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

    public static Frequencies sum(Frequencies f1, Frequencies f2) {
        return new Frequencies(f1.symbol + f2.symbol, f1.countOfSymbol + f2.countOfSymbol);
    }

    @Override
    public String toString() {
        return symbol + " - " + countOfSymbol;
    }

    @Override
    public int compareTo(Frequencies o) {
        return o.getCountOfSymbol() - this.getCountOfSymbol();
    }
}
