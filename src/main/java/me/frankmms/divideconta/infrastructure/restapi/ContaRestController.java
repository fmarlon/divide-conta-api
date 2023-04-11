package me.frankmms.divideconta.infrastructure.restapi;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.frankmms.divideconta.application.ContaAppService;
import me.frankmms.divideconta.application.model.DivisaoDTO;
import me.frankmms.divideconta.application.model.ContaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "contas")
@RestController
@RequestMapping("/contas")
public class ContaRestController {

    @Autowired
    ContaAppService service;

    @PostMapping("dividir")
    public ResponseEntity<DivisaoDTO>  calcularDivisao(@RequestBody ContaDTO contaDTO) {
        var result = service.calcularDivisao(contaDTO);

        return ResponseEntity.ok(result);
    }

}
