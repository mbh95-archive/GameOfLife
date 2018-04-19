package com.mbh.gol;

import org.junit.Assert;
import org.junit.Test;

public class GameOfLifeTest {

    private IGameOfLife gol;

    @org.junit.Before
    public void setUp() {
        gol = new GameOfLife();
    }

    @Test
    public void testCreation() {
        Assert.assertNotNull(gol);
    }

    @Test
    public void testInit() {
        Assert.assertFalse(gol.test(0, 0));
    }

    @Test
    public void testSetAlive() {
        gol.set(0, 0, true);
        Assert.assertTrue(gol.test(0, 0));
    }

    @Test
    public void testSetDead() {
        gol.set(0, 0, true);
        gol.set(0, 0, false);
        Assert.assertFalse(gol.test(0, 0));
    }

    @Test
    public void testUnderpopulation() throws Exception {
        gol.set(0, 0, true);
        gol.step();
        Assert.assertFalse(gol.test(0, 0));
    }

    @Test
    public void testOverpopulation() throws Exception {
        gol.setHelper(-1, -1, new boolean[][]{
                {true, false, false},
                {false, true, true},
                {false, true, true}});
        gol.step();
        Assert.assertFalse(gol.test(0, 0));
    }

    @Test
    public void testDeadRising() throws Exception {
        gol.setHelper(0, 0, new boolean[][]{
                {false, true},
                {true, true}});
        gol.step();
        Assert.assertTrue(gol.test(0, 0));
    }

    @Test
    public void testStableState() throws Exception {
        gol.setHelper(0, 0, new boolean[][]{
                {true, true},
                {true, true}});
        for (int i = 0; i < 100; i++) {
            gol.step();
        }
        Assert.assertTrue(gol.testHelper(0, 0, new boolean[][]{
                {true, true},
                {true, true}}));
    }

    @Test
    public void testOscillator() throws Exception {
        gol.setHelper(0, 0, new boolean[][]{
                {false, true, false},
                {false, true, false},
                {false, true, false}});
        for (int i = 0; i < 100; i++) {
            gol.step();
            Assert.assertTrue(gol.testHelper(0,0 ,new boolean[][]{
                    {false, false, false},
                    {true, true, true},
                    {false, false, false}}));
            gol.step();
            Assert.assertTrue(gol.testHelper(0,0 ,new boolean[][]{
                    {false, true, false},
                    {false, true, false},
                    {false, true, false}}));
        }
    }
}