package com.ptit.bookecommerce.utils;

public class Constants {
	public final static String URL_SERVER = "http://192.168.1.105:8000";
	

	public static final String URL_GENRE = URL_SERVER + "/api/genre";
	public static final String URL_BOOKS_BY_GENRE = URL_SERVER + "/api/book/genre";
	public static final String URL_BOOK = URL_SERVER + "/api/book/get";
	public static final String URL_BOOKS_NEW = URL_SERVER + "/api/book/new/";
	public static final String URL_BOOKS_ALL = URL_SERVER + "/api/book/";
	public static final String URL_BOOKS_RECOMMENDATION = URL_SERVER + "/api/book/recommendation/";
	public static final String URL_BOOK_SEARCH = URL_SERVER + "/api/book/search/";
	public final static String URL_BOOK_IMAGE = URL_SERVER + "/api/book/image?cover_url=";
	public static final String URL_CUSTOMER_LOGIN = URL_SERVER + "/api/customer/login/";
	public static final String URL_CUSTOMER_REGISTER = URL_SERVER + "/api/customer/register/";
	public static final String URL_CUSTOMER_PROFILE = URL_SERVER + "/api/customer/profile/";
	public static final String URL_CUSTOMER_OAUTH_REQUEST = URL_SERVER + "/api/customer/oauth/request/";
	public static final String URL_CUSTOMER_OAUTH = URL_SERVER + "/api/customer/oauth/";
	public static final String URL_ORDER = URL_SERVER + "/api/order/";	
	
	public static final String LOGIN_SUCCESS = "Login Success";
	public static final String REGISTER_SUCCESS = "Register Success";
	public static final String CHECKOUT_SUCCESS = "Checkout Success";
	public static final String REQUIRE_LOGIN = "You Have To Login";
	
	public static final String SEARCH_TYPE_TITLE = "book_title";
	
	public static final int FRAGMENT_PROFILE = 1;
	public static final int FRAGMENT_HOME = 0;
	public static final int FRAGMENT_CART = 2;
	
	public static final String[] qtyValues = {"0","1","2","3","4","5","6","7","8","9","10"};
}
