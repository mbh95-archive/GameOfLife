package com.mbh.gol;

import com.google.auto.value.AutoValue;

import java.util.HashSet;
import java.util.Set;

public class GameOfLife implements IGameOfLife {

    private Set<Cell> liveCells = new HashSet<>();

    public boolean test(int r, int c) {
        return liveCells.contains(Cell.create(r, c));
    }

    public void set(int r, int c, boolean alive) {
        if (alive) {
            liveCells.add(Cell.create(r, c));
        } else {
            liveCells.remove(Cell.create(r, c));
        }
    }

    public void step() {

    }

    @AutoValue
    static abstract class Cell {
        static Cell create(int r, int c) {
            return new AutoValue_GameOfLife_Cell(r, c);
        }
       abstract int r();
       abstract int c();
    }
}
