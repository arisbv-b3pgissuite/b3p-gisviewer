package nl.b3p.gis.viewer.db;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import nl.b3p.commons.services.FormUtils;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author Boy de Wit
 */
public class Applicatie {

    private Integer id;
    private String naam;
    private String code;
    private String gebruikersCode;
    private Applicatie parent;
    private Date datum_gebruikt;
    private Set children;

    public Applicatie() {
    }

    public Applicatie(String naam, String code) {
        this.naam = naam;
        this.code = code;
    }

    public Applicatie(String naam, String code, Applicatie parent) {
        this.naam = naam;
        this.code = code;
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDatum_gebruikt() {
        return datum_gebruikt;
    }

    public void setDatum_gebruikt(Date datum_gebruikt) {
        this.datum_gebruikt = datum_gebruikt;
    }

    public String getGebruikersCode() {
        return gebruikersCode;
    }

    public void setGebruikersCode(String gebruikersCode) {
        this.gebruikersCode = gebruikersCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Applicatie getParent() {
        return parent;
    }

    public void setParent(Applicatie parent) {
        this.parent = parent;
    }

    public Set getChildren() {
        return children;
    }

    public void setChildren(Set children) {
        this.children = children;
    }

    public static String createApplicatieCode()
            throws NoSuchAlgorithmException, UnsupportedEncodingException {

        Random rd = new Random();

        StringBuilder toBeHashedString = new StringBuilder();
        toBeHashedString.append(FormUtils.DateToFormString(new Date(), new Locale("NL")));
        toBeHashedString.append(rd.nextLong());

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(toBeHashedString.toString().getBytes("UTF-8"));

        byte[] md5hash = md.digest();
        return new String(Hex.encodeHex(md5hash));
    }
}