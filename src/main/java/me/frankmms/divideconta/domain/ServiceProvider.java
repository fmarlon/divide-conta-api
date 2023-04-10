package me.frankmms.divideconta.domain;

public interface ServiceProvider {
    <T> T get(Class<T> type);

    <T> T get(Class<T> type, Object param);

}
