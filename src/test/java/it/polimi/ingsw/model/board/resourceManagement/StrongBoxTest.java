package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class StrongBoxTest {
    StrongBox strongBox;

    @Before
    public void setUp() throws Exception {
        strongBox = new StrongBox();
    }

    @After
    public void tearDown() throws Exception {
        strongBox=null;
    }

    @Test
    public void getNumberResources() throws InvalidActionException {
        assertSame(0,strongBox.getNumberResources());
        ArrayList<Resource> resources = new ArrayList<>();
        for(int i=0;i<10;i++){
            resources.add(new Resource(Color.YELLOW));
            resources.add(new Resource(Color.BLUE));
            resources.add(new Resource(Color.PURPLE));
            resources.add(new Resource(Color.GREY));
        }
        strongBox.addResources(resources);
        assertSame(10*4,strongBox.getNumberResources());
        resources.clear();
        for(int i=0;i<3;i++){
            resources.add(new Resource(Color.BLUE));
            resources.add(new Resource(Color.GREY));
        }
        strongBox.removeResources(resources);
        assertSame(10*4-2*3,strongBox.getNumberResources());
    }

    @Test
    public void getContent() throws InvalidActionException {
        ArrayList<Resource> expected = new ArrayList<>();
        assertEquals(expected,strongBox.getContent());
        ArrayList<Resource> resources = new ArrayList<>();
        for(int i=0;i<10;i++){
            resources.add(new Resource(Color.YELLOW));
            resources.add(new Resource(Color.BLUE));
            resources.add(new Resource(Color.PURPLE));
            resources.add(new Resource(Color.GREY));
        }
        strongBox.addResources(resources);
        expected.addAll(resources);
        assertEquals(expected,strongBox.getContent());
        resources.clear();
        for(int i=0;i<3;i++){
            resources.add(new Resource(Color.BLUE));
            for(int j=0;j<expected.size();j++){
                if(expected.get(j).getType().equals(new Resource(Color.BLUE).getType())){
                    expected.remove(j);
                    break;
                }
            }
            resources.add(new Resource(Color.GREY));
            for(int j=0;j<expected.size();j++){
                if(expected.get(j).getType().equals(new Resource(Color.GREY).getType())){
                    expected.remove(j);
                    break;
                }
            }
        }

        strongBox.removeResources(resources);
        assertEquals(expected,strongBox.getContent());

    }

    @Test
    public void addResources() {
        ArrayList<Resource> expected = new ArrayList<>();
        assertEquals(expected,strongBox.getContent());
        ArrayList<Resource> resources = new ArrayList<>();
        for(int i=0;i<10;i++){
            resources.add(new Resource(Color.YELLOW));
            resources.add(new Resource(Color.BLUE));
            resources.add(new Resource(Color.PURPLE));
            resources.add(new Resource(Color.GREY));
        }
        strongBox.addResources(resources);
        expected.addAll(resources);
        assertEquals(expected,strongBox.getContent());
    }

    @Test (expected = InvalidActionException.class)
    public void removeResources_StrongBoxEmpty_InvalidActionException() throws InvalidActionException {
        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(new Resource(Color.GREY));
        strongBox.removeResources(resources);
    }

    @Test (expected = InvalidActionException.class)
    public void removeResources_NotEnoughResources_InvalidActionException() throws InvalidActionException{
        ArrayList<Resource> resources = new ArrayList<>();
        for(int i=0;i<10;i++){
            resources.add(new Resource(Color.YELLOW));
            resources.add(new Resource(Color.BLUE));
        }
        strongBox.addResources(resources);
        resources.clear();
        for(int i=0;i<3;i++){
            resources.add(new Resource(Color.BLUE));
            resources.add(new Resource(Color.GREY));
        }
        strongBox.removeResources(resources);
    }

    @Test
    //This method tests also the removeResource the checkResources private methods
    public void removeResources_StrongBoxNotEmpty_NoException() throws InvalidActionException {
        ArrayList<Resource> expected = new ArrayList<>();
        assertEquals(expected,strongBox.getContent());
        ArrayList<Resource> resources = new ArrayList<>();
        for(int i=0;i<10;i++){
            resources.add(new Resource(Color.YELLOW));
            resources.add(new Resource(Color.BLUE));
            resources.add(new Resource(Color.PURPLE));
            resources.add(new Resource(Color.GREY));
        }
        strongBox.addResources(resources);
        expected.addAll(resources);
        assertEquals(expected,strongBox.getContent());
        resources.clear();
        for(int i=0;i<3;i++){
            resources.add(new Resource(Color.BLUE));
            for(int j=0;j<expected.size();j++){
                if(expected.get(j).getType().equals(new Resource(Color.BLUE).getType())){
                    expected.remove(j);
                    break;
                }
            }
            resources.add(new Resource(Color.GREY));
            for(int j=0;j<expected.size();j++){
                if(expected.get(j).getType().equals(new Resource(Color.GREY).getType())){
                    expected.remove(j);
                    break;
                }
            }
        }
        strongBox.removeResources(resources);
        assertEquals(expected,strongBox.getContent());
    }





}