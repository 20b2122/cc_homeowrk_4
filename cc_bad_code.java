import java.beans.Transient;

// Chapter 6 - Objects and Data Structures

// Data Abstraction
// this is not abstraction because it is showing the implimentation. 
public class Shape {
    private double width;
    private double height;

    public double getWidth(){
        return width;
    }

    public void setWidth(double width){
        this.width = width;
    }

    public double getHeight(){
        return height;
    }

    public void setHeight(double height){
        this.height = height;
    }
}

//also not abstraction
public interface Vehicle {
    double getFuelTankCapacityInGallons();
    double getGallonsofGasoline();
}


//Law of Demeter - Train wreck
//violate the law because calling any methods on the object returned by getOwner() is not allowed
public void showData(Car car) {
    printStreet(acr.getOwner().getAddress().getStreet());
}

//refactored but still violates the law 
Owner owner = car.getOwner();
Address ownerAddress = owner.getAddress();
Street ownerStreet = ownerAddress.getStreet();




// Chapter 7 - Error Handling

//use Exceptions Rather than Return Codes
//example using return code
// problem - clutter the caller, where it must check for erros immediatly after the call
public class DeviceController {

    public void sendShutDown() {

        DeviceHandle handle = getHandle(DEV1);

        //check the state of the device
        if (handle != DeviceHandle.INVALID) {
            //save the device status to the record field
            retrieveDeviceRecord(handle);

            //if not suspended, shut down
            if (record.getStatus() != DEVICE_SUSPENDED) {
                pauseDevice(handle);
                clearDeviceWorkQueue(handle);
                clodeDevice(handle);
            } else {
                logger.log ("Device suspended. Unable to shut down");
            }
        } else {
            logger.log("Invalid handle for: " + DEV1.toString());
        }
    }
}

//Define Exception Classes in Terms of a Caller's Needs
//bad code because contains a lot of duplication 
ACMEPort Port = new ACMEPort(12);

try {
    port.open();
} catch (DeviceResponseException e) {
    reportPortError (e);
    logger.log("Device response exception", e);
} catch (ATM1212UnlockedException e) {
    reportPortError (e);
    logger.log("Unlock exception", e);
} catch (GMXError e) {
    reportPortError (e);
    logger.log("Device response exception");
} finally {
    ...
}


//Define The Normal Flow
//bad code - the exception clutters the logic.
try {
    MealExpenses expenses = expensesReportDAO.getMeals(employee.getID());
    m_total += expenses.getTotal();
} catch (MealExpensesNotFound e) {
    m_total += getMealPerDiem();
}


//Dont Return Null
//too many null check
public void registerItem(Item item) {
    if (item != null) {
        ItemRegistry registry = persistentStore.getItemRegistry();
        if (registry != null) {
            Item existing = registry.getItem(item.getID());
            if (existing.getBillingPeriod().hasRetailOwner()) {
                existing.register(item);
            }
        }
    }
}

//special case objects - the employees does not have to return null
List<Employee> employees = getEmployees();
if (employees != null) {
    for (Employee e : employees){
        totalPay += e.getPay();
    }
}


//Dont Pass Null
//bad code - what if p1 or p2 are null argument, it will then return NullPointerException
public class MetricsCalculator {
    public double xProjection(Point p1, Point p2) {
        reutrn (p2.x - p1.x) * 1.5;
    }
    ...
}



// Chapter 8 - Boundaries

//using third-party code this works but it is not clean code
Sensor s = (Sensor)sensors.get(sensorId);


//Learning log4j
@Test
public void testLogAddAppender() {
    Logger logger = Logger.getLogger("MyLogger");
    ConsoleAppender appender = new ConsoleAppender();
    logger.addAppender(appender);
    logger.info("hello");
}