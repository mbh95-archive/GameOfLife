package com.mbh.gol;

import org.junit.Assert;
import org.junit.Test;

public class IGameOfLifeTest {

    private IGameOfLife gol;

    @org.junit.Before
    public void setUp()  {
        gol = new GameOfLife();
    }

    @Test
    public void testCreation() {
        Assert.assertNotNull(gol);
    }

    @Test
    public void testGeInit() {
        Assert.assertFalse(gol.get(0,0));
    }

    @Test
    public void testSetAlive() {
        gol.set(0,0, true);
        Assert.assertTrue(gol.get(0,0));
    }

    @Test
    public void testSetDead() {
        gol.set(0,0, true);
        gol.set(0,0, false);
        Assert.assertFalse(gol.get(0,0));
    }
}