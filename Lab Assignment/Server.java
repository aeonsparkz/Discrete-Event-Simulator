import java.util.function.Supplier;

class Server {
    private final double timeAvailable;
    private final int serverID;
    private final int qMax;
    private final Supplier<Double> restTime;
    private final ImList<Customer> queue;
    private final int numServed;
    private final double totalWaitTime;
    
    Server(double timeAvailable, int serverID, int qMax,
            Supplier<Double> restTime, ImList<Customer> queue, 
            int numServed, double totalWaitTime) {
        this.timeAvailable = timeAvailable;
        this.serverID = serverID;
        this.qMax = qMax;
        this.restTime = restTime;
        this.queue = queue;
        this.numServed = numServed;
        this.totalWaitTime = totalWaitTime;
    }

    public double getTimeAvailable() {
        return this.timeAvailable;
    }

    public int getserverID() {
        return this.serverID;
    }

    boolean queueAvailable() {
        return (this.queue.size() < this.qMax);
    }

    int getQMax() {
        return this.qMax;
    }

    int getNumServed() {
        return this.numServed;
    }

    Supplier<Double> getSupplierRestTime() {
        return this.restTime;
    }

    double getRestTime() {
        return this.restTime.get();
    }

    Server addQueue(Customer customer) {
        ImList<Customer> queueUpdate = this.queue.add(customer);
        Server serverUpdate = new Server(
                this.getTimeAvailable(),
                this.getserverID(),
                this.getQMax(),
                this.getSupplierRestTime(),
                queueUpdate,
                this.getNumServed(),
                this.getTotalWaitTime());
        return serverUpdate;
    }

    Server removeQueue() {
        ImList<Customer> queueRemove = this.queue.remove(0);
        Server serverUpdate = new Server(
                this.getTimeAvailable(),
                this.getserverID(),
                this.getQMax(),
                this.restTime,
                queueRemove,
                this.getNumServed(),
                this.getTotalWaitTime());
        return serverUpdate;
    }

    Server addServed() {
        Server serverAddServed = new 
            Server(this.getTimeAvailable(),
            this.serverID,
            this.qMax,
            this.restTime,
            this.queue,
            this.numServed + 1,
            this.getTotalWaitTime());
        return serverAddServed;
    }

    Server served(Double serviceTime, Double time, Double custArrival) {
        Server serverUpdate = new Server(
                this.getTimeAvailable() + serviceTime,
                this.getserverID(),
                this.getQMax(),
                this.getSupplierRestTime(),
                this.getQueue(),
                this.getNumServed(),
                this.getTotalWaitTime() + time
                - custArrival);
        return serverUpdate;
    }

    Server doneServing(ImList<Server> serverList) {
        double restTime = this.getRestTime();
        Server serverUpdate = new Server(
                this.getTimeAvailable() + restTime,
                this.getserverID(),
                this.getQMax(),
                this.getSupplierRestTime(),
                serverList.get(this.getserverID() - 1).getQueue(),
                this.getNumServed(),
                this.getTotalWaitTime());
        return serverUpdate;
    }

    Server updateTime(double time) {
        Server update = new Server(time,
                this.getserverID(),
                this.getQMax(),
                this.getSupplierRestTime(),
                this.getQueue(),
                this.getNumServed(),
                this.getTotalWaitTime());
        return update;
    }

    ImList<Customer> getQueue() {
        return this.queue;
    }

    double getTotalWaitTime() {
        return this.totalWaitTime;
    }

    public String toString() {
        return "" + this.serverID;
    }

    public String serves(Customer cust) {
        return (cust.toString() + this.toString() + "\n");
    }
}
