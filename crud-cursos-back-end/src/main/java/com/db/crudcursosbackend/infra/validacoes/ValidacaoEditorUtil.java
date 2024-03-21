package com.db.crudcursosbackend.infra.validacoes;

import com.db.crudcursosbackend.domain.base.EntidadeBaseAudicao;
import com.db.crudcursosbackend.domain.usuario.pessoa.Pessoa;
import com.db.crudcursosbackend.infra.excecoes.EditorInvalido;
import java.time.LocalDateTime;

public class ValidacaoEditorUtil {

    public static void validarAtualizacao(Pessoa editor, EntidadeBaseAudicao entidade) {
        if (editor != null ){
            if (editor.isAtivo() || editorEhIgualEntidade(editor, entidade)){
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
            if (editor.isAtivo() || editorEhIgualEntidade(editor, entidade)) {
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

    public static void validarDesativacao(Pessoa editor, EntidadeBaseAudicao entidade) {
        if (editor != null){
            if (editor.isAtivo() || editorEhIgualEntidade(editor, entidade)){
                entidade.setAtivo(false);
                entidade.setDesativadoAs(LocalDateTime.now());
                entidade.setDesativadoPor(editor.getContato().getEmail());
            } else{
                throw new EditorInvalido("Editor " + editor.getContato().getEmail() + " foi desativado.");
            }
        } else {
            throw new EditorInvalido("Editor nulo.");
        }
    }

    public static void validarAtivacao(Pessoa editor, EntidadeBaseAudicao entidade) {
        if (editor != null){
            if (editor.isAtivo() || editorEhIgualEntidade(editor, entidade)) {
                entidade.setAtivo(true);
                entidade.setAtualizadoAs(LocalDateTime.now());
                entidade.setAtualizadoPor(editor.getContato().getEmail());
                entidade.setDesativadoAs(null);
                entidade.setDesativadoPor(null);
            } else{
                throw new EditorInvalido("Editor " + editor.getContato().getEmail() + " foi desativado.");
            }
        } else {
            throw new EditorInvalido("Editor nulo.");
        }
    }

    private static boolean editorEhIgualEntidade(Pessoa editor, EntidadeBaseAudicao entidade){
        if (entidade instanceof Pessoa){
            Pessoa entidadePessoa = (Pessoa) entidade;
            return editor.getCpf().equals(entidadePessoa.getCpf());
        }
        return false;
    }
}
