package br.com.campanha.application.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
/**
 * Classe para padronizacao e tratamento de execção
 * @author rodrigo
 *
 */
@Getter
@ToString
public class ApiException extends Exception
{
	private static final long serialVersionUID = 7062694006829294189L;
	public static final String VALIDATION_ERROR = "validation_error";
    public static final String GENERAL_ERROR = "general_error";
    public static final String NOTFOUND_ERROR = "notfound_error";
    public static final String UNPROCESSABLE_ENTITY = "unprocessable_entity";
    private final String code;
    private final String reason;
    private final Integer statusCode;

    @Builder
    public ApiException(String code, String reason, String message, Integer statusCode)
    {
        super(message);
        this.code = code;
        this.reason = reason;
        this.statusCode = statusCode;
    }
}