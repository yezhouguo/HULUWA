package Save;

public class initSave
{
    private int[][]field = new int[10][20];

    public initSave(int [][]init)
    {
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 20; j++)
            {
                field[i][j] = init[i][j];
            }
        }
    }

    public int[][] getField()
    {
        return this.field;
    }
}
