package Creatures;

import javafx.scene.image.Image;

public class Snake extends Creature
{
    public Snake()
    {
        super(-1,-1,false);
        this.name="蛇精";
        this.image =  new Image(getClass().getClassLoader().getResource("pic/snake.jpg").toString(),50,50,false,false);
    }

    public Snake(int x,int y)
    {
        super(x,y,false);
        this.name="蛇精";
        this.image =  new Image(getClass().getClassLoader().getResource("pic/snake.jpg").toString(),50,50,false,false);
    }
}