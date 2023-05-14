package org.tinder.servlets;

import org.tinder.services.Services;

import javax.servlet.http.HttpServlet;

abstract public class ServicesServlet extends HttpServlet {
    public final Services services;

    public ServicesServlet(Services services) {
        this.services = services;
    }

}
