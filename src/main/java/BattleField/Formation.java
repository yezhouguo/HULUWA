package BattleField;

import Creatures.huluwas;
import Creatures.Monsters;
import Creatures.Scorpion;

import java.util.ArrayList;

public class Formation
{
    public void Formationforbad(Field ground, Scorpion scorpion, Monsters[] monsters, int index)
    {
        
        switch(index)
        {
            case 0:         //HeYi鹤翼
            {
                ground.movTheCre(scorpion, 4, 10);
                ground.movTheCre(monsters[0], 5, 11);
                ground.movTheCre(monsters[1], 6, 12);
                ground.movTheCre(monsters[2], 7, 13);
                ground.movTheCre(monsters[3], 7, 14);
                ground.movTheCre(monsters[4], 6, 15);
                ground.movTheCre(monsters[5], 5, 16);
                ground.movTheCre(monsters[6], 4, 17);
                break;
            }

            case 1:         //YanXing雁行
            {
                ground.movTheCre(scorpion,2,17);
                ground.movTheCre(monsters[0],3,16);
                ground.movTheCre(monsters[1],4,15);
                ground.movTheCre(monsters[2],5,14);
                ground.movTheCre(monsters[3],6,13);
                ground.movTheCre(monsters[4],7,12);
                ground.movTheCre(monsters[5],8,11);
                ground.movTheCre(monsters[6],9,10);
                break;
            }
            case 2:         //ChongE衝轭
            {
                ground.movTheCre(scorpion,1,16);
                ground.movTheCre(monsters[0],2,17);
                ground.movTheCre(monsters[1],3,16);
                ground.movTheCre(monsters[2],4,17);
                ground.movTheCre(monsters[3],5,16);
                ground.movTheCre(monsters[4],6,17);
                ground.movTheCre(monsters[5],7,16);
                ground.movTheCre(monsters[6],8,17);
                break;
            }
            case 3: //长蛇
            {
                ground.movTheCre(scorpion,1,16);
                ground.movTheCre(monsters[0],2,16);
                ground.movTheCre(monsters[1],3,16);
                ground.movTheCre(monsters[2],4,16);
                ground.movTheCre(monsters[3],5,16);
                ground.movTheCre(monsters[4],6,16);
                ground.movTheCre(monsters[5],7,16);
                ground.movTheCre(monsters[6],8,16);
                break;
            }
            case 4:         //YuLin鱼鳞
            {
                ground.movTheCre(scorpion,4,13);
                ground.movTheCre(monsters[0],8,13);
                ground.movTheCre(monsters[1],5,14);
                ground.movTheCre(monsters[2],6,11);
                ground.movTheCre(monsters[3],6,13);
                ground.movTheCre(monsters[4],6,15);
                ground.movTheCre(monsters[5],7,12);
                ground.movTheCre(monsters[6],7,14);
                break;
            }
            case 5:         //FangYuan方円
            {
                ground.movTheCre(scorpion,3,13);
                ground.movTheCre(monsters[0],4,12);
                ground.movTheCre(monsters[1],4,14);
                ground.movTheCre(monsters[2],5,11);
                ground.movTheCre(monsters[3],5,15);
                ground.movTheCre(monsters[4],6,12);
                ground.movTheCre(monsters[5],6,14);
                ground.movTheCre(monsters[6],7,13);
                break;
            }
            case 6:         //YanYue偃月
            {
                ground.movTheCre(scorpion,2,15);
                ground.movTheCre(monsters[0],3,14);
                ground.movTheCre(monsters[1],4,13);
                ground.movTheCre(monsters[2],5,12);
                ground.movTheCre(monsters[3],6,12);
                ground.movTheCre(monsters[4],7,13);
                ground.movTheCre(monsters[5],8,14);
                ground.movTheCre(monsters[6],9,15);
                break;
            }
            case 7:         //FengShi锋矢
            {
                ground.movTheCre(scorpion,4,13);
                ground.movTheCre(monsters[0],5,12);
                ground.movTheCre(monsters[1],5,13);
                ground.movTheCre(monsters[2],5,14);
                ground.movTheCre(monsters[3],6,11);
                ground.movTheCre(monsters[4],6,13);
                ground.movTheCre(monsters[5],6,15);
                ground.movTheCre(monsters[6],7,13);
                break;
            }
        }
    }


    public void Formationforgood(Field battlefield, huluwas[] huluwas, int index)
    {
        int dis = 10;
        int up = 1;
        switch(index)
        {
            case 2:
            {
                battlefield.movTheCre(huluwas[0],2- up,13-dis);
                battlefield.movTheCre(huluwas[1],3- up,12-dis);
                battlefield.movTheCre(huluwas[2],4- up,13-dis);
                battlefield.movTheCre(huluwas[3],5- up,12-dis);
                battlefield.movTheCre(huluwas[4],6- up,13-dis);
                battlefield.movTheCre(huluwas[5],7- up,12-dis);
                battlefield.movTheCre(huluwas[6],8- up,13-dis);
                break;
            }
        }
    
    }

    public void Formationforgood(Field battlefield, ArrayList<huluwas> huluwas, int index)
    {
        switch(index)
        {
            case 3:     //ChangShe长蛇
            {
                for(int i=0;i<7;i++)
                {
                    battlefield.putTheCre(huluwas.get(i),i+4,1);
                    //filed.showthemap(15, 15);
                    //System.out.println("------------------------------------");
                }
                break;
            }
        }

    }
}