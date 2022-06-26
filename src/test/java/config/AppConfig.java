package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/demowebshop/app.properties"})
public interface AppConfig extends Config {

    @Key("baseUrl")
    String baseUrl();

    @Key("baseURI")
    String baseURI();

    @Key("login")
    String login();

    @Key("password")
    String password();
}
