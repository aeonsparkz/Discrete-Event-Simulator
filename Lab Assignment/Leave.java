class Leave extends Events {

    Leave(Customer customer, double time) {
        super(customer, time);
    }

    Pair<Events, Pair<ImList<Server>, Manage>> 
        nextEvent(ImList<Server> serverList, Manage manager) {
        Pair<Events, Pair<ImList<Server>, Manage>> update =
            new Pair<Events, Pair<ImList<Server>, Manage>>(this, 
                    new Pair<ImList<Server>, Manage>(serverList, manager));
        return update;
    }

    public String toString() {
        return String.format("%.3f %d", this.getTime(),
                this.getCustomer().getCustID()) + " leaves" + "\n";
    }
}

