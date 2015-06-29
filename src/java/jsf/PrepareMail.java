package jsf;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import mailsearch.Campaign;
import mailsearch.service.CampaignFacadeREST;
import org.primefaces.model.NativeUploadedFile;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Beny
 */
@ManagedBean(name="prepareMail")
@RequestScoped
public class PrepareMail {
    private Campaign campaign;
    private Integer campaignId;
    
    private UploadedFile mailFile;
    private String mailObject;
    private String mailContent;

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public UploadedFile getMailFile() {
        return mailFile;
    }

    public void setMailFile(UploadedFile mailFile) {
        this.mailFile = mailFile;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public String getMailObject() {
        return mailObject;
    }

    public void setMailObject(String mailObject) {
        this.mailObject = mailObject;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    @EJB
    private CampaignFacadeREST campaignFacadeREST;

    public PrepareMail() {
    }

    public void loadCampaign() {
        System.out.println(campaignId);
        campaign = campaignFacadeREST.find(campaignId);
        System.out.println(campaign.getMailContent());
    }

    public String send() {
        loadCampaign();
        campaign.setMailObject(mailObject);
        campaign.setMailContent(mailContent);
        campaign.setMailFileName(mailFile.getFileName());
        campaign.setMailFileContent(mailFile.getContents());
        campaignFacadeREST.edit(campaign);
        return "index.xhtml";
    }



}
