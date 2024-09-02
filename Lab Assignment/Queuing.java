import java.util.Queue;

class Queuing extends Events {
    private final Server server;

    Queuing(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    Pair<Events, Pair<ImList<Server>, Manage>> nextEvent(ImList<Server> serverList, 
            Manage manager) {
        if (this.getCustomer().getCustID() == serverList.get(server.getserverID() - 1)
                .getQueue().get(0).getCustID()) {
            if ((this.getTime() >= serverList.get(this.server.getserverID() - 1) 
                        .getTimeAvailable())) {
                Serve custServe = new Serve(this.getCustomer(), 
                        this.server.getTimeAvailable(),
                        serverList.get(this.server.getserverID() - 1)
                        .removeQueue());
                Server serverupdate = serverList.get(server.getserverID() - 1).removeQueue();
                serverList = serverList.set(server.getserverID() - 1, serverupdate);
                Pair<Events, Pair<ImList<Server>, Manage>> update = 
                    new Pair<Events, Pair<ImList<Server>, Manage>>(custServe, 
                            new Pair<ImList<Server>, Manage>(serverList, manager));
                return update;
            }
        }

        if (manager.canServe(this.getTime(), this.server.getserverID())) {
            int index = manager.counter(this.getTime());
            Sco sco = manager.update(index, this.getTime());
            Manage updated = manager.updateManager(sco);
            serverList = serverList.set(this.server.getserverID() - 1, 
                    serverList.get(server.getserverID() - 1).removeQueue());
            Serve custServe = new Serve(this.getCustomer(), 
                sco.getTimeAvailable(),
                sco);
            Pair<Events, Pair<ImList<Server>, Manage>> update =
                new Pair<Events, Pair<ImList<Server>, Manage>>(custServe,
                        new Pair<ImList<Server>, Manage>(serverList, updated));
            return update;

        }  else if (manager.canWait(this.server.getserverID())) {
            Queuing custQueue = new Queuing(this.getCustomer(), manager.lowest(), 
                    serverList.get(this.server.getserverID() - 1));
            Pair<Events, Pair<ImList<Server>, Manage>> update =
                new Pair<Events, Pair<ImList<Server>, Manage>>(custQueue, 
                        new Pair<ImList<Server>, Manage>(serverList, manager));
            return update;
        }  

        Queuing custQueue = new Queuing(this.getCustomer(),
                serverList.get(this.server.getserverID() - 1)
                .getTimeAvailable(),
                serverList.get(this.server.getserverID() - 1));
        Pair<Events, Pair<ImList<Server>, Manage>> update =
            new Pair<Events, Pair<ImList<Server>, Manage>>(custQueue, 
                    new Pair<ImList<Server>, Manage>(serverList, manager));
        return update;
    }

    public String toString() {
        return "";
    }
}
