/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marintek.tpm.dom.transport;

import com.danhaywood.isis.wicket.gmap3.applib.Locatable;
import com.danhaywood.isis.wicket.gmap3.applib.Location;
import com.marintek.service.LocationService;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

/**
 *
 * @author chris
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
//@javax.jdo.annotations.Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION")
//@ObjectType("Route")
public class Route implements Comparable {

//    public String iconName() {
//        return "status_ok";
//    }
    //    @Persistent
//    @Join(column="ROUTE_ID")
//    //@Element(column="PROVIDER_ID")
    ////////////
//    @Join
//    List<TransportServiceProvider> providers;
//    public List<TransportServiceProvider> getProviders() {
//        return providers;
//    }
//
//    public void setProviders(List<TransportServiceProvider> providers) {
//        this.providers = providers;
//    }
//    
//    public void addToProviders(TransportServiceProvider tsp){
//        if (tsp == null || getProviders().contains(tsp)) {
//            return;
//        }
//        tsp.addToRoutes(this);
//        getProviders().add(tsp);
//        //tsp.addToRoutes(this);
//    }
//    public void removeFromProviders(TransportServiceProvider tsp){
//        if (tsp == null || !getProviders().contains(tsp)) {
//            return;
//        }
//        tsp.removeFromRoutes(this);
//        getProviders().remove(tsp);
//    }
//
//    

    public String title() {
        return fromCity + "-" + toCity;
    }
    private String fromCity;

    public String getFromcity() {
        return fromCity;
    }

    public void setFromcity(String fromcity) {
        this.fromCity = fromcity;
    }
    private String toCity;

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    @Programmatic
    @Override
    public int compareTo(Object t) {
        String a = fromCity + toCity;
        return a.compareTo(fromCity + toCity);
    }

    public class Endpoint implements Locatable {

        String where;
        Location loc;
        
        public Endpoint(String where) {
            this.where = where;
            loc = ls.fromGoogleAddress(where);

        }

        public String title() {
            return where;
        }

        @Override
        public Location getLocation() {
            return loc;
        }
    }

    @Named("Waypoints")
    public List<Endpoint> getRoute() {
        List<Endpoint> l = new ArrayList<Endpoint>();

        l.add(new Endpoint(fromCity));
        l.add(new Endpoint(toCity));
        return l;

    }
//    @Bulk
//    public void deleteRoute(Route r){
//        System.out.println("delete " + r.getFromcity());
//        
//        List<TransportServiceProvider> tsp = tsps.allTransportServiceProviders();
//        for (TransportServiceProvider t: tsp){
//            if (t.getServedRoutes().contains(r))
//                t.remove(r);
//        }
//        container.remove(r);
//    }
//    
    private TransportServiceProviders tsps;

    public void setTransportServiceProviders(TransportServiceProviders tsps) {
        this.tsps = tsps;
    }
    private DomainObjectContainer container;

    public void setDomainObjectContainer(final DomainObjectContainer container) {
        this.container = container;
    }
    
    private LocationService ls;
    void setLocationService(LocationService ls){
        this.ls = ls;
    }
}
//    @Join
//    List<TransportServiceProvider> providers;
//    public List<TransportServiceProvider> getProviders() {
//        return providers;
//    }
//
//    public void setProviders(List<TransportServiceProvider> providers) {
//        this.providers = providers;
//    }
//    
//    public void addToProviders(TransportServiceProvider tsp){
//        if (tsp == null || getProviders().contains(tsp)) {
//            return;
//        }
//        tsp.addToRoutes(this);
//        getProviders().add(tsp);
//        //tsp.addToRoutes(this);
//    }
//    public void removeFromProviders(TransportServiceProvider tsp){
//        if (tsp == null || !getProviders().contains(tsp)) {
//            return;
//        }
//        tsp.removeFromRoutes(this);
//        getProviders().remove(tsp);
//    }
