/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marintek.tpm.dom.transport;

import com.danhaywood.isis.wicket.gmap3.applib.Locatable;
import com.danhaywood.isis.wicket.gmap3.applib.Location;
import com.marintek.service.LocationService;
import com.marintek.tpm.dom.destination.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Resolve;
import org.apache.isis.applib.annotation.Where;

/**
 *
 * @author chris
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
public class TransportServiceProvider implements Comparable<TransportServiceProvider> {

    @Programmatic
    // exclude from the framework's metamodel
    @Override
    public int compareTo(TransportServiceProvider t) {
        return title().compareToIgnoreCase(t.title());
    }

    public String title() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        return sb.toString();
    }
    private String name;

    public String getName() {
        return name;
    }

    @MemberOrder(sequence = "1")
    public void setName(String name) {
        this.name = name;
    }

    public static enum Category {

        Storage, Car, Ship, Airplane, Train;
    }
    // {{ Category
    private Category category;

    @MemberOrder(sequence = "2")
    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }
    private String Address;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
    //private List<Route> servedRoutes = new ArrayList<Route>();
    //@Persistent(mappedBy="providers")
    //@Persistent(mappedBy = "providers")
    @Join
    private List<ServedRoute> servedRoutes;

    //@Disabled
    @MemberOrder(sequence = "2")
    //@Hidden
    @Resolve(Resolve.Type.EAGERLY)
    public List<ServedRoute> getServedRoutes() {
        return servedRoutes;
    }

    public void setServedRoutes(List<ServedRoute> routes) {
        this.servedRoutes = routes;
    }

    @MemberOrder(name = "routes", sequence = "3")
    @Named("Add Route")
    public TransportServiceProvider addRoute(final Route route, @Named("Hours To Travel[h]") double hoursToTravel, @Named("$/kg") double amountPerKg) {
        ServedRoute sr = container.newTransientInstance(ServedRoute.class);
        sr.setRoute(route);
        sr.setTransportServiceProvider(this);
        sr.setAmountPerKg(amountPerKg);
        sr.setHoursToTravel(hoursToTravel);        
//        route.addToProviders(this);
        getServedRoutes().add(sr);
        return this;
    }

    public String validateAddRoute(final Route route, double ttt, double price) {
        if (getServedRoutes().contains(route)) {
            return "Already a route";
        }
        return null;
    }

    public List<Route> choices0AddRoute() {
        return routes.allRoutes();
    }
    // }}

    // {{ remove (action)
    @MemberOrder(sequence = "4")
    @Named("Remove Route")
    public TransportServiceProvider removeRoute(final ServedRoute r) {
//        r.removeFromProviders(this);
        getServedRoutes().remove(r);
//        List<Leg> legs = container.allInstances(Leg.class);
//        for (Leg leg: legs){
//            if (leg.getTransportServiceProvider().equals(this)
//                && leg.getRoute().equals(r)){
//                leg.setTransportServiceProvider(null);
//            }
//        }
        
        return this;
    }
//    @Programmatic
//    public void removeFromRoutes(final Route route){
//        getServedRoutes().remove(route);
//    }

    
    public String disableRemoveRoute(final ServedRoute r) {
        return getServedRoutes().isEmpty() ? "No routes to remove" : null;
    }

//    public String validateRemoveRoute(final Route route) {
//        if (!getServedRoutes().contains(route)) {
//            return "Not a route";
//        }
//        return null;
//    }

    public List<ServedRoute> choices0RemoveRoute() {
        for (ServedRoute r : servedRoutes) {
            //System.out.println(r);
        }

        return servedRoutes;
    }
    
    
    public class Waypoint implements Locatable, Comparable {

        String where;
        Location loc;

        public String title() {
            return where;
        }

        public Waypoint(String where) {
            this.where = where;
            loc = ls.fromGoogleAddress(where);
        }

        @Override
        public Location getLocation() {
            //return Location.fromGoogleAddress(where);
            return loc;
        }
        
        
        public boolean equals(Waypoint w){
            return (w.loc.equals(loc));
        }

        @Override
        public int compareTo(Object t) {
            return (((Waypoint) t).where.compareTo(where));
        }
    }

    @MemberOrder(sequence = "2")
    public List<Waypoint> getCities() {
        List<Waypoint> l = new ArrayList<Waypoint>();
        Set<Waypoint> s = new TreeSet<Waypoint>();
        for (ServedRoute r : servedRoutes) {
            s.add(new Waypoint(r.getFromcity()));
            s.add(new Waypoint(r.getToCity()));
        }
        for (Waypoint w: s)
            l.add(w);
        
        return l;
    }
    
    // {{ Notes (property)
    private String notes;

    @Optional
    @MultiLine(numberOfLines = 5)
    @Hidden(where = Where.ALL_TABLES)
    @MemberOrder(name = "Detail", sequence = "6")
    public String getNotes() {
        return notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }
    // }}
//    public static Filter<TransportServiceProvider> thoseOwnedBy(final String currentUser) {
//        return new Filter<TransportServiceProvider>() {
//            @Override
//            public boolean accept(final TransportServiceProvider item) {
//                return Objects.equal(item.getOwnedBy(), currentUser);
//            }
//        };
//    }
    // {{ injected: DomainObjectContainer
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    public void setDomainObjectContainer(final DomainObjectContainer container) {
        this.container = container;
    }
    // }}
    // {{ injected: Destinations
    @SuppressWarnings("unused")
    private Destinations destinations;

    public void setDestinations(final Destinations destinations) {
        this.destinations = destinations;
    }
    @SuppressWarnings("unused")
    private TransportServiceProviders transportDemands;

    public void setTransportServiceProviders(TransportServiceProviders transportDemands) {
        this.transportDemands = transportDemands;
    }
    @SuppressWarnings("unused")
    private Routes routes;

    public void setRoutes(Routes routes) {
        this.routes = routes;
    }
    
    private LocationService ls;
    void setLocationService(LocationService ls){
        this.ls = ls;
    }
    // }}
    
    
}
//    @Resolve(Resolve.Type.EAGERLY)
//    public List<Route> getServedRoutes() {
//        return servedRoutes;
//    }
//
//    public void setServedRoutes(List<Route> routes) {
//        this.servedRoutes = routes;
//    }
//
//    @MemberOrder(name = "routes", sequence = "3")
//    @Named("Add Route")
//    public TransportServiceProvider addRoute(final Route route, @Named("Time [h]") double time, @Named("$/kg") double amountPerKg) {
//        route.addToProviders(this);
//        
//        //getServedRoutes().add(route);
//        return this;
//    }
//
//    @Programmatic
//    public void addToRoutes(final Route route){
//        getServedRoutes().add(route);
//    }
//    
//    public String validateAddRoute(final Route route, double price) {
//        if (getServedRoutes().contains(route)) {
//            return "Already a route";
//        }
//        return null;
//    }
//
//    public List<Route> choices0AddRoute() {
//        return routes.allRoutes();
//    }
//    // }}
//
//    // {{ remove (action)
//    @MemberOrder(sequence = "4")
//    @Named("Remove Route")
//    public TransportServiceProvider removeRoute(final Route r) {
//        r.removeFromProviders(this);
//        //getServedRoutes().remove(r);
//        List<Leg> legs = container.allInstances(Leg.class);
//        for (Leg leg: legs){
//            if (leg.getTransportServiceProvider().equals(this)
//                && leg.getRoute().equals(r)){
//                leg.setTransportServiceProvider(null);
//            }
//        }
//        
//        return this;
//    }
//    @Programmatic
//    public void removeFromRoutes(final Route route){
//        getServedRoutes().remove(route);
//    }
//
//    
//    public String disableRemoveRoute(final Route r) {
//        return getServedRoutes().isEmpty() ? "No routes to remove" : null;
//    }
//
//    public String validateRemoveRoute(final Route route) {
//        if (!getServedRoutes().contains(route)) {
//            return "Not a route";
//        }
//        return null;
//    }
//
//    public List<Route> choices0RemoveRoute() {
//        for (Route r : servedRoutes) {
//            //System.out.println(r);
//        }
//
//        return servedRoutes;
//    }
