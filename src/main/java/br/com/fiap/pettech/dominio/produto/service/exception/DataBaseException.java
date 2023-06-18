package br.com.fiap.pettech.dominio.produto.service.exception;

public class DataBaseException extends  RuntimeException{
    public DataBaseException(String msg){
        super(msg);
    }
}
