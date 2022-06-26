package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/demowebshop/app.properties"})
public interface AppConfig extends Config {

    @Key("baseUrl")
    String baseUrl();

    @Key("baseURI")
    String baseURI();

    @Key("userLogin")
    String userLogin();

    @Key("userPassword")
    String userPassword();

    @Key("selenoidUrl")
    String selenoidUrl();

    @Key("selenoidLogin")
    String selenoidLogin();

    @Key("selenoidPassword")
    String selenoidPassword();

    @Key("browser")
    String browser();

    @Key("browserVersion")
    String browserVersion();
}
