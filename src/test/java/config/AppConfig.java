package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/demowebshop/app.properties"})
public interface AppConfig extends Config {

    @Key("baseUrl")
    String baseUrl();

    @Key("baseUri")
    String baseURI();

    @Key("userLogin")
    String login();

    @Key("userPassword")
    String password();
}
