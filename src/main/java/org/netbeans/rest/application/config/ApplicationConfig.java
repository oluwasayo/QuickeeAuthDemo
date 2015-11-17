/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author oladeji
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new java.util.HashSet<>();
    addRestResourceClasses(resources);
    return resources;
  }

  /**
   * Do not modify addRestResourceClasses() method.
   * It is automatically populated with
   * all resources defined in the project.
   * If required, comment out calling this method in getClasses().
   */
  private void addRestResourceClasses(Set<Class<?>> resources) {
    resources.add(com.hextremelabs.quickee.rest.exceptionmapper.BeanValidationExceptionMapper.class);
    resources.add(com.hextremelabs.quickee.rest.exceptionmapper.DefaultExceptionMapper.class);
    resources.add(com.hextremelabs.quickee.rest.exceptionmapper.IOExceptionMapper.class);
    resources.add(com.hextremelabs.quickee.rest.exceptionmapper.PersistenceExceptionMapper.class);
    resources.add(com.hextremelabs.quickee.rest.exceptionmapper.QuikeeExceptionMapper.class);
    resources.add(com.hextremelabs.quickee.rest.exceptionmapper.SecurityExceptionMapper.class);
    resources.add(com.hextremelabs.quickee.rest.output.OctetStreamMessageBodyWriter.class);
    resources.add(com.hextremelabs.quickee.rest.output.csv.CsvMessageBodyWriter.class);
    resources.add(com.hextremelabs.quickeeauthdemo.RestDemo.class);
  }

}
