package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/demowebshop/app.properties",
        "classpath:config/demowebshop/remote.properties",})
public interface AppConfig extends Config {

    @Key("baseUrl")
    String baseUrl();

    @Key("baseURI")
    String baseURI();

    @Key("login")
    String login();

    @Key("password")
    String password();

    @Key("selenoidUrl")
    String selenoidUrl();

    @Key("selenoidlogin")
    String selenoidlogin();

    @Key("selenoidpassword")
    String selenoidpassword();
}
