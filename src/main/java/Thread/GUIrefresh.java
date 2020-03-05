package Thread;

import BattleField.Battle;
import BattleField.Field;
import Creatures.Creature;
import Save.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class GUIrefresh extends Thread
{
    private Canvas mycanvas;
    private Field ground; //
    private Battle confront;
    private boolean is_finished;
    private boolean winnerOfBattle;
    private Image res_image; //显示图片
    private Image winner;
    private Writer write;
    private ArrayList<Saveaction> save_actions; //保存的动作
    private int num_of_flash = 0; //为0
    private boolean is_reloaded;
    private int num_lines;
    private int number_of_read;
    private FileReader read; //写线程

    public  GUIrefresh(Canvas mycanvans,Battle confront,Writer write,ArrayList<Saveaction> save_actions,boolean is_reloaded,FileReader read)
    {
        this.mycanvas = mycanvans;
        this.confront = confront;
        this.is_finished = false; //判断
        this.ground = confront.ret_ground();
        this.res_image = null;
        this.winner = new Image(this.getClass().getClassLoader().getResource(new String("pic/victory.jpg"))
                .toString(), 800, 400, false, false);
        this.write = write; //输出文件引用
        this.read = read; //输入文件
        this.save_actions = save_actions;
        this.save_actions.clear(); //清空操作
        this.is_reloaded = is_reloaded;
    }

    @Override
    public void run() {
        if (this.is_reloaded == false)  //战斗
        {
            this.confront.start_Fight();//开始战斗，让葫芦娃动起来了
            while (true)
            {
                this.check(); //进行检查判断是否是应该结束
                if (!this.is_finished)
                {
                    this.showBattleground(); //刷新
                    this.save_actions_by_flash(); //保存
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    this.showBattleground();
                    this.save_actions_by_flash(); //保存
                    this.mycanvas.getGraphicsContext2D().drawImage(this.winner, 100, 50);
                    this.mycanvas.getGraphicsContext2D().drawImage(this.res_image, 9 * 50, 5 * 50);
                    System.out.println("游戏结束!");
                    this.confront.closeAll();
                    this.confront.ret_ground().setWar_start(false);
                    try {
                        if(this.winnerOfBattle == true)
                            this.write.write("calash win");
                        else
                            this.write.write("monster win");
                        this.write.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
        else //回放
        {
            this.ground.setWar_start(true);
            BufferedReader bf = new BufferedReader(this.read); //
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.clear();
            while (true) {
                String temp = "";
                try {
                    if((temp = bf.readLine()) == null)
                        break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                arrayList.add(temp);
            }
            //开始处理
            num_lines = 0;
            number_of_read = arrayList.size();//
            int[][] pos_start = new int[10][20];
            while (num_lines < 10)
            {
                String[] t = arrayList.get(num_lines).split(" ");
                for (int j = 0; j < 20; j++)
                {
                    pos_start[num_lines][j] = Integer.parseInt(t[j]); //
                    System.out.print(pos_start[num_lines][j]);
                }
                System.out.println();
                num_lines++;
            }
            this.confront.put_init_pos(pos_start);
            this.showBattleground();
            while (true)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.reviewRefresh_by_one_flash(arrayList);
                if (num_lines >= number_of_read)
                {
                    System.out.println("回放结束");
                    this.mycanvas.getGraphicsContext2D().drawImage(this.winner, 100, 50);
                    this.mycanvas.getGraphicsContext2D().drawImage(this.res_image, 9 * 50, 5 * 50);
                    this.confront.ret_ground().setWar_start(false);
                    break;
                }
            }
        }
    }


    private void save_actions_by_flash() {
        synchronized (this.ground){
            try {
                this.write.write(Integer.toString(num_of_flash)+"\r\n");
                num_of_flash++;
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < this.save_actions.size(); i++)
            {
                int type_of_acction = this.save_actions.get(i).getAttack_or_move();
                int x_start = this.save_actions.get(i).getXStart();
                int y_start = this.save_actions.get(i).getYStart();
                int x_end = this.save_actions.get(i).getXEnd();
                int y_end = this.save_actions.get(i).getYEnd();
                String temp = type_of_acction + " " + x_start + " " + y_start + " " + x_end + " " + y_end;
                try {
                    this.write.write(temp);
                    this.write.write("\r\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            save_actions.clear(); //每一帧都清空一次
        }
    }

    public void check()
    {
        boolean is_Calash_Alive;
        boolean is_Monster_Alive;
        synchronized (this.ground)
        { //上锁，其他进程不能访问，通过其获得状态
            is_Calash_Alive = this.ground.anyHuluwaAlive(); //葫芦娃是否活着
            is_Monster_Alive = this.ground.anyMonsterAlive(); //怪物是否活着
        }
        if(is_Monster_Alive == false)
        {
            this.is_finished = true;
            this.winnerOfBattle = true;
            System.out.println("葫芦娃获得胜利！");
            this.res_image =  new Image(this.getClass().getClassLoader().getResource(new String("pic/1.jpg")).toString()
                    ,100,100,false,false);
        }
        if(is_Calash_Alive == false)
        {
            this.is_finished = true;
            this.winnerOfBattle = false;
            System.out.println("妖怪获得胜利");
            this.res_image =  new Image(this.getClass().getClassLoader().getResource(new String("pic/snake.jpg")).toString()
                    ,100,100,false,false);
        }
    }

    public  void showBattleground()
    {
        synchronized (this.ground)
        {
            this.mycanvas.getGraphicsContext2D().clearRect(0, 0, 1100, 600); //清空画布
            //画gezi
            this.mycanvas.getGraphicsContext2D().setStroke(Color.BLACK);
            for (int i = 0; i <= 10; i++)
            {
                this.mycanvas.getGraphicsContext2D().strokeLine(0, i * 50, 1000, i * 50);
            }
            for (int j = 0; j <= 20; j++)
            {
                this.mycanvas.getGraphicsContext2D().strokeLine(j * 50, 0, j * 50, 500);
            }
            for (int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 20; j++)
                {
                    this.ground.showThecreature(i, j, this.mycanvas);
                }
            }
        }
    }

    private void reviewRefresh_by_one_flash(ArrayList<String> arrayList) {
        Field ground =this.confront.ret_ground();
        while(num_lines < number_of_read)
        {
            String[] t = arrayList.get(num_lines).split(" ");
            if (t.length == 1)
            {
                System.out.println("帧数"+t[0]);
                num_lines++;
                this.showBattleground();
                return;
            }
            else if(t.length == 5){ //为action
                num_lines++;
                System.out.println(this.num_lines);
                int type = Integer.parseInt(t[0]);
                int []start_pos = {Integer.parseInt(t[1]),Integer.parseInt(t[2])};
                int []end_pos = {Integer.parseInt(t[3]),Integer.parseInt(t[4])};
                if(type == 0) //移动
                {
                    Creature cre = ground.get_Creature(start_pos[0], start_pos[1]);
                    if (cre != null)
                    {
                        System.out.println();
                        ground.movTheCre(cre, end_pos[0], end_pos[1]);
                        //cre.show_GUI(this.mycanvas);
                    }
                    else System.err.println("can't mov");
                }
                else if (type == 1)//攻击
                {
                    Creature cre = ground.get_Creature(start_pos[0], start_pos[1]);
                    Creature enemy = ground.get_Creature(end_pos[0], end_pos[1]);
                    if (cre != null && enemy != null)
                    {
                        cre.attackEnemy(enemy, this.mycanvas);
                        /*if(enemy.retisAlive()==false || enemy.retblood() <= 0)
                        {
                            enemy.show_GUI(this.mycanvas);
                        }*/
                    }
                    else System.err.println("错误");
                }
                else if(type == 2){//技能
                    Creature cre = ground.get_Creature(start_pos[0], start_pos[1]);
                    if(cre != null){
                        if(cre.retisAlive()){
                            //cre.usingSkill(ground,this.mycanvas); //使用技能
                        }
                    }
                    else System.err.println("错误");
                }
            }
            else if(t.length == 2){
                num_lines++;
                System.out.println(t[0]);
                if(t[0].equals("calash")) {
                    this.is_finished = true;
                    this.winnerOfBattle = true;
                    System.out.println("葫芦娃获得胜利！");
                    this.res_image =  new Image(this.getClass().getClassLoader().getResource(new String("pic/1.jpg")).toString()
                            ,100,100,false,false);
                }
                else if(t[0].equals("monster"))
                {
                    this.is_finished = true;
                    this.winnerOfBattle = false;
                    System.out.println("妖怪获得胜利");
                    this.res_image =  new Image(this.getClass().getClassLoader().getResource(new String("pic/snake.jpg")).toString()
                            ,100,100,false,false);
                }
            }
        }
    }

    private void removeFromBattle(Creature cre)
    {
        try {
            synchronized (this.ground) {
                this.ground.remove(cre.retx(),cre.rety());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("remove error");
        }
    }
}
