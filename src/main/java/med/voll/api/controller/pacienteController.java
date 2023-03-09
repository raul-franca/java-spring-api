package med.voll.api.controller;

import med.voll.api.paciente.DadosPaciente;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("paciente")
public class pacienteController {
    @PostMapping
    public void cadastroPaciente(@RequestBody DadosPaciente paciente){
        System.out.println(paciente);
    }
}
