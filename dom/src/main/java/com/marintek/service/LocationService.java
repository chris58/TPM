/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marintek.service;

import com.danhaywood.isis.wicket.gmap3.applib.Location;
import java.io.StringReader;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Hidden;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author chris
 */
@Hidden
public class LocationService extends AbstractService{
    private static final long serialVersionUID = 1L;
    static final String BASEURL = "http://maps.googleapis.com/maps/api/geocode/";
    static final String MODE = "xml";
    static final HttpClient httpclient = new HttpClient();
    static final GetMethod get = new GetMethod(BASEURL + MODE);

    /**
     * Factory method
     *
     * @see #toString()
     */
    static public Location fromString(String encodedString) {
        final String[] split = encodedString.split(";");
        try {
            double latitude = Double.parseDouble(split[0]);
            double longitude = Double.parseDouble(split[1]);
            return new Location(latitude, longitude);
        } catch (Exception e) {
            return null;
        }
    }

    static public Location fromGoogleAddress(String address) {
        boolean sensor = false;
        String query = "?address=" + address + "&sensor=" + sensor;

        try {
            get.setQueryString(URIUtil.encodeQuery(query));
            httpclient.executeMethod(get);
            String xml = get.getResponseBodyAsString();
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new StringReader(xml));
            Element root = doc.getRootElement();
            String lat = root.getChild("result").getChild("geometry").getChild("location").getChildTextTrim("lat");
            String lon = root.getChild("result").getChild("geometry").getChild("location").getChildTextTrim("lng");
            return Location.fromString(lat + ";" + lon);
        } catch (Exception ex) {
            return null;
        }
    }
}
