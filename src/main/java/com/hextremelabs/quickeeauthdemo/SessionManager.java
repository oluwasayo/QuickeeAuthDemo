/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hextremelabs.quickeeauthdemo;

import com.hextremelabs.quickee.configuration.StaticConfig;
import com.hextremelabs.quickee.session.AbstractSessionManager;
import javax.ejb.Singleton;

/**
 *
 * @author oladeji
 */
@Singleton
public class SessionManager extends AbstractSessionManager {

  @StaticConfig(key = "author.name")
  private String name;
}
