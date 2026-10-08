<<<<<<< HEAD

=======
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
package com.app.Alumno.service;

import java.util.List;

<<<<<<< HEAD
import com.app.Alumno.dto.NotaAgregarDto;
=======
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)
import com.app.Alumno.dto.NotaDto;

public interface NotaService {

<<<<<<< HEAD
    NotaDto crear(NotaAgregarDto dto);
=======
    NotaDto crear(NotaDto dto);
>>>>>>> 1674818 (uniendo parte de sol y cami para formar una estructura como base)

    List<NotaDto> listarPorEvaluacion(Long evaluacionId);

    NotaDto modificar(Long id, NotaDto dto);

    void eliminar(Long id);
}
