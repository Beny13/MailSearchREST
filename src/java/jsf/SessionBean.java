/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mailsearch.User;

/**
 *
 * @author Jonathan
 */
@ManagedBean (name="sessionBean")
@SessionScoped
public class SessionBean {

    private User currentUser;
    /**
     * Creates a new instance of SessionBean
     */
    public SessionBean() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    
    
    
}
