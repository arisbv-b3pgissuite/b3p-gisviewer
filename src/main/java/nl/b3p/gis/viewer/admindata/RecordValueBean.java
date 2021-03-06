package nl.b3p.gis.viewer.admindata;

import java.util.List;

/**
 *
 * @author Boy de Wit
 */
public class RecordValueBean {
    
    public static final String TYPE_DATA = "TYPE_DATA";
    public static final String TYPE_URL = "TYPE_URL";
    public static final String TYPE_QUERY = "TYPE_QUERY";
    public static final String TYPE_FUNCTION = "TYPE_FUNCTION";
    public static final String TYPE_GOOGLENAV = "TYPE_GOOGLENAV";
    public static final String TYPE_DATUM = "TYPE_DATUM";
    
    public static final String JAVASCRIPT_COMMAND_SURFACE_AREA = "surfaceArea";

    private String type;
    private String value;
    private String eenheid;

    private int kolomBreedte;

    private List valueList;

    public String getEenheid() {
        return eenheid;
    }

    public void setEenheid(String eenheid) {
        this.eenheid = eenheid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List getValueList() {
        return valueList;
    }

    public void setValueList(List valueList) {
        this.valueList = valueList;
    }

    @Override
    public String toString() {
        return "";
    }

    public static String getStringType(int id) {

        if (id == 1)
            return RecordValueBean.TYPE_DATA;

        if (id == 2)
            return RecordValueBean.TYPE_URL;

        if (id == 3)
            return RecordValueBean.TYPE_QUERY;

        if (id == 4)
            return RecordValueBean.TYPE_FUNCTION;
        
        if (id == 5)
            return RecordValueBean.TYPE_GOOGLENAV;
        
        if (id == 6)
            return RecordValueBean.TYPE_DATUM;

        return null;
    }

    public int getKolomBreedte() {
        return kolomBreedte;
    }

    public void setKolomBreedte(int kolomBreedte) {
        this.kolomBreedte = kolomBreedte;
    }
}
