/*
    ParkingLot Autoamation System

    Step 1 : Create required enums
    Step 2 : Vehicle Hierarchy creation
    Step 3 : VehicleFactory creation (Factory Pattern)
    Step 4 : ParkingSpot Hirrarchy
    Step 5 : ParkingObserver class
    Step 6 : ParkingFloor class 
    Step 7 : ParkingDispalyBoard (Observer Pattern)
    Step 8 : ParkingStrategy Class (Strategy Pattern)
    Step 9 : PricingStrategy Class (Strategy Pattern)
    Step 10 : PaymentStrategy Class
    Step 11 : ParkingTicket Class
    Step 12 : EntryGate Class
    Step 13 : ExitGate Class
    Step 14 : ParkingLot Class (Singleton Pattern)
    Step 15 : Main class (Controller)

*/

import java.util.*;
import java.time.Duration;
import java.time.LocalDateTime;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/////////////////////////////////////////////////////////
// Step 1 : Create Enums
// It is used to create fixed constants which are required
// throughout the project
/////////////////////////////////////////////////////////

// Represents the diffrent types of vehicles supported by the project
enum VehicleType
{
    BIKE,
    CAR,
    TRUCK
}

// Represents diffrent types of parking spots
enum SpotType
{
    BIKE,
    CAR,
    TRUCK
}

// Represents the current state of parking ticket
enum TicketStatus
{
    ACTIVE,
    CLOSED
}

/////////////////////////////////////////////////////////
// Step 2 : Create Vehicle Class hierarchy
// It is used to create multiple types of classes which r
// represnets the types of vehicles
// Concepts : Abstraction , Inheritance, Polymorphism, Encapsulation
/////////////////////////////////////////////////////////

// Class which represnts a generic vehicle type
abstract class Vehicle
{
    // Abstracted (Hidden) characteristics of class

    private String vehicleNumber;

    private VehicleType vehicleType;

    // Parametrised constructor
    public Vehicle(String vehicleNumber, VehicleType vehicleType)
    {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }

    // Concrete getter method
    public VehicleType getVehicleType()
    {
        return this.vehicleType;
    }

    // Concrete getter method
    public String getVehicleNumber()
    {
        return this.vehicleNumber;
    }

    // Every concrete class will provide its own defination
    public abstract void display();
} 

// Class which represnets the Vechile type as Bike
class Bike extends Vehicle
{
    // Parametrised constructor
    public Bike(String vehicleNumber)
    {
        // Calls Vechile class constructor
        super(vehicleNumber, VehicleType.BIKE);
    }

    // Method overriding
    @Override 
    public void display()
    {
        System.out.println("Bike : "+getVehicleNumber());
    }
}

// Class which represnets the Vechile type as Car
class Car extends Vehicle
{
    // Parametrised constructor
    public Car(String vehicleNumber)
    {
        // Calls Vechile class constructor
        super(vehicleNumber, VehicleType.CAR);
    }

    // Method overriding
    @Override 
    public void display()
    {
        System.out.println("Car : "+getVehicleNumber());
    }
}

// Class which represnets the Vechile type as Truck
class Truck extends Vehicle
{
    // Parametrised constructor
    public Truck(String vehicleNumber)
    {
        // Calls Vechile class constructor
        super(vehicleNumber, VehicleType.TRUCK);
    }

    // Method overriding
    @Override 
    public void display()
    {
        System.out.println("Truck : "+getVehicleNumber());
    }
}

/////////////////////////////////////////////////////////
// Step 3 : Create VehicleFActory Class
// It is used to centralsied the creation of vechile objects
// Concepts : Factory Design Pattern
/////////////////////////////////////////////////////////

class VehicleFactory
{
    // Creates and return the desired class object

    public static Vehicle creatVehicle(VehicleType type, String number)
    {
        switch(type)
        {
            case BIKE:
                return new Bike(number);

            case CAR:
                return new Car(number);

            case TRUCK:
                return new Truck(number);

            default:
                throw new IllegalArgumentException("Invalid Vehicle type");
        }
    }
}

/////////////////////////////////////////////////////////
// Step 4 : Create ParkingSpot Hierarchy
// It is used to create hierarchy of Parking Spots
// Concepts : Encapsulation, Abstraction, Inheritance, Polymorphism
/////////////////////////////////////////////////////////

abstract class ParkingSpot
{
    // Unique number for parking spot (Primary Key)
    private int spotNumber;

    // Type of parking spot
    private SpotType spotType;

    // Indaicates wheteher spot is currently occupied of not
    private boolean occupied;

    // Stores information about the vechicle
    private Vehicle vehicle;

    // Parametrised constructor
    public ParkingSpot(int spotNumber, SpotType spotType)
    {
        this.spotNumber = spotNumber;
        this.spotType = spotType;

        // Initialsed with default values
        this.occupied = false;
        this.vehicle = null;
    }

    public int getSpotNumber()
    {
        return this.spotNumber;
    }

    public SpotType getSpotType()
    {
        return this.spotType;
    }

    public boolean isOccupied()
    {
        return this.occupied;
    }

    public Vehicle getVehicle()
    {
        return this.vehicle;
    }

    // It is used to park the vehical
    public void parkVehicle(Vehicle vehicle)
    {
        if(this.occupied == true)
        {
            throw new RuntimeException("Parking spot is already occupied");
        }
        else
        {
            this.vehicle = vehicle;
            this.occupied = true;
        }
    }

    public Vehicle removeVehicle()
    {
        if(this.occupied == true)
        {
            Vehicle temp = vehicle;

            this.vehicle = null;
            this.occupied = false;

            return temp;
        }
        else
        {
            throw new RuntimeException("Parking spot is already empty");
        }
    }

    // This method decides whether we can park it in the spot or not
    public abstract boolean canFitVehicle(Vehicle vehicle);

    public void display()
    {
        System.out.println("Spot : "+spotNumber+ "["+ spotType+"]");
    
        if(this.occupied == true)
        {
            System.out.println("Occupied by : "+vehicle.getVehicleNumber());
        }
        else
        {
            System.out.println("Spot is available");
        }
    }
} // End of ParkingSpot class

class BikeSpot extends ParkingSpot
{
    public BikeSpot(int spotNumber)
    {
        super(spotNumber, SpotType.BIKE);
    }

    @Override 
    public boolean canFitVehicle(Vehicle vehicle)
    {
        if(vehicle.getVehicleType() == VehicleType.BIKE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

class CarSpot extends ParkingSpot
{
    public CarSpot(int spotNumber)
    {
        super(spotNumber, SpotType.CAR);
    }

    @Override 
    public boolean canFitVehicle(Vehicle vehicle)
    {
        if(vehicle.getVehicleType() == VehicleType.CAR)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

class TruckSpot extends ParkingSpot
{
    public TruckSpot(int spotNumber)
    {
        super(spotNumber, SpotType.TRUCK);
    }

    @Override 
    public boolean canFitVehicle(Vehicle vehicle)
    {
        if(vehicle.getVehicleType() == VehicleType.TRUCK)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

/////////////////////////////////////////////////////////
// Step 5 : ParkingObserver class
// It is used to automatically update display board when
// the parking availablity changes
// Concepts : Observer 
/////////////////////////////////////////////////////////

interface ParkingObserver
{
    void update();
}

/////////////////////////////////////////////////////////
// Step 6 : ParkingFloor class
// It is used to manage parking floor
// Concepts : Composition, ArrayList, Object Management
/////////////////////////////////////////////////////////

class ParkingFloor
{
    // Unique floor number
    private int floorNumber;

    // Collection of all parking spots
    private List<ParkingSpot> parkingSpots;

    // Collection of observers registered for the floor
    private List<ParkingObserver> observers;

    public ParkingFloor(int floorNumber)
    {
        this.floorNumber = floorNumber;

        this.parkingSpots = new ArrayList<>();

        this.observers = new ArrayList<>();
    }

    public List<ParkingSpot> getParkingSpots()
    {
        return parkingSpots;
    }

    public int getFloorNumber()
    {
        return this.floorNumber;
    }

    public void addParkingSpot(ParkingSpot spot)
    {
        parkingSpots.add(spot);
    }

    public void addObserver(ParkingObserver observer)
    {
        observers.add(observer);
    }

    private void notifyObservers()
    {
        for(ParkingObserver observer : observers)
        {
            observer.update();
        }
    }

    // Method is going to search parking spot for speific type of vehical
    public ParkingSpot findAvailabSpot(Vehicle vehicle)
    {
        for(ParkingSpot spot : parkingSpots)
        {
            if(!spot.isOccupied() && spot.canFitVehicle(vehicle))
            {
                return spot;
            }
        } 

        return null;
    }

    // Called when new vehical gets parked
    public void occupySpot(ParkingSpot spot, Vehicle vehicle)
    {
        // allocate sopt for the vehical
        spot.parkVehicle(vehicle);

        // Notify all observers about the availablity of spots
        notifyObservers();
    }

    public void releaseSpot(ParkingSpot spot)
    {
        // relase the already allocated spot
        spot.removeVehicle();

        // Notify all observers about the availablity of spots
        notifyObservers();
    }

    public int getAvailableCount(SpotType type)
    {
        int count = 0;

        for(ParkingSpot spot : parkingSpots)
        {
            if(spot.getSpotType() == type && !spot.isOccupied())
            {
                count++;
            }
        }

        return count;
    }

    // Display all parking spots on specific floor
    public void displayFloor()
    {
        System.out.println();

        System.out.println("Floor : "+floorNumber);

        for(ParkingSpot spot : parkingSpots)
        {
            spot.display();
        }
    }
}

/////////////////////////////////////////////////////////
// Step 7 : Create ParkingDispalyBoard Class
// It is used to create a class which displays the parking
// status

// Subject  ->  ParkingFloor 
// Observer ->  ParkingDispalyBoard

// Note : Any observer is going to observe the subject
// There will be multiple observers for one subject

// Concepts : Observer Design pattern
/////////////////////////////////////////////////////////

class ParkingDispalyBoard implements ParkingObserver
{
    // Floor whose availablity is displayed by this board
    private ParkingFloor floor;

    // Constructor
    public ParkingDispalyBoard(ParkingFloor floor)
    {
        this.floor = floor;
    }

    // Automatically called whenever floor availablity changes
    @Override 
    public void update()
    {
        System.out.println();
        System.out.println("--------- Display Board ---------");
        
        System.out.println("Floor : "+floor.getFloorNumber());

        System.out.println("Available Bike spots : "+floor.getAvailableCount(SpotType.BIKE));
       
        System.out.println("Available Car spots : "+floor.getAvailableCount(SpotType.CAR));
        
        System.out.println("Available Truck spots : "+floor.getAvailableCount(SpotType.TRUCK));
        
        System.out.println("---------------------------------");
        System.out.println();
    }
}

// We can create new observers for the same subject
/*
    class ParkingWebsite implements ParkingObserver
    {
        public void update()
        {   
        
        }
    }
*/

/////////////////////////////////////////////////////////
// Step 8 : Create ParkingStrategy Class

// It is used to create a class ParkingStrategy which is
// responsible to decide the parking spot selection

// Concepts : Strategy Design pattern
/////////////////////////////////////////////////////////

// Defines a common concepts for parking spot selection algorithm
interface ParkingStrategy
{
    ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle);
}

// Selects the first available parking spot 
class FirstAvialableParkingStrategy implements ParkingStrategy
{
    @Override 
    public ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle)
    {
        // Iterate over all available floors
        for(ParkingFloor floor : floors)
        {
            ParkingSpot spot = floor.findAvailabSpot(vehicle);

            if(spot != null)
            {
                return spot;
            }
        }

        return null;
    }
}

/*
    class NearestAvialableParkingStrategy implements ParkingStrategy
    {

    }
*/

/////////////////////////////////////////////////////////
// Step 9 : Create PricingStrategy Class

// It is used to create a class PricingStrategy
// It keeps the pricing algorithm idependent of exit logic

// Concepts : Strategy Design pattern
/////////////////////////////////////////////////////////

interface PricingStrategy
{
    double calculatePrice(Vehicle vehicle, long hours);
} 

class NormalPricingStrategy implements PricingStrategy
{
    @Override 
    public double calculatePrice(Vehicle vehicle, long hours)
    {
        if(hours <= 0)
        {
            hours = 1;
        }

        switch(vehicle.getVehicleType())
        {
            case BIKE:
                return hours * 20;

            case CAR:
                return hours * 50;

            case TRUCK:
                return hours * 100;

            default:
                return 0;
        }
    }
}

class WeekendPricingStrategy implements PricingStrategy
{
    @Override 
    public double calculatePrice(Vehicle vehicle, long hours)
    {
        if(hours <= 0)
        {
            hours = 1;
        }

        switch(vehicle.getVehicleType())
        {
            case BIKE:
                return hours * 40;

            case CAR:
                return hours * 100;

            case TRUCK:
                return hours * 200;

            default:
                return 0;
        }
    }
}

/////////////////////////////////////////////////////////
// Step 10 : Create PaymentStrategy Class

// It is used to create a class PaymentStrategy
// It supports diffrent types of payment methods

// Concepts : Strategy Design pattern
/////////////////////////////////////////////////////////

// Common contract for all payment methods
interface PaymentStrategy
{
    void pay(double amount);
}

class UPIPayment implements PaymentStrategy
{
    @Override 
    public void pay(double amount)
    {
        System.out.println("UPI payment succesful : Rs. "+amount);
    }
}

class CardPayment implements PaymentStrategy
{
    @Override 
    public void pay(double amount)
    {
        System.out.println("Card payment succesful : Rs. "+amount);
    }
}

class CashPayment implements PaymentStrategy
{
    @Override 
    public void pay(double amount)
    {
        System.out.println("Cash payment succesful : Rs. "+amount);
    }
}

/////////////////////////////////////////////////////////
// Step 11 : Create ParkingTicket Class

// It is used to represent one complete parking transaction

/////////////////////////////////////////////////////////

class ParkingTicket
{
    // Used for generating unique tickets
    private static int counter = 1000;

    // Ticket number for unique tcket
    private int ticketNumber;

    // vehical associated with that ticket
    private Vehicle vehicle;

    // Floor on which the vehical is parked
    private ParkingFloor floor;

    // Actual spot on which the vehical is parked
    private ParkingSpot spot;

    // Time at which vehical arrives
    private LocalDateTime entryTime;

    // Time at which vehical exited from parking floor
    private LocalDateTime exitTime;

    // It maintains the status of the ticket
    private TicketStatus status;

    // Paramerised constructor
    public ParkingTicket(
                            Vehicle vehicle,
                            ParkingFloor floor,
                            ParkingSpot spot
                        )
    {
        this.ticketNumber = ++counter;
        this.vehicle = vehicle;
        this.floor = floor;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
        this.status = TicketStatus.ACTIVE;
    }

    // Getter method for ticket number
    public int getTicketNumber()
    {
        return this.ticketNumber;
    }

    // Getter method for vechile
    public Vehicle getVehicle()
    {
        return this.vehicle;
    }

    // Getter method for floor
    public ParkingFloor getFloor()
    {
        return this.floor;
    }

    // Getter method for spot
    public ParkingSpot getSpot()
    {
        return this.spot;
    }

    // Getter method for entrytime
    public LocalDateTime getEntryTime()
    {
        return this.entryTime;
    }

    // Getter method for exittime
    public LocalDateTime getExitTime()
    {
        return this.exitTime;
    }

    // Getter method for status
    public TicketStatus getStatus()
    {
        return this.status;
    }

    // Method gets called when vechile is going out
    public void closeTicket()
    {
        this.exitTime = LocalDateTime.now();

        this.status = TicketStatus.CLOSED;
    }

    // Calculate the total number of hours the vechile is parked
    public long calculateHours()
    {
        LocalDateTime endtime;

        if(exitTime == null)
        {
            endtime = LocalDateTime.now();
        }
        else
        {
            endtime = exitTime;
        }

        // Calculate the actual time
        long minutes = Duration.between(entryTime, endtime).toMinutes();
        
        // Converts minutes to hours
        long hours = minutes / 60;

        if(minutes % 60 != 0)
        {
            hours++;
        }
        
        if(hours == 0)
        {
            hours = 1;
        }

        return hours;
    }

    // It will display complete ticket on screen
    public void displayTicket()
    {
        System.out.println();

        System.out.println("---------------------------------");
        System.out.println("---------- Parking Ticket -------");
        System.out.println("---------------------------------");

        System.out.println("Ticket Number : "+this.ticketNumber);

        System.out.println("Vechile Number : "+this.vehicle.getVehicleNumber());
        
        System.out.println("Vechile Type : "+this.vehicle.getVehicleType());

        System.out.println("Floor Number : "+this.floor.getFloorNumber());

        System.out.println("Spot Number : "+this.spot.getSpotNumber());

        System.out.println("Entry Time : "+this.entryTime);

        System.out.println("Ticket Status : "+this.status);

        System.out.println("---------------------------------");

        System.out.println();
    }
}

/////////////////////////////////////////////////////////
// Step 12 : Create EntryGate Class

// It is used to handle entry of a vechile and its ticket
// generation

/////////////////////////////////////////////////////////

class EntryGate
{
    private int gateNumber;

    public EntryGate(int gateNumber)
    {
        this.gateNumber = gateNumber;
    }

    public int getGateNumber()
    {
        return this.gateNumber;
    }

    // it generates the new parking ticket when
    // veichle enters

    public ParkingTicket generateTicket(Vehicle vehicle, ParkingFloor floor, ParkingSpot sopt)
    {
        System.out.println("Vehile entering from gate : "+this.gateNumber);
        
        // New parking ticket gets generated for the vechile
        return new ParkingTicket(vehicle, floor, sopt);
    }
}

/////////////////////////////////////////////////////////
// Step 13 : Create ExitGate Class

// It is used to handle billing and payment during the 
// vechile exit

/////////////////////////////////////////////////////////

class ExitGate
{
    private int gateNumber;

    public ExitGate(int gateNumber)
    {
        this.gateNumber = gateNumber;
    }

    public int getGateNumber()
    {
        return this.gateNumber;
    }

    // This performs complete exit operations

    public void processExit(
                                ParkingTicket ticket,
                                PricingStrategy pricingStrategy,
                                PaymentStrategy paymentStrategy
                            )
    {
        // Step 1 : Close the ticket and record the exit time
        ticket.closeTicket();

        // Step 2 : Calculate the parking duration
        long hours = ticket.calculateHours();

        // Step 3 : Calculate the parking chargers
        double amount = pricingStrategy.calculatePrice(ticket.getVehicle(), hours);

        System.out.println();

        System.out.println("Vehical exiting from gate : "+gateNumber);
        
        System.out.println("Parking Duration : "+hours);

        System.out.println("Parking charges : "+amount);
        
        // Step 4 : Process the payment using selected payment strategy

        paymentStrategy.pay(amount);
    }
}

/////////////////////////////////////////////////////////
// Step 14 : Create ParkingLot Class

// Pattern : Singleton pattern

// This class is the main controller of complete parking system

/////////////////////////////////////////////////////////

// Sigleton class

class ParkingLot
{
    // instance of class
    
    private static ParkingLot instance;

    // Store the parking Lot name
    
    private String parkingLotName;

    // Store all floors of the parking lot
    
    private List<ParkingFloor> floors;

    // Maps the ticket number with active parking slot
    
    private Map<Integer, ParkingTicket> activeTickets;

    // Maps vehical number with active tickets
    // Used for searching vehicle
    // It prevents duplicate parking

    private Map<String, ParkingTicket> vehicleTicketMap;   

    // Algorithm used for selecting parking spot
    
    private ParkingStrategy parkingStrategy;

    // Algorithm used for calculating parking charges
    
    private PricingStrategy pricingStrategy;

    // Private constructor for singleton class
    
    private ParkingLot()
    {
        floors = new ArrayList<>();

        activeTickets = new HashMap<>();

        vehicleTicketMap = new HashMap<>();

        // Default parking strategy

        parkingStrategy = new FirstAvialableParkingStrategy();

        // Default pricing strategy

        pricingStrategy = new NormalPricingStrategy();

    }

    // Method to return the singleton class object
    public static synchronized ParkingLot getInstance()
    {
        if(instance == null)
        {
            instance = new ParkingLot();
        }

        return instance;
    } 

    // Used to set name for complete parking lot

    public void setParkingLotName(String parkingLotName)
    {
        this.parkingLotName = parkingLotName;
    }

    // Used to add new parking floor

    public void addFloor(ParkingFloor floor)
    {
        // Insert in arraylist

        floors.add(floor);
    }

    // This method returns list of all floors

    public List<ParkingFloor> getFloors()
    {
        return floors;
    }

    // This method can be used to change the default parking strategy

    public void setParkingStrategy(ParkingStrategy strategy)
    {
        this.parkingStrategy = strategy;
    }

    // This method can be used to change the default priscing strategy

    public void setPricingStrategy(PricingStrategy strategy)
    {
        this.pricingStrategy = strategy;
    }

    /*
        Algorithm for parking the vehical

        Check Duplicate Vechile
                |
        Find available spot
                |
        Identify floor for vechile
                |
        Occupy Spot for vechile
                |
        Generate ticket for vechile
                |
        Store the finale ticket
    
    */
    public ParkingTicket parkVehicle(
                                        Vehicle vehical,
                                        EntryGate entryGate
    )
    {
        // Step 1 : Prevent the same vechile for being parked multiple times
        
        if(vehicleTicketMap.containsKey(vehical.getVehicleNumber()))
        {
            System.out.println("This vehical is already parked");

            throw new RuntimeException("This vehical is already parked");
        }

        // Step 2 : Find the available parking spot

        ParkingSpot spot = parkingStrategy.findSpot(floors, vehical);

        // If there is no empty spot

        if(spot == null)
        {
            throw new RuntimeException("Parking is full");
        }

        // Step 3 : Identiy the exact floor for the vechile

        ParkingFloor selectedFloor = null;

        for(ParkingFloor floor : floors)
        {
            ParkingSpot temp = floor.findAvailabSpot(vehical);
            
            if(temp == spot)
            {
                selectedFloor = floor;
                break;
            }
        }

        if(selectedFloor == null)
        {
            throw new RuntimeException("Unabl to identify floor");
        }

        // Step 4 : Occupy the spot

        selectedFloor.occupySpot(spot, vehical);

        // Step 5 : Generate parking ticket from entry gate

        ParkingTicket ticket = entryGate.generateTicket(vehical, selectedFloor, spot);

        // Step 6 : Store the tickct using ticket number
        activeTickets.put(ticket.getTicketNumber(),ticket);

        // Step 7 : Store the ticket using vehical number
        vehicleTicketMap.put(vehical.getVehicleNumber(), ticket);

        return ticket;
    }

    /*
        Find ticket
            |
        Process Exit
            |
        Calculate Charges
            |
        Payment
            |
        Release Spot
            |
        Remove active records 
    */

    public void removeVehicle(
                                int ticketNumber,
                                ExitGate exitGate, 
                                PaymentStrategy paymentStrategy
    )
    {

        // Step 1 : Find active ticket using ticket number
        ParkingTicket ticket = activeTickets.get(ticketNumber);

        if(ticket == null)
        {
            throw new RuntimeException("There is no such ticket");
        }

        // Step 2 : Perform billing and payment
        exitGate.processExit(ticket, pricingStrategy, paymentStrategy);

        // Step 3 : Relaease the occupied spot
        ticket.getFloor().releaseSpot(ticket.getSpot());

        // Step 4 : Remove ticket
        activeTickets.remove(ticketNumber);

        // Step 5 : Remove vechile from active vechile
        vehicleTicketMap.remove(ticket.getVehicle().getVehicleNumber());

        System.out.println("vehical removed succesfully");
    }

    // Search the specified method
    public ParkingTicket searchVehicle(String vehical)
    {
        return vehicleTicketMap.get(vehical);
    }

    // Display complete parking lot information
    public void displayParkingLot()
    {
        System.out.println();
        System.out.println("-------------------------------");
        System.out.println("----- Parking Lot Details -----");
        System.out.println("-------------------------------");

        for(ParkingFloor floor : floors)
        {
            floor.displayFloor();
        }
    }
} // End of ParkingLot Class

/////////////////////////////////////////////////////////

// Step 15 : Controller of the project

/////////////////////////////////////////////////////////

/*
    1 : Create ParkingLot class object

    2 : Create floors

    3 : Add parking spots

    4 : Create Display board

    5 : Register observers

    6 : Add floor to parkingLot

    7 : Create Entry Exit Gates

    8 : Display Menu

*/


public class ParkEngine extends Application
{
    private ParkingLot parkingLot;
    private EntryGate entryGate;
    private ExitGate exitGate;

    private BorderPane root;
    private VBox content;
    private Label totalLabel;
    private Label availableLabel;
    private Label occupiedLabel;
    private Label statusLabel;

    private final String background = "#F4F7FB";
    private final String navy = "#172554";
    private final String blue = "#2563EB";

    @Override
    public void start(Stage stage)
    {
        parkingLot = ParkingLot.getInstance();

        initializeParkingLot();

        entryGate = new EntryGate(1);
        exitGate = new ExitGate(1);

        root = new BorderPane();
        root.setStyle("-fx-background-color: " + background + ";");

        root.setLeft(createSidebar());
        root.setCenter(createDashboard());

        Scene scene = new Scene(root, 1100, 700);

        stage.setTitle("ParkEngine");
        stage.setMinWidth(850);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    private void initializeParkingLot()
    {
        // Create floors only once.
        if (!parkingLot.getFloors().isEmpty())
        {
            return;
        }

        parkingLot.setParkingLotName("ParkEngine");

        ParkingFloor floor1 = new ParkingFloor(1);
        floor1.addParkingSpot(new BikeSpot(101));
        floor1.addParkingSpot(new BikeSpot(102));
        floor1.addParkingSpot(new CarSpot(103));
        floor1.addParkingSpot(new CarSpot(104));
        floor1.addParkingSpot(new TruckSpot(105));
        floor1.addParkingSpot(new TruckSpot(106));

        ParkingDispalyBoard board1 =
            new ParkingDispalyBoard(floor1);
        floor1.addObserver(board1);

        ParkingFloor floor2 = new ParkingFloor(2);
        floor2.addParkingSpot(new BikeSpot(201));
        floor2.addParkingSpot(new BikeSpot(202));
        floor2.addParkingSpot(new CarSpot(203));
        floor2.addParkingSpot(new CarSpot(204));
        floor2.addParkingSpot(new TruckSpot(205));
        floor2.addParkingSpot(new TruckSpot(206));

        ParkingDispalyBoard board2 =
            new ParkingDispalyBoard(floor2);
        floor2.addObserver(board2);

        parkingLot.addFloor(floor1);
        parkingLot.addFloor(floor2);
    }

    private VBox createSidebar()
    {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(28, 18, 25, 18));
        sidebar.setPrefWidth(235);
        sidebar.setStyle("-fx-background-color: " + navy + ";");

        Label logo = new Label("PARKENGINE");
        logo.setTextFill(Color.WHITE);
        logo.setFont(Font.font("Arial", 23));

        Label subtitle = new Label("Parking Management");
        subtitle.setTextFill(Color.LIGHTBLUE);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.NEVER);

        Button dashboardButton =
            menuButton("Dashboard", "dashboard");
        Button parkButton =
            menuButton("Park Vehicle", "park");
        Button exitButton =
            menuButton("Exit Vehicle", "exit");
        Button searchButton =
            menuButton("Search Vehicle", "search");
        Button layoutButton =
            menuButton("Parking Layout", "layout");

        Label footer = new Label("Marvellous ParkEngine");
        footer.setTextFill(Color.LIGHTGRAY);
        footer.setWrapText(true);

        sidebar.getChildren().addAll(
            logo, subtitle,
            new Separator(),
            dashboardButton,
            parkButton,
            exitButton,
            searchButton,
            layoutButton,
            spacer,
            footer
        );

        return sidebar;
    }

    private Button menuButton(String text, String page)
    {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPadding(new Insets(13));
        button.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-cursor: hand;"
        );

        button.setOnAction(e -> showPage(page));
        return button;
    }

    private VBox createDashboard()
    {
        VBox page = createPage("Dashboard",
            "Overview of your parking facility");

        HBox cards = new HBox(18);
        cards.getChildren().addAll(
            createStatCard("Total Spots", "12", "#DBEAFE"),
            createStatCard("Available", "12", "#DCFCE7"),
            createStatCard("Occupied", "0", "#FEE2E2")
        );

        totalLabel = (Label) cards.getChildren()
            .get(0).lookup("#statValue");
        availableLabel = (Label) cards.getChildren()
            .get(1).lookup("#statValue");
        occupiedLabel = (Label) cards.getChildren()
            .get(2).lookup("#statValue");

        // Refresh after the cards are created.
        refreshDashboard();

        Label actionsTitle = heading("Quick Actions");

        Button park = primaryButton("Park a Vehicle");
        park.setOnAction(e -> showPage("park"));

        Button search = primaryButton("Search Vehicle");
        search.setOnAction(e -> showPage("search"));

        HBox actions = new HBox(12, park, search);

        Label info = heading("Parking Information");
        Label description = new Label(
            "2 floors  •  Bikes, Cars and Trucks  •  " +
            "Cash, UPI and Card payments"
        );
        description.setWrapText(true);

        page.getChildren().addAll(
            cards, actionsTitle, actions, info, description
        );

        return page;
    }

    private VBox createStatCard(
        String title, String value, String color)
    {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setMinWidth(150);
        card.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(card, Priority.ALWAYS);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-border-color: #E2E8F0;"
        );

        Label titleLabel = new Label(title);
        titleLabel.setTextFill(Color.web("#64748B"));

        Label valueLabel = new Label(value);
        valueLabel.setId("statValue");
        valueLabel.setFont(Font.font("Arial", 30));
        valueLabel.setTextFill(Color.web(navy));

        Label marker = new Label(" ");
        marker.setMinHeight(5);
        marker.setMaxWidth(Double.MAX_VALUE);
        marker.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-background-radius: 4;"
        );

        card.getChildren().addAll(
            titleLabel, valueLabel, marker
        );

        return card;
    }

    private VBox createPage(String title, String subtitle)
    {
        VBox page = new VBox(20);
        page.setPadding(new Insets(30));
        page.setStyle("-fx-background-color: " + background + ";");

        Label titleLabel = heading(title);
        titleLabel.setFont(Font.font("Arial", 27));

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setTextFill(Color.web("#64748B"));

        page.getChildren().addAll(titleLabel, subtitleLabel);
        return page;
    }

    private Label heading(String text)
    {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 18));
        label.setTextFill(Color.web(navy));
        return label;
    }

    private Button primaryButton(String text)
    {
        Button button = new Button(text);
        button.setPadding(new Insets(12, 20, 12, 20));
        button.setStyle(
            "-fx-background-color: " + blue + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 7;" +
            "-fx-cursor: hand;"
        );
        return button;
    }

    private void showPage(String page)
    {
        switch (page)
        {
            case "dashboard":
                root.setCenter(createDashboard());
                break;

            case "park":
                root.setCenter(createParkPage());
                break;

            case "exit":
                root.setCenter(createExitPage());
                break;

            case "search":
                root.setCenter(createSearchPage());
                break;

            case "layout":
                root.setCenter(createLayoutPage());
                break;
        }

        refreshDashboard();
    }

    private VBox createParkPage()
    {
        VBox page = createPage(
            "Park Vehicle",
            "Register a vehicle and generate its parking ticket"
        );

        ComboBox<VehicleType> typeBox = new ComboBox<>();
        typeBox.getItems().addAll(VehicleType.values());
        typeBox.setValue(VehicleType.CAR);
        typeBox.setMaxWidth(Double.MAX_VALUE);

        TextField numberField = new TextField();
        numberField.setPromptText("e.g. MH12AB1234");

        Button parkButton = primaryButton("Park Vehicle");
        TextArea result = resultArea();

        parkButton.setOnAction(e ->
        {
            String number = numberField.getText()
                .trim().toUpperCase();

            if (number.isEmpty())
            {
                showAlert("Enter a vehicle number.");
                return;
            }

            try
            {
                Vehicle vehicle = VehicleFactory.creatVehicle(
                    typeBox.getValue(), number
                );

                ParkingTicket ticket =
                    parkingLot.parkVehicle(vehicle, entryGate);

                result.setText(
                    "PARKING TICKET\n\n" +
                    "Ticket Number: " + ticket.getTicketNumber() +
                    "\nVehicle Number: " +
                    ticket.getVehicle().getVehicleNumber() +
                    "\nVehicle Type: " +
                    ticket.getVehicle().getVehicleType() +
                    "\nFloor: " + ticket.getFloor().getFloorNumber() +
                    "\nSpot: " + ticket.getSpot().getSpotNumber() +
                    "\nEntry Time: " + ticket.getEntryTime() +
                    "\nStatus: " + ticket.getStatus()
                );

                numberField.clear();
                refreshDashboard();
            }
            catch (Exception ex)
            {
                showAlert(ex.getMessage());
            }
        });

        page.getChildren().addAll(
            heading("Vehicle Type"), typeBox,
            heading("Vehicle Number"), numberField,
            parkButton, result
        );

        return page;
    }

    private VBox createExitPage()
    {
        VBox page = createPage(
            "Exit Vehicle",
            "Process payment and release a parking spot"
        );

        TextField ticketField = new TextField();
        ticketField.setPromptText("Enter ticket number");

        ComboBox<String> paymentBox = new ComboBox<>();
        paymentBox.getItems().addAll("Cash", "UPI", "Card");
        paymentBox.setValue("Cash");
        paymentBox.setMaxWidth(Double.MAX_VALUE);

        Button exitButton = primaryButton("Process Exit");
        TextArea result = resultArea();

        exitButton.setOnAction(e ->
        {
            try
            {
                int ticketNumber = Integer.parseInt(
                    ticketField.getText().trim()
                );

                PaymentStrategy payment;

                switch (paymentBox.getValue())
                {
                    case "UPI":
                        payment = new UPIPayment();
                        break;

                    case "Card":
                        payment = new CardPayment();
                        break;

                    default:
                        payment = new CashPayment();
                }

                parkingLot.removeVehicle(
                    ticketNumber, exitGate, payment
                );

                result.setText(
                    "Exit processed successfully.\n" +
                    "Ticket: " + ticketNumber + "\n" +
                    "Payment method: " + paymentBox.getValue() +
                    "\nCheck the console for the calculated charge."
                );

                ticketField.clear();
                refreshDashboard();
            }
            catch (Exception ex)
            {
                showAlert(ex.getMessage());
            }
        });

        page.getChildren().addAll(
            heading("Ticket Number"), ticketField,
            heading("Payment Method"), paymentBox,
            exitButton, result
        );

        return page;
    }

    private VBox createSearchPage()
    {
        VBox page = createPage(
            "Search Vehicle",
            "Find an active parking ticket using the vehicle number"
        );

        TextField numberField = new TextField();
        numberField.setPromptText("Enter vehicle number");

        Button searchButton = primaryButton("Search");
        TextArea result = resultArea();

        searchButton.setOnAction(e ->
        {
            String number = numberField.getText()
                .trim().toUpperCase();

            if (number.isEmpty())
            {
                showAlert("Enter a vehicle number.");
                return;
            }

            ParkingTicket ticket =
                parkingLot.searchVehicle(number);

            if (ticket == null)
            {
                result.setText("Vehicle is not currently parked.");
            }
            else
            {
                result.setText(
                    "VEHICLE FOUND\n\n" +
                    "Ticket Number: " + ticket.getTicketNumber() +
                    "\nVehicle Number: " +
                    ticket.getVehicle().getVehicleNumber() +
                    "\nVehicle Type: " +
                    ticket.getVehicle().getVehicleType() +
                    "\nFloor: " + ticket.getFloor().getFloorNumber() +
                    "\nSpot: " + ticket.getSpot().getSpotNumber() +
                    "\nEntry Time: " + ticket.getEntryTime() +
                    "\nStatus: " + ticket.getStatus()
                );
            }
        });

        page.getChildren().addAll(
            heading("Vehicle Number"), numberField,
            searchButton, result
        );

        return page;
    }

    private VBox createLayoutPage()
    {
        VBox page = createPage(
            "Parking Layout",
            "Green indicates an available spot; red indicates occupied"
        );

        for (ParkingFloor floor : parkingLot.getFloors())
        {
            VBox floorBox = new VBox(12);
            floorBox.setPadding(new Insets(18));
            floorBox.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #E2E8F0;" +
                "-fx-border-radius: 10;"
            );

            floorBox.getChildren().add(
                heading("Floor " + floor.getFloorNumber())
            );

            GridPane grid = new GridPane();
            grid.setHgap(12);
            grid.setVgap(12);

            int column = 0;
            int row = 0;

            for (ParkingSpot spot : floor.getParkingSpots())
            {
                boolean occupied = spot.isOccupied();

                String text = "Spot " + spot.getSpotNumber() +
                    "\n" + spot.getSpotType() +
                    "\n" + (occupied ? "OCCUPIED" : "AVAILABLE");

                Label spotLabel = new Label(text);
                spotLabel.setAlignment(Pos.CENTER);
                spotLabel.setMinSize(140, 75);
                spotLabel.setMaxWidth(Double.MAX_VALUE);
                spotLabel.setStyle(
                    "-fx-background-color: " +
                    (occupied ? "#FECACA" : "#DCFCE7") + ";" +
                    "-fx-background-radius: 8;" +
                    "-fx-text-alignment: center;" +
                    "-fx-padding: 10;"
                );

                grid.add(spotLabel, column, row);

                column++;
                if (column == 3)
                {
                    column = 0;
                    row++;
                }
            }

            floorBox.getChildren().add(grid);
            page.getChildren().add(floorBox);
        }

        ScrollPane scroll = new ScrollPane(page);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: " + background + ";");

        VBox wrapper = new VBox(scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return wrapper;
    }

    private TextArea resultArea()
    {
        TextArea area = new TextArea();
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefRowCount(7);
        area.setPromptText("Results will appear here...");
        return area;
    }

    private void refreshDashboard()
    {
        if (totalLabel == null ||
            availableLabel == null ||
            occupiedLabel == null)
        {
            return;
        }

        int total = 0;
        int available = 0;

        for (ParkingFloor floor : parkingLot.getFloors())
        {
            for (ParkingSpot spot : floor.getParkingSpots())
            {
                total++;
                if (!spot.isOccupied())
                {
                    available++;
                }
            }
        }

        totalLabel.setText(String.valueOf(total));
        availableLabel.setText(String.valueOf(available));
        occupiedLabel.setText(String.valueOf(total - available));
    }

    private void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ParkEngine");
        alert.setHeaderText("Operation failed");
        alert.setContentText(
            message == null ? "An unexpected error occurred." : message
        );
        alert.showAndWait();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}