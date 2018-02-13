package com.mbh.gol;

public interface IGameOfLife {
    boolean get(int r, int c);

    void set(int r, int c, boolean alive);

    void step();
}
