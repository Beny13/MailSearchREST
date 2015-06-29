/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import static com.sun.faces.facelets.util.Path.context;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import mailsearch.User;
import mailsearch.service.UserFacadeREST;

/**
 *
 * @author Jonathan
 */
@ManagedBean
@RequestScoped
public class ConnexionBean {
    private String identifiant;
    private String password;
    
    @ManagedProperty(value="#{sessionBean}")
    private SessionBean sessionBean;
    
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
    
    public ConnexionBean() {
        
    }
    
    public boolean comparePassword(User u) throws NoSuchAlgorithmException, UnsupportedEncodingException{

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes("UTF-8"));
        String hashedPassword = new String(hash);
        
        return u.getPassword().equals(hashedPassword);

    }
    
    public void testConnection() throws NoSuchAlgorithmException, UnsupportedEncodingException{
        User u = null;
        try{
            u = userFacadeREST.getEM().createNamedQuery("User.findByMail",User.class).setParameter("mail", identifiant).getSingleResult();
        }
        catch(NoResultException e){
            
        }
        if(u != null){
            boolean equality = comparePassword(u);
            if(equality){
                //connexion
                System.out.println("YOLOOOOOOOOOOO");
                this.sessionBean.setCurrentUser(u);
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur !", "Votre mot de passe est incorrect"));
            }
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur !", "Cet utilisateur n'existe pas"));
        }
    }
    
}
