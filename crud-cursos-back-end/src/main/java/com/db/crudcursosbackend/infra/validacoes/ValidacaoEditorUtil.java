package com.db.crudcursosbackend.infra.validacoes;

import com.db.crudcursosbackend.domain.base.EntidadeBaseAudicao;
import com.db.crudcursosbackend.domain.cursos.Curso;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.infra.excecoes.EditorInvalido;
import java.time.LocalDateTime;

public class ValidacaoEditorUtil {

    public static void validarAtualizacao(Pessoa editor, EntidadeBaseAudicao entidade) {
        if (editor != null){
            if (editor.isAtivo()){
                entidade.setAtualizadoAs(LocalDateTime.now());
                entidade.setAtualizadoPor(editor.getContato().getEmail());
            } else{
                throw new EditorInvalido("Editor " + editor.getContato().getEmail() + " foi desativado.");
            }
        } else {
            throw new EditorInvalido("Editor nulo.");
        }
    }

    public static void validarRegistro(EntidadeBaseAudicao entidade, Pessoa editor) {
        if (editor != null){
            if (editor.isAtivo()) {
                entidade.setAtivo(true);
                entidade.setCriadoAs(LocalDateTime.now());
                entidade.setCriadoPor(editor.getContato().getEmail());
            }
            else {
                throw new EditorInvalido("Editor " + editor.getContato().getEmail() + " foi desativado.");
            }
        } else {
            throw new EditorInvalido("Editor nulo.");
        }
    }
}
