package Save;

public class Saveaction
{
        int attack_or_move_or_skill;
        int[] start_pos;
        int[] end_pos; //留作攻击，每一个Creature进行操作;

        public Saveaction(int t, int[] start_pos, int[] end_pos)
        {
            this.attack_or_move_or_skill = t; //是攻击还是移动
            this.start_pos = start_pos;
            this.end_pos = end_pos;
        }

        public int getAttack_or_move()
        {
            return this.attack_or_move_or_skill;
        }

        public int getXStart()
        {
            return start_pos[0];
        }

        public int getYStart()
        {
            return start_pos[1];
        }

        public int getXEnd()
        {
            return end_pos[0];
        }

        public int getYEnd()
        {
            return end_pos[1];
        }
}
