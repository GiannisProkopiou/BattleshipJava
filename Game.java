import java.util.*;

public class Game
{
    public static void main(String[] args) throws OversizeException, OverlapTilesException, AdjacentTilesException
    {        
        Player user = new Player();
        Player computer = new Player();
        computer.playersBoard.setHidden(true);
        
        System.out.println("\n - JAVA BATTLESHIP - \n ** Glarakis George - Papadias Epameinondas - Prokipiou John ** \n CEID: 2nd Semester: Object Oriented Programming: Project 2018"); 
        SystemPause();
        
        System.out.println("Let's START!");
        if(randomPlace())
        { 
            user.placeAllShips();            
            user.playersBoard.drawboards(computer.playersBoard); 
        }
        else
        {
            user.playersBoard.drawboards(computer.playersBoard); 
            manualPlace(user, computer);
        }
        
        computer.placeAllShips();
        
        int shots = 0;
        boolean GameOver = false;
        while (!GameOver && shots < 30)
        {
            System.out.println("\nMake a hit:");
            
            int[] coords = getInput();
            System.out.print("You: ");
            user.fire(computer.playersBoard, coords);
            
            int[] randCoords = getRandInput();
            System.out.print("Computer: ");            
            computer.fire(user.playersBoard, randCoords);          
            
            user.playersBoard.drawboards(computer.playersBoard); 
            
            if (user.playersBoard.allShipsSunk() || computer.playersBoard.allShipsSunk())
            { GameOver = true; }
            
            shots++;
        }
        
        if (computer.playersBoard.allShipsSunk())
        { System.out.println("!!!YOU WIN!!!"); }
        else if (user.playersBoard.allShipsSunk())
        { System.out.println("!!!YOU LOSE!!!"); }
        else if (shots == 30)
        { System.out.println("!!!YOU ARE OUT OF AMMO. YOU LOSE!!!"); }  
        
        System.out.println("Game Stats:");
        user.getStats();
    } 
    
    public static int[] getInput()
    {
        int[] coord;
        coord = new int[2];
        boolean in = true;
        while (in)
        {
            Scanner sc = new Scanner(System.in);            
            System.out.println();
            System.out.print("Give the coordinates of the ship(Y / X): ");
            
            try
            {
                coord[0] = sc.nextInt();            
                coord[1] = sc.nextInt();
                in = false;
            }
            catch(Exception ex){ 
                System.out.println("Coordinates given are not valid!");
                in = true;
            }
            
            if (coord[0] > 6 || coord[0] < 0 || coord[1] > 6 || coord[1] < 0 )
            { 
                System.out.println("Coordinates given are not valid!");
                in = true;
            }
        }
        return coord;
    }
    
    public static int[] getRandInput()
    {
        int[] coord;
        coord = new int[2];
        
        coord[0] = (int)(Math.random() * 7);
        coord[1] = (int)(Math.random() * 7);
        
        return coord;
    }
    
    public static dir getOrientation()
    {
        boolean orient;
        while (true)
        {
            Scanner or = new Scanner(System.in);            
            System.out.println();
            System.out.println("Give the orientation of the ship.");
            System.out.print("Press H for Horizontal or V for Vertical: ");
            char a = or.next().charAt(0);
            
            if (a == 'H' || a == 'h')
            { 
                orient = true;
                break;
            }
            else if (a == 'V' || a == 'v')
            { 
                orient = false;
                break;
            }
            else 
            {
                System.out.println("Not valid answer.");
            }
        }
        if (orient)
        { return dir.horizontal; }
        else 
        { return dir.vertical; }
    }
    
    public static boolean randomPlace()
    {
        boolean answer;
        while (true)
        {
            Scanner ra = new Scanner(System.in);            
            System.out.println();
            System.out.println("Do you want random placement of the ships?");
            System.out.print("Press Y for Yes or N for No: ");
            char ans = ra.next().charAt(0);
            
            if (ans == 'Y' || ans == 'y')
            { 
                answer = true;
                break;
            }
            else if (ans == 'N' || ans == 'n')
            { 
                answer = false;
                break;
            }
            else 
            {
                System.out.println("Not valid answer.");
            }
        }
        if (answer)
        { return true; }
        else
        { return false; }
    }
    
    public static void manualPlace(Player p1, Player p2)
    {
            Carrier carrier = new Carrier(0, 0, dir.horizontal);
            Battleship battleship = new Battleship(0, 0, dir.horizontal);
            Cruiser cruiser = new Cruiser(0, 0, dir.horizontal);
            Submarine submarine = new Submarine(0, 0, dir.horizontal);
            Destroyer destroyer = new Destroyer(0, 0, dir.horizontal);
            
            ArrayList<Ship> myships = new ArrayList<Ship>();
            ArrayList<String> shipname = new ArrayList<String>();
            
            myships.add(carrier);
            myships.add(battleship);
            myships.add(cruiser);
            myships.add(submarine);
            myships.add(destroyer);
            
            shipname.add("Carrier");
            shipname.add("Battleship");
            shipname.add("Cruiser");
            shipname.add("Submarine");
            shipname.add("Destroyer");
            
            Scanner cell = new Scanner(System.in); 
            int shipCell = 0;
            int j=5;
            for (int i=0; i<5; i++) 
            {
                System.out.println();
                System.out.println("Your ships:");
                int k=1;
                for (Ship sh : myships)
                {
                    System.out.println(k + ". " + shipname.get(k-1));
                    k++;
                }
                
                while (true)
                {
                    System.out.print("Choose a ship to place: ");
                    shipCell = cell.nextInt();
                    if (shipCell > j)
                    { System.out.println("Please select one of the given numbers."); }
                    else break;
                }
                
                boolean shipDone = false;
                while (!shipDone)
                {
                    int[] coords = getInput();
                                    
                    try{ 
                        p1.placeShip(myships.get(shipCell-1), coords[0], coords[1], getOrientation());
                        shipDone = true;
                    }
                    catch(OversizeException ex1){ System.out.println("\n" + ex1.getMessage()); }  
                    catch(OverlapTilesException ex2){ System.out.println("\n" + ex2.getMessage()); } 
                    catch(AdjacentTilesException ex3){ System.out.println("\n" + ex3.getMessage()); } 
                    
                    p1.playersBoard.drawboards(p2.playersBoard); 
                }
                myships.remove(shipCell-1);
                shipname.remove(shipCell-1);
                j--;               
            }   
    }
    
    public static void SystemPause()
    {
        System.out.println("Press Any Key To Continue...");
        new Scanner(System.in).nextLine();
    }
}
