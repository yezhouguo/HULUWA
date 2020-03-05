package BattleField;

import Creatures.*;
import Save.Saveaction;
import Thread.CreThread;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Battle
{
    private Creature[] creatures;
    private huluwas[]Huluwas;
    private Grandpa grandfather;
    private Snake snake;
    private Scorpion scorpion;
    private Monsters[]follower;
    private Field ground;
    public Formation form;
    private Lock lock; //进程锁，以便后来的进行移动
    private CreThread[] threads_of_mine;
    private Canvas mycanvas;

    public Battle(Canvas canvas, ArrayList<Saveaction> save_actions)
    {
        this.creatures = new Creature[17]; //17生物体
        threads_of_mine = new CreThread[17]; //为17个线程
        ground = new Field(10,20); //创造一个10行20列的战场
        form = new Formation();
        lock = new ReentrantLock(); //建立同步锁
        //创建线程
        //葫芦娃
        Huluwas = new huluwas[7];
        this.mycanvas = canvas;
        for(int i  = 0; i < 7;i++)
        {
            Huluwas[i] = new huluwas(i,-1,-1);
            threads_of_mine[i] = new CreThread(Huluwas[i],ground,lock,mycanvas,save_actions); //线程
            creatures[i] = Huluwas[i];
        }
        //爷爷
        grandfather = new Grandpa();
        threads_of_mine[7] = new CreThread(grandfather,ground,lock,mycanvas,save_actions);
        creatures[7] = grandfather;
        //蛇精
        snake = new Snake();
        threads_of_mine[8] = new CreThread(snake,ground,lock,mycanvas,save_actions);
        creatures[8] = snake;
        //蝎子精
        scorpion = new Scorpion();
        threads_of_mine[9] = new CreThread(scorpion,ground,lock,mycanvas,save_actions);
        creatures[9] = scorpion;
        //小怪
        follower = new Monsters[7];
        for(int i = 0; i < 7; i++)
        {
            follower[i] = new Monsters();
            threads_of_mine[10 + i] = new CreThread(follower[i],ground,lock,mycanvas,save_actions);
            creatures[i + 10] = follower[i];
        }
        //初始化完毕
        //开始进行线程的RUN方法
    }


    public void initHuluwa()
    {
        Random rand =new Random();
        for(int i = 0; i  < 7;i++) {
            int k = rand.nextInt(7);
            huluwas temp = Huluwas[k];
            Huluwas[k] = Huluwas[i];
            Huluwas[i] = temp;
        }
        //将葫芦娃放置在战场上
        for(int i = 0; i <7;i++)
        {
            ground.putTheCre(Huluwas[i],i+1,5);
        }
    }
    public void initmonsters() {
        Random rand =new Random();
        for(int i = 0; i  < 7;i++)
        {
            int k = rand.nextInt(7);
            Monsters temp = follower[k];
            follower[k] = follower[i];
            follower[i] = temp;
        }
        //将葫芦娃放置在战场上
        for(int i = 0; i <7;i++)
        {
            ground.putTheCre(follower[i],i+1,8);
        }
    }

    public void initGrandfather()
    {
        ground.putTheCre(grandfather,5,0);
    }

    public void initMonster()
    {
        ground.putTheCre(snake,5,19);
    }

    public void printThefield()
    {
        this.mycanvas.getGraphicsContext2D().clearRect(0, 0, 1100, 600); //清空画布
        //画gezi
        this.mycanvas.getGraphicsContext2D().setStroke(Color.BLACK);
        for(int i = 0; i <= 10;i ++){
            this.mycanvas.getGraphicsContext2D().strokeLine(0,i * 50,1000, i* 50);
        }
        for(int j = 0;j <= 20; j ++){
            this.mycanvas.getGraphicsContext2D().strokeLine(j * 50, 0, j *50,500 );
        }
        for(int i = 0; i< 10 ;i++)
        {
            for(int j = 0; j < 20;j++)
            {
                ground.showThecreature(i,j,this.mycanvas);
            }
        }
    }

    public void start_Fight()
    {
        this.ground.setWar_start(true);
        for(int i = 0; i < 17 ; i++)
        {
            this.threads_of_mine[i].start();
        }
    }

    public void change_war_to_start()
    {
        this.ground.setWar_start(true);
    }

    public void change_war_to_finish()
    {
        this.ground.setWar_start(false);
    }

    public Field ret_ground()
    {
        return this.ground;
    }


    public int[][] getInitBattledFiled()
    {
        return this.ground.initFiled(this.creatures);
    }

    public void put_init_pos(int [][]t)
    {
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 20;j++)
            {
                if(t[i][j] != -1)
                {
                    int num = t[i][j];
                    if (num <= 6)
                        this.ground.movTheCre(Huluwas[num], i, j); //移动
                    else if (num == 7)
                        this.ground.movTheCre(this.grandfather, i, j);
                    else if (num == 8)
                        this.ground.movTheCre(this.snake, i, j);
                    else if (num == 9)
                        this.ground.movTheCre(this.scorpion, i, j);
                    else if (num < 17)
                        this.ground.movTheCre(this.follower[num - 10], i, j);
                }
            }
        }
    }

    public void closeAll()
    {
        this.ground.killAll();
    }

    public void sortThehuluwa()
    {
        for(int i = 6; i >= 0;i--)
        {
            for(int j = 0; j < i;j++)
            {
                if(Huluwas[j].compareTo(Huluwas[j+1])>0)
                {
                    huluwas temp = Huluwas[j];
                    Huluwas[j] = Huluwas[j+1];
                    Huluwas[j+1] = temp;
                }
            }
        }
        for(int i = 0; i<7;i++)
        {
            ground.movTheCre(Huluwas[i],i+1,5);
        }
    }


    public void makeChangeofFormation(boolean t,int index)
    {
        if(t == false)
            form.Formationforbad(ground,scorpion,follower,index); //改变小怪阵型
        else
            form.Formationforgood(ground,this.Huluwas,index);
    }


}
