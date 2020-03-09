import java.util.*;

abstract class Ship
{
    private int X, Y; 
    private int size;
    
    private dir direction;
    
    //public boolean valid = false;
    
    public Ship(int X, int Y, dir direction, int size)
    {
        this.X = X;
        this.Y = Y; 
        this.direction = direction;
        this.size = size;
    }   
    
    public void setX(int X)
    { this.X = X; }
    
    public void setY(int Y)
    { this.Y = Y; }
    
    public void setDir(dir direction)
    { this.direction = direction; }
    
    public boolean placeShip(Board b, boolean verbose) throws OversizeException, OverlapTilesException, AdjacentTilesException
    {        
        Tile[][] myboard = b.getBoard();
        
        boolean valid = false; 
                
            if (this.direction == dir.horizontal)
            {
                if ((this.Y + this.size) > 7)
                {
                    if (verbose == true)
                    { throw new OversizeException("This ship doesn't fit to the board!"); }
                }
                else valid = true;
                
                if (valid)
                {
                    for (int i=0; i<this.size; i++)
                    {
                        if (myboard[this.X][this.Y + i].getType() == TileType.ship)
                        {
                            if (verbose == true)
                            { throw new OverlapTilesException("There is another ship in this area!"); }
                            valid = false;
                            break;
                        }
                    }
                    
                    for (int j=0; j<this.size; j++)
                    {
                        ArrayList<Tile> myadj = new ArrayList<Tile>();
                        myadj = b.getAdjacentTiles(myboard[this.X][this.Y + j]);
                        
                        for (Tile obj : myadj)
                        {
                            if (obj.getType() == TileType.ship)
                            {
                                if (verbose == true)
                                { throw new AdjacentTilesException("The ship must not adjent to an existing ship!"); }
                                valid = false;
                                break;
                            }
                        }                
                    }
                }
            }
            else if (this.direction == dir.vertical)
            {
                if ((this.X + this.size) > 7)
                {
                    if (verbose == true)
                    { throw new OversizeException("This ship doesn't fit to the board!"); }
                }
                else valid = true;
                
                if (valid)
                {
                    for (int i=0; i<this.size; i++)
                    {
                        if (myboard[this.X + i][this.Y].getType() == TileType.ship)
                        {
                            if (verbose == true)
                            { throw new OverlapTilesException("There is another ship in this area!"); }
                            valid = false;
                            break;
                        }
                    }
                    
                    for (int j=0; j<this.size; j++)
                    {
                        ArrayList<Tile> myadj = new ArrayList<Tile>();
                        myadj = b.getAdjacentTiles(myboard[this.X + j][this.Y]);
                        
                        for (Tile obj : myadj)
                        {
                            if (obj.getType() == TileType.ship)
                            {
                                if (verbose == true)
                                { throw new AdjacentTilesException("The ship must not adjent to an existing ship!"); }
                                valid = false;
                                break;
                            }
                        }                
                    }
                }
            }
        
        
        if (valid == true)
        {
            if (this.direction == dir.horizontal)
            {
                for (int i=0; i<this.size; i++)
                {
                    myboard[this.X][this.Y + i].setType(TileType.ship);
                }
            }
            else if (this.direction == dir.vertical)
            {
                for (int i=0; i<this.size; i++)
                {
                    myboard[this.X + i][this.Y].setType(TileType.ship);
                }
            }
            b.setBoard(myboard);
            return true;
        }  
        else return false;   
    }
}
