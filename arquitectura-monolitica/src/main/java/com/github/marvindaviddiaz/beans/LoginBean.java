package com.github.marvindaviddiaz.beans;

import com.github.marvindaviddiaz.bo.Usuario;
import com.github.marvindaviddiaz.dto.UsuarioDTO;
import com.github.marvindaviddiaz.service.AuthService;
import com.github.marvindaviddiaz.service.ServicioService;
import com.github.marvindaviddiaz.util.SessionUtils;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@SessionScoped
@Named("loginBean")
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    @Inject
    private transient AuthService authService;

    private String pwd;
    private String msg;
    private String user;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    //validate login
    public String validateUsernamePassword() {
        try {
            UsuarioDTO usuario = this.authService.login(user, pwd);
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", user);
            session.setAttribute("name", usuario.getNombre());
            return "index";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            return "login";
        }
    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}
