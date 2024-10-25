package user.userpojo;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserPojo {
    private String name;
    private String job;
    private String id;
    private String createdAt;

    public static UserPojo userPojoInstance = null;

    private UserPojo() {
    }

    public static UserPojo userConfigHelperPojo() {
        if (userPojoInstance == null) {
            return new UserPojo();
        } else {
            return userPojoInstance;
        }
    }
}