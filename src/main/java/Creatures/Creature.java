package Creatures;

import BattleField.Field;
import BattleField.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.lang.reflect.Constructor;
import java.util.Random;

public class Creature<T>
{
    int x,y;    //位置坐标
    String name;    //生物名字
    boolean camp;  //阵营，1为好，0为坏
    Image image;
    Image dead_Image;
    boolean isAlive;
    int total_blood; //总体的血量
    int cur_blood; //当前的血量

    public Creature(int i,int j)
    {
        x=i;
        y=j;
        this.name="  ";
        this.camp=true;
        isAlive = true;
        total_blood = 100;
        cur_blood = 100; //都为满血power_of_attack
        this.dead_Image =  new Image(this.getClass().getClassLoader().getResource(new String("pic/dead.jpg")).toString(),50,50,false,false);
    }

    public Creature()
    {
        x=-1;
        y=-1;
        this.name="  ";
        this.camp=true;
        isAlive = true;
        total_blood = 100;
        cur_blood = 100; //都为满血power_of_attack
        this.dead_Image =  new Image(this.getClass().getClassLoader().getResource(new String("pic/dead.jpg")).toString(),50,50,false,false);
    }
    
    public Creature(Creature<T> another)   //复制对象坐标构造函数，创建temp用（Java真烦）
    {
        this.x = another.x;  
        this.y = another.y;
        this.dead_Image =  new Image(this.getClass().getClassLoader().getResource(new String("pic/dead.jpg")).toString(),50,50,false,false);
    }


    public Creature(int i,int j,String name)
    {
        x=i;
        y=j;
        this.camp=true;
        isAlive = true;
        total_blood = 100;
        cur_blood = 100; //都为满血power_of_attack
        this.dead_Image =  new Image(this.getClass().getClassLoader().getResource(new String("pic/dead.jpg")).toString(),50,50,false,false);
        this.name=name;
    }

    public Creature(int i,int j,boolean camp)
    {
        x=i;
        y=j;
        this.name="  ";
        isAlive = true;
        total_blood = 100;
        cur_blood = 100; //都为满血power_of_attack
        this.dead_Image =  new Image(this.getClass().getClassLoader().getResource(new String("pic/dead.jpg")).toString(),50,50,false,false);
        this.camp=camp;
    }

    public Creature(int i,int j,String name,boolean camp)
    {
        x=i;
        y=j;
        isAlive = true;
        total_blood = 100;
        cur_blood = 100; //都为满血power_of_attack
        this.dead_Image =  new Image(this.getClass().getClassLoader().getResource(new String("pic/dead.jpg")).toString(),50,50,false,false);
        this.name=name;
        this.camp=camp;
    }

    public void changeTheposition(int i,int j)
    {
        this.x = i;
        this.y = j;
    }

    public void show_GUI(Canvas canvas)
    {
        //显示图片
        if(this.isAlive == true) {
            canvas.getGraphicsContext2D().drawImage(this.image, this.y * 50, this.x * 50);
            //显示血量条
            double rate = (double) this.cur_blood / this.total_blood; //进行初始化
            canvas.getGraphicsContext2D().setFill(Color.BLUE);
            canvas.getGraphicsContext2D().fillRect(this.y * 50, this.x * 50, 50 * rate, 5);
            canvas.getGraphicsContext2D().setFill(Color.WHITE);
            canvas.getGraphicsContext2D().fillRect(this.y * 50 + 50 * rate, this.x * 50, 50 * (1 - rate), 5);
        }
        else
        {
            canvas.getGraphicsContext2D().drawImage(this.dead_Image, this.y * 50, this.x * 50);
        }
    }

    public String retname()
    {
        return this.name;
    }

    public int retx()
    {
        return this.x;
    }

    public int rety()
    {
        return this.y;
    }

    public boolean retcamp()
    {
        return this.camp;
    }

    public boolean retisAlive()
    {
        return this.isAlive;
    }




    public void attackEnemy(Creature enemy,Canvas canvas)
    {
        if(!this.isAlive || !enemy.isAlive) return;
        int damage=10;
        double rate = (double)cur_blood / total_blood;

        if((rate <= 1) && (rate >= 0.7))
        {
            damage = 30;
        }
        else if((rate < 0.7) && (rate >= 0.5))
        {
            damage = 50;
        }
        else if((rate < 0.5) && (rate >= 0.3))
        {
            damage = 70;
        }
        else if((rate < 0.3) && (rate > 0))
        {
            damage = 100;
        }

        if(this.camp==true)
        {
            damage*=1.15;
        }


        if(enemy.cur_blood-damage <= 0)
        {
            enemy.isAlive=false;
        }
        else
        {
            enemy.cur_blood -= damage;
        }
    }


    private int getY(boolean to_left, int y)
    {
        if(to_left== true)
        {
            y--;
            if(y < 0)
                y = 0;
        }
        else
        {
            y++;
            if(y > 19)
                y = 19;
        }
        return y;
    }

    private int getX(boolean to_up, int x)
    {
        if(to_up == true)
        {
            x--;
            if(x < 0)
                x = 0;
        }
        else
        {
            x++;
            if(x > 9)
                x = 9;
        }
        return x;
    }

    public int[] want_to_mov_x_y(Field ground)
    {
        ///检测，通往敌人多的方向去
        int num_up = 0;
        int num_down = 0;
        int num_right = 0;
        int num_left = 0;
        boolean to_left = true;
        boolean to_up = true; //向左和向上
        int pos[] = new int[2];
        pos[0] = this.x;
        pos[1] = this.y;
        //判断各个方位上的怪物
        for(int i = 0; i < 10;i++)
        {
            for(int j = 0; j < 20; j++)
            {
                if(i != this.x && j != this.y)
                {
                    Creature temp = ground.get_Creature(i,j); //得到
                    if(temp != null)
                    {
                        if(temp.isAlive == true&& this.isAlive == true && this.camp != temp.camp)
                        { //敌人
                            if(i < this.x)
                            {
                                num_up++;
                            }
                            else
                            {
                                num_down++;
                            }
                            if(j < this.y)
                            {
                                num_left ++;
                            }
                            else
                            {
                                num_right++;
                            }
                        }
                    }
                }
            }
        }
        if(num_left < num_right)
        {
            to_left =  false;
        }
        if(num_up < num_down)
        {
            to_up = false;
        }
        int count = 0;
        //设置坐标
        while (true)
        {
            int x = this.x;
            int y = this.y;
            Random t = new Random();
            int choose = t.nextInt(4);
            if (choose == 0) {
                x = getX(to_up, x);
            } else if (choose == 1) {
                y = getY(to_left, y);
            } else if (choose == 2) {
                x = getX(to_up, x);
                y = getY(to_left, y);
            } else { //相反方向
                Random k = new Random();
                int r = k.nextInt(3);
                if (r == 0)
                    x = getX(!to_up, x);
                else if (r == 1)
                    y = getY(!to_left, y);
                else {
                    x = getX(!to_up, x);
                    y = getY(!to_left, y);
                }
            }
            Creature temp = ground.get_Creature(x, y);
            if (temp != null) {
                if (temp.retisAlive() == false)
                {
                    count++;
                }
            } else {
                pos[0] = x;
                pos[1] = y;
                break;
            }
            //System.out.println("change");
            if(count == 10)
            {
                //System.out.println("change");
                for(int m = this.x - 1; m < this.x + 1 ; m++)
                {
                    for (int n = this.y - 1; n < this.y + 1; n++)
                    {
                        if (m >= 0 && m < 10 && n >= 0 && n < 20)
                        {
                            Creature cre_temp = ground.get_Creature(m, n);
                            if (cre_temp == null)
                            {
                                Random can_move = new Random();
                                int chance = can_move.nextInt(2);
                                if (chance == 0)
                                {
                                    pos[0] = m;
                                    pos[1] = n;
                                }
                            }
                            else if (cre_temp.retisAlive() == false)
                            {
                                Random can_move = new Random();
                                int chance = can_move.nextInt(2);
                                if (chance == 0)
                                {
                                    pos[0] = m;
                                    pos[1] = n;
                                }
                            }
                        }
                    }
                }
                return pos;
            }
        }
        return pos;
    }

    public void suicide()
    {
        this.isAlive=false;
    }

    public void getTheinfo()
    {
        if(name != "蝎精")
            System.out.print(name+"  ");
        else
            System.out.print(name);
        System.out.print(x+"坐标"+y);
    }

    public int retblood()
    {
        return cur_blood;
    }

    /*public void setto(int x,int y,Space filed)
    {
        filed.setCre(this);
        filed.space[x][y].x=x;
        filed.space[x][y].y=y;
    }*/

    /*public void moveto(int x,int y,Space filed)
    {
        if(filed.space[x][y]==null)
        {
            Creature temp = new Creature(this);
            
            filed.space[x][y]=this;
            filed.space[x][y].x=x;
            filed.space[x][y].y=y;
            if(temp.x==-1 && temp.y==-1)
            {
                ;
            }
            else
            {
                filed.space[temp.x][temp.y]=null;
            }
            
        }
        else
        {
            Creature temp = filed.space[x][y];
            filed.space[x][y]=this;
            this.x=x;
            this.y=y;
            filed.space[temp.x][temp.y]=temp;
            filed.space[temp.x][temp.y].x=temp.x;
            filed.space[temp.x][temp.y].y=temp.y;
        }
        
    }*/

}