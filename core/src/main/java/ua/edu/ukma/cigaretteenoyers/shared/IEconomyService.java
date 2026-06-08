package ua.edu.ukma.cigaretteenoyers.shared;

public interface IEconomyService {
    boolean trySpend(int amount);
    void    addMoney(int amount);
    int     getBalance();
    boolean isBankrupt();
}
