package com.mbh.gol;

public interface IGameOfLife {
    boolean test(int r, int c);

    void set(int r, int c, boolean alive);

    void step();

    default boolean testHelper(int baseR, int baseC, boolean[][] stencil) {
        for (int r = 0; r < stencil.length; r++) {
            boolean[] stencilRow = stencil[r];
            for (int c = 0; c < stencilRow.length; c++) {
                if (test(baseR + r, baseC + c) != stencil[r][c]) {
                    return false;
                }
            }
        }
        return true;
    }
    default void setHelper(int baseR, int baseC, boolean[][] stencil) {
        for (int r = 0; r < stencil.length; r++) {
            boolean[] stencilRow = stencil[r];
            for (int c = 0; c < stencilRow.length; c++) {
                set(baseR + r, baseC + c, stencil[r][c]);
            }
        }
    }
}
