package com.tririga.custom;

import com.tririga.pub.adapter.IConnect;
import com.tririga.ws.TririgaWS;
import in.vadlakonda.equilibrium.api.APIException;
import in.vadlakonda.equilibrium.dispatch.RequestDispatcherFactory;
import in.vadlakonda.equilibrium.init.AppInitiliazer;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

public class Equilibrium implements IConnect {

    private static final org.apache.log4j.Logger log = Logger.getLogger(Equilibrium.class);

    private static final RequestDispatcherFactory requestDispatcherFactory;
    private static final String TRIRIGA_WS_SESSION_KEY = "TririgaWS";

    static {

        File baseDir = AppInitiliazer.init();
        requestDispatcherFactory = RequestDispatcherFactory.getRequestDispatcherFactory("dispatcher-config.json",AppInitiliazer.getAppClassLoader(),baseDir);


    }

    @Override
    public void execute(TririgaWS tririgaWS, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        HttpSession session = httpServletRequest.getSession(false);

        if(session != null)
            session.setAttribute(TRIRIGA_WS_SESSION_KEY, tririgaWS);

        requestDispatcherFactory.getRequestDispatcher(httpServletRequest).dispatch(httpServletRequest, httpServletResponse, AppInitiliazer.getAppClassLoader());


    }

}
