package user.services;

import core.API.setup.RestAPIBase;
import io.restassured.response.Response;
import user.userpojo.UserPojo;

public class UserConfigHelperServices {

    public static Response createUserData(){
        UserPojo userPojoObj = UserPojo.userConfigHelperPojo();
        userPojoObj.setName("Kotesh");
        userPojoObj.setJob("QA");

        String userDataPayload = RestAPIBase.getJsonString(userPojoObj);
        return RestAPIBase.postCallNoAuth("api/users", userDataPayload);
    }

    public Response createUserData(String userName){
        UserPojo userPojoObj = UserPojo.userConfigHelperPojo();
        userPojoObj.setName(userName);
        userPojoObj.setJob("QA");
        userPojoObj.getName();
        String userDataPayload = RestAPIBase.getJsonString(userPojoObj);
        return RestAPIBase.postCallNoAuth("api/users", userDataPayload);
    }
}
