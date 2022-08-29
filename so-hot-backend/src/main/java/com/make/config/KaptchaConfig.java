package com.make.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static com.google.code.kaptcha.Constants.*;

/**
 * @Author make
 * @Description 图片生成器
 * @Date 2022/8/19 16:44
 */

@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        // 是否有边框
        properties.put(KAPTCHA_BORDER, "no");
        properties.put(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        properties.put(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "30");
        properties.put(KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "4");
        properties.put(KAPTCHA_IMAGE_HEIGHT, "35");
        properties.put(KAPTCHA_NOISE_IMPL, "120");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
