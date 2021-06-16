package it.polimi.ingsw.controller.factory;

import com.google.gson.internal.LinkedTreeMap;
import it.polimi.ingsw.model.card.LeaderCard;
import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static it.polimi.ingsw.model.Color.*;
import static it.polimi.ingsw.model.TypeResource.*;
import static it.polimi.ingsw.model.cardAbility.TypeAbility.*;

public class LeaderCardFactoryTest extends TestCase {
    private ArrayList<LeaderCard> leaderCards;

    public void setUp() throws FileNotFoundException {
        leaderCards = new LeaderCardFactory().createLeaderCardDeck();
    }

    public void testCreateLeaderCardDeck() {
        ArrayList<LeaderCard> expected=new ArrayList<>();

        //LeaderCard 1
        ArrayList<Object> requirements1 = new ArrayList<>();
        LinkedTreeMap<String,Object> content1_0 = new LinkedTreeMap<>();
        content1_0.put("color", GREEN);
        content1_0.put("level",(double)2);
        requirements1.add(content1_0);

        LeaderCard leaderCard1 = new LeaderCard(1,4, ADDITIONAL_POWER, COIN,requirements1);
        expected.add(leaderCard1);

        //LeaderCard 2
        ArrayList<Object> requirements2 = new ArrayList<>();
        LinkedTreeMap<String,Object> content2_0 = new LinkedTreeMap<>();
        content2_0.put("color", PURPLE);
        content2_0.put("level",(double)2);
        requirements2.add(content2_0);

        LeaderCard leaderCard2 = new LeaderCard(2,4, ADDITIONAL_POWER, STONE,requirements2);
        expected.add(leaderCard2);

        //LeaderCard 3
        ArrayList<Object> requirements3 = new ArrayList<>();
        LinkedTreeMap<String,Object> content3_0 = new LinkedTreeMap<>();
        content3_0.put("color", BLUE);
        content3_0.put("level",(double)2);
        requirements3.add(content3_0);

        LeaderCard leaderCard3 = new LeaderCard(3,4, ADDITIONAL_POWER, SERVANT,requirements3);
        expected.add(leaderCard3);

        //LeaderCard 4
        ArrayList<Object> requirements4 = new ArrayList<>();
        LinkedTreeMap<String,Object> content4_0 = new LinkedTreeMap<>();
        content4_0.put("color", YELLOW);
        content4_0.put("level",(double)2);
        requirements4.add(content4_0);

        LeaderCard leaderCard4 = new LeaderCard(4,4, ADDITIONAL_POWER, SHIELD,requirements4);
        expected.add(leaderCard4);

        //LeaderCard 5
        ArrayList<Object> requirements5 = new ArrayList<>();
        LinkedTreeMap<String,Object> content5_0 = new LinkedTreeMap<>();
        content5_0.put("color", PURPLE);
        content5_0.put("level",(double)0);
        requirements5.add(content5_0);
        LinkedTreeMap<String,Object> content5_1=new LinkedTreeMap<>();
        content5_1.put("color", PURPLE);
        content5_1.put("level",(double)0);
        requirements5.add(content5_1);
        LinkedTreeMap<String,Object> content5_2=new LinkedTreeMap<>();
        content5_2.put("color", GREEN);
        content5_2.put("level",(double)0);
        requirements5.add(content5_2);

        LeaderCard leaderCard5 = new LeaderCard(5,5, TRANSFORM_WHITE, COIN,requirements5);
        expected.add(leaderCard5);

        //LeaderCard 6
        ArrayList<Object> requirements6 = new ArrayList<>();
        LinkedTreeMap<String,Object> content6_0 = new LinkedTreeMap<>();
        content6_0.put("color", BLUE);
        content6_0.put("level",(double)0);
        requirements6.add(content6_0);
        LinkedTreeMap<String,Object> content6_1=new LinkedTreeMap<>();
        content6_1.put("color", BLUE);
        content6_1.put("level",(double)0);
        requirements6.add(content6_1);
        LinkedTreeMap<String,Object> content6_2=new LinkedTreeMap<>();
        content6_2.put("color", YELLOW);
        content6_2.put("level",(double)0);
        requirements6.add(content6_2);

        LeaderCard leaderCard6 = new LeaderCard(6,5, TRANSFORM_WHITE, STONE,requirements6);
        expected.add(leaderCard6);

        //LeaderCard 7
        ArrayList<Object> requirements7 = new ArrayList<>();
        LinkedTreeMap<String,Object> content7_0 = new LinkedTreeMap<>();
        content7_0.put("color", GREEN);
        content7_0.put("level",(double)0);
        requirements7.add(content7_0);
        LinkedTreeMap<String,Object> content7_1=new LinkedTreeMap<>();
        content7_1.put("color", GREEN);
        content7_1.put("level",(double)0);
        requirements7.add(content7_1);
        LinkedTreeMap<String,Object> content7_2=new LinkedTreeMap<>();
        content7_2.put("color", PURPLE);
        content7_2.put("level",(double)0);
        requirements7.add(content7_2);

        LeaderCard leaderCard7 = new LeaderCard(7,5, TRANSFORM_WHITE, SHIELD,requirements7);
        expected.add(leaderCard7);

        //LeaderCard 8
        ArrayList<Object> requirements8 = new ArrayList<>();
        LinkedTreeMap<String,Object> content8_0 = new LinkedTreeMap<>();
        content8_0.put("color", YELLOW);
        content8_0.put("level",(double)0);
        requirements8.add(content8_0);
        LinkedTreeMap<String,Object> content8_1=new LinkedTreeMap<>();
        content8_1.put("color", YELLOW);
        content8_1.put("level",(double)0);
        requirements8.add(content8_1);
        LinkedTreeMap<String,Object> content8_2=new LinkedTreeMap<>();
        content8_2.put("color", BLUE);
        content8_2.put("level",(double)0);
        requirements8.add(content8_2);

        LeaderCard leaderCard8 = new LeaderCard(8,5, TRANSFORM_WHITE, SERVANT,requirements8);
        expected.add(leaderCard8);

        //LeaderCard 9
        ArrayList<Object> requirements9 = new ArrayList<>();
        LinkedTreeMap<String,Object> content9_0 = new LinkedTreeMap<>();
        content9_0.put("color", BLUE);
        content9_0.put("typeResource", SHIELD);
        requirements9.add(content9_0);
        LinkedTreeMap<String,Object> content9_1=new LinkedTreeMap<>();
        content9_1.put("color", BLUE);
        content9_1.put("typeResource", SHIELD);
        requirements9.add(content9_1);
        LinkedTreeMap<String,Object> content9_2=new LinkedTreeMap<>();
        content9_2.put("color", BLUE);
        content9_2.put("typeResource",SHIELD);
        requirements9.add(content9_2);
        LinkedTreeMap<String,Object> content9_3=new LinkedTreeMap<>();
        content9_3.put("color", BLUE);
        content9_3.put("typeResource",SHIELD);
        requirements9.add(content9_3);
        LinkedTreeMap<String,Object> content9_4=new LinkedTreeMap<>();
        content9_4.put("color", BLUE);
        content9_4.put("typeResource",SHIELD);
        requirements9.add(content9_4);

        LeaderCard leaderCard9 = new LeaderCard(9,3, SPECIAL_DEPOT, COIN,requirements9);
        expected.add(leaderCard9);

        //LeaderCard 10
        ArrayList<Object> requirements10 = new ArrayList<>();
        LinkedTreeMap<String,Object> content10_0 = new LinkedTreeMap<>();
        content10_0.put("color", PURPLE);
        content10_0.put("typeResource", SERVANT);
        requirements10.add(content10_0);
        LinkedTreeMap<String,Object> content10_1=new LinkedTreeMap<>();
        content10_1.put("color", PURPLE);
        content10_1.put("typeResource", SERVANT);
        requirements10.add(content10_1);
        LinkedTreeMap<String,Object> content10_2=new LinkedTreeMap<>();
        content10_2.put("color", PURPLE);
        content10_2.put("typeResource",SERVANT);
        requirements10.add(content10_2);
        LinkedTreeMap<String,Object> content10_3=new LinkedTreeMap<>();
        content10_3.put("color", PURPLE);
        content10_3.put("typeResource",SERVANT);
        requirements10.add(content10_3);
        LinkedTreeMap<String,Object> content10_4=new LinkedTreeMap<>();
        content10_4.put("color", PURPLE);
        content10_4.put("typeResource",SERVANT);
        requirements10.add(content10_4);

        LeaderCard leaderCard10 = new LeaderCard(10,3, SPECIAL_DEPOT, SHIELD,requirements10);
        expected.add(leaderCard10);

        //LeaderCard 11
        ArrayList<Object> requirements11 = new ArrayList<>();
        LinkedTreeMap<String,Object> content11_0 = new LinkedTreeMap<>();
        content11_0.put("color", GREY);
        content11_0.put("typeResource", STONE);
        requirements11.add(content11_0);
        LinkedTreeMap<String,Object> content11_1=new LinkedTreeMap<>();
        content11_1.put("color", GREY);
        content11_1.put("typeResource", STONE);
        requirements11.add(content11_1);
        LinkedTreeMap<String,Object> content11_2=new LinkedTreeMap<>();
        content11_2.put("color", GREY);
        content11_2.put("typeResource",STONE);
        requirements11.add(content11_2);
        LinkedTreeMap<String,Object> content11_3=new LinkedTreeMap<>();
        content11_3.put("color", GREY);
        content11_3.put("typeResource",STONE);
        requirements11.add(content11_3);
        LinkedTreeMap<String,Object> content11_4=new LinkedTreeMap<>();
        content11_4.put("color", GREY);
        content11_4.put("typeResource",STONE);
        requirements11.add(content11_4);

        LeaderCard leaderCard11 = new LeaderCard(11,3, SPECIAL_DEPOT, SERVANT,requirements11);
        expected.add(leaderCard11);

        //LeaderCard 12
        ArrayList<Object> requirements12 = new ArrayList<>();
        LinkedTreeMap<String,Object> content12_0 = new LinkedTreeMap<>();
        content12_0.put("color", YELLOW);
        content12_0.put("typeResource", COIN);
        requirements12.add(content12_0);
        LinkedTreeMap<String,Object> content12_1=new LinkedTreeMap<>();
        content12_1.put("color", YELLOW);
        content12_1.put("typeResource", COIN);
        requirements12.add(content12_1);
        LinkedTreeMap<String,Object> content12_2=new LinkedTreeMap<>();
        content12_2.put("color", YELLOW);
        content12_2.put("typeResource",COIN);
        requirements12.add(content12_2);
        LinkedTreeMap<String,Object> content12_3=new LinkedTreeMap<>();
        content12_3.put("color", YELLOW);
        content12_3.put("typeResource",COIN);
        requirements12.add(content12_3);
        LinkedTreeMap<String,Object> content12_4=new LinkedTreeMap<>();
        content12_4.put("color", YELLOW);
        content12_4.put("typeResource",YELLOW);
        requirements12.add(content12_4);

        LeaderCard leaderCard12 = new LeaderCard(12,3, SPECIAL_DEPOT, STONE,requirements12);
        expected.add(leaderCard12);

        //LeaderCard 13
        ArrayList<Object> requirements13 = new ArrayList<>();
        LinkedTreeMap<String,Object> content13_0 = new LinkedTreeMap<>();
        content13_0.put("color", YELLOW);
        content13_0.put("level", (double)0);
        requirements13.add(content13_0);
        LinkedTreeMap<String,Object> content13_1=new LinkedTreeMap<>();
        content13_1.put("color", PURPLE);
        content13_1.put("level", (double)0);
        requirements13.add(content13_1);

        LeaderCard leaderCard13 = new LeaderCard(13,2, DISCOUNT, COIN,requirements13);
        expected.add(leaderCard13);

        //LeaderCard 14
        ArrayList<Object> requirements14 = new ArrayList<>();
        LinkedTreeMap<String,Object> content14_0 = new LinkedTreeMap<>();
        content14_0.put("color", GREEN);
        content14_0.put("level", (double)0);
        requirements14.add(content14_0);
        LinkedTreeMap<String,Object> content14_1=new LinkedTreeMap<>();
        content14_1.put("color", BLUE);
        content14_1.put("level", (double)0);
        requirements14.add(content14_1);

        LeaderCard leaderCard14 = new LeaderCard(14,2, DISCOUNT, STONE,requirements14);
        expected.add(leaderCard14);

        //LeaderCard 15
        ArrayList<Object> requirements15 = new ArrayList<>();
        LinkedTreeMap<String,Object> content15_0 = new LinkedTreeMap<>();
        content15_0.put("color", BLUE);
        content15_0.put("level", (double)0);
        requirements15.add(content15_0);
        LinkedTreeMap<String,Object> content15_1=new LinkedTreeMap<>();
        content15_1.put("color", PURPLE);
        content15_1.put("level", (double)0);
        requirements15.add(content15_1);

        LeaderCard leaderCard15 = new LeaderCard(15,2, DISCOUNT, SHIELD,requirements15);
        expected.add(leaderCard15);

        //LeaderCard 16
        ArrayList<Object> requirements16 = new ArrayList<>();
        LinkedTreeMap<String,Object> content16_0 = new LinkedTreeMap<>();
        content16_0.put("color", YELLOW);
        content16_0.put("level", (double)0);
        requirements16.add(content16_0);
        LinkedTreeMap<String,Object> content16_1=new LinkedTreeMap<>();
        content16_1.put("color", GREEN);
        content16_1.put("level", (double)0);
        requirements16.add(content16_1);

        LeaderCard leaderCard16 = new LeaderCard(16,2, DISCOUNT, SERVANT,requirements16);
        expected.add(leaderCard16);

        //Check expected with factory(json)
        for(int i=0;i<16;i++){
            System.out.println(i+1);
            LeaderCard expectedLeaderCard = expected.get(i);
            LeaderCard leaderCard = leaderCards.get(i);
            assertSame(expectedLeaderCard.getCardID(),leaderCard.getCardID());
            assertSame(expectedLeaderCard.getVictoryPoints(),leaderCard.getVictoryPoints());
            assertEquals(expectedLeaderCard.getState().toString(),leaderCard.getState().toString());
            assertEquals(expectedLeaderCard.getSpecialAbility().toString(),leaderCard.getSpecialAbility().toString());
            assertEquals(expectedLeaderCard.getSpecialResource(),leaderCard.getSpecialResource());
            assertSame(expectedLeaderCard.getRequirements().size(),leaderCard.getRequirements().size());
            for(int j=0;j<leaderCard1.getRequirements().size();j++){
                LinkedTreeMap<String,Object> expectedRequirement = ((LinkedTreeMap)expectedLeaderCard.getRequirements().get(j));
                LinkedTreeMap<String,Object> requirement = ((LinkedTreeMap)leaderCard.getRequirements().get(j));
                assertSame(expectedRequirement.size(),requirement.size());
                Object[] expectedKeys = expectedRequirement.keySet().toArray();
                Object[] keys = requirement.keySet().toArray();
                Object[] expectedValues = expectedRequirement.values().toArray();
                Object[] values = requirement.values().toArray();
                assertSame(expectedKeys.length,expectedValues.length);
                assertSame(expectedKeys.length,keys.length);
                assertSame(expectedValues.length,values.length);
                assertSame(keys.length,values.length);
                for(int k=0;k<expectedKeys.length;k++){
                    String expectedKey = (String) expectedKeys[k];
                    String key = (String) keys[k];
                    Object expectedValue = expectedValues[k];
                    Object value = values[k];
                    assertEquals(expectedKey,key);
                    if(key.equals("level")) assertEquals(expectedValue,value);
                    if(key.equals("color")) assertEquals(expectedValue.toString(),value.toString());
                    if(key.equals("typeResource")) assertEquals(expectedValue.toString(),value.toString());
                }
            }
        }
    }
}