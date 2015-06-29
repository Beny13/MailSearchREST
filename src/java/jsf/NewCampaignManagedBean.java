/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import mailsearch.Campaign;
import mailsearch.User;
import mailsearch.service.CampaignFacadeREST;
import mailsearch.service.UserFacadeREST;

@ManagedBean
@RequestScoped
public class NewCampaignManagedBean {
    @EJB
    private UserFacadeREST userFacadeREST;
    @EJB
    private CampaignFacadeREST campaignFacadeREST;
    
    private Campaign campaign;
    private String keyword;

    public NewCampaignManagedBean() {
        campaign = new Campaign();
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public String create() {
        User usr = userFacadeREST.find(1);
        
        campaign.setKeyword(keyword);
        campaign.setStatus(Campaign.SCRAPPING_PENDING);
        campaign.setUserId(usr);
        campaignFacadeREST.create(campaign);
        return "index";
    }
    
       
    
}
