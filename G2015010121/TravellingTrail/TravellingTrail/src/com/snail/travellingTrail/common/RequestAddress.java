package com.snail.travellingTrail.common;


public class RequestAddress
{
	
	public static final String SERVER_HOST_NAME = "http://115.29.138.60:17777/";
//	public static final String SERVER_HOST_NAME = "http://192.168.1.112:17777/";
//	public static final String SERVER_HOST_NAME = "http://wando.wicp.net:17777";
//	public static final String SERVER_HOST_NAME = "http://capta.nat123.net:54234";

	
	//获取某个旅程的介绍（简要）信息
	public static final String GET_TRAVEL_NOTES_INTRODUCTION = SERVER_HOST_NAME + "/api/travel/user/";
	//获取某个旅程所有迹点信息(足迹地图专用)
	public static final String GET_TRAVEL_MAP_FOOTPRINTS = SERVER_HOST_NAME + "/api/footprintsformap/travel/";
	//获取某个旅程所有迹点信息
	public static final String GET_TRAVEL_FOOTPRINTS = SERVER_HOST_NAME + "/api/footprints/travel/";
	//获取单个迹点信息
	public static final String GET_SINGLE_FOOTPRINT = SERVER_HOST_NAME + "/api/footprints/";
	//获取某个迹点的评论
	public static final String GET_SINGLE_FOOTPRINT_COMMENTS = SERVER_HOST_NAME + "/api/footprints/comment/";

	//获取旅程的评论
	public static final String GET_TRAVEL_COMMENTS = SERVER_HOST_NAME + "/api/travels/comment/";

	
	//获取某用户所有旅程所有迹点信息(足迹地图专用)
	public static final String GET_USER_ALL_TRAVEL_MAP_FOOTPRINTS = SERVER_HOST_NAME + "/api/footprintsformap/user/";
	
	//广场显示的旅程
	public static final String SQUARE_TRIP = SERVER_HOST_NAME + "/api/travels/";
	
	//个人资料
	public static final String MINE_DATA = SERVER_HOST_NAME + "/api/users/";
	
	//个人所有旅程列表
	public static final String PERSONAL_TRAVEL = SERVER_HOST_NAME + "/api/travels/user/";
	
	//注册
	public static final String REGISTER = SERVER_HOST_NAME + "/api/user/";
	
	//登录
	public static final String LOGIN = SERVER_HOST_NAME + "/api/useraccount/";
	
	//个人所有旅程
	public static final String TRIP_FOOT_POINTS = SERVER_HOST_NAME + "/api/travelsmap/user/";
	
	//获取客户端更新信息信息
	public static final String UPDATE = SERVER_HOST_NAME + "/api/client/";

	//修改个人资料
	public static final String MODIFY_DATA = SERVER_HOST_NAME + "/api/users/modify/info/";
	
	//创建旅程
	public static final String CREATE_NEW_TRAVEL = SERVER_HOST_NAME + "/api/travels/";
	
	//创建足迹点
	public static final String CREATE_FOOTPRINT = SERVER_HOST_NAME + "/api/footprints/";
	
	//上传足迹点图文内容
	public static final String SEND_FOOTPRINT_CONTENT = SERVER_HOST_NAME + "/api/footprints/content/";
	
	//获取用户粉丝列表
	public static final String GET_FANS_LIST = SERVER_HOST_NAME + "/api/user/fans/";
	
	//获取用户关注列表
	public static final String GET_FOLLOWS_LIST = SERVER_HOST_NAME + "/api/user/concern/";
	
	//评论足迹点
	public static final String SEND_FOOTPRINT_COMMENT = SERVER_HOST_NAME + "/api/footprint/comment/";
	
	//评论旅程
	public static final String SEND_TRAVEL_COMMENT = SERVER_HOST_NAME + "/api/travel/comment/";
	
	//关注某一用户
	public static final String FOLLOW_SOMEONE = SERVER_HOST_NAME + "/api/user/add/fans/";

	//点赞旅程
	public static final String LIKE_TRAVEL = SERVER_HOST_NAME + "/api/travel/like/";

	//点赞足迹点
	public static final String LIKE_FOOTPRINT = SERVER_HOST_NAME + "/api/travel/like/";
	
	//结束旅程
	public static final String FINISH_TRAVEL = SERVER_HOST_NAME + "/api/travels/end/";
}
