package br.com.naveguard.naveguardBackend.infra.errors;

public class TokenException extends RuntimeException {
    public TokenException(String mensagem) { super(mensagem); }

}