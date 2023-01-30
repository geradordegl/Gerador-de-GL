package generalization;

import java.util.ArrayList;


public class Episode {
    
    private ArrayList<Event> sequenceEvents;
    private int tamananho;
    
    public Episode(){
        this.sequenceEvents= new ArrayList<>();
    }
      
    public void addSequenceEvents(Event event) {
        sequenceEvents.add(event);
    }

    public ArrayList<Event> getSequenceEvents() {
        return sequenceEvents;
    } 

    public int getTamananho() {
        return tamananho;
    }

    public void setTamananho(int tamananho) {
        this.tamananho = tamananho;
    }
    
}
