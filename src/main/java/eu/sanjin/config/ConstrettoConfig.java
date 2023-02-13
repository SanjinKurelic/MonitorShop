package eu.sanjin.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.constretto.ConstrettoBuilder;
import org.constretto.ConstrettoConfiguration;
import org.constretto.model.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("SpellCheckingInspection")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstrettoConfig {

    private static ConstrettoConfiguration configuration;

    public static ConstrettoConfiguration getConfiguration() {
        if (Objects.isNull(configuration)) {
            configuration = new ConstrettoBuilder(ConstrettoConfig::getTags, true)
                .createIniFileConfigurationStore()
                .addResource(Resource.create(Resource.CLASSPATH_PREFIX + "configuration.ini"))
                .addResource(Resource.create(Resource.CLASSPATH_PREFIX + "configuration_secret.ini"))
                .done()
                .getConfiguration();
        }

        return configuration;
    }

    private static List<String> getTags() {
        var profile = System.getProperty("profile");

        if (Objects.isNull(profile)) {
            return List.of();
        }

        return Arrays.stream(profile.split(",")).toList();
    }
}

