import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RentalSystem {
	static RentalSystem rentalSystem = new RentalSystem();
	
	private RentalSystem (){}
	
	static RentalSystem getInstance()
	{
		return rentalSystem;
	}
	
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();

    public boolean addVehicle(Vehicle vehicle) {
    	if(vehicles.getLicensePlate() )
    	{
            vehicles.add(vehicle);
        	try {
        		BufferedWriter saveVehicle = new BufferedWriter(new FileWriter("vehicles.txt"));
        		saveVehicle.write("\n" + vehicle.getLicensePlate() + "|" + vehicle.getMake() + "|" + vehicle.getModel() + "|" + vehicle.getYear());
        		saveVehicle.close();
        	} catch(IOException e) 
        	{
        		e.printStackTrace();
        	}
        	return true;
    	}
    	else
    	{
    		return false;
    	}
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    	try {
    		BufferedWriter saveCustomer = new BufferedWriter(new FileWriter("customers.txt"));
    		saveCustomer.write("\n" + customer.toString());
    		saveCustomer.close();
    	} catch(IOException e) 
    	{
    		e.printStackTrace();
    	}
    }

    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
            vehicle.setStatus(Vehicle.VehicleStatus.RENTED);
            rentalHistory.addRecord(new RentalRecord(vehicle, customer, date, amount, "RENT"));
            System.out.println("Vehicle rented to " + customer.getCustomerName());
            
            try {
            	BufferedWriter saveRecord = new BufferedWriter(new FileWriter("rental_records.txt"));
            	saveRecord.write("\n" + vehicle.getInfo());
            	saveRecord.close();
            } catch(IOException e)
            {
            	e.printStackTrace();
            }
        }
        else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.RENTED) {
            vehicle.setStatus(Vehicle.VehicleStatus.AVAILABLE);
            rentalHistory.addRecord(new RentalRecord(vehicle, customer, date, extraFees, "RETURN"));
            System.out.println("Vehicle returned by " + customer.getCustomerName());
            
            try {
            	BufferedWriter saveRecord = new BufferedWriter(new FileWriter("rental_records.txt"));
            	saveRecord.write("\n" + vehicle.getInfo());
            	saveRecord.close();
            } catch(IOException e)
            {
            	e.printStackTrace();
            }
        }
        else {
            System.out.println("Vehicle is not rented.");
        }
    }    

    public void displayAvailableVehicles() {
    	System.out.println("|     Type         |\tPlate\t|\tMake\t|\tModel\t|\tYear\t|");
    	System.out.println("---------------------------------------------------------------------------------");
    	 
        for (Vehicle v : vehicles) {
            if (v.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
                System.out.println("|     " + (v instanceof Car ? "Car          " : "Motorcycle   ") + "|\t" + v.getLicensePlate() + "\t|\t" + v.getMake() + "\t|\t" + v.getModel() + "\t|\t" + v.getYear() + "\t|\t");
            }
        }
        System.out.println();
    }
    
    public void displayAllVehicles() {
        for (Vehicle v : vehicles) {
            System.out.println("  " + v.getInfo());
        }
    }

    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }
    
    public void displayRentalHistory() {
        for (RentalRecord record : rentalHistory.getRentalHistory()) {
            System.out.println(record.toString());
        }
    }
    
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }
    
    public Customer findCustomerById(int id) {
        for (Customer c : customers)
            if (c.getCustomerId() == id)
                return c;
        return null;
    }

    public Customer findCustomerByName(String name) {
        for (Customer c : customers)
            if (c.getCustomerName().equalsIgnoreCase(name))
                return c;
        return null;
    }
    
    public void saveRecord(RentalRecord record)
    {
    	try {
    		BufferedWriter rentalRecordWriter = new BufferedWriter(new FileWriter("rental_records.txt"));
    		
    		rentalRecordWriter.close();
    	} catch(IOException e) 
    	{
    		e.printStackTrace();
    	}
    }
    
    public void loadData()
    {
    	String line;
    	try {
    		BufferedReader vehicleReader = new BufferedReader(new FileReader("vehicles.txt"));
    		while((line = vehicleReader.readLine()) != null)
    		{
    			System.out.println(line);
    		}
    		vehicleReader.close();
    	}catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	try {
    		BufferedReader customerReader = new BufferedReader(new FileReader("customers.txt"));
    		while((line = customerReader.readLine()) != null)
    		{
    			System.out.println(line);
    		}
    		customerReader.close();
    	}catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	try {
    		BufferedReader recordReader = new BufferedReader(new FileReader("rental_records.txt"));
    		while((line = recordReader.readLine()) != null)
    		{
    			System.out.println(line);
    		}
    		recordReader.close();
    	}catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}