package generalization;

import java.util.Objects;


public class Event implements Comparable<Event> {
    
    private String eventType;
    private String inflections;
    private String grammaticalCodes;    
    private String lemma;
    private String literal;
    private int position;
    private int frequency;
    
    

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    
    public String getGrammaticalCodes() {
        return grammaticalCodes;
    }

    public void setGrammaticalCodes(String grammaticalCodes) {
        this.grammaticalCodes = grammaticalCodes;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
       
    public void setInflections(String inflections) {
        this.inflections = inflections;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getInflections() {
        return inflections;
    }

    public String getLiteral() {
        return literal;
    }

    public String getLemma() {
        return lemma;
    }    
    
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(eventType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (!Objects.equals(eventType, other.eventType)) {
            return false;
        }
        
        return true;
    }

    

    @Override
    public int compareTo(Event t) {
        return Integer.compare(frequency, t.getFrequency());
    }
    
   public void printEvent() {
       
       System.out.println("\n");
       System.out.println("event type " + eventType);
       System.out.println("literal " + literal);
       System.out.println("lema " + lemma);
       System.out.println("code " + grammaticalCodes);
       System.out.println("inflections " + inflections);
       
   }
}
