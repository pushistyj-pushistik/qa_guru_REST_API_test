package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/demowebshop/app.properties"})
public interface AppConfig extends Config {

    @Key("baseUrl")
    String baseUrl();

    @Key("baseUri")
    String baseUri();

    @Key("userLogin")
    String userLogin();

    @Key("userPassword")
    String userPassword();
}
