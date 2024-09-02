import java.util.function.Supplier;

class Customer {
    private final double arrivalTime;
    private final Supplier<Double> serviceTime;
    private final int custID;

    Customer(double arrivalTime, Supplier<Double>  serviceTime, int custID) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.custID = custID;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public int getCustID() {
        return this.custID;
    }
    
    double getServiceTime() {
        return this.serviceTime.get();
    }

    public String toString() {
        return String.format("%.3f",this.arrivalTime) + " customer " + this.custID;
    }

    public String arrives() {
        return this.toString() + " arrives" + "\n";
    }

    public String leaves() {
        return this.toString() + " leaves" + "\n";
    }
}
