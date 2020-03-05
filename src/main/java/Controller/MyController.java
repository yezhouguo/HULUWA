package Controller;

import BattleField.Battle;
import Save.Saveaction;
import Save.initSave;
import Thread.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyController implements Initializable
{
    @FXML private Canvas mycanvas;
    @FXML private MenuBar mymenu;
    @FXML private MenuItem change;
    @FXML private MenuItem init;

    private int index_form_of_Huluwa;
    private int index_form_of_Mon;
    private Battle battle;
    private GUIrefresh Refresh;
    private initSave save_init;
    private ArrayList<Saveaction> save_actions;
    private boolean is_ready_to_fight;
    private boolean is_reviewing; //
    private Writer write;
    private FileReader read; //写线程
    private int FormationChanged;

    public void initialize(URL location, ResourceBundle resources)
    {
        this.FormationChanged=2;
        this.index_form_of_Huluwa = 0;
        this.index_form_of_Mon = 0;
        this.is_ready_to_fight = false;
        this.save_init = null;
        this.save_actions = new ArrayList<Saveaction>(); //清空操作
        this.is_reviewing = false;
        this.init_HULUWA();
    }


    public void set_queue_Calash(ActionEvent event)
    {
        //进行初始化操作
        if(this.is_ready_to_fight == false) return;
        if(this.is_reviewing == true) return;
        battle.makeChangeofFormation(true,2);
        this.mycanvas.getGraphicsContext2D().clearRect(0, 0, 1100, 600); //清空画布
        this.mycanvas.getGraphicsContext2D().setStroke(Color.BLACK);
        for(int i = 0; i <= 10;i ++){
            this.mycanvas.getGraphicsContext2D().strokeLine(0,i * 50,1000, i* 50);
        }
        for(int j = 0;j <= 20; j ++){
            this.mycanvas.getGraphicsContext2D().strokeLine(j * 50, 0, j *50,500 );
        }
        battle.printThefield();
        index_form_of_Huluwa++;
        index_form_of_Huluwa = index_form_of_Huluwa % 7;
    }


    public void set_queue_monster(ActionEvent event)
    {
        //进行初始化操作
        if(this.is_ready_to_fight == false) return;
        if(this.is_reviewing == true) return;
        battle.makeChangeofFormation(false,0);
        this.mycanvas.getGraphicsContext2D().clearRect(0, 0, 1100, 600); //清空画布
        this.mycanvas.getGraphicsContext2D().setStroke(Color.BLACK);
        for(int i = 0; i <= 10;i ++)
        {
            this.mycanvas.getGraphicsContext2D().strokeLine(0,i * 50,1000, i* 50);
        }
        for(int j = 0;j <= 20; j ++)
        {
            this.mycanvas.getGraphicsContext2D().strokeLine(j * 50, 0, j *50,500 );
        }
        battle.printThefield();
        index_form_of_Mon++;
        index_form_of_Mon = index_form_of_Mon % 7;
    }


    public void startfight()
    {
        File saveRes = new File("save.txt"); //文件
        if (!saveRes.exists())
        {
            try {
                saveRes.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            write = new FileWriter(saveRes,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //进行初始化操作
        if(this.is_ready_to_fight == false) return;
        if(this.is_reviewing == true) return;
        //先保存战场初始化
        this.save_init = new initSave(this.battle.getInitBattledFiled());
        this.output_init();
        Refresh = new GUIrefresh(this.mycanvas,this.battle,this.write,this.save_actions,false,this.read);
        this.Refresh.start(); //开始执行
        this.is_ready_to_fight = false;
    }


    public void reviewAction() throws IOException//读取
    {
        //if (this.is_ready_to_fight == true) return;
        if (this.is_reviewing == false)
        {
            this.is_reviewing = true; //开始回放
        }
        else return;
        FileChooser fileChooser = new FileChooser();
        Stage mainStage = null;
        File selectedFile = fileChooser.showOpenDialog(mainStage); //打开文件
        try {
            this.read = new FileReader(selectedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.battle = new Battle(this.mycanvas, this.save_actions);
        this.battle.change_war_to_start();
        Refresh = new GUIrefresh(this.mycanvas,this.battle,this.write,this.save_actions,true,this.read);
        this.Refresh.start(); //开始执行
        this.is_reviewing = false;
    }


    public void output_init()
    {
        int[][] t = this.save_init.getField();
        for(int i = 0; i < 10; i++)
        {
            String lineres = "";
            for(int j = 0; j < 20; j++)
            {
                System.out.println(t[i][j]);
                lineres = lineres + Integer.toString(t[i][j]) + " ";
            }
            try
            {
                this.write.write(lineres);
                this.write.write("\r\n");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void init_HULUWA()
    {
        //进行初始化操作
        if(this.is_ready_to_fight == true) return;
        else {
            this.is_reviewing = false;
            this.is_ready_to_fight = true;
        }
        battle = new Battle(this.mycanvas,this.save_actions); //开始新的战斗
        battle.initGrandfather();
        battle.initMonster();
        //battle.initmonsters();
        battle.initHuluwa();
        battle.sortThehuluwa();
        battle.makeChangeofFormation(true, 2);
        battle.makeChangeofFormation(false, FormationChanged);

        battle.printThefield();

    }


    public void onSpace(KeyEvent event)
    {

        if(this.battle.ret_ground().retwarstart() == false)
        {
            if(event.getCode() == KeyCode.S)
            {
                init_HULUWA();
            }
            if (event.getCode() == KeyCode.SPACE)
            {
                this.index_form_of_Huluwa = 0;
                this.index_form_of_Mon = 0;
                this.is_ready_to_fight = false;
                this.save_init = null;
                this.save_actions = new ArrayList<Saveaction>(); //清空操作
                this.is_reviewing = false;
                this.init_HULUWA();
                startfight();
                //System.out.println("yeyeyeyey");
            }
            else if(event.getCode() == KeyCode.L)
            {
                try {
                    reviewAction();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(event.getCode() == KeyCode.DIGIT1)
            {
                this.battle.makeChangeofFormation(false,0);
                this.battle.printThefield();
                this.FormationChanged=0;
            }
            else if(event.getCode() == KeyCode.DIGIT2)
            {
                this.battle.makeChangeofFormation(false,1);
                this.battle.printThefield();
                this.FormationChanged=1;
            }
            else if(event.getCode() == KeyCode.DIGIT3)
            {
                this.battle.makeChangeofFormation(false,2);
                this.battle.printThefield();
                this.FormationChanged=2;
            }
            else if(event.getCode() == KeyCode.DIGIT4)
            {
                this.battle.makeChangeofFormation(false,3);
                this.battle.printThefield();
                this.FormationChanged=3;
            }
            else if(event.getCode() == KeyCode.DIGIT5)
            {
                this.battle.makeChangeofFormation(false,4);
                this.battle.printThefield();
                this.FormationChanged=4;
            }
            else if(event.getCode() == KeyCode.DIGIT6)
            {
                this.battle.makeChangeofFormation(false,5);
                this.battle.printThefield();
                this.FormationChanged=5;
            }
            else if(event.getCode() == KeyCode.DIGIT7)
            {
                this.battle.makeChangeofFormation(false,6);
                this.battle.printThefield();
                this.FormationChanged=6;
            }
            else if(event.getCode() == KeyCode.DIGIT8)
            {
                this.battle.makeChangeofFormation(false,7);
                this.battle.printThefield();
                this.FormationChanged=7;
            }
        }
    }
}
