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
// Step 7 : Create ParkingStrategy Class 
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



class program1007
{
    public static void main(String A[]) 
    {

    }    
}
