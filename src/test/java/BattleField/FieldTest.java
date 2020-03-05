package BattleField;

import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTest {

    Field field = new Field(10,20);

    @Test
    public void retwarstart()
    {
        System.out.println(field.retwarstart());
        field.setWar_start(true);
        System.out.println(field.retwarstart());
    }

    @Test
    public void anyHuluwaAlive()
    {
        System.out.println(field.anyHuluwaAlive());
    }

    @Test
    public void anyMonsterAlive()
    {
        System.out.println(field.anyMonsterAlive());
    }
}