package icu.jnet.mcd.model.listener;

public interface StateChangeListener {

    <T> void changed(T source);
}
