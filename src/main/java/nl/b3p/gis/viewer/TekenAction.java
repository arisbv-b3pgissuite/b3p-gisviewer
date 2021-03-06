package nl.b3p.gis.viewer;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nl.b3p.gis.utils.ConfigKeeper;
import nl.b3p.gis.utils.KaartSelectieUtil;
import nl.b3p.gis.viewer.db.Applicatie;
import nl.b3p.gis.viewer.services.HibernateUtil;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Session;

/**
 * B3partners B.V. http://www.b3partners.nl
 * @author Meine Toonen
 */
public class TekenAction extends ViewerCrudAction{
    
    @Override
    public ActionForward unspecified(ActionMapping mapping, DynaValidatorForm dynaForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
        /* Applicatie instellingen ophalen */
        HttpSession session = request.getSession(true);
        String appCode = (String) session.getAttribute("appCode");
        
        BaseGisAction.setCMSPageFromRequest(request);

        ConfigKeeper configKeeper = new ConfigKeeper();
        Map instellingen = configKeeper.getConfigMap(appCode, true);

        request.setAttribute("tekenTitel", instellingen.get("tekenTitel"));
        request.setAttribute("tekenTekstBoven", instellingen.get("tekenTekstBoven"));
        request.setAttribute("tekenTekstOnder", instellingen.get("tekenTekstOnder"));
        request.setAttribute("tekenPlaatje", instellingen.get("tekenPlaatje"));
        request.setAttribute("tekenGegevensbron", instellingen.get("tekenGegevensbron"));
        request.setAttribute("tekenFilterColumn", instellingen.get("tekenFilterColumn"));
        
        Session sess = HibernateUtil.getSessionFactory().getCurrentSession();
        
        return mapping.findForward(SUCCESS);
    }
}

