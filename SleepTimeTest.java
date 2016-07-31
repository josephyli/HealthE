/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthe;

import java.sql.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author josephli
 */
public class SleepTimeTest {
    SleepTime testSleep;
    
    public SleepTimeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        testSleep = new SleepTime("test", new Date(2016, 5, 5), 1.0);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getHours method, of class SleepTime.
     */
    @Test
    public void testGetHours() {
        System.out.println("getHours");
        Double expResult = 1.0;
        Double result = testSleep.getHours();
        assertEquals(expResult, result);

    }

    /**
     * Test of setHours method, of class SleepTime.
     */
    @Test
    public void testSetHours() {
        System.out.println("setHours");
        Double hours = null;
        Date testDate = new Date(2016, 8, 6);
        SleepTime instance = new SleepTime("test", testDate, 5.1);
        instance.setHours(hours);
        
        assertEquals(instance.getHours(), hours);
    }

    /**
     * Test of aDate method, of class SleepTime.
     */
    @Test
    public void testADate() {
        System.out.println("aDate");
        Date testDate = new Date(2016, 8, 6);
        SleepTime instance = new SleepTime("test", testDate, 5.1);
        
        Date result = instance.getDate();
        assertEquals(testDate, result);
    }
    
}
