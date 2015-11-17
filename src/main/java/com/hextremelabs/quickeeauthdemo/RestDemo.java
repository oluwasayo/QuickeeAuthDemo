/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hextremelabs.quickeeauthdemo;

import com.hextremelabs.quickee.response.BaseResponse;
import com.hextremelabs.quickee.security.Auth;
import com.hextremelabs.quickee.security.BasicLoginParam;
import static com.hextremelabs.quickee.security.ContextHelper.SESSION_ID;
import com.hextremelabs.quickee.security.Role;
import com.hextremelabs.quickee.security.Stakeholders;
import com.hextremelabs.quickee.session.LeanSessionManager;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author oladeji
 */
@Path("demo")
@Stateless
public class RestDemo {

  @Inject
  private LeanSessionManager sessionManager;

  @Resource
  private EJBContext ejbContext;

  @Context
  private HttpServletRequest request;

  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(BasicLoginParam param) {
    // TODO: Get user id and role from user account in DB.
    Long id = 123456l;
    final Role ROLE = Role.USER;
    String sessionId = sessionManager.generateNewSession(id, ROLE);
    Cookie cookie = new Cookie(SESSION_ID, sessionId);
    NewCookie newCookie = new NewCookie(cookie, "Session identifier", 60 * 60, true);
    UserAccount ua = new UserAccount(id, param.getUsername(), ROLE);
    // Session ID is available directly in header and as a cookie.
    return Response.ok(new BaseResponse<>(ua)).cookie(newCookie).header(SESSION_ID, sessionId).build();
  }

  @GET
  @Path("logout")
  @Produces(MediaType.APPLICATION_JSON)
  public Response logout() {
    try {
      sessionManager.invalidateSession(request.getHeader(SESSION_ID));
      return Response.ok(new BaseResponse()).header(SESSION_ID, "")
          .header("Set-Cookie", "token=deleted; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT").build();
    } catch (Exception ex) {
      return Response.ok(new BaseResponse(900, "System Error.")).build();
    }
  }

  @GET
  @Path("secure-request")
  @Auth // Verifies the cookie and ensures user is logged in, then sets up home-grown security context.
  @Stakeholders({Role.USER, Role.ADMIN}) // Ensures that only principals with USER or ADMIN role can invoke resource.
  @Produces("text/plain")
  public Response secureRequest() {
    return Response.ok("Authenticated successfully").build();
  }

  @GET
  @Path("secure-request")
  @Produces("text/plain")
  public Response unsecureRequest() {
    return Response.ok("Unsecured resource available to everyone.").build();
  }

  @XmlRootElement()
  public static class UserAccount {

    private final Long id;
    private final String username;
    private final Role role;

    public UserAccount(Long id, String username, Role role) {
      this.id = id;
      this.username = username;
      this.role = role;
    }
  }
}
