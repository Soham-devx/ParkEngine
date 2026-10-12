package Project_Parking_System;

/*
    ParkingLot Automation System

    step 1 : Create required enums
    step 2 : Vehicle Hierarchy creation
    step 3 : VahicleFactory creation (Factory Pattern)
    step 4 : ParkingSlot Hierarchy
    step 5 : ParkingObserver
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


class program998
{
    public static void main(String A[]) 
    {

    }    
}
