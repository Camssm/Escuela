package com.app.Alumno.mappers;

public interface IMapper<D, E> {

    public D toDto();

    public E toEntity();
}