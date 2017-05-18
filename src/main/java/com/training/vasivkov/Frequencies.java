package com.training.vasivkov;

/**
 * Created by vasya on 18/05/17.
 */
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
