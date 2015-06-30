/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.NoResultException;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import mailsearch.User;
import mailsearch.service.UserFacadeREST;

/**
 *
 * @author Jonathan
 */
@ManagedBean
@RequestScoped
public class RegistrationBean {

    private String mail;
    private String confMail;
    private String password;
    private String confPassword;
    @EJB
    private UserFacadeREST userFacadeREST;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getConfMail() {
        return confMail;
    }

    public void setConfMail(String confMail) {
        this.confMail = confMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfPassword() {
        return confPassword;
    }

    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }

    public UserFacadeREST getUserFacadeREST() {
        return userFacadeREST;
    }

    public void setUserFacadeREST(UserFacadeREST userFacadeREST) {
        this.userFacadeREST = userFacadeREST;
    }

    /**
     * Creates a new instance of inscriptionBean
     */
    public RegistrationBean() {
    }

    public boolean compareString(String chaine1, String chaine2){
        return chaine1.equals(chaine2);
    }

    public boolean mailCorrectlyStructured(){
        boolean result = true;
        try {
           InternetAddress emailAddr = new InternetAddress(this.mail);
           emailAddr.validate();
        } catch (AddressException ex) {
           result = false;
        }
         return result;
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes("UTF-8"));
        String hashedPassword = (new HexBinaryAdapter()).marshal(hash);
        hashedPassword = hashedPassword.toLowerCase();

        return hashedPassword;
    }

    public void Inscription() throws IOException, NoSuchAlgorithmException{
        if(mailCorrectlyStructured()){
            if(compareString(mail, confMail)){
                User userTest = null;
                try{
                    userTest = userFacadeREST.getEM().createNamedQuery("User.findByMail",User.class).setParameter("mail", mail).getSingleResult();
                }
                catch(NoResultException e){}
                if(userTest == null){
                    if(compareString(password, confPassword)){


                        User u = new User();
                        u.setMail(mail);
                        u.setPassword(this.hashPassword(password));
                        userFacadeREST.create(u);
                        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                        ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml?inscription=true");
                    }
                    else{
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur !", "Les deux mots de passes sont différents !"));
                    }
                }
                else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur !", "Un utilisateur utilisant cette adresse mail existe déjà !"));
                }
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur !", "Les adresses mails rentrées ne sont pas identiques !"));
            }

        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur !", "Votre adresse mail n'est pas correctement structurée !"));
        }


    }
}