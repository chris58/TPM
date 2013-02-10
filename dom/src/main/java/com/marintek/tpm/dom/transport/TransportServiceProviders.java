/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marintek.tpm.dom.transport;

import java.util.Collections;
import java.util.List;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

/**
 *
 * @author chris
 */
public class TransportServiceProviders extends AbstractFactoryAndRepository{
    
    // {{ newToDo  (action)
    @MemberOrder(sequence = "1")
    public TransportServiceProvider newTransportServiceProvider(
            @Named("Name") String name, 
            @Named("Category") TransportServiceProvider.Category category,
            @Named("Address") String address
            )
            {
        final TransportServiceProvider tsp = newTransientInstance(TransportServiceProvider.class);
        tsp.setName(name);
        tsp.setAddress(address);
        tsp.setCategory(category);
        persist(tsp);
        return tsp;
    }
    // }}

    
//    public List<Leg> findLegs(String from, String to){
//        List<Leg> l = new ArrayList<Leg>();
//        return l;
//    }
//
//    //
//    
//    
//    
//    protected List<Route> nextLegs(String from) {
//        return allMatches(Route.class, new Filter<Route>() {
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
    
    
    public List<TransportServiceProvider> allTransportServiceProviders(){
        final List<TransportServiceProvider> items = allInstances(TransportServiceProvider.class);
        Collections.sort(items);
        return items;
    }
           
}
