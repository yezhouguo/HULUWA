package Creatures;

import javafx.scene.image.Image;

public class Grandpa extends Creature
{
    public Grandpa()
    {
        super(-1,-1,true);
        this.name="爷爷";
        this.image =  new Image(getClass().getClassLoader().getResource("pic/grandpa.jpg").toString(),50,50,false,false);
    }

    public Grandpa(int x,int y)
    {
        super(x,y,true);
        this.name="爷爷";
        this.image =  new Image(getClass().getClassLoader().getResource("pic/grandpa.jpg").toString(),50,50,false,false);
    }

}