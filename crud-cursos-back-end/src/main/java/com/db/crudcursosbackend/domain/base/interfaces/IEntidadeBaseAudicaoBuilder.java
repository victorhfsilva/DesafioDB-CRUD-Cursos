package com.db.crudcursosbackend.domain.base.interfaces;

import java.time.LocalDateTime;

public interface IEntidadeBaseAudicaoBuilder {
    IEntidadeBaseAudicaoBuilder criadoPor(String email);
    IEntidadeBaseAudicaoBuilder atualizadoPor(String email);
    IEntidadeBaseAudicaoBuilder desativadoPor(String email);
    IEntidadeBaseAudicaoBuilder criadoAs(LocalDateTime timestamp);
    IEntidadeBaseAudicaoBuilder atualizadoAs(LocalDateTime timestamp);
    IEntidadeBaseAudicaoBuilder desativadoAs(LocalDateTime timestamp);
    IEntidadeBaseAudicaoBuilder ativo(boolean active);
}
