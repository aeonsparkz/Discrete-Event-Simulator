class Done extends Events {
    private final Server server;

    Done(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    Pair<Events, Pair<ImList<Server>, Manage>> nextEvent(ImList<Server> serverList, 
            Manage manager) {
        Server serverUpdate = server.doneServing(serverList);
        serverList = serverList.set(this.server.getserverID() - 1, serverUpdate);
        Pair<Events, Pair<ImList<Server>, Manage>> update =
            new Pair<Events, Pair<ImList<Server>, Manage>>(this, 
                    new Pair<ImList<Server>, Manage>(serverList, manager));
        return update;
    }

    public String toString() {
        return String.format("%.3f %d done serving by %s", 
                this.getTime(), this.getCustomer().getCustID(), server.toString()) + "\n";
    }
}

