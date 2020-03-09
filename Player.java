public class Player
{
    private String name;
    private int hit = 0, miss = 0, shots = 0, reps = 0;
    public Board playersBoard; 
    
    public Player()
    {
        Board playersBoard = new Board(false);
        this.playersBoard = playersBoard;
    }
    
    public void placeAllShips() throws OversizeException, OverlapTilesException, AdjacentTilesException
    {
        playersBoard.placeAllShips(playersBoard); 
    }
    
    public boolean placeShip(Ship myShip, int X, int Y, dir direction) throws OversizeException, OverlapTilesException, AdjacentTilesException
    {
        myShip.setX(X);
        myShip.setY(Y);
        myShip.setDir(direction);
        return myShip.placeShip(playersBoard, true);
    }
    
    public void fire(Board opBoard, int[] coord)
    {
        Tile[][] gameBoard = opBoard.getBoard();
        TileType shot = gameBoard[coord[0]][coord[1]].getType();
        shots++;
        if (shot == TileType.ship)
        { 
            gameBoard[coord[0]][coord[1]].setType(TileType.hit);
            System.out.println("Hit!");
            hit++;
        }
        else if (shot == TileType.sea)
        { 
            gameBoard[coord[0]][coord[1]].setType(TileType.miss);
            System.out.println("Miss!");
            miss++;
        }
        else if (shot == TileType.hit)
        {
            System.out.println("Already hit!");
            reps++;
        }
        else if (shot == TileType.miss)
        { 
            System.out.println("Already hit!");
            reps++;
        }
    } 
    
    
    
    public void getStats()
    {
        System.out.println("Shots fired: " + shots);
        System.out.println("Shots hit: " + hit);
        System.out.println("Shots missed: " + miss);
        System.out.println("Repeated shots: " + reps);
    }
}
