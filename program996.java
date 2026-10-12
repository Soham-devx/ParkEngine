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

class program996 
{
    public static void main(String A[]) 
    {

    }    
}
