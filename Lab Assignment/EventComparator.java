import java.util.Comparator;

class EventComparator implements Comparator<Events> {
    
    @Override
    public int compare(Events event1, Events event2) {
        if (event1.getTime() < event2.getTime()) {
            return -1;
        } else if (event1.getTime() > event2.getTime()) {
            return 1;
        } else {
            if (event1.getCustomer().getCustID() < 
                    event2.getCustomer().getCustID()) {
                return -1;
            } else {
                return 1;
            }
        } 
    }
}



