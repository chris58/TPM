/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marintek.tpm.dom.transport;

import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Where;

/**
 *
 * @author chris
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
public class ServedRoute {

    public String title(){
        return route.title();
    }

    public String iconName(){
        return (transportServiceProvider != null)? "status_ok" : "status_alert";
    }
    
    public boolean getOk(){
        return transportServiceProvider != null;
    }

    // Service Provider
    private TransportServiceProvider transportServiceProvider;

    public TransportServiceProvider getTransportServiceProvider() {
        return transportServiceProvider;
    }

    public void setTransportServiceProvider(TransportServiceProvider transportServiceProvider) {
        this.transportServiceProvider = transportServiceProvider;
    }

    // route
    private Route route;

    @Hidden
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    // hours to travel
    private double hoursToTravel;

    public double getHoursToTravel() {
        return hoursToTravel;
    }

    public void setHoursToTravel(double hoursToTravel) {
        this.hoursToTravel = hoursToTravel;
    }
    
    // amountPerKg
    private double amountPerKg;

    public double getAmountPerKg() {
        return amountPerKg;
    }

    public void setAmountPerKg(double amountPerKg) {
        this.amountPerKg = amountPerKg;
    }
    
    
    @Hidden(where=Where.ALL_TABLES)
    public String getFromcity() {
        return route.getFromcity();
    }

    @Hidden(where=Where.ALL_TABLES)
    public String getToCity() {
        return route.getToCity();
    }

    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    public void setDomainObjectContainer(final DomainObjectContainer container) {
        this.container = container;
    }
}
