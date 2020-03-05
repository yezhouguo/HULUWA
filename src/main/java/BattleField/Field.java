package BattleField;

import Creatures.Creature;
import javafx.scene.canvas.*;

import static java.lang.System.exit;

public class Field
{
    private int x;
    private int y;
    private boolean war_start;
    public Space[][] field;

    public Field(int x, int y)
    {
        field = new Space[x][y];
        this.x = x;
        this.y = y;
        for(int i =0;i<x;i++)
        {
            for(int j = 0; j < y;j++)
            {
                field[i][j] = new Space(i,j);
            }
        }
        war_start = false;
    }

    public boolean retwarstart()
    {
        return war_start;
    }

    public void putTheCre(Creature temp, int i, int j)
    {
        if(i < 0 || i > 9 || j < 0 || j > 19)
        {
            System.err.println("坐标错误!");
            exit(-1);
        }
        field[i][j].setCre(temp);
    }

    public void movTheCre(Creature temp,int i,int j)
    {
        int x = temp.retx();
        int y = temp.rety();
        //未放置
        if(x == -1 && y == -1)
        {
            field[i][j].setCre(temp);
        }
        else if(x != i || y != j)
        {
            if(field[i][j].isempty == false)
            {
                if(war_start == false) field[i][j].swapCre(field[x][y]); //战斗没开始可以交换位置
            }
            else
            {
                field[i][j].movCre(field[x][y]);
            }
        }
    }

    public void setWar_start(boolean state)
    {
        war_start = state;
    }


    public void showThecreature(int i, int j, Canvas canvas)
    {
        field[i][j].showThecreature(canvas);
    }

    public void remove(int i, int j)
    {
        this.field[i][j].initSpace();
    }

    public Creature get_Creature(int i,int j)
    {
        return this.field[i][j].retCre(); //返回
    }

    public int[][] initFiled(Creature[] creatures)
    {
        int [][]init = new int[10][20];
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 20; j++)
            {
                Creature t = this.field[i][j].retCre();
                if (t == null) init[i][j] = -1;
                else
                    {
                    for(int k = 0; k < creatures.length; k++)
                    {
                        if(creatures[k] == t)
                        {
                            init[i][j] = k;
                        }
                    }
                }
            }
        }
        return init; //返回日志数组
    }

    public void showFiled(int x,int y)
    {
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y;j++)
            {
                if(this.field[i][j].retCre()!=null)
                {
                    System.out.print(this.field[i][j].retCre().retname());
                }
                else
                {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }


    public Creature checkEnemy(int x, int y)
    {
        if (x < 0 || x > 9 || y < 0 || y > 19)
        {
            return null;
        }
        Creature res = null;
        boolean hasEnemy = false;
        boolean camp;
        if(this.field[x][y].retCre()!=null)
            camp = this.field[x][y].retCre().retcamp();
        else
            return null;
        for(int i = x - 1; i <=  x + 1; i++)
        {
            for(int j = y - 1; j <= y + 1; j++)
            {
                if(i < 0 || i > 9 || j < 0 || j > 19)
                {
                    continue;
                }
                Creature t = this.field[i][j].retCre();
                if(t == null )
                    continue;
                if(t.retisAlive() == false)
                    continue;
                if(t.retcamp() != camp)
                {
                    hasEnemy = true;
                    res = t;
                    break;
                }
            }
        }
        return res;
    }

    public void killAll()
    {
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 20; j++)
            {
                this.field[i][j].killPos();
            }
        }
    }

    public boolean anyHuluwaAlive()
    {
        boolean alive = false;
        for(int i = 0; i < 10; i ++)
        {
            for(int j = 0; j < 20; j++)
            {
                if(this.field[i][j].isempty == false)
                { //有生物体存在
                    Creature temp = this.field[i][j].retCre();//得到引用
                    if(temp.retisAlive() && temp.retcamp() == true) //正义
                    {
                        alive = true;
                    }
                }
            }
        }
        return alive;
    }

    public boolean anyMonsterAlive()
    {
        boolean alive = false;
        for(int i = 0; i < 10; i ++)
        {
            for(int j = 0; j < 20; j++)
            {
                if(this.field[i][j].isempty == false)
                { //有生物体存在
                    Creature temp = this.field[i][j].retCre();//得到引用
                    if(temp.retisAlive() && temp.retcamp() == false)//邪恶
                    {
                        alive = true;
                    }
                }
            }
        }
        return alive;
    }
}
