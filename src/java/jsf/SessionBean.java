package jsf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import mailsearch.User;
import mailsearch.service.UserFacadeREST;

/**
 *
 * @author Jonathan
 */
@ManagedBean(name="sessionBean")
@SessionScoped
public class SessionBean {
    private String identifiant;
    private String password;
    public boolean registrationDone;
    private User currentUser;

    public SessionBean() {
        this.currentUser = null;
    }

    public boolean isRegistrationDone() {
        return registrationDone;
    }

    public void setRegistrationDone(boolean registrationDone) {
        this.registrationDone = registrationDone;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @EJB
    private UserFacadeREST userFacadeREST;

    public User getCurrentUser() {
        return currentUser;
    }

    public String logout() {
        currentUser = null;
        return "index";
    }

    public boolean comparePassword(User u) throws NoSuchAlgorithmException, UnsupportedEncodingException{

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes("UTF-8"));
        String hashedPassword = (new HexBinaryAdapter()).marshal(hash);
        hashedPassword = hashedPassword.toLowerCase();

        return u.getPassword().equals(hashedPassword);

    }

    public void testConnection() throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException{
        User u = null;

        try{
            u = userFacadeREST.getEM().createNamedQuery("User.findByMail",User.class).setParameter("mail", identifiant).getSingleResult();
        }
        catch(NoResultException e){

        }
        if(u != null){
            boolean equality = comparePassword(u);
            if(equality){
                currentUser = u;
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/faces/userInterface.xhtml");
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur !", "Votre mot de passe est incorrect"));
            }
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur !", "Cet utilisateur n'existe pas"));
        }
    }

    public void setTitleRegistration(){
        if (registrationDone)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inscription réussie !", "Votre inscription s'est déroulée avec succès !"));
    }

}
