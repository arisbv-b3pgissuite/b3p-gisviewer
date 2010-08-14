package nl.b3p.gis.utils;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.geom.util.PolygonExtracter;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import com.vividsolutions.jts.operation.buffer.BufferOp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import nl.b3p.gis.geotools.DataStoreUtil;
import nl.b3p.gis.viewer.db.Themas;
import nl.b3p.gis.viewer.services.HibernateUtil;
import nl.b3p.gis.viewer.services.SpatialUtil;
import nl.b3p.zoeker.configuratie.Bron;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.opengis.feature.Feature;

/**
 *
 * @author Boy de Wit
 */
public class EditUtil {

    private static final Log log = LogFactory.getLog(EditUtil.class);
    private static final int QUAD_SEGS = 16;

    /**
     * Constructor
     **/
    public EditUtil() throws Exception {
    }

    public String buffer(String wkt, double bufferafstand) throws Exception {

        if ( (wkt == null) || (wkt.length() < 1) ) {
            throw new Exception("Kan niet bufferen. Er is nog geen handeling geselecteerd.");
        }

        if (bufferafstand == 0) {
            throw new Exception("De bufferafstand mag niet 0 zijn.");
        }

        String buffer = "";
        Geometry geom = createGeomFromWKTString(wkt);

        if (geom == null) {
            throw new Exception("Kan niet bufferen. Geometrie is incorrect.");
        }
 
        Geometry result = geom.buffer(bufferafstand, QUAD_SEGS, BufferOp.CAP_BUTT);

        if (result == null)
            throw new Exception("Resultaat buffer geeft incorrecte geometrie.");
   
        Geometry poly = getLargestPolygonFromMultiPolygon(result);

        if (poly == null)
            throw new Exception("Bufferfout bij omzetten MultiPolygon naar grootste Polygon.");

        buffer = poly.toText();
        
        return buffer;
    }

    public String getHighlightWktForThema(String themaIds, String wktPoint) {
        
        Session sess = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = sess.beginTransaction();

        try {
            Geometry geom = createGeomFromWKTString(wktPoint);

            if (geom == null)
                throw new Exception("Kan niet highlighten. Geometrie is incorrect.");

            Themas thema = SpatialUtil.getThema(themaIds);

            if (thema == null)
                throw new Exception("Kan niet highlighten. Layer niet gevonden.");

            List thema_items = SpatialUtil.getThemaData(thema, true);

            if (thema_items.size() < 1)
                throw new Exception("Kan niet highlighten. Geen themadata gevonden.");

            WebContext ctx = WebContextFactory.get();
            HttpServletRequest request = ctx.getHttpServletRequest();
            Bron b = (Bron) thema.getConnectie(request);
            if (b == null) {
               throw new Exception("Kan niet highlighten. Geen connectie gevonden.");
            }
            ArrayList<Feature> features = DataStoreUtil.getFeatures(b, thema, geom, null, DataStoreUtil.themaData2PropertyNames(thema_items), null);

            if ( (features == null) || (features.size() < 1) )
                throw new Exception("Kan niet highlighten. Geen features gevonden.");

            if (features.size() > 1)
                throw new Exception("Kan niet highlighten. Meerdere features gevonden.");

            
            Feature f = features.get(0);

            if (f == null || f.getDefaultGeometryProperty() == null) {
                return null;
            }

            Geometry object = (Geometry) f.getDefaultGeometryProperty().getValue();
            if (object != null && object.isSimple() && object.isValid()) {
                WKTWriter wktw = new WKTWriter();

                return wktw.write(object);
            }

        } catch (Exception ex) {

            if (tx.isActive())
                tx.rollback();

            log.debug(ex);
        }
        
        return wktPoint;
    }

    private Geometry createGeomFromWKTString(String wktstring) throws Exception {
        WKTReader wktreader = new WKTReader(new GeometryFactory(new PrecisionModel(), 28992));
        try {
            return wktreader.read(wktstring);
        } catch (ParseException ex) {
            throw new Exception(ex);
        }

    }

    private Geometry getLargestPolygonFromMultiPolygon(Geometry multipolygon) {

        if (multipolygon == null) {
            return null;
        }

        if (multipolygon.isEmpty()) {
            return null;
        }

        Polygon single = null;

        /* convert multipolygon to single polygons */
        List list = new ArrayList();
        list = PolygonExtracter.getPolygons(multipolygon);
        Iterator iter = list.iterator();

        double opp = 0;

        while (iter.hasNext()) {
            Polygon po = (Polygon) iter.next();
            double temp = po.getArea();

            if (temp > opp) {
                single = po;
                opp = temp;
            }
        }

        LineString line = single.getExteriorRing();

        if (line instanceof LinearRing)
            return new Polygon((LinearRing)line, null, new GeometryFactory(new PrecisionModel(), 28992));

        return null;
    }

    private int getNumInteriorRings(Geometry geom)
    {
        List list = new ArrayList();
        list = PolygonExtracter.getPolygons(geom);
        Iterator iter = list.iterator();

        Polygon single = null;

        double opp = 0;

        while (iter.hasNext()) {
            Polygon po = (Polygon) iter.next();
            double temp = po.getArea();

            if (temp > opp) {
                single = po;
                opp = temp;
            }
        }

        return single.getNumInteriorRing();
    }
}
