
package com.app.Alumno.dto;

import com.app.Alumno.entity.Nota;
import com.app.Alumno.mappers.IMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaAgregarDto implements IMapper<Nota> {

    private Double valor;
    private Long evaluacionId;
    private Long alumnoId;

    @Override
    public Nota mapperTo() {
        Nota nota = new Nota();
        nota.setValor(valor);

        return nota;
    }
}
