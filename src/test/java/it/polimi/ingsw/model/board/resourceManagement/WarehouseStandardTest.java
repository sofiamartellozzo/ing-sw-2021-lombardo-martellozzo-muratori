package it.polimi.ingsw.model.board.resourceManagement;

import org.junit.After;
import org.junit.Before;

//See WarehouseTest
public class WarehouseStandardTest {
    WarehouseStandard warehouseStandard;

    @Before
    public void setUp() throws Exception {
        warehouseStandard = new WarehouseStandard();
    }

    @After
    public void tearDown() throws Exception {
        warehouseStandard=null;
    }
}