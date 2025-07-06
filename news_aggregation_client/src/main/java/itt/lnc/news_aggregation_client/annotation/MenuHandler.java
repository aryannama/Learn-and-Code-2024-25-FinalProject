package itt.lnc.news_aggregation_client.annotation;

import itt.lnc.news_aggregation_client.constants.MenuType;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface MenuHandler {
    MenuType menuType();
}
