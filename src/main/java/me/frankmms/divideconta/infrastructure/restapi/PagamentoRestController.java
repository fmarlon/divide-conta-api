package me.frankmms.divideconta.infrastructure.restapi;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.frankmms.divideconta.application.PagamentoAppService;
import me.frankmms.divideconta.application.model.CobrancaDTO;
import me.frankmms.divideconta.application.model.GerarCobrancaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "pagamentos")
@RestController
@RequestMapping("/pagamentos")
public class PagamentoRestController {

    @Autowired
    PagamentoAppService service;

    @PostMapping("gerar-cobranca")
    public ResponseEntity<CobrancaDTO>  gerarCobranca(@RequestBody GerarCobrancaDTO solicitacao) {
        var cobrancaDTO = service.gerarCobranca(solicitacao);

        return ResponseEntity.ok(cobrancaDTO);
    }

}
