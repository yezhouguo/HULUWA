package Creatures;

import javafx.scene.image.Image;

public class Monsters extends Creature
{
    public Monsters()
    {
        super(-1,-1,false);
        this.name="喽啰";
        this.image =  new Image(getClass().getClassLoader().getResource("pic/monsters.jpg").toString(),50,50,false,false);
    }

    public Monsters(int x,int y)
    {
        super(x,y,false);
        this.name="喽啰";
        this.image =  new Image(getClass().getClassLoader().getResource("pic/monsters.jpg").toString(),50,50,false,false);
    }
}