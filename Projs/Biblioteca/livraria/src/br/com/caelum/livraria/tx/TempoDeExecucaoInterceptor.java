package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Log
@Interceptor
public class TempoDeExecucaoInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@AroundInvoke
	public Object executaLog(InvocationContext contexto) throws Exception {

		long antes = System.currentTimeMillis();

		Object resultado = contexto.proceed();

		long depois = System.currentTimeMillis();

		System.out.println("Metodo:" + contexto.getMethod().getName() + " Tempo Gasto: " + (depois - antes));

		return resultado;
	}
}
