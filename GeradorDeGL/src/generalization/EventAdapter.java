package generalization;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EventAdapter {
    
    public void adaptEvent(ArrayList<Event> events) {
        for(Event ev: events) {
            
            if(ev.getEventType().contains("{")) {
                detachLiteral(ev);
                detachLemmaFromMultipleOptionsEvent(ev);
                detachGrammaticalCodesFromMultipleOptionsEvent(ev);
                detachInflectionFromMultipleOptionsEvent(ev);
            } else if (ev.getEventType().contains(",")){
                detachLiteral(ev);
                detachLemma(ev);
                detachGrammaticalCodes(ev);
                detachInflection(ev);
            } else {
                ev.setLiteral(ev.getEventType());
            }
        }
    }
    
    private void detachLiteral(Event ev) {
        Pattern p = Pattern.compile("(.*?),");
        Matcher m = p.matcher(ev.getEventType());
        if(m.find()) {
            ev.setLiteral(m.group(1));
        }
    }
   
    private void detachLemma(Event ev) {
        Pattern p = Pattern.compile(",(.*)\\.");    
        Matcher m = p.matcher(ev.getEventType());
        if(m.find()) {
            ev.setLemma(m.group(1));
        }
    }
    
    private void detachGrammaticalCodes(Event ev) {
        Pattern p = Pattern.compile("\\.(.*):");
        Matcher m = p.matcher(ev.getEventType());
        if(m.find()) {
            ev.setGrammaticalCodes(m.group(1));
        }   
    }
        
    private void detachInflection(Event ev) {
        Pattern p = Pattern.compile(":(.*)$"); 
        Matcher m = p.matcher(ev.getEventType());
        if(m.find()) {
            ev.setInflections(m.group(1));
        }
    }
   
   
    private void detachLemmaFromMultipleOptionsEvent(Event ev) {
        Pattern p = Pattern.compile(",(.*?)\\.");    
        Matcher m = p.matcher(ev.getEventType());
        String lema = "";
        while(m.find()) {          
            lema = lema + "|" + m.group(1);
        }
        ev.setLemma(lema.substring(1, lema.length()));
    }
    
    private void detachGrammaticalCodesFromMultipleOptionsEvent(Event ev) {
        Pattern p = Pattern.compile("\\.(.*?)(:|\\})");
        Matcher m = p.matcher(ev.getEventType());
        String code = "";
        while(m.find()) {
            code = code + "|" + m.group(1);
            m.group(1);
            m.group(2);
        }
        ev.setGrammaticalCodes(code.substring(1, code.length()));
    }
        
    private void detachInflectionFromMultipleOptionsEvent(Event ev) {
        Pattern p = Pattern.compile(":(.*?)(\\}|$)");
        Matcher m = p.matcher(ev.getEventType());
        String inflection = "";
        while(m.find()) {
            inflection = inflection + "|" + m.group(1);
        }
        ev.setInflections(inflection.substring(1, inflection.length()));
    }
}
