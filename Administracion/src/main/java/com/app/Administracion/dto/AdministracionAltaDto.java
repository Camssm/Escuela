package com.app.Administracion.dto;

import com.app.Administracion.Enum.Cargo;
import com.app.Administracion.entity.Administracion;
import com.app.Administracion.mappers.IMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministracionAltaDto implements IMapper<Administracion> {

    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private Cargo cargo;

    @Override
    public Administracion mapperTo() {

        return new Administracion(
                nombre,
                apellido,
                dni,
                email,
                cargo
        );
    }
}