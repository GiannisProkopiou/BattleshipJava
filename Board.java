import java.util.*;

public class Board
{
    private Tile[][] board = new Tile[7][7];
    boolean hidden;
    
    public Board(boolean hidden)
    {
        this.hidden = hidden;
        
        for (int row=0; row<7; row++)
        {
            for (int col=0; col<7; col++)
            {
                board[row][col] = new Tile(row, col, TileType.sea);
            }
        }
    }
    
    public void drawboards(Board opponentBd)
    {        
        System.out.println();
        for (int i=0; i<45; i++)
        { System.out.print("-"); }
        System.out.println();
        System.out.println(" - - Y O U - - \t\t - O P P O N E N T -");
        System.out.print("  ");
        for (int col=0; col<7; col++)
        {
            System.out.print(col + " ");                
        }
            System.out.print(" \t   ");        
        for (int col=0; col<7; col++)
        {
            System.out.print(col + " ");                
        }
            System.out.println();
        for (int row=0; row<7; row++)
        {
                System.out.print(row + " ");
            for (int col=0; col<7; col++)
            {
                if (hidden && board[row][col].draw() == 's')
                { System.out.print("~ "); }
                else
                { System.out.print(board[row][col].draw() + " "); }                
            }
                System.out.print(" \t ");
                System.out.print(row + " ");
            for (int col=0; col<7; col++)
            {
                if (opponentBd.hidden && opponentBd.board[row][col].draw() == 's')
                { System.out.print("~ "); }
                else
                { System.out.print(opponentBd.board[row][col].draw() + " "); }               
            }
                System.out.println();
        }
        
        for (int i=0; i<45; i++)
        { System.out.print("-"); }
        System.out.println();
    }
    
    public void setHidden(boolean hidden)
    { this.hidden = hidden; }
    
    public Tile[][] getBoard()
    { return board; }
    
    public void setBoard(Tile[][] nboard)
    { this.board = nboard; }
    
    public ArrayList<Tile> getAdjacentTiles(Tile tile)
    {
        int myX = tile.getX();
        int myY = tile.getY();
        
        ArrayList<Tile> adj = new ArrayList<Tile>();
        
        if (myX == 0 && myY == 0)
        {
            adj.add(board[myX][myY + 1]); 
            adj.add(board[myX + 1][myY]); 
        }
        else if (myX == 0 && myY == 6)
        {
            adj.add(board[myX][myY - 1]); 
            adj.add(board[myX + 1][myY]); 
        }
        else if (myX == 6 && myY == 0)
        {
            adj.add(board[myX][myY + 1]); 
            adj.add(board[myX - 1][myY]); 
        }
        else if (myX == 6 && myY == 6)
        {
            adj.add(board[myX][myY - 1]); 
            adj.add(board[myX - 1][myY]); 
        }
        else if (myX == 0)
        {
            adj.add(board[myX][myY + 1]);   
            adj.add(board[myX][myY - 1]); 
            adj.add(board[myX + 1][myY]); 
        }
        else if (myX == 6)
        {
            adj.add(board[myX][myY + 1]);   
            adj.add(board[myX][myY - 1]); 
            adj.add(board[myX - 1][myY]); 
        }
        else if (myY == 0)
        {
            adj.add(board[myX][myY + 1]);    
            adj.add(board[myX + 1][myY]); 
            adj.add(board[myX - 1][myY]); 
        }
        else if (myY == 6)
        {  
            adj.add(board[myX][myY - 1]); 
            adj.add(board[myX + 1][myY]); 
            adj.add(board[myX - 1][myY]); 
        }
        
        else
        {
            adj.add(board[myX][myY + 1]);   
            adj.add(board[myX][myY - 1]); 
            adj.add(board[myX + 1][myY]); 
            adj.add(board[myX - 1][myY]); 
        }
        
        return adj; 
    }   
    
    public void placeAllShips(Board bd) 
    {
        Carrier carrier = new Carrier(0, 0, dir.horizontal);
        Battleship battleship = new Battleship(0, 0, dir.horizontal);
        Cruiser cruiser = new Cruiser(0, 0, dir.horizontal);
        Submarine submarine = new Submarine(0, 0, dir.horizontal);
        Destroyer destroyer = new Destroyer(0, 0, dir.horizontal);
        
        ArrayList<Ship> myships = new ArrayList<Ship>();
        
        myships.add(carrier);
        myships.add(battleship);
        myships.add(cruiser);
        myships.add(submarine);
        myships.add(destroyer);
        
        int randX = 0;
        int randY = 0;
        int randDir = 0;
        boolean shipDone;
        int j = 5;
        for (int i=0; i<5; i++)
        {
            shipDone = false;
            while (!shipDone)
            {                               
                randX = (int)(Math.random() * 7);
                myships.get(i).setX(randX);
                randY = (int)(Math.random() * 7);                
                myships.get(i).setY(randY);                
                              
                randDir = (int)(Math.random() * 2);
                if (randDir == 0)
                { myships.get(i).setDir(dir.horizontal); }
                else if (randDir == 1)
                { myships.get(i).setDir(dir.vertical); }
                
                try{
                    shipDone = myships.get(i).placeShip(bd, true);}
                catch(OversizeException ex1){}  
                catch( OverlapTilesException ex2){} 
                catch(AdjacentTilesException ex3){} 
            }
            j--;
        }
    }
    
    public boolean allShipsSunk()
    {
        boolean sunk = true;
        for (int row=0; row<7; row++)
        {
            for (int col=0; col<7; col++)
            {
                if (board[row][col].getType() == TileType.ship)
                { 
                    sunk = false;
                    break;                    
                }
            }
            if (!sunk)
            { break; }
        }
        return sunk;
    }
}
