package nl.b3p.gis.viewer.db;

import com.vividsolutions.jts.geom.Geometry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;

/**
 *
 * @author Chris
 */
public class Meldingen {

    private Integer id;
    private String naamZender;
    private String adresZender;
    private String emailZender;
    private String meldingType;
    private String meldingTekst;
    private String meldingStatus;
    private String meldingCommentaar;
    private String naamOntvanger;
    private Date datumOntvangst;
    private Date datumAfhandeling;
    private Geometry theGeom;
    private String kenmerk;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the naamZender
     */
    public String getNaamZender() {
        return naamZender;
    }

    /**
     * @param naamZender the naamZender to set
     */
    public void setNaamZender(String naamZender) {
        this.naamZender = naamZender;
    }

    /**
     * @return the adresZender
     */
    public String getAdresZender() {
        return adresZender;
    }

    /**
     * @param adresZender the adresZender to set
     */
    public void setAdresZender(String adresZender) {
        this.adresZender = adresZender;
    }

    /**
     * @return the emailZender
     */
    public String getEmailZender() {
        return emailZender;
    }

    /**
     * @param emailZender the emailZender to set
     */
    public void setEmailZender(String emailZender) {
        this.emailZender = emailZender;
    }

    /**
     * @return the meldingType
     */
    public String getMeldingType() {
        return meldingType;
    }

    /**
     * @param meldingType the meldingType to set
     */
    public void setMeldingType(String meldingType) {
        this.meldingType = meldingType;
    }

    /**
     * @return the meldingTekst
     */
    public String getMeldingTekst() {
        return meldingTekst;
    }

    /**
     * @param meldingTekst the meldingTekst to set
     */
    public void setMeldingTekst(String meldingTekst) {
        this.meldingTekst = meldingTekst;
    }

    /**
     * @return the meldingStatus
     */
    public String getMeldingStatus() {
        return meldingStatus;
    }

    /**
     * @param meldingStatus the meldingStatus to set
     */
    public void setMeldingStatus(String meldingStatus) {
        this.meldingStatus = meldingStatus;
    }

    /**
     * @return the meldingCommentaar
     */
    public String getMeldingCommentaar() {
        return meldingCommentaar;
    }

    /**
     * @param meldingCommentaar the meldingCommentaar to set
     */
    public void setMeldingCommentaar(String meldingCommentaar) {
        this.meldingCommentaar = meldingCommentaar;
    }

    /**
     * @return the naamOntvanger
     */
    public String getNaamOntvanger() {
        return naamOntvanger;
    }

    /**
     * @param naamOntvanger the naamOntvanger to set
     */
    public void setNaamOntvanger(String naamOntvanger) {
        this.naamOntvanger = naamOntvanger;
    }

    /**
     * @return the the_geom
     */
    public Geometry getTheGeom() {
        return theGeom;
    }

    /**
     * @param theGeom the theGeom to set
     */
    public void setTheGeom(Geometry theGeom) {
        this.theGeom = theGeom;
    }

    /**
     * @return the kenmerk
     */
    public String getKenmerk() {
        return kenmerk;
    }

    /**
     * @param kenmerk the kenmerk to set
     */
    public void setKenmerk(String kenmerk) {
        this.kenmerk = kenmerk;
    }

    /**
     * @return the datumOntvangst
     */
    public Date getDatumOntvangst() {
        return datumOntvangst;
    }

    /**
     * @param datumOntvangst the datumOntvangst to set
     */
    public void setDatumOntvangst(Date datumOntvangst) {
        this.datumOntvangst = datumOntvangst;
    }

    /**
     * @return the datumAfhandeling
     */
    public Date getDatumAfhandeling() {
        return datumAfhandeling;
    }

    /**
     * @param datumAfhandeling the datumAfhandeling to set
     */
    public void setDatumAfhandeling(Date datumAfhandeling) {
        this.datumAfhandeling = datumAfhandeling;
    }

    public HashMap getAttributesMap() {
        HashMap hm = new HashMap();
        hm.put("naam_zender", naamZender);
        hm.put("adres_zender", adresZender);
        hm.put("email_zender", emailZender);
        hm.put("melding_type", meldingType);
        hm.put("melding_tekst", meldingTekst);
        hm.put("melding_status", meldingStatus);
        hm.put("melding_commentaar", meldingCommentaar);
        hm.put("naam_ontvanger", naamOntvanger);
        hm.put("datum_ontvangst", datumOntvangst);
        hm.put("datum_afhandeling", datumAfhandeling);
        hm.put("the_geom", theGeom);
        hm.put("kenmerk", kenmerk);
        return hm;
    }

    public SimpleFeature getFeature(SimpleFeatureType ft) {
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(ft);

        List<AttributeDescriptor> attributeDescriptors = new ArrayList<AttributeDescriptor>(ft.getAttributeDescriptors());
        for (AttributeDescriptor ad : attributeDescriptors) {
            String ln = ad.getLocalName().toLowerCase();
            Object lnv = this.getAttributesMap().get(ln);
            featureBuilder.add(lnv);
        }

        SimpleFeature f = featureBuilder.buildFeature( null );

        return f;
    }
}
