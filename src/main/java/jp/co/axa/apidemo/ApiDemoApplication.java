package jp.co.axa.apidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The class which serves as the starting point for the API demo application.
 */
@EnableSwagger2
@SpringBootApplication
public class ApiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDemoApplication.class, args);
	}

	/**
	 * Configuration for the Cache management.
	 */
	@Configuration
	@EnableCaching
    public static class CacheConfig {

		/**
		 * Cache Manager Bean
		 * 
		 * @return The cache manager instance.
		 */
        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("employees", "employee");
        }
    }

}
