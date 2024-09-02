import java.util.function.Supplier;

class Sco extends Server {

    Sco(double timeAvailable, int serverID, int qMax, Supplier<Double> restTime, 
            ImList<Customer> queue, int numServed, double totalWaitTime) {
        super(timeAvailable, serverID, qMax, restTime, 
                queue, numServed, totalWaitTime);
    }

    @Override
    Sco addQueue(Customer customer) {
        ImList<Customer> queueUpdate = this.getQueue().add(customer);
        Sco serverUpdate = new Sco(
                this.getTimeAvailable(),
                this.getserverID(),
                this.getQMax(),
                this.getSupplierRestTime(),
                queueUpdate,
                this.getNumServed(),
                this.getTotalWaitTime());
        return serverUpdate;
    }

    @Override
    Sco updateTime(double time) {
        Sco update = new Sco(time,
                this.getserverID(),
                this.getQMax(),
                this.getSupplierRestTime(),
                this.getQueue(),
                this.getNumServed(),
                this.getTotalWaitTime());
        return update;
    }

    @Override
    Sco removeQueue() {
        ImList<Customer> queueRemove = this.getQueue().remove(0);
        Sco serverUpdate = new Sco(
                this.getTimeAvailable(),
                this.getserverID(),
                this.getQMax(),
                this.getSupplierRestTime(),
                queueRemove,
                this.getNumServed(),
                this.getTotalWaitTime());
        return serverUpdate;
    }

    @Override
    Sco addServed() {
        Sco serverAddServed = new 
            Sco(this.getTimeAvailable(),
            this.getserverID(),
            this.getQMax(),
            this.getSupplierRestTime(),
            this.getQueue(),
            this.getNumServed() + 1,
            this.getTotalWaitTime());
        return serverAddServed;
    }

    @Override
    Sco served(Double serviceTime, Double time, Double arrivalTime) {
        Sco serverUpdate = new Sco(
                this.getTimeAvailable() + serviceTime,
                this.getserverID(),
                this.getQMax(),
                this.getSupplierRestTime(),
                this.getQueue(),
                this.getNumServed(),
                this.getTotalWaitTime() + time
                - arrivalTime);
        return serverUpdate;
    }

    @Override
    Sco doneServing(ImList<Server> serverList) {
        double restTime = this.getRestTime();
        Sco serverUpdate = new Sco(
                this.getTimeAvailable() + restTime,
                this.getserverID(),
                this.getQMax(),
                this.getSupplierRestTime(),
                serverList.get(this.getserverID() - 1).getQueue(),
                this.getNumServed(),
                this.getTotalWaitTime());
        return serverUpdate;
    }

    @Override
    double getRestTime() {
        return 0;
    }

    @Override
    public String toString() {
        return "self-check " + super.getserverID();
    }
}    
