package com.mbh.gol;

import org.junit.Assert;
import org.junit.Test;

public class IGameOfLifeTest {

    private IGameOfLife gol;

    @org.junit.Before
    public void setUp() throws Exception {
        gol = new GameOfLife();
    }

    @Test
    public void testCreation() throws Exception {
        Assert.assertNotNull(gol);
    }

    @Test
    public void testGetBasic() throws Exception {
        Assert.assertFalse(gol.get(0,0));
    }

    @Test
    public void testSetBasic() throws Exception {
        gol.set(0,0, true);
        Assert.assertTrue(gol.get(0,0));
    }
}