package BattleField;

import Creatures.Creature;
import javafx.scene.canvas.Canvas;

import static java.lang.System.exit;


public class Space
{
    private int x;
    private int y;
    private Creature creature;
    public boolean isempty;
    //public Creature[][] space;

    public Space(int x,int y)
    {
        this.x = x;
        this.y = y;
        this.isempty = true;
        creature = null;
    }

    public Creature retCre()
    {
        return this.creature;
    }

    public void setCre(Creature temp)
    {
        creature = temp;
        this.isempty = false;
        temp.changeTheposition(x,y);
    }

    public void movCre(Space temp)
    {
        creature = temp.creature;
        creature.changeTheposition(x,y);
        isempty = false;
        temp.creature = null;
        temp.isempty = true;
    }

    public void swapCre(Space temp)
    {
        Creature tempswap = temp.creature;
        temp.creature = creature;
        creature = tempswap;
        creature.changeTheposition(x,y);
        temp.creature.changeTheposition(temp.x,temp.y);
    }


    public void initSpace()      //初始化空间
    {
        this.creature=null;
        this.isempty=true;
    }


    public void showthespace(int x,int y)      //输出地图
    {
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<x;j++)
            {
                if(this.creature==null)
                {
                    System.out.print("  ");
                }
                else
                {
                    System.out.print(this.creature.retname());
                }
                
            }
            System.out.print("\n");
        }
    }

    public void showThecreature(Canvas canvas) {
        if(isempty != true) {
            //creature.getTheinfo();
            if (canvas != null)
                creature.show_GUI(canvas);
            else {
                System.out.println("no canvas");
                exit(-1);
            }
        }
    }


    public void killPos()
    {
        if(this.creature!=null)
        {
            if(this.creature.retisAlive() == true)
            {
                this.creature.suicide();
            }
            this.creature = null;
        }
        this.isempty = true;
    }
}