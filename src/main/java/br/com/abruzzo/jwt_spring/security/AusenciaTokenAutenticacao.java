package br.com.abruzzo.jwt_spring.security;


import org.apache.commons.logging.Log;

/**
 * @link https://projectlombok.org/features/log
 */
public class AusenciaTokenAutenticacao extends RuntimeException {


    public AusenciaTokenAutenticacao(String mensagemErro) {
        super(mensagemErro);
    }

    public AusenciaTokenAutenticacao(String mensagemErro, Log logger) {
        super(mensagemErro);
        logger.error(mensagemErro);
    }
}
