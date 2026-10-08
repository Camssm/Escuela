package com.app.Cursos.mappers;

public interface IMapper<D, E> {

    public D toDto();

    public E toEntity();
}