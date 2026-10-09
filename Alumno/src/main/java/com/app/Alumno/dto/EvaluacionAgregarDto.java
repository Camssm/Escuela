
package com.app.Alumno.dto;

import java.time.LocalDate;

import com.app.Alumno.Enum.TipoEvaluacion;
import com.app.Alumno.entity.Evaluacion;
import com.app.Alumno.mappers.IMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionAgregarDto implements IMapper<Evaluacion> {

    private TipoEvaluacion tipo;
    private String descripcion;
    private LocalDate fecha;
    private Long cursoId;
    private Long docenteId;

    @Override
    public Evaluacion mapperTo() {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setTipo(tipo);
        evaluacion.setDescripcion(descripcion);
        evaluacion.setFecha(fecha);
        evaluacion.setCursoId(cursoId);
        evaluacion.setDocenteId(docenteId);

        return evaluacion;
    }
}
