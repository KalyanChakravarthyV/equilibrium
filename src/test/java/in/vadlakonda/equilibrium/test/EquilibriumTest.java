package in.vadlakonda.equilibrium.test;

import com.tririga.custom.Equilibrium;
import com.tririga.ws.TririgaWS;
import org.apache.log4j.Logger;
import org.mockito.Mock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EquilibriumTest {
    private static final org.apache.log4j.Logger log = Logger.getLogger(EquilibriumTest.class);

    @org.junit.jupiter.api.Test
    void execute() throws ServletException, IOException {

        TririgaWS tririgaWS = mock(TririgaWS.class);

        HttpServletRequest request =
                mock(HttpServletRequest.class);

        when(request.getRequestURI()).thenReturn("/html/en/default/rest/Equilibrium/resource/dashboard/index.html");

        HttpServletResponse response  =
                mock(HttpServletResponse.class);

        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);

        when(response.getWriter()).thenReturn(writer);

        new Equilibrium().execute(tririgaWS,request, response);

        log.info(out.getBuffer());

    }
}