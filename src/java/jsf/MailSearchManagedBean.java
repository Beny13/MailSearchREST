/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import mailsearch.Campaign;
import mailsearch.User;
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

    public MailSearchManagedBean() {
    }

    public String test() {
        return userFacadeREST.find(1).getMail();
    }

    public List<Campaign> getCampaingsByUser() {
        return campaignFacadeREST.findAll();
    }

}
