package test.http.message.client;

import test.http.Http;
import test.http.message.HeaderField;

public class AcceptLanguage extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public AcceptLanguage(String text)
	{
		super(Http.ACCEPTL, text);
	}
}
