class Wait extends Events {

    private final Server server;

    Wait(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    Pair<Events, Pair<ImList<Server>, Manage>> nextEvent(ImList<Server> serverList, 
            Manage manager) {
        Queuing custQueue = new Queuing(this.getCustomer(),
                this.getTime(),
                this.server);
        Pair<Events, Pair<ImList<Server>, Manage>> update =
            new Pair<Events, Pair<ImList<Server>, Manage>>(custQueue, 
                    new Pair<ImList<Server>, Manage>(serverList, manager));
        return update;
    }
    
    public String toString() {
        return String.format("%.3f %d waits at %s", this.getTime(),
                this.getCustomer().getCustID(), this.server.toString()) + "\n";
    }
}
