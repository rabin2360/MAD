package com.example.rabin.testapplication;

/**
 * Created by rabinranabhat on 11/23/16.
 */
public class ValueContainer<T> {
    private T value;

    public ValueContainer()
    {

    }

    public ValueContainer(T v)
    {
        this.value = v;
    }

    public T getVal()
    {
        return value;
    }

    public void setVal(T v)
    {
        this.value = v;
    }

    public String toString()
    {
        if(value != null)
           return value.toString();
        else
            return null;
    }
}

