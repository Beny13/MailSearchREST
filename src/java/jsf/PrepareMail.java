package jsf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;
import mailsearch.Campaign;
import mailsearch.service.CampaignFacadeREST;

/**
 *
 * @author Beny
 */
@ManagedBean(name="prepareMail")
@RequestScoped
public class PrepareMail {
    private Campaign campaign;
    private Integer campaignId;

    private Part mailFile;
    private String mailObject;
    private String mailContent;

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Part getMailFile() {
        return mailFile;
    }

    public void setMailFile(Part mailFile) {
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
        campaign = campaignFacadeREST.find(campaignId);
    }

    public void send() {
        loadCampaign();
        campaign.setMailObject(mailObject);
        campaign.setMailContent(mailContent);
        campaign.setMailFileName(mailFile.getSubmittedFileName());
        campaign.setMailFileContent(toByteArray(mailFile));
        campaign.setStatus(Campaign.MAILING_PENDING);
        campaignFacadeREST.edit(campaign);
    }

    public byte[] toByteArray(Part file) {
        try {
            InputStream is = file.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int next = is.read();
            while (next > -1) {
                bos.write(next);
                next = is.read();
            }
            bos.flush();
            return bos.toByteArray();
        } catch (IOException ex) {
            return null;
        }
    }
}
