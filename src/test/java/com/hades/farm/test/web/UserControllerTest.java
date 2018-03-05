package com.hades.farm.test.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.hades.farm.utils.JSONUtils;
import com.hades.farm.web.model.ViewResult;
import com.hades.farm.web.model.view.user.UserModel;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Map;

public class UserControllerTest extends BaseWebTest{

    private final static Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    @Test
    public void getUserById() throws IOException {
        String url = host + port + "/api/user/getUserById";
        Map<String, String> json = Maps.newHashMap();
        json.put("userId", "1");
        String postRes = post(url, json);
        Assert.assertNotNull(postRes);

        ViewResult<UserModel> result = JSONUtils.stringToObjectForGeneric(postRes, new TypeReference<ViewResult<UserModel>>() {
        });
        Assert.assertTrue(result.isOk());
    }

}
