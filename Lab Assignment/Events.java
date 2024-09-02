abstract class Events {
    private final double time;
    private final Customer customer;

    Events(Customer customer, double time) {
        this.customer = customer;
        this.time = time;
    }

    protected double getTime() {
        return this.time;
    }

    protected Customer getCustomer() {
        return this.customer;
    }

    protected double getWaitingTime() {
        return 0;
    }
    
    abstract Pair<Events, Pair<ImList<Server>, Manage>>
        nextEvent(ImList<Server> serverList, Manage manager);
}
