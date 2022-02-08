package com.receiverequestresponse.app;

import com.receiverequestresponse.app.utils.Helpers;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReceiveRequestResponseAppApplication.class)
@WebAppConfiguration
public abstract class AbstractTest {

    protected final Properties props = Helpers.loadProps();

    protected final String authRequest = props.getProperty("application.basic-authorization-local");
    protected final String invalidAuthRequest = props.getProperty("application.test.basic-authorization-invalid");

    protected final String uriBaseTestRt = props.getProperty("application.test.uri-base-test-rt");
    protected final String uriBaseTestWc = props.getProperty("application.test.uri-base-test-wc");

    protected final String urlRemoteBaseTest = props.getProperty("application.test.base-url-remote");
    protected final String uriRemoteBaseTest = props.getProperty("application.test.base-uri-remote");
    protected final String authRemoteRequest = props.getProperty("application.test.basic-authorization-remote");

    protected final String uriWrongTest1 = props.getProperty("application.test.uri-base-test-wrong-1");
    protected final String uriWrongTest2 = props.getProperty("application.test.uri-base-test-wrong-2");
    protected final String uriWrongTest3 = props.getProperty("application.test.uri-base-test-wrong-3");

    protected final String userIdFoundTest = props.getProperty("application.test.user-found");
    protected final String userIdNotFoundTest = props.getProperty("application.test.user-not-found");

    protected MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected void createCustomerBeforeTest(String customer_data, String resource) throws Exception {
        String uri = uriBaseTestRt;
        if (resource.equals("wc")) uri = uriBaseTestWc;
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(uri)
                                .content(customer_data)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", authRequest)
                ).andReturn();
    }

    protected void rollbackTest(String md5Id, String resource) throws Exception {
        String uri = uriBaseTestRt;
        if (resource.equals("wc")) uri = uriBaseTestWc;
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete(uri+"/"+md5Id)
                                .header("Authorization", authRequest)
                ).andReturn();
    }

}