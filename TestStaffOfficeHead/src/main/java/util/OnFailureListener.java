package util;

import java.util.Properties;
import util.WebAccess;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import testlink.api.java.client.TestLinkAPIException;
import testlink.api.java.client.TestLinkAPIResults;

public class OnFailureListener extends TestListenerAdapter
{

    private static final Logger logger = Logger.getLogger(OnFailureListener.class);

    static final Properties prop = new Properties();

    static final Properties urls = new Properties();

    @Override
    public void onTestFailure(ITestResult tr)
    {
        logger.error("Error on test", tr.getThrowable());
        String resultado = TestLinkAPIResults.TEST_FAILED;
        notificarTestLink(resultado, tr);

        if (tr.getInstance() instanceof WebAccess) {
        	WebAccess instance = (WebAccess) tr.getInstance();
//            instance.getScreenShotManager().takeFailureScreenShot();
        } else {
            logger.error("Test instance must be an instance AccesoWeb type");
        }
    }

    @Override
    public void onTestSuccess(ITestResult tr)
    {
        String resultado = TestLinkAPIResults.TEST_PASSED;
        notificarTestLink(resultado, tr);
    }

    private void notificarTestLink(String resultado, ITestResult tr)
    {
        String nombrePlan = "Jenkins";
        String nombreBuild = "Full Regression";
        String nombreTestCase = "Not defined";
        String nota = "";

        Object[] att = tr.getParameters();
        if (att.length >= 4) {
            nombreTestCase = att[3].toString();
        }
        if (tr.getThrowable() != null) {
            nota = tr.getThrowable().getMessage();
        }
//        try {
//            ResultadoExecucao.reportTestCaseResult(IConstantes.PROJETO, nombrePlan, nombreTestCase, nombreBuild, nota,
//        //        resultado);
//        } catch (TestLinkAPIException e) {
//            logger.error("Error notifying", e);
//        }
    }

}
