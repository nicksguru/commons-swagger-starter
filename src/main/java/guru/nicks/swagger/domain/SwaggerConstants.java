package guru.nicks.swagger.domain;

import lombok.experimental.UtilityClass;

/**
 * Data for {@link io.swagger.v3.oas.annotations.tags.Tag @Tag}.
 */
@UtilityClass
public class SwaggerConstants {

    public static final String TAG_INTERNAL = "internal";
    public static final String TAG_INTERNAL_DESCRIPTION = "Internal API for calls between own microservices only";

    public static final String TAG_MY = "my";
    public static final String TAG_MY_DESCRIPTION = "User operations on own resources";

    public static final String TAG_USER_PROFILES = "user-profiles";
    public static final String TAG_USER_PROFILES_DESCRIPTION = "User profile management";

    public static final String TAG_USERS = "users";
    public static final String TAG_USERS_DESCRIPTION = "User account management";

    public static final String TAG_AUTHENTICATION = "auth";
    public static final String TAG_AUTHENTICATION_DESCRIPTION = "Authentication";

    public static final String TAG_ORDERS = "orders";
    public static final String TAG_ORDERS_DESCRIPTION = "Order management";

    public static final String TAG_I18N = "i18n";
    public static final String TAG_I18N_DESCRIPTION = "Localization and internationalization";

}
