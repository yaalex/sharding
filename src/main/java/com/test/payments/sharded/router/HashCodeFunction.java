package com.test.payments.sharded.router;

public class HashCodeFunction implements RouteFunction {
	/**
	 * Функция с хардкодом кол-ва серверов =3
	 * @param key - ключ роутинга
	 * @return - число от 0 до 2 указывающее на номер сервера
	 */
	@Override
	public Long getValue(String key) {
		return (long) Math.abs(key.hashCode() % 3);
	}
}
