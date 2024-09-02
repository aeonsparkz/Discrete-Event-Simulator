import java.util.function.Supplier;
import java.util.Random;
import java.util.stream.Stream;

class Simulator {
    private final int numOfServers;
    private final int numOfSelfChecks;
    private final int qMax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTime;
    private final Supplier<Double> restTime;

    Simulator(int numOfServers, int numOfSelfChecks, int qMax, 
            ImList<Double> arrivalTimes,
            Supplier<Double> serviceTime, Supplier<Double> restTime) {
        this.numOfServers = numOfServers;
        this.numOfSelfChecks = numOfSelfChecks;
        this.qMax = qMax;
        this.arrivalTimes = arrivalTimes;
        this.serviceTime = serviceTime;
        this.restTime = restTime;
    }

    public String simulate() {
        String output = "";
        ImList<Customer> custList = new ImList<Customer>();
        ImList<Server> serverList = new ImList<Server>();

        PQ<Events> eventPQ = new PQ<Events>(new EventComparator());

        for (int i = 0;i < this.arrivalTimes.size();i++) {
            Customer customer = new Customer(this.arrivalTimes.get(i),
                    this.serviceTime, i + 1);
            custList = custList.add(customer);
            Arrive custArrive = new Arrive(customer, this.arrivalTimes.get(i));
            eventPQ = eventPQ.add(custArrive); 
        }

        for (int j = 0;j < numOfServers;j++) {
            serverList = serverList.add(new Server(0.00, j + 1,
                        this.qMax, restTime,new ImList<Customer>(), 0, 0));
        }
        ImList<Sco> scoList = new ImList<Sco>();
        for (int l = numOfServers; l < numOfServers + numOfSelfChecks; l++) {
            if (l == numOfServers) {
                serverList = serverList.add(new Sco(0.00, l + 1,
                        this.qMax, restTime, new ImList<Customer>(), 0, 0));
                scoList = scoList.add(new Sco(0.00, l + 1, this.qMax, restTime,
                        new ImList<Customer>(), 0, 0));
            } else {
                serverList = serverList.add(new Sco(0.00, l + 1, 0, restTime,
                            new ImList<Customer>(), 0, 0));
                scoList = scoList.add(new Sco(0.00, l + 1, 0, restTime,
                            new ImList<Customer>(), 0, 0));
            }
        }
        Manage manager = new Manage(scoList);

        int totalserved = 0;
        double totalWaitTime = 0;
        double averageWaitTime = 0;

        while (!eventPQ.isEmpty()) {
            Pair<Events, PQ<Events>> pq = eventPQ.poll();
            Events currentEvent = pq.first();
            Pair<Events, Pair<ImList<Server>, Manage>> 
                pr = currentEvent.nextEvent(serverList, manager);
            output = output + currentEvent.toString();
            Events nextEv = pr.first();
            totalWaitTime += nextEv.getWaitingTime();
            serverList = pr.second().first();
            manager = pr.second().second();
            if (currentEvent == nextEv) {
                eventPQ = pq.second();
            } else {
                eventPQ = pq.second();
                eventPQ = eventPQ.add(nextEv);
            }
        }
        for (Server server : serverList) {
            totalserved += server.getNumServed();
        }
        if (totalserved > 0) {
            averageWaitTime = totalWaitTime / totalserved; 
        } else {
            averageWaitTime = 0;
        }

        return output + "[" + String.format("%.3f", averageWaitTime)
            + " " + totalserved 
            + " "  + (custList.size() - totalserved) + "]"; 
    }
}
