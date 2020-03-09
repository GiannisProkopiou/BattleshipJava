public class Tile
{
    private int X, Y;        
    private TileType type;
    
    public Tile(int X, int Y, TileType type)
    {
        this.X = X;
        this.Y = Y; 
        this.type = type;
    }
    
    public int getX()
    { return this.X; }
    
    public void setX(int x)
    { this.X = x; }
    
    public int getY()
    { return this.Y; }
    
    public void setY(int y)
    { this.Y = y; }
    
    public TileType getType()
    { return this.type; }
    
    public void setType(TileType type)
    { this.type = type; }
    
    public char draw()
    {
        char ty = 'N';
        switch(this.type)
        {
            case sea:
                //System.out.print("~");
                ty = '~';
                break;
            case ship:
                //System.out.print("s");
                ty = 's';
                break;
            case hit:
                //System.out.print("X");
                ty = 'X';
                break;
            case miss:
                //System.out.print("o");
                ty = 'o';
                break;
        }
        return ty;
    }      
}
