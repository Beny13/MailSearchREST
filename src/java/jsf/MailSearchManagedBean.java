/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import mailsearch.Campaign;
import mailsearch.Email;
import mailsearch.service.CampaignFacadeREST;
import mailsearch.service.EmailFacadeREST;
import mailsearch.service.UserFacadeREST;

/**
 *
 * @author ben
 */
@ManagedBean
@RequestScoped
public class MailSearchManagedBean {
    @EJB
    private EmailFacadeREST emailFacadeREST;

    @EJB
    private UserFacadeREST userFacadeREST;

    @EJB
    private CampaignFacadeREST campaignFacadeREST;
    
    @ManagedProperty(value="#{sessionBean}")
    private SessionBean sessionBean;

    public MailSearchManagedBean() {
    }

    public List<Campaign> getCampaignsByUser() {
        return campaignFacadeREST.findAll();
    }

    public List<Email> getEmailsByCampaign(Campaign campaign) {
        return (List<Email>) campaign.getEmailCollection();
    }

    public String goToSendMail(Integer campaignId) {
        return "emailEdit?campaignId=" + campaignId;
    }

}
