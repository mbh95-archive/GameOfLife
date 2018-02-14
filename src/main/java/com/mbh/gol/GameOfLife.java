package com.mbh.gol;

import com.google.auto.value.AutoValue;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<Cell> nextLiveCells = new HashSet<>();

        // Get the set of live cells which will stay alive for the next generation.
        Set<Cell> stillAliveCells = liveCells.stream()
                .filter(liveCell -> {
                    long liveNeighborCount = liveCell.getNeighbors().stream()
                            .filter(neighborCell -> liveCells.contains(neighborCell))
                            .count();
                    return liveNeighborCount == 2 || liveNeighborCount == 3;
                }).collect(Collectors.toSet());
        nextLiveCells.addAll(stillAliveCells);

        // Get the set of dead cells which will come alive for the next generation.
        Set<Cell> risingDeadCells = liveCells.stream()
                .flatMap(liveCell -> liveCell.getNeighbors().stream())
                .filter(neighborCell -> !liveCells.contains(neighborCell))
                .filter(deadCell -> {
                    long liveNeighborCount = deadCell.getNeighbors().stream()
                            .filter(neighborCell -> liveCells.contains(neighborCell))
                            .count();
                    return liveNeighborCount == 3;
                }).collect(Collectors.toSet());
        nextLiveCells.addAll(risingDeadCells);

        liveCells = nextLiveCells;
    }

    @AutoValue
    static abstract class Cell {
        static Cell create(int r, int c) {
            return new AutoValue_GameOfLife_Cell(r, c);
        }

        abstract int r();

        abstract int c();

        private Set<Cell> getNeighbors() {
            Set<Cell> neighborSet = new HashSet<>();
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr == 0 && dc == 0) {
                        continue;
                    }
                    neighborSet.add(Cell.create(r() + dr, c() + dc));
                }
            }
            return neighborSet;
        }
    }
}