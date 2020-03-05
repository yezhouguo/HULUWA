package Creatures;

import javafx.scene.image.Image;

import java.net.URL;

enum HuluBro
{
    R("老大","红色"),O("老二","橙色"),Y("老三","黄色"),G("老四","绿色"),C("老五","青色"),B("老六","蓝色"),P("老七","紫色");
    final String name;
    final String color;

    HuluBro(String name,String color)
    {
        this.name = name;
        this.color = color;
    }

}

public class huluwas extends Creature implements Comparable<huluwas>
{
    int num;
    String color;
    HuluBro hulubro;
    private URL url_of_image = null;
    public huluwas(int i, int x, int y)
    {
        super(x,y,true);
        hulubro = HuluBro.values()[i];
        this.name=hulubro.name;
        this.color=hulubro.color;
        this.num=hulubro.ordinal();
        this.url_of_image = this.getClass().getClassLoader().getResource(new String("pic/"+ (this.num+1) +".jpg"));
        this.image =  new Image(url_of_image.toString(),50,50,false,true);
    }
    
    @Override
    public int compareTo(huluwas o)
    {
        return this.num - o.num;
    }

}
