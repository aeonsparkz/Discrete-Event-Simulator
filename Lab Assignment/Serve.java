class Serve extends Events {
    
    private final Server server;
    
    Serve(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    Pair<Events, Pair<ImList<Server>, Manage>> nextEvent(ImList<Server> serverList, 
            Manage manager) {
        double serveTime = this.getCustomer().getServiceTime();
        Server serverUpdate = server.served(serveTime, this.getTime(), 
                this.getCustomer().getArrivalTime());
        Manage newManager = manager;
        for (int i = 0; i < manager.get().size(); i++) {
            if (this.server.getserverID() == manager.get().get(i).getserverID()) {
                Sco update = manager.get().get(i).updateTime(serverUpdate.getTimeAvailable());
                update = update.addServed();
                newManager = manager.updateManager(update);
                break;
            }
        }
        Done custDone = new Done(this.getCustomer(),
                serverUpdate.getTimeAvailable(), serverUpdate.addServed());
        serverList = serverList.set(this.server.getserverID() - 1,
                serverUpdate.addServed());
        Pair<Events, Pair<ImList<Server>, Manage>> update =
            new Pair<Events, Pair<ImList<Server>, 
                Manage>>(custDone, new Pair<ImList<Server>, Manage>(serverList, newManager));
        return update;
    }

    @Override
    public double getWaitingTime() {
        return this.getTime() - this.getCustomer().getArrivalTime();
    }
    
    public String toString() {
        return String.format("%.3f %d serves by %s", 
                this.getTime(),
                this.getCustomer().getCustID(), this.server.toString()) + "\n";
    }
}
