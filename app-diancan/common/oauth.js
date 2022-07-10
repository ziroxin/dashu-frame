export default async function oauth(e) {
	// 主要拦截判断用户取消授权操作
	let resume = e && e.detail?.errorMessage && e.detail?.errorMessage.indexOf('取消授权');
	if (resume) {
		// 一个弹框
		this.$message('用户取消授权');
		return false;
	}
	// 获取服务商
	let provider = await getProvider();
	// 获取登录code
	let loginRes = await getLogin(provider);
	// 获取用户信息(头像，昵称，地区等)
	// let userInfo = await getUserInfo(provider);
	// #ifdef MP-ALIPAY
	// 支付宝返回的数据为JSON字符串，处理一下
	// userInfo = JSON.parse(userInfo).response
	// #endif
	// 获取用户手机号
	// let phoneNumber = await getPhoneNumber(provider);
	// #ifdef MP-ALIPAY
	// 支付宝返回的数据为JSON字符串，处理一下
	// phoneNumber = JSON.parse(phoneNumber);
	// #endif
	let userInfoObj = {
		loginCode: loginRes.code || '', // login获取code/authCode
		// nickName: userInfo.nickName, // getUserInfo 获取用户昵称
		// avatarUrl: userInfo.avatarUrl || userInfo.avatar, // getUserInfo 获取用户头像
		// userPhoneNumber: phoneNumber.response || '', // getPhoneNumber 获取用户手机号
		// sign: phoneNumber.sign || '', // getPhoneNumber 获取用户手机号时的加密签
	}
	return userInfoObj;
}

export function getProvider() {
	return new Promise((resolve, reject) => {
		uni.getProvider({
			service: 'oauth',
			success: function(res) {
				if (res.provider.includes('weixin')) {
					resolve("weixin");
				} else {
					resolve("ali");
				}
			}
		});
	})
}

export function getLogin(pay) {
	return new Promise((resolve, reject) => {
		uni.login({
			scopes: "auth_user",
			// auth_base
			provider: pay,
			success: (loginRes) => {
				// 获取登录code
				resolve(loginRes)
			}
		});
	})
}

export function getUserInfo(pay) {
	return new Promise((resolve, reject) => {
		// #ifdef MP-ALIPAY
		my.getOpenUserInfo({
			success: (info) => {
				resolve(info.response);
			},
			fail: (err) => {
				reject(err);
			}
		})
		// #endif
		// #ifndef MP-ALIPAY
		uni.getUserInfo({
			provider: pay,
			success: (info) => {
				resolve(info['userInfo']);
			},
			fail: (err) => {
				reject(err);
			}
		})
		// #endif

	})
}

export function getPhoneNumber(pay) {
	return new Promise((resolve, reject) => {
		// #ifdef MP-ALIPAY
		my.getPhoneNumber({
			protocols: {
				// 小程序模板所属的三方应用appId        
				isvAppId: '2021002146627162'
			},
			success: res => {
				resolve(res.response);
			},
			fail: res => {
				reject(res);
			}
		});
		// #endif
		// #ifdef MP-WEIXIN
		wx.getPhoneNumber({
			success: (res) => {
				resolve(res);
			},
			fail: (err) => {
				reject(err)
			}
		})
		// #endif
		// #ifndef MP-ALIPAY || MP-WEIXIN
		uni.getPhoneNumber({
			success: (res) => {
				resolve(res);
			},
			fail: (err) => {
				reject(err)
			}
		})
		// #endif
	})
}
