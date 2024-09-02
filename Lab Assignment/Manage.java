class Manage {

    private final ImList<Sco> scoList;

    Manage(ImList<Sco> scoList) {
        this.scoList = scoList;
    }

    boolean canServe(double time, int id) {
        if (scoList.isEmpty()) {
            return false;
        }

        if (id != scoList.get(0).getserverID()) {
            return false;
        }

        for (int i = 0; i < scoList.size(); i++) {
            if (time >= scoList.get(i).getTimeAvailable()) {
                return true;
            }
        }
        return false;
    }

    int counter(double time) {
        double earliest = time;
        int index = 0;
        for (int i = 0; i < this.scoList.size(); i++) {
            if (earliest >= this.scoList.get(i).getTimeAvailable()) {
                earliest = this.scoList.get(i).getTimeAvailable();
                index = this.scoList.get(i).getserverID();
            }
        }
        return index;
    }

    int checkLoc(int id) {
        int loc = 0;
        for (int i = 0; i < scoList.size(); i++) {
            if (scoList.get(i).getserverID() == id) {
                loc = i;
            }
        }
        return loc;
    }

    Sco update(int index, double time) {
        int loc = this.checkLoc(index);
        Sco updated = this.scoList.get(loc).updateTime(time);
        return updated;
    }

    Manage updateManager(Sco sco) {
        int index = 0;
        for (int i = 0; i < scoList.size(); i++) {
            if (sco.getserverID() == scoList.get(i).getserverID()) {
                index = i;
                break;
            }
        }
        ImList<Sco> update = this.scoList.set(index, sco);
        return new Manage(update);
    }

    boolean canWait(int id) {
        if (scoList.isEmpty()) {
            return false;
        }
        return id == this.scoList.get(0).getserverID();
    }

    double lowest() {
        double lowest = scoList.get(0).getTimeAvailable();
        for (int i = 1; i < this.scoList.size(); i++) { 
            if (lowest > scoList.get(i).getTimeAvailable()) {
                lowest = scoList.get(i).getTimeAvailable();
            }
        }
        return lowest;
    } 

    ImList<Sco> get() {
        return this.scoList;
    }
}
