package jsf;

import java.io.File;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import mailsearch.Campaign;
import mailsearch.service.CampaignFacadeREST;

/**
 *
 * @author Beny
 */
@ManagedBean(name="prepareMail")
@RequestScoped
public class PrepareMail {
    public Campaign campaign;
    public File mailFile;
    public Integer campaignId;

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public File getMailFile() {
        return mailFile;
    }

    public void setMailFile(File mailFile) {
        this.mailFile = mailFile;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    @EJB
    private CampaignFacadeREST campaignFacadeREST;

    public PrepareMail() {
    }

    public void loadCampaign() {
        campaign = campaignFacadeREST.find(campaignId);
    }

    public String send() {
        System.out.println(mailFile.getName());
        return "";
    }



}
