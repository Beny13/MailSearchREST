/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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

    public MailSearchManagedBean() {
    }
    
    public String test() {
        return userFacadeREST.find(1).getMail();
    }
    
}
