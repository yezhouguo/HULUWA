package Creatures;

import javafx.scene.image.Image;

public class Scorpion extends Creature
{
    public Scorpion()
    {
        super(-1,-1,false);
        this.name="蝎精";
        this.image =  new Image(getClass().getClassLoader().getResource("pic/scorpion.jpg").toString(),50,50,false,false);
    }

    public Scorpion(int x,int y)
    {
        super(x,y,false);
        this.name="蝎精";
        this.image =  new Image(getClass().getClassLoader().getResource("pic/scorpion.jpg").toString(),50,50,false,false);
    }
}