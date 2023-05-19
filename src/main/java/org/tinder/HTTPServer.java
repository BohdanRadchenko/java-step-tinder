package org.tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.tinder.enums.ServletPath;
import org.tinder.filters.RequestFilter;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.util.Date;
import java.util.EnumSet;

public class HTTPServer {
    private final Server server;
    private final ServletContextHandler handler;

    public HTTPServer(int port) {
        server = new Server(port);
        handler = new ServletContextHandler();
    }

    public void addFilter(Filter filter, ServletPath servletPath, EnumSet<DispatcherType> dispatcherTypes) {
        handler.addFilter(new FilterHolder(filter), servletPath.path(), dispatcherTypes);
    }

    public void addFilter(Filter filter, ServletPath servletPath) {
        EnumSet<DispatcherType> dt = EnumSet.of(DispatcherType.REQUEST);
        addFilter(filter, servletPath, dt);
    }

    public ServletHolder addServlet(HttpServlet servlet, ServletPath servletPath) {
        ServletHolder sh = new ServletHolder(servlet);
        handler.addServlet(sh, servletPath.path());
        return sh;
    }
    public ServletHolder addServlet(HttpServlet servlet, ServletPath servletPath, RequestFilter... filters) {
        for (RequestFilter filter : filters) {
            addFilter(filter.of(), servletPath);
        }
        return addServlet(servlet, servletPath);
    }

    private void run() throws Exception {
        server.start();

        System.out.printf("Server started on port: %d, host: %s%n", server.getURI().getPort(), server.getURI().getHost());
        System.out.println(new Date(System.currentTimeMillis()));

        server.join();
    }

    public void start() throws Exception {
        server.setHandler(handler);

        run();
    }
}
