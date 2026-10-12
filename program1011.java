/*
    ParkingLot Automation System

    step 1 : Create required enums
    step 2 : Vehicle Hierarchy creation
    step 3 : VahicleFactory creation (Factory Pattern)
    step 4 : ParkingSlot Hierarchy
    step 5 : ParkingObserver class
    step 6 : ParkingFloor class
    step 7 : Parking DisplayBoard (Observer Pattern)
    step 8 : ParkingStrategy Class (Strategy Pattern)
    step 9 : PricingStrategy Class (Strategy Pattern)
    step 10 : Payment Class
    Step 11 : ParkingTicket Class
    step 12 : EntryGate Class
    step 13 : ExitGate Class
    step 14 : ParkingLot Class (SingleTon Pattern)
    step 15 : Main Class (Controller)

*/

import java.util.*;

import javax.management.RuntimeErrorException;
import javax.print.PrintServiceLookup;

import java.time.Duration;
import java.time.LocalDateTime;

/////////////////////////////////////////////////////////////
//
// Step 1 : Create Enums
// It is used to create fixed constants which are required
// throughout the project
//
/////////////////////////////////////////////////////////////

// Represents the diffrent types of vehicles supported by the project
enum VehicleType
{
    BIKE,
    CAR,
    TRUCK
}

// Represent diffrent types of Parking spots
enum SpotType
{
    BIKE,
    CAR,
    TRUCK
}

// Represents current state of parking ticket
enum TicketStatus
{
    ACTIVE,
    CLOSED
}

/////////////////////////////////////////////////////////////
//
//  Step 2 : Create Vehicle Class Hierarchy
//  It is used to create multiple types of classes which 
//  represents the type of vehicles
//
// Concepts : Abstraction, Inheritance, Polymorphism,
//            Encapsulation
//
/////////////////////////////////////////////////////////////


// Class which represents a generic vehicle type
abstract class Vehicle
{
    // Abstracted (Hidden) characteristics of class
    private String vehicleNumber;

    private VehicleType vehicleType;


    // Parameterized constructor
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

    // Every concrete class will provide its own definition
    public abstract void display();
}

// Class which represents the Vehicle Type as Bike
class Bike extends Vehicle
{
    public Bike(String vehicleNumber)
    {
        super(vehicleNumber, VehicleType.BIKE);
    }

    // Method overriding
    @Override
    public void display()
    {
        System.out.println("BIKE : " + getVehicleNumber());
    }
}

// Class which represents the Vehicle Type as Car
class Car extends Vehicle
{
    public Car(String vehicleNumber)
    {
        super(vehicleNumber, VehicleType.CAR);
    }

    // Method overriding
    @Override
    public void display()
    {
        System.out.println("CAR : " + getVehicleNumber());
    }
}

// Class which represents the Vehicle Type as Truck
class Truck extends Vehicle
{
    public Truck(String vehicleNumber)
    {
        super(vehicleNumber, VehicleType.TRUCK);
    }

    // Method overriding
    @Override
    public void display()
    {
        System.out.println("TRUCK : " + getVehicleNumber());
    }
}


/////////////////////////////////////////////////////////////
//
//  Step 3 : Create VehicleFactory Class
//  It is used to centralized the creation of vehicle objects
// Concepts : Factory Design Pattern
//
/////////////////////////////////////////////////////////////

class VehicleFactory
{
    // Created and return the desired class object

    public static Vehicle creatVehicle(VehicleType type, String number)
    {
        switch(type)
        {
            case BIKE :
                return new Bike(number);

            case CAR :
                return new Car(number);

            case TRUCK :
                return new Truck(number);

            default :
                throw new IllegalArgumentException("Invalid vehicle type");
        }
    }
}

/////////////////////////////////////////////////////////////
//
//  Step 4 : Create ParkingSpot Hierarchy
//  It is used to create hierarchy of Parking spots
//
// Concepts : Abstraction, Inheritance, Polymorphism,
//            Encapsulation
//
/////////////////////////////////////////////////////////////

abstract class ParkingSpot
{
    // Unique number for parking spot (Primary key)
    private int spotNumber;

    // Type of Parking spot
    private SpotType spotType;

    // Indicates whether spot is currently occupied or not
    private boolean occupied;

    // Stores the information about the vehicle
    private Vehicle vehicle;

    // Parameterized Constructor
    public ParkingSpot(int spotNumber, SpotType spotType)
    {
        this.spotNumber = spotNumber;
        this.spotType = spotType;

        // Initialized with default values
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

    // It is used to park the vehicle
    public void ParkVehicle(Vehicle vehicle)
    {
        if(this.occupied == true)
        {
            throw new RuntimeException("Parking Spot is already occupied");
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
            throw new RuntimeException("Parking Spot is already empty");
        }
    }

    // This method decides whether we can park it in the spot or not
    public abstract boolean canFitVehicle(Vehicle vehicle);

    public void display()
    {
        System.out.println("Spot : " + spotNumber + "["+ spotType +"]");

        if(this.occupied == true)
        {
            System.out.println("Occupied by : " + vehicle.getVehicleNumber());
        }
        else
        {
            System.out.println("Spot is available");
        }
    }

}// END OF ParkingSpot class


class BikeSpot extends ParkingSpot
{
    public BikeSpot(int spotNumber)
    {
        super(spotNumber,SpotType.BIKE);
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
        super(spotNumber,SpotType.CAR);
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
        super(spotNumber,SpotType.TRUCK);
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

/////////////////////////////////////////////////////////////
//
//  Step 5 : ParkingObserver Class
//  It is used to automatically update displayBoard when the
//  parking availability changes
//
// Concepts : Observer Design Pattern
//
/////////////////////////////////////////////////////////////

interface ParkingObserver
{
    void update();
}

/////////////////////////////////////////////////////////////
//
//  Step 6 : ParkingFloor Class
//  It is used to manage parking floor
//
// Concepts : Composition, ArrayList, Object Management
//
/////////////////////////////////////////////////////////////

class ParkingFloor
{
    // unique floor number
    private int floorNumber;

    // Collection of all parking spots
    private List<ParkingSpot> parkingSpots;

    // Collection of Observers registered for the floor
    private List<ParkingObserver> observers;

    public ParkingFloor(int floorNumber)
    {
        this.floorNumber = floorNumber;

        this.parkingSpots = new ArrayList<>();
        
        this.observers = new ArrayList<>();
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

    // Method is going to search parking spot for specific type of vehicle
    public ParkingSpot findAvailableSpot(Vehicle vehicle)
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

    // Called when new vehicle gets parked
    public void occupySpot(ParkingSpot spot, Vehicle vehicle)
    {
        // allocate spot for vehicle
        spot.ParkVehicle(vehicle);

        // notify all observers about the availability
        notifyObservers();
    }

    public void releaseSpot(ParkingSpot spot)
    {
        // release the already allocated spot
        spot.removeVehicle();

        // notify all observers about the availability
        notifyObservers();
    }

    public int getAvailaibleCount(SpotType type)
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

    // display all parking spots on specific floor
    public void displayFloor()
    {
        System.out.println();

        System.out.println("Floor : " + floorNumber);

        for(ParkingSpot spot : parkingSpots)
        {
            spot.display();
        }
    }
}

/////////////////////////////////////////////////////////////
// Step 7 : Create ParkingDisplayBoard Class 
// It is used to create a class which displays the parking 
// status
// Subject --> ParkingFloor
// Observer --> ParkingDisplayBoard
//
//
// Note : Any observer is going to observ the subject
// There will be multiple observers for one subject
// Concepts : Observer design pattern
/////////////////////////////////////////////////////////////


class ParkingDisplayBoard implements ParkingObserver
{
    // Floor whose availability is displayed by this board
    private ParkingFloor floor;

    // Parameterized constructor
    public ParkingDisplayBoard(ParkingFloor floor)
    {
        this.floor = floor;
    }

    // Automatically called whenever floor availability changes
    @Override 
    public void update()
    {
        System.err.println();
        System.out.println("---------- Display Board -------------");

        System.out.println("Floor : " + floor.getFloorNumber());
        System.out.println("Available Bike spots : " + floor.getAvailaibleCount(SpotType.BIKE));
        System.out.println("Available Car spots : " + floor.getAvailaibleCount(SpotType.CAR));
        System.out.println("Available Truck spots : " + floor.getAvailaibleCount(SpotType.TRUCK));

        System.out.println("--------------------------------------");
        System.out.println();
    }
}

// We can create new observers for same subject
/*
    class ParkingWebsite implements ParkingObserver
    {
        public void update()
        {

        }
    }
*/


/////////////////////////////////////////////////////////////
// Step 8 : Create ParkingStrategy Class 
// It is used to create a class ParkingStrategy which is 
// responsible to decide the parking spot selection
//
// Concepts : Strategy design pattern
/////////////////////////////////////////////////////////////

// Defines a common concepts for parking spot selection algorithm
interface ParkingStrategy
{
    ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle); 
}

// Selects the first available parking spot
class firstAvailableParkingStrategy implements ParkingStrategy
{
    @Override
    public ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle)
    {
        // Iterate over all available floors
        for(ParkingFloor floor : floors)
        {
            ParkingSpot spot = floor.findAvailableSpot(vehicle);

            if(spot != null)
            {
                return spot;
            }
        }

        return null;
    } 
}

/////////////////////////////////////////////////////////////
// Step 9 : Create PricingStrategy Class 
// It is used to create a class PricingStrategy
// It keeps the pricing algorithm independent of exit logic
//
// Concepts : Strategy design pattern
/////////////////////////////////////////////////////////////

interface PricingStrategy
{
    double calculatePrice(Vehicle vehicle,long hours);
}

class NormalPricingStrategy implements PricingStrategy
{
    @Override

    public double calculatePrice(Vehicle vehicle,long hours)
    {
        if(hours <= 0)
        {
            hours = 1;
        }

        switch(vehicle.getVehicleType())
        {
            case BIKE : 
                    return hours * 20;
            case CAR : 
                    return hours * 50;
            case TRUCK : 
                    return hours * 100;
            default : 
                    return 0;
        }
    }

}


class WeekendPricingStrategy implements PricingStrategy
{
    @Override

    public double calculatePrice(Vehicle vehicle,long hours)
    {
        if(hours <= 0)
        {
            hours = 1;
        }

        switch(vehicle.getVehicleType())
        {
            case BIKE : 
                    return hours * 40;
            case CAR : 
                    return hours * 100;
            case TRUCK : 
                    return hours * 200;
            default : 
                    return 0;
        }
    }

}

/////////////////////////////////////////////////////////////
// Step 10 : Create PaymentStrategy Class 
// It is used to create a class PricingStrategy
// It keeps the pricing algorithm independent of exit logic
//
// Concepts : Strategy design pattern
/////////////////////////////////////////////////////////////

interface PaymentStrategy
{
    void Pay(double amount);
}

class UPIpayment implements PaymentStrategy
{
    @Override
    public void Pay(double amount)
    {
        System.out.println("UPI Payment successful : Rs. " + amount);
    }
}

class Cardpayment implements PaymentStrategy
{
    @Override
    public void Pay(double amount)
    {
        System.out.println("Card Payment successful : Rs. " + amount);
    }
}

class Cashpayment implements PaymentStrategy
{
    @Override
    public void Pay(double amount)
    {
        System.out.println("Cash Payment successful : Rs. " + amount);
    }
}

/////////////////////////////////////////////////////////////
//
// Step 11 : Create ParkingTicket Class 
// It is used to represent one complete parking transaction
//
/////////////////////////////////////////////////////////////

class ParkingTicket
{
    // Used for generating unique ticket number
    private static int counter = 1000;

    // Ticket number for unique ticket
    private int ticketNumber;

    // Vehicle assosiated with that ticket
    private Vehicle vehicle;

    // Floor on which the vehicle is parked
    private ParkingFloor floor;

    // Actual spot on which vehicle is parked
    private ParkingSpot spot;

    // Time at which vehicle entered in parking floor
    private LocalDateTime entryTime;

    // Time at which vehicle exited from parking floor
    private LocalDateTime exitTime;

    // It maintains the status of the ticket
    private TicketStatus status;

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

    // Getter method for ticket Number
    public int getTicketNumber()
    {
        return this.ticketNumber;
    }

    // Getter method for vehicle
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

    // Getter method for entryTime
    public LocalDateTime getEntryTime()
    {
        return this.entryTime;
    }

    // Getter method for entryTime
    public LocalDateTime getExitTime()
    {
        return this.exitTime;
    }

    // Getter method for ticket status
    public TicketStatus gTicketStatus()
    {
        return this.status;
    }

    // Method is called when vehicle is going out
    public void closeTicket()
    {
        this.exitTime = LocalDateTime.now();

        this.status = TicketStatus.CLOSED;
    }

    // Calculate the total number of hours 
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

        // Calculate the actuale time 
        long minutes = Duration.between(entryTime , endtime).toMinutes();

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

    public void displayTicket()
    {
        System.out.println("----------------------------------");
        System.out.println("---------Parking Ticket-----------");
        System.out.println("----------------------------------");

        System.out.println("Ticket Number : " + this.ticketNumber);
        System.out.println("Vehicle Number : " + this.vehicle.getVehicleNumber());
        System.out.println("Vehicle Type : " + this.vehicle.getVehicleType());
        System.out.println("Floor Number : " + this.floor.getFloorNumber());
        System.out.println("Spot Number : " + this.spot.getSpotNumber());
        System.out.println("Entry Time : " + this.entryTime);
        System.out.println("Status : " + this.status);


        System.out.println("----------------------------------");
    }
}

/////////////////////////////////////////////////////////////
// Step 12 : Create EntryGate Class 
// It is used to handle entry of a vehicle and its ticket
// generation
/////////////////////////////////////////////////////////////


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

    // It generates the new parking ticket when 
    // vehicle enters
    public ParkingTicket generateTicket(Vehicle vehicle, ParkingFloor floor, ParkingSpot spot)
    {
        System.out.println("Vehicle entering from gate : " + this.gateNumber);

        // New parking ticket gets generated for the vehicle
        return new ParkingTicket(vehicle, floor, spot);     // Anonymus object
    }
}

/////////////////////////////////////////////////////////////
// Step 13 : Create ExitGate Class 
//
// It is used to handle payment and billing during the 
// vehicle exit
//
/////////////////////////////////////////////////////////////

class ExitGate
{
    private int gateNumber;

    public ExitGate(int gateNumber)
    {
        this.gateNumber = gateNumber;
    }


    public int getGateNumber()
    {
        return this.getGateNumber();
    }


    // This performs complete exit operations
    public void processExit(
                            ParkingTicket ticket,
                            PricingStrategy pricingStrategy,
                            PaymentStrategy paymentStrategy
                           )
    {


        // Step 1 : close the ticket and record the exit time
        ticket.closeTicket();


        // Step 2 : Calculate the parking duration
        long hours = ticket.calculateHours();


        // Step 3 : Calculate the parking charges
        double amount = pricingStrategy.calculatePrice(ticket.getVehicle(), hours);


        System.out.println();

        System.out.println("Vahicle exiting from gate : " + gateNumber);

        System.out.println("Parking Duration : " + hours);

        System.out.println("Parking charges : " + amount);


        // Step 4 : process the payment using selected payment startegy
        paymentStrategy.Pay(amount);


    }
}

/////////////////////////////////////////////////////////////
// Step 14 : Create ParkingLot Class 
//
// Pattern : singleTon pattern
//
// This class is the main controller of the complete parking
// System
/////////////////////////////////////////////////////////////

// SingleTon class
class ParkingLot
{
    // Instance of class

    private static ParkingLot instance;

    // Store the parking lot name

    private String parkingLotName;
    
    // Store all floors of the parking lot

    private List<ParkingFloor> floors;

    // Maps the ticket number with active parking spot

    private Map<Integer, ParkingTicket> activeTickets;

    // Maps vehicle number with active tickets
    // used for searching vehicle
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

        parkingStrategy = new firstAvailableParkingStrategy();

        // Default pricing strategy

        pricingStrategy = new NormalPricingStrategy();

    }

    // Used to set name for complete parking Lot

    public void setParkingLotName(String parkingLotName)
    {
        this.parkingLotName = parkingLotName;
    }
    
    // Used to add new parking floor

    public void addFloors(ParkingFloor floor)
    {
        // Insert in arraylist
        floors.add(floor);
    }

    // this method returns list of all floors

    public List<ParkingFloor> getFloors()
    {
        return floors;
    }

    // this method can be used to change the default parking strategy

    public void setParkingStrategy(ParkingStrategy strategy)
    {
        this.parkingStrategy = strategy;
    }

    // this method can be used to change the default pricing strategy

    public void setPricingStrategy(PricingStrategy strategy)
    {
        this.pricingStrategy = strategy;
    }

    /*

      ||Alogrithm for park vehicle||

        Check duplicate vehicle
                  |
        Find available spot
                  |
        Identify Floor for vehicle
                  |
        Occupy spot for vehicle
                  |
        Generate ticket for vehicle
                  |
        Store the final ticket
    
    */

    public ParkingTicket parkVehicle(
                                        Vehicle vehicle,
                                        EntryGate entryGate
                                    )
    {
        // Step 1 : Prevent the same vehicle for being park multiple times

        if(vehicleTicketMap.containsKey(vehicle.getVehicleNumber()))
        {
            System.out.println("This vehicle is already parked");

            throw new RuntimeException("This vehicle is already parked");
        }

        // Step 2 : Find the available parking spot

        ParkingSpot spot = parkingStrategy.findSpot(floors, vehicle);

        // If there is no empty spot 
        if(spot == null)
        {
            throw new RuntimeException("There is no parking spot available on any floor");
        }

        // Step 3 : Identify the exact floor for the vehicle

        ParkingFloor selectedFloor = null;
        for(ParkingFloor floor : floors)
        {
            ParkingSpot temp = floor.findAvailableSpot(vehicle);

            if(temp == spot)
            {
                selectedFloor = floor;
                break;
            }
        }

        if(selectedFloor == null)
        {
            throw new RuntimeException("Unable to identify floor");
        }

        // Step 4 : Occupy the spot

        selectedFloor.occupySpot(spot, vehicle);

        // Step 5 : Generate parking ticket from entry gate

        ParkingTicket ticket = entryGate.generateTicket(vehicle, selectedFloor, spot);

        // Step 6 : Store the ticket using ticket number

        activeTickets.put(ticket.getTicketNumber(), ticket);

        // Step 7 : Store ticket using vehicle number

        vehicleTicketMap.put(vehicle.getVehicleNumber(), ticket);

        return ticket;

    }

    /*
      ||Algorithm for removing vehicle

        Find ticket
            |
        Process exit
            |
        Calculate Charges
            |
        Payment
            |
        Release spot
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

        // Step 3 : Release the occupied spot
        ticket.getFloor().releaseSpot(ticket.getSpot());

        // Step 4 : Remove ticket 
        activeTickets.remove(ticketNumber);

        // Step 5 : Remove vehicle
        vehicleTicketMap.remove(ticket.getVehicle().getVehicleNumber());

        System.out.println("------Vehicle removed successfully---------");
    }

    // Search the specified method
    public ParkingTicket searchVehicle(String vehicleNumber)
    {
        return vehicleTicketMap.get(vehicleNumber);
    }

    // Display complete parking lot information

    public void displayParkingLot()
    {
        System.out.println();

        System.out.println("------------------------------------------");
        System.out.println("---------- Parking Lot Details -----------");
        System.out.println("------------------------------------------");

        for(ParkingFloor floor : floors)
        {
            floor.displayFloor();
        }

    }
}// End of parking lot class


class program1011
{
    public static void main(String A[]) 
    {

    } 

}// End of main class
