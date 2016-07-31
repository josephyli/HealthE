/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthe;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
public class SleepControllerTest {
    
    public SleepControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of initData method, of class SleepController.
     */
    @Test
    public void testInitData() {
        System.out.println("initData");
        User user = null;
        SleepController instance = new SleepController();
        instance.initData(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Generate method, of class SleepController.
     */
    @Test
    public void testGenerate() throws Exception {
        System.out.println("Generate");
        ActionEvent event = null;
        SleepController instance = new SleepController();
        instance.Generate(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initialize method, of class SleepController.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        SleepController instance = new SleepController();
        instance.initialize(url, rb);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeChart method, of class SleepController.
     */
    @Test
    public void testMakeChart() {
        System.out.println("makeChart");
        SleepController instance = new SleepController();
        instance.makeChart();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
