package Thread;

import BattleField.Field;
import Creatures.Creature;
import Save.Saveaction;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

public class CreThread extends Thread
{
    private Creature creature_of_thread; //类
    private Field ground; //引用类型
    private Lock lock;
    private Canvas mycanvas;
    private boolean living;
    private ArrayList<Saveaction> save_actions;
    public CreThread(Creature creature_of_thread, Field ground, Lock lock, Canvas canvas, ArrayList<Saveaction> save_actions)
    {
        this.ground = ground;
        creature_of_thread.getTheinfo();
        System.out.println("创建!");
        this.creature_of_thread = creature_of_thread;
        this.lock = lock; //锁
        this.mycanvas = canvas;
        this.save_actions = save_actions; //保存动作
        this.living = true;
    }


    //重写方法
    @Override
    public synchronized void run() //作为进行传参
    {
        while(true)
        {
            if(this.creature_of_thread.retisAlive()) //如果存活，则继续
            {
                try
                {
                    this.attack();
                    Thread.sleep(500);
                    this.move();
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace(); //打印出来
                    return;
                }
            }
            else
            {
                if (this.living == true)
                { //结束延迟5s后死亡
                    try
                    {
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    this.living = false;
                    return; //结束线程
                }
            }
        }
    }

    private void attack()
    {
        if(this.living == false)
        {
            return;
        }
        synchronized (this.ground)
        {
            if (this.creature_of_thread.retisAlive() == false)
            {
                return;
            }
            Creature enemy = this.ground.checkEnemy(this.creature_of_thread.retx(),this.creature_of_thread.rety()); //根据坐标获取
            if (enemy == null)
            {
                return;
            }
            if (this.creature_of_thread.retisAlive() && enemy.retisAlive()) //都活着
            {
                int[] start = {this.creature_of_thread.retx(),this.creature_of_thread.rety()};
                int []end = {enemy.retx(),enemy.rety()};
                System.out.print("攻击");
                System.out.println(start[0]+" " + start[1] + " " +end[0] + " "+end[1]);
                Saveaction action = new Saveaction(1,start,end);
                synchronized (this.save_actions)
                {
                    this.save_actions.add(action);
                }
                this.creature_of_thread.attackEnemy(enemy, this.mycanvas);
            }
        }//保存动作
    }

    public void move()
    {
        if(this.living == false) return;
        try
        {
            //访问临界区方法，移动
            synchronized(this.ground)
            {
                int pos[] = creature_of_thread.want_to_mov_x_y(this.ground); //检测
                int x= pos[0];
                int y =pos[1]; //移动的x，y
                int []start = {this.creature_of_thread.retx(),this.creature_of_thread.rety()};
                Saveaction action = new Saveaction(0,start,pos);
                System.out.print("移动");
                System.out.println(start[0]+" " + start[1] + " " +pos[0] + " "+pos[1]);
                synchronized (this.save_actions)
                {
                    this.save_actions.add(action);
                }
                ground.movTheCre(this.creature_of_thread, x, y);
            }
        }
        catch (Exception e) {
            System.out.println("");
        }
    }

}
