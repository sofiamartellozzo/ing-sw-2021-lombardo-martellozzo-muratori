package it.polimi.ingsw.model.board.resourceManagement;

import it.polimi.ingsw.exception.InvalidActionException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.TypeResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

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
    public void addResources() throws InvalidActionException {
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
    public void addResources_IncorrectInput_InvalidActionException() throws InvalidActionException {
        Random random = new Random();
        boolean r = random.nextBoolean();
        if(r) {
            strongBox.addResources(null);
        }else{
            strongBox.addResources(new ArrayList<>());
        }
    }

    @Test (expected = InvalidActionException.class)
    public void removeResources_IncorrectInput_InvalidActionException() throws InvalidActionException {
        Random random = new Random();
        boolean r = random.nextBoolean();
        if(r){
            strongBox.removeResources(null);
        }else{
            strongBox.removeResources(new ArrayList<>());
        }
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

    @Test
    public void getInstanceStrongbox() throws InvalidActionException {
        ArrayList<TypeResource> expected = new ArrayList<>();
        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        ArrayList<Resource> resourcesToRemove = new ArrayList<>();
        Random randomNumber = new Random();
        int add = randomNumber.nextInt(100);
        for(int i=0;i<add;i++){
            int r = randomNumber.nextInt(4);
            if(r==0){
                resourcesToAdd.add(new Resource(TypeResource.SHIELD));
                expected.add(TypeResource.SHIELD);
            }else if(r==1){
                resourcesToAdd.add(new Resource(TypeResource.COIN));
                expected.add(TypeResource.COIN);
            }else if(r==2){
                resourcesToAdd.add(new Resource(TypeResource.SERVANT));
                expected.add(TypeResource.SERVANT);
            }else if(r==3){
                resourcesToAdd.add(new Resource(TypeResource.STONE));
                expected.add(TypeResource.STONE);
            }
        }
        strongBox.addResources(resourcesToAdd);
        assertEquals(expected,strongBox.getInstanceStrongbox());
        int remove = randomNumber.nextInt(100);
        for(int i=0;i<remove;i++){
            int r = randomNumber.nextInt(4);
            Resource resource = null;
            if(r==0){
                 resource = new Resource(TypeResource.SHIELD);
            }else if(r==1){
                 resource = new Resource(TypeResource.COIN);
            }else if(r==2){
                 resource = new Resource(TypeResource.SERVANT);
            }if(r==3){
                 resource = new Resource(TypeResource.STONE);
            }
            resourcesToRemove.add(resource);
            try{
                strongBox.removeResources(resourcesToRemove);
                expected.remove(resource.getType());
                resourcesToRemove.clear();
            }catch(InvalidActionException e){
                resourcesToRemove.clear();
            }
        }
        assertEquals(expected,strongBox.getInstanceStrongbox());
    }

    @Test
    public void countResource() throws InvalidActionException {
        Random random = new Random();
        int n = random.nextInt(100);
        int shield =0;
        int servant=0;
        int stone=0;
        int coin=0;
        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        for(int i=0;i<n;i++){
            int r = random.nextInt(4);
            if(r==0){
                resourcesToAdd.add(new Resource(TypeResource.COIN));
                coin++;
            }else if(r==1){
                resourcesToAdd.add(new Resource(TypeResource.SHIELD));
                shield++;
            }else if(r==2){
                resourcesToAdd.add(new Resource(TypeResource.STONE));
                stone++;
            }else if(r==3){
                resourcesToAdd.add(new Resource(TypeResource.SERVANT));
                servant++;
            }
        }
        strongBox.addResources(resourcesToAdd);
        assertSame(coin,strongBox.countResource(strongBox.getContent(),new Resource(TypeResource.COIN)));

        assertSame(shield,strongBox.countResource(strongBox.getContent(),new Resource(TypeResource.SHIELD)));

        assertSame(stone,strongBox.countResource(strongBox.getContent(),new Resource(TypeResource.STONE)));

        assertSame(servant,strongBox.countResource(strongBox.getContent(),new Resource(TypeResource.SERVANT)));
    }

    @Test
    public void checkEnoughResources() throws InvalidActionException {
        ArrayList<Resource> resourcesToCheck = new ArrayList<>();
        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToCheck.add(new Resource(TypeResource.COIN));
        resourcesToCheck.add(new Resource(TypeResource.SHIELD));
        resourcesToAdd.add(new Resource(TypeResource.SHIELD));
        strongBox.addResources(resourcesToAdd);
        resourcesToAdd.clear();
        assertFalse(strongBox.checkEnoughResources(resourcesToCheck));
        resourcesToAdd.add(new Resource(TypeResource.SHIELD));
        strongBox.addResources(resourcesToAdd);
        resourcesToAdd.clear();
        assertFalse(strongBox.checkEnoughResources(resourcesToCheck));
        resourcesToAdd.add(new Resource(TypeResource.COIN));
        strongBox.addResources(resourcesToAdd);
        resourcesToAdd.clear();
        assertTrue(strongBox.checkEnoughResources(resourcesToCheck));
        resourcesToAdd.add(new Resource(TypeResource.STONE));
        strongBox.addResources(resourcesToAdd);
        resourcesToAdd.clear();
        assertTrue(strongBox.checkEnoughResources(resourcesToCheck));
        resourcesToAdd.add(new Resource(TypeResource.STONE));
        strongBox.addResources(resourcesToAdd);
        resourcesToAdd.clear();
        assertTrue(strongBox.checkEnoughResources(resourcesToCheck));
    }

}