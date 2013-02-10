/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marintek.tpm.dom.transport;

import java.util.Collections;
import java.util.List;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.filter.Filter;

/**
 *
 * @author chris
 */
// Shown in TPM Fixture service menu
@Hidden
public class Routes extends AbstractFactoryAndRepository{

//    public String iconName(){
//        return "status_ok";
//    }
//    
    // {{ newRoute  (action)
    @MemberOrder(sequence = "1")
    public Route newRoute(
            @Named("From City") String fromCity, 
            @Named("To City") String toCity
            )
            {
        final Route route = newTransientInstance(Route.class);
        route.setFromcity(fromCity);
        route.setToCity(toCity);
        
        persist(route);
        return route;
    }
    // }}
    
//    @Bulk
//    public void deleteRoute(Route r){
//        System.out.println("delete " + r.getFromcity());
//        
//        List<TransportServiceProvider> tsp = tsps.allTransportServiceProviders();
//        for (TransportServiceProvider t: tsp){
//            if (t.getServedRoutes().contains(r))
//                t.remove(r);
//        }
//        remove(r);
//    }
    
    public List<Route> allRoutes(){
        final List<Route> items = allInstances(Route.class);
        Collections.sort(items);
        return items;
    }
    
    @Programmatic
    public Route findRoute(final String from, final String to) {
        return  firstMatch(Route.class, new Filter<Route>() {
            @Override
            public boolean accept(Route t) {
                String fromWhere;;
                return (t.getFromcity().equals(from) && t.getToCity().equals(to));
            }
        });
    }

    private TransportServiceProviders tsps;
    public void setTransportServiceProviders(TransportServiceProviders tsps){
        this.tsps = tsps;
    }
    
}
