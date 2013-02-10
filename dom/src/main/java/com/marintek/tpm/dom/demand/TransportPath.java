/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marintek.tpm.dom.demand;

import com.marintek.tpm.dom.demand.TransportDemand;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import org.apache.isis.applib.annotation.Hidden;

/**
 *
 * @author chris
 */
//@Hidden
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
public class TransportPath {
    private String title;

    public TransportPath(){
        System.out.println("Allocating new TP");
    }
//    @TypicalLength(80)
    public String title() {
        return title;
    }
    private List<ShortLeg> legs = new ArrayList<ShortLeg>();

    @Hidden
    public void addLeg(ShortLeg leg){
        //System.out.println("adding leg " + leg.getFrom() + "-" + leg.getTo());
        if (legs.isEmpty()) {
            title = leg.getFrom() + "-" + leg.getTo();
        }else{
            title += "-" + leg.getTo();
        }
            
        getLegs().add(leg);
    }
    
    //@Resolve(Resolve.Type.EAGERLY)
    @Persistent
    public List<ShortLeg> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<ShortLeg> legs) {
        System.out.println("Setting legs " + legs);
        this.legs = legs;
    }
}
