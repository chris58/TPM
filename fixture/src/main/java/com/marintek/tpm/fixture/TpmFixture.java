/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marintek.tpm.fixture;

/**
 *
 * @author chris
 */
import com.marintek.tpm.dom.cargo.CargoItem;
import com.marintek.tpm.dom.cargo.CargoItems;
import com.marintek.tpm.dom.demand.TransportDemand;
import com.marintek.tpm.dom.demand.TransportDemands;
import com.marintek.tpm.dom.destination.Destination;
import com.marintek.tpm.dom.destination.Destinations;
import com.marintek.tpm.dom.transport.Route;
import com.marintek.tpm.dom.transport.Routes;
import com.marintek.tpm.dom.transport.TransportServiceProvider;
import com.marintek.tpm.dom.transport.TransportServiceProviders;
import java.util.ArrayList;
import java.util.List;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.fixtures.AbstractFixture;

public class TpmFixture extends AbstractFixture {

//    List<Route> routeList = new ArrayList<Route>();
//    ArrayList<CargoItem> cargoList = new ArrayList<CargoItem>();
//    ArrayList<TransportServiceProvider> tspList = new ArrayList<TransportServiceProvider>();
//    ArrayList<TransportDemand> tdList = new ArrayList<TransportDemand>();
//    ArrayList<Destination> destinationList = new ArrayList<Destination>();

    @Override
    public void install() {
//        List<Route> r;
//        while (!(r = routes.allRoutes()).isEmpty()) {
//            r.remove(0);
//        }
//        List<CargoItem> c;
//        while (!(c = cargoItems.allCargoItems()).isEmpty()) {
//            c.remove(0);
//        }
//        List<Destination> d;
//        while (!(d = destinations.allDestinations()).isEmpty()) {
//            d.remove(0);
//        }
//        List<TransportDemand> td;
//        while (!(td = transportDemands.allTransportDemands()).isEmpty()) {
//            td.remove(0);
//        }
//        List<TransportServiceProvider> tspl;
//        while (!(tspl = tsps.allTransportServiceProviders()).isEmpty()) {
//            tspl.remove(0);
//        }
//
        

        CargoItem c0 = createCargo("Some fish", CargoItem.Category.Bulk, CargoItem.HazCode.Frozen, 55, CargoItem.Unit.Pallets);
        CargoItem c1 = createCargo("Some more fish", CargoItem.Category.Bulk, CargoItem.HazCode.Frozen, 10, CargoItem.Unit.Pallets);
        createCargo("Some bricks", CargoItem.Category.Bulk, CargoItem.HazCode.Hazardous, 5, CargoItem.Unit.Pallets);

        createRoute("Fiskarstrand", "Ålesund");
        createRoute("Ålesund", "Rotterdam");
        createRoute("Ålesund", "Hamburg");
        createRoute("Ålesund", "Oslo");
        createRoute("Ålesund", "Trondheim");
        createRoute("Trondheim", "Hamburg");
        createRoute("Oslo", "Rotterdam");
        createRoute("Oslo", "Kiel");
        createRoute("Oslo", "Trondheim");
        createRoute("Kiel", "Hamburg");
        createRoute("Hamburg", "Rotterdam");
        createRoute("Rotterdam", "Arnhem");

        createDestination("Fishing Company", "Havgata 5", "Fiskarstrand", "N-1234", "Norway");
        createDestination("Fish eating Customer", "xyz 55", "Rotterdam", "NL-1234", "The Netherlands");
        Destination d0 = createDestination("Happy Salmon Company", "Fjordgata 66", "Fiskarstrand", "N-1234", "Norway");
        Destination d1 = createDestination("Fish&Chips", "Rottestret 99", "Rotterdam", "NL-1234", "The Netherlands");
        createDestination("Brick Producer", "Fjordgata 55", "Trondheim", "N-7000", "Norway");
        createDestination("Brick Customer", "Carl Johann 007", "Oslo", "N-2000", "Norway");

        TransportDemand trd = transportDemands.newTransportDemand(d0, d1);
        trd.setCargo(c0);
//        trd = transportDemands.newTransportDemand(d0, d1);
//        trd.setCargo(c1);


        TransportServiceProvider tsp = createTSP("Transferd", "Førde", TransportServiceProvider.Category.Car);
        tsp.addRoute(routes.findRoute("Fiskarstrand", "Ålesund"), 2.0, 3.0);

        tsp = createTSP("Hoel Transport", "Ålesund", TransportServiceProvider.Category.Car);
        tsp.addRoute(routes.findRoute("Fiskarstrand", "Ålesund"), 3.0, 2.0);

        tsp = createTSP("Fish Cargo", "Trondheim", TransportServiceProvider.Category.Car);
        tsp.addRoute(routes.findRoute("Fiskarstrand", "Ålesund"), 4.0, 1.0);
        tsp.addRoute(routes.findRoute("Rotterdam", "Arnhem"), 3.0, 2.0);

        tsp = createTSP("Maersk", "Oslo", TransportServiceProvider.Category.Ship);
        tsp.addRoute(routes.findRoute("Oslo", "Rotterdam"), 48.0, 1.0);

        tsp = createTSP("NCL", "Trondheim", TransportServiceProvider.Category.Ship);
        tsp.addRoute(routes.findRoute("Ålesund", "Rotterdam"), 40.0, 1.0);
        tsp.addRoute(routes.findRoute("Ålesund", "Hamburg"), 35.0, 1.0);

        tsp = createTSP("ECL", "Timbuktu", TransportServiceProvider.Category.Ship);
        tsp.addRoute(routes.findRoute("Ålesund", "Rotterdam"), 35.0, 1.5);
        tsp.addRoute(routes.findRoute("Oslo", "Rotterdam"), 45.0, 1.0);


        tsp = createTSP("NSB", "Oslo", TransportServiceProvider.Category.Train);
        tsp.addRoute(routes.findRoute("Ålesund", "Oslo"), 0.0, 0.0);
        tsp.addRoute(routes.findRoute("Oslo", "Trondheim"), 0.0, 0.0);
        tsp.addRoute(routes.findRoute("Oslo", "Kiel"), 0.0, 0.0);

        tsp = createTSP("DB", "Berlin", TransportServiceProvider.Category.Train);
        tsp.addRoute(routes.findRoute("Kiel", "Hamburg"), 0.0, 0.0);
        tsp.addRoute(routes.findRoute("Hamburg", "Rotterdam"), 0.0, 0.0);
        tsp.addRoute(routes.findRoute("Rotterdam", "Arnhem"), 0.0, 0.0);


        //createTSP("NSB", "Oslo", TransportServiceProvider.Category.Car);

        getContainer().flush();
    }

    protected CargoItem createCargo(String description,
            CargoItem.Category category,
            CargoItem.HazCode hazcode,
            double amount,
            CargoItem.Unit unit) {
        return cargoItems.newCargo(description, category, hazcode, amount, unit);
    }

    protected Destination createDestination(String name,
            String street,
            String city,
            String postalCode,
            String country) {
        return destinations.newDestination(name, street, city, postalCode, country);
    }

    protected TransportServiceProvider createTSP(String name,
            String address,
            TransportServiceProvider.Category cat) {
        return tsps.newTransportServiceProvider(name, cat, address);
    }

    protected Route createRoute(String fromCity,
            String toCity) {
        return routes.newRoute(fromCity, toCity);
    }
    private CargoItems cargoItems;

    public void setCargoItems(final CargoItems ci) {
        cargoItems = ci;
    }
    private Destinations destinations;

    public void setDestinations(final Destinations d) {
        destinations = d;
    }
    private TransportServiceProviders tsps;

    public void setTransportServiceProviders(TransportServiceProviders tsps) {
        this.tsps = tsps;
    }
    private TransportDemands transportDemands;

    public void setTransportDemands(TransportDemands td) {
        this.transportDemands = td;
    }
    private Routes routes;

    public void setRoutes(Routes routes) {
        this.routes = routes;
    }
}
