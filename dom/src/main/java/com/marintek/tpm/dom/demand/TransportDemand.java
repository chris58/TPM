/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marintek.tpm.dom.demand;

import com.danhaywood.isis.wicket.gmap3.applib.Locatable;
import com.danhaywood.isis.wicket.gmap3.applib.Location;
import com.google.common.base.Objects;
import com.marintek.service.LocationService;
import com.marintek.tpm.dom.cargo.CargoItem;
import com.marintek.tpm.dom.destination.*;
import java.util.List;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberGroups;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.filter.Filter;
import org.apache.isis.applib.value.Time;
import org.joda.time.LocalDate;

/**
 *
 * @author chris
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
@MemberGroups({"General", "Pickup", "Delivery"})
public class TransportDemand implements Comparable<TransportDemand> {

    @Programmatic
    // exclude from the framework's metamodel
    @Override
    public int compareTo(TransportDemand t) {
        return title().compareToIgnoreCase(t.title());
    }

    public String title() {
        StringBuilder sb = new StringBuilder();
        sb.append((pickupDate != null) ? pickupDate.toString() : "");
        sb.append(" ");
        sb.append((pickup != null) ? pickup.getCity() : "");
        sb.append(" -> ");
        sb.append((delivery != null) ? delivery.getCity() : "");
        return sb.toString();
    }

    @Named("Set Cargo")
    public TransportDemand newCargo(
            @Named("Description") String description,
            @Named("Category") CargoItem.Category category,
            @Named("HazCode") CargoItem.HazCode hazcode,
            @Named("Amount") double amount,
            @Named("Unit") CargoItem.Unit unit) {
//        final String ownedBy = getContainer().getUser().getName();
        final CargoItem cargoItem = container.newTransientInstance(CargoItem.class);
        cargoItem.setDescription(description);
        cargoItem.setCategory(category);
        cargoItem.setHazCode(hazcode);
        cargoItem.setAmount(amount);
        cargoItem.setUnit(unit);

        container.persist(cargoItem);
        this.cargo = cargoItem;
        return this;
    }

    public boolean hideNewCargo() {
        return (getCargo() != null);
    }
    // {{ OwnedBy (property)
    private String ownedBy;

    @Hidden
    // not shown in the UI
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String ownedBy) {
        this.ownedBy = ownedBy;
    }
    private CargoItem cargo;

    @Optional
    @MemberOrder(sequence = "1")
    public CargoItem getCargo() {
        return cargo;
    }

    public void setCargo(CargoItem cargo) {
        this.cargo = cargo;
    }

    public List<CargoItem> choicesCargo() {
        return container.allInstances(CargoItem.class);
    }
    private Destination pickup;

    @MemberOrder(name = "Pickup", sequence = "2")
    @Hidden(where = Where.ALL_TABLES)
    public Destination getPickup() {
        return pickup;
    }

    public List<Destination> choicesPickup() {
        return container.allInstances(Destination.class);
        //return destinations.allDestinations();
    }

    public void setPickup(Destination pickup) {
        this.pickup = pickup;
    }
    private LocalDate pickupDate;

    @javax.jdo.annotations.Persistent
    @MemberOrder(name = "Pickup", sequence = "3")
    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }
    private Time pickupTime;

    @javax.jdo.annotations.Persistent
    @MemberOrder(name = "Pickup", sequence = "4")
    public Time getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Time pickupTime) {
        this.pickupTime = pickupTime;
    }
    private int pickupInterval;

    @javax.jdo.annotations.Persistent
    @MemberOrder(name = "Pickup", sequence = "5")
    @Named("Interval [hours]")
    public int getPickupInterval() {
        return pickupInterval;
    }

    public void setPickupInterval(int pickupInterval) {
        this.pickupInterval = pickupInterval;
    }
    private Destination delivery;

    @MemberOrder(name = "Delivery", sequence = "7")
    @Hidden(where = Where.ALL_TABLES)
    public Destination getDelivery() {
        return delivery;
    }

    public List<Destination> choicesDelivery() {
        return container.allInstances(Destination.class);
//        return destinations.allDestinations();
    }

    public void setDelivery(Destination delivery) {
        this.delivery = delivery;
    }
    private LocalDate deliveryDate;

    @javax.jdo.annotations.Persistent
    @MemberOrder(name = "Delivery", sequence = "8")
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    private Time deliveryTime;

    @javax.jdo.annotations.Persistent
    @MemberOrder(name = "Delivery", sequence = "9")
    public Time getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Time deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    private int deliveryInterval;

    @javax.jdo.annotations.Persistent
    @MemberOrder(name = "Delivery", sequence = "10")
    @Named("Interval [hours]")
    public int getDeliveryInterval() {
        return deliveryInterval;
    }

    public void setDeliveryInterval(int deliveryInterval) {
        this.deliveryInterval = deliveryInterval;
    }
//    @Persistent(mappedBy = "transportDemand")
//    private List<Leg> plannedLegs = new ArrayList<Leg>();
//
////    @Optional
////    @Disabled
//    @MemberOrder(sequence = "1")
//    @Resolve(Resolve.Type.EAGERLY)
//    public List<Leg> getPlannedLegs() {
//        return plannedLegs;
//    }
//
//    public void setPlannedLegs(ArrayList<Leg> plannedLegs) {
//        this.plannedLegs = plannedLegs;
//    }
//    // {{ Notes (property)
    private String notes;

    @Optional
    @MultiLine(numberOfLines = 5)
    @Hidden(where = Where.ALL_TABLES)
    @MemberOrder(name = "Notes", sequence = "6")
    public String getNotes() {
        return notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }
    // }}

    public class Waypoint implements Locatable {

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
    }

//    public List<Waypoint> getWaypoints() {
//        List<Waypoint> l = new ArrayList<Waypoint>();
//        for (Leg leg : plannedLegs) {
//            l.add(new Waypoint(leg.getFromcity()));
//        }
//        if (plannedLegs.size() > 0) {
//            l.add(new Waypoint(plannedLegs.get(plannedLegs.size() - 1).getToCity()));
//        }
//        return l;
//    }
    
    
    
    
//    public TransportDemand addLeg(Route route) {
//        final Leg leg = container.newTransientInstance(Leg.class);
//        leg.setRoute(route);
//        plannedLegs.add(leg);
//        container.persistIfNotAlready(leg);
//        return this;
//    }
//
//    public List<Route> choices0AddLeg() {
//        return container.allMatches(Route.class, new Filter<Route>() {
//            @Override
//            public boolean accept(Route t) {
//                String fromWhere;;
//                if (getPlannedLegs().size() > 0) {
//                    fromWhere = "";
//                    List<Leg> l = getPlannedLegs();
//                    Leg ll = l.get(l.size() - 1);
//                    fromWhere = ll.getToCity();
//
//                } else {
//                    fromWhere = getPickup().getCity();
//                }
//                return t.getFromcity().equals(fromWhere);
//            }
//        });
//    }
//
//    public TransportDemand removeLeg(Leg leg) {
//        plannedLegs.remove(leg);
//        return this;
//    }
//
//    public List<Leg> choices0RemoveLeg() {
//        return plannedLegs;
//    }
//
//    public boolean hideRemoveLeg() {
//        return plannedLegs.isEmpty();
//    }

    private TransportPath transportPath;

    @TypicalLength(80)
    @Bulk
    public TransportPath getTransportPath() {
        return transportPath;
    }

    public void setTransportPath(TransportPath path) {
        this.transportPath = path;
    }

//    @TypicalLength(80)
//    public TransportDemand plan(TransportPath p){
//        container.persistIfNotAlready(p);
//        this.path = p;
//        return this;
//    }
   
//    @TypicalLength(80)
//    public List<TransportPath> choices0Plan() {
//    public List<TransportPath> choicesTransportPath() {
//        List<Route> lr = container.allInstances(Route.class);
//        HashMap<String, BaseVertex> cityVertexHash = new HashMap<String, BaseVertex>();
//        HashMap<BaseVertex, String> vertexCityHash = new HashMap<BaseVertex, String>();
//        Graph g = new Graph();
//
//        for (Route r : lr) {
//            if (!cityVertexHash.containsKey(r.getFromcity())) {
//                BaseVertex v = g.addVertex();
//                cityVertexHash.put(r.getFromcity(), v);
//            }
//            if (!cityVertexHash.containsKey(r.getToCity())) {
//                BaseVertex v = g.addVertex();
//                cityVertexHash.put(r.getToCity(), v);
//            }
//        }
//        List<ServedRoute> lsr = container.allInstances(ServedRoute.class);
//        for (ServedRoute sr : lsr) {
//            BaseVertex startCity = cityVertexHash.get(sr.getFromcity());
//            BaseVertex start = g.addVertex();
//            vertexCityHash.put(start, sr.getFromcity() + "(" + sr.getTransportServiceProvider().getName() + ")");
//            g.add_edge(startCity.get_id(), start.get_id(), 0.0);
//
//            BaseVertex end = g.addVertex();
//            vertexCityHash.put(end, sr.getToCity() + "(" + sr.getTransportServiceProvider().getName() + ")");
//            BaseVertex endCity = cityVertexHash.get(sr.getToCity());
//            g.add_edge(end.get_id(), endCity.get_id(), 0.0);
//
//            g.add_edge(start.get_id(), end.get_id(), sr.getHoursToTravel());
//        }
//
//        YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(
//                g, g.get_vertex(cityVertexHash.get(pickup.getCity()).get_id()),
//                g.get_vertex(cityVertexHash.get(delivery.getCity()).get_id()));
//
//        int i = 0;
//
//        List<TransportPath> paths = new ArrayList<TransportPath>();
//        while (yenAlg.has_next()) {
//            Path p = yenAlg.next();
//            System.out.println("Path " + i++ + " : " + p);
//
//            BaseVertex lastVertex = null;
//            TransportPath tp = container.newTransientInstance(TransportPath.class);
////            TransportPath tp = new TransportPath();
//            for (BaseVertex v : p.get_vertices()) {
//                if (vertexCityHash.get(v) != null) {
//                    if (lastVertex == null){
//                        lastVertex = v;
//                        continue;
//                    }
////                    ShortLeg leg = new ShortLeg();
//                    ShortLeg leg = container.newTransientInstance(ShortLeg.class);
//                    leg.setFrom(vertexCityHash.get(lastVertex));
//                    leg.setTo(vertexCityHash.get(v));
//                    container.persist(leg);
//                    tp.addLeg(leg);
//                    lastVertex = v;
//                    System.out.print(vertexCityHash.get(v) + "-");
//                }
//            }
//            container.persist(tp);
//            paths.add(tp);
//            System.out.println();
//        }
//        return paths;
//    }

    public static Filter<TransportDemand> thoseOwnedBy(final String currentUser) {
        return new Filter<TransportDemand>() {
            @Override
            public boolean accept(final TransportDemand item) {
                return Objects.equal(item.getOwnedBy(), currentUser);
            }
        };
    }

//    public class TransportPath {
//
//        @TypicalLength(80)
//        public String title(){
//            String t = "";
//            for (ShortLeg l: legs){
//                t += "-" + l.getTo();
//            }
//            return t;
//        }
//        
//        private List<ShortLeg> legs = new ArrayList<ShortLeg>();
//
////        @Resolve(Resolve.Type.EAGERLY)
//        public List<ShortLeg> getLegs() {
//            return legs;
//        }
//
//        public void setLegs(ArrayList<ShortLeg> legs) {
//            this.legs = legs;
//        }
//    }
    
//    public class ShortLeg{
//        
//        private String from;
//
//        public String getFrom() {
//            return from;
//        }
//
//        public void setFrom(String from) {
//            this.from = from;
//        }
//        private String to;
//
//        public String getTo() {
//            return to;
//        }
//
//        public void setTo(String to) {
//            this.to = to;
//        }
//
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
    private TransportDemands transportDemands;

    public void setTransportDemands(TransportDemands transportDemands) {
        this.transportDemands = transportDemands;
    }
    
    private LocationService ls;
    void setLocationService(LocationService ls){
        this.ls = ls;
    }
    // }}
}
