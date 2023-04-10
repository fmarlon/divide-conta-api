package me.frankmms.divideconta.infrastructure.spring;


import lombok.AllArgsConstructor;
import me.frankmms.divideconta.domain.ServiceProvider;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ApplicationContext;

@AllArgsConstructor
public class SpringServiceProvider implements ServiceProvider {

    ApplicationContext applicationContext;

    @Override
    public <T> T get(Class<T> type) {
        return applicationContext.getBean(type);
    }

    @Override
    public <T> T get(Class<T> type, Object param) {
        T bean = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getAutowireCapableBeanFactory(), type, param.toString());
        return bean;
    }
}
