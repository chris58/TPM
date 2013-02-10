/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marintek.service;

import com.marintek.tpm.dom.demand.ShortLeg;
import com.marintek.tpm.dom.demand.TransportDemand;
import com.marintek.tpm.dom.demand.TransportPath;
import com.marintek.tpm.dom.destination.Destination;
import com.marintek.tpm.dom.transport.Route;
import com.marintek.tpm.dom.transport.ServedRoute;
import edu.asu.emit.qyan.alg.control.YenTopKShortestPathsAlg;
import edu.asu.emit.qyan.alg.model.Graph;
import edu.asu.emit.qyan.alg.model.Path;
import edu.asu.emit.qyan.alg.model.abstracts.BaseVertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Hidden;

/**
 *
 * @author chris
 */
@Hidden
public class ShortestPathService extends AbstractService {

    @Hidden
    public List<TransportPath> getShortestPaths(TransportDemand td) {
        return getShortestPaths(td.getPickup(), td.getDelivery());
    }

    protected List<TransportPath> getShortestPaths(Destination pickup, Destination delivery) {
        List<Route> lr = getContainer().allInstances(Route.class);
        HashMap<String, BaseVertex> cityVertexHash = new HashMap<String, BaseVertex>();
        HashMap<BaseVertex, String> vertexCityHash = new HashMap<BaseVertex, String>();
        Graph g = new Graph();

        for (Route r : lr) {
            if (!cityVertexHash.containsKey(r.getFromcity())) {
                BaseVertex v = g.addVertex();
                cityVertexHash.put(r.getFromcity(), v);
            }
            if (!cityVertexHash.containsKey(r.getToCity())) {
                BaseVertex v = g.addVertex();
                cityVertexHash.put(r.getToCity(), v);
            }
        }
        List<ServedRoute> lsr = getContainer().allInstances(ServedRoute.class);
        for (ServedRoute sr : lsr) {
            BaseVertex startCity = cityVertexHash.get(sr.getFromcity());
            BaseVertex start = g.addVertex();
            vertexCityHash.put(start, sr.getFromcity() + "(" + sr.getTransportServiceProvider().getName() + ")");
            g.add_edge(startCity.get_id(), start.get_id(), 0.0);

            BaseVertex end = g.addVertex();
            vertexCityHash.put(end, sr.getToCity() + "(" + sr.getTransportServiceProvider().getName() + ")");
            BaseVertex endCity = cityVertexHash.get(sr.getToCity());
            g.add_edge(end.get_id(), endCity.get_id(), 0.0);

            g.add_edge(start.get_id(), end.get_id(), sr.getHoursToTravel());
        }

        YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(
                g, g.get_vertex(cityVertexHash.get(pickup.getCity()).get_id()),
                g.get_vertex(cityVertexHash.get(delivery.getCity()).get_id()));

        int i = 0;

        List<TransportPath> paths = new ArrayList<TransportPath>();
        while (yenAlg.has_next()) {
            Path p = yenAlg.next();
            System.out.println("Path " + i++ + " : " + p);

            BaseVertex lastVertex = null;
            TransportPath tp = getContainer().newTransientInstance(TransportPath.class);
//            TransportPath tp = new TransportPath();
            for (BaseVertex v : p.get_vertices()) {
                if (vertexCityHash.get(v) != null) {
                    if (lastVertex == null) {
                        lastVertex = v;
                        continue;
                    }
//                    ShortLeg leg = new ShortLeg();
                    ShortLeg leg = getContainer().newTransientInstance(ShortLeg.class);
                    leg.setFrom(vertexCityHash.get(lastVertex));
                    leg.setTo(vertexCityHash.get(v));
//                    getContainer().persist(leg);
                    tp.addLeg(leg);
                    lastVertex = v;
                    System.out.print(vertexCityHash.get(v) + "-");
                }
            }
//            getContainer().persist(tp);
            paths.add(tp);
            System.out.println();
        }
        return paths;
    }
}
