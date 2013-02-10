/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marintek.tpm.dom.transport;

import com.marintek.tpm.dom.demand.TransportDemand;
import java.util.List;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.filter.Filter;

/**
 *
 * @author chris
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
public class Leg {

    private TransportServiceProvider transportServiceProvider;
    private TransportDemand transportDemand;

    public String title(){
        return route.title();
    }

    public String iconName(){
        return (transportServiceProvider != null)? "status_ok" : "status_alert";
    }
    
    
    public boolean getOk(){
        return transportServiceProvider != null;
    }
    
    @Hidden(where=Where.ALL_TABLES)
    public TransportDemand getTransportDemand() {
        return transportDemand;
    }

    public void setTransportDemand(TransportDemand transportDemand) {
        this.transportDemand = transportDemand;
    }

    @Optional
    public TransportServiceProvider getTransportServiceProvider() {
        return transportServiceProvider;
    }

    public TransportDemand setTransportServiceProvider(TransportServiceProvider transportServiceProvider) {
        this.transportServiceProvider = transportServiceProvider;
        return this.transportDemand;
    }
    private Route route;

    @Hidden
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Hidden(where=Where.ALL_TABLES)
    public String getFromcity() {
        return route.getFromcity();
    }

    @Hidden(where=Where.ALL_TABLES)
    public String getToCity() {
        return route.getToCity();
    }

    @Named("Select Service provider")
    public Leg configServiceProvider(
            @Named("Provider") TransportServiceProvider provider) {

        this.transportServiceProvider = provider;
        return this;
    }

    public List<TransportServiceProvider> choices0ConfigServiceProvider() {
        return container.allMatches(TransportServiceProvider.class, new Filter<TransportServiceProvider>() {
            @Override
            public boolean accept(TransportServiceProvider t) {
                for (ServedRoute r : t.getServedRoutes()) {
                    if (r.getFromcity().equals(route.getFromcity()) && r.getToCity().equals(route.getToCity())) {
                        return true;
                    }
                }
                return false;
            }
        });

    }
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    public void setDomainObjectContainer(final DomainObjectContainer container) {
        this.container = container;
    }
}
