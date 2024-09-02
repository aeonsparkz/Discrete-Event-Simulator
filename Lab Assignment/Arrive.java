class Arrive extends Events {
    
    Arrive(Customer customer, double time) {
        super(customer, time);
    }

    Pair<Events, Pair<ImList<Server>, Manage>> nextEvent(ImList<Server> serverList, 
            Manage manager) {
        for (Server server : serverList) {
            if (this.getTime() >= server.getTimeAvailable()) {
                Serve custServe = new Serve(this.getCustomer(), this.getTime(), 
                        server.updateTime(this.getTime()));
                Pair<Events, Pair<ImList<Server>, Manage>> update = 
                    new Pair<Events, Pair<ImList<Server>, Manage>>(custServe, 
                            new Pair<ImList<Server>, Manage>(serverList, manager));
                return update;
            }  
        }

        for (Server server : serverList) {
            if (server.queueAvailable()) {
                Wait custWait = new Wait(this.getCustomer(), this.getTime(),
                        server.addQueue(this.getCustomer()));
                serverList = serverList.set(server.getserverID() - 1, 
                        server.addQueue(this.getCustomer()));
                Pair<Events, Pair<ImList<Server>, Manage>> update =
                    new Pair<Events, Pair<ImList<Server>, Manage>>(custWait, 
                            new Pair<ImList<Server>, Manage>(serverList, manager));
                return update;
            }
        }
        Leave custLeave = new Leave(this.getCustomer(), this.getTime());
        Pair<Events, Pair<ImList<Server>, Manage>> update = 
            new Pair<Events, Pair<ImList<Server>, Manage>>(custLeave, 
                    new Pair<ImList<Server>, Manage>(serverList, manager));
        return update;
    }

    public String toString() {
        return String.format("%.3f %d", this.getTime(),
                this.getCustomer().getCustID()) + " arrives" + "\n";
    }
}
