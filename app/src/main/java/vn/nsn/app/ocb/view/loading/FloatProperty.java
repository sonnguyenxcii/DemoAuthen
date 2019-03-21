package vn.nsn.app.ocb.view.loading;

import android.util.Property;

public abstract class FloatProperty<T> extends Property<T, Float> {

    FloatProperty(String name) {
        super(Float.class, name);
    }

    public abstract void setValue(T object, float value);

    @Override
    final public void set(T object, Float value) {
        setValue(object, value);
    }

}