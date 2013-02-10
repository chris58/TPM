/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package com.marintek.tpm.fixture;

import com.marintek.tpm.dom.cargo.CargoItem;
import com.marintek.tpm.dom.cargo.CargoItems;
import com.marintek.tpm.dom.demand.TransportDemands;
import com.marintek.tpm.dom.destination.Destination;
import com.marintek.tpm.dom.destination.Destinations;
import com.marintek.tpm.dom.transport.Route;
import com.marintek.tpm.dom.transport.Routes;
import com.marintek.tpm.dom.transport.TransportServiceProviders;
import java.util.Collections;
import java.util.List;
import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.filter.Filter;

/**
 * Enables fixtures to be installed from the application.
 */
@Named("System")
public class TpmFixturesService extends AbstractService {

    @MemberOrder(sequence = "1")
//    @Named("Init/Reset")
    public String install() {
        final TpmFixture fixture = new TpmFixture();
        fixture.setContainer(getContainer());
        fixture.setCargoItems(cargoItems);
        fixture.setDestinations(destinations);
        fixture.setTransportServiceProviders(tsps);
        fixture.setRoutes(routes);
        fixture.setTransportDemands(transportDemands);
        fixture.install();
        return "TPM fixtures installed";
    }

    public boolean hideInstall(){
       return (allDestinations().size() > 0);
    }

    
    // Destination stuff

        @MemberOrder(sequence = "5")
    public Destination newDestination(
            @Named("Name") String name, 
            @Named("Street") String street, 
            @Named("City") String city,
            @Named("Postal Code") String postalCode,
            @Named("Country") String country
            )
            {
        return destinations.newDestination(name, street, city, postalCode, country);
    }
   
    
    @MemberOrder(sequence = "2")
    public List<Destination> allDestinations() {
        final List<Destination>  l = destinations.allDestinations();
        Collections.sort(l);
        return l;
    }    
    
    // Routes stuff
//    @MemberOrder(sequence = "6")
//    public Route newRoute(
//            @Named("From City") String fromCity, 
//            @Named("To City") String toCity
//            )
//            {
//        return routes.newRoute(fromCity, toCity);
//    }

    @MemberOrder(sequence = "3")
    public List<Route> allRoutes() {
        return routes.allRoutes();
    }

    @MemberOrder(sequence = "4")
    public List<CargoItem> allCargoItems() {
        return cargoItems.allCargoItems();
    }

    
    
    
    private CargoItems cargoItems;

    public void setCargoItems(final CargoItems cargoItems) {
        this.cargoItems = cargoItems;
    }
    private Destinations destinations;

    public void setDestinations(final Destinations d) {
        destinations = d;
    }
    private TransportServiceProviders tsps;

    public void setTsps(TransportServiceProviders tsps) {
        this.tsps = tsps;
    }
    private Routes routes;

    public void setRoutes(Routes routes) {
        this.routes = routes;
    }
    private TransportDemands transportDemands;

    public void setTransportDemands(TransportDemands td) {
        this.transportDemands = td;
    }
    
}
