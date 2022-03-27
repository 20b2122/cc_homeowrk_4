import javafx.scene.effect.Light.Point;

// Chapter 6 - Objects and Data Structures

//Data Abstaction
//abstration because we do not know anything about the form of the data (internal details such as in what measurement)
//we do not want to expose theb details of our data, but to express then in abstract terms (hiding details)
public interface Vehicle {
    double getPercentageFuelRemaining;
}

//Data Structures
// show their data (public) and have no functions

// the shape class data are all in public and have no fuction except for the geometry class.
// if say i were to add a perimeter class, the shape class would not be affected, 
// and i would have to add some functions in the geometry class only.
public class Square {
    public Point topLeft;
    public double side;
}

public class Rectangle {
    public Point topLeft;
    public double height;
    public double width;
}

public class Circle {
    public Point center;
    public double radius;
}

public class Geometry {
    public final double PI = 3.141592653589793;

    public double area(Object shape) throws NoSuchShapeException {
        if (shape instanceof Square) {
            Square s = (Square) shape;
            return s.side * s.side;
        } else if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            return r.height * r.width;
        } else if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            return PI * c.radius * c.radius;
        }
        throw new NoSuchShapeException();
    }
}

// Object Oriented 
//hide their data (private) and have dunctions to operate on that data

// there area() function is polymorphic and no geometry class is needed.
// when a new shape is added none of the existing functions will be affected.
// when a new function is added all of the shapes must be changed.
public interface Shape {
    public double area();
}

public class Square implements Shape {
    private Point topLeft;
    private double side;
    
    public double area(){
        return side * side;
    }
}

public class Rectangle implements Shape {
    private Point topLeft;
    private double height;
    private double width;

    public double area(){
        return height * width;
    }
}

public class Circle implements Shape {
    private Point center;
    private double radius;
    public final double PI = 3.141592653589793;
    
    public double area(){
        return PI * radius * radius;
    }
}


//Law of Demeter - says module should not know about the inner details of the object it manipulates
class MyClass {
    IService service;

    public MyClass(IService service) {
        this.service = service;
    }

    public void myMethod(Param param) {
        //1. O itself
        anotherMethod();

        //2. M's parameters
        param.method1();

        //3. Any objects created within m
        TempObject temp = new TempObject();
        temp.doSomething();

        //4. O's direct component objects
        service.provideService();
    }

    private void anotherMethod(){
        ...
    }
}


// Data Transfer Objects
public class Address {
    private String street;
    private String streetExtra;
    private String city;
    private String state;
    private String zip;

    public Address(String street, String streeExtra, String city, String state, String zip) {
        this.street = street;
        this.streetExtra = streeExtra;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetExtra() {
        return streetExtra;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }
}


// Chapter 7 - Error Handling

//use Exceptions Rather than Return Codes
//example using exception code - to detect errors, cleaner,
//the algorithm for device shutdown and error handling are separated.

public class DeviceController {

    public void sendShutDown() {
        try {
            tryToShutDown();
        } catch (DeviceSHutDownError e) {
            logger.log(e);
        }
    }

    private void tryToShutDown() throws DeviceSHutDownError {
        DeviceHandle handle = getHandle(DEV1);
        DeviceRecord record = retrieveDeviceRecord(handle);

        pauseDevice(handle);
        clearDeviceWorkQueue(handle);
        clodeDevice(handle);
    }

    private DeviceHandle getHandle (DevideID id) {
        throw new DeviceSHutDownError("Invalid handle for: " + id.toString());
    }
}


//Define Exception Classes in Terms of a Caller's Needs
//return a common exception type
LocalPort port = new LocalPort(12);
try {
    port.open();
} catch (PortDeciveFailure e){
    reportError(e);
    logger.log(e.getMessage(), e);
} finally {
    ...
}


//Define The Normal Flow
//SPECIAL CASE PATTERN [Fowler] - create a class or configure an object so that it handles a special case for you, 
//behaviour is encapsulated in the special case object
public class PerDiemMealExpenses implements MealExpenses {
    public int getTotal() {
        // return the per diem default
    }
}


//Dont Return Null
//special case objects - the employees does not have to return null
List<Employee> employees = getEmployees();
for (Employee e : employees){
    totalPay += e.getPay();
}


//Dont Pass Null
//better than having a null pointer exception
public class MetricsCalculator {
    public double xProjection(Point p1, Point p2) {
        if (p1 == null || p2 == null) {
            throw InvalidArgumentException ("Invalid argument for MetricsCalculator.xProjection");
        }
        reutrn (p2.x - p1.x) * 1.5;
    }
}

//another alternative
public class MetricsCalculator {
    public double xProjection(Point p1, Point p2) {
        assert p1 != null : "p1 should not be null";
        assert p2 != null : "p2 should not be null";
        reutrn (p2.x - p1.x) * 1.5;

    }

}



// Chapter 8 - Boundaries

//using third-party code
//improved readability by using generics
//however using the Map<Sensor> means there will be a lot of places to fix if the interface to map ever cahgnes
Map<Sensor> sensors = new HashMap<Sensor>();
...
Sensor s = (Sensor)sensors.get(sensorId);

//more cleaner than above example, interface at the boundary (Map) is hidden
public class Sensors {
    private Map sensors = new HashMap();

    public Sensor getById(String id){
        return (Sensor) sensors.get(id);
    }

    //snip
}


//Learning log4j
@Test
public void testLogAddAppender() {
    Logger logger = Logger.getLogger("MyLogger");
    logger.removeAllAppenders();
    logger.addAppender(new ConsoleAppender(new PatternLayout("%p %t %m%n"), ConsoleAppender.SYSTEM_OUT));
    logger.info("hello");
}